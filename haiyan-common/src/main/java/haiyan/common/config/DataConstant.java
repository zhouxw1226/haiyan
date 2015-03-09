/*
 * Created on 2006-8-4
 *
 */
package haiyan.common.config;

import java.util.Arrays;

/**
 * @author zhouxw
 * 
 */
public class DataConstant {

	///////////////////////////////// for config /////////////////////////////////
	
	// public static String APP_ROOT_PATH = null;// ConfigUtil.getPathName();
	// public static String UPLOAD_SERVICE_PATH = null;//
	// ConfigUtil.getPathName();
	// public static final String LOGIN_DSN = "com.suddenzhou.genmis.LoginDSN";
	
	/**
	 * 附件存放路径
	 */
	public static String UPLOAD_PATH = null;
	
	/**
	 * 老版本的应用存放路径变量
	 */
	public static String APPLICATION_PATH = null; // ConfigUtil.getPathName();

	/**
	 * 老版本的皮肤资源路径变量
	 */
	public static String SKIN_PATH = null; // ConfigUtil.getPathName();
	

	public static final String LOGIN_USER = "com.suddenzhou.genmis.LoginUser";

	public static final String LOGIN_TIME = "com.suddenzhou.genmis.LoginTime";

	public static final String LOGIN_ADDR = "com.suddenzhou.genmis.LoginAddr";

	public static final String LOGIN_SESSIONID = "com.suddenzhou.genmis.SessionID";

	public static final String UPLOADFILE_ATTRIBUTE_PREFIX = "com.suddenzhou.genmis.AttributeForFileUpload";

	public static final String PARAM_PRE_REQUEST_URL = "com.suddenzhou.genmis.RequestURL";
	
	// ---------------------------------------------------------------------------------------------------------- //

	public static final String DOWNLOAD_ORIGIN_ENCODE = "DOWNLOAD_ORIGIN_ENCODE";

	public static final String DOWNLOAD_TARGET_ENCODE = "DOWNLOAD_TARGET_ENCODE";

	public static final String REGIST_USER_LOGIN = "REGIST_USER_LOGININ";

	public static final String REGIST_USER_LOGOUT = "REGIST_USER_LOGOUT";

	public static final String GONE = "GONE";

	public static final String EXCLUSIVE = "EXCLUSIVE";

	public static final String OVER_TIME_NAME = "OVER_TIME";

	public static final String LANGUAGE_COOKIE = "LANGUAGE_COOKIE";

	public static final String PATH_MAKEXML = "PATH_MAKEXML";

	public static final String PATH_TRANS = "PATH_TRANS";

	public static final String MIN_CONN = "MIN_CONN";

	public static final String MAX_CONN = "MAX_CONN";

	public static final String TIMEOUT_CONN = "TIMEOUT_CONN";

	public static final String TRANS_TIMEOUT_CONN = "TRANS_TIMEOUT_CONN";

	public static final String WAITTIME_CONN = "WAITTIME_CONN";

	public static final String APP_ROOT_NAME = "APP_ROOT_NAME";
	
	public static final String PATH_NAME = "PATH_NAME";

	public static final String UPLOAD_PATH_NAME = "UPLOAD_PATH_NAME";

	public static final String UPLOAD_SERVICE = "UPLOAD_SERVICE";

	public static final int MAXLENTH_LOG = 1021;
	// @deprecated
    public static final String USEDSTATUS_FLDNAME = "USEDSTATUS";

    public static final String USEDSTATUS = USEDSTATUS_FLDNAME;
	
	public static final String WFBILLID = "WFBILLID";
	
	public static final String HYFORMKEY = "HYFORMKEY";
	
	public static final String HYVERSION = "HYVERSION";
	
	public static final String HYVERSIONCHECK = "HYVERSIONCHECK";

	//------------------------------------------------- for webpara --------------------------------------------- //
	
	public static final String CHARSET = "UTF-8";

