package test.config;

import haiyan.common.CloseUtil;
import haiyan.common.SysCode;
import haiyan.common.cache.AppDataCache;
import haiyan.common.exception.Warning;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Haiyan;
import haiyan.config.castorgen.Table;
import haiyan.config.util.ConfigUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

public class TestOperator {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestTableConfig t = new TestTableConfig();
		t.test1();
	}
	@Test
	public void test1() {
		ConfigUtil.setDataCache(new AppDataCache()); 
		
		File file = new File(TestTableConfig.class.getResource("SYSOPERATOR.xml").getPath());
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
            Table[] tables = genmis.getTable();
            if (tables != null) {
                for (Table table:tables) {
                    System.out.println(table.getName());
                    System.out.println(table.getDescription());
                    System.out.println(table.getId());
                    Field fld = ConfigUtil.getFieldByName(table, "ROLE");
                    System.out.println(fld.getReferenceTable());
                    //System.out.println(fld.get);
                }
            }
        } catch (Throwable ex) {
            throw new Warning(ex, SysCode.create(null, 100055, "error_read_config", new String[] { file.getName() }));
        } finally {
        	CloseUtil.close(reader);
        }
	}
}
