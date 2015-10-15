package haiyan.orm.intf.database.sql;

import haiyan.common.intf.database.IDBFilter;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSet;
import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.common.intf.database.sql.ISQLRender;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.orm.intf.session.ITableDBContext;

import java.sql.PreparedStatement;

/**
 * @author ZhouXW
 *
 */
public interface ITableSQLRender extends ISQLRender {

	IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBRecord record, ISQLRecordFactory factory, long startRow, int count) throws Throwable;
	IDBResultSet selectByLimit(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, long startRow, int count) throws Throwable;
	IDBResultSet selectBy(ITableDBContext context, Table table, IDBRecord record, ISQLRecordFactory factory, int maxPageRecordCount, int currPageNO) throws Throwable;
	IDBResultSet selectBy(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, int maxPageRecordCount, int currPageNO) throws Throwable;
	void loopBy( ITableDBContext context, Table table, IDBRecord record, ISQLRecordFactory factory, IDBRecordCallBack callback) throws Throwable;
	void loopBy(ITableDBContext context, Table table, IDBFilter filter, ISQLRecordFactory factory, IDBRecordCallBack callback) throws Throwable;
	long countBy(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	long countBy(ITableDBContext context, Table table, IDBFilter filter) throws Throwable;
	void insertPreparedStatement(ITableDBContext context, Table table, IDBRecord record, PreparedStatement ps, Field[] fields, String newID) throws Throwable;
	void updatePreparedStatementValue(ITableDBContext context, Table table, IDBRecord record, PreparedStatement ps, Field[] fields,IDBFilter filter) throws Throwable;
	PreparedStatement getDeletePreparedStatement(ITableDBContext context, Table table, String[] ids) throws Throwable;
	PreparedStatement getSelectPreparedStatement(ITableDBContext context, Table table, String id) throws Throwable;
	PreparedStatement getInsertPreparedStatement(ITableDBContext context, Table table, IDBRecord record, String newID)  throws Throwable;
	PreparedStatement getUpdatePreparedStatement(ITableDBContext context, Table table, IDBRecord record, IDBFilter filter) throws Throwable;
	PreparedStatement getInsertPreparedStatement(ITableDBContext context, Table table) throws Throwable; // batch
	PreparedStatement getUpdatePreparedStatement(ITableDBContext context, Table table) throws Throwable; // batch
	Field[] getInsertValidField(ITableDBContext context, Table table) throws Throwable; // batch
	Field[] getUpdateValidField(ITableDBContext context, Table table) throws Throwable; // batch
	Field[] getInsertValidField(ITableDBContext context, Table table, IDBRecord record) throws Throwable;
	Field[] getUpdateValidField(ITableDBContext context, Table table, IDBRecord record) throws Throwable;

}
