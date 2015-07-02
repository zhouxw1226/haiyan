package haiyan.common.intf.config;

public interface IBillConfig {

	String getName();
	String getRealName();
	IBillTable[] getBillTable();
	IBillIDConfig[] getBillID();
	int getBillTableCount();
	IBillField[] getBillField();

}
