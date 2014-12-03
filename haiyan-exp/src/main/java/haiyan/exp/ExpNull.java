package haiyan.exp;

/**
 * 
 */
class ExpNull {

	protected ExpNull() {
	}

	public String toString() {
		return "null";
	}

	static String value() {
		return new ExpNull().toString();
	}
}
