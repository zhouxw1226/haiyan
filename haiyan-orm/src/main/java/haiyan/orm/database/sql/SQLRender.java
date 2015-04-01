/*
 * Created on 2004-9-26
 */
package haiyan.orm.database.sql;

import static haiyan.common.StringUtil.isBlankOrNull;
import static haiyan.common.StringUtil.isEmpty;
import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;
import haiyan.common.InvokeUtil;
import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.PluggedFilter;
import haiyan.config.castorgen.QuerySQL;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.castorgen.types.QueryConditionTypeType;
import haiyan.config.intf.database.sql.ITableSQLRender;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.config.util.NamingUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.database.DBPage;
import haiyan.orm.database.TableDBTemplate;
import haiyan.orm.database.sql.page.GeneralDBPageFactory;
import haiyan.orm.database.sql.page.SQLDBPageFactory;
import haiyan.orm.database.sql.query.BetweenCriticalItem;
import haiyan.orm.database.sql.query.CriticalItem;
import haiyan.orm.database.sql.query.EqualCriticalItem;
import haiyan.orm.database.sql.query.InCriticalItem;
import haiyan.orm.database.sql.query.IsNotNullCriticalItem;
import haiyan.orm.database.sql.query.IsNullCriticalItem;
import haiyan.orm.database.sql.query.LeftOuterJoinedTable;
import haiyan.orm.database.sql.query.LikeCriticalItem;
import haiyan.orm.database.sql.query.LowerThanCriticalItem;
import haiyan.orm.database.sql.query.NotEqualCriticalItem;
import haiyan.orm.database.sql.query.NotLikeCriticalItem;
import haiyan.orm.database.sql.query.OrderByItem;
import haiyan.orm.database.sql.query.PrimaryTable;
import haiyan.orm.database.sql.query.Query;
import haiyan.orm.database.sql.query.RefLikeCriticalItem;
import haiyan.orm.database.sql.query.UpperThanCriticalItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhouxw
 */
