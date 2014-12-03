/*
 * Created on 2004-10-30
 */
package haiyan.orm.database.sql;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.config.util.NamingUtil;
import haiyan.orm.database.TableDBManager;

/**
 * @author zhouxw
 */
class SQLDBMappingManager extends TableDBManager {

	/** 
	 * @param context
	 * @param table
	 * @param field
	 * @param record
	 * @param ID
	 * @param context
	 * @throws Throwable
	 */
	static void queryMappingTable(ITableDBContext context, Table table, Field field, IDBRecord record,
			String ID) throws Throwable {
		Table mappingTable = ConfigUtil.getTable(field.getMappingTable());
		Table refTable = ConfigUtil.getTable(field.getReferenceTable());
		Field refTableChildTableRefField = ConfigUtil.searchChildTableRefField(refTable, mappingTable);
		Field tableChildTableRefField = ConfigUtil.searchChildTableRefField(table, mappingTable);
		if (refTableChildTableRefField == null) {
			throw new Warning(context.getUser(), 100006, "error_childtable_reffld", new String[] { refTable.getName(), mappingTable.getName() });
		}
		if (tableChildTableRefField == null) {
			throw new Warning(context.getUser(), 100006, "error_childtable_reffld", new String[] { table.getName(), mappingTable.getName() });
		}
		String realRefTable = ConfigUtil.getRealTableName(refTable);
		String realMappingTable = ConfigUtil.getRealTableName(mappingTable);
		String sql = "select " + realRefTable + "." + refTable.getId().getName() + "," + realRefTable + "." + ConfigUtil.getDisplayRefFields(field)[0] 
				+ " from " + realRefTable + "," + realMappingTable
				+ " where " + realRefTable + "." + refTable.getId().getName() + "=" + realMappingTable + "." + refTableChildTableRefField.getName()
				+ " and " + realMappingTable + "." + tableChildTableRefField.getName() + "=?";
		Object[][] rs = context.getDBM().getResultArray(sql, 2, new Object[]{ID});
		StringBuffer idBuf = new StringBuffer();
		StringBuffer nameBuf = new StringBuffer();
		for (int j = 0; j < rs.length; j++) {
			if (j > 0) {
				idBuf.append(",");
				nameBuf.append(",");
			}
			idBuf.append(rs[j][0]);
			nameBuf.append(rs[j][1]);
		}
		record.set(field.getName(), idBuf.toString());
		record.set(NamingUtil.getDisplayFieldAlias(field), nameBuf.toString());
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @param ID
	 * @return
	 * @throws Throwable
	 */
	static IDBRecord queryMappingTable(ITableDBContext context, Table table, IDBRecord record, String ID) throws Throwable {
		Field[] mappingFields = ConfigUtil.getMappingTableFileds(table);
		for (Field field : mappingFields)
			queryMappingTable(context, table, field, record, ID);
		return record;
	}
	/**
	 * @param context
	 * @param table
	 * @param field
	 * @param values
	 * @return
	 * @throws Throwable
	 */
	static String[] getIDSFromMapping(ITableDBContext context, Table table, Field field,
			String[] values) throws Throwable {
		if (values.length == 0)
			return new String[0];
		if (field.getMappingTable() == null)
			return values;
		Table mappingTable = ConfigUtil.getTable(field.getMappingTable());
		Table refTable = ConfigUtil.getTable(field.getReferenceTable());
		Field refFldOfRefChildTbl = ConfigUtil.searchChildTableRefField(refTable, mappingTable);
		Field refFldOfChildTbl = ConfigUtil.searchChildTableRefField(table, mappingTable);
		if (refFldOfRefChildTbl == null) {
			throw new Warning(context.getUser(), 100006, "error_childtable_reffld", new String[] { refTable.getName(), mappingTable.getName() });
		}
		if (refFldOfChildTbl == null) {
			throw new Warning(context.getUser(), 100006, "error_childtable_reffld", new String[] { table.getName(), mappingTable.getName() });
		}
		String label = ConfigUtil.isDecimalPK(table)?"":"'";
		String sql = "select " + refFldOfChildTbl.getName()
				+ " from " + ConfigUtil.getRealTableName(mappingTable)
				+ " where " + refFldOfRefChildTbl.getName() + " in (" + StringUtil.joinSQL(values, ",", label) + ")";
		Object[][] rs = context.getDBM().getResultArray(sql, 1, null);
		String[] idValues = new String[rs.length];
		for (int j = 0; j < rs.length; j++) {
			idValues[j] = VarUtil.toString(rs[j][0]);
		}
		return idValues;
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @param ID
	 * @param operationType
	 * @throws Throwable
	 */
	static void setMappingTableValue(ITableDBContext context, Table table, IDBRecord record, String ID,
			int operationType) throws Throwable {
		Field[] mappingFields = ConfigUtil.getMappingTableFileds(table);
		if (mappingFields == null)
			return;
		for (int i = 0; i < mappingFields.length; i++) {
			Table mappingTable = ConfigUtil.getTable(mappingFields[i].getMappingTable());
			String realTableName = ConfigUtil.getRealTableName(mappingTable);
			Table refTable = ConfigUtil.getTable(mappingFields[i].getReferenceTable());
			if (operationType == SET_MAPPING_TABLE_WHEN_MODIFY
				|| operationType == SET_MAPPING_TABLE_WHEN_REMOVE) {
				String deleteSql = "delete from " + realTableName
						+ " where " + ConfigUtil.searchChildTableRefField(table, mappingTable).getName() + "=?";
				DebugUtil.debug(">MappingSQL=" + deleteSql);
				context.getDBM().executeUpdate(deleteSql, new Object[]{ID});
			}
			if (operationType == SET_MAPPING_TABLE_WHEN_MODIFY
				|| operationType == SET_MAPPING_TABLE_WHEN_CREATE) {
				String valueStr = (String)record.get(mappingFields[i].getName());
				String[] values = (StringUtil.isBlankOrNull(valueStr) ? null : valueStr.split(","));
				if (values != null) {
					for (int j = 0; j < values.length; j++) {
						IDBRecord newRecord = context.getDBM().createRecord();
						String key1 = ConfigUtil.searchChildTableRefField(table, mappingTable).getName();
						newRecord.set(key1, ID);
						String key2 = ConfigUtil.searchChildTableRefField(refTable, mappingTable).getName();
						newRecord.set(key2, values[j]);
						context.getDBM().insert(context, mappingTable, newRecord);
					}
				}
			}
		}
	}

}