package test.orm;

import haiyan.common.intf.database.orm.IDBRecordBean;

public interface OrderBean extends IDBRecordBean {
	void setCode(String v);
	String getCode();
}
