package haiyan.common.intf.session;

import java.io.Closeable;

/**
 * 会话接口==生命周期管理ILifeCycle(UnitOfWork)
 * 
 * @author ZhouXW
 *
 */
public interface ISession extends Closeable {

	/**
	 * 是否存活
	 * 
	 * @return
	 */
	boolean isAlive();
	
}
