package haiyan.exp;



/**
 * context wrapper
 */
public interface IExpContext<T> extends java.io.Serializable {

//	/**
//	 * java.util.PropertyResourceBundle
//	 */
//	static ResourceBundle FORMULA_RESOURCE = ResourceBundle.getBundle("formula");
	/**
	 * @param funName
	 * @return IFunctionEval
	 * @throws Throwable
	 */
	IExpFunction<T> getImplInstance(String funName) throws Throwable;
	/**
	 * @param sExtendProg
	 * @param function
	 * @return IFunctionEval
	 * @throws Throwable
	 */
	IExpFunction<T> getExtendImpInstance(String sExtendProg, String function)
			throws Throwable;
	/**
	 * @return IExpContext
	 */
	IExpContext<T> getNextLayer();

	/**
	 * @return String[]
	 * @throws Throwable 
	 */
	String[] getExtendImpClass() throws Throwable;

	/**
	 * @param vars
	 */
	void setInnerVars(Object[] vars);

	/**
	 * @return Object[]
	 */
	Object[] getInnerVars();

	/**
	 * @return Context
	 * @throws Throwable 
	 */
	T getContext() throws Throwable;

//	/**
//	 * @return DBManager
//	 * @throws Throwable
//	 */
//	ITableDBManager getDBM() throws Throwable; 
//
//	/**
//	 * @return DBManager
//	 * @throws Throwable
//	 */
//	IBillDBManager getBBM() throws Throwable; 

	/**
	 * @param key
	 * @param value
	 */
	void setParameter(String key, Object value);

	/**
	 * @param key
	 * @return Object
	 */
	Object getParameter(String key);

	/**
	 * @param key
	 * @return
	 */
	Object removeParameter(String key);

	/**
	 * 
	 */
	void close();

}
