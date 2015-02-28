package haiyan.common.util.zip;

/**
 * @author zhouxw
 * 
 */
public class EZipMode {

	public final static byte defaultMode = EZipMode.ezmOn;
	public final static byte ezmNone = 0;
	public final static byte ezmAuto = 2;
	public final static byte ezmOn = 1;
	protected byte type = 0;
	public EZipMode(byte type) {
		this.type = type;
	}

}
