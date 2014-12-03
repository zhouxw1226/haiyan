/*
 * Created on 2004-9-25
 */
package haiyan.common;

/**
 * 
 * @author zhouxw
 */
public class HyStringBuffer {

	private StringBuffer buff;

	/**
	 * @param buff
	 */
	public HyStringBuffer(StringBuffer buff) {
		this.buff = buff;
	}

	/**
	 * @param buff
	 */
	public HyStringBuffer(String str) {
		this.buff = new StringBuffer(str);
	}
	
	/**
	 * @param i
	 */
	public HyStringBuffer(int i) {
		this.buff = new StringBuffer(i);
	}

	/**
	 * 
	 */
	public HyStringBuffer() {
		this.buff = new StringBuffer();
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public HyStringBuffer appendln(String s) {
		buff.append(s).append("\n");
		return this;
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public HyStringBuffer append(String s) {
		buff.append(s);
		return this;
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public HyStringBuffer appendln(Object s) {
		buff.append(s).append("\n");
		return this;
	}

	/**
	 * @param s
	 * @return StringBuffer
	 */
	public HyStringBuffer append(Object s) {
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