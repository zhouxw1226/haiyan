package test.cache;
//package haiyan.common.cache;
//
//import com.danga.MemCached.MemCachedClient;
//import com.danga.MemCached.SockIOPool; 
//
//public class TestMemCache {
//
//	public static void main(String[] args) throws Throwable {
//		test();
//		test1();
//	}
//	
//	public static boolean flushAll() {
//		return mcc.flushAll();
//	}
//	
//	public static boolean set(String k, Object o) {
//		return mcc.set(k, o);
//	}
//	
//	public static Object get(String k) {
//		return mcc.get(k);
//	}
//	
//	private static void test1() {
//		String k = "Haiyan.USERS.fef7eda0-9bb2-4b57-8607-c91f72845000";
//		try {
//			System.out.println(mcc.get(k));
//		}catch(Throwable e){
////			mcc.flushAll();
//			mcc.delete(k);
//			e.printStackTrace();
//		}
//	}
//	
//	private static void test() throws Throwable {
//		//SrvContext c = new TempSrvContext("1");
//		User u = new User();//c.getUser();
//		u.setID("1");
//		u.setName("admin");
//		u.setCode("admin");
//		u.setProperty("ROLEID", 11223344);
//		
//		Qbq3Form f = new MapForm();
//		f.set("ID", "aaa");
//		String s = "E:\\eclipse_workspace_hy\\haiyan\\upload\\SYSMESSAGE\\MEMO\\test.txt";
//        FileUtil.toFile("abcdefg", s + ".bak", DataConstant.CHARSET);
//        f.addBlobFile(s);
//
//		//mcc.set("foo", "This is a test String 11");
//		//mcc.append("foo", u);
//		mcc.set("foo", u);
//		mcc.set("bar", f);
//		mcc.set("bar.size", 0);
//		//System.out.println(">>> " + mcc.gets("foo").getValue().getClass());
//		System.out.println(">>> " + mcc.get("foo").getClass());
//		System.out.println(">>> " + mcc.get("foo"));
//		System.out.println(">>> " + mcc.get("bar").getClass());
//		System.out.println(">>> " + mcc.get("bar"));
//		System.out.println(">>> " + ((Qbq3Form)mcc.get("bar")).getBlobFile());
//		System.out.println(">>> " + mcc.incr("bar.size"));
//		System.out.println(">>> " + mcc.decr("bar.size"));
//		System.out.println(">>> " + mcc.get("bar.size"));
//		
//		f.commit();
//	}
//
//	protected static MemCachedClient mcc = new MemCachedClient();
//
//	static {
//
//		// 设置缓存服务器列表，当使用分布式缓存的时，可以指定多个缓存服务器。这里应该设置为多个不同的服务，我这里将两个服务设置为一样的，大家不要向我学习，呵呵。
//		String[] servers = { "localhost:46666",
//		// "server3.mydomain.com:1624"
//		};
//
//		// 设置服务器权重
//		Integer[] weights = { 1 };
//
//		// 创建一个Socked连接池实例
//		SockIOPool pool = SockIOPool.getInstance();
//
//		// 向连接池设置服务器和权重
//		pool.setServers(servers);
//		pool.setWeights(weights);
//
//		// set some TCP settings
//		// disable nagle
//		// set the read timeout to 3 secs
//		// and don't set a connect timeout
//		pool.setNagle(false);
//		pool.setSocketTO(3000);
//		pool.setSocketConnectTO(0);
//
//		// initialize the connection pool
//		pool.initialize();
//	}
//}
