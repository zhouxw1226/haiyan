package haiyan.common.cache;

import haiyan.common.intf.cache.IDataCache;

@Deprecated
public class NoDataCache extends AppDataCache implements IDataCache {
	public NoDataCache() {
		super();
	}
	@Override
	public Object getLocalData(String cacheID, Object key) {
		return null;
	}
}
