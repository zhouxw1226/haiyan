package haiyan.common.intf.config;

public interface IBillConfig {

	String getName();
	String getRealName();
	int getBillTableCount();
	IBillIDConfig[] getBillID();
	IBillTable[] getBillTable();
	IBillField[] getBillField();

}
