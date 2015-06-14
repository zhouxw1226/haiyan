/*
 * Created on 2004-10-9
 */
package haiyan.orm.database.sql;

import haiyan.common.StringUtil;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.intf.database.ITableDBTemplate;

/**
 * 平台的核心模板模式，SQL生成和对象关系元数据映射等等都是基于此模板模式
 * <p/>
 * SQL生成
 * call by:{@link haiyan.orm.database.sql.SQLRender}.getBaseSelectSQL(SrvContext, Table) return PrimaryTable
 * <p/>
 * 对象-关系元数据映射
 * call by:{@link haiyan.orm.database.sql.SQLTableDBManager}.getFormByRow(SrvContext, Table)->getBusinessObjFactory(SrvContext, Table) return IRecord
 * <p/>
 * 配置对象处理
 * call by:{@link haiyan.config.util.ConfigUtil}.getIndexFields(Table, Field) return String[][]
 * 
 * @author zhouxw
 */
public abstract class TableDBTemplate implements ITableDBTemplate {
	public TableDBTemplate() {
	}
	/**
	 * @param table
	 * @param globalVars
	 * @throws Throwable
	 */
	public void deal(Table table, Object[] globalVars) throws Throwable {
		final int BEGIN_INDEX = 0;
		Field[] fields = table.getField();
		int refTableIndex;
		refTableIndex = BEGIN_INDEX;
		for (Field field:fields) {
			if (isLazyLoad(table, field))
				continue;
			if (field.getReferenceTable() != null && !field.isVisual()) {
				refTableIndex++;
				dealWithReferenceTable(table, refTableIndex, field, globalVars);
			}
		}
		int index = 1;
		dealWithIdField(table, index++, globalVars);
		refTableIndex = BEGIN_INDEX;
		for (Field field:fields) {
			if (!StringUtil.isBlankOrNull(field.getSubQuerySQL())) {
				dealWithComputeField(table, index++, field, globalVars);
				continue;
			}
			if (field.isVisual()) {
				if (isLazyLoad(table, field)) {
					dealWithLazyLoadField(table, field, globalVars);
					continue;
				} else {
					continue;
				}
			}
			if (!isLazyLoad(table, field))
				if (field.getReferenceTable() != null) {
					refTableIndex++;
					if (!StringUtil.isBlankOrNull(field.getDisplayReferenceField()))
						index = dealWithDisplayField(table, refTableIndex, index++, field, globalVars);
					if (!StringUtil.isBlankOrNull(field.getAssociatedFields())) {
						String[] associatedFields = ConfigUtil.getAssociatedFields(field);
						for (int j = 0; j < associatedFields.length; j++) {
							Field assoField = ConfigUtil.getFieldByName(table, associatedFields[j]);
							dealWithAssociatedField(table, refTableIndex, index++, field, assoField, globalVars);
						}
					}
				} else {
					// TODO option的lazy处理
				}
			dealWithGeneralField(table, index++, field, globalVars);
			if (isLazyLoad(table, field))
				dealWithLazyLoadField(table, field, globalVars);
		}
		Field[] mappingFields = ConfigUtil.getMappingTableFileds(table);
		for (Field f : mappingFields)
			dealWithMappingField(table, f, globalVars);
		// // TODO 处理下真实表 保证实体bean的字段一致 但是界面上没有该值会丢失
		// if (table.getRealName() != null
		// && !table.getName().equalsIgnoreCase(table.getRealName())) {
		// Table rTable = ConfigUtil.getTable(table.getRealName());
		// deal(rTable, globalVars);
		// }
	}
	/**
	 * @param table
	 * @param field
	 * @return boolean
	 */
	protected boolean isLazyLoad(Table table, Field field) {
		if (field.getLazyLoad()) {
			return true;
		}
		return false;
	}
//	/**
//	 * @param table
//	 * @param field
//	 * @param globalVars
//	 * @throws Throwable
//	 */
//	public abstract void dealWithMappingField(Table table, Field field,
//			Object[] globalVars) throws Throwable;
//	/**
//	 * @param table
//	 * @param field
//	 * @param globalVars
//	 * @throws Throwable
//	 */
//	protected abstract void dealWithLazyLoadField(Table table, Field field,
//			Object[] globalVars) throws Throwable;
//	/**
//	 * @param table
//	 * @param index
//	 * @param globalVars
//	 * @throws Throwable
//	 */
//	protected abstract void dealWithIdField(Table table, int index,
//			Object[] globalVars) throws Throwable;
//	/**
//	 * @param table
//	 * @param tableIndex
//	 * @param index
//	 * @param field
//	 * @param globalVars
//	 * @return int
//	 * @throws Throwable
//	 */
//	protected abstract int dealWithDisplayField(Table table, int tableIndex,
//			int index, Field field, Object[] globalVars) throws Throwable;
//	/**
//	 * @param table
//	 * @param tableIndex
//	 * @param index
//	 * @param mainField
//	 * @param associatedField
//	 * @param globalVars
//	 * @throws Throwable
//	 */
//	protected abstract void dealWithAssociatedField(Table table,
//			int tableIndex, int index, Field mainField, Field associatedField,
//			Object[] globalVars) throws Throwable;
//	/**
//	 * @param table
//	 * @param tableIndex
//	 * @param field
//	 * @param globalVars
//	 * @throws Throwable
//	 */
//	protected abstract void dealWithGeneralField(Table table,
//			int indtableIndexex, Field field, Object[] globalVars)
//			throws Throwable;
//	/**
//	 * @param table
//	 * @param tableIndex
//	 * @param field
//	 * @param globalVars
//	 * @throws Throwable
//	 */
//	protected abstract void dealWithComputeField(Table table, int tableIndex,
//			Field field, Object[] globalVars) throws Throwable;
//	/**
//	 * @param table
//	 * @param tableIndex
//	 * @param field
//	 * @param globalVars
//	 */
//	protected abstract void dealWithReferenceTable(Table table, int tableIndex,
//			Field field, Object[] globalVars);
}