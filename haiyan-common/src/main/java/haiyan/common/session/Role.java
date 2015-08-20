package haiyan.common.session;

import haiyan.common.intf.session.IRole;

public class Role implements IRole {
	private static final long serialVersionUID = 1L;
	public Role() {
		super();
	} 
	public Role(String id, String name, String code, String right) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.right = right;
	}
	private String id;
	private String name;
	private String code;
	private String right;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}

}
