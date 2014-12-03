/*
 * Created on 2004-11-30
 *
 */
package haiyan.common;

/**
 * @author zhouxw
 * 
 */
public class DBColumnVO {

	private Object data;

	private DBColumn type;

	/**
	 * @return Returns the cls.
	 */
	public DBColumn getType() {
		return type;
	}

	/**
	 * @param cls
	 *            The cls to set.
	 */
	public void setCls(DBColumn type) {
		this.type = type;
	}

	/**
	 * @return Returns the data.
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            The data to set.
	 */
	public void setData(Object data) {
		this.data = data;
	}
}