	public static final String CONTENT_TYPE = "text/html;charset=" + CHARSET;
	
	public static final String SESSION_ID = "__sessionid"; // cookie sid

	public static final String SID_NAME = "__sid"; // url sid
	
	public static final String ONE_2_ONE_ID = "__one2oneID";

	public static final String PAGE_NO_PARAM_NAME = "__go2pageNum";

	public static final String MAX_COUNT_PER_PAGE_NAME = "__maxNumPerPage";

	public static final String MODEL_CODE = "__model_code";

	public static final String MODEL_ID = "__model_id";

	public static final String HAS_BUTTON = "__hasButton";

	public static final String CRUD_VERSION = "__version"; // 不要改名

	public static final String CRUD_FLAG = "__flag"; // 不要改名已经在js中用了

	public static final String TABLE_DATAS = "__table_datas";

	public static final String TABLE_ROWN = "__table_rown";

	public static final String TABLE_COLN = "__table_coln";

	public static final String PAGEVIEW_INDEX = "__pageViewIndex";

	public final static String PROCID = "__procID";

	public final static String PROCCODE = "__procCode";

	public static final String SELECTED_ID = "__selectedID";
	
	public static final String NORETURN = "__noReturn";

	public static final String SELECTED_COLUMN_NAME_PARAM = "__fieldName";

	public static final String MAIN_TABLE_PARAM = "__main_table";

	public static final String MAIN_FIELD_PARAM = "__main_field";

	public static final String PLUGIN_NAME_PARAM = "__pluginName";

	public static final String OPTION_DISNAME = "Option__";

	public static final String PASSWORD_DISNAME = "PassWord__";

//	public static final String PLUGINVIEW1_FRMNAME = "iframe1.";
	
	public static final String BUTTON_RIGHTSTR = "disabled";

	public static final String XHE_TABLE_LABEL = "cls";

	public static final String XHE_TABLE_HEAD = "head";

	public static final String XHE_TABLE_TAIL = "tail";

	public static final String XHE_EDIT_AREA = "editarea";

	public static final String XHE_DATA_TABLE = "datatable";

	public static final String XHE_MAIN_AREA = "mainArea";

	public static final String XLOADTREE_LABEL = "xloadtree";

	public static final String XLOADTREE_INFOPARAM = "treeinfo";

	public static final String XLOADTREE_DATAPARAM = "treedata";

	public static final String EXTEND_OPERATION_CODE_PARAM_CHOOSE_MODE = "__chooseMode";

	public static final String CHOOSE_MODE = "__chooseMode";

	public static final String CHOOSE_MODE_SINGLE = "single";

	public static final String CHOOSE_MODE_MULTIPLE = "multiple";

	public static final String INIT_USER = "initUser";

	public static final String LOGIN_PARAM = "loginParam";

	public static final String SLIENT_LOGIN = "silentlyLogin";

	public static final String PARAMETER_RELOADBASECONFIG = "reloadBaseConfig";

	public static final String PARAMETER_TABLE_FILE_NAME = "tableFileName";

	public static final String FORWARD_LOGIN_PAGE = "showLoginPage";

	public static final String FORWARD_REGISTER = "register";

	public static final String FORWARD_MAIN = "main";

	public static final String FORWARD_GOHOME = "goHome";

	public static final String MPC_NAME_PARAM = "mpcPages";

	public static final String UPLOADFILE_PREFIX = "uploadFileName";

	public static final String FILE_DOWNLOAD_PARAM = "downloadFileName";

	/**
	 * ext queryOpr
	 */
	public static final String EXT_QUERY = "query";

	/**
	 * ext queryValue
	 */
	public static final String EXT_QUERYVALUE = "queryValue";

	/**
	 * ext queryField
	 */
	public static final String EXT_QUERYFIELD = "queryField";

	/**
	 * ext start
	 */
	public static final String EXT_START = "start";

