package test.orm;

import haiyan.orm.aop.DBRecordProxyFactory;

public class TestOrderBean {

	public static void main(String[] args) throws Throwable {
		TestOrderBean t = new TestOrderBean();
		t.test1();
	}
	private void test1() throws Throwable {
		OrderBean bean = (OrderBean)DBRecordProxyFactory.createDBRecordProxy(OrderBeanImpl.class);
		bean.setCode("112233");
		System.out.println(bean.getCode());
//		MySqlDBManager.updateRecord((ITableDBContext)null, ConfigUtil.getTable("T_ORDER"), bean);
//		dbm.update();
	}
}
