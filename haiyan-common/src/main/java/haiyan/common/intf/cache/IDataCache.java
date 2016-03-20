package haiyan.common.intf.cache;

import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.File;

public interface IDataCache {
	void setServers(String[] servers); // 集群服务器
	void initialize(); // 初始化
	// ------------------------------------ //
	Object removeLocalData(String schema, Object key); // 删除本地缓存
	Object setLocalData(String schema, Object key, Object ele); // 设置本地缓存
	Object getLocalData(String schema, Object key); // 获取本地缓存
	// ------------------------------------ //
	Object setData(String schema, Object key, Object ele); // 设置缓存（loadfromdb|syncData->如果有集群同时更新集群对象,不更新状态）
	Object setData(String schema, Object key, Object ele, int seconds); 
	Object getData(String schema, Object key); // 获取缓存（如果本地缓存不存在获取集群状态）
	Object updateData(String schema, Object key, Object ele); // 更新缓存（commit->同时更新集群状态）
	Object updateData(String schema, Object key, Object ele, int seconds); 
	Object deleteData(String schema, Object key); // 删除记录（commit->同时更新集群状态）
//	boolean addListData(String schema, Object ele); // 增加临时列表记录 session beans
//	boolean removeListData(String schema, Object key); // 删除临时列表记录 session beans
//	int getDataSize(String schema); // 获取临时列表对象 session beans‘s size
//	int getIndexOf(String key, Object o); // 获取临时列表对象索引
//	int getLastIndexOf(String key, Object o); // 获取临时列表对象最后索引
	void clearData(String schema); // 清空本地缓存
	// ------------------------------------ //
	ITableConfig setTable(String sKey, ITableConfig table);
	ITableConfig getTable(String sKey);
	File setTableFile(String sKey, File f);
	File getTableFile(String sKey);
	String[] getAllTableKeys();
	boolean removeTable(String sKey);
	boolean removeTableFile(String sKey);
	// ------------------------------------ //
	IBillConfig setBill(String sKey, IBillConfig bill);
	IBillConfig getBill(String sKey);
	File setBillFile(String sKey, File f);
	File getBillFile(String sKey);
	String[] getAllBillKeys();
	boolean removeBill(String sKey);
	boolean removeBillFile(String sKey);
	// ------------------------------------ //
	IUser[] getAllUsers();
	IUser setUser(String sessionId, IUser user); // SessionID索引
	IUser getUser(String sessionId); // SessionID索引
	boolean removeUser(String sessionId); // SessionID索引
	boolean containsUser(String sessionId); // SessionID索引
	IUser getUserByCode(String userCode); // Code索引
	IUser getUserByID(String userID); // UserID索引
}