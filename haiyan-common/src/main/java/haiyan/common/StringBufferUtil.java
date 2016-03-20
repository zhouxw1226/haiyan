/*
 * Created on 2004-9-25
 */
package haiyan.common;

/**
 * 
 * @author zhouxw
 */
public class StringBufferUtil {

	private StringBuffer buff;

	/**
	 * @param buff
	 */
	public StringBufferUtil(StringBuffer buff) {
		this.buff = buff;
	}

	/**
	 * @param buff
	 */
	public StringBufferUtil(String str) {
		this.buff = new StringBuffer(str);
	}
	
	/**
	 * @param i
	 */
	public StringBufferUtil(int i) {
		this.buff = new StringBuffer(i);
	}

	/**
	 * 
	 */
	public StringBufferUtil() {
		this.buff = new StringBuffer();
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public StringBufferUtil appendln(String s) {
		buff.append(s).append("\n");
		return this;
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public StringBufferUtil append(String s) {
		buff.append(s);
		return this;
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public StringBufferUtil appendln(Object s) {
		buff.append(s).append("\n");
		return this;
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public StringBufferUtil append(Object s) {
		buff.append(s);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return buff.toString();
	}

	// public static void main(String[] args) {
	// }
}