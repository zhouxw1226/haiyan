///*
// * Created on 2004-10-30
// */
//package haiyan.orm.database.sql;
//
//import haiyan.common.DebugUtil;
//import haiyan.common.StringUtil;
//import haiyan.common.config.ConfigUtil;
//import haiyan.common.intf.datbase.orm.IRecord;
//import haiyan.common.intf.session.IContext;
//import haiyan.config.castorgen.Field;
//import haiyan.config.castorgen.Table;
//
///**
// * @author zhouxw
// */
//class DBOne2OneManager extends DBTableManager {
//
//	/**
//	 * @deprecated
//	 * 
//	 * @param table
//	 * @param field
//	 * @param values
//	 * @return String[]
//	 * @throws Throwable
//	 */
//	static String[] getIDSFromOne2One(Table table, Field field,
//			String[] values, ITableContext context) throws Throwable {
//		// assert (false);
//		if (values.length == 0)
//			return new String[0];
//		//
//		Table oneTable = ConfigUtil.getTable(field.getOne2oneTable());
//		Field oneField = ConfigUtil.getFieldByName(oneTable, field
//				.getOne2oneField());
//		String realTableName = ConfigUtil.getRealTableName(oneTable);
//		//
//		String l = ConfigUtil.isDecimalPK(table)?"":"'";
//		String sql = "select "
//				+ realTableName + "." + oneTable.getId().getName()
//				+ " from "
//				+ realTableName
//				+ " where "
//				+ realTableName + "." + oneField.getName() 
//				+ " in ("
//				+ StringUtil.joinSQL(values, ",", l) 
//				+ ")";
//		//
//		String[][] rs = context.getDBM().getResultStrArray(sql, 1);
//		//
//		String[] idValues = new String[rs.length];
//		for (int j = 0; j < rs.length; j++) {
//			idValues[j] = rs[j][0];
//		}
//		return idValues;
//	}
//
//	/**
//	 * one2one MapTable query
//	 * 
//	 * @param context
//	 * @param table
//	 * @param field
//	 * @param form
//	 * @return
//	 * @throws Throwable
//	 */
//	static IRecord queryOne2OneTable(ITableContext context,Table table, Field field, IRecord form) throws Throwable {
//		// if (field != null && field.getOne2oneTable() != null) {
//		Table one2oneTable = ConfigUtil.getTable(field.getOne2oneTable());
//		Field one2oneField = ConfigUtil.getFieldByName(one2oneTable, field.getOne2oneField());
//		IRecord one2oneForm = null;
//		if (form.getOne2oneForm(one2oneTable) != null) {
//			one2oneForm = form.getOne2oneForm(one2oneTable);
//		} else {
//			DebugUtil.debug(">One2one.findByPK");
//			one2oneForm = context.getDBM().findByPK(one2oneTable,
//					form.get(one2oneTable.getId().getName()), context);
//		}
//		if (one2oneForm == null)
//			one2oneForm = new MapForm();
//		form.set(field.getName(), one2oneForm.get(one2oneField.getName()));
//		form.setOne2oneRecord(one2oneTable, one2oneForm);
//		// }
//		return form;
//	}
//
//	/**
//	 * one2one MapTable setValue
//	 * 
//	 * @param context
//	 * @param table
//	 * @param form
//	 * @param id
//	 * @param operationType
//	 * @throws Throwable
//	 */
//	static void setOne2OneTableValue(ITableContext context, Table table, IRecord form, String id,
//			int operationType) throws Throwable {
//		// assert (false);
//		Field[] fields = ConfigUtil.getOne2oneTableFileds(table);
//		if (fields != null)
//			for (int i = 0; i < fields.length; i++) {
//				Table one2oneTable = ConfigUtil.getTable(fields[i].getOne2oneTable());
//				Field one2oneField = ConfigUtil.getFieldByName(one2oneTable, fields[i].getOne2oneField());
//				if (operationType == SET_MAPPING_TABLE_WHEN_REMOVE) {
//					DebugUtil.debug(">One2oneSQL.delete(may be many-diff one2one)");
//					context.getDBM().delete(one2oneTable, new String[] { id }, context);
//				}
//				if (operationType == SET_MAPPING_TABLE_WHEN_MODIFY
//						|| operationType == SET_MAPPING_TABLE_WHEN_CREATE) {
//					String valueStr = form.get(fields[i].getName());
//					if (valueStr != null) {
//						IRecord one2oneForm = null;
//						if (form.getOne2oneForm(one2oneTable) != null) {
//							one2oneForm = form.getOne2oneForm(one2oneTable);
//						} else {
//							one2oneForm = context.getDBM().findByPK(context, one2oneTable, id);
//						}
//						if (one2oneForm == null) {
//							one2oneForm = context.getDBM().createRecord();
//							one2oneForm.set(one2oneTable.getId().getName(), id);
//							one2oneForm.set(one2oneField.getName(), valueStr);
//							DebugUtil.debug(">One2one.insert(may be many-diff one2one)");
//							context.getDBM().insertNoSyn(context, one2oneTable, one2oneForm);
//						} else {
//							if (!valueStr.equals(one2oneForm.get(one2oneField.getName()))) {
//								one2oneForm.set(one2oneField.getName(), valueStr);
//								DebugUtil.debug(">One2one.update(may be many-diff one2one)");
//								context.getDBM().update(context, one2oneTable, one2oneForm);
//							}
//						}
//						form.setOne2oneRecord(one2oneTable, one2oneForm);
//					}
//				}
//			}
//	}
//
//}
