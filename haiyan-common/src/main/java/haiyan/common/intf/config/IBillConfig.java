package haiyan.common.intf.config;

public interface IBillConfig {

	String getName();

	String getRealName();

	IBillTable[] getBillTable();

	IBillID getBillID(int tableIndex);

}
