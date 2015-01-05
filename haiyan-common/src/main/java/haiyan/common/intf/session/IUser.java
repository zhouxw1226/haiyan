package haiyan.common.intf.session;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户对象接口
 * 
 * @author ZhouXW
 *
 */
public interface IUser extends IUserSession, Serializable { // Externalizable
	String getId();
	void setId(String id);
	String getName();
	void setName(String name);
	String getCode();
	void setCode(String code);
	String getPassword();
	void setPassword(String pass);
	String getDeptID();
	void setDeptID(String deptID);
	IRole[] getRoles();
	void setRoles(IRole[] roles);
	String getDSN();
	void setDSN(String DSN);
	String getLanguageName();
	void setLanguageName(String languageName);
	Object getProperty(String key);
	void setProperty(String key, Object value);
	void setProperties(Map<String, Object> properties);
	Map<?, ?> getProperties();
	void setSex(String sex);
	String getSex();
	void setEmail(String email);
	String getEmail();
	void setMobile(String mobile);
	String getMobile();
	String getRights();
	void setRights(String rights);
	String getPortrait();
	void setPortrait(String portrait);
}
 