	/**
	 * ext limit
	 */
	public static final String EXT_LIMIT = "limit";
	
	/**
     * for jsoncore
     */
	public static final String JSON_SET = "__jsonSet";

	/**
	 * for metacore
     */
	public static final String META_FORM_SET = "FormSet";

	/**
	 * for batchplugin
     */
	public static final String DATA_SET = "__dataSet";
	
	///////////////////////////////// for midpara /////////////////////////////////
	/**
	 * for SQLRender.getQueryFilterPlugin
	 */
	public static final String EXTRA_FILTER_PREFIX = "__extraFilter.";
	/**
	 * for ConfigUtil.getProperty
	 */
	public static final String CONST_DBERRORS_NAME = "dberrors";
	/**
	 * for ConfigUtil.getProperty
	 */
	public static final String CONST_PROMPT_NAME = "prompt";
	/**
	 * for ConfigUtil.getProperty
	 */
	public static final String CONST_ALERT_NAME = "alert";
	/**
	 * for ConfigUtil.getProperty
	 */
	public static final String CONST_WARNING_NAME = "warning";

	/**
	 * condition para
	 **/
	public static final String ALL_RELATIONOPR = "__allRelationOpr";
	/**
	 * sysautonumber para
	 **/
	public static final String NEWID_LASTTIME = "__newIDLastTime";
	
//	////////////////////////////////// for defaultopt //////////////////////////////////
	
	public static final int USER_DEFINED = 0;
	public static final int ADD_BT_TYPE = 1;
	public static final int DELETE_BT_TYPE = 2;
	public static final int COLUMNS_BT_TYPE = 3;
	public static final int PRINT_BT_TYPE = 4;
	public static final int SAVE_BT_TYPE = 5;
	public static final int SAVEANDADD_BT_TYPE = 6;
	public static final int SAVEANDADDCOPY_BT_TYPE = 7;
	public static final int QUERYEDIT_BT_TYPE = 8;
	public static final int FCKEDIT_BT_TYPE = 9;
	public static final int QUERY_BT_TYPE = 10;
	
	////////////////////////////////// for delimeter //////////////////////////////////

	/**
	 * PASSWORD DELIMETER
	 */
	public static final String PASSWORD_DISVALUE = "*****";

	/**
	 * XMLHTTP OBJ DELIMETER
	 */
	public static final String HTTP_OBJ_DELIM = "#####";

	/**
	 * XLOADTREE DELIMETER
	 */
	public static final String XLOADTREE_DIM = "@@@@@";

	/**
	 * NORMAL DELIMETER
	 */
	public static final String NORMAL_DIM = "^^^^^";

	/**
	 * SQL DELIMETER
	 */
	public static final String SQL_DIM = "$$$$$";

	/**
	 * SQL VALUE DELIMETER
	 */
	public static final String SQL_VALUE_DIM = ",";

	/**
	 * PLUGIN DELIMETER
	 */
	public static final String PLUGIN_DIM = "";
	
	////////////////////////////////// for action //////////////////////////////////

	@Deprecated
	public static final String OPERATION_FLAG = "__operation";

	public static final String OPERATION_LIBERARY = "liberary";

	public static final String OPERATION_SHOW_LOGIN_PAGE = "showLoginPage";

	public static final String OPERATION_LOGIN = "login";

	public static final String OPERATION_LOGOUT = "logout";

	public static final String OPERATION_AFFIRM = "affirm";

	public static final String OPERATION_CHANGEPASS = "changePassword";

	public static final String OPERATION_ERROR = "error";

	public static final String OPERATION_RELOAD_CONFIG = "reloadConfig";

	public static final String OPERATION_REGISTER = "register";

	public static final String OPERATION_MAIN = "main";

	public static final String OPERATION_GOHOME = "goHome";

	public static final String OPERATION_SHOW_LEFT_FRAME = "showLeftFrame";

