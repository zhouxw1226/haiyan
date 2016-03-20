package haiyan.common.session;

import haiyan.common.annotation.GetMethod;
import haiyan.common.annotation.SetMethod;
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
	// SSO多品牌用户联合ID
	private String unionID;
	@SetMethod("unionID")
	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}
	@GetMethod("unionID")
	public String getUnionID() {
		return unionID;
	}
	// SSO多品牌登录用户SessionID
	private String ssoSessionID;
	@SetMethod("ssoSessionID")
	public void setSSOSessionID(String gwSessionID) {
		this.ssoSessionID = gwSessionID;
	}
	@GetMethod("ssoSessionID")
	public String getSSOSessionID() {
		return ssoSessionID;
	}
	// 表权限
	private String[] tables;
	@SetMethod("tables")
	@Deprecated
	public void setTables(String[] tables) {
		this.tables = tables;
	}
	@GetMethod("tables")
	@Deprecated
	public String[] getTables() {
		return this.tables;
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
