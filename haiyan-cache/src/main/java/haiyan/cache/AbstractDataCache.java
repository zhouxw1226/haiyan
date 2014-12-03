package haiyan.cache;

import haiyan.common.intf.cache.IDataCache;

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
}