	public static final String OPERATION_SHOW_TOP_FRAME = "showTopFrame";

	public static final String OPERATION_SHOW_MANAGER_PAGE = "showManager";

	public static final String OPERATION_SHOW_MAIN_FRAME = "showMainFrame";

	public static final String OPERATION_SHOW_MESSAGE_FRAME = "showMessageFrame";

	public static final String OPERATION_SHOW_BOTTOM_FRAME = "showBottomFrame";

	public static final String OPERATION_SHOW_DESK_FRAME = "showDeskFrame";

	public static final String OPERATION_SHOW_CUSTOMIZE_COLUMNS = "showCustomizeColumns";

	public static final String OPERATION_SHOW_CONDITION = "showCondition";

	public static final String OPERATION_SHOW_DOWNLOAD_FILE = "showDownloadFile";

	public static final String OPERATION_CONNECT = "connect";

	public static final String OPERATION_DOWNLOAD = "download";

	public static final String OPERATION_UPLOAD = "upload";

	public static final String OPERATION_QUICKQUERY = "quickquery";
	
	public static final String OPERATION_FILTERDESIGN = "filter";
	
	public static final String OPERATION_FILTERBY = "filterby";
	
	public static final String OPERATION_PRINT = "print";

	public static final String OPERATION_NEXT = "next";

	public static final String OPERATION_PRE = "pre";

	public static final String OPERATION_PLUGIN = "plugin";
	
    public static final String OPERATION_RIGHT = "right";
	
    public static final String OPERATION_GRID = "grid";
	
    public static final String OPERATION_CONDITION = "condition";
	
    public static final String OPERATION_QUERY = "query";

    public static final String OPERATION_QUERYONE = "queryOne";

	public static final String OPERATION_EDIT = "edit";

	public static final String OPERATION_EDITONE = "editOne";

	public static final String OPERATION_SAVE = "save";

	public static final String OPERATION_SAVEANDADD = "saveAndAdd";

	public static final String OPERATION_SAVEANDADDCOPY = "saveAndAddCopy";

	public static final String OPERATION_SAVE_PLUGIN = "savePlugin";

    public static final String OPERATION_DIRTY = "dirty";

    public static final String OPERATION_NEW = "new";

	public static final String OPERATION_ADD = "add";

	public static final String OPERATION_ADD_SAME_LEVEL = "addSameLevel";

	public static final String OPERATION_ADD_LOWER = "addLower";

	public static final String OPERATION_LIST_RESULT = "listResult";

	public static final String OPERATION_QUERY_BY_FORM = "queryByForm";

	public static final String OPERATION_SHOW_TREE_VIEW = "showTreeView";

	public static final String OPERATION_DELETE = "delete";

	public static final String OPERATION_DELETE_TREE_NODE = "deleteTree";

	public static final String OPERATION_XMLHTTP = "xmlhttp";

	public static final String OPERATION_XMLHTTP_INVOKE = "xmlhttp_invoke";

	public static final String OPERATION_XMLHTTP_JSON = "xmlhttp_json";

	public static final String OPERATION_XMLHTTP_P = "xmlhttp_p";

	public static final String OPERATION_XMLHTTP_QUERY = "xmlhttpQuery";

	public static final String OPERATION_QUERYADD = "xmlhttpAdd";

	public static final String OPERATION_QUERYEDIT = "xmlhttpEdit";

	public static final String OPERATION_QUERYSAVE = "xmlhttpSave";

	public static final String OPERATION_QUERYDELETE = "xmlhttpDelete";

	public static final String OPERATION_QUERYPLUGIN = "xmlhttpPlugin";
	
	public static final String OPERATION_RIGHT_TABLE = "__table";
	
	public static final String OPERATION_ACTION_PARAM = "__action";

	public static final String OPERATION_NAME_PARAM = "__opr_name";

	public static final String OPERATION_DATA_PARAM = "__opr_data";

