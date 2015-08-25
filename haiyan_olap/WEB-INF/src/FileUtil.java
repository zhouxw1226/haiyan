import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

/**
 * @author zhouxw
 * 
 */
public class FileUtil {

	public static String path = "H:/mondrian3/WEB-INF/queries";

	/**
	 * @param srcPath
	 * @param destPath
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean CopyFile(String srcPath, String destPath)
			throws Exception {
		//
		if (srcPath == null || srcPath.equals("") || srcPath.length() == 0)
			return false;
		if (destPath == null || destPath.equals("") || destPath.length() == 0)
			return false;
		//
		File srcFile = new File(srcPath);
		File nestFile = new File(destPath);
		//
		return FileUtil.CopyFile(srcFile, nestFile);
	}

	/**
	 * @param srcFile
	 * @param destFile
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean CopyFile(File srcFile, File destFile)
			throws Exception {
		//
		FileInputStream fin = null;
		FileOutputStream fout = null;
		int chi;
		//
		if (srcFile == null || !srcFile.exists())
			return false;
		if (destFile == null)
			return false;
		if (!destFile.exists())
			destFile.mkdirs();
		//
		try {
			fin = new FileInputStream(srcFile);
			fout = new FileOutputStream(destFile);
			while ((chi = fin.read()) != -1) {
				fout.write((char) chi);
			}
			fout.flush();
			return true;
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fout.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 获取文件内容，以String形式返回
	 * 
	 * @param file
	 * @return String
	 * @throws Warning
	 * @throws Exception
	 */
	public static String File2String(File srcFile, String encoding)
			throws Exception {
		String content = null;
		// StringBuffer buffer = null;
		byte[] bytes = null;
		FileInputStream fin = null;
		// Exception exp = null;
		// boolean succ = false;
		if (srcFile == null || !srcFile.exists())
			return content;
		try {
			// buffer = new StringBuffer();
			bytes = new byte[(int) srcFile.length()];
			fin = new FileInputStream(srcFile);
			fin.read(bytes);
			if (encoding == null)
				content = new String(bytes);
			else
				content = new String(bytes, encoding);// encoding="gb2312";
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (fin != null)
				fin.close();
		}
		return content;
	}

	/**
	 * @param srcPath
	 * @param encoding
	 * @return String
	 * @throws Warning
	 * @throws Exception
	 */
	public static String File2String(String srcPath, String encoding)
			throws Exception {
		return File2String(new File(srcPath), encoding);
		// String content = null;
		// // StringBuffer buffer = null;
		// byte[] bytes = null;
		// FileInputStream fin = null;
		// // boolean succ = false;
		// File srcFile = null;
		// try {
		// srcFile = new File(srcPath);
		// // DebugUtil.debug(srcFile.getName());
		// if (srcFile == null || !srcFile.exists())
		// return content;
		// // buffer = new StringBuffer();
		// bytes = new byte[(int) srcFile.length()];
		// fin = new FileInputStream(srcFile);
		// fin.read(bytes);
		// // DebugUtil.debug();
		// if (encoding != null)
		// content = new String(bytes, encoding);// encoding="gb2312";
		// else
		// content = new String(bytes);
		// } catch (Exception ex) {
		// throw ex;
		// } finally {
		// if (fin != null)
		// fin.close();
		// }
		// return content;
	}

	/**
	 * @param str
	 *            内容
	 * @param filePath
	 *            文件路径
	 * @param confirm
	 *            是否确认覆盖
	 * @return File
	 * @throws Warning
	 * @throws Exception
	 */
	public static File String2File(String str, String filePath, boolean confirm)
			throws Exception {
		if (filePath == null || filePath.equals("") || filePath.length() == 0)
			return null;
		File destFile = new File(filePath);
		return FileUtil.String2File(str, destFile, confirm);
	}

	/**
	 * @param str
	 * @param destFile
	 * @return File
	 * @throws Exception
	 */
	public static File String2File(String str, File destFile, boolean confirm)
			throws Exception {
		//
		byte[] bytes = null;
		//
		if (destFile == null)
			return null;
		if (destFile.exists() && confirm) {
			if (JOptionPane.showConfirmDialog(null, " 改文件已存在，覆盖源文件吗 ", "确认",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != 0)
				return null;
		}
		FileOutputStream fout = null;
		try {
			File parentFile = new File(destFile.getParent());
			if (!parentFile.exists())
				parentFile.mkdirs();
			fout = new FileOutputStream(destFile);
			//
			bytes = str.getBytes();
			//
			fout.write(bytes);
			fout.flush();
			// succ = true;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (fout != null)
				fout.close();
		}
		return destFile;
	}

	/**
	 * @param resource
	 * @param pack
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getInStream(String resource, Package pack)
			throws IOException {
		String path = pack.getName().replace('.', '/') + '/' + resource;
		// System.out.println(path);
		FileUtil t = new FileUtil();
		InputStream in = t.getClass().getClassLoader()
				.getResourceAsStream(path);
		return in;
	}

	/**
	 * @param resource
	 * @param pack
	 * @return ByteArrayOutputStream
	 * @throws IOException
	 */
	public static ByteArrayOutputStream getByteOutStream(String resource,
			Package pack) throws IOException {
		String path = pack.getName().replace('.', '/') + '/' + resource;
		// System.out.println(path);
		FileUtil t = new FileUtil();
		InputStream in = t.getClass().getClassLoader()
				.getResourceAsStream(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final int BUFFER_SIZE = 1024 * 8;// 缓冲区大小
		byte[] buffer = new byte[BUFFER_SIZE];// 缓冲区
		int bytesRead;// 实际读取的字节数
		while ((bytesRead = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos;
	}

	/**
	 * @param fileName
	 * @return String
	 */
	public static String getFileType(String fileName) {
		if (fileName.lastIndexOf(".") != -1)
			return fileName.substring(fileName.lastIndexOf("."), fileName
					.length());
		return "";
	}

	/**
	 * @param file
	 * @param size
	 * @return long
	 */
	public static long getFileSize(File file, long size) {
		String[] dir = file.list();
		if (dir != null)
			for (int i = 0; i < dir.length; i++) {
				// 加空格
				File temp = new File(file, dir[i]);
				// 如果是一个目录，还得去搜索子目录
				if (temp.isDirectory()) {
					size = getFileSize(temp, size);
				} else {
					size += temp.length();
				}
			}
		return size;
	}

	/**
	 * @param destFile
	 * @param bytes
	 * @throws FileNotFoundException
	 */
	public static void write(File destFile, byte[] bytes) throws Exception {
		File parentFile = new File(destFile.getParent());
		if (!parentFile.exists())
			parentFile.mkdirs();
		if (!destFile.exists())
			destFile.createNewFile();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destFile);
			fos.write(bytes);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * @param fileName
	 * @return String
	 */
	public static String getFileName(String fileName) {
		if (fileName == null)
			return "";
		String tempValue = null;
		if (fileName.lastIndexOf("/") != -1)
			tempValue = fileName.substring(fileName.lastIndexOf("/") + 1,
					fileName.length());
		else if (fileName.lastIndexOf("\\") != -1)
			tempValue = fileName.substring(fileName.lastIndexOf("\\") + 1,
					fileName.length());
		else
			tempValue = fileName;
		if (tempValue.lastIndexOf(".") != -1)
			tempValue = tempValue.substring(0, tempValue.lastIndexOf("."));
		return tempValue;
	}
}