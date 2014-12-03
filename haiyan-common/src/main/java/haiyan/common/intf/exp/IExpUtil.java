package haiyan.common.intf.exp;

import java.io.Closeable;

public interface IExpUtil extends Closeable {

	Object evalExp(String exp) throws Throwable;

}
