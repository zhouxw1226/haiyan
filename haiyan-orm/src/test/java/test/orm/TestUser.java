package test.orm;

import haiyan.common.intf.session.IUser;
import haiyan.common.session.User;

public class TestUser {

	private TestUser() {
	}
	public static IUser createUser() {
		IUser user = new User();
		user.setId("10000");
		user.setName("admin");
		user.setCode("admin");
		user.setDSN("local_mysql");
		return user;
	}

}
