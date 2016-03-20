package haiyan.orm.database.sql;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;
import haiyan.common.PropUtil;
import haiyan.common.Ref;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IDBClear;
import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCacheManager;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLDBListener;
import haiyan.common.intf.database.sql.ISQLDBManager;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.PluginInterceptor;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.util.ConfigUtil;
import haiyan.config.util.NamingUtil;
import haiyan.orm.database.DBRecord;
import haiyan.orm.database.MappingDBManager;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.database.ITableDBManager;
import haiyan.orm.intf.database.sql.ITableSQLRender;
import haiyan.orm.intf.session.ITableDBContext;

abstract class SQLTableDBManager extends AbstractSQLDBManager implements ITableDBManager, ISQLDBManager {
	protected static final int MAX_DEEP = 2;
	protected transient int deep = 0;
	protected transient ITableSQLRender sqlRender;
	SQLTableDBManager(ISQLDatabase db) {
		super(db);
	}
	@Override
	public void commit(ITableDBContext context) throws Throwable {
		this.commit();
	}
	@Override
	public void rollback(ITableDBContext context) throws Throwable {
		this.rollback();
	}
	@Override
	public void commit() throws Throwable {
		if (this.isAlive()) {
			if (!this.connection.getAutoCommit()) { // 事务不是自动提交
				this.beforeCommit(); // for hsqldb
				this.connection.commit(); // 主动提交
				this.connection.setAutoCommit(true); // 变回自动提交
				this.autoCommit=true; // 设置成自动提交
				this.afterCommit(); // for hsqldb
			}
			DebugUtil.debug(">----< dbm.commit.connHash:" + this.connection.hashCode()
					+ "\tdbm.isAutoCommit:" + this.autoCommit);
		} else {
			DebugUtil.debug(">----< dbm.commit.visualHash:" + this.hashCode()
					+ "\tdbm.isAutoCommit:" + this.autoCommit);
		}
		super.commit();
	}
	@Override
	public void rollback() throws Throwable {
		if (this.isAlive()) {
			if (!this.connection.getAutoCommit()) { // 事务不是自动提交
				this.beforeRollback(); // for hsqldb
				if (this.getSavepoint()!=null)
					this.connection.rollback(this.getSavepoint());
				else
					this.connection.rollback();
				this.connection.setAutoCommit(true); // 变回自动提交
				this.autoCommit=true;
				this.afterRollback(); // for hsqldb
			}
			DebugUtil.debug(">----< dbm.rollback.connHash:" + this.connection.hashCode()
					+ "\tdbm.isAutoCommit:" + this.autoCommit);
		} else {
			DebugUtil.debug(">----< dbm.rollback.visualHash:" + this.hashCode()
					+ "\tdbm.isAutoCommit:" + this.autoCommit);
		}
		super.rollback();
	}
	@Override
	public void clear() {
		//LOCAL.remove();
		super.clear();
		try {
			if (this.dbClear != null)
				this.dbClear.clean(); // clear cache blob object or etc
		} catch (Throwable ignore) {
			ignore.printStackTrace();
		}
	}
	@Override
	public IDBClear getClear() {
		return this.dbClear;
	}
	// ----------------------------------------------------------//
	protected static SQLTableDBManager getDBM(ITableDBContext context) {
		return (SQLTableDBManager)context.getDBM();
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @return
	 * @throws Throwable
	 */
	public static IDBRecord updateRecord(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		IDBRecord res = getDBM(context).update(context, table, record);
		return res;
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @return
	 * @throws Throwable
	 */
	public static IDBRecord insertRecord(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		IDBRecord res = getDBM(context).insert(context, table, record);
		return res;
	}
	/**
	 * @param context
	 * @param table
	 * @param id
	 * @return
	 * @throws Throwable
	 */
	public static IDBRecord selectByID(ITableDBContext context, Table table, String id) throws Throwable {
		return getDBM(context).select(context, table, id);
	}
	/**
	 * @param context
	 * @param table
	 * @param id
	 * @return
	 * @throws Throwable
	 */
	public static IDBResultSet selectByID(ITableDBContext context, Table table, String[] ids) throws Throwable {
		return getDBM(context).select(context, table, ids);
	}
	/**
	 * @param context
	 * @param tableName
	 * @param record
	 * @param maxPageRecordCount
	 * @param currPageNO
	 * @return
	 * @throws Throwable
	 */
	public static IDBResultSet selectByRecord(ITableDBContext context, String tableName, IDBRecord record,
			int maxPageRecordCount, int currPageNO)
			throws Throwable {
		return selectByRecord(context, ConfigUtil.getTable(tableName), record,
				maxPageRecordCount, currPageNO);
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @param maxPageRecordCount
	 * @param currPageNO
	 * @return
	 * @throws Throwable
	 */
	public static IDBResultSet selectByRecord(ITableDBContext context, Table table, IDBRecord record,
			int maxPageRecordCount, int currPageNO)
			throws Throwable {
		return getDBM(context).select(context, table, record, maxPageRecordCount, currPageNO);
	}
	/**
	 * @param tableName
	 * @param filter
	 * @param maxPageRecordCount
	 * @param currPageNO
	 * @param context
	 * @return Page
	 * @throws Throwable
	 */
	public static IDBResultSet selectByFilter(ITableDBContext context, String tableName, IDBFilter filter,
			int maxPageRecordCount, int currPageNO)
			throws Throwable {
		return selectByFilter(context, ConfigUtil.getTable(tableName), filter, maxPageRecordCount, currPageNO);
	}
	/**
	 * @param table
	 * @param filter
	 * @param context
	 * @return Page
	 * @throws Throwable
	 */
	public static IDBResultSet selectByFilter(ITableDBContext context, Table table, IDBFilter filter,
			int maxPageRecordCount, int currPageNO)
			throws Throwable {
		return getDBM(context).select(context, table, filter, maxPageRecordCount, currPageNO);
	}
//	/**
//	 * 不推荐使用 树数据过于庞大
//	 * 
//	 * @param tableName
//	 * @param pID
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public static Collection<IRecord> staticFindChildrenByPID(
//			String tableName, String pID, ITableContext context) throws Throwable {
//		return staticFindChildrenByPID(ConfigUtil.getTable(tableName), pID, context);
//	}
//	/**
//	 * 不推荐使用 树数据过于庞大
//	 * 
//	 * @param table
//	 * @param pID
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public static Collection<IRecord> staticFindChildrenByPID(Table table,
//			String pID, ITableContext context) throws Throwable {
//		return getDBM(context).findChildrenByPID(table, pID, context);
//	}
//	/**
//	 * 不推荐使用 树数据过于庞大
//	 * 
//	 * @param tableName
//	 * @param pID
//	 * @param context
//	 * @param recFlag
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public static Collection<IRecord> staticFindAllByPID(String tableName,
//			String pID, ITableContext context, boolean recFlag) throws Throwable {
//		return staticFindAllByPID(ConfigUtil.getTable(tableName), pID, context, recFlag);
//	}
//	/**
//	 * 不推荐使用 树数据过于庞大
//	 * 
//	 * @param table
//	 * @param pID
//	 * @param context
//	 * @param recFlag
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public static Collection<IRecord> staticFindAllByPID(Table table,
//			String pID, ITableContext context, boolean recFlag) throws Throwable {
//		return getDBM(context).findAllByPID(table, pID, context, recFlag);
//	}
//	/**
//	 * 不推荐使用 树数据过于庞大
//	 * 
//	 * @param tableName
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public static Collection<IRecord> staticFindAllFromTopLevel(
//			String tableName, ITableContext context) throws Throwable {
//		return staticFindAllFromTopLevel(ConfigUtil.getTable(tableName), context);
//	}
//	/**
//	 * 不推荐使用 树数据过于庞大
//	 * 
//	 * @param table
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public static Collection<IRecord> staticFindAllFromTopLevel(Table table,
//			ITableContext context) throws Throwable {
//		return getDBM(context).findAllFromTopLevel(table, context);
//	}
//	/**
//	 * 默认数据库查询
//	 * 
//	 * @param SQL
//	 * @return int
//	 * @throws Throwable
//	 */
//	public static int staticExecute(ITableContext context, String SQL)
//			throws Throwable {
//		return getDBM(context).executeUpdate(SQL);
//	}
//	/**
//	 * 默认数据库查询
//	 * 
//	 * @param SQL
//	 * @param colNum
//	 * @return String[][]
//	 * @throws Throwable
//	 */
//	public static String[][] staticGetResultStrArray(String SQL, int colNum, ITableContext context) throws Throwable {
//		return getDBM(context).getResultStrArray(SQL, colNum);
//	}
//	// ----------------------------------------------------------//
//	/**
//	 * 不推荐使用 可能这层数据过于庞大
//	 * 
//	 * @param table
//	 * @param pID
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public ArrayList<IRecord> findChildrenByPID(Table table, String pID,
//			ITableContext context, int... args) throws Throwable {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ArrayList<IRecord> result = new ArrayList<IRecord>();
//			ps = getSQLDBTemplate().findChildrenByPIDPreStat(table, pID, context);
//			rs = ps.executeQuery();
//			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + getSQLDBTemplate().getSQL());
//			while (rs.next()) {
//				IRecord obj = getFormByRow(context, table, rs);
//				if (obj == null)
//					continue;
//				TreeForm treeForm = new TreeForm(obj);
//				result.add(treeForm);
//			}
//			return result;
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(getSQLDBTemplate().getSQL());
//				if (args != null && isDeep(args))
//					return findChildrenByPID(table, pID, context, getDeep(args));
//			}
//			throw ex;
//		} finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(ps);
//		}
//	}
//	/**
//	 * 不推荐使用 查所有的子树数据过于庞大
//	 * 
//	 * @param table
//	 * @param pID
//	 * @param context
//	 * @param recFlag
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public ArrayList<IRecord> findAllByPID(Table table, String pID,
//			ITableContext context, boolean recFlag, int... args) throws Throwable {
//		ArrayList<IRecord> result = new ArrayList<IRecord>();
//		TreeForm topForm = null;
//		// DBTransaction tx = this.transaction;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			final Field parentField = ConfigUtil.searchChildTableRefField(table, table);
//			// find first, compose later
//			ps = getSQLDBTemplate().findAllFromTopLevelPreStat(table, context);
//			rs = ps.executeQuery();
//			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + getSQLDBTemplate().getSQL());
//			// TODO PageList
//			HashMap<String, ArrayList<IRecord>> nodes = new HashMap<String, ArrayList<IRecord>>();
//			while (rs.next()) {
//				IRecord obj = getFormByRow(context, table, rs);
//				if (obj == null)
//					continue;
//				TreeForm treeForm = new TreeForm(obj);
//				String id = treeForm.get(table.getId().getName());
//				String pid = treeForm.get(parentField.getName());
//				if (id.equals(pID)) {
//					topForm = treeForm;
//				}
//				if (StringUtil.isStrBlankOrNull(pid) || "-1".equals(pid)) {
//					result.add(treeForm);
//				} else {
//					ArrayList<IRecord> tempList = nodes.get(pid);
//					if (tempList == null) {
//						tempList = new ArrayList<IRecord>();
//					}
//					tempList.add(treeForm);
//					nodes.put(pid, tempList);
//				}
//			}
//			ArrayList<IRecord> queue = new ArrayList<IRecord>();
//			queue.addAll(result);
//			while (queue.size() > 0) {
//				TreeForm tempForm = (TreeForm) (queue).get(0);
//				queue.remove(0);
//
//				String id = tempForm.get(table.getId().getName());
//				ArrayList<IRecord> childrenList = nodes.get(id);
//
//				if (childrenList == null) {
//					continue;
//				} else {
//					for (int i = 0; i < childrenList.size(); i++) {
//						tempForm.addChild((TreeForm) childrenList.get(i));
//						// DebugUtil.debug(">"+((TreeForm)tempList.get(i)).getParameter(table.getId().getName()));
//					}
//					queue.addAll(childrenList);
//				}
//			}
//			if (topForm != null)
//				result = topForm.getChildren();
//			if (!recFlag) {
//				return result;
//			} else {
//				ArrayList<IRecord> recResult = new ArrayList<IRecord>();
//				for (int i = 0; i < result.size(); i++) {
//					//
//					TreeForm dForm = (TreeForm) result.get(i);
//					//
//					recResult.add(dForm);
//					ArrayList<IRecord> list = dForm.recurseToCol();
//					for (int j = 0; j < list.size(); j++) {
//						//
//						recResult.add(list.get(j));
//					}
//				}
//				return recResult;
//			}
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(getSQLDBTemplate().getSQL());
//				if (isDeep(args))
//					return findAllByPID(table, pID, context, recFlag,
//							getDeep(args));
//			}
//			throw ex;
//		} finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(ps);
//		}
//	}
//	/**
//	 * 不推荐使用 查所有的树数据过于庞大
//	 * 
//	 * @param table
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public ArrayList<IRecord> findAllFromTopLevel(Table table,
//			ITableContext context, int... args) throws Throwable {
//		// 全部查询的树结构只对小数据有效
//		int c = this.countByFilter(table, "", context, args);
//		if (c > 500)
//			throw new Warning("因单次加载的数据量过大响应失败，请使用延迟加载页面。");
//		// TODO PageList
//		ArrayList<IRecord> result = new ArrayList<IRecord>();
//		// DBTransaction tx = this.transaction;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			Field parentField = ConfigUtil.searchChildTableRefField(table, table);
//			ps = getSQLDBTemplate().findAllFromTopLevelPreStat(table, context);
//			rs = ps.executeQuery();
//			// LogUtil.info(" execute-time:" + DateUtil.getLastTime()
//			// + " execute-sql:" + getSQLDBTemplate().getSQL());
//			HashMap<String, ArrayList<IRecord>> nodes = new HashMap<String, ArrayList<IRecord>>();//
//			// //
//			while (rs.next()) {
//				IRecord obj = getFormByRow(context, table, rs);
//				if (obj == null)
//					continue;
//				TreeForm treeForm = new TreeForm(obj);
//				// DebugUtil.debug("====>parentField" + parentField);
//				String pid = null;
//				if (parentField != null)
//					pid = treeForm.get(parentField.getName());
//				// TODO pid default value
//				if (StringUtil.isStrBlankOrNull(pid) || pid.equals("-1") || pid.equals("0")) {
//					result.add(treeForm);
//				} else {
//					ArrayList<IRecord> tempList = nodes.get(pid);
//					if (tempList == null) {
//						tempList = new ArrayList<IRecord>();
//					}
//					tempList.add(treeForm);
//					nodes.put(pid, tempList);
//				}
//			}
//			ArrayList<IRecord> queue = new ArrayList<IRecord>();//
//			queue.addAll(result);
//			while (queue.size() > 0) {
//				TreeForm tempForm = (TreeForm) (queue).get(0);
//				queue.remove(0);
//				String id = tempForm.get(table.getId().getName());//
//				ArrayList<IRecord> childrenList = nodes.get(id);//
//				if (childrenList == null) {
//					continue;
//				} else {
//					for (int i = 0; i < childrenList.size(); i++) {
//						tempForm.addChild((TreeForm) childrenList.get(i));
//						// DebugUtil.debug(":"+((TreeForm)tempList.get(i)).getParameter(table.getId().getName()));
//					}
//					queue.addAll(childrenList);
//				}
//			}
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(getSQLDBTemplate().getSQL());
//				if (isDeep(args))
//					return findAllFromTopLevel(table, context, getDeep(args));
//			}
//			throw ex;
//		} finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(ps);
//		}
//		return result;
//	}
//	/**
//	 * 不推荐使用 查所有的树数据过于庞大
//	 * 
//	 * @param table
//	 * @param context
//	 * @return Collection
//	 * @throws Throwable
//	 */
//	@Deprecated
//	public ArrayList<IRecord> findAllXNodesFromPID(final Table table,
//			final ITableContext context, int... args) throws Throwable {
//		long time = System.currentTimeMillis();
//		// TODO PageList
//		ArrayList<IRecord> result = new ArrayList<IRecord>();
//		// MyXLoadTreeNode
//		String tchooseMode = context.getParameter(DataConstant.EXTEND_OPERATION_CODE_PARAM_CHOOSE_MODE);
//		// String tmainTableName = context.getParameter(DataConstant.MAIN_TABLE_PARAM);
//		String param = context.getParameter(DataConstant.XLOADTREE_DATAPARAM);
//		String tparentlast = null;
//		String tparenttreeid = null;
//		String tparentdataid = null;
//		String[] params = null;
//		if (param != null && param.length() != 0) {
//			// params = param.split(DataConstant.XLOADTREE_DIM);
//			params = StringUtil.split(param, DataConstant.XLOADTREE_DIM);
//			// DebugUtil.debug(params.length);
//			tparentdataid = params[2];
//			tparenttreeid = params[5];
//			tparentlast = params[6];
//			tchooseMode = params[8];
//		}
//		DebugUtil.debug(">xloadparam:" + param);
//		DebugUtil.debug(">xloadparam.tparenttreeid:" + tparenttreeid
//				+ ",tparentdataid:" + tparentdataid + ",tparentlast:"
//				+ tparentlast + ",tchooseMode:" + tchooseMode);
//		final String chooseMode = tchooseMode;
//		final String parentlast = tparentlast;
//		final String parenttreeid = tparenttreeid;
//		final String parentdataid = tparentdataid;
//		final String pageviewindex = context
//				.getParameter(DataConstant.PAGEVIEW_INDEX);
//		final String showFields = table.getPageView()[Integer
//				.parseInt(pageviewindex)].getShowField();
//		// final String mainTableName = (tmainTableName == null) ?
//		// table.getName()
//		// : tmainTableName;
//		// final TreeViewRender render = new TreeViewRender(table, result,
//		// pageviewindex);
//		if (renderUtil == null)
//			renderUtil = new RenderUtil();
//		// String procInfo = context.getParameter(DataConstant.PROCID);
//		// String procInfoStr = "";
//		// if (procInfo != null) {
//		// procInfoStr = "&" + DataConstant.PROCID + "=" + procInfo;
//		// }
//		// final String flowinfo = procInfoStr;
//		final HashMap<?, ?> judgeMx = pGetXLoadPIDSMap(table, context);
//		// (HashMap)pidsMap.clone();
//		// DBTransaction tx = this.transaction;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			MyXLoadTreeFactory factory = new MyXLoadTreeFactory() {
//				public void dealLastNode(MyXLoadTreeNode node) {
//					node.setLast("1");
//					node.setPlast(parentlast == null ? "0" : parentlast
//							+ node.getLast());
//					dealNodeOther(node);
//				}
//				public void dealNodeParentLast(MyXLoadTreeNode node) {
//					node.setPlast(parentlast == null ? "0" : parentlast
//							+ node.getLast());
//				}
//				public void dealNodeOther(MyXLoadTreeNode node) {
//					node.setOther(DataConstant.XLOADTREE_INFOPARAM
//							+ DataConstant.XLOADTREE_DIM + table.getName()
//							+ DataConstant.XLOADTREE_DIM + node.getDataID()
//							+ DataConstant.XLOADTREE_DIM + node.getJs()
//							+ DataConstant.XLOADTREE_DIM + node.getMx()
//							+ DataConstant.XLOADTREE_DIM + node.getTreeID()
//							+ DataConstant.XLOADTREE_DIM + node.getPlast()
//							+ DataConstant.XLOADTREE_DIM + node.getLast()
//							+ DataConstant.XLOADTREE_DIM + chooseMode);
//				}
//				public void dealNodeURL(MyXLoadTreeNode node) {
//					String href = "javascript:showDetailFrame(\""
//							+ DataConstant.OPERATION_EDIT + "\",\""
//							+ node.getDataID() + "\");";
//					node.setHref(href);
//				}
//				public void dealNodeName(MyXLoadTreeNode node) {
//					// 2007-07-24 zhouxw
//					String[] names = StringUtil.split(showFields, ",");
//					String name = "";
//					for (int j = 0; j < names.length; j++) {
//						if (j != 0)
//							name += " ";
//						name += node.get(names[j]);
//					}
//					// NOTICE 2008-02-05 zhouxw 显示的节点名
//					node.setName("&nbsp;" + name);
//				}
//				public void dealNodeMx(MyXLoadTreeNode node) {
//					node.setMx("0");
//					if (judgeMx.get(node.getDataID()) == null) {
//						node.setMx("1");
//					}
//				}
//				public void dealNodeChooseMode(MyXLoadTreeNode node) {
//					node.setChooseMode(chooseMode);
//				}
//				public void dealNodeJs(MyXLoadTreeNode node) {
//					node.setJs(parenttreeid == null ? "1"
//							: Integer.toString(StringUtil.split(parenttreeid,
//									"_").length));
//				}
//				public void dealNodeTreeID(MyXLoadTreeNode node, int index) {
//					node.setId(parenttreeid == null ? DataConstant.XLOADTREE_LABEL + "_" + index
//							: parenttreeid + "_" + index);
//				}
//				public void dealNodeDataID(MyXLoadTreeNode node) {
//					node.setDataID(node.getParameter(table.getId().getName()));
//				}
//				public void dealCanBeSelected(MyXLoadTreeNode node)
//						throws Throwable {
//					// DebugUtil.debug(">" + chooseMode);
//					if (chooseMode != null) { // && !chooseMode.equals("normal")
//						node.setCanBeSelect(renderUtil.isSelectable(table, node, context));
//					} else {
//						node.setCanBeSelect(false);
//					}
//				}
//				public void dealNodeLast(MyXLoadTreeNode node) {
//					node.setLast("0");
//				}
//				public void dealCanBeShowed(MyXLoadTreeNode node) {
//					// node.setCanBeShowed(renderUtil.isShowable(dbm2, node));
//					node.setCanBeShowed(true);
//				}
//			};
//			// TODO findXLoadByPIDPreStat
//			ps = getSQLDBTemplate().findXLoadByPIDPreStat(table, parentdataid, showFields, context); // parenttreeid,
//			rs = ps.executeQuery();
//			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + getSQLDBTemplate().getSQL());
//			int treeIndex = 0;
//			while (rs.next()) {
//				IRecord obj = getFormByRow(context, table, rs);
//				if (obj == null)
//					continue;
//				factory.deal(obj, treeIndex++, result);
//			}
//			if (treeIndex > 0) {
//				MyXLoadTreeNode node = ((MyXLoadTreeNode) result.get(treeIndex - 1));
//				factory.dealLastNode(node);
//			}
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(getSQLDBTemplate().getSQL());
//				if (isDeep(args))
//					return findAllXNodesFromPID(table, context, getDeep(args));
//			}
//			throw ex;
//		} finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(ps);
//		}
//		DebugUtil.debug(">findAllXNodesFromPID cost:" + (System.currentTimeMillis() - time) + "ms");
//		return result;
//	}
//	/**
//	 * @param table
//	 * @param context
//	 * @return HashMap
//	 * @throws Throwable
//	 */
//	private HashMap<String, String> pGetXLoadPIDSMap(Table table,
//			ITableContext context, int... args) throws Throwable {
//		Field parentField = ConfigUtil.searchChildTableRefField(table, table);
//		HashMap<String, String> pidsMap = new HashMap<String, String>();
//		// ArrayList pids = new ArrayList();
//		// DBTransaction tx = this.transaction;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ps = getSQLDBTemplate().findXLoadPIDsPreStat(table, parentField, context);
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				String id = rs.getString(1);
//				// pids.add(id);
//				pidsMap.put(id, id);
//			}
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(getSQLDBTemplate().getSQL());
//				if (isDeep(args))
//					return pGetXLoadPIDSMap(table, context, getDeep(args));
//			}
//			throw ex;
//		} finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(ps);
//		}
//		// return pids;
//		return pidsMap;
//	}
//	/**
//	 * @param SQL
//	 * @param colNum
//	 * @param paras
//	 * @param args
//	 * @return
//	 * @throws Throwable
//	 */
//	public JSONArray getJSonArrayPre(String SQL, Object[] paras, IDB2JSon db2json, int... args)
//			throws Throwable {
//		try {
//			return SQLDBHelper.getJSonArrayPre(SQL, paras, db2json, this.getConnection());
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(SQL);
//				if (isDeep(args))
//					return getJSonArrayPre(SQL, paras, db2json, getDeep(args));
//			}
//			throw ex;
//		}
//	}
//	/**
//	 * @param SQL
//	 * @param colNum
//	 * @param db2json
//	 * @param args
//	 * @return
//	 * @throws Throwable
//	 */
//	public JSONArray getJSonArray(String SQL, IDB2JSon db2json, int... args)
//			throws Throwable {
//		try {
//			return SQLDBHelper.getJSonArray(SQL, db2json, this.getConnection());
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(SQL);
//				if (isDeep(args))
//					return getJSonArray(SQL, db2json, getDeep(args));
//			}
//			throw ex;
//		}
//	}
	// ------------------------------------------------------ createRecord ------------------------------------------------------ //
	@Override
	public IDBRecord createRecord() {
		IDBRecord record = new DBRecord();
		return record;
	}
	@Override
	public IDBRecord createRecord(Class<?> clz) throws Throwable {
		IDBRecord record = (IDBRecord)clz.newInstance();
		return record;
	}
	// ------------------------------------------------------ @Deprecated ------------------------------------------------------ //
	/**
	 * @param SQL
	 * @param colNum
	 * @param paras
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public Object[][] getResultArray(String SQL, int colNum, Object[] paras, int... args)
			throws Throwable {
		try {
			return SQLDBHelper.getResultArray(SQL, colNum, paras, this.getConnection());
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(SQL);
				if (isDeep(args))
					return getResultArray(SQL, colNum, paras, getDeep(args));
			}
			throw ex;
		}
	} 
	@Override
	public Object[][] getResultArray(String SQL, int colNum, Object[] paras) throws Throwable {
		return getResultArray(SQL, colNum, paras, null);
	}
	protected static final List<String> EVENTS = new ArrayList<String>();
	/**
	 * @param SQL
	 * @param paras
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public int executeUpdate(final String SQL, Object[] paras, int... args) throws Throwable {
		try {
			int r = SQLDBHelper.executeUpdate(SQL, paras, this.getConnection(true));
			String t = SQL.trim().toLowerCase().substring(0, 10);
			if (t.startsWith("update ") || t.startsWith("delete ")) { // || t.startsWith("insert ")
				synchronized(EVENTS) { // 对SQL的处理特殊，要分析对哪些表做了改动，之后要整理多级缓存
					if (!EVENTS.contains(SQL))
						EVENTS.add(SQL);
				}
			}
			return r;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(SQL);
				if (isDeep(args))
					return executeUpdate(SQL, paras, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public int executeUpdate(String SQL, Object[] paras) throws Throwable {
		return executeUpdate(SQL, paras, null);
	}
	// ------------------------------------------------------ status ------------------------------------------------------ //
	protected void checkUsedStatus(ITableDBContext context, Table table, String[] ids)
			throws Throwable {
		Field USEDSTATUS = ConfigUtil.getFieldByName(table, DataConstant.USEDSTATUS, true);
		if (USEDSTATUS != null) {
			String label = USEDSTATUS.getJavaType() == AbstractCommonFieldJavaTypeType.STRING ? "'" : "";
			//String label = ConfigUtil.isDecimalPK(table)?"":"'";
			String SQL = String.format(
				"select count(%s) from %s where %s=? and %s in (%s)", //
				table.getId().getName(),
				getDBName(table),
				DataConstant.USEDSTATUS,
				label + "1" + label, 
				table.getId().getName(), 
				StringUtil.joinSQL(ids, ",", label));
			Object[][] rs = getDBM(context).getResultArray(SQL, 1, null);
			if (rs.length > 0 && rs[0].length > 0 && VarUtil.toDouble(rs[0][0]) > 0)
				throw new Warning("数据被占用不可删除.");
		}
	}
	public void changeUsedStatus(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		ArrayList<ConfigUtil.LinkField> list = ConfigUtil.getLinkTable(table);
		for (ConfigUtil.LinkField f : list) {
			String fv = (String)record.get(f.field);
			if (StringUtil.isEmpty(fv))
				continue;
			String[] vs = StringUtil.split(fv, ",");
			for (String v : vs) {
				IDBRecord rec = getDBM(context).select(context, ConfigUtil.getTable(f.linkTable), v);
				if (rec == null || "1".equals(rec.get(DataConstant.USEDSTATUS)))
					continue;
				rec.set(DataConstant.USEDSTATUS, "1");
				getDBM(context).update(context, ConfigUtil.getTable(f.linkTable), rec);
			}
		}
	}
	// 乐观离线锁 TODO 用MVCC多版本并发控制来做，其实innoDB实现了
	public void checkVersion(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		if (!ConfigUtil.isORMUseCache() || !ConfigUtil.isORMSMCC() || table.getName().toUpperCase().startsWith("V_")) // 系统标记 
			return;
		if (ConfigUtil.getFieldByName(table, DataConstant.HYVERSION, true)==null) // 配置中取消检查的标记
			return;
		Boolean check = (Boolean)context.getAttribute(DataConstant.HYVERSIONCHECK); // 一个App生命周期中是否需要检查的标记
		if (check!=null && check==false)
			return;
		// 双缓冲：更新缓存后能提高DB->VO的转换效率
		// 要注意直接执行SQL语句的操作对缓冲数据的影响
		String IDName = table.getId().getName();
		String IDVal = (String)record.get(IDName);
		// NOTICE checkVersion这样会多一次网络和数据库访问，只用在非常严谨的企业应用里
		IDBRecord oldRecord = this.select(context, table, IDVal); // IDBRecordCacheManager.PERSIST_SESSION);
		if (oldRecord!=null && oldRecord!=record) { // 同一个dbsession中可能是一个
			int vOld = oldRecord.getVersion();
			int vNow = record.getVersion();
			if (vOld > vNow) {
				DebugUtil.debug(">vOld="+vOld+",vNow="+vNow);
				throw new Warning(VERSION_WARNING);
			}
		}
	}
	public final static String VERSION_WARNING = "当前数据已被修改,请重新加载数据后继续操作.";
	// ------------------------------------------------------ CRUD ------------------------------------------------------ //
	/**
	 * @param table
	 * @param form
	 * @param context
	 * @return IRecord
	 * @throws Throwable
	 */
	protected List<IDBRecord> insertNoSyn(ITableDBContext context, Table table, List<IDBRecord> records,
			int... args) throws Throwable {
		PreparedStatement ps = null;
		try {
			ps = getSQLRender().getInsertPreparedStatement(context, table);
			if (ps == null) {
				for (IDBRecord record : records) {
					record.setStatus(IDBRecord.INSERT_FAIL);
				}
				return records;
			}
			Field[] fields = getSQLRender().getInsertValidField(context, table);
			for (IDBRecord record:records) {
				String newID = record.getString(table.getId().getName()); // context.getNextID(table);
				if (StringUtil.isEmpty(newID)) {
					throw new Warning("insert时既没有ID也没有Filter");
				}
				try {
					record.updateVersion();
					getSQLRender().insertPreparedStatement(context, table, record, ps, fields, newID);
					ps.addBatch();
					SQLMappingDBManager.setMappingTableValue(context, table, record, newID,
							MappingDBManager.SET_MAPPING_TABLE_WHEN_CREATE);
	//				SQLDBOne2OneManager.setOne2OneTableValue(context, table, form, newID,
	//						DBTableManager.SET_MAPPING_TABLE_WHEN_CREATE);
					// 2006-12-06
					record.set(table.getId().getName(), newID);
					this.updateCache(context, table, record, IDBRecordCacheManager.CONTEXT_SESSION);
					this.changeUsedStatus(context, table, record);
				} catch (Throwable e) {
					record.rollbackVersion();
					throw e;
				}
			}
			ps.executeBatch(); 
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return insertNoSyn(context, table, records, getDeep(args));
			}
			throw ex;
		} finally {
			CloseUtil.close(ps);
		}
		return records;
	}
	@Override
	public List<IDBRecord> insert(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
		return insert(context, table, records, null);
	}
	/**
	 * @param context
	 * @param table
	 * @param records
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	protected List<IDBRecord> insert(ITableDBContext context, Table table, List<IDBRecord> records,
			int... args) throws Throwable {
		for (IDBRecord record:records) {
			String newID = (String)context.getNextID(table);
			record.set(table.getId().getName(), newID);
		}
		return this.insertNoSyn(context, table, records, args);
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	protected IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record,
			int... args) throws Throwable {
		String newID = record.getString(table.getId().getName()); // context.getNextID(table);
		if (StringUtil.isEmpty(newID)) {
			throw new Warning("insert时既没有ID也没有Filter");
		}
		PreparedStatement ps = null;
		try {
			record.updateVersion();
			ps = getSQLRender().getInsertPreparedStatement(context, table, record, newID);
			if (ps == null) {
				record.setStatus(IDBRecord.INSERT_FAIL);
				return record;
			}
			// ps.executeUpdate();
			if (!ps.execute()) {
				record.setStatus(IDBRecord.INSERT_FAIL);
			}
			// LogUtil.info(" execute-time:" + DateUtil.getLastTime() + " execute-sql:" + getSQLDBTemplate().getSQL());
			SQLMappingDBManager.setMappingTableValue(context, table, record, newID, MappingDBManager.SET_MAPPING_TABLE_WHEN_CREATE);
//			SQLDBOne2OneManager.setOne2OneTableValue(context, table, form, newID, DBTableManager.SET_MAPPING_TABLE_WHEN_CREATE);
			// 2006-12-06
			record.set(table.getId().getName(), newID);
			this.updateCache(context, table, record, IDBRecordCacheManager.CONTEXT_SESSION);
			this.changeUsedStatus(context, table, record);
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return insertNoSyn(context, table, record, getDeep(args));
			}
			record.rollbackVersion();
			throw ex;
		} finally {
			CloseUtil.close(ps);
		}
		return record;
	}
	@Override
	public IDBRecord insertNoSyn(ITableDBContext context, Table table, IDBRecord record) throws Throwable {
		return insertNoSyn(context, table, record, null);
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	protected IDBRecord insert(ITableDBContext context, Table table, IDBRecord record, 
			int... args) throws Throwable {
		String id = table.getId().getName();
		if(record.get(id)==null){
			String newID = (String)context.getNextID(table);
			record.set(id, newID);
		}
		return this.insertNoSyn(context, table, record, args);
	}
	@Override
	public IDBRecord insert(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		return insert(context, table, record, null);
	}
	/**
	 * @param table
	 * @param form
	 * @param context
	 * @return IRecord
	 * @throws Throwable
	 */
	protected List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, IDBFilter filter, int... args) throws Throwable {
		for (IDBRecord record:records) {
			this.checkVersion(context, table, record);
		}
		PreparedStatement ps = null;
		try {
			ps = getSQLRender().getUpdatePreparedStatement(context, table);
			if (ps == null) {
				for (IDBRecord record : records) {
					record.setStatus(IDBRecord.UPDATE_FAIL);
				}
				return records;
			}
			Field[] fields = getSQLRender().getUpdateValidField(context, table);
			for (IDBRecord record:records) {
				String newID = record.getString(table.getId().getName()); // context.getNextID(table);
				if (StringUtil.isEmpty(newID)) {
					throw new Warning("update时既没有ID也没有Filter");
				}
				try {
					record.updateVersion();
					getSQLRender().updatePreparedStatementValue(context, table, record, ps, fields,filter);
					ps.addBatch();
					SQLMappingDBManager.setMappingTableValue(context, table, record, newID, MappingDBManager.SET_MAPPING_TABLE_WHEN_MODIFY);
	//				SQLDBOne2OneManager.setOne2OneTableValue(context, table, form, newID, DBTableManager.SET_MAPPING_TABLE_WHEN_MODIFY);
					this.updateCache(context, table, record, IDBRecordCacheManager.CONTEXT_SESSION);
					this.changeUsedStatus(context, table, record);
				} catch (Throwable e) {
					record.rollbackVersion();
					throw e;
				}
			}
			ps.executeBatch(); 
			return records;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return update(context, table, records,filter, getDeep(args));
			}
			throw ex;
		} finally {
			CloseUtil.close(ps);
		}
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records) throws Throwable {
		return update(context, table, records, null, null);
	}
	@Override
	public List<IDBRecord> update(ITableDBContext context, Table table, List<IDBRecord> records, IDBFilter filter) throws Throwable {
		return update(context, table, records, filter, null);
	}
	/** 
	 * @param context
	 * @param table
	 * @param record
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	protected IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter, int... args) throws Throwable {
		this.checkVersion(context, table, record);
		String newID = record.getString(table.getId().getName()); // context.getNextID(table);
		if (StringUtil.isEmpty(newID) && filter==null) {
			throw new Warning("update时既没有ID也没有Filter");
		}
		PreparedStatement ps = null;
		try {
            record.updateVersion();
			ps = getSQLRender().getUpdatePreparedStatement(context, table, record, filter);
			if (ps == null) {
				record.setStatus(IDBRecord.UPDATE_FAIL);
				return record;
			}
			// ps.executeUpdate();
			if (!ps.execute()) {
				record.setStatus(IDBRecord.UPDATE_FAIL);
			}
			SQLMappingDBManager.setMappingTableValue(context, table, record, newID, MappingDBManager.SET_MAPPING_TABLE_WHEN_MODIFY);
//			SQLDBOne2OneManager.setOne2OneTableValue(context, table, record, newID, DBTableManager.SET_MAPPING_TABLE_WHEN_MODIFY);
			this.updateCache(context, table, record, IDBRecordCacheManager.CONTEXT_SESSION);
			this.changeUsedStatus(context, table, record);
		} catch (SQLException ex) {
			record.rollbackVersion();
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return update(context, table, record,filter, getDeep(args));
			}
			throw ex;
		} finally {
			CloseUtil.close(ps);
		}
		return record;
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter)
			throws Throwable {
		return update(context, table, record, filter, null);
	}
	@Override
	public IDBRecord update(ITableDBContext context, Table table, IDBRecord record)
			throws Throwable {
		return update(context, table, record, null, null);
	}
	/**
	 * @param table
	 * @param ids
	 * @param context
	 * @return boolean
	 * @throws Throwable
	 */
	protected boolean delete(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable {
		PreparedStatement ps = null;
		try {
			checkUsedStatus(context, table, ids);
			for (int i = 0; i < ids.length; i++) {
				SQLMappingDBManager.setMappingTableValue(context, table, null, ids[i], MappingDBManager.SET_MAPPING_TABLE_WHEN_REMOVE);
//				SQLDBOne2OneManager.setOne2OneTableValue(context, table, null, ids[i], DBTableManager.SET_MAPPING_TABLE_WHEN_REMOVE);
			}
			ps = getSQLRender().getDeletePreparedStatement(context, table, ids);
			// ps.executeUpdate();
			boolean flag = ps.execute();
			removeCache(context, table, ids, IDBRecordCacheManager.CONTEXT_SESSION);
			return flag;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return delete(context, table, ids, getDeep(args));
			}
			throw ex;
		} finally {
			CloseUtil.close(ps);
		}
	}
	@Override
	public boolean delete(ITableDBContext context, Table table, String[] ids)
			throws Throwable {
		return delete(context, table, ids, null);
	}
	// ------------------------------------------------------ Query ------------------------------------------------------ //
	/**
	 * @param table
	 * @param tableAlias
	 * @param context
	 * @return String
	 * @throws Throwable
	 */
	public String getPluginQueryFilter(ITableDBContext context, Table table, String tableAlias) throws Throwable {
		return SQLRender.calFilter(context, table, tableAlias, null, true);
	}
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id)
			throws Throwable {
		return select(context, table, id, IDBRecordCacheManager.CONTEXT_SESSION);
	}
	/**
	 * @param context
	 * @param table
	 * @param id
	 * @param type
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	@Override
	public IDBRecord select(ITableDBContext context, Table table, String id,
			short type, int... args) throws Throwable {
		IDBRecord record = getCache(context, table, id, type);
		if (record!=null) {
//			// flush源数据集
//			flushOreign(context, table, record, type);
			DebugUtil.debug(">selectByPKFromCache(Table=" + table.getName() + ",ID=" + id + ",Type:" + type + ")");
			return record;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = getSQLRender().getSelectPreparedStatement(context, table, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				record = getRecordByRow(context, table, rs, type);
				if (record == null)
					return null;
			} else {
				return null;
			}
			// SQLMappingTableManager.queryMappingTable(context, table, form, id, this);
			// SQLOne2OneTableManager.queryOne2OneTable(context, table, result, id, this);
			return record;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return select(context, table, id, type, getDeep(args));
			}
			throw ex;
		} finally {
			CloseUtil.close(rs);
			CloseUtil.close(ps);
		}
	}
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids)
			throws Throwable {
		return select(context, table, ids, IDBRecordCacheManager.CONTEXT_SESSION);
	}
	/**
	 * @param context
	 * @param table
	 * @param id
	 * @param type
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	@Override
	public IDBResultSet select(ITableDBContext context, Table table, String[] ids, int... args) throws Throwable {
		if (ids==null || ids.length==0) {
			//return new DBPage(new ArrayList<IDBRecord>());
			return null;
		}
		String placeholder = "";
		for (int i=0;i<ids.length;i++) {
			if (placeholder.length()>0)
				placeholder+=",";
			placeholder+="?";
		}
		IDBFilter filter = new SQLDBFilter(" and t_1."+table.getId().getName()+" in ("+placeholder+")", ids);
		return this.select(context, table, filter, ids.length, 1);
	}
	@Override
	public long countBy(final ITableDBContext context, final Table table, IDBFilter filter) throws Throwable {
		return countBy(context, table, filter, null);
	}
	/**
	 * @param context
	 * @param table
	 * @param filter
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public long countBy(final ITableDBContext context, final Table table, IDBFilter filter,
			int... args) throws Throwable {
		try {
			return getSQLRender().countBy(context, table, filter);
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return countBy(context, table, filter, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public long countBy(final ITableDBContext context, final Table table, IDBRecord record) throws Throwable {
		return countBy(context, table, record, null);
	}
	/**
	 * @param context
	 * @param table
	 * @param record
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public long countBy(final ITableDBContext context, final Table table, IDBRecord record, int... args) throws Throwable {
		try {
			return getSQLRender().countBy(context, table, record);
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return countBy(context, table, record, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, long startRow, int count) throws Throwable {
		return selectByLimit(context, table, filter, startRow, count, null);
	}
	/**
	 * @param table
	 * @param filter
	 * @param startRow
	 * @param count
	 * @param context
	 * @return Page
	 * @throws Throwable
	 */
	protected IDBResultSet selectByLimit(final ITableDBContext context, final Table table, IDBFilter filter,
			long startRow, int count, int... args) throws Throwable {
		try {
			ISQLRecordFactory factory = getPageRecordFactory(context, table); //, DBManager.DBBATCHSESSION);
			IDBResultSet page = getSQLRender().selectByLimit(context, table, filter, factory, startRow, count);
			factory = null;
			// for (Iterator<?> iter = page.getData().iterator(); iter.hasNext();) {
			// IRecord form = (IRecord) iter.next();
			// if (form == null)
			// continue;
			// MappingTableManager.queryMappingTable(table, form, form.get(table.getId().getName()), this, context);
			// // One2OneTableManager.queryOne2OneTable(table, element, element.get(table.getId().getName()), this, context);
			// }
			return page;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return selectByLimit(context, table, filter, startRow, count, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord record, long startRow, int count) throws Throwable {
		return selectByLimit(context, table, record, startRow, count, null);
	}
	/**
	 * @param table
	 * @param filter
	 * @param startRow
	 * @param count
	 * @param context
	 * @return Page
	 * @throws Throwable
	 */
	protected IDBResultSet selectByLimit(final ITableDBContext context, final Table table, IDBRecord queryRecord,
			long startRow, int count, int... args) throws Throwable {
		try {
			ISQLRecordFactory factory = getPageRecordFactory(context, table); //, DBManager.DBBATCHSESSION);
			IDBResultSet page = getSQLRender().selectByLimit(context, table, queryRecord, factory, startRow, count);
			factory = null;
			// for (Iterator<?> iter = page.getData().iterator(); iter.hasNext();) {
			// IRecord form = (IRecord) iter.next();
			// if (form == null)
			// continue;
			// MappingTableManager.queryMappingTable(table, form, form.get(table.getId().getName()), this, context);
			// // One2OneTableManager.queryOne2OneTable(table, element, element.get(table.getId().getName()), this, context);
			// }
			return page;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return selectByLimit(context, table, queryRecord, startRow, count, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public IDBResultSet select(final ITableDBContext context, final Table table, IDBFilter filter,
			int pageRowCount, int pageIndex) throws Throwable {
		return selectBy(context, table, filter, pageRowCount, pageIndex, null);
	}
	/**
	 * @param table
	 * @param filter
	 * @param pageRowCount
	 * @param pageIndex
	 * @param context
	 * @return Page
	 * @throws Throwable
	 */
	protected IDBResultSet selectBy(final ITableDBContext context, final Table table, IDBFilter filter,
			int pageRowCount, int pageIndex, 
			int... args) throws Throwable {
		try {
			ISQLRecordFactory factory = getPageRecordFactory(context, table); //, DBManager.DBBATCHSESSION);
			IDBResultSet page = getSQLRender().selectBy(context, table, filter, factory, pageRowCount, pageIndex);
			factory = null;
			// for (Iterator<?> iter = page.getData().iterator();
			// iter.hasNext();) {
			// IRecord form = (IRecord) iter.next();
			// if (form == null) {
			// continue;
			// }
			// MappingTableManager.queryMappingTable(table, form, form.get(table.getId().getName()), this, context);
			// // One2OneTableManager.queryOne2OneTable(table, element, element.get(table.getId().getName()), this, context);
			// }
			return page;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (isDeep(args))
					return selectBy(context, table, filter, pageRowCount, pageIndex, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public void loopBy(final ITableDBContext context, final Table table, IDBFilter filter,
			final IDBRecordCallBack callback)
			throws Throwable {
		loopBy(context, table, filter, callback, null);
	}
	/**
	 * @param context
	 * @param table
	 * @param filter
	 * @param callback
	 * @param args
	 * @throws Throwable
	 */
	public void loopBy(final ITableDBContext context, final Table table, IDBFilter filter,
			final IDBRecordCallBack callback, int... args)
			throws Throwable {
		try {
			ISQLRecordFactory factory = getPageRecordFactory(context, table);
			getSQLRender().loopBy(context, table, filter, factory, callback);
			factory = null;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (args != null && isDeep(args))
					loopBy(context, table, filter, callback, getDeep(args));
			}
			throw ex;
		}
	}
	@Override
	public IDBResultSet select(final ITableDBContext context, final Table table, IDBRecord queryRecord,
			final int maxPageRecordCount, final int currPageNO) throws Throwable {
		return selectBy(context, table, queryRecord, maxPageRecordCount, currPageNO, null);
	}
	/**
	 * ### 对于Martin Fowler大师思想的最佳实践方法 ##
	 * <p/>
	 * {@link #getPageRecordFactory(IContext, Table)}
	 * <p/>
	 * {@link haiyan.orm.database.sql.TableDBTemplate}.getBaseSelectSQL(IContext, Table)
	 * {@link haiyan.orm.database.sql.SQLRender}.deal(Table, Object[])
	 * <p/>
	 * extends
	 * <p/>
	 * {@link haiyan.orm.database.ITemplate}
	 * <p/>
	 * 
	 * @param table
	 * @param queryRecord
	 * @param maxPageRecordCount
	 * @param currPageNO
	 * @param context
	 * @return Page 返回数据集对象(外面在封装成一个Document对象即可以作为单据领域模型的数据Model)
	 * @throws Throwable
	 */
	protected IDBResultSet selectBy(final ITableDBContext context, final Table table, IDBRecord queryRecord,
			final int maxPageRecordCount, final int currPageNO, int... args) throws Throwable {
		try {
			ISQLRecordFactory factory = getPageRecordFactory(context, table); //, DBManager.DBBATCHSESSION);
			IDBResultSet page = getSQLRender().selectBy(context, table, queryRecord, factory, // SQLDBTemplate.findPageByForm
					maxPageRecordCount, currPageNO);
			factory = null;
			// for (Iterator<?> iter = page.getData().iterator();
			// iter.hasNext();) {
			// IRecord form = (IRecord) iter.next();
			// if (form == null) {
			// continue;
			// }
			// MappingTableManager.queryMappingTable(table, form, form.get(table.getId().getName()), this, context);
			// // One2OneTableManager.queryOne2OneTable(table, element, element.getParameter(table.getId().getName()), this, context);
			// }
			return page;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (args != null && isDeep(args))
					return selectBy(context, table, queryRecord, maxPageRecordCount, currPageNO, getDeep(args));
			}
			throw ex;
		}
		// return findBy(table, queryForm, null, null, null, maxPageRecordCount,
		// currPageNO, context);
	}
	@Override
	public void loopBy(final ITableDBContext context, final Table table, IDBRecord record,
			final IDBRecordCallBack callback)
			throws Throwable {
		loopBy(context, table, record, callback, null);
	}
	/**
	 * @param table
	 * @param record
	 * @param callback
	 * @param context
	 * @param args
	 * @throws Throwable
	 */
	public void loopBy(final ITableDBContext context, final Table table, IDBRecord record,
			final IDBRecordCallBack callback, int... args)
			throws Throwable {
		try {
			ISQLRecordFactory factory = getPageRecordFactory(context, table);
			getSQLRender().loopBy(context, table, record, factory, callback);
			factory = null;
		} catch (SQLException ex) {
			if (isDBCorrect(ex)) {
				this.tableErrHandle(getSQLRender().getSQL());
				if (args != null && isDeep(args))
					loopBy(context, table, record, callback, getDeep(args));
			}
			throw ex;
		}
		// return findBy(table, queryForm, null, null, null, maxPageRecordCount,
		// currPageNO, context);
	}
	/**
	 * ### 对象-关系结构模式、元数据映射模式（关系结构及元数据映射工厂）###
	 *  
	 * @param context
	 * @param table
	 * @return
	 */
	protected ISQLRecordFactory getPageRecordFactory(
			final ITableDBContext context, final Table table) {
		// TODO 循环引用 要检查是否存在内存泄露
		ISQLRecordFactory factory = new ISQLRecordFactory() {
			private static final long serialVersionUID = 1L;
			public Object getRecord(ResultSet rs) throws Throwable {
//				if (rs.getRow()==1)
					return getRecordByRow(context, table, rs, IDBRecordCacheManager.CONTEXT_SESSION);
//				return getRecordByRow(context, table, rs, IDBRecordCacheManager.NO_SESSION);
			}
			public String getTableName() {
				return table.getName();
			}
			public String getIDName() {
				return table.getId().getName();
			}
			// public void addCacheID(String ID) throws Throwable {
			// getDBM(context).getClear().addCacheID(ID);
			// }
		};
		return factory;
	}
//	/**
//	 * @param context
//	 * @param table
//	 * @param rs
//	 * @return
//	 * @throws Throwable
//	 */
//	protected IDBRecord getRecordByRow(final ITableDBContext context,
//            final Table table, final ResultSet rs) throws Throwable {
//	    return getRecordByRow(context, table, rs, IDBRecordCacheManager.CONTEXT_SESSION);
//	}
	/**
	 * 对象-关系结构模式
	 * 
	 * @param context
	 * @param table
	 * @param rs
	 * @param type
	 * @return
	 * @throws Throwable
	 */
	protected IDBRecord getRecordByRow(final ITableDBContext context,
			final Table table, final ResultSet rs, short type) throws Throwable {
		IDBRecord record = null;
		record = getCache(context, table, rs.getString(1), type); // 用Cache可能还会进行网络请求，除非是本地Cache否则不要这么做
		if (record!=null) {
			return record;
		}
		record = this.createRecord();
		final SQLDBTypeConvert dataTrans = new SQLDBTypeConvert();
		try {
			// NOTICE 对象-关系结构模式
			TableDBTemplate template = new TableDBTemplate() {
				@Override // 标识域
				public void dealWithIdField(Table table, int index,
						Object[] globalVars) throws Throwable {
					ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord record = (IDBRecord) globalVars[1];
					String id = rs.getString(1);
					record.set(table.getId().getName(), (id == null ? null : id.toString()));
				}
				@Override // 依赖映射
				public void dealWithReferenceTable(Table table,
						int tableIndex, Field field, Object[] globalVars) {
				}
				@Override // 外键映射
				public int dealWithDisplayField(Table table, int tableIndex,
						int index, Field mainField, Object[] globalVars)
						throws Throwable {
					ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord record = (IDBRecord) globalVars[1];
					Table refTable = ConfigUtil.getTable(mainField.getReferenceTable());
					String[] disFieldNames = ConfigUtil.getDisplayRefFields(mainField);
					String finalValue = "";
					for (int i = 0; i < disFieldNames.length; i++) {
						Field displayField = ConfigUtil.getFieldByName(refTable, disFieldNames[i]);
						if (i != 0)
							finalValue += " ";
						String disRefTable = displayField.getReferenceTable();
						if (disRefTable != null) {
							String displayFieldName = NamingUtil.getDisplayFieldAlias(displayField);
							finalValue += record.get(displayFieldName);
							index++;
						} else {
							finalValue += SQLDBTypeConvert.getValue(context, rs, getClear(), (index++), table, displayField);
						}
					}
					record.set(NamingUtil.getDisplayFieldAlias(mainField.getName(), disFieldNames[0]), finalValue);
					return index;
				}
				@Override // 外键关联映射
				public void dealWithAssociatedField(Table table,
						int tableIndex, int index, Field mainField,
						Field associatedField, Object[] globalVars)
						throws Throwable {
					ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord record = (IDBRecord) globalVars[1];
					Table refTable = ConfigUtil.getTable(mainField.getReferenceTable());
					Field displayField = ConfigUtil.getFieldByName(refTable, ConfigUtil.getDisplayRefFields(mainField)[0]);
					Object value = SQLDBTypeConvert.getValue(context, rs, getClear(), index, table, displayField);
					record.set(associatedField.getName(), value);
				}
				@Override // 嵌入值
				public void dealWithComputeField(Table table, int index,
						Field field, Object[] globalVars) throws Throwable {
					ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord record = (IDBRecord) globalVars[1];
					Object value = SQLDBTypeConvert.getValue(context, rs, getClear(), index, table, field);
					record.set(field.getName(), value);
				}
				@Override // 普通映射
				public void dealWithGeneralField(Table table, int index,
						Field field, Object[] globalVars) throws Throwable {
					ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord record = (IDBRecord) globalVars[1];
					Object value = SQLDBTypeConvert.getValue(context, rs, getClear(), index, table, field);
					record.set(field.getName(), value);
					if (field.getJavaType().equals(AbstractCommonFieldJavaTypeType.PASSWORD)) {
						record.set(NamingUtil.getDisplayFieldAlias(field.getName(), DataConstant.PASSWORD_DISNAME), DataConstant.PASSWORD_DISVALUE);
					} else if (field.getOption() != null && field.getOptionCount() > 0) {
						record.set(NamingUtil.getDisplayFieldAlias(field.getName(), DataConstant.OPTION_DISNAME), dataTrans.getOptionNameByValue(context, table, field, VarUtil.toString(value)));
					}
				}
				@Override // 延迟加载
				public void dealWithLazyLoadField(Table table, Field field,
						Object[] globalVars) throws Throwable {
					// ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord record = (IDBRecord) globalVars[1];
					ITableDBContext subContext = context;
					try {
						if (!StringUtil.isBlankOrNull(field.getDsn())) { // 分布式应用不用context的dbm
							subContext = TableDBContextFactory.createDBContext((TableDBContext)context);
							subContext.setAttribute(DataConstant.PARAM_DSN, field.getDsn());
						}
						String value = null;
//						// ////////////////////////////////////////////////////////////
//						if (field.getOne2oneTable() != null) {
//							form = DBOne2OneManager.queryOne2OneTable(table, field, form, subContext);
//						}
						value = (String)record.get(field.getName());
						if (StringUtil.isBlankOrNull(value))
							return;
						String resTableName = field.getReferenceTable();
						if (StringUtil.isBlankOrNull(resTableName))
							return;
						String[] refShowRefFlds = ConfigUtil.getDisplayRefFields(field);
						if (refShowRefFlds.length == 0)
							return;
						String resDisplayName = refShowRefFlds[0];
						if (StringUtil.isBlankOrNull(resDisplayName))
							return;
						if (value.endsWith(DataConstant.SQL_VALUE_DIM))
							value = value.substring(0, value.length() - 1);
						if (value.startsWith(DataConstant.SQL_VALUE_DIM))
							value = value.substring(1);
						String resShowValue = "";
						String loadType = field.getLoadType();
						String[] associatedFields = ConfigUtil.getAssociatedFields(field);
						Table resTable = ConfigUtil.getTable(resTableName);
						if (field.getMultipleSelect()) {
							DebugUtil.debug(">Mapping.findByFilter: name=" + field.getName() + ",value=" + value + ",loadType="+loadType);
							if (value != null) {
								String[] array = StringUtil.split(value, ",");
								String l = field.getJavaType() == AbstractCommonFieldJavaTypeType.STRING?"'":"";
								value = StringUtil.joinSQL(array, ",", l);
								IDBRecord resForm = null;
								Field assoField = null;
								for (String id:array) {
									resForm = getDBM(subContext).select(subContext, resTable, id); // CONTEXT_SESSION
									if (resForm==null)
										continue;
									// 20081111 zhouxw
									if (resShowValue.length() > 0)
										resShowValue += DataConstant.SQL_VALUE_DIM;
									resShowValue += resForm.get(resDisplayName);
									for (int j = 0; j < associatedFields.length; j++) {
										assoField = ConfigUtil.getFieldByName(table, associatedFields[j]);
										record.set(associatedFields[j], resForm.get(ConfigUtil.getDisplayRefFields(assoField)[0]));
									}
								}
							}
//							IDBFilter filter = new SQLDBFilter(" and t_1." + resFieldName + " in (" + value + ")");
//							ArrayList<IRecord> refData = getDBM(context)
//									.findByFilter(resTable, filter, Page.MAXCOUNTPERQUERY, 1, subContext).getData();
//							IRecord resForm = null;
//							Field assoField = null;
//							String[] associatedFields = ConfigUtil.getAssociatedFields(field);
//							for (int i = 0; i < refData.size(); i++) {
//								resForm = refData.get(i); // 20081111 zhouxw
//								if (resShowValue.length() > 0)
//									resShowValue += DataConstant.SQL_VALUE_DIM;
//								resShowValue += resForm.get(resDisplayName);
//								for (int j = 0; j < associatedFields.length; j++) {
//									assoField = ConfigUtil.getFieldByName(table, associatedFields[j]);
//									form.set(associatedFields[j], resForm.get(ConfigUtil.getDisplayRefFields(assoField)[0]));
//								}
//							}
//							refData.clear();
						} else {
							DebugUtil.debug(">Mapping.findByPK: name=" + field.getName() + ",value=" + value + ",loadType="+loadType);
							IDBRecord resForm = null;
							resForm = getDBM(subContext).select(subContext, resTable, value); // CONTEXT_SESSION
							if (resForm == null)
								return;
							resShowValue = (String)resForm.get(resDisplayName);
							Field assoField = null;
							for (int i = 0; i < associatedFields.length; i++) {
								assoField = ConfigUtil.getFieldByName(table, associatedFields[i]);
								record.set(associatedFields[i], resForm.get(ConfigUtil.getDisplayRefFields(assoField)[0]));
							}
						}
						record.set(NamingUtil.getDisplayFieldAlias(field.getName(), resDisplayName), resShowValue);
					} finally {
						if (!StringUtil.isBlankOrNull(field.getDsn())) {
							subContext.close();
						}
					}
				}
				@Override // 关联表映射
				public void dealWithMappingField(Table table, Field field,
						Object[] globalVars) throws Throwable {
					ResultSet rs = (ResultSet) globalVars[0];
					IDBRecord form = (IDBRecord) globalVars[1];
					ITableDBContext subContext = context;
					try {
						if (!StringUtil.isBlankOrNull(field.getDsn())) {
							// 分布式应用不用context的dbm
							subContext = TableDBContextFactory.createDBContext((TableDBContext)context);
							subContext.setAttribute(DataConstant.PARAM_DSN, field.getDsn());
						}
						String id = rs.getString(1);
						SQLMappingDBManager.queryMappingTable(subContext, table, field, form, id);
					} finally {
						if (!StringUtil.isBlankOrNull(field.getDsn()))
							subContext.close();
					}
				}
			};
			// 配置模板处理（对象-关系元数据映射处理）
			template.deal(table, new Object[] { rs, record });
//			// 更新缓存
//			record.setStatus(IDBRecord.UPDATE);
			// record.clearDirty();
			// flush源数据集
			flushOreign(context, table, record, type);
			// 更新到缓存框架
			updateCache(context, table, record, type);
			// 结果插件处理 一个事务一个插件
//			if (renderUtil == null)
//				renderUtil = new RenderUtil();
//			if (renderUtil.isShowable(table, record, context))
//				return record;
//			return null;
			return record;
		} finally {
			dataTrans.clearCache();
		}
	}
	// ------------------------------------------------------ Flag ------------------------------------------------------ //
	protected void flushOreign(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable {
		record.flushOreign();
	}
	/**
	 * 是否在纠错范围内
	 * 
	 * @param args
	 * @return boolean
	 */
	protected boolean isDeep(int[] args) {
		if (deep <= MAX_DEEP) {
			deep++;
			return true;
		}
		deep = 0;
		return false;
	}
	/**
	 * 纠错层次
	 * 
	 * @param args
	 * @return int[]
	 */
	protected int[] getDeep(int[] args) {
		if (args == null || args.length == 0) {
			deep = 1;
			return new int[] { 1 };
		}
		return args;
	}
	// ------------------------------------------------------ DBCorrect ------------------------------------------------------ //
	/**
	 * @param ex
	 * @return boolean
	 */
	@Override
	public boolean isDBCorrect(Throwable e) {
		if (e == null)
			return false;
		if (!(e instanceof SQLException))
			return false;
		SQLException ex = (SQLException)e;
		DebugUtil.error(">--< dbm.dbcorrect.errorCode=" + ex.getErrorCode()+",DSN="+getDSN(), ex);
		if (ex.getErrorCode() == 942 || ex.getErrorCode() == 904)
			return true;
		return false;
	}
	/**
	 * @param asExistTbl
	 * @param asFlds
	 * @param asDDLs
	 * @throws Throwable
	 */
	private void pAppendFldSQL(Ref<String[]> asTbls, Ref<String[]> asFlds,
			Ref<String[]> asDDLs) throws Throwable {
		DebugUtil.debug(">pAppendFldSQL()");
		if (asTbls.refValue.length == 0)
			return;
		// BillMetaTableCollection metaTables = oMetaData.MetaTables;
		Table tbl = null;
		for (int l = 0; l < asTbls.refValue.length; l++) {
			String tableName = asTbls.refValue[l];
			DebugUtil.debug(">pAppendFldSQL.tableName=" + tableName);
			tbl = ConfigUtil.getTable(tableName);
			if (tbl == null)
				continue;
			// if ( hasTable(metaTbl, asTbls.refValue[l])) {
			Ref<String> asFld = new Ref<String>(asFlds.refValue[l]);
			asDDLs.refValue[l] = this.pCheckTableFields(tbl, asFld);
			asFlds.refValue[l] = asFld.refValue;
			if (!StringUtil.isBlankOrNull(asDDLs.refValue[l]))
				break;
			// }
		}
		// asFlds.setValue(asTmpFld);
		// asSqls.setValue(asRstSQL);
	}
	/**
	 * @param asTblName
	 * @param asSQLs
	 * @return int
	 * @throws Throwable
	 */
	private int pCreateTableSQL(Ref<String[]> asTblName, Ref<String[]> asSQLs)
			throws Throwable {
		DebugUtil.debug(">pCreateTableSQL()");
		ArrayList<String> colTabs = new ArrayList<String>();
		ArrayList<String> colSqls = new ArrayList<String>();
		boolean bShouldCreate = false;
		if (asTblName.refValue != null)
			for (int l = 0; l < asTblName.refValue.length; l++) {
				// 已经存在
				if (colTabs.contains(asTblName.refValue[l])) {
					// GoTo lbl_NextFor
					continue;
				}
				Table tbl = null; // 如果不存在则找realTable
				tbl = ConfigUtil.getTable(asTblName.refValue[l], true, true);
				if (tbl == null)
					continue;
				// bShouldCreate = this.pCreateTableSQL_pCreateMainSQL(tbl, colTabs, colSqls);
				if (!colTabs.contains(tbl.getName())) {
					colTabs.add(tbl.getName());
					colSqls.add(this.getCreateTableSQL(tbl));
					bShouldCreate = true;
				}
				// return false;
				if (!bShouldCreate)
					if (asTblName.refValue != null)
						throw new Warning("exist table:" + StringUtil.join(asTblName.refValue, ";", ""));
					else
						throw new Warning("table name lost.");
			}
		// this.pCreateTableSQL_TakeIndexSQL(colTabs, colSqls);
		// this.pCreateTableSQL_Convert(colTabs, colSqls, asTblName, asSQLs);
		asTblName.setValue(colTabs.toArray(new String[0]));
		asSQLs.setValue(colSqls.toArray(new String[0]));
		return asTblName.refValue.length;
	}
	/**
	 * @param oSerTable
	 * @param sFlds
	 * @return String
	 * @throws Throwable
	 */
	private String pCheckTableFields(Table oSerTable, Ref<String> sFlds)
			throws Throwable {
		// DebugUtil.debug(">checkTableFields("
		// + ConfigUtil.getRealTableName(oSerTable) + ")");
		if (!(sFlds.refValue.startsWith("|") && sFlds.refValue.endsWith("|")))
			sFlds.refValue = "|" + sFlds.refValue.toUpperCase() + "|";
		int len = sFlds.refValue.length();
		if (len >= 3 && "|".equals(sFlds.refValue.substring(len - 2, len - 1)))
			sFlds.refValue = sFlds.refValue.substring(0, len - 1);
		if (len >= 3 && "|".equals(sFlds.refValue.substring(1, 2)))
			sFlds.refValue = sFlds.refValue.substring(1);
		// DebugUtil.debug(">sFlds.refValue=" + sFlds.refValue);
		ArrayList<String> notExistCols = new ArrayList<String>();
		String sRet = "";
		String sDBColumn = null;
		// judge owner table's field and extend realtable's field
		// Iterator<?> iter = ConfigUtil.getTables().keySet().iterator();
		// while (iter.hasNext()) {
		for (String sKey : ConfigUtil.getAllTableKeys()) {
			Table tbl = ConfigUtil.getTable(sKey);
			if (oSerTable.getName().equals(tbl.getRealName()) || oSerTable.getName().equals(tbl.getName())) {
				for (Field fld : tbl.getField()) {
					if (fld.isVisual())
						continue;
					sDBColumn = fld.getName().toUpperCase();
					if (sFlds.refValue.indexOf("|" + sDBColumn + "|") == -1 && !notExistCols.contains(sDBColumn)) {
						sRet += "ALTER TABLE " + oSerTable.getName() + " add " + this.genFieldSQL(fld) + DataConstant.SQL_DIM;
						notExistCols.add(sDBColumn);
					}
				}
			}
		}
		String sTableName = getDBName(oSerTable);
		DebugUtil.debug(">exist fields[" + sTableName + "]:" + sFlds.refValue);
		if (notExistCols.size() > 0)
			DebugUtil.debug(">not exist fields["+ sTableName+"]:"
				+ StringUtil.join(notExistCols.toArray(new String[0]), "|", ""));
		// colDBColumnName.clear();
		return sRet;
	}
	/**
	 * 获取字段
	 * 
	 * @param asTable
	 *            存在的表名
	 * @param asFlds
	 *            存在的字段,用"|"相隔
	 * @throws Throwable
	 */
	private void pGetDBTableFields(Ref<String[]> asTable, Ref<String[]> asFlds)
			throws Throwable {
		// DebugUtil.debug(">pGetTableFields()");
		asFlds.refValue = new String[asTable.refValue.length];
		// String[] asEFilds = new String[asTable.refValue.length];
		String sql = null;
		for (int l = 0; l < asTable.refValue.length; l++) {
			// String tableName = ;
			sql = "select * from " + asTable.refValue[l] + " where 1=2 ";
			try {
				// 先用sql判断字段
				SQLDBHelper.getResultArray(sql, 0, this.getConnection());
			} catch (Throwable ex) {
				DebugUtil.error(ex);
			}
			ResultSet rst = null;
			Statement stat = null;
			ResultSetMetaData rstMetaData = null;
			try {
				DebugUtil.info(">pGetTableFields.SQL=" + sql);
				stat = this.getConnection().createStatement();
				rst = stat.executeQuery(sql);
				rstMetaData = rst.getMetaData();
				String sFlds = "";
				for (int i = 1; i <= rstMetaData.getColumnCount(); i++) {
					// int columnType = rstMetaData.getColumnType(i);
					String columnName = rstMetaData.getColumnName(i);
					sFlds += columnName + "|";
				}
				// asEFilds[l] = sFlds;
				asFlds.refValue[l] = sFlds;
			} finally {
				CloseUtil.close(rst);
				CloseUtil.close(stat);
			}
		}
		// asFlds.refValue = asEFilds;
	}
//	/**
//	 * 追加filter，自定义条件
//	 * 
//	 * @param context
//	 * @param filterByID
//	 *            自定义过滤的方案ID
//	 * @throws Throwable
//	 */
//	public String getExtendFilterBy(ITableDBContext context, String filterByID)
//			throws Throwable {
//		// __filterBy sysquerycondition id
//		// String filterBy = context.getParameter("__filterBy");
//		if (StringUtil.isBlankOrNull(filterByID))
//			return "";
//		IDBRecord mainRecord = this.select(context, ConfigUtil.getTable("SYSQUERYCONDITION"), filterByID);
//		if (mainRecord != null) {
//			Table mainTbl = ConfigUtil.getTable((String)mainRecord.get("TABLENAME"));
//			return getExtendFilterBy(context, mainTbl, mainRecord, filterByID);
//		} else
//			return "";
//	}
//	/**
//	 * 追加filter，自定义条件
//	 * 
//	 * @param context
//	 * @param mainTbl
//	 *            要过滤的表名
//	 * @param headRecord
//	 *            自定义过滤的配置头表表单
//	 * @param filterByID
//	 *            自定义过滤的方案ID
//	 * @return String
//	 * @throws Throwable
//	 */
//	public String getExtendFilterBy(ITableDBContext context, Table mainTbl, IDBRecord headRecord, String filterByID) throws Throwable {
//		IDBRecord condRecord = this.createRecord();
//		condRecord.set("HEADID", filterByID);
//		IDBResultSet condPg = this.select(context, ConfigUtil.getTable("SYSQUERYCONDITION_D"), condRecord, DBPage.MAXCOUNTPERQUERY, 1);
//		if (condPg.getTotalRecordCount() == 0)
//			return "";
//		String filterSql = "";
//		for (int i = 0; i < condPg.getTotalRecordCount(); i++) {
//			IDBRecord record = condPg.getRecord(i);
//			String COLUMNNAME = (String)record.get("COLUMNNAME"); // 字段名
//			String RELATION = (String)record.get("RELATION"); // 连接符
//			String LEFT_P = (String)record.get("LEFT_P"); 
//			String CONDITIONS = (String)record.get("CONDITIONS"); // 运算符
//			String CONTENT = (String)record.get("CONTENT"); // 值
//			String RIGHT_P = (String)record.get("RIGHT_P");
//			// NOTICE 防止SQL注入
//			if (CONTENT!=null) {
//				CONTENT = StringUtil.unSqlInjection(CONTENT);
//			}
//			if (RELATION != null) {
////				if (RELATION.length()>18) 
////					throw new Warning("非法参数COLUMNNAME:"+RELATION);
//				RELATION = StringUtil.unSqlInjection(RELATION);
//				filterSql += " " + RELATION + " ";
//			}
//			if (LEFT_P != null) {
////				if (LEFT_P.length()>18) 
////					throw new Warning("非法参数COLUMNNAME:"+LEFT_P);
//				LEFT_P = StringUtil.unSqlInjection(LEFT_P);
//				filterSql += " " + LEFT_P + " ";
//			}
//			if (COLUMNNAME != null) {
////				if (COLUMNNAME.length()>18) 
////					throw new Warning("非法参数COLUMNNAME:"+COLUMNNAME);
//				COLUMNNAME = StringUtil.unSqlInjection(COLUMNNAME);
//				int s = COLUMNNAME.lastIndexOf("(");
//				int e = COLUMNNAME.lastIndexOf(")");
//				// String fieldName = COLUMNNAME.substring(0,s);
//				filterSql += " " + COLUMNNAME.substring(s + 1, e) + " ";
//			}
//			if (CONDITIONS != null) {
////				if (CONDITIONS.length()>18) 
////					throw new Warning("非法参数CONDITIONS:"+CONDITIONS);
//				CONDITIONS = StringUtil.unSqlInjection(CONDITIONS);
//				filterSql += " " + CONDITIONS + " ";
//			}
//			if (CONTENT != null
//				&& CONDITIONS.indexOf("is not null") == -1
//				&& CONDITIONS.indexOf("is null") == -1) {
//				int s = COLUMNNAME.lastIndexOf(".");
//				int e = COLUMNNAME.lastIndexOf(")");
//				int f = COLUMNNAME.lastIndexOf("(");
//				String startIndex = COLUMNNAME.substring(f + 1, s);
//				String refFldName = COLUMNNAME.substring(s + 1, e);
//				Table refTbl = null;
//				int ref_count = 1;
//				for (Field fld : mainTbl.getField())
//					if (fld.getReferenceTable() != null) {
//						ref_count++;
//						if (("t_" + ref_count).equals(startIndex)) {
//							refTbl = ConfigUtil.getTable(fld.getReferenceTable());
//							break;
//						}
//					}
//				// if (refTbl == null)// debug 20081104 by zhouxw
//				// refTbl = mainTbl;
//				Field refFld = null;
//				if (refTbl != null)
//					refFld = ConfigUtil.getFieldByName(refTbl, refFldName, true);
//				else
//					refFld = ConfigUtil.getFieldByName(mainTbl, refFldName, true);
//				if (refFld == null)
//					continue;
//				
//				if (refFld.getJavaType() == AbstractCommonFieldJavaTypeType.BIGDECIMAL)
//					filterSql += " " + CONTENT + " ";
//				else {
//					if (refFld.getJavaType() == AbstractCommonFieldJavaTypeType.STRING
//						&& (record.get("CONDITIONS").toString().indexOf("like") != -1 
//						|| record.get("CONDITIONS").toString().indexOf("not like") != -1))
//						filterSql += " '%" + CONTENT + "%' ";
//					else
//						filterSql += " '" + CONTENT + "' ";
//				}
//			}
//			if (RIGHT_P != null)
//				filterSql += " " + RIGHT_P + " ";
//		}
//		return filterSql;
//	}
//	/**
//	 * 追加filter，快速查询语句
//	 * 
//	 * TODO 名称应该叫 getQuickFilterBy
//	 * 
//	 * @param context
//	 * @param mainTbl
//	 *            要过滤的主表名
//	 * @param filterValue
//	 *            快速查询条件
//	 * @return String
//	 * @throws Throwable
//	 */
//	public String getExtendFilterBy(ITableDBContext context, Table mainTbl, String filterValue) throws Throwable {
//		// filterValue
//		if (StringUtil.isBlankOrNull(filterValue))
//			return "";
//		// NOTICE 防止SQL注入
//		//filterValue = filterValue.substring(0, Math.min(filterValue.length(), 12));
//		filterValue = StringUtil.unSqlInjection(filterValue);
//		String[][] colOptions = SQLDBHelper.getIndexFields(mainTbl, null);
//		if (colOptions.length == 0)
//			return "";
//		int count = 0;
//		String filterSql = " and (";
//		for (String[] colOption : colOptions) {
//			String COLUMNNAME = " " + colOption[0];
//			{ // t_n.COLUMNNAME
//				int s = COLUMNNAME.lastIndexOf(".");
//				// int e = COLUMNNAME.lastIndexOf(")");
//				// int f = COLUMNNAME.lastIndexOf("(");
//				String startIndex = COLUMNNAME.substring(0, s).trim();
//				String refFldName = COLUMNNAME.substring(s + 1).trim();
//				Table refTbl = null;
//				int ref_count = 1;
//				for (Field fld : mainTbl.getField())
//					if (fld.getReferenceTable() != null) {
//						ref_count++;
//						if (("t_" + ref_count).equals(startIndex)) {
//							refTbl = ConfigUtil.getTable(fld.getReferenceTable());
//							break;
//						}
//					}
//				// if (refTbl == null)// debug 20081104 by zhouxw
//				// refTbl = mainTbl;
//				Field refFld = null;
//				if (refTbl != null)// 联合表字段
//					refFld = ConfigUtil.getFieldByName(refTbl, refFldName, true);
//				else // 本表字段
//					refFld = ConfigUtil.getFieldByName(mainTbl, refFldName, true);
//				if (refFld == null)
//					continue;
//				if ("HYVERSION".equalsIgnoreCase(refFld.getName()))
//					continue;
//				if (refFld.getQueryCondition()!=null 
//						&& refFld.getQueryCondition().getType()==QueryConditionTypeType.NONE)
//					continue;
//				// TODO 暂不考虑BLOB
//				if (refFld != null
//					&& (refFld.getJavaType() == AbstractCommonFieldJavaTypeType.BLOB 
//					 || refFld.getJavaType() == AbstractCommonFieldJavaTypeType.DBBLOB
//					 || refFld.getJavaType() == AbstractCommonFieldJavaTypeType.DBCLOB))
//					continue;
//				// boolean hasFilter = false;
//				if (refFld.getOptionCount() > 0) {
//					for (Option o : refFld.getOption()) {
//						if (o.getDisplayName().toLowerCase().indexOf(filterValue.toLowerCase()) >= 0) {
//							if (count++ > 0)
//								filterSql += " or ";
//							filterSql += COLUMNNAME;
//							if (refFld.getJavaType() == AbstractCommonFieldJavaTypeType.BIGDECIMAL)
//								filterSql += "=" + o.getValue() + " ";
//							else
//								filterSql += " like '" + o.getValue() + "' ";
//						}
//					}
//				}
//				// if (refFld==null)
//				if (refFld.getJavaType() == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
//					if (StringUtil.isNumeric(filterValue)) {
//						if (count++ > 0)
//							filterSql += " or ";
//						filterSql += COLUMNNAME;
//						filterSql += "=" + filterValue + " ";
//					}
//				} else {
//					if (count++ > 0)
//						filterSql += " or ";
//					filterSql += COLUMNNAME;
//					if (refFld.getJavaType() == AbstractCommonFieldJavaTypeType.STRING) {
//						filterSql += " like '%" + filterValue + "%' ";
//					} else {
//						filterSql += " like '" + filterValue + "' ";
//					}
//					// if (refTbl == null && refFld.getJavaType() == AbstractCommonFieldJavaTypeType.STRING
//					// && (refFld.getQueryCondition() == null 
//					// || refFld.getQueryCondition().getType() == QueryConditionTypeType.BLURMATCHING))
//					// filterSql += " like '%" + filterValue + "%' ";
//					// else {
//					// filterSql += " like '" + filterValue + "' ";
//					// }
//				}
//			}
//		}
//		filterSql += ")";
//		//
//		return filterSql;
//	}
	// ---------------------------------------------------------------------------------------------------------------------- //
	/**
	 * 错误判断
	 * 
	 * @param sql
	 * @throws Throwable
	 */
	public void tableErrHandle(String sql) throws Throwable {
		if ("0".equals(PropUtil.getProperty("TABLE_ERR_FIXED")))
			return;
		DebugUtil.info(">tableErrHandle:" + sql);
		String[] regTables = SQLRegUtil.getTableFromSQL(sql);
		this.tableErrHandle(regTables, true);
	}
	/**
	 * 错误判断
	 * 
	 * @param regTables
	 * @param checkTbl
	 * @throws Throwable
	 */
	public void tableErrHandle(String[] regTables, boolean checkTbl)
			throws Throwable {
		// 初始化变量
		Ref<String[]> asTable = new Ref<String[]>(regTables);
		Ref<String[]> asExistTbl = new Ref<String[]>();
		// 检查是否存在
		if (checkTbl)
			pValidateTables(asTable, asExistTbl);
		else
			asExistTbl = new Ref<String[]>(regTables);
		// DebugUtil.debug(">tableErrHandle.no exist:" + asTable.refValue.length);
		for (String tName : asTable.refValue)
			DebugUtil.debug(">tableErrHandle.not exist:" + tName);
		// DebugUtil.debug(">tableErrHandle.exist:" + asExistTbl.refValue.length);
		for (String tName : asExistTbl.refValue)
			DebugUtil.debug(">tableErrHandle.exist:" + tName);
		// 批量sql cache
		Ref<String[]> asSQLs = null;
		Ref<String[]> asFlds = new Ref<String[]>();
		// 创建表
		if (!initByConfig(asTable))
			if (asTable.refValue.length > 0) {
				asSQLs = new Ref<String[]>(new String[asTable.refValue.length]);
				this.pCreateTableSQL(asTable, asSQLs);
				for (int i = 0; i < asTable.refValue.length; i++) {
					String tableName = asTable.refValue[i]; // .getValue(i);
					if (!StringUtil.isBlankOrNull(tableName) && tableName.indexOf(" ") == -1 && !isExistTable(tableName)) {
						// this.pExecuteCreateTable(tableName, asSQLs.refValue[i]);
						String[] sqls = StringUtil.split(asSQLs.refValue[i], DataConstant.SQL_DIM);
						for (int j = 0; j < sqls.length; j++)
							SQLDBHelper.executeUpdate(sqls[j], this.getConnection(true));
					}
				}
			}
		// 追加字段
		if (!initByConfig(asExistTbl))
			if (asExistTbl.refValue.length > 0) {
				asSQLs = new Ref<String[]>(new String[asExistTbl.refValue.length]);
				this.pGetDBTableFields(asExistTbl, asFlds);
				this.pAppendFldSQL(asExistTbl, asFlds, asSQLs);
				for (int i = 0; i < asExistTbl.refValue.length; i++) {
					if (asSQLs.refValue.length > i && !StringUtil.isBlankOrNull(asSQLs.refValue[i])) {
						// this.pExecuteUpdateTable(asExistTbl.refValue[i], "ALTER TABLE " + asExistTbl.refValue[i] + asSQLs.refValue[i]);
						String[] sqls = StringUtil.split(asSQLs.refValue[i], DataConstant.SQL_DIM);
						for (int j = 0; j < sqls.length; j++)
							SQLDBHelper.executeUpdate(sqls[j], this.getConnection());
						// DBHelper.executeUpdate("ALTER TABLE " + asExistTbl.refValue[i] + asSQLs.refValue[i], this.getConnection());
					}
				}
			}
	}
	// 利用配置初始化
	private boolean initByConfig(Ref<String[]> asTable) {
		ArrayList<String> notTbl = new ArrayList<String>();
		for (String tblName : asTable.refValue) {
//			Table sysTable = ConfigUtil.getTable("SYS", false, true); // getNvlTable
//			if (sysTable != null) {
//				for (PluginInterceptor inter : sysTable.getPluginInterceptor()) {
//					if ("initDB".equalsIgnoreCase(inter.getPluginName()) || "__initDB".equalsIgnoreCase(inter.getPluginName())) {
//						this.executeUpdate(inter.getContent());
//					}
//				}
//			} // NOTICE 不能用sys不能判断是否成功创建当前的dbtable
			Table table = ConfigUtil.getTable(tblName, false, true); // getNvlTable
			if (table == null) {
				notTbl.add(tblName);
			} else {
				boolean inited = false;
				String sql;
				for (PluginInterceptor inter : table.getPluginInterceptor()) {
					if ("initDB".equalsIgnoreCase(inter.getPluginName())
						|| "__initDB".equalsIgnoreCase(inter.getPluginName())) {
						try {
							sql=inter.getContent();
							this.executeUpdate(sql, null);
							inited = true;
						} catch (Throwable ex) {
							// notTbl.add(tblName);
							DebugUtil.error(ex);
						}
					}
				}
				if (!inited)
					notTbl.add(tblName);
			}
		}
		asTable.setValue(notTbl.toArray(new String[0]));
		return notTbl.size() == 0;
	}
	/**
	 * 判断表是否存在
	 * 
	 * @param asTable
	 *            返回不存在的表名
	 * @param asExistTbl
	 *            返回存在的表名
	 * @throws Throwable
	 */
	private void pValidateTables(final Ref<String[]> asTable,
			final Ref<String[]> asExistTbl) throws Throwable {
		final ArrayList<String> existList = new ArrayList<String>();
		final ArrayList<String> notExistList = new ArrayList<String>();
		String sql = getValidateTablesSQL(asTable.refValue);
		if (sql != null) {
			DebugUtil.info(">pValidateTables.sql=" + sql);
			SQLDBHelper.getResultArray(sql, 1, this.getConnection(),
				new ISQLDBListener() {
					public void listen(ResultSet rs) throws Throwable {
						String tableName = rs.getString(1);
						existList.add(tableName.toUpperCase());
					}
				}
			);
		} else {
			HashMap<String, Serializable> map = SQLDBHelper.getTableMap(
					this.getConnection(), this.getConnection().getCatalog());
			for (String tableName : asTable.refValue) {
				if (map.get(tableName.toUpperCase()) != null)
					existList.add(tableName.toUpperCase());
			}
		}
		for (int i = 0; i < asTable.refValue.length; i++) {
			String srcName = asTable.refValue[i].toUpperCase();
			if (!existList.contains(srcName))
				notExistList.add(srcName);
		}
		asTable.refValue = (String[]) notExistList.toArray(new String[0]);
		asExistTbl.refValue = (String[]) existList.toArray(new String[0]);
		// asTable.setValue(notExistList.toArray(new String[0]));
		// asExistTbl.setValue(existList.toArray(new String[0]));
	}
	/**
	 * 表是否存在
	 * 
	 * @param tableNames "," 隔开的多张表名
	 * @return boolean
	 * @throws Throwable
	 */
	public boolean isExistTable(String tableNames) throws Throwable {
		// String sql=getSQLDBTemplate().getValidateTablesSQL(tableNames);
		String sql = this.getValidateTablesSQL(tableNames);
		if (sql != null) {
			DebugUtil.info(">isExistTable().SQL=" + sql);
			ResultSet rst = null;
			Statement stat = null;
			try {
				stat = this.getConnection().createStatement();
				rst = stat.executeQuery(sql);
				if (rst.next())
					return true;
			} finally {
				CloseUtil.close(rst);
				CloseUtil.close(stat);
			}
		} else {
			// Ref<String[]> asExistTbl = new Ref<String[]>(StringUtil.split(tableNames, ","));
			// ArrayList<String> existList = new ArrayList<String>();
			String[] tNames = StringUtil.split(tableNames, ",");
			// HashMap<String, ?> map = getExistsTables(existList, tNames);
			HashMap<String, Serializable> map = SQLDBHelper.getTableMap(this.getConnection(), this.getConnection().getCatalog());
			for (String bName : tNames)
				if (map.get(bName) == null)
					return false;
			return true;
		}
		return false;
	}
	/**
	 * 不进行数据库管理的字段
	 * 
	 * @param field
	 * @return boolean
	 */
	protected boolean isIgnoredField(Field field) {
		return field.isVisual();
	}
	protected String getDBName(AbstractField field) {
		return field.getName();
	}
	protected String getDBName(Table table) {
		return ConfigUtil.getDBName(table);
	}
	// ---------------------------------------------------------------------------------------------------------------------- //
	/**
	 * sql描绘器
	 * 
	 * @return ISQLRender
	 */
	public abstract ITableSQLRender getSQLRender();
	/**
	 * @return String
	 */
	public String getSQL() {
		return this.getSQLRender().getSQL();
	}
    public String getValidateTablesSQL(String[] tableNames) {
        String filter = "";
        int i = 0;
        for (; i < tableNames.length; i++) {
            if (i != 0)
                filter += " or ";
            filter += " UPPER(TABLE_NAME)='" + tableNames[i] + "' ";
        }
        if (i > 0)
            filter = " AND (" + filter + ")";
        return "SELECT TABLE_NAME FROM USER_ALL_TABLES WHERE 1=1 " + filter;
        // AS TNAME
    }
    public String getValidateTablesSQL(String tableNames) {
        String tableSql = StringUtil.replaceAll(tableNames, ",", "','");
        return "SELECT TABLE_NAME FROM USER_ALL_TABLES WHERE 1=1 AND TABLE_NAME IN ('"
                + tableSql + "')";
    }
    public String getCreateTableSQL(Table oSerTable) {
        ArrayList<String> sqls = new ArrayList<String>();
        ArrayList<String> sKeySQL = new ArrayList<String>();
        // int count = 0;
        // alter table users add constraint PK_users primary key(USERID);
        String tableName = getDBName(oSerTable);
        // tableName = this.genTableName(tableName);
        sKeySQL.add("alter table " + tableName + " add constraint PK_"
			+ tableName + " primary key (" + oSerTable.getId().getName()
			+ ")");
        String sFieldCreateSQL = this.genFieldSQL(oSerTable.getId()) + ",";
        for (int i = 0; i < oSerTable.getFieldCount(); i++) {
            Field field = oSerTable.getField(i);
            if (isIgnoredField(field))
                continue;
            sFieldCreateSQL += this.genFieldSQL(field) + ",";
        }
        sqls.add("CREATE TABLE "
                + tableName
                + "("
                + StringUtil.left(sFieldCreateSQL, sFieldCreateSQL.length() - 1)
                + ")");
        sqls.addAll(sKeySQL);
        String[] strArray = (String[]) (sqls.toArray(new String[0]));
        return StringUtil.join(strArray, DataConstant.SQL_DIM, "");
    }
	public static boolean isFormula(String param) {
		return param != null
				&& (param.startsWith("^") || param.startsWith("?")
				 || param.startsWith("=") || param.startsWith("$"));
	}
    public String genFieldSQL(AbstractField field) {
        AbstractCommonFieldJavaTypeType fldType = field.getJavaType();
        // boolean bDefaultValue = false;
        // DebugUtil.debug(">genFieldSQL" + (eDBType.get() == EnumDBType.edbtDB2));
        String generateSQL = this.genFieldName(field) + " " + this.genTypeSQL(field);
        if (field instanceof Id) {
            generateSQL += " not null";
        } else {
            // Field field = (Field) fld;
            String defValue = field.getDefaultValue();
            if (defValue != null) {
                if (!isFormula(defValue))
                    if (fldType == AbstractCommonFieldJavaTypeType.STRING || fldType == AbstractCommonFieldJavaTypeType.DATE) {
                        generateSQL += " default '" + defValue + "'";
                    } else {
                        if (StringUtil.isBlankOrNull(defValue)) {
                            // generateSQL = generateSQL;
                        } else {
                            generateSQL += " default " + defValue;
                        }
                    }
            } else {
                if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
                    generateSQL += " default -1";
                } else if (fldType == AbstractCommonFieldJavaTypeType.INTEGER) {
                    generateSQL += " default -1";
                }
            }
            if (!field.getNullAllowed()) {
                // if (generateSQL.indexOf(" default ") != -1)
                // generateSQL += " not null";
                // if (generateSQL.indexOf(" default ") == -1)
                // generateSQL += " default ''";
                generateSQL += " not null";
            }
        }
        return generateSQL;
    }
    public String genTypeSQL(AbstractField field) {
        AbstractCommonFieldJavaTypeType fldType = field.getJavaType();
        if (fldType == AbstractCommonFieldJavaTypeType.STRING) {
            long len = field.getLength() <= 0 ? 50 : field.getLength();
            if (len <= 100
                && (field.getJavaType()  == AbstractCommonFieldJavaTypeType.BLOB 
                || field.getJavaType() == AbstractCommonFieldJavaTypeType.DBBLOB))
                len = 255;
            return "VARCHAR2(" + len + ")";
        } else if (fldType == AbstractCommonFieldJavaTypeType.BLOB) {
            long len = field.getLength() <= 0 ? 255 : field.getLength();
            return "VARCHAR2(" + len + ")";
        } else if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
            long len = field.getLength() <= 0 ? 18 : field.getLength();
            if (field.hasMinFractionDigit())
                return "NUMBER(" + len + "," + field.getMinFractionDigit() + ")";
            else if (field.hasMaxFractionDigit())
                return "NUMBER(" + len + "," + field.getMaxFractionDigit() + ")";
            return "NUMBER";
        } else if (fldType == AbstractCommonFieldJavaTypeType.INTEGER) {
            return "NUMBER";
        } else if (fldType == AbstractCommonFieldJavaTypeType.DATE) {
            return "DATE";
        } else if (fldType == AbstractCommonFieldJavaTypeType.DBBLOB) {
            return "BLOB";
        } else if (fldType == AbstractCommonFieldJavaTypeType.DBCLOB) {
			return "TEXT";
		} else {
            throw new Warning("Unknown AbstractCommonFieldJavaTypeType=" + fldType);
        }
    }
    public String genFieldName(AbstractField field) {
        return field.getName();
    }
    public String SQLDateTimeFromStr(String gsCurTime) {
        return "TO_DATE('" + gsCurTime + "','yyyy-MM-dd HH24:MI:SSxFF')";
    }
    public String SQLCurrentTimeStamp(boolean stringType) {
        return stringType ? "TO_DATE(SYSDATE, 'yyyy-MM-dd HH24:MI:SSxFF')" : "SYSDATE";
    }
//	/**
//	 * 获取数据库字段名(有些数据库字段名带引号)
//	 * 
//	 * @param field
//	 * @return String
//	 */
//	public abstract String genFieldName(AbstractField field);
//	/**
//	 * 转换数据库合法时间
//	 * 
//	 * @param sCurTime
//	 * @return String
//	 */
//	public abstract String SQLDateTimeFromStr(String sCurTime);
//	/**
//	 * 获取数据库当前时间
//	 * 
//	 * @param stringType
//	 * @return String
//	 */
//	public abstract String SQLCurrentTimeStamp(boolean stringType);
//	/**
//	 * 获取数据库表DDL
//	 * 
//	 * @param oSerTable
//	 * @return String
//	 */
//	public abstract String getCreateTableSQL(Table oSerTable);
//	/**
//	 * 获取数据库字段DDL
//	 * 
//	 * @param field
//	 * @return String
//	 */
//	public abstract String genFieldSQL(AbstractField field);
//	/**
//	 * 获取数据库字段类型DDL
//	 * 
//	 * @param field
//	 * @return String
//	 */
//	public abstract String genTypeSQL(AbstractField field);
//	/**
//	 * 判断tableNames(xx,xx)在数据库系统表中是否存在
//	 * 
//	 * @param tableNames
//	 * @return String
//	 */
//	protected abstract String getValidateTablesSQL(String tableNames);
//	/**
//	 * 判断tableNames[xx,xx]在数据库系统表中是否存在
//	 * 
//	 * @param tableNames
//	 * @return String
//	 */
//	protected abstract String getValidateTablesSQL(String[] tableNames);

}
