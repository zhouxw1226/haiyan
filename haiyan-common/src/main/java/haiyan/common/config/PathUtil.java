package haiyan.common.config;

import static haiyan.common.config.DataConstant.HAIYAN_CONFIG_HOME;
import static haiyan.common.config.DataConstant.HAIYAN_HOME;
import static haiyan.common.config.DataConstant.PATH_NAME;
import static haiyan.common.config.DataConstant.UPLOAD_PATH;
import static haiyan.common.config.DataConstant.UPLOAD_PATH_NAME;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import haiyan.common.CloseUtil;
import haiyan.common.PropUtil;
import haiyan.common.StringUtil;

/**
 * @author zhouxw
 */
public class PathUtil {

    private static List<String> classRepository = null;
    /**
     * @return List<String>
     */
    public final static List<String> getClassPaths() {
        if (classRepository == null) {
            classRepository = new ArrayList<String>();
            /** 取环境变量 */
            String classPath = System.getProperty("java.class.path");
            /** 取得该路径下的所有文件夹 */
            if (classPath != null && !"".equals(classPath)) {
                StringTokenizer tokenizer = new StringTokenizer(classPath, File.pathSeparator);
                while (tokenizer.hasMoreTokens()) {
                    classRepository.add(tokenizer.nextToken());
                }
            }
            if (true) // 不显示class path & env
                return classRepository;
            // System.out.println("classPath:"+classPath);
            @SuppressWarnings("unused")
            Map<?, ?> map = System.getenv();
            Iterator<?> iter = map.keySet().iterator();
            System.out.println("System.env:");
            while (iter.hasNext()) {
                Object key = iter.next();
                System.out.print("{"+key+":"+map.get(key)+"},");
            }
            System.out.println("{}");
            // System.out.println("classPath:"+classPath);
            Properties props = System.getProperties();
            Enumeration<?> enums = props.keys();
            System.out.println("System.props:");
            while (enums.hasMoreElements()) {
                String key = ""+enums.nextElement();
                System.out.println(key+"="+props.getProperty(key));
            }
        }
        return classRepository;
    }
    /**
     * @return String
     * @throws Throwable
     */
    private static final String getPropertyHome() throws Throwable {
        getClassPaths();
        ResourceBundle props = PropUtil.getLocalPropertyBundle(null);
        if (!props.containsKey(PATH_NAME))
        	return null;
        return props.getString(PATH_NAME);
    }
	public static final String getUploadPath(boolean son) {
		return UPLOAD_PATH + File.separator + (son? "upload" + File.separator:"");
	}
	private static String webInfoHome;
//	public static final void setWebInfoHome(String home) {
//		webInfoHome = home;
//	}
	public static final String getWebInfoHome() {
		if (!StringUtil.isEmpty(webInfoHome))
			return webInfoHome;
		return getHome()+File.separator+"WEB-INF";
	}
    public static final Properties getEnvVars() throws Throwable {
    	return ReadEnv.getEnvVars();
    }
    private static String HOME = null;
//    public static final void setHome(String home) {
//    	HOME = home;
//    }
    public static final String getHome() {
    	getClassPaths();
        if (!StringUtil.isEmpty(HOME))
            return HOME;
    	try {
            Properties p = ReadEnv.getEnvVars();
            if (!StringUtil.isEmpty(p.getProperty(HAIYAN_HOME))) {
                HOME = p.getProperty(HAIYAN_HOME);
                System.out.println("#ReadEnv.home:"+HOME);
            }
		} catch (Throwable ignore) {
			ignore.printStackTrace();
		}
        try {
	        if (StringUtil.isEmpty(HOME)) {
	            if (!StringUtil.isEmpty(System.getProperty(HAIYAN_HOME))) {
	                HOME = System.getProperty(HAIYAN_HOME);
	                System.out.println("#SystemProperty.home="+HOME);
	            } else if (!StringUtil.isEmpty(getPropertyHome())) {
	                HOME = getPropertyHome();
	                System.out.println("#Property.home="+HOME);
	            } else {
	            	HOME = System.getProperty("user.dir");
	                System.out.println("#UserDir.home="+HOME);
	            }
	        }
        } catch (Throwable ex) {
        	throw new RuntimeException(ex);
        }
        System.out.println("#Haiyan.home="+HOME);
        if (HOME != null && (HOME.endsWith("\\") || HOME.endsWith("/")))
            HOME = HOME.substring(0, HOME.length() - 1);
        return HOME;
    }
    private static String CONFIG_HOME = null;
    public static final String getConfigHome(String webInfPath) {
    	getClassPaths();
        if (!StringUtil.isEmpty(CONFIG_HOME))
            return CONFIG_HOME;
        synchronized(PathUtil.class) {
        	if (!StringUtil.isEmpty(CONFIG_HOME))
                return CONFIG_HOME;
	    	try {
	            Properties p = ReadEnv.getEnvVars();
	            if (p.containsKey(HAIYAN_CONFIG_HOME)) {
	            	CONFIG_HOME = p.getProperty(HAIYAN_CONFIG_HOME);
	                System.out.println("#ReadEnv.confighome:"+CONFIG_HOME);
	            }
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			}
	        try {
		        if (StringUtil.isEmpty(CONFIG_HOME)) {
		            if (!StringUtil.isEmpty(System.getProperty(HAIYAN_CONFIG_HOME))) {
		            	CONFIG_HOME = System.getProperty(HAIYAN_CONFIG_HOME);
		                System.out.println("#SystemProperty.confighome="+CONFIG_HOME);
		            } else {
	        			String rootName = webInfPath + File.separator + "classes";
	        			File file = new File(rootName);
	        			if (file.exists()) {
		        			CONFIG_HOME = file.getAbsolutePath();
		        			System.out.println("#UserDir.confighome="+CONFIG_HOME);
	        			} else {
							// CONFIG_HOME = getHome();
							// System.out.println("#UserDir.confighome="+CONFIG_HOME);
							System.out.println("#UserDir.confighome is null");
							return null;
	        			}
		            }
		        }
	        } catch (Throwable ex) {
	        	throw new RuntimeException(ex);
	        }
	        System.out.println("#Haiyan.confighome="+CONFIG_HOME);
	        if (CONFIG_HOME != null && (CONFIG_HOME.endsWith("\\") || CONFIG_HOME.endsWith("/")))
	        	CONFIG_HOME = CONFIG_HOME.substring(0, CONFIG_HOME.length() - 1);
	        return CONFIG_HOME;
        }
    }
    public static final String getConfigHome() {
    	return getConfigHome(getWebInfoHome());
    }
    /**
     * @return String
     * @throws Throwable
     */
    public final static String getSkinPath() throws Throwable {
        String value = PropUtil.getProperty("SKIN");
        if (StringUtil.isEmpty(value)) {
            return "defualt";
        }
        return value;
    }
    /**
     * @return String
     * @throws Throwable
     */
    private final static String getUploadRootName() throws Throwable {
        getClassPaths();
    	ResourceBundle rs = PropUtil.getLocalPropertyBundle(null);
    	if (!rs.containsKey(UPLOAD_PATH_NAME))
    		return null;
    	return rs.getString(UPLOAD_PATH_NAME);
    }
    private static String UPLOADPATH = null;
	/**
	 * @return String
	 * @throws Throwable
	 */
	static String getConfigUploadPath() throws Throwable {
		if (!StringUtil.isEmpty(UPLOADPATH))
			return UPLOADPATH;
		UPLOADPATH = getUploadRootName();
		if (StringUtil.isEmpty(UPLOADPATH))
			UPLOADPATH = getHome();
		return UPLOADPATH;
	}
//  /**
//  * @return String
//  * @throws Throwable
//  */
// public final static String getUploadService(IContext context)
//         throws Throwable {
//     try {
//         String uploadService = getPropertyBundle(null).getString(UPLOAD_SERVICE);
//         if (!StringUtil.isBlankOrNull(uploadService))
//             return uploadService;
//     } catch (Throwable ex) {
//         DebugUtil.debug(">use default uploadservice: /haiyan_config");
//     }
//     if (context != null) {
//         int lastIndex = context.getContextName().lastIndexOf("/");
//         return context.getContextName().substring(0, lastIndex) + "/haiyan_config";
//     }
//     throw new Warning("upload service not config.");
// }
//	/**
//	 * 服务器附件绝对路径
//	 * 
//	 * @param context
//	 * @param table
//	 * @param field
//	 * @return String
//	 */
//	public static String getUploadFilePath(IContext context, Table table, AbstractField field) {
//		// TODO 需要从context中根据dsn再到对应目录下取路径
//		// String uploadRootPath = APPLICATION_PATH + File.separator + "upload";
//		String result = getUploadPath(true);
//		// DebugUtil.debug(">table,pathTable,result:" + table.getName() + "," +
//		// field.getComponent().getPathTable() + "," + result);
//		if (field.getComponent() != null 
//				&& !StringUtil.isBlankOrNull(field.getComponent().getPathTable()))
//			result += field.getComponent().getPathTable() + File.separator;
//		else
//			result += ConfigUtil.getRealTableName(table) + File.separator;
//		result += field.getName() + File.separator;
//		return result;
//	}
//	/**
//	 * URL附件相对路径
//	 * 
//	 * @param context
//	 * @param table
//	 * @param field
//	 * @return String
//	 */
//	public static String getUploadFileUrl(IContext context, Table table, AbstractField field) {
//		// TODO 需要从context中根据dsn再到对应目录下取路径
//		// String uploadRootPath = UPLOAD_SERVICE_PATH + File.separator + "upload";
//		String result = "upload" + File.separator;
//		// DebugUtil.debug(">table,pathTable,result:" + table.getName() + "," +
//		// field.getComponent().getPathTable() + "," + result);
//		if (field.getComponent() != null 
//				&& !StringUtil.isBlankOrNull(field.getComponent().getPathTable()))
//			result += field.getComponent().getPathTable() + File.separator;
//		else
//			result += ConfigUtil.getRealTableName(table) + File.separator;
//		result += field.getName() + File.separator;
//		return result;
//	}
    /**
     * @param cls
     * @return String
     */
    public static String getPathFromClass(Class<?> cls) throws IOException {
        String path = null;
        if (cls == null) {
            throw new NullPointerException();
        }
        URL url = getClassLocationURL(cls);
        if (url != null) {
            path = url.getPath();
            if ("jar".equalsIgnoreCase(url.getProtocol())) {
                try {
                    path = new URL(path).getPath();
                } catch (MalformedURLException e) {
                }
                int location = path.indexOf("!/");
                if (location != -1) {
                    path = path.substring(0, location);
                }
            }
            File file = new File(path);
            path = file.getCanonicalPath();
        }
        return path;
    }
    /**
     * 
     * @param relatedPath
     * @param cls
     * @return String
     * @throws IOException
     */
    public static String getFullPathRelateClass(String relatedPath, Class<?> cls)
            throws IOException {
        String path = null;
        if (relatedPath == null) {
            throw new NullPointerException();
        }
        String clsPath = getPathFromClass(cls);
        File clsFile = new File(clsPath);
        String tempPath = clsFile.getParent()+File.separator+relatedPath;
        File file = new File(tempPath);
        path = file.getCanonicalPath();
        return path;
    }
    /**
     * @param cls
     * @return URL
     */
    private static URL getClassLocationURL(final Class<?> cls) {
        if (cls == null)
            throw new IllegalArgumentException("cls lost");
        URL result = null;
        final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
        final ProtectionDomain pd = cls.getProtectionDomain();
        // java.lang.Class contract does not specify
        // if 'pd' can ever be null;
        // it is not the case for Sun's implementations,
        // but guard against null
        // just in case:
        if (pd != null) {
            final CodeSource cs = pd.getCodeSource();
            // 'cs' can be null depending on
            // the classloader behavior:
            if (cs != null)
                result = cs.getLocation();
            if (result != null) {
                // Convert a code source location into a full class file location
                // for some common cases:
                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar")
                            || result.toExternalForm().endsWith(".zip"))
                            result = new URL("jar:"
                                    .concat(result.toExternalForm())
                                    .concat("!/").concat(clsAsResource));
                        else if (new File(result.getFile()).isDirectory())
                            result = new URL(result, clsAsResource);
                    } catch (MalformedURLException ignore) {
                    	ignore.printStackTrace();
                    }
                }
            }
        }
        if (result == null) {
            // Try to find 'cls' definition as a resource;
            // this is not implementations seem to //allow this:
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ? clsLoader.getResource(clsAsResource)
                    : ClassLoader.getSystemResource(clsAsResource);
        }
        return result;
    }
