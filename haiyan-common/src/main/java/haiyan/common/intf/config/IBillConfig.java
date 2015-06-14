package haiyan.common.intf.config;

import haiyan.config.castorgen.BillField;

public interface IBillConfig {

	String getName();

	String getRealName();

	IBillTable[] getBillTable();

	IBillIDConfig[] getBillID();

	int getBillTableCount();

	BillField[] getBillField();

}
