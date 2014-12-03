package test.common.config;

import haiyan.common.config.PathUtil;

import java.io.IOException;

import org.junit.Test;

public class TestPathUtil {

	public static void main(String[] args) throws IOException {
		TestPathUtil t = new TestPathUtil();
		t.test1();
	}
	@Test
	public void test1() throws IOException {
        System.out.println(PathUtil.getPathFromClass(PathUtil.class));
        System.out.println(PathUtil.getFullPathRelateClass("../../../../..", PathUtil.class));
        // System.out.println(getClassPath());
	} 

}
