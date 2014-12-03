package haiyan.common.intf.database;

import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.session.IDBSession;

/**
 * DB管理器
 * 
 * @author ZhouXW
 *
 */
public interface IDBManager extends IDBSession {
	
	IDBRecord createRecord();
	IDBRecord createRecord(Class<?> clz) throws InstantiationException, IllegalAccessException;
	IDBClear getClear();

}
