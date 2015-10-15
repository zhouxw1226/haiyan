/*
 * Created on 2007-4-6
 *
 */
package haiyan.orm.database.sql;

import haiyan.common.DebugUtil;
import haiyan.common.intf.database.sql.ISQLDBClear;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.sql.BLOB;

/**
 * 数据库对象自动清理器
 * 
 * @author zhouxw
 * 
 */
class SQLDBClear implements ISQLDBClear {

	/**
	 * Comment for <code>blobList</code>
	 */
	ArrayList<Blob> blobList = new ArrayList<Blob>();
	/**
	 * Comment for <code>readerList</code>
	 */
	ArrayList<Reader> readerList = new ArrayList<Reader>();
	/**
	 * Comment for <code>insList</code>
	 */
	ArrayList<InputStream> insList = new ArrayList<InputStream>();
	/**
	 * Comment for <code>connList</code>
	 */
	ArrayList<Connection> connList = new ArrayList<Connection>();
	/**
	 * Comment for <code>statList</code>
	 */
	ArrayList<Statement> statList = new ArrayList<Statement>();
	/**
	 * Comment for <code>restList</code>
	 */
	ArrayList<ResultSet> restList = new ArrayList<ResultSet>();
	// /**
	// * Comment for <code>restList</code>
	// */
	// ArrayList<String> cacheList = new ArrayList<String>();
	@Override
	public void addReader(Reader reader) {
		readerList.add(reader);
	}
	@Override
	public void addInputStream(InputStream ins) {
		insList.add(ins);
	}
	@Override
	public void addBlob(Blob blob) {
		blobList.add(blob);
	}
	@Override
	public void addConn(Connection conn) {
		connList.add(conn);
	}
	@Override
	public void addStat(Statement stat) {
		statList.add(stat);
	}
	@Override
	public void addRest(ResultSet rest) {
		restList.add(rest);
	}
	@Override
	public void clean() throws Throwable {
		// // NOTICE MyCache有自动销毁机制
		// for (String cacheID : cacheList) {
		// try {
		// // NOTICE 有些dbm事先关闭再取数不能删除缓存 想一个更好的方案
		// // 不过MidSrv是长事务可以这样处理...
		// MyCache.clearData(cacheID);
		// MyCache.removeCache(cacheID);
		// } catch (Throwable ex) {
		// DebugUtil.error(ex);
		// }
		// }
		// if (blob != null)
		// blob.close();
		for (int i = 0; i < blobList.size(); i++) {
			try {
				Blob blob = blobList.get(i);
				if (blob != null) {
					DebugUtil.debug(">clear blob:" + blob);
					if (blob instanceof BLOB)
						((BLOB)blob).freeTemporary();
				}
				blob = null;
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			} 
		}
		blobList.clear();
		for (int i = 0; i < insList.size(); i++) {
			try {
				InputStream ins = insList.get(i);
				if (ins != null) {
					DebugUtil.debug(">clear inputstream:" + ins);
					ins.close();
				}
				ins = null;
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			} 
		}
		insList.clear();
		for (int i = 0; i < readerList.size(); i++) {
			try {
				Reader reader = readerList.get(i);
				if (reader != null) {
					DebugUtil.debug(">clear reader:" + reader);
					reader.close();
				}
				reader = null;
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			} 
		}
		readerList.clear();
		// =============================================================================================================== //
		Connection c = null;
		for (int i = 0; i < restList.size(); i++) {
			try {
				ResultSet rest = restList.get(i);
				if (rest != null)
					if (rest.getStatement() != null && (c=rest.getStatement().getConnection())!=null && !c.isClosed()) {
						if (!c.getAutoCommit()) {
							c.rollback();
							c.setAutoCommit(true);
						}
						rest.close();
					}
				rest = null;
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			} 
		}
		restList.clear();
		for (int i = 0; i < statList.size(); i++) {
			try {
				Statement stat = statList.get(i);
				if (stat != null)
					if ((c=stat.getConnection())!=null && !c.isClosed()) {
						if (!c.getAutoCommit()) {
							c.rollback();
							c.setAutoCommit(true);
						}
						stat.close();
					}
				stat = null;
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			} 
		}
		statList.clear();
		for (int i = 0; i < connList.size(); i++) {
			try {
				Connection conn = connList.get(i);
				if (conn != null)
					if (!conn.isClosed()) {
						if (!conn.getAutoCommit()) {
							conn.rollback();
							conn.setAutoCommit(true);
						}
						conn.close();
					}
				conn = null;
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			} 
		}
		connList.clear();
		// =============================================================================================================== //
		// System.gc();
	}

}