class SQLRender implements ITableSQLRender {
	protected transient String mainSQL = null;
	protected transient HashMap<String, HashSet<String>> visComponentSPM = new HashMap<String, HashSet<String>>();
	@Override
	public String getSQL() {
		return this.mainSQL;
	}
	public PrimaryTable getBaseSelectSQL(ITableDBContext context, Table table) throws Throwable {
		TableDBTemplate template = new TableDBTemplate() {
			@Override
			public void dealWithReferenceTable(Table table, int index,
					Field field, Object[] globalVars) {
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				String refTableName = field.getReferenceTable();
				pTable.addJoinedTable(new LeftOuterJoinedTable(ConfigUtil.getRealTableName(refTableName), 
					ConfigUtil.getReferenceFieldName(field), pTable.getTableAlias(),
					field.getName(), pTable, pTable.getIndex() + index));
			}
			@Override
			public void dealWithIdField(Table table, int index,
					Object[] globalVars) {
				StringBuffer cols = (StringBuffer) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				cols.append(pTable.getFirstTableAlias()).append(".").append(table.getId().getName()).append(" f_" + index);

			}
			@Override
			public int dealWithDisplayField(Table table, int tableIndex,
					int index, Field field, Object[] globalVars) {
				StringBuffer cols = (StringBuffer) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				String[] disFieldNames = ConfigUtil.getDisplayRefFields(field);
				// Table refTable = ConfigUtil.getTable(field.getCommon().getReferenceTable());
				for (int i = 0; i < disFieldNames.length; i++) {
					// DebugUtil.debug(">disFieldNames[" + i + "]="
					// + pTable.getTableAliasByIndex(pTable.getIndex()
					// + tableIndex) + "." + disFieldNames[i]);
					// Field displayField = ConfigUtil.getFieldByName(refTable, disFieldNames[i]);
					cols.append(",").append(pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex)).append(".")
						.append(disFieldNames[i]).append(" f_" + (index++));
				}
				// DebugUtil.debug(">cols=" + cols.toString() );
				return index;
			}
			@Override
			public void dealWithAssociatedField(Table table, int tableIndex,
					int index, Field mainField, Field associatedField,
					Object[] globalVars) {
				StringBuffer cols = (StringBuffer) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				String[] disRefFlds = ConfigUtil.getDisplayRefFields(associatedField);
				if (disRefFlds.length > 0)
					cols.append(",").append(pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex)).append(".")
						.append(disRefFlds[0]).append(" f_" + index);
				else 
					cols.append(",").append("'WrongDisSetOf_" + associatedField.getName() + "' as f_" + index);
				// pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex)).append(".").append(disRefFlds[0]).append(" f_" + index);
			}
			@Override
			public void dealWithComputeField(Table table, int index,
					Field field, Object[] globalVars) {
				StringBuffer cols = (StringBuffer) globalVars[0];
				cols.append(",").append("(").append(field.getSubQuerySQL()).append(")").append(" f_" + index);
			}
			@Override
			public void dealWithGeneralField(Table table, int index,
					Field field, Object[] globalVars) {
				StringBuffer cols = (StringBuffer) globalVars[0];
				PrimaryTable pTable = (PrimaryTable) globalVars[1];
				cols.append(",").append(pTable.getFirstTableAlias()).append(".").append(field.getName()).append(" f_" + index);
			}
			@Override
			public void dealWithLazyLoadField(Table table, Field field,
					Object[] globalVars) throws Throwable {
				//System.err.println("dealWithLazyLoadField");
			}
			@Override
			public void dealWithMappingField(Table table, Field field,
					Object[] globalVars) throws Throwable {
				//System.err.println("dealWithMappingField");
			}
		};
		StringBuffer cols = new StringBuffer();
		PrimaryTable pTable = new PrimaryTable(ConfigUtil.getRealTableName(table), "");
		template.deal(table, new Object[] { cols, pTable });
		pTable.setSelectColumnSQL("select " + cols.toString() + " from ");
		QuerySQL qs = table.getQuerySQL();
		if (qs!=null) {
			String className = qs.getClassName();
			String methodName = qs.getMethodName();
			String parameter = qs.getParameter();
			String content = qs.getContent();
			if (isExpMethod(methodName)) {
				IExpUtil expUtil = context.getExpUtil();
				if (expUtil==null) {
					throw new Warning("context.exp is null");
				}
				boolean needContent = true;
//				if (isEmpty(parameter)) {
//					throw new Warning("QuerySQL.parameter is null");
//					if (!isEmpty(content)) {
//						needContent = true;
//					}
//				} else {
//					Object v = expUtil.evalExp(parameter);
//					if (v instanceof Boolean) {
//						if (v==Boolean.TRUE)
//							needContent = true;
//					}
//				}
				if (needContent) {
					String sql;
					if (ExpUtil.isFormula(content))
						sql = VarUtil.toString(expUtil.evalExp(content));
					else
						sql = content;
					pTable.setPrimaryTableSQL(sql);
				}
			} else {
				if (!isEmpty(methodName) && !isEmpty(className)) {
					// NOTICE 后面有pf.getContent()的处理
					DebugUtil.debug(">doQuerySQL:" + className + " " + methodName);
					String sql =  InvokeUtil.getString(className, methodName, 
						new Class[] { IContext.class, Table.class, String.class, String.class },
						new Object[] { context, table, pTable.getFirstTableAlias(), parameter });
					pTable.setPrimaryTableSQL(sql);
				} else if (!isEmpty(content)){
					pTable.setPrimaryTableSQL(content);
				}
			}
		}
		return pTable;
	}

	/**
	 * @param table
	 * @param field
	 * @param queryRecord
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getDefaultCriticalItem(Table table, Field field,
			IDBRecord queryRecord, PrimaryTable pTable) throws Throwable {
		if (queryRecord == null)
			return null;
		// CriticalItem item = null;
		AbstractCommonFieldJavaTypeType fieldType = field.getJavaType();
		String fullFieldName = SQLDBHelper.getCriticalFieldName(table, field, pTable);
//		ComponentTypeType compType = field.getCommon().getType();
		if (field.isVisual()) { 
			return null;
		} else if (fieldType==AbstractCommonFieldJavaTypeType.BLOB||fieldType==AbstractCommonFieldJavaTypeType.DBBLOB)
//		(compType.equals(ComponentTypeType.FCKEDITOR)
//				|| compType.equals(ComponentTypeType.FILE)
//				|| compType.equals(ComponentTypeType.IMAGE)) 
		{
			return null;
		} else if (fieldType==AbstractCommonFieldJavaTypeType.STRING||fieldType==AbstractCommonFieldJavaTypeType.PASSWORD) {
//			if (compType.equals(ComponentTypeType.TEXT)
//				|| compType.equals(ComponentTypeType.TEXTAREA)
//				|| compType.equals(ComponentTypeType.READONLYTEXT)
//				|| compType.equals(ComponentTypeType.PASSWORD))
//				return getLikeCriticalItem(queryRecord, table, field, fullFieldName);
//			else 
			if (field.getReferenceTable() != null)
				return getRefLikeCriticalItem(queryRecord, table, field, fullFieldName);
			else
				return getLikeCriticalItem(queryRecord, table, field, fullFieldName);
		} 
		else if (field.getQueryCondition()!=null && field.getQueryCondition().getType()==QueryConditionTypeType.REGION) {
			return getBetweenCriticalItem(queryRecord, table, field, fullFieldName);
		}
		return getEqualCriticalItem(queryRecord, table, field, fullFieldName);
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getIsNullCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		CriticalItem item = new IsNullCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getIsNotNullCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		CriticalItem item = new IsNotNullCriticalItem(fullFieldName,
				CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getLikeCriticalItem(IDBRecord queryRecord, Table table,
			Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		CriticalItem item = new LikeCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getRefLikeCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (!StringUtil.isBlankOrNull(queryRecord.get(field.getName()))) {
			return new LikeCriticalItem(fullFieldName, 
				CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		}
		String disfld = null;
		if (field.getReferenceTable() != null) {
			disfld = NamingUtil.getDisplayFieldAlias(field);
		} else
			// disfld= NamingUtil.getDisplayFieldAlias(field.getName(), field.getCommon().getDisplay , ReferenceField());
			return null; // TODO 需要确定display是否必填
		// CriticalItem item = null;
		if (!StringUtil.isBlankOrNull(queryRecord.get(disfld))) {
			return new RefLikeCriticalItem(fullFieldName, queryRecord.get(disfld), String.class, table, field);
			// TODO SQLDBTypeConvert.getFieldJavaType(field)应该获取displayField的字段类型
		}
		return null;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getNotLikeCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		CriticalItem item = new NotLikeCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getUpperThanCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		CriticalItem item = new UpperThanCriticalItem(fullFieldName,
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getLowerThanCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		CriticalItem item = new LowerThanCriticalItem(fullFieldName,
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getEqualCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		CriticalItem item = new EqualCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getNotEqualCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		CriticalItem item = new NotEqualCriticalItem(fullFieldName,
			CriticalItem.getItemValue(queryRecord, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}
	/**
	 * @param context
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return
	 * @throws Throwable
	 */
	protected CriticalItem getInCriticalItem(ITableDBContext context, IDBRecord queryRecord, Table table, 
			Field field, String fullFieldName)
			throws Throwable {
		if (queryRecord==null)
			return null;
		if (context==null)
			return null;
		if (StringUtil.isBlankOrNull(queryRecord.get(field.getName())))
			return null;
		String[] args = (String[])queryRecord.get(field.getName());
		ArrayList<String> argList = new ArrayList<String>();
		if (args != null)
			for (int i = 0; i < args.length; i++) {
				if (StringUtil.isBlankOrNull(args[i]))
					continue;
				String[] brgs = StringUtil.split(args[i], ",");
				for (int j = 0; j < brgs.length; j++) {
					if (StringUtil.isBlankOrNull(brgs[j]))
						continue;
					argList.add(brgs[j]);
				}
			}
		String[] values = new String[argList.size()];
		for (int i = 0; i < argList.size(); i++) {
			values[i] = (String) argList.get(i);
		}
		CriticalItem item;
		if (!field.getMultipleSelect()) {
			item = new InCriticalItem(fullFieldName, values, String.class);
		} else {
			String[] values2 = SQLDBMappingManager.getIDSFromMapping(context, table, field, values);
			item = new InCriticalItem(fullFieldName, values2, String.class);
		}
		return item;
	}
	/**
	 * @param queryRecord
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getBetweenCriticalItem(IDBRecord queryRecord,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (queryRecord == null)
			return null;
		Object lowerValue = CriticalItem.getItemValue(queryRecord, NamingUtil.getRegionFieldName(field.getName(), NamingUtil.REGION_START),
				table, field);
		Object upperValue = CriticalItem.getItemValue(queryRecord, NamingUtil.getRegionFieldName(field.getName(), NamingUtil.REGION_END),
				table, field);
		// if (lowerValue == null && upperValue == null)
		// return null;
		CriticalItem item = new BetweenCriticalItem(fullFieldName, lowerValue,
				upperValue, SQLDBTypeConvert.getJavaType(field));
		return item;
	}
	/**
	 * @param table
	 * @param queryRecord
	 * @param pTable
	 * @param cItems
	 * @return ArrayList<CriticalItem>
	 * @throws Throwable
	 */
	protected ArrayList<CriticalItem> getCriticalItems(ITableDBContext context, Table table, 
			PrimaryTable pTable, IDBRecord queryRecord, CriticalItem[] cItems) throws Throwable {
		if (queryRecord==null)
			return null;
		if (context==null)
			return null;
		ArrayList<CriticalItem> critcalItems = new ArrayList<CriticalItem>();
		Field[] fields = table.getField();
		for (Field field:fields) {
			CriticalItem item = null;
			if (field.getQueryCondition() != null && field.getQueryCondition().getType() != null) {
				QueryConditionTypeType condType = field.getQueryCondition().getType();
				String fullFieldName = SQLDBHelper.getCriticalFieldName(table, field, pTable);
				if (condType.equals(QueryConditionTypeType.NONE)
					|| condType.equals(QueryConditionTypeType.DISPLAYONLY)) { // none displyonly
					continue;
				} else if (condType.equals(QueryConditionTypeType.EQUAL)) { // equal
					item = getEqualCriticalItem(queryRecord, table, field, fullFieldName);
				} else if (condType.equals(QueryConditionTypeType.BLURMATCHING)) { // like
					item = getLikeCriticalItem(queryRecord, table, field, fullFieldName);
				} else if (condType.equals(QueryConditionTypeType.REGION)) { // region
					item = getBetweenCriticalItem(queryRecord, table, field, fullFieldName);
				} else if (condType.equals(QueryConditionTypeType.IN)) { // in
					item = getInCriticalItem(context, queryRecord, table, field, fullFieldName);
				}
			} else {
				item = getDefaultCriticalItem(table, field, queryRecord, pTable);
			}
			if (item != null) {
				critcalItems.add(item);
			}
			if (item != null && queryRecord != null && queryRecord.get(DataConstant.ALL_RELATIONOPR) != null) {
				item.setRelationOpr((String)queryRecord.get(DataConstant.ALL_RELATIONOPR));
			}
		}
		if (queryRecord!=null && !StringUtil.isBlankOrNull(queryRecord.get(table.getId().getName()))) {
			CriticalItem item = new EqualCriticalItem("t_1." + table.getId().getName(), 
				CriticalItem.getItemValue(queryRecord, table.getId().getName(), table, table.getId()), 
				SQLDBTypeConvert.getJavaType(table.getId()));
			critcalItems.add(item);
		}
		if (cItems != null) {
			for (int j = 0; j < cItems.length; j++) {
				critcalItems.add(cItems[j]);
			}
		}
		return critcalItems;
	}
	private static String INSERTVALIDFIELD = "__InsertValidField";  
	@Override
	public Field[] getInsertValidField(ITableDBContext context, Table table) { // batch
		String k = INSERTVALIDFIELD+"_"+table.getName(); // 减少批量插入的重复运算
		Field[] res = (Field[])context.getAttribute(k);
		if (res!=null)
			return res;
		// 2008-10-09 zhouxw
		Field[] fields = table.getField();
		ArrayList<Field> result = new ArrayList<Field>();
		for (Field field:fields) {
			if (field.isVisual() == false) {
				if (field.getCreateable() == true) {
					result.add(field);
				}
			}
		}
		res = (Field[]) result.toArray(new Field[0]);
		context.setAttribute(k, res);
		return res;
	}
	@Override
	public Field[] getInsertValidField(ITableDBContext context, Table table, IDBRecord record) {
		// 2008-10-09 zhouxw
		Set<String> insertKeys = record==null?null:record.insertedKeySet();
		Field[] fields = table.getField();
		ArrayList<Field> result = new ArrayList<Field>();
		for (Field field:fields) {
			if (field.isVisual() == false) {
				if (field.getCreateable() == true) {
					if (insertKeys==null||insertKeys.size() == 0)
						result.add(field);
					else if (insertKeys.contains(field.getName()))
						result.add(field);
				}
			}
		}
		return (Field[]) result.toArray(new Field[0]);
	}
	private static String UPDATEVALIDFIELD = "__UpdateValidField";  
	@Override
	public Field[] getUpdateValidField(ITableDBContext context, Table table) { // batch
		String k = UPDATEVALIDFIELD+"_"+table.getName(); // 减少批量更新的重复运算
		Field[] res = (Field[])context.getAttribute(k);
		if (res!=null)
			return res;
		// 2008-10-09 zhouxw
		Field[] fields = table.getField();
		ArrayList<Field> result = new ArrayList<Field>();
		for (Field field:fields) {
			if (field.isVisual() == false) {
				if (field.getUpdateable() == true) {
					result.add(field);
				}
			}
		}
		res = (Field[]) result.toArray(new Field[0]);
		context.setAttribute(k, res);
		return res;
	}
	@Override
	public Field[] getUpdateValidField(ITableDBContext context, Table table, IDBRecord record) {
		Set<String> updateKeys = record==null?null:record.updatedKeySet(); // 2008-10-09 zhouxw
		Field[] fields = table.getField();
		ArrayList<Field> result = new ArrayList<Field>();
		for (Field field:fields) {
			if (field.isVisual() == false) {
				if (field.getUpdateable() == true) {
					if (updateKeys==null||updateKeys.size() == 0)
						result.add(field);
					else if (updateKeys.contains(field.getName()))
						result.add(field);
				}
			}
		}
		return (Field[]) result.toArray(new Field[0]);
	}
	protected String getDBName(AbstractField field) {
		return field.getName();
	}
	protected String getDBName(Table table) {
		return ConfigUtil.getDBName(table);
	}
	/**
	 * @param table
	 * @param validFields
	 * @return String
	 * @throws Throwable
	 */
	protected String getInsertSQL(Table table, Field[] validFields)
			throws Throwable {
		StringBuffer buf = new StringBuffer().append("insert into ").append(getDBName(table)).append(" (").append(getDBName(table.getId()));
		StringBuffer bufValue = new StringBuffer().append(" values (?");
		for (int i = 0; i < validFields.length; i++) {
			buf.append(",");
			buf.append(getDBName(validFields[i]));
			bufValue.append(",");
			bufValue.append("?");
		}
		buf.append(")");
		bufValue.append(")");
		buf.append(bufValue.toString());
		mainSQL = buf.toString();
		DebugUtil.debug(">InsertSQL=" + mainSQL);
		return mainSQL;
	}

	/**
	 * @param table
	 * @param validFields
	 * @return String
	 * @throws Throwable
	 */
	protected String getUpdateSQL(Table table, Field[] validFields)
			throws Throwable {
		StringBuffer buf = new StringBuffer().append("update ").append(ConfigUtil.getRealTableName(table)).append(" set ");
		for (int i = 0; i < validFields.length; i++) {
			if (i > 0)
				buf.append(",");
			buf.append(getDBName(validFields[i])).append("=?");
		}
		buf.append(" where ").append(getDBName(table.getId())).append("=? ");
		mainSQL = buf.toString();
		DebugUtil.debug(">UpdateSQL=" + mainSQL);
		return mainSQL;
	}
	/**
	 * @param countPS
	 * @param selectPS
	 * @param factory
	 * @return PageFactory
	 */
	protected SQLDBPageFactory getPageFactory(PreparedStatement countPS, PreparedStatement selectPS, ISQLRecordFactory factory) {
		return new GeneralDBPageFactory(countPS, selectPS, factory);
	}
	/**
	 * @param selectPS
	 * @param factory
	 * @return PageFactory
	 */
	protected SQLDBPageFactory getPageFactory(PreparedStatement selectPS, ISQLRecordFactory factory) {
		return new GeneralDBPageFactory(selectPS, factory);
	}
	/**
	 * @param selectQuery
	 * @param currPageNO
	 * @param maxPageRecordCount
	 * @return Query
	 */
	protected Query dealWithSelectQuery(Query selectQuery, int currPageNO, int maxPageRecordCount) {
		return selectQuery;
	}
	/**
	 * @param selectQuery
	 * @param currPageNO
	 * @param maxPageRecordCount
	 * @return Query
	 */
	protected Query dealWithSelectQueryByLimit(Query selectQuery, long startNum, int count) {
		throw new Warning("Not Support!");
	}
	/**
	 * @param context
	 * @param oItems
	 * @return ArrayList<OrderByItem>
	 */
	protected ArrayList<OrderByItem> getOrderByItems(ITableDBContext context, Table table, OrderByItem[] oItems) {
		ArrayList<OrderByItem> orderItems = new ArrayList<OrderByItem>();
		if (context != null) {
			String[] orderByFields, orderByMethods;
			String orderByFieldKey = NamingUtil.getOrderFieldKey(table);
			String orderByMethodKey = NamingUtil.getOrderMethodKey(table);
			orderByFields = SQLDBTypeConvert.toStringArray(context.getAttribute(orderByFieldKey));
			orderByMethods = SQLDBTypeConvert.toStringArray(context.getAttribute(orderByMethodKey));
			if (orderByFields != null) {
				for (int i = 0; i < orderByMethods.length; i++) {
					OrderByItem item = new OrderByItem(orderByFields[i], orderByMethods[i]);
					orderItems.add(item);
				}
			}
			// NOTICE 兼容处理
			String sort=(String)context.getAttribute(DataConstant.SORTNAME)
				,dir=(String)context.getAttribute(DataConstant.SORTMETHOD);
			if (!StringUtil.isEmpty(sort) && !StringUtil.isEmpty(dir)) {
				OrderByItem item = new OrderByItem(sort, dir);
				orderItems.add(item);
			}
		}
		if (oItems != null) {
			for (int j = 0; j < oItems.length; j++) {
				orderItems.add(oItems[j]);
			}
		}
		return orderItems;
	}
	private transient ExpUtil exp = null;
	@Override
	public void insertPreparedStatement(ITableDBContext context, Table table, IDBRecord record, PreparedStatement ps, Field[] fields, String newID) throws Throwable {
		ps.setString(1, newID);
		StringBuffer ss = new StringBuffer();
		ss.append("##insert(" + table.getId().getName() + "):"+newID+"\t");
		int i;
		for (i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			Object value = record.get(fieldName);
			String defValue = fields[i].getDefaultValue();
			if (StringUtil.isBlankOrNull(value) && !isBlankOrNull(defValue)) {
				if (ExpUtil.isFormula(defValue)) {
					if (exp == null)
						exp = new ExpUtil(context, table, record);
					value = "" + exp.evalExp(defValue.substring(1));
				} else {
					value = defValue;
				}
			}
			SQLDBTypeConvert.setValue(ps, (SQLDBClear)context.getDBM().getClear(), i + 2, fields[i], value);
			ss.append("##insert(" + fieldName + "):"+value+"\t");
		}
		DebugUtil.debug(ss.toString());
	}
	@Override
	public PreparedStatement getInsertPreparedStatement(ITableDBContext context, Table table, IDBRecord record, String newID) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getInsertValidField(context, table, record);
		mainSQL = getInsertSQL(table, fields);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		if (record!=null) {
			this.insertPreparedStatement(context, table, record, ps, fields, newID);
		}
		DebugUtil.debug("------insert().end------");
		return ps;
	}
	@Override
	public PreparedStatement getInsertPreparedStatement(ITableDBContext context, Table table) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getInsertValidField(context, table);
		mainSQL = getInsertSQL(table, fields);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		DebugUtil.debug("------insert_().end------");
		return ps;
	}
	@Override
	public void updatePreparedStatementValue(ITableDBContext context, Table table, IDBRecord record, PreparedStatement ps, Field[] fields) throws Throwable {
		int i;
		Set<String> deletedKeySet = record.deletedKeySet();
		StringBuffer ss = new StringBuffer();
		for (i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			Object value = record.get(fieldName);
			if (deletedKeySet.contains(fieldName)) {
				SQLDBTypeConvert.setValue(ps, (SQLDBClear)context.getDBM().getClear(), i + 1, fields[i], null);
				ss.append("##update(" + fieldName + "):"+null+"\t");
			} else {
				String defValue = fields[i].getDefaultValue();
				if (StringUtil.isBlankOrNull(value) && !StringUtil.isBlankOrNull(defValue)) {
					if (ExpUtil.isFormula(defValue)) {
						if (exp == null)
							exp = new ExpUtil(context, table, record);
						value = "" + exp.evalExp(defValue.substring(1));
					} else {
						value = defValue;
					}
				}
				SQLDBTypeConvert.setValue(ps, (SQLDBClear)context.getDBM().getClear(), i + 1, fields[i], value);
				ss.append("##update(" + fieldName + "):"+value+"\t");
			}
		}
		ps.setString(i + 1, (String)record.get(table.getId().getName()));
		ss.append("##update(" + table.getId().getName() + "):"+record.get(table.getId().getName())+"\t");
		DebugUtil.debug(ss.toString());
	}
	@Override
	public PreparedStatement getUpdatePreparedStatement(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getUpdateValidField(context, table, record);
		mainSQL = getUpdateSQL(table, fields);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		if (record!=null) {
			this.updatePreparedStatementValue(context, table, record, ps, fields);
		}
		DebugUtil.debug("------update().end------");
		return ps;
	}
	@Override
	public PreparedStatement getUpdatePreparedStatement(ITableDBContext context, Table table) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getUpdateValidField(context, table);
		mainSQL = getUpdateSQL(table, fields);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		DebugUtil.debug("------update_().end------");
		return ps;
	}
	@Override
	public PreparedStatement getDeletePreparedStatement(ITableDBContext context, Table table, String[] ids) throws Throwable {
		mainSQL = "delete from " + ConfigUtil.getRealTableName(table) + " where " + getDBName(table.getId()) + " in (";
		for (int i=0;i<ids.length;i++) {
			if (i>0)
				mainSQL += ",";
			mainSQL += "?";
		}
		mainSQL += ")";
		DebugUtil.debug(">deleteSQL:" + mainSQL);
		StringBuffer ss = new StringBuffer();
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		for (int i=0;i<ids.length;i++) {
			SQLDBTypeConvert.setValue(ps, context.getDBM().getClear(), i+1, table.getId(), ids[i]);
			ss.append("##delete(" + table.getId().getName() + "):"+ids[i]+"\t");
		}
		DebugUtil.debug(ss.toString());
		DebugUtil.debug("------delete().end------");
		return ps;
	}
	// ========================================================================================================================= //
	/**
	 * @param context
	 * @param table
	 * @param query
	 * @param filter
	 */
	private void dealExtFilter(ITableDBContext context, Table table, Query query, IDBFilter filter) {
		if (filter!=null)
			query.addFilter(filter);
		if (context!=null) {
			Object o = context.getAttribute("__extendFilter." + table.getName());
			if (o!=null) {
				SQLDBFilter extFilter = null;
				if (o instanceof SQLDBFilter) 
					extFilter = (SQLDBFilter)o;
				else
					extFilter = new SQLDBFilter(o.toString());
				query.addFilter(extFilter);
			}
		}
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBFilter filter)
			throws Throwable {
		DebugUtil.debug("##countByFilter:" + filter);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// count
		String sFilterC = calFilter(context, table, pTableAlias, filter, false);
		Query countQry = new Query("select count("+table.getId().getName()+") from "+pTable.getFormSQL(), sFilterC); // count不需要order
		dealExtFilter(context, table, countQry, filter);
		// deal
		mainSQL = countQry.getSQL(); // 4 log
		ResultSet rs = null;
		try {
			rs = countQry.build(getConnection(context)).executeQuery();
			if (rs != null) {
				if (rs.next())
					return rs.getInt(1);
			}
		} finally {
			CloseUtil.close(rs);
		}
		return -1;
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBRecord queryRecord)
			throws Throwable {
		DebugUtil.debug("##countByRecord:" + queryRecord);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
//		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, null);
		// count
		String sFilterC = calFilter(context, table, pTableAlias, null, false);
		Query countQry = new Query("select count("+table.getId().getName()+") from " + pTable.getFormSQL(), sFilterC, criticalItems, null); // count不需要order
		dealExtFilter(context, table, countQry, null);
		mainSQL = countQry.getSQL(); // 4 log
		// deal
		ResultSet rs = null;
		try {
			rs = countQry.build(getConnection(context)).executeQuery();
			if (rs != null) {
				if (rs.next())
					return rs.getLong(1);
			}
		} finally {
			CloseUtil.close(rs);
		}
		return -1;
	}
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, 
			long startRowNum, int count) throws Throwable {
		DebugUtil.debug("##selectByFilterLimit:" + filter);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
//		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, table, null);
		// count
		String sFilterC = calFilter(context, table, pTableAlias, filter, false);
		Query countQry = new Query("select count("+table.getId().getName()+") from " + pTable.getFormSQL(), sFilterC); // count不需要order
		dealExtFilter(context, table, countQry, filter);
		// main
		String sFilter = calFilter(context, table, pTableAlias, filter, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, orderByItems);
		dealExtFilter(context, table, selectQry, filter);
		// deal
		selectQry = dealWithSelectQueryByLimit(selectQry, startRowNum, count);
		mainSQL = selectQry.getSQL(); // 4 log
		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
		SQLDBPageFactory pf = getPageFactory(
				countQry.build(getConnection(context)), //
				selectQry.buildWithScrollCursor(getConnection(context)), //
				factory);
		DBPage pg = pf.getPage(count, 1);
		return pg;
	} 
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord queryRecord, ISQLRecordFactory factory, 
			long startRowNum, int count) throws Throwable {
		DebugUtil.debug("##selectByRecordLimit:" + queryRecord);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, table, null);
		// count
		String sFilterC = calFilter(context, table, pTableAlias, null, false);
		Query countQry = new Query("select count("+table.getId().getName()+") from " + pTable.getFormSQL(), sFilterC, criticalItems, null); // count不需要order
		dealExtFilter(context, table, countQry, null);
		// main
		String sFilter = calFilter(context, table, pTableAlias, null, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, criticalItems, orderByItems);
		dealExtFilter(context, table, selectQry, null);
		// deal
		selectQry = dealWithSelectQueryByLimit(selectQry, startRowNum, count);
		mainSQL = selectQry.getSQL(); // 4 log
		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
		SQLDBPageFactory pf = getPageFactory(
				countQry.build(getConnection(context)), //
				selectQry.buildWithScrollCursor(getConnection(context)), //
				factory);
		DBPage pg = pf.getPage(count, 1);
		return pg;
	} 
    @Override
	public IDBResultSet selectBy(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, 
			final int maxPageRecordCount, final int currPageNO) throws Throwable {
		DebugUtil.debug("##selectByFilter:" + filter);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
//		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, table, null);
//		    // count
////		String sCoundSQL; // NOTICE 可能是联立SQL无法取count(id)
////		Id id = table.getId();
////		if (id!=null) {
////			sCoundSQL = "select count("+table.getId().getName()+") from " + pTable.getFormSQL();
////		} else {
////			sCoundSQL = "select count("+table.getId().getName()+") from " + pTable.getFormSQL();
////		}
		String sFilterC = calFilter(context, table, pTableAlias, filter, false);
		Query countQry = new Query("select count("+table.getId().getName()+") from " + pTable.getFormSQL(), sFilterC); // count不需要order
		dealExtFilter(context, table, countQry, filter);
		// main
		String sFilter = calFilter(context, table, pTableAlias, filter, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, orderByItems);
		dealExtFilter(context, table, selectQry, filter);
		// deal
		selectQry = dealWithSelectQuery(selectQry, currPageNO, maxPageRecordCount);
		mainSQL = selectQry.getSQL(); // 4 log
		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
		SQLDBPageFactory pf = getPageFactory( //
				countQry.build(getConnection(context)), //
				selectQry.buildWithScrollCursor(getConnection(context)), //
				factory);
		DBPage pg = pf.getPage(maxPageRecordCount, currPageNO);
		// 2008-10-09 zhouxw
		// pg.setVisCompPage(getSPMVisCompMap(context, table));
		return pg;
	}
	@Override
	public IDBResultSet selectBy(final ITableDBContext context, Table table, IDBRecord queryRecord, ISQLRecordFactory factory, 
			final int maxPageRecordCount, final int currPageNO) throws Throwable {
		DebugUtil.debug("##selectByRecord:" + queryRecord);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, table, null);
		// count
		String sFilterC = calFilter(context, table, pTableAlias, null, false);
		Query countQry = new Query("select count("+table.getId().getName()+") from " + pTable.getFormSQL(), sFilterC, criticalItems, null); // count不需要order
		dealExtFilter(context, table, countQry, null);
		// main
		String sFilter = calFilter(context, table, pTableAlias, null, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, criticalItems, orderByItems);
		dealExtFilter(context, table, selectQry, null);
		// deal
		selectQry = dealWithSelectQuery(selectQry, currPageNO, maxPageRecordCount);
		mainSQL = selectQry.getSQL(); // 4 log
		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
		SQLDBPageFactory pf = getPageFactory( // GeneralDBPageFactory.getPage
				countQry.build(getConnection(context)), 
				selectQry.buildWithScrollCursor(getConnection(context)), 
				factory);
		DBPage pg = pf.getPage(maxPageRecordCount, currPageNO);
		// 2008-10-09 zhouxw
		// pg.setVisCompPage(getSPMVisCompMap(context, table));
		return pg;
	}
    @Override
	public void loopBy(ITableDBContext context, Table table, IDBFilter filter,
			ISQLRecordFactory factory, IDBRecordCallBack callback) throws Throwable {
		DebugUtil.debug("##loopByFilter:" + filter);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
//		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, table, null);
		// main
		String sFilter = calFilter(context, table, pTableAlias, filter, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, orderByItems);
		dealExtFilter(context, table, selectQry, filter);
		// deal
		long recordCount = this.countBy(context, table, filter);
		int currPageNO = 1;
		int maxPageRecordCount = DBPage.MAXCOUNTPERQUERY;
		int totalPage = VarUtil.toInt(recordCount / maxPageRecordCount + (recordCount % maxPageRecordCount > 0 ? 1 : 0));
		for (; currPageNO <= totalPage; currPageNO++) { // 分批处理数据
			selectQry.clearListener();
			selectQry = dealWithSelectQuery(selectQry, currPageNO, maxPageRecordCount); // NOTICE for wrapSQL
			mainSQL = selectQry.getSQL(); // 4 log
			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
			SQLDBPageFactory pf = getPageFactory(
					selectQry.buildWithScrollCursor(getConnection(context)), 
					factory);
			pf.dealPage(maxPageRecordCount, currPageNO, callback);
		}
	}
	@Override
	public void loopBy(ITableDBContext context, Table table, IDBRecord queryRecord,
			ISQLRecordFactory factory, final IDBRecordCallBack callback) throws Throwable {
		DebugUtil.debug("##loopByRecord:" + queryRecord);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// items
		ArrayList<CriticalItem> criticalItems = getCriticalItems(context, table, pTable, queryRecord, null);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(context, table, null);
		// main
		String sFilter = calFilter(context, table, pTableAlias, null, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, criticalItems, orderByItems);
		dealExtFilter(context, table, selectQry, null);
		// deal
		long recordCount = this.countBy(context, table, queryRecord);
		int currPageNO = 1;
		int maxPageRecordCount = DBPage.MAXCOUNTPERQUERY;
		int totalPage = VarUtil.toInt(recordCount / maxPageRecordCount + (recordCount % maxPageRecordCount > 0 ? 1 : 0));
		for (; currPageNO <= totalPage; currPageNO++) {
			selectQry.clearListener();
			selectQry = dealWithSelectQuery(selectQry, currPageNO, maxPageRecordCount); // NOTICE for wrapSQL
			mainSQL = selectQry.getSQL(); // 4 log
			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
			SQLDBPageFactory pf = getPageFactory(
					selectQry.buildWithScrollCursor(getConnection(context)), 
					factory);
			pf.dealPage(maxPageRecordCount, currPageNO, callback);
		}
	}
	@Override
	public PreparedStatement getSelectPreparedStatement(ITableDBContext context, Table table, String id) throws Throwable {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// main
		mainSQL = pTable.getSQL();
		mainSQL += " where " + pTableAlias + "." + table.getId().getName() + "=? ";
		DebugUtil.debug(">selectByPK(Table=" + table.getName() + ",ID=" + id + "):" + mainSQL);
		// deal
		PreparedStatement ps = null;
		ps = getConnection(context).prepareStatement(mainSQL);
		//ps.setInt(1,id);
		//ps.setObject(1, id);
		ps.setString(1, id);
		return ps;
	}
	/**
	 * @return Connection
	 * @throws Throwable 
	 */
	private Connection getConnection(ITableDBContext context) throws Throwable {
		return ((SQLTableDBManager)context.getDBM()).getConnection();
	}
	/**
	 * @return Connection
	 * @throws Throwable 
	 */
	private Connection getConnection(ITableDBContext context, boolean openTrans) throws Throwable {
		return ((SQLTableDBManager)context.getDBM()).getConnection(openTrans);
	}
