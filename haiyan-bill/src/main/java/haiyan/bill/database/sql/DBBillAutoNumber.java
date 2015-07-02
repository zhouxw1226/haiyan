package haiyan.bill.database.sql;

import haiyan.common.CloseUtil;
import haiyan.common.Ref;
import haiyan.common.StringUtil;
import haiyan.common.VarUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IContext;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.util.ConfigUtil;
import haiyan.orm.database.TableDBContextFactory;
import haiyan.orm.intf.session.ITableDBContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;
 
/** 
 *
 */
class DBBillAutoNumber {

	private final static BigDecimal DESCCACHE = new BigDecimal(200); // 一次申请的最少数值
	private static final BigDecimal DESCWARN = new BigDecimal(120); // 申请边界警告
	private static final BigDecimal DESC = new BigDecimal(1);
	// private final static String strAutoNumberBegin = "AutoNumberBegin";//
	// 共享属性起始值组的名称
	// private final static String strAutoNumberEnd = "AutoNumberEnd";//
	// 共享属性结束值组的名称
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
	static synchronized BigDecimal requestID(IContext context, Table table,
			long lngNumber) throws Throwable {
		// '申请的个数不可以小于等于零
		if (lngNumber <= 0) {
			throw new Warning("申请的ID个数不能小于等于零.");
		}
		String key = ConfigUtil.getRealTableName(table);
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
		if (spBegin.add(spNumber).compareTo(spEnd.add(DESC)) == 1) {
			// ' 数据库数据请求的数量
			if (spNumber.compareTo(DESCCACHE) == 1) {
				lngDBIDRequestNumber = spNumber;
			} else {
				lngDBIDRequestNumber = DESCCACHE;
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
		ITableDBContext subContext = TableDBContextFactory.createDBContext(context); // 开启一个独立事务
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
							lngIniStartNumber = s.add(DESC);
							lngIniEndNumber = s.add(DESCCACHE);
							lngIniWarnNumber = s.add(DESCWARN);
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
				if (lngGetValue.add(lngDBIDRequestNumber).compareTo(lngEndNumber.add(DESC)) == 1) {
					// // '数据库中AutoNumber中的数量不够。要从AutoNumberPool中重新分配
					// lngIniStartNumber =
					// rstNumber2.bkGetInt("IniStartNumber");
					// lngIniEndNumber = rstNumber2.bkGetInt("IniEndNumber");
					// lngIniWarnNumber = rstNumber2.bkGetInt("IniWarnNumber");
					lngIniStartNumber = lngEndNumber.add(DESCCACHE).add(DESC);
					lngIniEndNumber = lngEndNumber.add(DESCCACHE);
					lngIniWarnNumber = lngIniEndNumber.add(DESCWARN);
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
			lngREndNumber.setValue(lngRBeginNumber.getValue().add(lngDBIDRequestNumber.subtract(DESC)));
			subContext.commit();
		} catch (Throwable e) {
			if (subContext != null)
				subContext.rollback();
			throw e;
		} finally {
			CloseUtil.close(subContext);
		}
	}
	
}
