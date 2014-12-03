package haiyan.common.intf.session;

import java.io.Serializable;

public interface IRole extends Serializable {
	String getId();
	void setId(String id);
	String getName();
	void setName(String name);
	String getCode();
	void setCode(String code);
}