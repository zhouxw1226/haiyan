package haiyan.common.cache;

import haiyan.common.intf.cache.IDataCache;
import haiyan.common.intf.config.IBillConfig;
import haiyan.common.intf.config.ITableConfig;
import haiyan.common.intf.session.IUser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AppDataCache implements IDataCache {
    protected final HashMap<String, Object> localCache = new HashMap<String, Object>();
	public AppDataCache() {
		super();
	}
	@Override
	public void setServers(String[] servers) {
	}
	@Override
	public void initialize() {
	}
	@Override
	public Object removeLocalData(String cacheID, Object key) {
		return localCache.remove(cacheID+"+"+key);
	}
	@Override
	public Object setLocalData(String cacheID, Object key, Object ele) {
		return localCache.put(cacheID+"+"+key,ele);
	}
	@Override
	public Object getLocalData(String cacheID, Object key) {
		return localCache.get(cacheID+"+"+key);
	}
	@Override
	public Object deleteData(String cacheID, Object key) {
		return removeLocalData(cacheID, key);
	}
	@Override
	public Object setData(String cacheID, Object key, Object ele) {
		return setLocalData(cacheID, key, ele);
	}
	@Override
	public Object getData(String cacheID, Object key) {
		return getLocalData(cacheID, key);
	}
	@Override
	public Object updateData(String cacheID, Object key, Object ele) {
		return setLocalData(cacheID, key, ele);
	}
	@Override
	public void clearData(String cacheID) {
	}
//	// ------------------------------------------------------------------------ //
//	@Override
//	public boolean addListData(String cacheID, Object ele) {
//		return false;
//	}
//	@Override
//	public boolean removeListData(String cacheID, Object key) {
//		return false;
//	}
//	@Override
//	public int getDataSize(String cacheID) {
//		return 0;
//	}
//	@Override
//	public int getIndexOf(String key, Object o) {
//		return 0;
//	}
//	@Override
//	public int getLastIndexOf(String key, Object o) {
//		return 0;
//	}
	// ------------------------------------------------------------------------ //
    @Override
    public File setTableFile(String sKey, File file) {
		return (File)this.setData("TABLEFILE", sKey, file);
    }
    @Override
    public File getTableFile(String sKey) {
		return (File)this.getData("TABLEFILE", sKey);
    }
    @Override
    public boolean removeTableFile(String sKey) {
		Object o = this.deleteData("TABLEFILE", sKey);
		return o!=null;
    }
	@Override
	public ITableConfig setTable(String sKey, ITableConfig table) {
		return (ITableConfig)this.setData("TABLE", sKey, table);
	}
	@Override
	public ITableConfig getTable(String sKey) {
		return (ITableConfig)this.getData("TABLE", sKey);
	}
	@Override
	public boolean removeTable(String sKey) {
		Object o = this.deleteData("TABLE", sKey);
		return o!=null;
	}
	// ------------------------------------------------------------------------ //
    @Override
    public File setBillFile(String sKey, File file) {
		return (File)this.setData("BILLFILE", sKey, file);
    }
    @Override
    public File getBillFile(String sKey) {
		return (File)this.getData("BILLFILE", sKey);
    }
    @Override
    public boolean removeBillFile(String sKey) {
		Object o = this.deleteData("BILLFILE", sKey);
		return o!=null;
    }
	@Override
	public IBillConfig setBill(String sKey, IBillConfig bill) {
		return (IBillConfig)this.setData("BILL", sKey, bill);
	}
	@Override
	public IBillConfig getBill(String sKey) {
		return (IBillConfig)this.getData("BILL", sKey);
	}
	@Override
	public boolean removeBill(String sKey) {
		Object o = this.deleteData("BILL", sKey);
		return o!=null;
	}
	// ------------------------------------------------------------------------ //
	@Override
	public IUser[] getAllUsers() {
		return null;
	}
	@Override
	public IUser setUser(String sKey, IUser user) {
		return (IUser)this.setData("IUSER", sKey, user);
	}
	@Override
	public IUser getUser(String sKey) {
		return (IUser)this.getData("IUSER", sKey);
	}
	@Override
	public boolean removeUser(String sKey) {
		Object o = this.deleteData("IUSER", sKey);
		return o!=null;
	}
	@Override
	public boolean containsUser(String sKey) {
		return localCache.containsKey("IUSER+"+sKey);
	}
	@Override
	public IUser getUserByCode(String userCode) {
		return null;
	}
	@Override
	public IUser getUserByID(String userID) {
		return null;
	}
	// ------------------------------------------------------------------------ //
    @Override
    public String[] getAllTableKeys() {
        ArrayList<String> list = new ArrayList<String>(); 
        Iterator<String> iter = localCache.keySet().iterator();
       	while (iter.hasNext()){
       		String key = iter.next();
            Object obj = localCache.get(key);
            if (obj == null)
                continue;
            if (!(obj instanceof ITableConfig))
            	continue;
            ITableConfig table = (ITableConfig) obj;
            list.add(table.getName());
        }
        return list.toArray(new String[0]);
    }
    @Override
    public String[] getAllBillKeys() {
        ArrayList<String> list = new ArrayList<String>();
        Iterator<String> iter = localCache.keySet().iterator();
       	while (iter.hasNext()){
       		String key = iter.next();
            Object obj = localCache.get(key);
            if (obj == null)
                continue;
            if (!(obj instanceof IBillConfig))
            	continue;
            IBillConfig bill = (IBillConfig) obj;
            list.add(bill.getName());
        }
        return list.toArray(new String[0]);
    }

}
