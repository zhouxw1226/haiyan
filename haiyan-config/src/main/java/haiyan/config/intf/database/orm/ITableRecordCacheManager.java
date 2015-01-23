package haiyan.config.intf.database.orm;

import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCacheManager;
import haiyan.config.castorgen.Table;
import haiyan.config.intf.session.ITableDBContext;

/**
 * @author ZhouXW
 *
 */
public interface ITableRecordCacheManager extends IDBRecordCacheManager {
	
	IDBRecord getCache(ITableDBContext context, Table table, String id) throws Throwable;
	void updateCache(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	void removeCache(ITableDBContext context, Table table, String[] ids) throws Throwable;

}
