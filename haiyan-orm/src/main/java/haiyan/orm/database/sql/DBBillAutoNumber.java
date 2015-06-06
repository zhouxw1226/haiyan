package haiyan.orm.database.sql;

import haiyan.common.CloseUtil;
import haiyan.common.Ref;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.intf.session.ITableDBContext;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.DBContextFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;
 
/** 
 *
 */
class DBBillAutoNumber {

	private final static BigDecimal SEEDCACHE = new BigDecimal(200); // 一次种子的最少数值
	private static final BigDecimal SEEDWARN = new BigDecimal(120); // 种子边界警告
	private static final BigDecimal SEED = new BigDecimal(1); // 自增长种子
	// private final static String strAutoNumberBegin = "AutoNumberBegin";// 共享属性起始值组的名称
	// private final static String strAutoNumberEnd = "AutoNumberEnd";// 共享属性结束值组的名称
	/**
	 * 申请不重复的ID(获取不指定表对象的唯一ID值)
	 * 
	 * @param context
	 * @return Integer
	 * @throws Throwable
	 */
	static BigDecimal requestID(IContext context) throws Throwable {
		return requestID(context, ConfigUtil.getTable("SYS"), 1);
	}
	private static HashMap<String, BigDecimal> cacheID = new HashMap<String, BigDecimal>();
	/**
	 * 申请不重复的ID
	 * 
	 * @param env
	 * @param strObjName
	 * @param lngNumber
	 * @return int
	 * @throws Throwable
	 */
	static synchronized BigDecimal requestID(IContext context, Table table, long lngNumber) throws Throwable {
		// '申请的个数不可以小于等于零
		if (lngNumber <= 0) {
			throw new Warning("申请的ID个数不能小于等于零.");
		}
		String key = context.getDSN()+"_"+ConfigUtil.getRealTableName(table);
		BigDecimal spNumber = BigDecimal.valueOf(lngNumber);
		BigDecimal spBegin = cacheID.get(key + ".start");
		BigDecimal spEnd = cacheID.get(key + ".end");
		if (spBegin == null) {
			spBegin = BigDecimal.valueOf((long) 1);
		}
		if (spEnd == null) {
			spEnd = BigDecimal.valueOf((long) 0);
		}
		BigDecimal lngDBIDRequestNumber;
		Ref<BigDecimal> lngRBeginNumber = new Ref<BigDecimal>();
		Ref<BigDecimal> lngREndNumber = new Ref<BigDecimal>();
		if (spBegin.add(spNumber).compareTo(spEnd.add(SEED)) == 1) {
			// ' 数据库数据请求的数量
			if (spNumber.compareTo(SEEDCACHE) == 1) {
				lngDBIDRequestNumber = spNumber;
			} else {
				lngDBIDRequestNumber = SEEDCACHE;
			}
			dbGetNumber(context, table, lngDBIDRequestNumber, lngRBeginNumber, lngREndNumber);
			spBegin = lngRBeginNumber.getValue();
			spEnd = lngREndNumber.getValue();
			if (spBegin == null) {
				spBegin = BigDecimal.valueOf((long) 1);
			}
			if (spEnd == null) {
				spEnd = BigDecimal.valueOf((long) 0);
			}
		}
		BigDecimal ret = spBegin;
		spBegin = ret.add(spNumber);
		cacheID.put(key + ".start", spBegin);
		cacheID.put(key + ".end", spEnd);
		return ret;
	}
	/**
	 * TODO 可以利用分布式锁框架或NoSQL来实现全局自增长ID
	 * 
	 * @param context
	 * @param strObjName
	 * @param lngNumber
	 * @param lngRBeginNumber
	 * @param lngREndNumber
	 * @throws Throwable
	 */
	private static void dbGetNumber(IContext context, Table table,
			BigDecimal lngDBIDRequestNumber, Ref<BigDecimal> lngRBeginNumber,
			Ref<BigDecimal> lngREndNumber) throws Throwable {
		String strObjName = ConfigUtil.getRealTableName(table);
		strObjName = strObjName.toUpperCase();
		ITableDBContext subContext = DBContextFactory.createDBContext(context); // 开启一个独立事务
		try {
			String sSQL = "SELECT ID, VALUE, PROP1 FROM SYSCACHE WHERE K like ?";
			// for update only for mysql
			Object[][] rstNumber1 = subContext.getDBM().getResultArray(sSQL, 3, new Object[]{strObjName});
			BigDecimal lngGetValue;
			BigDecimal lngEndNumber;
			BigDecimal lngIniStartNumber;
			BigDecimal lngIniEndNumber;
			BigDecimal lngIniWarnNumber;
			if (rstNumber1.length == 0) {
				String ID = UUID.randomUUID().toString();
				lngIniStartNumber = BigDecimal.valueOf((long) 1);
				lngIniEndNumber = BigDecimal.valueOf((long) 100);
				lngIniWarnNumber = BigDecimal.valueOf((long) 90);
				Id idFld = table.getId();
				if (idFld.getJavaType() != AbstractCommonFieldJavaTypeType.STRING) {
					String sql = "select max(" + idFld.getName() + ") from " + strObjName;
					Object[][] rsStr = subContext.getDBM().getResultArray(sql, 1, null);
					if (rsStr != null && !StringUtil.isBlankOrNull(rsStr[0][0])) {
						if (StringUtil.isNumeric(rsStr[0][0])) {
							BigDecimal s = VarUtil.toBigDecimal(rsStr[0][0]);
							lngIniStartNumber = s.add(SEED);
							lngIniEndNumber = s.add(SEEDCACHE);
							lngIniWarnNumber = s.add(SEEDWARN);
						}
					}
				}
				// lngIniStartNumber = BigDecimal.valueOf((long) 1);
				// lngIniEndNumber = BigDecimal.valueOf((long) 100);
				// lngIniWarnNumber = BigDecimal.valueOf((long) 90);
				sSQL = "insert into SYSCACHE(ID, K, VALUE, PROP1, PROP2) values (?,?,?,?,?)";
				subContext.getDBM().executeUpdate(sSQL, new Object[]{ID, strObjName, lngIniStartNumber.add(lngDBIDRequestNumber), lngIniEndNumber, lngIniWarnNumber});
				lngGetValue = lngIniStartNumber;
			} else {
				String ID = VarUtil.toString(rstNumber1[0][0]);
				lngGetValue = VarUtil.toBigDecimal(rstNumber1[0][1]); // VALUE
				lngEndNumber = VarUtil.toBigDecimal(rstNumber1[0][2]); // PROP1
				if (lngGetValue.add(lngDBIDRequestNumber).compareTo(lngEndNumber.add(SEED)) == 1) {
					// // '数据库中AutoNumber中的数量不够。要从AutoNumberPool中重新分配
					// lngIniStartNumber =
					// rstNumber2.bkGetInt("IniStartNumber");
					// lngIniEndNumber = rstNumber2.bkGetInt("IniEndNumber");
					// lngIniWarnNumber = rstNumber2.bkGetInt("IniWarnNumber");
					lngIniStartNumber = lngEndNumber.add(SEEDCACHE).add(SEED);
					lngIniEndNumber = lngEndNumber.add(SEEDCACHE);
					lngIniWarnNumber = lngIniEndNumber.add(SEEDWARN);
					// rstNumber.Close
					sSQL = "UPDATE SYSCACHE set K=?, VALUE=?, PROP1=?, PROP2=? where ID=? ";
					subContext.getDBM().executeUpdate(sSQL, new Object[]{strObjName, lngIniStartNumber, lngIniEndNumber, lngIniWarnNumber, ID});
					lngGetValue = lngIniStartNumber;
				} else {
					sSQL = "UPDATE SYSCACHE SET VALUE=(VALUE*1+" + lngDBIDRequestNumber + ") WHERE K=? ";
					subContext.getDBM().executeUpdate(sSQL,new Object[]{strObjName});
				}
			}
			lngRBeginNumber.setValue(lngGetValue);
			lngREndNumber.setValue(lngRBeginNumber.getValue().add(lngDBIDRequestNumber.subtract(SEED)));
			subContext.commit();
		} catch (Throwable e) {
			if (subContext != null)
				subContext.rollback();
			throw e;
		} finally {
			CloseUtil.close(subContext);
		}
	}
	/**
	 * 根据种子key，和请求数量lngNumber，计算出一个不重复的长度为6的字符串
	 * 
	 * @param context
	 * @param table
	 * @param lngNumber
	 * @return
	 * @throws Throwable
	 */
	static synchronized String requestShortID(IContext context, Table table, long lngNumber) throws Throwable { 
		long id = requestID(context, table, lngNumber).longValue();
		return shortUrl(id);
	}
	/**
	 * 根据id，生成长度为6的字符串
	 * @param id
	 * @return
	 */
	static String shortUrl(long id) {
		return shortUrl(id,6);
	}
	/**
	 * 根据id，生成长度为length的字符串
	 * @param id
	 * @param length
	 * @return
	 */
	static String shortUrl(long id,int length) {
		StringBuffer buf = new StringBuffer();
	    while (id > 0) {  
	        int remainder = (int) (id % 62);
	        buf.insert(0, int2Char(remainder));
	        id = id / 62;  
	    }
	    for(int l=buf.length();l<length;l=buf.length()){
	    	buf.insert(0, int2Char(0));
	    }
	    return buf.toString();
	}
	private static char int2Char(int i){
		switch (i){
		case 0:
			return 'a';
		case 1:
			return 'b';
		case 2:
			return 'c';
		case 3:
			return 'd';
		case 4:
			return 'e';
		case 5:
			return 'f';
		case 6:
			return 'g';
		case 7:
			return 'h';
		case 8:
			return 'i';
		case 9:
			return 'j';
		case 10:
			return 'k';
		case 11:
			return 'l';
		case 12:
			return 'm';
		case 13:
			return 'n';
		case 14:
			return 'o';
		case 15:
			return 'p';
		case 16:
			return 'q';
		case 17:
			return 'r';
		case 18:
			return 's';
		case 19:
			return 't';
		case 20:
			return 'u';
		case 21:
			return 'v';
		case 22:
			return 'w';
		case 23:
			return 'x';
		case 24:
			return 'y';
		case 25:
			return 'z';
		case 26:
			return '0';
		case 27:
			return '1';
		case 28:
			return '2';
		case 29:
			return '3';
		case 30:
			return '4';
		case 31:
			return '5';
		case 32:
			return '6';
		case 33:
			return '7';
		case 34:
			return '8';
		case 35:
			return '9';
		case 36:
			return 'A';
		case 37:
			return 'B';
		case 38:
			return 'C';
		case 39:
			return 'D';
		case 40:
			return 'E';
		case 41:
			return 'F';
		case 42:
			return 'G';
		case 43:
			return 'H';
		case 44:
			return 'I';
		case 45:
			return 'J';
		case 46:
			return 'K';
		case 47:
			return 'L';
		case 48:
			return 'M';
		case 49:
			return 'N';
		case 50:
			return 'O';
		case 51:
			return 'P';
		case 52:
			return 'Q';
		case 53:
			return 'R';
		case 54:
			return 'S';
		case 55:
			return 'T';
		case 56:
			return 'U';
		case 57:
			return 'V';
		case 58:
			return 'W';
		case 59:
			return 'X';
		case 60:
			return 'Y';
		case 61:
			return 'Z';
		}
		return ' ';
	}
}
