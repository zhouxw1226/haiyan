package haiyan.config.util;

import haiyan.common.CloseUtil;
import haiyan.common.DBColumn;
import haiyan.common.DebugUtil;
import haiyan.common.FileUtil;
import haiyan.common.InvokeUtil;
import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.SysCode;
import haiyan.common.config.DataConstant;
import haiyan.common.config.PathUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.IBillIDConfig;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.exp.IExpUtil;
import haiyan.common.intf.session.IUser;
import haiyan.config.castorgen.AbstractBillField;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.BillTable;
import haiyan.config.castorgen.DataRules;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Haiyan;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.PluginInterceptor;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.root.Config;
import haiyan.config.castorgen.root.DataSource;
import haiyan.config.castorgen.root.DbSource;
import haiyan.config.castorgen.root.Functions;
import haiyan.config.castorgen.root.JdbcURL;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
 
/**
 * ConfigManager Facade
 * 
 * IConfigManager<-DefaultConfigManager
 * 
 * @author ZhouXW
 *
 */
public class ConfigUtil {

	private ConfigUtil() {
	}
    private static String[] tableKeys = null;
    /**
     * 存放所有同db数据表的table对象
     */
    private static final Map<String, HashSet<String>> REALMAP = new HashMap<String, HashSet<String>>();
    /**
     * 存放table的外键关联的usedstatus字段对象
     */
    private static final Map<String, ArrayList<LinkField>> LINKMAP = new HashMap<String, ArrayList<LinkField>>();
    private static final HashSet<String> SYSMAPLIST = new HashSet<String>();
    private static Config ROOT = null; // 根配置
    private static IExpUtil EXPUTIL = null;
    private static IDataCache DATACACHE = null;
    private static boolean ORMUSECACHE = false; // 是否启用ORM多级缓存
    private static boolean ORMSMCC = false; // 是否启用ORM单版本并发控制
    private static synchronized void pInit() {
    	if (DATACACHE==null)
    		throw new Warning(SysCode.SysCodeMessage.ERROR_1001);
        // // if (tables != null)
        // // return;
        // // tables = new HashMap<String, Table>();
        // // tableFiles = new HashMap<String, File>();
        // // resources = new ValidatorResources();
        // // dbSources = new HashMap<String, MyDatabase>();
        // try {
        // loadEnvironment();
        // } catch (Throwable ex) {
        // ex.printStackTrace();
        // // "Init Failed! Cause:" +ex.getMessage()
        // // System.out.println(ex.getMessage());
        // // System.exit(0);
        // // ex.printStackTrace();
        // // throw ex;
        // }
    }
    public final static void setExpUtil(IExpUtil c) {
    	EXPUTIL = c;
    }
    public final static void setDataCache(IDataCache c) {
    	DATACACHE = c;
    }
	public static void setORMUseCache(boolean b) {
		ORMUSECACHE = b;
	}
	public static boolean isORMUseCache() {
		return ORMUSECACHE;
	}
	public static void setORMSMCC(boolean b) {
		ORMSMCC = b;
	}
	public static boolean isORMSMCC() {
		return ORMSMCC;
	}
//    public static void loadEnvironment() throws Throwable {
//        loadProps();
//        System.out.println("Loading Environment.");
//        loadRootConfig();
//        loadAllTableFile();
//        System.out.println("Environment Loaded.");
//    }
    /**
     * @return String
     * @throws Throwable
     */
    public final static String getDefaultDSNOfSlaves() {
		String DSN = PropUtil.getProperty("SERVER.DSN.Slaves");
		return DSN;
    }
    /**
     * @return String
     * @throws Throwable
     */
    public final static String getDefaultDSNOfMaster() {
		String DSN = PropUtil.getProperty("SERVER.DSN.Master");
		return DSN;
    }
    /**
     * @return String
     * @throws Throwable
     */
    public final static String getDefaultDSN() {
		String DSN = PropUtil.getProperty("SERVER.DSN");
		return DSN;
    }
    /**
     * @return String
     * @throws Throwable
     */
    public final static String getDefaultDBType() throws Throwable {
        JdbcURL jdbc = ROOT.getJdbcURL();
        if (jdbc != null)
            return jdbc.getDbType();
        DataSource ds = ROOT.getDataSource();
        if (ds != null)
            return ds.getDbType();
        return null;
    }
    /**
     * @param dsn
     * @return JdbcURL
     * @throws Throwable
     */
    public final static JdbcURL getJdbcURL(String dsn) throws Throwable {
        if (!StringUtil.isBlankOrNull(dsn)) {
            Config config = getConfig();
            DbSource dbSource = config.getDbSource();
            for (JdbcURL jdbc : dbSource.getJdbcURL()) {
                if (dsn.equalsIgnoreCase(jdbc.getName())) {
                    return jdbc;
                }
            }
        }
        return null;
    }
    /**
     * @param dsn
     * @return DataSource
     * @throws Throwable
     */
    public final static DataSource getDataSource(String dsn) throws Throwable {
        if (!StringUtil.isBlankOrNull(dsn)) {
            Config config = getConfig();
            DbSource dbSource = config.getDbSource();
            for (DataSource ds : dbSource.getDataSource()) {
                if (dsn.equalsIgnoreCase(ds.getName())) {
                    return ds;
                }
            }
        }
        return null;
    }
    /** 
     * @return DataSource
     * @throws Throwable
     */
    public final static String[] getFunctionScanClasses() throws Throwable {
        Config config = getConfig();
        Functions functions = config.getFunctions();
        if (functions!=null) {
            String p = functions.getClasses();
            if (!StringUtil.isEmpty(p))
            	return StringUtil.split(p, ";");
        }
        return StringUtil.EMPTY_STRINGARR;
    }
//    /**
//     * @param dsn
//     * @return String
//     * @throws Throwable
//     */
//    public final static String getDBType(String dsn) throws Throwable {
//        if (!StringUtil.isBlankOrNull(dsn)) {
//            Config config = getConfig();
//            DbSource dbSource = config.getDbSource();
//            for (JdbcURL jdbc : dbSource.getJdbcURL()) {
//                if (dsn.equalsIgnoreCase(jdbc.getName())) {
//                    return jdbc.getDbType();
//                }
//            }
//            for (DataSource ds : dbSource.getDataSource()) {
//                if (dsn.equalsIgnoreCase(ds.getName())) {
//                    return ds.getDbType();
//                }
//            }
//        }
//        return null;
//    }
    /**
     * @return Config
     * @throws Throwable
     */
    public final static Config getConfig() {
        if (ROOT == null) {
            throw new Warning("Config配置没有正确读取");
        }
        return ROOT;
    }
    /**
	 * 
	 */
    public final static void clearAllConfigCache() {
        ROOT = null;
        // if (tables != null)
        // tables.clear();
        // if (dbSources != null)
        // dbSources.clear();
        // System.gc();
    }
    /**
     * @param tableName
     */
    public final static void clearTable(String tableName) {
        pInit();
        File file = getTableFile(tableName);
        if (file.exists() && file.delete()) {
            // tables.remove(tableName);
            // tableFiles.remove(tableName);
        	DATACACHE.removeTable(tableName);
            DATACACHE.removeTableFile(tableName);
            // tables.put(tableName, null);
            // tableFiles.put(tableName, null);
            tableKeys = null;
        }
        // System.gc();
    }
	public final static String getDBName(AbstractBillField field) {
		return StringUtil.isEmpty(field.getDbName())?field.getName():field.getDbName();
	}
	public final static String getDBName(ITableConfig table) {
        return getRealTableName(table);
    }
    public final static String getRealTableName(ITableConfig table) {
        String tableName = table.getRealName();
        if (StringUtil.isBlankOrNull(tableName)
            || table.getName().equalsIgnoreCase(tableName))
            return table.getName();
        return getRealTableName(getTable(tableName));
    }
    /**
     * @param tableN
     * @return String
     */
    public final static String getRealTableName(String tableN) {
        return getRealTableName(getTable(tableN));
    }
    /**
     * @throws Throwable
     */
    public final static synchronized void loadProps() throws Throwable {
//        // DebugUtil.debug(">properties");
//        // InputStream in = null;
//        // cert.loadEnvironment();
//        System.out.println("Loading Properties.");
//        // fileName
//        try {
//            // setter
////            setLogType();
////            setNeedDebug();
//            setNeedExclusive();
//            setNeedDBPools();
//            setCacheParam();
//            setDBPoolsParam();
//            setPageParam();
////            setDownloadParam();
////            setDataConstant();
//            // setValidator();
//        } catch (Throwable ex) {
//            throw ex; // 4debug
//        } finally {
//            // if (in != null)
//            // in.close();
//        }
//        System.out.println("Properties Loaded.");
    }
//    /**
//     * @throws Throwable
//     * 
//     */
//    private static void setLogType() throws Throwable {
//        // String value = getProperty("USELOG");
//        String value = getProperty("LOGGERTYPE");
//        LogUtil.loggerType = StringUtil.isBlankOrNull(value) ? -1 : 
//        	Integer.valueOf(value).intValue();
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setNeedDebug() throws Throwable {
//        final String ISDEBUG = getProperty("DEBUG");
//        System.out.println("--debug=" + ISDEBUG);
//        // if ("true".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value)) {
//        // if (DebugUtil.logger == null)
//        DebugUtil.logger = new ILogger() { // 强制设置logger接口
//
//            public void debug(Object info) {
//                LogUtil.info(info);
//            }
//            public void error(Object info, Throwable ex) {
//                LogUtil.error(info, ex);
//            }
//            public void error(Object info) {
//                if (info instanceof Throwable) {
//                    Throwable ex = (Throwable)info;
//                    LogUtil.error(ex.getMessage(), ex);
//                } else
//                    LogUtil.error(info);
//            }
//            public void warn(Object info) {
//                LogUtil.warn(info);
//            }
//
//        };
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setNeedDBPools() throws Throwable {
//        String value = getProperty("USE_DBPOOLS");
//        DBHelper.useDBPool = "1".equals(value) || "true".equals(value);
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setNeedExclusive() throws Throwable {
//        String value = getProperty("EXCLUSIVE");
//        LoginBMP.isExcluesive = "1".equals(value) || "true".equals(value);
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setDBPoolsParam() throws Throwable {
//        if (getProperty(DataConstant.MIN_CONN) != null)
//            ConnectionParam.MIN_CONN = Integer.parseInt(ConfigUtil.getProperty(DataConstant.MIN_CONN));
//        if (getProperty(DataConstant.MAX_CONN) != null)
//            ConnectionParam.MAX_CONN = Integer.parseInt(ConfigUtil .getProperty(DataConstant.MAX_CONN));
//        if (getProperty(DataConstant.TIMEOUT_CONN) != null)
//            ConnectionParam.TIMEOUT_VALUE = Integer.parseInt(ConfigUtil.getProperty(DataConstant.TIMEOUT_CONN));
//        if (getProperty(DataConstant.TRANS_TIMEOUT_CONN) != null)
//            ConnectionParam.TIMEOUT_TRANS = Integer.parseInt(ConfigUtil.getProperty(DataConstant.TRANS_TIMEOUT_CONN));
//        if (getProperty(DataConstant.WAITTIME_CONN) != null)
//            ConnectionParam.WAIT_TIME = Integer.parseInt(ConfigUtil.getProperty(DataConstant.WAITTIME_CONN));
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setCacheParam() throws Throwable {
//        String value = getProperty("USECACHE");
//        if (!StringUtil.isTrimBlankOrNull(value))
//            DBManager.USECACHE = Byte.parseByte(value);
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setPageParam() throws Throwable {
//        String value = null;
//        value = getProperty("MAXPAGERECORDCOUNT");
//        if (!StringUtil.isTrimBlankOrNull(value))
//            Page.MAXNUMPERPAGE = Integer.parseInt(value);
//        value = getProperty("MAXCOUNTPERQUERY");
//        if (!StringUtil.isTrimBlankOrNull(value))
//            Page.MAXCOUNTPERQUERY = Integer.parseInt(value);
//
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setDownloadParam() throws Throwable {
//        ComponentCreator.setFileBoxEncoding(
//                getProperty(DataConstant.DOWNLOAD_ORIGIN_ENCODE),
//                getProperty(DataConstant.DOWNLOAD_TARGET_ENCODE));
//    }
//    /**
//     * @throws Throwable
//     */
//    private static void setDataConstant() throws Throwable {
//        DataConstant.APPLICATION_PATH = getPathName();
//        DataConstant.SKIN_PATH = getSkinPath();
//        DataConstant.UPLOAD_PATH = PathUtil.getConfigUploadPath();
//		if (StringUtil.isEmpty(DataConstant.UPLOAD_PATH))
//			DataConstant.UPLOAD_PATH = DataConstant.APPLICATION_PATH;
//    }
//    /**
//     * @param key
//     * @return String
//     * @throws Throwable
//     */
//    @Deprecated
//    public final static String getInvokeProperty(String key) {
//    	try {
//    		return PropUtil.getInvokeProperty(key);
//    	}catch(Throwable e){
//    		throw Warning.wrapException(e);
//    	}
//    }
//    /**
//     * @param bundName
//     * @param key
//     * @return String
//     * @throws Throwable
//     */
//    @Deprecated
//    public final static String getProperty(String bundName, String key) {
//    	try {
//    		return PropUtil.getProperty(bundName, key, "");
//    	}catch(Throwable e){
//    		throw Warning.wrapException(e);
//    	}
//    }
//    /**
//     * @param bundName
//     * @param key
//     * @return String
//     * @throws Throwable
//     */
//    @Deprecated
//    public final static String getProperty(String bundName, String key, String def) {
//    	try {
//    		return PropUtil.getProperty(bundName, key, def);
//    	}catch(Throwable e){
//    		throw Warning.wrapException(e);
//    	}
//    }
//    /**
//     * @param key
//     * @return String
//     * @throws Throwable
//     */
//    @Deprecated
//    public final static String getProperty(String key) {
//    	try {
//    		return PropUtil.getProperty(key);
//    	}catch(Throwable e){
//    		throw Warning.wrapException(e);
//    	}
//    }
//    /**
//     * @throws Throwable
//     */
//    public final static void setValidator() throws Throwable {
//        InputStream in = null;
//        try {
//            in = new FileInputStream(getPathName() + File.separator + "WEB-INF"
//                    + File.separator + "validation.xml");
//            if (resources != null)
//                ValidatorResourcesInitializer.initialize(resources, in, false);
//        } finally {
//            if (in != null) {
//                in.close();
//            }
//        }
//        in = null;
//        try {
//            in = new FileInputStream(getPathName() + File.separator + "WEB-INF"
//                    + File.separator + "validator-rules.xml");
//            if (resources != null)
//                ValidatorResourcesInitializer.initialize(resources, in, false);
//        } finally {
//            if (in != null) {
//                in.close();
//            }
//        }
//    }
//    /**
//     * @return ValidatorResources
//     */
//    public final static ValidatorResources getValidatorResources() {
//        return resources;
//    }
//    /**
//     * @param conn
//     * @param filePath
//     * @param schema
//     * @throws Throwable
//     *             {
//     */
//    public final static void db2Files(Connection conn, String filePath,
//            String schema) throws Throwable {
//        // try {
//        File path = new File(filePath);
//        if (!path.exists()) {
//            if (!path.mkdirs()) {
//                return;
//            }
//        }
//        HashMap<?, ?> tablems = DBHelper.getTableMap(conn, schema);
//        Vector<?> tableNames = (Vector<?>) tablems.get("ALL_DATABASE_NAME_VECTOR");
//        // DebugUtil.debug(">"+tableNames);
//        for (int i = 0; i < tableNames.size(); i++) {
//            String tableName = (String) tableNames.get(i);
//            tableName = tableName.toUpperCase();
//            File xmls = new File(filePath + File.separator + tableName + ".xml");
//            // DebugUtil.debug(">" + filePath + File.separator + tableName +
//            // ".xml");
//            saveTableFile(xmls, tables2Genmis(tablems, tableName));
//        }
//        DebugUtil.debug(">__file_path=" + filePath + ", __total_tables=" + tableNames.size());
//        // } catch (Throwable ex) {
//        // throw ex;
//        // }
//    }
    /**
     * @param file
     * @return Workflow
     * @throws Throwable
     */
    public final static String saveTable(Haiyan genmis) throws Throwable {
        // throw new Warning("Not Support");
        Writer writer = null;
        try {
            // DebugUtil.debug("loading config:" + pathName);
            // writer = new OutputStreamWriter(new BufferedOutputStream(),
            // "UTF-8");
            writer = new StringWriter();
            // org.exolab.castor.xml.Marshaller.marshal(proc, writer);
            org.exolab.castor.xml.Marshaller marshal = 
            		new org.exolab.castor.xml.Marshaller(writer);
            marshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
            // marshal.setEncoding("UTF-8");
            marshal.setEncoding("UTF-8");
            marshal.setSuppressXSIType(true);
            marshal.marshal(genmis);
            // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            // xsi:noNamespaceSchemaLocation="../mywf.xsd"
            // Table[] newTables = genmis.getTable();
            return writer.toString();
        }
        // catch (Throwable ex) {
        // throw ex;
        // }
        finally {
        	CloseUtil.close(writer);
        }
    }
    /**
     * @param file
     * @param genmis
     */
    public final static void saveTableFile(File file, Haiyan genmis)
            throws Throwable {
        // throw new Warning("Not Support");
        Writer writer = null;
        try {
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    return;
                }
            }
            // writer = new FileWriter(file);
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            // org.exolab.castor.xml.Marshaller.marshal(proc, writer);
            org.exolab.castor.xml.Marshaller marshal = new org.exolab.castor.xml.Marshaller(
                    writer);
            marshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
            // marshal.setEncoding(DataConstant.CHARSET);
            marshal.setEncoding("UTF-8");
            marshal.setSuppressXSIType(true);
            marshal.marshal(genmis);
            // xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            // xsi:noNamespaceSchemaLocation="../Haiyan.xsd"
            // Table[] newTables = genmis.getTable();
            DebugUtil.debug(">saved tableFile:" + file.getAbsolutePath());
        }
        // catch (Throwable ex) {
        // throw ex;
        // // ex.printStackTrace();
        // // throw new Warning(ex, new TransCode(100054, "error_write_config",
        // // new String[] { file.getAbsolutePath() }));
        // }
        finally {
        	CloseUtil.close(writer);
        }
    }
    // /**
    // * @param table
    // */
    // public final static void saveTable(Table table) throws Throwable {
    // //
    // pInit();
    // //
    // File file = ConfigUtil.getTableFile(table.getName());
    // Haiyan genmis = new Haiyan();
    // genmis.addTable(table);
    // saveTableFile(file, genmis);
    // }
//    /**
//     * @param name
//     * @return ComponentTypeType
//     */
//    public final static ComponentTypeType getComponentTypeType(String name) {
//        return ComponentTypeType.valueOf(name);
//    }
    /**
     * @param tableMap
     * @param tableName
     * @return GenMis
     */
    public static Haiyan tables2Genmis(IUser user, HashMap<?, ?> tableMap, String tableName) {
        if (tableMap.get(tableName) == null) {
            throw new Warning(SysCode.create(user, 100053, "config_not_found", new String[] { tableName }));
        }
        Table table = new Table();
        table.setName(tableName);
        table.setDescription(tableName);
        // getPrimaryKeys
        // CommonDBColumnProp pkColumn = DBHelper.getPKsVector()
        HashMap<?, ?> fieldMap = (HashMap<?, ?>) tableMap.get(tableName);
        Iterator<?> iter = fieldMap.keySet().iterator();
        while (iter.hasNext()) {
            String fieldName = (String) iter.next(); // 用key取不安全，因为不是entries的copy
            DBColumn dbColumn = (DBColumn) fieldMap.get(fieldName);
            // DebugUtil.debug(dbColumn.isPK());
            if (dbColumn.isPK()) {
                Id id = dBProp2ID(fieldName, dbColumn);
                table.setId(id);
            } else {
                Field field = dBProp2Field(tableName, fieldName, dbColumn);
                table.addField(field);
            }
        }
        if (table.getId() == null) {
            Id id = new Id();
            id.setName("ID");
            id.setJavaType(AbstractCommonFieldJavaTypeType.BIGDECIMAL);
            table.setId(id);
        }
        Haiyan genmis = new Haiyan();
        genmis.addTable(table);
        return genmis;
    }
    /**
     * @param idName
     * @param prop
     * @return Id
     * 
     */
    private static Id dBProp2ID(String idName, DBColumn prop) {
        Id id = new Id();
        id.setName(idName);
        if (prop.getType().equalsIgnoreCase("NUMBER")) {
            id.setJavaType(AbstractCommonFieldJavaTypeType.BIGDECIMAL);
        } else {
            id.setJavaType(AbstractCommonFieldJavaTypeType.STRING);
        }
        return id;
    }
    /**
     * @param tableName
     * @param fieldName
     * @param prop
     * @return
     */
    private static Field dBProp2Field(String tableName, String fieldName,
            DBColumn prop) {
        Field field = new Field();
        field.setName(fieldName);
        field.setDescription(fieldName);
        if (prop.getType().equalsIgnoreCase("NUMBER")) {
            field.setJavaType(AbstractCommonFieldJavaTypeType.BIGDECIMAL);
        } else if (prop.getType().equalsIgnoreCase("DATE")) {
            field.setJavaType(AbstractCommonFieldJavaTypeType.DATE);
        } else {
            field.setJavaType(AbstractCommonFieldJavaTypeType.STRING);
        }
        //Common comp = new Common();
        if (prop.isFK()) {
            //comp.setType(ComponentTypeType.RICHSELECT);
        	field.setReferenceTable(prop.getRefTable());
            field.setReferenceField(prop.getRefField());
            field.setDisplayReferenceField("ID");
        } else {
            //comp.setType(ComponentTypeType.TEXT);
        }
        //field.setCommon(comp);
        return field;
    }
    private static final void loadDBSameTable() {
        String[] tables = getAllTableKeys();
        for (int j = 0; j < tables.length; j++) {
            String tableName = tables[j].toUpperCase();
            String realName = getRealTableName(tables[j]);
            if (!REALMAP.containsKey(realName))
                synchronized (REALMAP) {
                    if (!REALMAP.containsKey(realName))
                        REALMAP.put(realName, new HashSet<String>());
                }
            if (!REALMAP.get(realName).contains(tableName))
                REALMAP.get(realName).add(tableName);
        }
    }
    public static boolean hasDataRuleTables(Table table) {
        Table sysMapTabl = getTable("SYSMAP");
        if (SYSMAPLIST.size() == 0 && sysMapTabl.getDataRulesCount() > 0)
            synchronized (SYSMAPLIST) {
                if (SYSMAPLIST.size() == 0) {
                    for (DataRules dr : sysMapTabl.getDataRules()) {
                        if (!SYSMAPLIST.contains(dr.getSrcTable()))
                            SYSMAPLIST.add(dr.getSrcTable());
                        if (!SYSMAPLIST.contains(dr.getDestTable()))
                            SYSMAPLIST.add(dr.getDestTable());
                    }
                }
            }
        return SYSMAPLIST.contains(table.getName());
    }
    public static final void reloadLinkTable() {
        loadDBSameTable();
        synchronized (LINKMAP) {
            LINKMAP.clear();
        }
        synchronized (SYSMAPLIST) {
            SYSMAPLIST.clear();
        }
    }
    public static void loadAllTableConfig() throws Throwable {
        loadSYSTableConfig(false);
        File[] xmls = getConfigFiles();
        for (int i = 0; i < xmls.length; i++) {
            loadTableConfig(xmls[i], false);
        }
        reloadLinkTable();
    }
    public final static void loadSYSTableConfig(boolean reload) throws Throwable {
        File[] xmls = getSYSTableFiles();
        for (int i = 0; i < xmls.length; i++) {
            loadTableConfig(xmls[i], reload);
        }
//      LogUtil.logTable = getTable("SYSLOG");
//	    File srcDir = new File(getConfigPath()+File.separator+"project");
//	    if (srcDir.exists()) {
//	        File destDir = new File(getPathName()+File.separator+"project");
//	        FileUtil.copy(srcDir, destDir);
//	    }
    }
	public static int getMainTableIndex(IBillConfig billCfg) {
		return 0;
	}
	public static ITableConfig getMainTable(IBillConfig billCfg) {
		Bill bill = (Bill)billCfg;
		BillTable[] tables = bill.getBillTable();
		if (tables.length>0) {
			return getTable(tables[0].getDbName());
		}
		return null;
	}
    public static class LinkField {
        public String field;
        public String linkTable;
    }
    public final static ArrayList<LinkField> getLinkTable(ITableConfig table) {
        String key = table.getName();
        ArrayList<LinkField> list = null;
        if (!LINKMAP.containsKey(key)) {
            synchronized (LINKMAP) {
                if (!LINKMAP.containsKey(key)) {
                    list = new ArrayList<LinkField>();
                    Field[] flds = ((Table)table).getField();
                    for (Field f : flds) {
                        String lt = f.getReferenceTable();
                        if (StringUtil.isEmpty(lt))
                            continue;
                        Table t = ConfigUtil.getTable(lt);
                        Field fld = ConfigUtil.getFieldByName(t, DataConstant.USEDSTATUS, true);
                        if (fld == null)
                            continue;
                        LinkField lf = new LinkField();
                        lf.field = f.getName();
                        lf.linkTable = lt;
                        // lf.linkIDName = t.getId().getName();
                        list.add(lf);
                    }
                    LINKMAP.put(key, list);
                }
            }
        } else
            list = LINKMAP.get(key);
        return list;
    }
    /**
     * @param tableName
     * @return String[]
     */
    public final static String[] getSameDBTableNames(String tableName) {
        String realName = getRealTableName(tableName);
        HashSet<String> set = REALMAP.get(realName);
        if (set != null)
            return set.toArray(new String[0]);
        return new String[0];
    }
    /**
     * @param realName
     * @return String[]
     */
    public final static String[] getSameDBTableNamesByReal(String realName) {
        HashSet<String> set = REALMAP.get(realName);
        if (set != null)
            return set.toArray(new String[0]);
        return new String[0];
    }
    /**
     * @param rootConfig
     * @throws Throwable
     */
    public final static synchronized void loadRootConfig(File rootConfig) throws Throwable {
        pInit();
        clearAllConfigCache(); 
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(rootConfig), "utf-8"));
            // 实例转换
            // Unmarshaller unmarshal = new Unmarshaller(fr);
            // unmarshal.(DataConstant.CHARSET);
            // unmarshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
            // unmarshal.setSuppressXSIType(true);
            // 静态转换
            ROOT = (Config) org.exolab.castor.xml.Unmarshaller.unmarshal(Config.class, reader);
            // Marshaller.marshal();
        } finally {
        	CloseUtil.close(reader);
        }
    }
    /**
     * @param file
     * @param isReload
     */
    public final static synchronized void loadTableConfig(File file, boolean isReload) {
        pInit();
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
//            Haiyan genmis;
//            if (!StringUtil.isEmpty(xsdURL)) {
//				// 实例转换
//				Unmarshaller unmarshal = new Unmarshaller(Haiyan.class);
////				unmarshal.set(DataConstant.CHARSET);
//				unmarshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
//				unmarshal.setSuppressXSIType(true);
//				Haiyan genmis = unmarshal.unmarshal(Haiyan.class);
//            }
            // 静态转换
            Haiyan genmis = (Haiyan) org.exolab.castor.xml.Unmarshaller.unmarshal(Haiyan.class, reader);
            // Marshaller.marshal();
            Table[] tables = genmis.getTable();
            if (tables != null) {
                String tableName;
                File f;
                for (Table table:tables) {
                    tableName = table.getName().toUpperCase();
                    f = file.getAbsoluteFile();
                    Table t = (Table)DATACACHE.getTable(tableName);
                    if (t != null && !isReload) {
                        File tf = DATACACHE.getTableFile(tableName);
                        if (tf != null && tf.exists()) {
                            DebugUtil.error(">loaded config file:" + f + ", tableName=" + tableName);
                            throw new Warning(SysCode.create(null, 100056, "config_conflict", new String[] { table.getName() }));
                        }
                    }
                    DATACACHE.setTable(tableName, table); // 先放到缓存区 让initByConfig可以取到
                    DATACACHE.setTableFile(tableName, f);
                    initByConfig(table);
                    DATACACHE.setTable(tableName, table); // initByConfig后修改的配置重新放到缓存区
                    DATACACHE.setTableFile(tableName, f);
                    DebugUtil.debug(">loading table success:" + tableName + "," + f);
                    tableKeys = null;
                }
            }
        } catch (Throwable ex) {
            throw new Warning(ex, SysCode.create(null, 100055, "error_read_config", new String[] { file.getName() }));
        } finally {
        	CloseUtil.close(reader);
        }
    }/**
     * @param file
     * @param isReload
     */
    public final static synchronized void loadBillConfig(File file, boolean isReload) {
        pInit();
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
//            Haiyan genmis;
//            if (!StringUtil.isEmpty(xsdURL)) {
//				// 实例转换
//				Unmarshaller unmarshal = new Unmarshaller(Haiyan.class);
////				unmarshal.set(DataConstant.CHARSET);
//				unmarshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
//				unmarshal.setSuppressXSIType(true);
//				Haiyan genmis = unmarshal.unmarshal(Haiyan.class);
//            }
            // 静态转换
            Haiyan genmis = (Haiyan) org.exolab.castor.xml.Unmarshaller.unmarshal(Haiyan.class, reader);
            // Marshaller.marshal();
            Bill[] bills = genmis.getBill();
            if (bills != null) {
                String billName;
                File f;
                for (Bill bill:bills) {
                    billName = bill.getName().toUpperCase();
                    f = file.getAbsoluteFile();
                    Bill t = (Bill)DATACACHE.getBill(billName);
                    if (t != null && !isReload) {
                        File tf = DATACACHE.getBillFile(billName);
                        if (tf != null && tf.exists()) {
                            DebugUtil.error(">loaded config file:" + f + ", billName=" + billName);
                            throw new Warning(SysCode.create(null, 100056, "config_conflict", new String[] { bill.getName() }));
                        }
                    }
                    DATACACHE.setBill(billName, bill); // 先放到缓存区 让initByConfig可以取到
                    DATACACHE.setBillFile(billName, f);
                    initByConfig(bill);
                    DATACACHE.setBill(billName, bill); // initByConfig后修改的配置重新放到缓存区
                    DATACACHE.setBillFile(billName, f);
                    DebugUtil.debug(">loading bill success:" + billName + "," + f);
                    tableKeys = null;
                }
            }
        } catch (Throwable ex) {
            throw new Warning(ex, SysCode.create(null, 100055, "error_read_config", new String[] { file.getName() }));
        } finally {
        	CloseUtil.close(reader);
        }
    }
    private static void initByConfig(Bill bill) {
    	
    }
    private static void initByConfig(Table table) {
        Table sysTable = getTable("SYS", false, true); // getNvlTable
        if (sysTable != null) { // 优先处理sys的
            for (PluginInterceptor inter : sysTable.getPluginInterceptor()) {
                if ("initTable".equalsIgnoreCase(inter.getPluginName())
                   || "__initTable" .equalsIgnoreCase(inter.getPluginName())) {
                	if ("exp".equalsIgnoreCase(inter.getMethodName())) {
                		try {
                    		if (EXPUTIL==null)
                    			throw new Warning(SysCode.SysCodeMessage.ERROR_1002);
//                			String s = inter.getParameter();
//                			if (StringUtil.isEmpty(s))
//                				s = inter.getContent();
                			EXPUTIL.evalExp("InitTable("+table.getName()+")");
                		}catch (Throwable ex) {
	                        DebugUtil.error(ex);
	                    }
                	} else {
	                    try {
	                        InvokeUtil.invoke(inter.getClassName(),
	                                inter.getMethodName(),
	                                new Class[] { Table.class },
	                                new Object[] { table });
	                    } catch (Throwable ex) {
	                        DebugUtil.error(ex);
	                    }
                	}
                }
            }
        }
        // NOTICE 暂时不提供速度太慢了
        // // else {
        // for (PluginInterceptor inter : table.getPluginInterceptor()) {
        // if ("initTable".equalsIgnoreCase(inter.getPluginName())
        // || "__initTable".equalsIgnoreCase(inter.getPluginName())) {
        // try {
        // InvokeUtil.invoke(inter.getClassName(),
        // inter.getMethodName(), new Class[] { Table.class },
        // new Object[] { table });
        // } catch (Throwable ex) {
        // // ex.printStackTrace();
        // DebugUtil.error(ex);
        // }
        // }
        // }
        // // }
    }
    /**
     * @param xml
     * @return Table
     */
    public final static Table xml2Table(String xml) throws Throwable {
        pInit();
        Reader reader = null;
        try {
            // DebugUtil.debug(">loading table file:" + file.getAbsoluteFile());
            // fr = new FileReader(file);
            reader = new StringReader(xml);
            // 实例转换
            // Unmarshaller unmarshal = new Unmarshaller(fr);
            // unmarshal.(DataConstant.CHARSET);
            // unmarshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
            // unmarshal.setSuppressXSIType(true);
            // Haiyan genmis = unmarshal.xxx(Haiyan.class);
            // 静态转换
            Haiyan genmis = (Haiyan) org.exolab.castor.xml.Unmarshaller.unmarshal(Haiyan.class, reader);
            // Marshaller.marshal();
            // Table[] newTables = genmis.getTable();
            // if (newTables != null) {
            // for (int j = 0; j < newTables.length; j++) {
            // String tableName = newTables[j].getName().toUpperCase();
            // }
            // }
            return genmis.getTable()[0];
        }
        finally {
        	CloseUtil.close(reader);
        }
    }
//	/**
//	 * @return HashMap
//	 * @throws Throwable
//	 */
//	public static final HashMap<String, Table> getTables() throws Throwable {
//		pInit();
//		return tables;
//	}
//	/**
//	 * @return HashMap
//	 * @throws Throwable
//	 */
//	public static final HashMap<String, File> getTableFiles() throws Throwable {
//		pInit();
//		return tableFiles;
//	}
	/**
	 * @return File[]
	 * @throws Throwable
	 *             {
	 */
	public static final File[] getConfigFiles() throws Throwable {
		String pathName = getConfigHome();
		return FileUtil.getFilesByPath(pathName, ".xml");
	}
	/**
	 * @return File[]
	 * @throws Throwable
	 */
	private static File[] getSYSTableFiles() throws Throwable {
		String pathName = PathUtil.getHome() + File.separator +"WEB-INF" + File.separator + "systableconfig";
		return FileUtil.getFilesByPath(pathName, ".xml");
	}
	/**
	 * @param pathName
	 * 
	 * @return ArrayList
	 * @throws Throwable
	 */
	static ArrayList<File> getPattern(String pathName) throws Throwable {
		File path = new File(pathName);
		final ArrayList<File> list = new ArrayList<File>();
		File[] xmls = path.listFiles(new FileFilter() {
			public boolean accept(File pathname) {//
				if (pathname.isFile()
						&& pathname.getName().toLowerCase().endsWith(".xml")) {
					return true;
				} else {
					if (pathname.isDirectory()) {
						try {
							list.addAll(getPattern(pathname.getAbsolutePath()));
						} catch (Throwable ignore) {
							ignore.printStackTrace();
						}
					}
					return false;
				}
			}
		});
		for (int i = 0; i < xmls.length; i++) {
			list.add(xmls[i]);
		}
		return list;
	}
    /**
     * @return String[]
     */
    public final static String[] getAllTableKeys() {
        if (tableKeys == null)
            tableKeys = DATACACHE.getAllTableKeys();
        return tableKeys;
    }
    /**
     * @param tableName
     * @return Table
     */
    public static final Table getTable(String tableName) {
        return getTable(tableName, false, false);
    }
    /**
     * @param tableName
     *            tableName
     * @param checkRealTable
     *            checkRealTable 是否冗余检查realtable
     * @param returnNull
     *            returnNull 是否可返回空
     * @return Table
     */
    public final static Table getTable(String tableName, boolean checkRealTable, boolean returnNull) {
        pInit();
        if (tableName == null) {
            if (returnNull)
                return null;
            else
                throw new Warning(SysCode.create(null, 100057, "table_not_found", new String[] { "#Null TableName#" }));
        }
        tableName = tableName.toUpperCase();
        Table result = (Table)DATACACHE.getTable(tableName);
        if (result == null) {
            synchronized (ConfigUtil.class) {
                if (result == null) {
                    // 不检索realTable
                    if (!checkRealTable) {
                        if (returnNull)
                            return null;
                        throw new Warning(SysCode.create(null, 100057, "table_not_found", new String[] { tableName }));
                    } else {
                        for (String sKey : getAllTableKeys()) {
                            Table tbl = (Table)DATACACHE.getTable(sKey);
                            if (tableName.equals(tbl.getRealName()))
                                return tbl;
                        }
                        if (returnNull)
                            return null;
                        throw new Warning(SysCode.create(null, 100057, "table_not_found", new String[] { tableName }));
                    }
                }
            }
        }
        return result;
    }
    /**
     * @param billName
     * @return Table
     */
    public static final Bill getBill(String billName) {
        return getBill(billName, false, false);
    }
    /**
     * @param billName
     *            billName
     * @param checkRealBill
     *            checkRealBill 是否冗余检查realbill
     * @param returnNull
     *            returnNull 是否可返回空
     * @return Table
     */
    public final static Bill getBill(String billName, boolean checkRealBill, boolean returnNull) {
        pInit();
        if (billName == null) {
            if (returnNull)
                return null;
            else
                throw new Warning(SysCode.create(null, 100057, "bill_not_found", new String[] { "#Null BillName#" }));
        }
        billName = billName.toUpperCase();
        Bill result = (Bill)DATACACHE.getBill(billName);
        if (result == null) {
            synchronized (ConfigUtil.class) {
                if (result == null) {
                    // 不检索realTable
                    if (!checkRealBill) {
                        if (returnNull)
                            return null;
                        throw new Warning(SysCode.create(null, 100057, "table_not_found", new String[] { billName }));
                    } else {
                        for (String sKey : getAllTableKeys()) {
                        	Bill tbl = (Bill)DATACACHE.getBill(sKey);
                            if (billName.equals(tbl.getRealName()))
                                return tbl;
                        }
                        if (returnNull)
                            return null;
                        throw new Warning(SysCode.create(null, 100057, "table_not_found", new String[] { billName }));
                    }
                }
            }
        }
        return result;
    }
//	/**
//	 * 获取table,可返回为空
//	 * 
//	 * @param tableName
//	 * @return Table
//	 */
//	public final static Table getNvlTable(String tableName) {
//		return getNvlTable(tableName, false);
//	}
//	/**
//	 * @param tableName
//	 * @param checkRealTable
//	 * @return Table
//	 */
//	public final static Table getNvlTable(String tableName,
//			boolean checkRealTable) {
//		pInit();
//		tableName = tableName.toUpperCase();
//		Table result = HyCache.getTable(tableName);
//		if (result == null) {
//			// 不检索realTable
//			if (!checkRealTable) {
//				return null;
//			} else {
//				// Iterator<String> iter = tables.keySet().iterator();
//				// while (iter.hasNext()) {
//				// Object key = iter.next();
//				for (String sKey : getAllTableKeys()) {
//					Table tbl = HyCache.getTable(sKey);
//					if (tableName.equals(tbl.getRealName()))
//						return tbl;
//				}
//			}
//		}
//		return result;
//	}
    /**
     * @param tableName
     * @return File
     */
    public static final File getTableFile(String tableName) {
        pInit();
        tableName = tableName.toUpperCase();
        File result = DATACACHE.getTableFile(tableName);
        if (result == null) {
            throw new Warning(SysCode.create(null, 100057, "table_not_found", new String[] { tableName }));
        }
        return result;
    }
    /**
     * @param table
     * @param fieldName
     * @param returnNull
     * @return Field
     */
    public static final AbstractField getAbsFieldByName(Table table, String fieldName, boolean returnNull) {
        pInit();
        for (Field fld : table.getField()) {
            if (fld.getName().equals(fieldName)) {
                return fld;
            }
        }
        if (table.getId().getName().equalsIgnoreCase(fieldName))
            return table.getId();
        DebugUtil.debug(">getAbsFieldByName,table=" + table.getName()
                + ",fieldName=" + fieldName);
        if (returnNull)
            return null;
        throw new Warning(null, 100058, "field_not_found", new String[] { table.getName(), fieldName });

    }
    /**
     * @param table
     * @param fieldName
     * @return Field
     */
    public static final AbstractField getAbsFieldByName(Table table, String fieldName) {
        return getAbsFieldByName(table, fieldName, false);
    }
    /**
     * @param table
     * @param fieldName
     * @return
     */
    public static final AbstractCommonFieldJavaTypeType getFieldType(Table table, String fieldName) {
        Field fld = getFieldByName(table, fieldName, true);
        if (fld != null)
            fld.getJavaType();
        return AbstractCommonFieldJavaTypeType.STRING;
    }
//    /**
//     * @param table
//     * @param fieldName
//     * @return Field
//     */
//    public static final Field getNvlFieldByName(Table table, String fieldName) {
//        // pInit();
//        // for (Field fld : table.getField()) {
//        // if (fld.getName().equals(fieldName)) {
//        // return fld;
//        // }
//        // }
//        return getFieldByName(table, fieldName, true);
//    }
    /**
     * @param table
     * @param fieldName
     * @param returnNull
     * @return Field
     */
    public static final Field getFieldByName(Table table, String fieldName,
            boolean returnNull) {
        // Field field = null;
        // if (fieldName.equals(table.getId().getName())) {
        // return table.getId();
        // } else {
        pInit();
        // Field[] fields = table.getField();
        // for (int i = 0; i < fields.length; i++) {
        // if (fields[i].getName().equals(fieldName)) {
        // field =
        // return fields[i];
        // }
        // }
        for (Field fld : table.getField()) {
            if (fld.getName().equals(fieldName)) {
                return fld;
            }
        }
        // }
        DebugUtil.debug(">getFieldByName,table=" + table.getName()
                + ",fieldName=" + fieldName);
        if (returnNull)
            return null;
        throw new Warning(null, 100058, "field_not_found",
                new String[] { table.getName(), fieldName });

    }
    /**
     * @param table
     * @param fieldName
     * @return Field
     */
    public static final Field getFieldByName(Table table, String fieldName) {
        return getFieldByName(table, fieldName, false);
    }
    /**
     * @param jndiName
     * @return jndi
     * @throws NamingException
     */
    public final static Object getJNDI(String jndiName) throws NamingException {
        InitialContext ctx = new InitialContext();
        return ctx.lookup(jndiName);
    }
    /**
     * @param table
     * @return boolean
     */
    public final static boolean isTreeTable(Table table) {
        pInit();
        for (Field fld : table.getField()) {
            if (fld.getReferenceTable() != null) {
                Table refTable = getTable(fld.getReferenceTable());
                if (getRealTableName(refTable).equals(getRealTableName(table)))
                    return true;
            }
        }
        return false;
    }
    /**
     * @param table
     * @return table
     * @throws Throwable
     */
    public static final Table getParentTable(Table table) throws Throwable {
        pInit();
        if (!StringUtil.isBlankOrNull(table.getParentTable()))
            return getTable(table.getParentTable());
        Table uTable = null;
        Table uChildTable = null;
        ArrayList<Table> list = new ArrayList<Table>();
        // HashMap<?, ?> tables = getAllTableKeys();
        // for (Iterator<?> itr = tables.keySet().iterator(); itr.hasNext();) {
        // unitTable = (Table) tables.get(itr.next());
        for (String sKey : getAllTableKeys()) {
            uTable = getTable(sKey);// MyCache.getTable(sKey);
            // NOTICE 是否会形成死循环
            if (!StringUtil.isBlankOrNull(uTable.getChildTable())) {
                uChildTable = getTable(uTable.getChildTable());
                // String unitRealChildTableName =
                // ConfigUtil.getRealTableName(unitChildTable);
                if (table != null && uChildTable != null)
                    if (table.getName().equalsIgnoreCase(uChildTable.getName())) {
                        // if
                        // (realTableName.equalsIgnoreCase(unitRealChildTableName))
                        // { list.add(unitTable);
                        if (list.size() > 1) {
                            break;
                        }
                    }
            }
        }
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            return list.get(0);
            // String parentRealTableName = ConfigUtil.getRealTableName((Table)
            // list.get(0));
            // return ConfigUtil.getTable(parentRealTableName);
        }
        return null;
    }
    /**
     * @param parentTable
     * @param childTable
     * @return Field
     */
    public static final Field searchChildTableRefField(Table parentTable,
            Table childTable) {
        pInit();
        Field result = null;
        if (!StringUtil.isBlankOrNull(parentTable.getChildTableRefField())) {
            result = ConfigUtil.getFieldByName(childTable, parentTable.getChildTableRefField());
            return result;
        }
        Field[] fs = childTable.getField();
        for (Field field:fs) {
            if (StringUtil.isBlankOrNull(field.getReferenceTable()))
                continue;
            if (parentTable.getName().equals(field.getReferenceTable())) { // 先判断虚拟名称
                result = field;
                break;
            }
            String[] vts = field.getVisualTable();
            if (vts!=null) { // 先判断虚拟名称
            	for (String vt:vts) {
            		if (parentTable.getName().equals(vt)) {
		                result = field;
		                break;
            		}
            	}
            }
        }
        if (result == null) {
        	for (Field field:fs) {
                if (StringUtil.isBlankOrNull(field.getReferenceTable()))
                    continue;
                String realParentTableName = getRealTableName(parentTable);
                if (realParentTableName.equals(field.getReferenceTable())) {
                    result = field;
                    break;
                }
            }
        }
        return result;
    }
    /**
     * @param parentTable
     * @return Field
     */
    public static final Field searchChildTableRefField(Table parentTable) {
        pInit();
        if (StringUtil.isBlankOrNull(parentTable.getChildTable()))
            return null;
        // String[] cTables =
        // StringUtil.getDivBySTK(parentTable.getChildTable(),",");
        // for (int i=0;i<cTables.length;i++){
        Table childTable = getTable(parentTable.getChildTable());
        return searchChildTableRefField(parentTable, childTable);
        // }
    }
    /**
     * @param parentTable
     * @return String
     */
    public static final String getChildTableRefFieldName(Table parentTable) {
        pInit();
        if (parentTable.getChildTableRefField() != null)
            return parentTable.getChildTableRefField();
        // String childRefFieldName = parentTable.getChildTableRefField();
        String childRefFieldName = null;
        Field field = ConfigUtil.searchChildTableRefField(parentTable);
        if (field != null) {
            childRefFieldName = field.getName();
        }
        // else {
        if (childRefFieldName == null)
            throw new Warning(null, 100060, "error_use_fatherfield", new String[] { parentTable.getName() });
        // }
        // }
        return childRefFieldName;
    }
    /**
     * @param field
     * @return
     */
    public static final String getReferenceTableName(Field field) {
        pInit();
        Table tbl = getTable(field.getReferenceTable());
        return tbl.getName();
    }
    /**
     * @param field
     * @return field
     */
    public static final String getReferenceFieldName(Field field) {
        pInit();
        String refName = field.getReferenceField();
        if (StringUtil.isBlankOrNull(refName)) {
            Table tbl = getTable(field.getReferenceTable());
            refName = tbl.getId().getName();
        }
        return refName;
    }
    /**
     * @param table
     * @return Field[]
     */
    public static final Field[] getMappingTableFileds(Table table) {
        pInit();
        ArrayList<Field> result = new ArrayList<Field>();
        Field[] fields = table.getField();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getMappingTable() != null)
                result.add(fields[i]);
        }
        return result.toArray(new Field[0]);
    }
    /**
     * @param table
     * @return Field[]
     */
    public static final Field[] getOne2oneTableFileds(Table table) {
        pInit();
        ArrayList<Field> result = new ArrayList<Field>();
        Field[] fields = table.getField();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getOne2oneTable() != null)
                result.add(fields[i]);
        }
        return result.toArray(new Field[0]);
    }
    /**
     * @return PropertyResourceBundle
     * @throws Throwable
     */
    public final static ResourceBundle getPropertyBundle(String bundName)
            throws Throwable {
        return PropUtil.getPropertyBundle(bundName);
    }