	public static final String OPERATION_DATA_PARAM_REF = "__opr_data_ref";

	public static final String NEXT_OPRERATION_PARAM = "__next_opr";

	public static final String NEXT_OPRERATION_MODIFY = "__next_modify";

	public static final String NEXT_OPRERATION_ADD = "__next_add";

	public static final String NEXT_OPRERATION_ADD_COPY = "__next_add_copy";

	public static final String NEXT_OPRERATION_OPR_CHILD = "__next_add";

	public static final String NEXT_OPRERATION_QUERY = "__next_query";

	public static final String NEXT_OPRERATION_NEXT = "__next_next";

	public static final String NEXT_OPRERATION_PRE = "__next_pre";

	public static final String ONE_DATA_PARAM = "__one_data";

	public static final String PAGE_DATA_PARAM = "__page_data";

	public static final String TREE_DATA_PARAM = "__tree_data";

	public static final String PARAM_EXPPARA = "__expPara";
	
	public static final String PARAM_EXP = "__exp";
	
	public static final String PARAM_DSN = "__dsn";

	////////////////////////////////// for sort //////////////////////////////////
	
	public static final String SORTNAME = "sort";

	public static final String SORTMETHOD = "dir";

	public static final String ASCEND = "ASC";

	public static final String DESCEND = "DESC";
	
	////////////////////////////////// for ignore //////////////////////////////////
	/**
	 * 
	 */
	public final static int REGION_START = 1;

	/**
	 * 
	 */
	public final static int REGION_END = 2;

	/**
	 * 
	 */
	public final static String DELIMITER = "__";

	/**
	 * AutoNaming太长了直接去掉
	 */
	public final static String _PREFIX = "";

	/**
	 * 
	 */
	public final static String CONSTANT_POSTFIX = "ConatantName";

	/**
	 * 
	 */
	public final static String ORDER_BY_FIELD_NAME = _PREFIX + DELIMITER
			+ "OrderByField" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String ORDER_BY_METHOD_NAME = _PREFIX + DELIMITER
			+ "OrderByMethod" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String QUICKCONDITION_FIELD_NAME = _PREFIX + DELIMITER
			+ "QuickConditionByField" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String QUICKCONDITION_METHOD_NAME = _PREFIX + DELIMITER
			+ "QuickConditionByMethod" + CONSTANT_POSTFIX + DELIMITER;

	/**
	 * 
	 */
	public final static String QUICKCONDITION_VALUE_NAME = _PREFIX + DELIMITER
			+ "QuickConditionByValue" + CONSTANT_POSTFIX + DELIMITER;
	/**
     * 
     */
	public final static String[] SYS_OPRS = new String[] {
			//
            OPERATION_LIBERARY, OPERATION_REGISTER, OPERATION_MAIN, OPERATION_GOHOME,
            OPERATION_XMLHTTP_QUERY, OPERATION_XMLHTTP, OPERATION_XMLHTTP_P, OPERATION_XMLHTTP_JSON,
			//
            OPERATION_DOWNLOAD, OPERATION_UPLOAD, OPERATION_SHOW_DOWNLOAD_FILE, OPERATION_SHOW_TREE_VIEW, 
            OPERATION_QUERY, OPERATION_QUERY_BY_FORM, OPERATION_LIST_RESULT,
            OPERATION_QUERYONE, OPERATION_EDIT, OPERATION_EDITONE, 
            OPERATION_PLUGIN, OPERATION_SAVE, OPERATION_SAVE_PLUGIN, 
            OPERATION_SAVEANDADD, OPERATION_SAVEANDADDCOPY, OPERATION_ADD, OPERATION_ADD_LOWER,
            OPERATION_ADD_SAME_LEVEL, OPERATION_DELETE, OPERATION_DELETE_TREE_NODE,
			//
			OPERATION_QUERYSAVE, OPERATION_QUERYEDIT, OPERATION_QUERYADD, OPERATION_QUERYDELETE,
			OPERATION_QUICKQUERY, OPERATION_FILTERBY, OPERATION_FILTERDESIGN, OPERATION_PRINT, OPERATION_SHOW_CUSTOMIZE_COLUMNS };