//  @Deprecated 
//	public PreparedStatement findAllFromTopLevelPreStat(ITableContext context, Table table) throws Throwable {
//		// try {
//		PrimaryTable pTable = getBaseSelectSQL(context, table);
//		String pTableAlias = pTable.getFirstTableAlias();
//		//
//		String fixedQueryFilter = "";
//		// NOTICE 2005-11-25 zhouxw where pid=...
//		String treefiltersql = getQueryTreeExtendFilter(context, table, pTableAlias);
//		if (!StringUtil.isBlankOrNull(treefiltersql))
//			fixedQueryFilter += " and " + treefiltersql;
//		fixedQueryFilter += calFilter(context, table, pTableAlias, null, true);
//		// 
//		mainSQL = pTable.getSQL();
//		mainSQL += fixedQueryFilter.length() > 0 ? " where 1=1 " + fixedQueryFilter : "";
//		// 
//		DebugUtil.debug(">findAllFromTopLevel(" + table.getName() + "):" + mainSQL);
//		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
//		return getConnection(context).prepareStatement(mainSQL);
//	}
//  @Deprecated 
//	public PreparedStatement findChildrenByPIDPreStat(ITableContext context, Table table, String pID) throws Throwable {
//		// try {
//		Field parentField = ConfigUtil.searchChildTableRefField(table, table);
//		PrimaryTable pTable = getBaseSelectSQL(context, table);
//		String pTableAlias = pTable.getFirstTableAlias();
//		//
//		String fixedQueryFilter = "";
//		// BUG 2005-11-25 zhouxw
//		String treefiltersql = getQueryTreeExtendFilter(context, table, pTableAlias);
//		if (!StringUtil.isBlankOrNull(treefiltersql))
//			fixedQueryFilter += " and " + treefiltersql;
//		// extend ref filter
//		if (parentField != null) {
//			if (pID != null)
//				fixedQueryFilter += " and " + pTableAlias + "." + parentField.getName() + "='" + pID + "'";
//			else if (context.getAttribute(table.getId().getName()) == null)
//				fixedQueryFilter += " and " + pTableAlias + "." + parentField.getName() + " is null ";
//			else
//				fixedQueryFilter += " and " + pTableAlias + "."
//					+ parentField.getName() + "='" + context.getAttribute(table.getId().getName()) + "'";
//		}
//		fixedQueryFilter += calFilter(context, table, pTableAlias, null, true);
//		//
//		mainSQL = pTable.getSQL();
//		mainSQL += fixedQueryFilter.length() > 0 ? " where 1=1 " + fixedQueryFilter : "";
//		//
//		DebugUtil.debug(">findChildrenByPID(" + table.getName() + "):" + mainSQL);
//		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
//		return getConnection(context).prepareStatement(mainSQL);
//	}
//  @Deprecated 
//	public PreparedStatement findXLoadByPIDPreStat(ITableContext context, Table table, String pID,
//			String showFields) throws Throwable {
//		// try {
//		Field parentField = ConfigUtil.searchChildTableRefField(table, table);
//		PrimaryTable pTable = getBaseSelectSQL(context, table);
//		String pTableAlias = pTable.getFirstTableAlias();
//		//
//		String fixedQueryFilter = "";
//		// BUG 2005-11-25 zhouxw
//		String treefiltersql = getQueryTreeExtendFilter(context, table, pTableAlias);
//		if (!StringUtil.isBlankOrNull(treefiltersql))
//			fixedQueryFilter += " and " + treefiltersql;
//		// extend ref filter
//		if (parentField != null) {
//			if (StringUtil.isBlankOrNull(pID))
//				fixedQueryFilter += " and " + pTableAlias + "." + parentField.getName() + " is null ";
//			else if (context.getAttribute(table.getId().getName()) == null)
//				fixedQueryFilter += " and " + pTableAlias + "." + parentField.getName() + " is null ";
//			else
//				fixedQueryFilter += " and " + pTableAlias + "." + parentField.getName() + "=" + pID;
//		}
//		fixedQueryFilter += calFilter(context, table, pTableAlias, null, true);
//		//
//		mainSQL = pTable.getSQL();
//		mainSQL += fixedQueryFilter.length() > 0 ? " where 1=1 " + fixedQueryFilter : "";
//		//
//		DebugUtil.debug(">findXLoadByPID(" + table.getName() + "):" + mainSQL);
//		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
//		return getConnection(context).prepareStatement(mainSQL);
//	}
//  @Deprecated 
//	public PreparedStatement findXLoadPIDsPreStat(ITableContext context, Table table,
//			Field parentField) throws Throwable {
//		// try {
//		PrimaryTable pTable = getBaseSelectSQL(context, table);
//		String pTableAlias = pTable.getFirstTableAlias();
//		//
//		String fixedQueryFilter = "";
//		// BUG 2005-11-25 zhouxw
//		String treefiltersql = getQueryTreeExtendFilter(context, table, pTableAlias);
//		if (!StringUtil.isBlankOrNull(treefiltersql))
//			fixedQueryFilter += " and " + treefiltersql;
//		fixedQueryFilter += calFilter(context, table, pTableAlias, null, true);
//		//
//		mainSQL = "select distinct(" + table.getId().getName() + ") from "
//				+ ConfigUtil.getRealTableName(table) + " " + pTableAlias
//				+ " where 1<>1 ";
//		if (parentField != null)
//			mainSQL = "select distinct(" + parentField.getName() + ") from "
//					+ ConfigUtil.getRealTableName(table) + " " + pTableAlias
//					+ " where 1=1 ";
//		mainSQL += fixedQueryFilter;
//		//
//		DebugUtil.debug(">findXloadPIDs(" + table.getName() + "):" + mainSQL);
//		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
//		return getConnection(context).prepareStatement(mainSQL);
//	}
//  @Deprecated 
//	private final String getQueryTreeExtendFilter(ITableContext context, Table table, String tableAlias) throws Throwable {
//		if (context == null)
//			return "";
//		String result = "";
//		// headTable
//		Table headTable = ConfigUtil.getParentTable(table);
//		if (headTable != null) {
//			DebugUtil.debug(">headTableName:" + headTable.getName() + ",currTableName:" + table.getName());
//			Field headField = ConfigUtil.searchChildTableRefField(headTable, table);
//			if (headField == null) {
//				throw new Warning("HeadField is Null!");
//			}
//			// NOTICE 2005-11-25 zhouxw
//			String headID = (String)context.getAttribute(headField.getName());
//			DebugUtil.debug(">headTableName:" + headField.getName() + ",headRefField=" + headID);
//			if (StringUtil.isStrBlankOrNull(headID))
//				result = tableAlias + "." + headField.getName() + " is null ";
//			else {
////				if (!StringUtil.isNumeric(headID)) // NOTICE 防止SQL注入
////					throw new Warning("HeadField must be numeric!");
//				headID = StringUtil.unSqlInjection(headID);
//				result = tableAlias + "." + headField.getName() + "='" + headID + "'";
//			}
//		}
//		return result;
//	}
	/**
	 * @param table
	 * @param pTableAlias
	 * @param filter
	 * @param context
	 * @param hasOrderBy
	 * @return String
	 * @throws Throwable
	 */
	static final String calFilter(ITableDBContext context, Table table, String pTableAlias,
			IDBFilter filter, boolean hasOrderBy) throws Throwable {
		String fixedQueryFilter = "";
		if (filter != null) {
			fixedQueryFilter = ((SQLDBFilter)filter).getSql();
		}
		// 插件过滤器
		String queryFilter = getPluginQueryFilter(context, table, pTableAlias, hasOrderBy);
		if (!StringUtil.isBlankOrNull(queryFilter)) {
			fixedQueryFilter += queryFilter;
		}
//		if (!StringUtil.isBlankOrNull(fixedQueryFilter)) {
//			if ((fixedQueryFilter.indexOf("@")>=0 && fixedQueryFilter.indexOf("@")!=fixedQueryFilter.lastIndexOf("@"))
//				||fixedQueryFilter.indexOf("${")>=0) {
//				com.haiyan.genmis.util.exp.FormulaUtil formula = new com.haiyan.genmis.util.exp.FormulaUtil(context);
//				fixedQueryFilter = formula.getString(fixedQueryFilter);
//			}
//		}
		return fixedQueryFilter;
	}
	/**
	 * @param table
	 * @param tableAlias
	 * @param context
	 * @return String
	 * @throws Throwable
	 */
	private final static String getPluginQueryFilter(ITableDBContext context, Table table, String tableAlias, boolean hasOrderBy)
			throws Throwable {
		String result = "";
		// extend filter
		if (context != null) {
			SQLDBFilter defaultFilter = (SQLDBFilter)context.getAttribute(NamingUtil.getDefaultFilterAlias(table));
			if (defaultFilter != null) {
				int s = result.lastIndexOf("order by");
				if (s >= 0) { // 补充过滤
					result = result.substring(0, s) + defaultFilter.getSql() + result.substring(s);
				} else
					result += defaultFilter.getSql();
			}
		}
		// getPluginQueryFilter
		PluggedFilter[] filters = SQLDBFilterFactory.getQueryFilter(context, table, tableAlias);
		if (filters != null) {
			for (int i = 0; i < filters.length; i++) {
				PluggedFilter pf = (PluggedFilter) filters[i];
				if (pf == null) 
					continue;
				String filterStr = "";
				String className = isEmpty(pf.getClassName()) ? PropUtil.getProperty("DEFAULT_FILTER") : pf.getClassName();
				String methodName = pf.getMethodName();
				String parameter = pf.getParameter();
				String content = pf.getContent();
				if (isExpMethod(methodName)) {
					IExpUtil expUtil = context.getExpUtil();
					if (expUtil==null) {
						throw new Warning("context.exp is null");
					}
					boolean needContent = false;
					if (isEmpty(parameter)) {
						throw new Warning("PluggedFilter.parameter is null");
					} else {
						Object v = expUtil.evalExp(parameter);
						if (v instanceof Boolean && v==Boolean.TRUE) {
							needContent = true;
						}
					}
					if (needContent) {
						String sFilter;
						if (ExpUtil.isFormula(content))
							sFilter = VarUtil.toString(expUtil.evalExp(content));
						else
							sFilter = content;
						filterStr += sFilter;
					}
				} else {
					if (!isEmpty(methodName) && !isEmpty(className)) {
						// NOTICE 后面有pf.getContent()的处理
						DebugUtil.debug(">doExecuteFilter:" + className + " " + methodName);
						filterStr += InvokeUtil.getString(className, methodName, 
							new Class[] { IContext.class, Table.class, String.class, String.class },
							new Object[] { context, table, tableAlias, parameter });
					} else if (!isEmpty(content)) { // 直接的sql语句
						filterStr += content;
					}
				}
				if (!isEmpty(filterStr)) {
					int s = result.lastIndexOf("order by");
					if (s >= 0) { // 补充过滤
						result = result.substring(0, s) + filterStr + result.substring(s);
					} else
						result += filterStr;
				}
			}
		}
		if (!hasOrderBy) {
			int s = result.lastIndexOf("order by"); // 去除orderby再加上过滤
			// TODO 万一order by后面还有括号 配置时小心
			if (s >= 0)
				result = result.substring(0, s);
		}
		return result.length() == 0 ? "" : result;
	}
	private static boolean isExpMethod(String methodName) {
		return "exp".equalsIgnoreCase(methodName);
	}
}