//     /**
//     * @param mainTable
//     * @param field
//     * @return String
//     * @throws Throwable
//     */
//     public static String getTableIndex(Table mainTable, Field field)
//     throws Throwable {
//     //
//     String[][] flds = getIndexFields(mainTable);
//     for (String[] fld : flds) {
//     String fldName = fld[0].substring(fld[0].indexOf(".") + 1);
//     if (field.getName().equalsIgnoreCase(fldName))
//     return fld[0].substring(fld[0].indexOf("."));
//     }
//     return "t_1";
//     }
//    /**
//     * @param mainTable
//     * @param field
//     * @return String
//     * @throws Throwable
//     */
//    public final static String getIndexName(Table mainTable, Field field)
//            throws Throwable {
//        String[][] flds = getIndexFields(mainTable, field);
//        if (flds.length > 0 && flds[0].length > 0)
//            return flds[0][0];
//        return null;
//    }
//    /**
//     * SQL字段名，注释（包含关联表字段）选项
//     * 
//     * @param table
//     * @param field
//     * @return String[][] examples:[["t_1.NAME","主表名称"],["t_2.CODE","机构编码"]...]
//     * @throws Throwable
//     */
//    public final static String[][] getIndexFields(Table table, Field field)
//    // IContext context,
//            throws Throwable {
//        // AbstractOperationsRender render =
//        // OperationsRenderCreator.createRender(context, table);
//        Field[] fields = table.getField();// render.getVisibleField();
//        // RenderUtil.getQueryVisibleField(table, null);
//        HashMap<String, String> visFlds = new HashMap<String, String>();
//        for (Field fld : fields) {
//            visFlds.put(fld.getName(), fld.getName());
//        }
//        ArrayList<String[]> list = new ArrayList<String[]>();
//        PrimaryTable pTable = new PrimaryTable(
//                ConfigUtil.getRealTableName(table), ""); // realTable,selectColumnSQL
//        RetrieveTemplate temp = pGetDealTemp(visFlds, field);
//        temp.deal(table, new Object[] { list, pTable });
//        return list.toArray(new String[0][0]);
//    }
//    /**
//     * @return RetrieveTemplate
//     */
//    private static TableDBTemplate pGetDealTemp(
//            final HashMap<String, String> visFld, final Field fld) {
//        // StringBuffer cols = new StringBuffer();
//        // PrimaryTable pTable = new PrimaryTable(ConfigUtil.getRealTableName(table), "");
//        // dealer.deal(table, new Object[] { cols, pTable });
//        TableDBTemplate dealer = new TableDBTemplate() {
//            @Override
//            protected void dealWithIdField(Table table, int index,
//                    Object[] globalVars) {
//            }
//            @SuppressWarnings("unchecked")
//            @Override
//            protected int dealWithDisplayField(Table table, int tableIndex,
//                    int index, Field field, Object[] globalVars) {
//                if (fld != null && fld != field)
//                    return index;
//                if (visFld.get(field.getName()) == null)
//                    return index;
//                @SuppressWarnings("rawtypes")
//                ArrayList cols = (ArrayList) globalVars[0];
//                PrimaryTable pTable = (PrimaryTable) globalVars[1];
//                String[] disFieldNames = ConfigUtil.getDisplayRefFields(field);
//                // Table refTable = ConfigUtil.getTable(field.getCommon()
//                // .getReferenceTable());
//                for (int i = 0; i < disFieldNames.length; i++) {
//                    // DebugUtil.debug(">disFieldNames[" + i + "]="
//                    // + pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex) + "." + disFieldNames[i]);
//                    // Field displayField = ConfigUtil.getFieldByName(refTable, disFieldNames[i]);
//                    String value = pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex) + "." + disFieldNames[i];
//                    cols.add(new String[] { value, field.getDescription() });
//                }
//                // DebugUtil.debug(">cols=" + cols.toString() );
//                return index;
//            }
//            protected void dealWithReferenceTable(Table table, int index,
//                    Field field, Object[] globalVars) {
//                if (fld != null && fld != field)
//                    return;
//                PrimaryTable pTable = (PrimaryTable) globalVars[1];
//                String refTableName = field.getCommon().getReferenceTable();
//                pTable.addJoinedTable(new LeftOuterJoinedTable(
//                		ConfigUtil.getRealTableName(refTableName), 
//                		ConfigUtil.getReferenceFieldName(field), 
//                		pTable.getTableAlias(), field.getName(), pTable, pTable.getIndex() + index));
//            }
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void dealWithAssociatedField(Table table, int tableIndex,
//                    int index, Field mainField, Field associatedField,
//                    Object[] globalVars) {
//                if (fld != null && fld != mainField)
//                    return;
//                if (visFld.get(mainField.getName()) == null)
//                    return;
//                @SuppressWarnings("rawtypes")
//                ArrayList cols = (ArrayList) globalVars[0];
//                PrimaryTable pTable = (PrimaryTable) globalVars[1];
//                // DebugUtil.debug(">associatedField:"
//                // + pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex) + "."
//                // + associatedField.getCommon().getDisplay ReferenceField() + " f_" + index);
//                if (associatedField.getCommon().getDisplayReferenceField() == null)
//                    throw new Warning(new TransCode(100108, "disreferencefield_is_null",
//                            new String[] { table.getName(), associatedField.getName() }));
//                String value = pTable.getTableAliasByIndex(pTable.getIndex() + tableIndex)
//                        + "." + ConfigUtil.getDisplayRefFields(associatedField)[0];
//                cols.add(new String[] { value, mainField.getDescription() });
//            }
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void dealWithGeneralField(Table table, int index,
//                    Field field, Object[] globalVars) {
//                if (fld != null && fld != field)
//                    return;
//                if (visFld.get(field.getName()) == null)
//                    return;
//                if (field.getCommon().getReferenceTable() != null)
//                    return;
//                @SuppressWarnings("rawtypes")
//                ArrayList cols = (ArrayList) globalVars[0];
//                PrimaryTable pTable = (PrimaryTable) globalVars[1];
//                //
//                String value = pTable.getFirstTableAlias() + "." + field.getName();
//                cols.add(new String[] { value, field.getDescription() });
//            }
//            @Override
//            protected void dealWithComputeField(Table table, int index,
//                    Field field, Object[] globalVars) {
//                if (fld != null && fld != field)
//                    return;
//                // if (field.getDisplayOnly())
//                // return;
//                // ArrayList cols = (ArrayList) globalVars[0];
//                // PrimaryTable pTable = (PrimaryTable) globalVars[1];
//                // String value = pTable.getFirstTableAlias() + "." + field.getName();
//                // cols.add(new String[] { value, field.getDescription() });
//            }
//            @Override
//            protected void dealWithLazyLoadField(Table table, Field field,
//                    Object[] globalVars) throws Throwable {
//                if (fld != null && fld != field)
//                    return;
//            }
//            @Override
//            protected void dealWithMappingField(Table table, Field field,
//                    Object[] globalVars) throws Throwable {
//                if (fld != null && fld != field)
//                    return;
//            }
//        };
//        return dealer;
//    }
    /**
     * @param field
     * @return
     */
    public final static boolean isRichSelect(Field field) {
        return !StringUtil.isEmpty(field.getReferenceTable());
//            && (field.getCommon().getType().equals(ComponentTypeType.RICHSELECT)
//            || field.getCommon().getType().equals(ComponentTypeType.COMBO)
//            || field.getCommon().getType().equals(ComponentTypeType.TREECOMB) 
//            || field.getCommon().getType().equals(ComponentTypeType.TREEPCOMB));
        // || field.getCommon().getType().equals(ComponentTypeType.GRID));
    }
    /**
     * @param table
     * @return
     * @throws Throwable
     */
    public final static Table copyTable(Table table) throws Throwable {
        // Haiyan
        Haiyan genmis = new Haiyan();
        genmis.addTable(table);
        // Table table2 = genmis.
        String xml = saveTable(genmis);
        return xml2Table(xml);
    }
    /**
     * @param field
     * @return String[]
     */
    public final static String[] getDisplayRefFields(AbstractField field) {
        if (field instanceof Field && ((Field)field).getDisplayReferenceField() != null)
            return ((Field)field).getDisplayReferenceField().split(",");
        else {
        	if (field.getOptionCount()>0)
        		return new String[]{NamingUtil._OPTION};
            return new String[0];
        }
    }
    /**
     * @param field
     * @return String[]
     */
    public final static String[] getAssociatedFields(AbstractField field) {
        if (field instanceof Field && ((Field)field).getAssociatedFields() != null)
            return ((Field)field).getAssociatedFields().split(",");
        else
            return new String[0];
    }
    private static String CONFIGHOME = null;
    /**
     * @return String
     * @throws Throwable
     */
    public final static String getConfigHome() throws Throwable {
        if (CONFIGHOME != null)
            return CONFIGHOME;
        String configPath = getConfig().getTableConfigFilePath().getValue();
        if (configPath.indexOf("/") >= 0 || configPath.indexOf("\\") >= 0) {
            String fileName = configPath;
            fileName = StringUtil.replaceAll(fileName, "\\", File.separator);
            fileName = StringUtil.replaceAll(fileName, "/", File.separator);
            CONFIGHOME = fileName;
        } else {
            String fileName = PathUtil.getHome() + File.separator + configPath;
            fileName = StringUtil.replaceAll(fileName, "\\", File.separator);
            fileName = StringUtil.replaceAll(fileName, "/", File.separator);
            CONFIGHOME = fileName;
        }
        return CONFIGHOME;
    }
    /**
     * @param table
     * @return boolean
     */
    public static boolean isDecimalPK(Table table) {
        return table.getId().getJavaType().equals(AbstractCommonFieldJavaTypeType.BIGDECIMAL);
    }
    /**
     * @param table
     * @param ID
     * @return
     */
    public static String transIDValue(Table table, String ID) {
    	ID = StringUtil.unSqlInjection(ID);
        return isDecimalPK(table) ? ID : "'" + ID + "'";
    }
//	/**
//	 * @param billConfig
//	 * @param tableIndex
//	 * @return
//	 */
//	public static int getTableInBill(Bill billConfig, int tableIndex) {
//		BillTable[] billTables = billConfig.getBillTable();
//		for (BillTable billTable:billTables) {
//			if (billTable.getTableIndex()==tableIndex) {
//				return 
//			}
//		}
//		return 0;
//	}
	public static IBillIDConfig getBillIDConfig(IBillConfig billConfig,
			int tableIndex) {
		IBillIDConfig[] billids = billConfig.getBillID();
		for (IBillIDConfig billid:billids) {
			if (billid.getTableIndex()==tableIndex)
				return billid;
		}
		return null;
	}
}