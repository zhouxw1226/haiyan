package haiyan.orm.database.sql.query;

import haiyan.common.StringUtil;

/**
 * @author liw
 */
public class OrderByItem extends Item {

	/**
	 * @param colName
	 * @param order
	 */
	public OrderByItem(String colName, String order) {
		this.colName = StringUtil.unSqlInjection(colName);
		this.order = StringUtil.unSqlInjection(order);
	}

	//
	private String colName;

	//
	private String order;

	/**
	 * Returns the colName.
	 * 
	 * @return String
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * Returns the order.
	 * 
	 * @return String
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Sets the colName.
	 * 
	 * @param colName
	 *            The colName to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}

	/**
	 * Sets the order.
	 * 
	 * @param order
	 *            The order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return String
	 */
	public String getSqlStr() {
		String result = "";
		if (!isIgnored()) {
			result = this.colName + " "
					+ (isValueValid(this.order) ? this.order : "");
		}
		return result;
	}

	/**
	 * @return boolean
	 */
	public boolean isIgnored() {
		return !this.isValueValid(this.colName);
	}

}