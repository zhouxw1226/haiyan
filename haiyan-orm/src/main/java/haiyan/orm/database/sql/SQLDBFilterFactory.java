/*
 * Created on 2006-7-20
 *
 */
package haiyan.orm.database.sql;

import haiyan.common.StringUtil;
import haiyan.common.intf.factory.IFactory;
import haiyan.config.castorgen.PluggedFilter;
import haiyan.config.castorgen.QueryFilter;
import haiyan.config.castorgen.Table;
import haiyan.orm.intf.session.ITableDBContext;

import java.util.ArrayList;

/**
 * @author zhouxw
 * 
 */
class SQLDBFilterFactory implements IFactory {
	/**
	 * @param table
	 * @param tableAlias
	 * @param context
	 * @return PluggedFilter[]
	 */
	static PluggedFilter[] getQueryFilter(ITableDBContext context, Table table, String tableAlias) {
		// Security sec = ConfigUtil.getConfig().getSecurity();
		// if (sec != null && sec.getEnabled() == true) {
		// if (table.getOperation() != null
		// && table.getOperation().getUseFlowEngine()) { //
		// List temp = PDMEngine.getFilters(tx, table, tableAlias, context);
		// if (temp != null)
		// return temp.toArray();
		// } else {
		ArrayList<PluggedFilter> pluggedFilterList = new ArrayList<PluggedFilter>();
		QueryFilter[] queryFilters = table.getQueryFilter();
		// DebugUtil.debug(table.getName() + "..." + qfs.length);
		for (int i = 0; i < queryFilters.length; i++) {
			boolean addQueryFilter = false;
			if (context == null) {
				addQueryFilter = true;
			} else if (StringUtil.isBlankOrNull(queryFilters[i].getRole())) {
				addQueryFilter = true;
			} else {
//				String[] roleCodes = StringUtil.split(queryFilters[i].getRole(), " ");
//				for (int k = 0; k < roleCodes.length; k++) {
////					if (RightUtil.isUserInRole(context.getUser(), roleCodes[k])) {
//						addQueryFilter = true;
//						break;
////					}
//				}
			}
			if (addQueryFilter) {
				PluggedFilter[] plFilters = queryFilters[i].getPluggedFilter();
				for (int j = 0; j < plFilters.length; j++)
					pluggedFilterList.add(plFilters[j]);
			}
		}
		return pluggedFilterList.toArray(new PluggedFilter[0]);
	}
}
// /**
// * @param args
// */
// public static void main(String[] args) {
// Table table = ConfigUtil.getTable("T_BUGTRACEVIEWTTTT");
// User user = new User();
// user.setRoles(RightUtil.getUserRoles("1"));
// // DBTransaction tx = null;
// // try {
//
// List pluggedFilterList = new ArrayList();
// QueryFilter[] qfs = table.getQueryFilter();
// for (int i = 0; i < qfs.length; i++) {
// //
// boolean addQueryFilter = false;
// if (StringUtil.isBlankOrNull(qfs[i].getRole())) {
// addQueryFilter = true;
// } else {
// String[] roleCodes = StringUtil.getDivBySTK(qfs[i].getRole(),
// ",");
// for (int k = 0; k < roleCodes.length; k++) {
// if (RightUtil.isUserInRole(user, roleCodes[k])) {
// addQueryFilter = true;
// break;
// }
// }
// }
// if (addQueryFilter) {
// Object[] qfv = qfs[i].getPluggedFilterList().toArray();
// for (int j = 0; j < qfv.length; j++)
// pluggedFilterList.add(qfv[j]);
// }
// }
// //return pluggedFilterList.toArray();
// // } finally {
// // if (tx != null)
// // tx.close();
// // }
// }
