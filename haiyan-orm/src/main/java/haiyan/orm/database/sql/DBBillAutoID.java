package haiyan.orm.database.sql;

import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Table;

import java.math.BigDecimal;

public class DBBillAutoID {

//	private static Map<String, List<BigDecimal>> CACHE = new HashMap<String, List<BigDecimal>>();
	public static BigDecimal genNewID(IContext context, Table table, int range)
			throws Throwable {
//		String edge = context.getDSN()+"_"+table.getRealName();
//		List<BigDecimal> list = null;
//		if (!CACHE.containsKey(edge)) {
//			synchronized (CACHE) {
//				if (!CACHE.containsKey(edge)) {
//					CACHE.put(edge, new ArrayList<BigDecimal>());
//				}
//			}
//		}
//		list = CACHE.get(edge);
//		synchronized (list) {
//			if (list.size() == 0) {
//				BigDecimal ID1 = DBBillAutoNumber.requestID(context, table, 1);
//				BigDecimal ID2 = DBBillAutoNumber.requestID(context, table, range <= 1 ? 100 : range);
//				//long id1 = ID1.longValue(), id2 = ID2.longValue();
//				BigDecimal id = ID1;
//				int com = id.compareTo(ID2);
//				while(com<0) {
//				//for (BigDecimal id = ID1; id.compareTo(ID2)<0; ID1.add(BigDecimal.ONE)) {
//					list.add(id);
//					id = id.add(BigDecimal.ONE);
//					com = id.compareTo(ID2);
//				}
//			}
//			return (BigDecimal) list.remove(0);
//		}
		return DBBillAutoNumber.requestID(context, table, 1);
	}
	/**
	 * 根据种子key，和请求数量lngNumber，计算出一个不重复的长度为6的字符串
	 * 
	 * @param context
	 * @param table
	 * @param range
	 * @return
	 * @throws Throwable
	 */
	public static String genShortID(IContext context, ITableConfig table, long range) throws Throwable { 
		return DBBillAutoNumber.requestShortID(context,table,range);
	}
	/**
	 * 根据id，生成长度为6的字符串
	 * 
	 * @param id
	 * @return
	 */
	public static String shortUrl(long id) {
		return DBBillAutoNumber.shortUrl(id);
	}
	/**
	 * 根据id，生成长度为length的字符串
	 * 
	 * @param id
	 * @param length
	 * @return
	 */
	public static String shortUrl(long id,int length) {
		return DBBillAutoNumber.shortUrl(id,length);
	}

}
