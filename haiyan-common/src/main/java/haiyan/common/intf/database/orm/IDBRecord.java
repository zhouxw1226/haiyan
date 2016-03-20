package haiyan.common.intf.database.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

/**
 * 元数据对象
 * 
 * @author ZhouXW
 *
 */
public interface IDBRecord extends Serializable {

	static byte DEFAULT = (byte) 0;
	static byte INSERT = (byte) 1;
	static byte UPDATE = (byte) 2;
	static byte DELETE = (byte) 3;
	static byte INSERT_DELETE = (byte) 4;
	static byte UPDATE_FAIL = (byte) 5;
	static byte INSERT_FAIL = (byte) 6;
	/**
	 * 把DB读出的数据刷新到record中的一级缓存中
	 * @throws Throwable
	 */
	void flushOreign() throws Throwable;
	/**
	 * 将一级缓存中的数据刷新到DB或二级缓存中（看具体IDBManager策略）
	 * @throws Throwable
	 */
	void flush() throws Throwable;
	/**
	 * 当前元数据回滚到oreign状态
	 * @return
	 * @throws Throwable
	 */
	boolean rollback() throws Throwable;
	/**
	 * 修改数据提交到DB或二级缓存
	 * @throws Throwable
	 */
	void commit() throws Throwable;
	/**
     * 设置元数据持久化状态
	 * @param status
	 */
	void setStatus(byte status);
    /**
     * 清空元数据持久化状态
     */
    public void clearStatus();
    /**
     * 获取元数据持久化状态
     * @return
     */
    public byte getStatus();
	/**
	 * 设置脏标记
	 * @throws Throwable
	 */
	void setDirty() throws Throwable;
	/**
	 * 清楚脏标记
	 * @throws Throwable
	 */
	void clearDirty() throws Throwable;
	/**
	 * 是否脏数据
	 * @return
	 */
	boolean isDirty();
	/**
	 * 更新元数据版本
	 * @throws Throwable
	 */
	void updateVersion() throws Throwable;
	/**
	 * 回滚元数据版本
	 * @throws Throwable
	 */
	void rollbackVersion() throws Throwable;
	/**
	 * 获取元数据版本
	 * @return
	 */
	int getVersion();
	/**
	 * @return JSONObject
	 */
	JSONObject toJSon();
	IDBRecord fromJSon(JSONObject json);
	IDBRecord fromJSon(JSONObject json, boolean ignoreJSON);
	// ----------------------------------------------------------------------- //
	public String getTableName();
    public void setTableName(String tableName);
	// ----------------------------------------------------------------------- //
	Set<String> oreignKeySet();
	Set<String> insertedKeySet();
	Set<String> updatedKeySet();
	Set<String> deletedKeySet();
	Set<String> dataKeySet();
	Map<String, Object> getDataMap();
//	Iterator<String> keyIterator();
	boolean contains(String key);
	Object remove(String key);
	Object delete(String key);
	void set(String key, Object val);
	Object get(String key);
    void setValues(String name, Object[] values);
    Object[] getValues(String name);
	String getString(String name);
	Date getDate(String key, String dateStyle);
	int getInteger(String key);
	long getLong(String key);
    double getDouble(String key);
    BigDecimal getBigDecimal(String key);
    String getValuesString(String name, String split);
	// ----------------------------------------------------------------------- //
	void putAll(Map<?, ?> map);
	void applyIf(IDBRecord etcRecord);
	void apply(IDBRecord etcRecord);

}
