package haiyan.orm.intf.database;

import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.session.IDBSession;
import haiyan.config.castorgen.Table;
import haiyan.orm.intf.session.ITableDBContext;

public interface ICacheDBManager extends IDBSession {
	IDBRecord getCache(ITableDBContext context, Table table, String id, short type) throws Throwable;
	void removeCache(ITableDBContext context, Table table, String[] ids, short type) throws Throwable;
	void updateCache(ITableDBContext context, Table table, IDBRecord record, short type) throws Throwable;
}
