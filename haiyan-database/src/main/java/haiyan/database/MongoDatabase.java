package haiyan.database;

import com.mongodb.Mongo;

import haiyan.common.VarUtil;

public class MongoDatabase extends NoSQLDatabase {

	private String server;
	private int port;
	private String service;
	public MongoDatabase(String url) {
		super(url);
		String[] paras1 = this.getURL().split(":");
		this.server = paras1[0];
		String[] paras2 = paras1[1].split("\\/");
		this.port = VarUtil.toInt(paras2[0]);
		this.service = paras2[1];
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	@SuppressWarnings("deprecation")
	public Mongo getMongoDB() {
		return new Mongo(server, port);
	}

}
