/*
 * Created on 2004-9-26
 */
package haiyan.orm.database.sql;

import static haiyan.common.StringUtil.isBlankOrNull;
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
				// TODO Auto-generated method stub
				//System.err.println("dealWithLazyLoadField");
			}
			@Override
			public void dealWithMappingField(Table table, Field field,
					Object[] globalVars) throws Throwable {
				// TODO Auto-generated method stub
				//System.err.println("dealWithMappingField");
			}

		};
		StringBuffer cols = new StringBuffer();
		PrimaryTable pTable = new PrimaryTable(ConfigUtil.getRealTableName(table), "");
		template.deal(table, new Object[] { cols, pTable });
		pTable.setSelectColumnSQL("select " + cols.toString() + " from ");
		QuerySQL qs = table.getQuerySQL();
		if (qs!=null) {
			if ("exp".equalsIgnoreCase(qs.getMethodName())) {
				String p = qs.getParameter();
				if (StringUtil.isEmpty(p))
					p = qs.getContent();
				IExpUtil exp = context.getExpUtil();
				if (exp==null) {
					throw new Warning("");
				}
				pTable.setPrimaryTableSQL(""+context.getExpUtil().evalExp(p));
			} else {
				pTable.setPrimaryTableSQL(qs.getContent());
			}
		}
		return pTable;
	}

	/**
	 * @param table
	 * @param field
	 * @param queryForm
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getDefaultCriticalItem(Table table, Field field,
			IDBRecord queryForm, PrimaryTable pTable) throws Throwable {
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
//				return getLikeCriticalItem(queryForm, table, field, fullFieldName);
//			else 
			if (field.getReferenceTable() != null)
				return getRefLikeCriticalItem(queryForm, table, field, fullFieldName);
			else
				return getLikeCriticalItem(queryForm, table, field, fullFieldName);
		} 
		else if (field.getQueryCondition()!=null && field.getQueryCondition().getType()==QueryConditionTypeType.REGION) {
			return getBetweenCriticalItem(queryForm, table, field, fullFieldName);
		}
		return getEqualCriticalItem(queryForm, table, field, fullFieldName);
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getIsNullCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		CriticalItem item = new IsNullCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getIsNotNullCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		CriticalItem item = new IsNotNullCriticalItem(fullFieldName,
				CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getLikeCriticalItem(IDBRecord queryForm, Table table,
			Field field, String fullFieldName) throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		CriticalItem item = new LikeCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getRefLikeCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (!StringUtil.isBlankOrNull(queryForm.get(field.getName()))) {
			return new LikeCriticalItem(fullFieldName, 
				CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		}
		String disfld = null;
		if (field.getReferenceTable() != null) {
			disfld = NamingUtil.getDisplayFieldAlias(field);
		} else
			// disfld= NamingUtil.getDisplayFieldAlias(field.getName(), field.getCommon().getDisplay , ReferenceField());
			return null; // TODO 需要确定display是否必填
		// CriticalItem item = null;
		if (!StringUtil.isBlankOrNull(queryForm.get(disfld))) {
			return new RefLikeCriticalItem(fullFieldName, queryForm.get(disfld), String.class, table, field);
			// TODO SQLDBTypeConvert.getFieldJavaType(field)应该获取displayField的字段类型
		}
		return null;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getNotLikeCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		CriticalItem item = new NotLikeCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getUpperThanCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		CriticalItem item = new UpperThanCriticalItem(fullFieldName,
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getLowerThanCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		CriticalItem item = new LowerThanCriticalItem(fullFieldName,
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getEqualCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		CriticalItem item = new EqualCriticalItem(fullFieldName, 
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getNotEqualCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		CriticalItem item = new NotEqualCriticalItem(fullFieldName,
			CriticalItem.getItemValue(queryForm, field.getName(), table, field), SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getBetweenCriticalItem(IDBRecord queryForm,
			Table table, Field field, String fullFieldName) throws Throwable {
		Object lowerValue = CriticalItem.getItemValue(queryForm, NamingUtil.getRegionFieldName(field.getName(), NamingUtil.REGION_START),
				table, field);
		Object upperValue = CriticalItem.getItemValue(queryForm, NamingUtil.getRegionFieldName(field.getName(), NamingUtil.REGION_END),
				table, field);
		// if (lowerValue == null && upperValue == null)
		// return null;
		CriticalItem item = new BetweenCriticalItem(fullFieldName, lowerValue,
				upperValue, SQLDBTypeConvert.getJavaType(field));
		return item;
	}

	/**
	 * @param queryForm
	 * @param table
	 * @param field
	 * @param fullFieldName
	 * @return CriticalItem
	 * @throws Throwable
	 */
	protected CriticalItem getInCriticalItem(IDBRecord queryForm, Table table,
			Field field, String fullFieldName, ITableDBContext context)
			throws Throwable {
		if (StringUtil.isBlankOrNull(queryForm.get(field.getName())))
			return null;
		String[] args = (String[])queryForm.get(field.getName());
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
	 * @param table
	 * @param queryRecord
	 * @param pTable
	 * @param cItems
	 * @return ArrayList<CriticalItem>
	 * @throws Throwable
	 */
	protected ArrayList<CriticalItem> getCriticalItems(Table table,
			IDBRecord queryRecord, PrimaryTable pTable, CriticalItem[] cItems,
			ITableDBContext context) throws Throwable {
		ArrayList<CriticalItem> critcalItems = new ArrayList<CriticalItem>();
		if (queryRecord != null) {
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
						item = getInCriticalItem(queryRecord, table, field, fullFieldName, context);
					}
				} else {
					item = getDefaultCriticalItem(table, field, queryRecord, pTable);
				}
				if (item != null) {
					critcalItems.add(item);
				}
				if (item != null && queryRecord != null && queryRecord.get(DataConstant.ALL_RELATIONOPR) != null) {
					// TODO can't do in this way
					// if (critcalItems.size() > 1)
					item.setRelationOpr((String)queryRecord.get(DataConstant.ALL_RELATIONOPR));
				}
			}
			if (!StringUtil.isBlankOrNull(queryRecord.get(table.getId().getName()))) {
				CriticalItem item = new EqualCriticalItem("t_1." + table.getId().getName(), 
					CriticalItem.getItemValue(queryRecord, table.getId().getName(), table, table.getId()), 
					SQLDBTypeConvert.getJavaType(table.getId()));
				critcalItems.add(item);
			}
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
		Set<String> insertKeys = record==null?null:record.inertKeySet();
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
		Set<String> updateKeys = record==null?null:record.updateKeySet(); // 2008-10-09 zhouxw
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
		return new GeneralDBPageFactory( selectPS, factory);
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
		//return selectQuery;
		throw new Warning("Not Support!");
	}
	/**
	 * @param record
	 * @param oItems
	 * @return ArrayList<OrderByItem>
	 */
	protected ArrayList<OrderByItem> getOrderByItems(IDBRecord record, OrderByItem[] oItems) {
		ArrayList<OrderByItem> orderItems = new ArrayList<OrderByItem>();
		if (record != null) {
			String[] orderByFields, orderByMethods;
			orderByFields = SQLDBTypeConvert.toStringArray(record.get(NamingUtil.ORDER_BY_FIELD_NAME));
			orderByMethods = SQLDBTypeConvert.toStringArray(record.get(NamingUtil.ORDER_BY_METHOD_NAME));
			if (orderByFields != null) {
				for (int i = 0; i < orderByMethods.length; i++) {
					OrderByItem item = new OrderByItem(orderByFields[i], orderByMethods[i]);
					orderItems.add(item);
				}
			}
			String sort=(String)record.get(DataConstant.SORTNAME)
				,dir=(String)record.get(DataConstant.SORTMETHOD);
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
		// return (OrderByItem[]) orderItems.toArray(new OrderByItem[0]);
	}
	private transient ExpUtil exp = null;
	@Override
	public void insertPreparedStatement(ITableDBContext context, Table table, IDBRecord record, PreparedStatement ps, Field[] fields, String newID) throws Throwable {
		ps.setString(1, newID);
		StringBuffer ss = new StringBuffer();
		ss.append("##insert(" + table.getId().getName() + "):" + newID+"\t");
		int i;
		for (i = 0; i < fields.length; i++) {
			Object value = record.get(fields[i].getName());
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
			ss.append("##insert(" + fields[i].getName() + "):" + value+"\t");
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
	public void updatePreparedStatementValue(ITableDBContext context, Table table, IDBRecord record, PreparedStatement ps, Field[] fields) throws Throwable {
		int i;
		StringBuffer ss = new StringBuffer();
		for (i = 0; i < fields.length; i++) {
			Object value = record.get(fields[i].getName());
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
			ss.append("##update(" + fields[i].getName() + "):" + value+"\t");
		}
		ps.setString(i + 1, (String)record.get(table.getId().getName()));
		ss.append("##update(" + table.getId().getName() + "):" + record.get(table.getId().getName())+"\t");
		DebugUtil.debug(ss.toString());
	}
	@Override
	public PreparedStatement getUpdatePreparedStatement(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getUpdateValidField(context, table, record);
		this.mainSQL = getUpdateSQL(table, fields);
		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		if (record!=null) {
			this.updatePreparedStatementValue(context, table, record, ps, fields);
		}
		DebugUtil.debug("------update().end------");
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
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		for (int i=0;i<ids.length;i++) {
			SQLDBTypeConvert.setValue(ps, context.getDBM().getClear(), i + 1, table.getId(), ids[i]);
		}
		DebugUtil.debug("------delete().end------");
		return ps;
	}
	@Override
	public PreparedStatement getInsertPreparedStatement(ITableDBContext context, Table table) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getInsertValidField(context, table);
		mainSQL = getInsertSQL(table, fields);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		DebugUtil.debug("------insert().end------");
		return ps;
	}
	@Override
	public PreparedStatement getUpdatePreparedStatement(ITableDBContext context, Table table) throws Throwable {
		// 2007-01-09 zhouxw
		Field[] fields = getUpdateValidField(context, table);
		this.mainSQL = getUpdateSQL(table, fields);
		PreparedStatement ps = getConnection(context, true).prepareStatement(mainSQL);
		DebugUtil.debug("------update().end------");
		return ps;
	}
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
				//SQLDBFilterFactory.createBFilter();
				//new Filter(o.toString(), null); // @deprecated
				//if (extFilter!=null) {
				query.addFilter(extFilter);
				//}
			}
		}
	}
	@Override
	public long countBy(ITableDBContext context, Table table, IDBFilter filter)
			throws Throwable {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		String sFilterC = calFilter(context, table, pTableAlias, filter, false);
		Query countQry = new Query("select count(*) from " + pTable.getFormSQL(), sFilterC);
		dealExtFilter(context, table, countQry, filter);
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
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, 
			long startRowNum, int count) throws Throwable {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		//String sFilterC = calFilter(context, table, pTableAlias, filter, false);
		//Query countQry = new Query("select count(*) from " + pTable.getFormSQL(), sFilterC);
		//dealExtFilter(context, table, countQry, filter);
		String sFilter = calFilter(context, table, pTableAlias, filter, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter);
		dealExtFilter(context, table, selectQry, filter);
		
		selectQry = dealWithSelectQueryByLimit(selectQry, startRowNum, count);
		mainSQL = selectQry.getSQL(); // 4 log
		// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
		SQLDBPageFactory pf = getPageFactory(
				selectQry.buildWithScrollCursor(getConnection(context)), //
				factory);
		DBPage pg = pf.getPage(count, 1);
		return pg;
	} 
    @Override
	public IDBResultSet selectBy(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, 
			final int maxPageRecordCount, final int currPageNO) throws Throwable {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// 
		String sFilterC = calFilter(context, table, pTableAlias, filter, false);
//		String sCoundSQL; // NOTICE 可能是联立SQL无法取count(id)
////		Id id = table.getId();
////		if (id!=null) {
////			sCoundSQL = "select count(*) from " + pTable.getFormSQL();
////		} else {
//			sCoundSQL = "select count(*) from " + pTable.getFormSQL();
////		}
		Query countQry = new Query("select count(*) from " + pTable.getFormSQL(), sFilterC);
		dealExtFilter(context, table, countQry, filter);
		
		String sFilter = calFilter(context, table, pTableAlias, filter, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter);
		dealExtFilter(context, table, selectQry, filter);
		
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
	public long countBy(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		DebugUtil.debug("##countByRecord:" + record);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		//
		ArrayList<CriticalItem> criticalItems = getCriticalItems(table, record, pTable, null, context);
		// ArrayList<OrderByItem> orderByItems = getOrderByItems(queryForm, null);
		//
		String sFilterC = calFilter(context, table, pTableAlias, null, false);
		Query countQry = new Query("select count(*) from " + pTable.getFormSQL(), sFilterC,
				criticalItems, null);
		dealExtFilter(context, table, countQry, null);
		mainSQL = countQry.getSQL(); // 4 log
		//
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
	public IDBResultSet selectBy(final ITableDBContext context, Table table, IDBRecord queryForm,
			ISQLRecordFactory factory, final int maxPageRecordCount,
			final int currPageNO) throws Throwable {
		DebugUtil.debug("##selectBy:" + queryForm);
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// Items
		ArrayList<CriticalItem> criticalItems = getCriticalItems(table, queryForm, pTable, null, context);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(queryForm, null);
		//
		String sFilterC = calFilter(context, table, pTableAlias, null, false);
		Query countQry = new Query("select count(*) from " + pTable.getFormSQL(), sFilterC,
				criticalItems, null); // count不需要order
		dealExtFilter(context, table, countQry, null);
		//
		String sFilter = calFilter(context, table, pTableAlias, null, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter,
				criticalItems, orderByItems);
		dealExtFilter(context, table, selectQry, null);
		//
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
		// 
		String sFilter = calFilter(context, table, pTableAlias, filter, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter);
		dealExtFilter(context, table, selectQry, filter);
		//
		long recordCount = this.countBy(context, table, filter);
		int currPageNO = 1;
		int maxPageRecordCount = DBPage.MAXCOUNTPERQUERY;
		int totalPage = VarUtil.toInt(recordCount / maxPageRecordCount + (recordCount % maxPageRecordCount > 0 ? 1 : 0));
		for (; currPageNO <= totalPage; currPageNO++) { // 分批处理数据
			selectQry.clearListener();
			selectQry = dealWithSelectQuery(selectQry, currPageNO, maxPageRecordCount); // NOTICE for wrapSQL
			mainSQL = selectQry.getSQL(); // 4 log
			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
			SQLDBPageFactory pf = getPageFactory(null, selectQry.buildWithScrollCursor(getConnection(context)), factory);
			pf.dealPage(maxPageRecordCount, currPageNO, callback);
		}
	}
	@Override
	public void loopBy(ITableDBContext context, Table table, IDBRecord record,
			ISQLRecordFactory factory, final IDBRecordCallBack callback) throws Throwable {
		DebugUtil.debug("##loopByRecord:" + record);
		// try {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		// Items
		ArrayList<CriticalItem> criticalItems = getCriticalItems(table, record, pTable, null, context);
		ArrayList<OrderByItem> orderByItems = getOrderByItems(record, null); // count不需要order
		// 
		String sFilter = calFilter(context, table, pTableAlias, null, true);
		Query selectQry = new Query(pTable.getSQL(), sFilter, criticalItems, orderByItems);
		dealExtFilter(context, table, selectQry, null);
		//
		long recordCount = this.countBy(context, table, record);
		int currPageNO = 1;
		int maxPageRecordCount = DBPage.MAXCOUNTPERQUERY;
		int totalPage = VarUtil.toInt(recordCount / maxPageRecordCount + (recordCount % maxPageRecordCount > 0 ? 1 : 0));
		for (; currPageNO <= totalPage; currPageNO++) {
			selectQry.clearListener();
			selectQry = dealWithSelectQuery(selectQry, currPageNO, maxPageRecordCount); // NOTICE for wrapSQL
			mainSQL = selectQry.getSQL();  // 4 log
			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + sql);
			SQLDBPageFactory pf = getPageFactory(
					null, 
					selectQry.buildWithScrollCursor(getConnection(context)), 
					factory);
			pf.dealPage(maxPageRecordCount, currPageNO, callback);
		}
	}
	@Override
	public PreparedStatement getSelectPreparedStatement(ITableDBContext context, Table table, String id) throws Throwable {
		PrimaryTable pTable = getBaseSelectSQL(context, table);
		String pTableAlias = pTable.getFirstTableAlias();
		//
		mainSQL = pTable.getSQL();
		mainSQL += " where " + pTableAlias + "." + table.getId().getName() + "=? ";
		//
		DebugUtil.debug(">selectByPK(Table=" + table.getName() + ",ID=" + id + "):" + mainSQL);
		//
		PreparedStatement ps = null;
		ps = getConnection(context).prepareStatement(mainSQL);
		// ps.setInt(1,id);
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
//	/**
//	 * @deprecated 
//	 * (non-Javadoc)
//	 * 
//	 * @see com.haiyan.genmis.db.SQLRender#findAllFromTopLevelPreStat(
//	 *      com.haiyan.genmis.castorgen.Table,
//	 *      com.haiyan.genmis.core.IContext)
//	 */
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
//	/**
//	 * @deprecated
//	 * (non-Javadoc)
//	 * 
//	 * @see com.haiyan.genmis.db.SQLRender#findChildrenByPIDPreStat(
//	 *      com.haiyan.genmis.castorgen.Table, java.lang.String,
//	 *      com.haiyan.genmis.core.IContext)
//	 */
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
//	/**
//	 * @deprecated 
//	 * (non-Javadoc)
//	 * 
//	 * @see com.haiyan.genmis.db.SQLRender#findXLoadByPIDPreStat(
//	 *      com.haiyan.genmis.castorgen.Table, java.lang.String,
//	 *      java.lang.String, com.haiyan.genmis.core.IContext)
//	 */
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
//
//	/**
//	 * @deprecated 
//	 * (non-Javadoc)
//	 * 
//	 * @see com.haiyan.genmis.db.SQLRender#findXLoadPIDsPreStat(
//	 *      com.haiyan.genmis.castorgen.Table,
//	 *      com.haiyan.genmis.castorgen.Field,
//	 *      com.haiyan.genmis.core.IContext)
//	 */
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
//
//	/**
//	 * @param table
//	 * @param tableAlias
//	 * @param context
//	 * @return String
//	 * @throws Throwable
//	 */
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
			SQLDBFilter extendFilter = (SQLDBFilter)context.getAttribute("__extendFilter." + table.getName());
			if (extendFilter != null) {
				int s = result.lastIndexOf("order by");
				if (s >= 0) { // 补充过滤
					result = result.substring(0, s) + extendFilter.getSql() + result.substring(s);
				} else
					result += extendFilter.getSql();
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
				if (!StringUtil.isBlankOrNull(pf.getMethodName())) {
					String className = StringUtil.isBlankOrNull(pf.getClassName()) ? 
						PropUtil.getProperty("DEFAULT_FILTER") : pf.getClassName();
					DebugUtil.debug(">doExecuteFilter:" + className + " " + pf.getMethodName());
					// NOTICE 后面有pf.getContent()的处理
					// String sPara = !StringUtil.isBlankOrNull(pf.getParameter()) ? pf.getParameter() : pf.getContent();
					filterStr += InvokeUtil.getString(className, pf.getMethodName(), 
						new Class[] { IContext.class, Table.class, String.class, String.class },
						new Object[] { context, table, tableAlias, pf.getParameter() });
				}
				// 直接的sql语句
				if (!StringUtil.isBlankOrNull(pf.getContent()))
					filterStr += pf.getContent(); // formula.getString(pf.getContent());

				if (filterStr != null) {
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
}