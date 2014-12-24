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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouxw
 */
public class User implements IUser, Serializable { // Externalizable
	private static final long serialVersionUID = 1L;
	private Map<String, Object> properties = new HashMap<String, Object>();
	private String ID;
	private String name;
	private String code;
	private String password;
	private String deptID;
	private String DSN;
	private IRole[] roles;
	private Boolean alive;
	private String languageName;
	private String sex;
	private String email;
	private String mobile;
	private String portrait;
	private String[] rights;
	//@Table("SYSOPERATOR")
	public User() {
	}
	User(String ID, String code, String name) {
		this.ID = ID;
		this.code = code;
		this.name = name;
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
	@Override
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}
	@Override
	public Object getProperty(String key) {
		return this.properties.get(key);
	}
	@SetMethod("properties")
	@Override
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	@GetMethod("properties")
	@Override
	public Map<?, ?> getProperties() {
		return this.properties;
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
	@Override
	public void close() throws IOException {
	}
	@SetMethod("sex")
	@Override
	public void setSex(String sex) {
		this.sex = sex;
	}
	@GetMethod("sex")
	@Override
	public String getSex() {
		return this.sex;
	}
	@SetMethod("email")
	@Override
	public void setEmail(String email) {
		this.email = email;
	}
	@GetMethod("email")
	@Override
	public String getEmail() {
		return this.email;
	}
	@SetMethod("mobile")
	@Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@GetMethod("mobile")
	@Override
	public String getMobile() {
		return this.mobile;
	}
	@GetMethod("portrait")
	@Override
	public String getPortrait() {
		return portrait;
	}
	@SetMethod("portrait")
	@Override
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	@GetMethod("rights")
	@Override
	public String getRights() {
		return Arrays.toString(rights);
	}
	@SetMethod("rights")
	@Override
	public void setRights(String rights) {
		if(rights == null)
			this.rights = new String[]{"LOGIN"};
		else
			this.rights = rights.split(";");
	}

}