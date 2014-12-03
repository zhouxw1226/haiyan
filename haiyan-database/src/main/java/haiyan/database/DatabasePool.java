/*
 * Created on 2005-2-16
 *
 */
package haiyan.database;

import java.util.Hashtable;

import javax.naming.NameNotFoundException;

/**
 * @author zhouxw
 * 
 */
public class DatabasePool {

	/**
	 * 
	 */
	private static Hashtable<String, SQLDatabase> register = new Hashtable<String, SQLDatabase>(2, 0.75F);

	/**
	 * @param key
	 * @return DatabaseIntf
	 * @throws NameNotFoundException
	 */
	public static SQLDatabase lookup(String key) throws Throwable {
		synchronized (DatabasePool.class) {
			return register.get(key);
		}
	}

	/**
	 * @param key
	 * @param db
	 * @return DatabaseIntf
	 * @throws Throwable
	 */
	public static SQLDatabase bind(String key, SQLDatabase db)
			throws Throwable {
		SQLDatabase source = null;
		// try {
		// source = lookup(key);
		if (lookup(key) == null) {
			synchronized (DatabasePool.class) {
				if (lookup(key) == null) {
					register.put(key, db);
				}
				source = lookup(key);
			}
		} else
			source = lookup(key);
		// } catch (Throwable ex) {
		// throw ex;
		// }
		return source;
	}

	/**
	 * @param name
	 * @param db
	 * @return DatabaseIntf
	 * @throws Throwable
	 */
	public static SQLDatabase rebind(String key, SQLDatabase db)
			throws Throwable {
		unbind(key);
		return bind(key, db);
	}

	/**
	 * @param name
	 * @throws Throwable
	 */
	public static void unbind(String key) throws Throwable {
		register.remove(key);
		// DatabaseIntf db = lookup(key);
		// if (db instanceof HaiyanDatabase) { // haiyan.datasource
		// HaiyanDatabase dbi = (HaiyanDatabase) db;
		// try {
		// dbi.clear();
		// } catch (Exception ex) {
		// throw ex;
		// // e.printStackTrace();
		// } finally {
		// dbi = null;
		// }
		// }
	}

}