package haiyan.exp;


/**
 * @author suddenzhou
 * 
 * @param <T>
 */
public interface IExpFunction<T> extends java.io.Serializable {
	/**
	 * @param context
	 * @param funcName
	 * @param paras
	 * @return Object
	 * @throws ParseException
	 * @throws Throwable
	 */
	Object eval(IExpContext<T> context, String funcName, Object[] paras)
			throws Throwable;
}
//interface IInnerFunEval {
//	/**
//	 * @param funcName
//	 * @param paras
//	 * @return Object
//	 * @throws ParseException
//	 */
//	Object eval(String funcName, Object[] paras) throws ParseException;
//}