package haiyan.exp.function;

import haiyan.common.StringUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.intf.exp.IFunction;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.util.ConfigUtil;
  
/**
 * @author ZhouXW
 *
 */
public class DefaultFunction {

	@IFunction(name="LowerCase")
	public static Object lowerCase(IContext context, Table table, Object bean, Object[] paras) {
		String s = (String)paras[0];
		return s.toLowerCase();
	}
	@IFunction(name="UpperCase")
	public static Object upperCase(IContext context, Table table, Object bean, Object[] paras) {
		String s = (String)paras[0];
		return s.toUpperCase();
	}
	@IFunction(name="SQLSafe")
	public static Object sqlSafe(IContext context, Table table, Object bean, Object[] paras) {
		String s = (String)paras[0];
		return StringUtil.unSqlInjection(s);
	}
	@IFunction(name="GetPara")
	public static Object getParameter(IContext context, Table table, Object bean, Object[] paras) {
		Object o = context.getAttribute((String)paras[0]);
		return o;
	}
	@IFunction(name="InitTable")
	public static Object initTable(IContext context, Table table, Object bean, Object[] paras) throws Throwable {
		String tableName = (String)paras[0];
		Table table2 = ConfigUtil.getTable(tableName);
		initTable(table2);
		return true;
	}
    /**
     * 追加系统字段
     * 
     * @param table
     * @throws Throwable
     */
    public static void initTable(Table table) throws Throwable {
    	// 动态追加的系统字段，使用状态：USEDSTATUS，数据加密Key：HYFORMKEY，版本号：HYVERSION
        Field f;
        if (table.getName().startsWith("T_DIC_")) { // 字典类配置
            String[] a = new String[]{DataConstant.USEDSTATUS, DataConstant.HYFORMKEY, DataConstant.HYVERSION};
            for (String k:a){
                f = ConfigUtil.getFieldByName(table, k, true);
                if (f == null) {
                    synchronized (table) {
                        if (f == null) {
                            f = new Field();
//                          Component c = new Component();
//                          c.setType(ComponentTypeType.HIDDEN);
//                          f.setComponent(c);
                            f.setName(k);
                            f.setJavaType(AbstractCommonFieldJavaTypeType.STRING);
                            f.setLength(100);
                            if (k.equals(DataConstant.HYFORMKEY))
                                f.setVisual(true);
                            table.addField(f);
                        }
                    }
                }
            }
        } else if (!table.getName().startsWith("V_") && !table.getName().startsWith("SYS")) { // 非视图和系统配置
        	String[] a = new String[]{DataConstant.WFBILLID, DataConstant.HYFORMKEY, DataConstant.HYVERSION};
        	for (String k:a){
	            f = ConfigUtil.getFieldByName(table, k, true);
	            if (f == null) {
	                synchronized (table) {
	                    if (f == null) {
	                        f = new Field();
//	                        Component c = new Component();
//	                        c.setType(ComponentTypeType.HIDDEN);
//	                        f.setComponent(c);
	                        f.setName(k);
	                        f.setJavaType(AbstractCommonFieldJavaTypeType.STRING);
	                        f.setLength(100);
	                        if (k.equals(DataConstant.HYFORMKEY))
	                        	f.setVisual(true);
	                        table.addField(f);
	                    }
	                }
	            }
        	}
        }
    }

}
