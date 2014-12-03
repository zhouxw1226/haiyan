package test.config;

import haiyan.common.CloseUtil;
import haiyan.common.SysCode;
import haiyan.common.exception.Warning;
import haiyan.config.castorgen.Bill;
import haiyan.config.castorgen.Haiyan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

public class TestBillConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestBillConfig t = new TestBillConfig();
		t.test1();
	}
	@Test
	public void test1() {
		File file = new File(TestBillConfig.class.getResource("SYSBILL.xml").getPath());
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            // 实例转换
            // Unmarshaller unmarshal = new Unmarshaller(fr);
            // unmarshal.(DataConstant.CHARSET);
            // unmarshal.setNoNamespaceSchemaLocation("../../haiyan.xsd");
            // unmarshal.setSuppressXSIType(true);
            // Haiyan genmis = unmarshal.xxx(Haiyan.class);
            // 静态转换
            Haiyan genmis = (Haiyan) org.exolab.castor.xml.Unmarshaller.unmarshal(Haiyan.class, reader);
            // Marshaller.marshal();
            Bill[] bills = genmis.getBill();
            if (bills != null) {
                for (Bill bill:bills) {
                    System.out.println(bill.getName());
                    System.out.println(bill.getDescription());
                    System.out.println(bill.getBillID());
                }
            }
        } catch (Throwable ex) {
            throw new Warning(ex, SysCode.create(null, 100055, "error_read_config", new String[] { file.getName() }));
        } finally {
        	CloseUtil.close(reader);
        }
	}

}
