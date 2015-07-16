package haiyan.orm.database;

public class DBRecordUtil {
	public static void put(DBRecord record, String key, Object v) {
		record.dataMap.put(key, v);
	}
	public static Object get(DBRecord record, String key) { 
		return record.dataMap.get(key);
	} 
}