	/**
     * 
     */
	public final static String[] URL_IGNORE = new String[] {
			"hiddenQuickCondition__des", "hiddenQuickCondition__opr",
			"hiddenQuickCondition__0", "hiddenQuickCondition__1", "hiddenQuickCondition__2",
			// "_dc",
			ORDER_BY_FIELD_NAME, ORDER_BY_METHOD_NAME,
			QUICKCONDITION_FIELD_NAME, QUICKCONDITION_METHOD_NAME, QUICKCONDITION_VALUE_NAME,
			NEXT_OPRERATION_PARAM,
			//
			OPERATION_LIBERARY, OPERATION_REGISTER, OPERATION_MAIN, OPERATION_GOHOME,
			OPERATION_XMLHTTP_QUERY, OPERATION_XMLHTTP, OPERATION_XMLHTTP_P, OPERATION_XMLHTTP_JSON,
			//
			OPERATION_DOWNLOAD, OPERATION_UPLOAD, OPERATION_SHOW_DOWNLOAD_FILE, OPERATION_SHOW_TREE_VIEW, 
            OPERATION_QUERY, OPERATION_QUERY_BY_FORM, OPERATION_LIST_RESULT,
            OPERATION_QUERYONE, OPERATION_EDIT, OPERATION_EDITONE, 
            OPERATION_PLUGIN, OPERATION_SAVE, OPERATION_SAVE_PLUGIN, 
            OPERATION_SAVEANDADD, OPERATION_SAVEANDADDCOPY, OPERATION_ADD, OPERATION_ADD_LOWER,
            OPERATION_ADD_SAME_LEVEL, OPERATION_DELETE, OPERATION_DELETE_TREE_NODE,
			//
			OPERATION_QUERYSAVE, OPERATION_QUERYEDIT, OPERATION_QUERYADD, OPERATION_QUERYDELETE,
			OPERATION_QUICKQUERY, OPERATION_FILTERBY, OPERATION_FILTERDESIGN, OPERATION_PRINT, OPERATION_SHOW_CUSTOMIZE_COLUMNS };

	/**
     * 
     */
	public final static String[] FORM_IGNORE = new String[] {
			"__groupField", "__countField", "_dc",
			// ext param
			EXT_QUERY, EXT_QUERYVALUE, EXT_QUERYFIELD, EXT_LIMIT, EXT_START,
			// haiyan param
			MAX_COUNT_PER_PAGE_NAME, PAGE_NO_PARAM_NAME, // page
			MAIN_TABLE_PARAM, MAIN_FIELD_PARAM, SELECTED_COLUMN_NAME_PARAM,
			//
			NEXT_OPRERATION_ADD, NEXT_OPRERATION_ADD_COPY,
			NEXT_OPRERATION_MODIFY, NEXT_OPRERATION_OPR_CHILD,
			NEXT_OPRERATION_PARAM, NEXT_OPRERATION_QUERY, 
			//
			MODEL_ID, MODEL_CODE,
//			COLUMNS_NAME, CLOSE_NAME, ADD_NAME, QUERY_NAME, SAVE_AND_ADD_NAME,
//			DELETE_NAME, QUERYEDIT_NAME, SAVE_NAME, PRINT_NAME, SELECT_AND_RETURN_NAME, 
			//
			PROCID, SELECTED_ID, OPERATION_DATA_PARAM, OPERATION_NAME_PARAM 
	};

	static {
		Arrays.sort(SYS_OPRS);
		Arrays.sort(URL_IGNORE);
		Arrays.sort(FORM_IGNORE);
	}

}

//
// public static final String FLOW_QUERY_BY_FORM = "flowQueryByForm";
