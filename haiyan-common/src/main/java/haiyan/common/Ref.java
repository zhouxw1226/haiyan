package haiyan.common;

/**
 * @author ZhouXW
 * 
 * @param <T>
 */
public class Ref<T> {

	public T refValue;

	public Ref() {
	}

	public Ref(T v) {
		this.refValue = v;
	}

	public T getValue() {
		return this.refValue;
	}

	public void setValue(T v) {
		this.refValue = v;
	}

	public String toString() {
		if (this.refValue == null)
			return "";
		return this.refValue.toString();
	}
}
