package haiyan.common;

import haiyan.common.exception.Warning;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

public class PropUtil {

	static class MyPropertyResourceBundle extends PropertyResourceBundle {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public MyPropertyResourceBundle(ResourceBundle rs) throws IOException {
			super(new InputStream(){
				@Override
				public int read() throws IOException {
					return -1;
				}});
			lookup = new HashMap(System.getProperties()); // 优先级最低
			lookup.putAll(System.getenv()); // OS Environment Variables
			Iterator<String> keys = rs.keySet().iterator();
			while(keys.hasNext()) {
				String k = keys.next();
				lookup.put(k, rs.getObject(k));
			}
		}
		public Object handleGetObject(String key) {
			if (key == null) {
				throw new NullPointerException();
			}
			return lookup.get(key);
		}
		@SuppressWarnings("restriction")
		public Enumeration<String> getKeys() {
			ResourceBundle parent = this.parent;
			return new sun.util.ResourceBundleEnumeration(lookup.keySet(),
					(parent != null) ? parent.getKeys() : null);
		}
		protected Set<String> handleKeySet() {
			return lookup.keySet();
		}
		// ==================privates====================
		private Map<String, Object> lookup;
	}
	public static String APP_BUNDLE_NAME = "AppResources";
	public static String INVOKE_BUNDLE_NAME = "AppInvokes";
	private static ResourceBundle BUNDLE = null;
    /**
	 * @return ResourceBundle(PropertyResourceBundle) 
	 */
	public final static ResourceBundle getPropertyBundle() {
		if (BUNDLE==null)
			synchronized(PropUtil.class) {
				if (BUNDLE==null)
					try {
						String s = System.getProperty("APP_BUNDLE_NAME");
						if (StringUtil.isEmpty(s))
							s = APP_BUNDLE_NAME;
						ResourceBundle rs = ResourceBundle.getBundle(s, 
								Locale.getDefault(), Thread.currentThread().getContextClassLoader());
						BUNDLE = new MyPropertyResourceBundle(rs);
					} catch (IOException e) {
						throw Warning.getWarning(e);
					}
			}
		return BUNDLE;
	}
    /**
     * @param bundName
     * @param key
     * @return
     */
    public static String getProperty(String bundName, String key, String def) {
        try {
        	ResourceBundle b;
        	if (!StringUtil.isEmpty(bundName))
        		b = getPropertyBundle(bundName);
        	else
	        	b = getPropertyBundle();
            if (!b.containsKey(key)) {
            	return def;
            }
            return b.getString(key);
        } catch (Throwable ignore) {
        	ignore.printStackTrace();
        	return def;
        }
    }
    /**
     * @return PropertyResourceBundle
     * @throws Throwable
     */
    public final static ResourceBundle getPropertyBundle(String bundleName)
            throws Throwable {
        // ResourceBundle.getBundle(bundName)
        // ResourceBundle.getBundle(bundName,Locale.getDefault())
        // ResourceBundle.getBundle(bundName,Locale.getDefault(),getLoader()) (PropertyResourceBundle)
		String s = System.getProperty("APP_BUNDLE_NAME");
		if (!StringUtil.isEmpty(bundleName))
			s = bundleName;
		if (StringUtil.isEmpty(s))
			s = APP_BUNDLE_NAME;
        return ResourceBundle.getBundle(s, Locale.getDefault(), Thread.currentThread().getContextClassLoader());
    }
    /**
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return getProperty(null, key, "");
    }
    /**
     * @return PropertyResourceBundle
     * @throws Throwable
     */
    public final static ResourceBundle getInvokePropertyBundle()
            throws Throwable {
        // ResourceBundle.getBundle(bundName)
        // ResourceBundle.getBundle(bundName,Locale.getDefault())
        // ResourceBundle.getBundle(bundName,Locale.getDefault(),getLoader()) (PropertyResourceBundle)
		String s = System.getProperty("INVOKE_BUNDLE_NAME");
		if (StringUtil.isEmpty(s))
			s = INVOKE_BUNDLE_NAME;
        return ResourceBundle.getBundle(s, Locale.getDefault(), Thread.currentThread().getContextClassLoader());
    }
    /**
     * @param key
     * @return
     */
    public static String getInvokeProperty(String key) {
        try {
            ResourceBundle b = getInvokePropertyBundle();
            if (!b.containsKey(key))
                return "";
            return b.getString(key);
        } catch (Throwable ignore) {
        	ignore.printStackTrace();
            return "";
        }
    }
    
}