/*
 * Created on 2004-10-29
 */
package haiyan.common.session;

import haiyan.common.annotation.GetMethod;
import haiyan.common.annotation.SetMethod;
import haiyan.common.intf.session.IRole;
import haiyan.common.intf.session.IUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouxw
 */
public class User implements IUser, Serializable { // Externalizable
	private static final long serialVersionUID = 1L;
	private Map<String, Object> properties = new HashMap<String, Object>(); // 扩展属性
	private String sessionID;
	private String ID;
	private String name;
	private String code;
	private String password;
	private String deptID;
	private String DSN;
	private String mobile;
	private String languageName;
	private Boolean alive;
	private IRole[] roles;
	//	@Override
//	public void writeExternal(ObjectOutput out) throws IOException {
//		out.writeUTF(ID);
//		out.writeUTF(name);
//		out.writeUTF(code);
//		out.writeUTF(password);
//		out.writeUTF(deptID);
//		out.writeUTF(DSN);
//		out.write(VarUtil.toBytes(roles));
//		out.writeBoolean(alive);
//	}
//	@Override
//	public void readExternal(ObjectInput in) throws IOException,
//			ClassNotFoundException {
//		
//	}
	//@Table("SYSOPERATOR")
	public User() {
	}
	User(String ID, String code, String name) {
		this.ID = ID;
		this.code = code;
		this.name = name;
	}
	@GetMethod("sessionID")
	@Override
	public String getSessionId() {
		return sessionID;
	}
	@SetMethod("sessionID")
	@Override
	public void setSessionId(String sessionId) {
		this.sessionID = sessionId;
	}
	@GetMethod("ID")
	@Override
	public String getId() {
		return this.ID;
	}
	@SetMethod("ID")
	@Override
	public void setId(String id) {
		this.ID = id;
	}
	@GetMethod("DSN")
	@Override
	public String getDSN() {
		return DSN;
	}
	@SetMethod("DSN")
	@Override
	public void setDSN(String dsn) {
		DSN = dsn;
	}
	@GetMethod("deptID")
	public String getDeptID() {
		return this.deptID;
	}
	@SetMethod("deptID")
	public void setDeptID(String deptID) {
		this.deptID = deptID;
	}
	@GetMethod("password")
	@Override
	public String getPassword() {
		return this.password;
	}
	@SetMethod("password")
	@Override
	public void setPassword(String passWord) {
		this.password = passWord;
	}
	@GetMethod("roles")
	@Override
	public IRole[] getRoles() {
		return this.roles;
	}
	@SetMethod("roles")
	@Override
	public void setRoles(IRole[] roles) {
		this.roles = roles;
	}
	@GetMethod("code")
	@Override
	public String getCode() {
		return this.code;
	}
	@SetMethod("code")
	@Override
	public void setCode(String code) {
		this.code = code;
	}
	@GetMethod("name")
	@Override
	public String getName() {
		return this.name;
	}
	@SetMethod("name")
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@GetMethod("mobile")
	@Override
	public String getMobile() {
		return mobile;
	}
	@SetMethod("mobile")
	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}
	@Override
	public Object getProperty(String key) {
		return this.properties.get(key);
	}
	@SetMethod("languageName")
	@Override
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	@GetMethod("languageName")
	@Override
	public String getLanguageName() {
		return this.languageName;
	}
	@GetMethod("alive")
	@Override
	public Boolean isAlive() {
		return alive;
	}
	@SetMethod("alive")
	@Override
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	@SetMethod("properties")
	@Override
	public void setProperties(Map<String, Object> properties) {
		if(properties!=null)
			this.properties = properties;
		else
			this.properties = new HashMap<String, Object>();
	}
	@GetMethod("properties")
	@Override
	public Map<String, Object> getProperties() {
		return this.properties;
	}
	@Override
	public void close() throws IOException {
	}
	@Override
	public String toString() {
		return "["+this.getId()+","+this.getCode()+","+this.getProperties()+"]";
	}
}