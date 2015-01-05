package haiyan.cache;

import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.File;
import java.util.HashMap;

public abstract class AbstractDataCache implements IDataCache {

    protected final HashMap<String, File> tableFiles = new HashMap<String, File>();
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
        return true;
    }
    protected final HashMap<String, File> billFiles = new HashMap<String, File>();
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
        return true;
    }
    // ---------------------------------------------------------------------- //
	@Override
	public void clearData(String cacheID) {
		// TODO Auto-generated method stub
	}
	@Override
	public Object removeLocalData(String cacheID, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object setLocalData(String cacheID, Object key, Object ele) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getLocalData(String cacheID, Object key) {
		// TODO Auto-generated method stub
		return null;
	}
    // ---------------------------------------------------------------------- //
	@Override
	public ITableConfig setTable(String sKey, ITableConfig table) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ITableConfig getTable(String sKey) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getAllTableKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean removeTable(String sKey) {
		// TODO Auto-generated method stub
		return false;
	}
    // ---------------------------------------------------------------------- //
	@Override
	public IBillConfig setBill(String sKey, IBillConfig bill) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IBillConfig getBill(String sKey) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getAllBillKeys() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean removeBill(String sKey) {
		// TODO Auto-generated method stub
		return false;
	}
    // ---------------------------------------------------------------------- //
	@Override
	public IUser[] getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean containsUser(String sessionId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public IUser getUserByCode(String userCode) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IUser getUserByID(String userID) {
		// TODO Auto-generated method stub
		return null;
	}
}
