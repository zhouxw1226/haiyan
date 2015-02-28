package haiyan.common.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author zhouxw
 * 
 */
public class ZipUtil {
	/**
	 * 
	 */
	private static int MAX_BUF_SIZE = 1024;
	/**
	 * @param data
	 * @param off
	 * @param len
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] zip(byte[] data, int off, int len) throws IOException {
		// Decompress the bytes
		byte[] buf = new byte[MAX_BUF_SIZE];
		// ����һ��ѹ��������ý�ѹ�����Ҫѹ�����Ϊdata
		Deflater compresser = new Deflater();
		while (compresser.needsInput())
			compresser.setInput(data, off, len);
		compresser.finish();
		// ����һ���)չ��byte array���ڱ���ѹ�����
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// DeflaterOutputStream os = new DeflaterOutputStream(bos, compresser);
		// ѹ�����
		int size = 0;
		while (!compresser.finished()) {
			int count = compresser.deflate(buf);
			if (count == 0)
				break;
			bos.write(buf, size, count);
			// size += count;
		}
		byte[] t = bos.toByteArray();
		compresser.end();
		bos.close();
		return t;
	}
	/**
	 * @param data
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] zip(byte[] data) throws IOException {
		return zip(data, 0, data.length);
	}
	/**
	 * @param data
	 * @param off
	 * @param len
	 * @return byte[]
	 * @throws DataFormatException
	 * @throws IOException
	 */
	public static byte[] unzip(byte[] data, int off, int len)
			throws DataFormatException, IOException {
		// Decompress the bytes
		byte[] buf = new byte[MAX_BUF_SIZE];
		// ����һ���ѹ������ý�ѹ�����Ҫ��ѹ����Ϊdata
		Inflater decompressor = new Inflater();
		while (decompressor.needsInput())
			decompressor.setInput(data, off, len);
		// decompressor.end();
		// ����һ���)չ��byte array���ڱ����ѹ���
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// ��ѹ���
		int size = 0;
		while (!decompressor.finished()) {
			try {
				int count = decompressor.inflate(buf);
				if (count == 0)
					break;
				bos.write(buf, size, count);
			} catch (DataFormatException e) {
				throw e;
			}
		}
		byte[] t = bos.toByteArray();
		decompressor.end();
		bos.close();
		return t;
	}
	/**
	 * @param data
	 * @return byte[]
	 * @throws DataFormatException
	 * @throws IOException
	 */
	public static byte[] unzip(byte[] data) throws DataFormatException,
			IOException {
		return unzip(data, 0, data.length);
	}

}
