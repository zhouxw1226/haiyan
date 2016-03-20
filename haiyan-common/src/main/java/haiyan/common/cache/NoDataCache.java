package haiyan.common.cache;

@Deprecated
public class NoDataCache extends AppDataCache {
	public NoDataCache() {
		super();
	}
	@Override
	public Object getLocalData(String cacheID, Object key) {
		return null;
	}
}
