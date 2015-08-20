package haiyan.common.session;

import haiyan.common.intf.session.IRole;

/**
 * @author zhouxw
 *
 */
public class AppUser extends User { 
	private static final long serialVersionUID = 1L;
	public AppUser() { 
		super();
	}
	private String[] tables;
	public void setTables(String[] tables) {
		this.tables = tables;
	}
	public String[] getTables() {
		return this.tables;
	}
	private String gwSessionID;
	public String getGwSessionID() {
		return gwSessionID;
	}
	public void setGwSessionID(String gwSessionID) {
		this.gwSessionID = gwSessionID;
	}
	public boolean isMemberOf(IRole role) {
		IRole[] roles = this.getRoles();
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].getCode().equals(role.getCode()))
				return true;
		}
		return false;
	}
	public boolean isMemberOfByRoleCode(String roleCode) {
		IRole[] roles = this.getRoles();
		for (int i = 0; i < roles.length; i++) {
			if (roles[i].getCode().equals(roleCode))
				return true;
		}
		return false;
	}
}
