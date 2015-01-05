package haiyan.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;

/**
 * @author suddenzhou
 * 
 */
public class ByteUtil {

//	public static Reader getReader(String value) { // byte[] bytes
////		InputStream bais = new ByteArrayInputStream(bytes);
////		return new InputStreamReader(bais);
//		return new StringReader(value);
//	}
	public static byte[] toBytes(Serializable obj) {
		if (obj == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream dos = null;
		byte[] bytes = null;
		try {
			dos = new ObjectOutputStream(baos);
			dos.writeObject(obj);
			bytes = baos.toByteArray();
		} catch(Throwable e){
			throw new RuntimeException(e);
		} finally {
			CloseUtil.close(dos);
			CloseUtil.close(baos);
		}
		return bytes;
	}
	public static Serializable toObject(byte[] bytes) {
		if (bytes == null)
			return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream dis = null;
		Serializable s = null;
		try {
			dis = new ObjectInputStream(bis);
			s = (Serializable) dis.readObject();
		} catch(Throwable e){
			throw new RuntimeException(e);
		} finally {
			CloseUtil.close(dis);
			CloseUtil.close(bis);
		}
		return s;
	}
	public static String getString(Reader reader) {
		if (reader == null)
			return null;
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer writer = null;
		char[] buff = new char[1024]; 
		try {
//			writer = new OutputStreamWriter(baos);
			writer = new StringWriter();
	        for (int i = 0; (i = reader.read(buff)) > 0;) { 
	        	writer.write(buff, 0, i); 
	        } 
	        writer.flush();
	        return writer.toString();
//	        return baos.toByteArray();
		} catch(Throwable e){
			throw new RuntimeException(e);
		} finally {
			CloseUtil.close(writer);
		}
	}
	// /**
	// * @param str
	// * @return InputStream
	// */
	// public static InputStream getInputStream(String str) {
	// if (StringUtil.isBlankOrNull(str))
	// return null;
	// return new ByteArrayInputStream(str.getBytes());
	// }
	/**
	 * @param is
	 * @return byte[]
	 * @throws Throwable
	 */
	public static byte[] getBytes(InputStream is) {
		if (is==null)
			return null;
		// ByteBuffer bb = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int cha = -1;
		try {
			while ((cha = is.read(bytes)) != -1) {
				baos.write(bytes, 0, cha);
			}
			baos.flush();
			return baos.toByteArray();
		} catch(Throwable e){
			throw new RuntimeException(e);
		} finally {
			CloseUtil.close(baos);
		}
	}

	/**
	 * @param is
	 * @return OutputStream
	 * @throws Throwable
	 */
	public static OutputStream getByteArray(InputStream is)
			throws Throwable {
		// ByteBuffer bb = null;
		OutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int cha = -1;
		while ((cha = is.read(bytes)) != -1) {
			baos.write(bytes, 0, cha);
		}
		baos.flush();
		return baos;
	}
	/**
	 * @param is
	 * @param off
	 * @param length
	 * @param total
	 * @return byte[]
	 * @throws Throwable
	 */
	public static byte[] getBytes(InputStream is, int off, int length, int total)
			throws Throwable {
		int endCount = (off - 1) / length;
		if (off > total)
			return EMPTY_BYTES;
		ByteBuffer bb = null;
		// int len = length < total - off ? length : total - off;
		byte[] bytes = new byte[length];
		int count = 0;
		int cha = -1;
		while ((cha = is.read(bytes, 0, length)) != -1) {
			if (count == endCount) {
				bb = ByteBuffer.allocate(cha);
				bb.put(bytes, 0, cha);
				// return ByteBuffer.wrap(bytes).array();
				break;
			}
			count++;
		}
		if (bb == null)
			return EMPTY_BYTES;
		return bb.array();
	}
	public final static byte[] EMPTY_BYTES = new byte[0];

}