//	public static File getRootFile(String webInfPath) {
//		File file = null;
//		File home = new File(getHome() + File.separator + "config");
//		if (home.exists()) {
//			String rootName = home.getAbsolutePath();
//			file = new File(rootName);
//		} else {
//			if (webInfPath==null)
//				return null;
//			String rootName = webInfPath + File.separator + "classes";
//			file = new File(rootName);
//		}
//		return file;
//	}
	public static File getConfigRootFile(String webInfPath) {
		File file = null;
		if (webInfPath==null)
			webInfPath = getWebInfoHome();
		String configHome = getConfigHome(webInfPath);
		if (configHome!=null) {
			File home = new File(configHome);
			if (home.exists()) {
				String rootName = home.getAbsolutePath();
				file = new File(rootName);
			}
		}
		return file;
	}
}
class ReadEnv {
    final static Properties envVars = new Properties();
    final static Properties getEnvVars() throws Throwable {
        Process p = null;
        Runtime r = Runtime.getRuntime();
        String OS = System.getProperty("os.name").toLowerCase();
        // System.out.println(OS);
        if (OS.indexOf("windows 9") > -1) {
            p = r.exec("command.com /c set");
        } else if ((OS.indexOf("nt") > -1) 
        		|| (OS.indexOf("windows 20") > -1)
                || (OS.indexOf("windows xp") > -1)
                || (OS.indexOf("windows 7") > -1)
                || (OS.indexOf("windows vista") > -1)
                || (OS.indexOf("windows 8.1") > -1)) {
            // thanks to JuanFran for the xp fix!
            p = r.exec("cmd.exe /c set");
        } else {
            // our last hope, we assume Unix (thanks to H. Ware for the fix)
            p = r.exec("env");
        }
        InputStream in = null;
        try {
            in = p.getInputStream();
            InputStreamReader is = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(is);
            String line;
            while ((line = br.readLine()) != null) {
                int idx = line.indexOf('=');
                String key = line.substring(0, idx);
                String value = line.substring(idx + 1);
                envVars.setProperty(key, value);
            }
            return envVars;
        } finally {
            CloseUtil.close(in);
        }
    }
}