package haiyan.common.jms;

public class JMSMessage {
	public static final String ACTION = "__action";
	JMSType type = JMSType.STRING;
	Object data;
	String action;
	public JMSMessage(Object data) {
		this.data = data;
	}
	public JMSMessage(Object data, JMSType type, String action) {
		this.data = data;
		this.type = type;
		this.action = action;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Object getData() {
		return this.data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public JMSType getType() {
		return this.type;
	}
	public void setType(JMSType type) {
		this.type = type;
	}
}