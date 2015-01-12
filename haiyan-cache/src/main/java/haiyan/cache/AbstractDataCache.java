package haiyan.cache;

import haiyan.common.StringUtil;
import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractDataCache implements IDataCache {

    protected final Map<String, File> tableFiles = new HashMap<String, File>();
    @Override
    public File setTableFile(String sKey, File file) {
        tableFiles.put(sKey, file);
        return file;
    }
    @Override
    public File getTableFile(String sKey) {
        return tableFiles.get(sKey);
    }
    @Override
    public boolean removeTableFile(String sKey) {
    	tableFiles.remove(sKey);
        return true;
    }
    // ---------------------------------------------------------------------- //
    protected final Map<String, ITableConfig> tableConfigs = new HashMap<String, ITableConfig>();
	@Override
	public ITableConfig setTable(String sKey, ITableConfig table) {
		tableConfigs.put(sKey, table);
		return table;
	}
	@Override
	public ITableConfig getTable(String sKey) {
		return tableConfigs.get(sKey);
	}
	@Override
	public boolean removeTable(String sKey) {
		tableConfigs.remove(sKey);
		return true;
	}
	@Override
	@Deprecated
	public String[] getAllTableKeys() {
		Iterator<String> iter = tableConfigs.keySet().iterator();
		String[] arr = new String[tableConfigs.size()]; 
		int i=0;
		while(iter.hasNext()) {
			arr[i++]=iter.next();
		}
		return arr;
	}
    // ---------------------------------------------------------------------- //
    protected final Map<String, File> billFiles = new HashMap<String, File>();
    @Override
    public File setBillFile(String sKey, File file) {
    	billFiles.put(sKey, file);
        return file;
    }
    @Override
    public File getBillFile(String sKey) {
        return billFiles.get(sKey);
    }
    @Override
    public boolean removeBillFile(String sKey) {
        billFiles.remove(sKey);
        return true;
    }
    // ---------------------------------------------------------------------- //
    protected final Map<String, IBillConfig> billConfigs = new HashMap<String, IBillConfig>();
	@Override
	public IBillConfig setBill(String sKey, IBillConfig bill) {
		billConfigs.put(sKey, bill);
		return null;
	}
	@Override
	public IBillConfig getBill(String sKey) {
		return billConfigs.get(sKey);
	}
	@Override
	public boolean removeBill(String sKey) {
		billConfigs.remove(sKey);
		return true;
	}
	@Override
	@Deprecated
	public String[] getAllBillKeys() {
		Iterator<String> iter = billConfigs.keySet().iterator();
		String[] arr = new String[billConfigs.size()]; 
		int i=0;
		while(iter.hasNext()) {
			arr[i++]=iter.next();
		}
		return arr;
	}
    // ---------------------------------------------------------------------- //
    protected final Map<String, IUser> sessionUsers = new HashMap<String, IUser>();
    @Override
    public IUser setUser(String sessionId, IUser user) {
    	sessionUsers.put(sessionId, user);
    	return user;
    } 
    @Override
    public IUser getUser(String sessionId) {
    	return sessionUsers.get(sessionId);
    }
    @Override
    public boolean removeUser(String sessionId) {
    	sessionUsers.remove(sessionId);
    	return true;
    }
	@Override
	public boolean containsUser(String sessionId) {
		return sessionUsers.containsKey(sessionId);
	}
	@Override
	@Deprecated
	public IUser getUserByID(String userID) {
		if (StringUtil.isEmpty(userID))
			return null;
		Iterator<String> iter = sessionUsers.keySet().iterator();
		while(iter.hasNext()) {
			IUser user = sessionUsers.get(iter.next());
			if (userID.equalsIgnoreCase(user.getId()))
				return user;
		}
		return null;
	}
	@Override
	@Deprecated
	public IUser getUserByCode(String userCode) {
		if (StringUtil.isEmpty(userCode))
			return null;
		Iterator<String> iter = sessionUsers.keySet().iterator();
		while(iter.hasNext()) {
			IUser user = sessionUsers.get(iter.next());
			if (userCode.equalsIgnoreCase(user.getCode()))
				return user;
		}
		return null;
	}
	@Override
	@Deprecated
	public IUser[] getAllUsers() { 
		Iterator<String> iter = sessionUsers.keySet().iterator();
		IUser[] arr = new IUser[sessionUsers.size()]; 
		int i=0;
		while(iter.hasNext()) {
			arr[i++]=sessionUsers.get(iter.next());
		}
		return arr;
	}
}
