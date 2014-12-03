package test.common.config;

import haiyan.common.config.Language;
import haiyan.common.intf.session.IUser;
import haiyan.common.session.AppUser;

import org.junit.Test;

public class TestLanguage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestLanguage t = new TestLanguage();
		t.test1();
	}
	@Test
	public void test1() {
		IUser user = new AppUser();
		Language lang = Language.getLanguage(user);
		System.out.println(lang.getName());
	}

}
