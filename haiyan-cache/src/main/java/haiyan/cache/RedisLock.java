package haiyan.cache;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import haiyan.common.exception.Warning;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool; 

@SuppressWarnings("rawtypes")
public class RedisLock implements Lock, Serializable {

	private static final long serialVersionUID = 1L;
	/** 加锁标志 */
	public static final String LOCKED = "TRUE";
	/** 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒 */
	public static final long MILLI_NANO_CONVERSION = 1000 * 1000L;
	/** 默认超时时间（毫秒） */
	public static final long DEFAULT_TIME_OUT = 1000;
	public static final Random RANDOM = new Random();
	/** 锁的超时时间（秒），过期删除 */
	public static final int EXPIRE = 3 * 60;
	private Pool pool;
	private Jedis jedis;
	private String key;
	// 锁状态标志
	private boolean locked = false;
	/**
	 * This creates a RedisLock
	 * 
	 * @param key
	 *            key
	 * @param pool
	 *            数据源
	 */
	public RedisLock(String key, Pool pool) {
		this.key = key + "_lock";
		this.pool = pool;
		this.jedis = (Jedis)this.pool.getResource();
	}
	public Pool getPool() {
		return pool;
	}
	public Jedis getJedis() {
		return jedis;
	}
	public boolean isLocked() {
		return locked;
	}
	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @param timeout
	 *            超时时间(ms)
	 * @return 成功或失败标志
	 */
	public boolean lock(long timeout) {
		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (this.jedis.setnx(this.key, LOCKED) == 1) {
					this.jedis.expire(this.key, EXPIRE);
					this.locked = true;
					return this.locked;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
		}
		return false;
	}
	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @param timeout
	 *            超时时间(ms)
	 * @param expire
	 *            锁的超时时间（秒），过期删除
	 * @return 成功或失败标志
	 */
	public boolean lock(long timeout, int expire) {
		long nano = System.nanoTime();
		timeout *= MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (this.jedis.setnx(this.key, LOCKED) == 1) {
					this.jedis.expire(this.key, expire);
					this.locked = true;
					return this.locked;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
		}
		return false;
	}
	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @return 成功或失败标志
	 */
	@Override
	public void lock() {
		lock(DEFAULT_TIME_OUT);
	}
	/**
	 * 解锁 无论是否加锁成功，都需要调用unlock 应该以： lock(); try { doSomething(); } finally {
	 * unlock()； } 的方式调用
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void unlock() {
		try {
			if (this.locked) {
				this.jedis.del(this.key);
			}
		} finally {
			this.pool.returnResource(this.jedis);
		}
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		throw new Warning("not impl");
	}
}
