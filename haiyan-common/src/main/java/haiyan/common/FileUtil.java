package haiyan.common;

import haiyan.common.exception.Warning;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author zhouxw
 * 
 */
public class FileUtil {

	// public static void main(String[] args) {
	// try {
	// System.getProperties();
	// System.out.println(toString(new File(ConfigUtil.getPathName()
	// + File.separator + "derby.log"), null));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// //
	// private static final FileUtil fileUtil = new FileUtil();

	//
	private static final int BUFFER_SIZE = 1024 * 32;

	/**
	 * @param srcPath
	 * @param destPath
	 * @return boolean
	 * @throws Throwable
	 */
	public static boolean copy(String srcPath, String destPath)
			throws Throwable {
		if (srcPath == null || srcPath.equals("") || srcPath.length() == 0)
			return false;
		if (destPath == null || destPath.equals("") || destPath.length() == 0)
			return false;
		File srcFile = new File(srcPath);
		File nestFile = new File(destPath);
		return copy(srcFile, nestFile);
	}
	

	/**
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @throws Throwable
	 */
	public static boolean copy(File srcFile, File destFile) throws Throwable {
		return copy(srcFile, destFile, (String)null);
	}

	/**
	 * @param srcFile
	 * @param destFile
	 * @param srcRootPath
	 * @return
	 * @throws Throwable
	 */
	public static boolean copy(File srcFile, File destFile, String... srcRootPath) throws Throwable {
		if (srcFile == null || !srcFile.exists())
			return false;
		if (destFile == null)
			return false;
		
		if (!destFile.getParentFile().exists())
			destFile.getParentFile().mkdirs();
		
		if (srcFile.isFile()) {
			if (!destFile.exists())
				destFile.createNewFile();
			
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);
				int chi;
				while ((chi = in.read()) != -1) {
					out.write((char) chi);
				}
				out.flush();
				return true;
			}
			// catch (Throwable ex) {
			// throw ex;
			// }
			finally {
				CloseUtil.close(in);
				CloseUtil.close(out);
			}
			
		} else if (srcFile.isDirectory()) {
			if (!destFile.exists())
				destFile.mkdirs();

			String srcPath = null;
			String destPath = null;
			File copyFile = null;
			File[] files = srcFile.listFiles();
			for (File file:files) {
				if (srcRootPath==null||srcRootPath.length==0||srcRootPath[0]==null) {
					srcRootPath[0] = srcFile.getAbsolutePath();
				}
				srcPath = file.getAbsolutePath();
				destPath = destFile.getAbsolutePath();
				copyFile = new File(destPath.substring(0, destPath.lastIndexOf("project")+7)
						+srcPath.substring(srcPath.lastIndexOf("project")+7));
				copy(file, copyFile, srcRootPath[0]);
			}
		}
		return false;
	}

	/**
	 * @param srcPath
	 * @param encoding
	 * @return String
	 * @throws Throwable
	 */
	public static String toString(String srcPath, String encoding)
			throws Throwable {
		if (StringUtil.isBlankOrNull(srcPath))
			return null;
		return toString(new File(srcPath), encoding);
	}

	/**
	 * @param srcFile
	 * @param encoding
	 * @return String
	 * @throws Throwable
	 */
	public static String toString(File srcFile, String encoding)
			throws Throwable {
		if (srcFile == null || !srcFile.exists())
			return null;
		String content = null;
		// StringBuffer buffer = null;
		byte[] bytes = null;
		// InputStream in = null;
		// Exception exp = null;
		ByteArrayOutputStream out = null;
		// boolean succ = false;
		try {
			// buffer = new StringBuffer();
			// bytes = new byte[(int) srcFile.length()];
			// in = new FileInputStream(srcFile);
			// in.read(bytes);
			out = getByteOutStream(srcFile);
			bytes = out.toByteArray();
			if (encoding == null)
				content = new String(bytes);
			else
				content = new String(bytes, encoding);
		}
		// catch (Exception ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(out);
		}
		return content;
	}

	/**
	 * @param data
	 * @param filePath
	 * @return File
	 * @throws Throwable
	 */
	public static File toFile(String data, String filePath) throws Throwable {
		if (StringUtil.isBlankOrNull(filePath))
			return null;
		// File destFile = new File(filePath);
		return FileUtil.toFile(data, new File(filePath), null);
	}

	/**
	 * @param data
	 * @param filePath
	 * @param encoding
	 * @return File
	 * @throws Throwable
	 */
	public static File toFile(String data, String filePath, String encoding)
			throws Throwable {
		if (StringUtil.isBlankOrNull(filePath))
			return null;
		// File destFile = new File(filePath);
		return FileUtil.toFile(data, new File(filePath), encoding);
	}

	/**
	 * @param data
	 * @param destFile
	 * @param encoding
	 * @return File
	 * @throws Throwable
	 */
	public static File toFile(String data, File destFile, String encoding)
			throws Throwable {
		if (data==null)
			return null;
		byte[] bytes = null;
		if (destFile == null)
			return null;
		File parentFile = new File(destFile.getParent());
		if (!parentFile.exists())
			parentFile.mkdirs();
		// if (destFile.exists() && confirm) {
		// if (JOptionPane.showConfirmDialog(null, "ȷ��",
		// JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != 0)
		// return null;
		// }
		OutputStream out = null;
		try {
			//
			out = new FileOutputStream(destFile);
			if (encoding == null)
				bytes = data.getBytes();
			else
				bytes = data.getBytes(encoding);
			out.write(bytes);
			out.flush();
			// succ = true;
		}
		// catch (Exception ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(out);
		}
		return destFile;
	}

	/**
	 * @param resource
	 * @param pack
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getInputStream(String resource, Package pack)
			throws Throwable {
		String path = pack.getName().replace('.', '/') + '/' + resource;
		// System.out.println(path);
		FileUtil t = new FileUtil();
		InputStream in = t.getClass().getClassLoader().getResourceAsStream(path);
		return in;
	}

	/**
	 * @param clz
	 * @param resource
	 * @return ByteArrayOutputStream
	 * @throws Throwable
	 */
	public static ByteArrayOutputStream getByteOutStream(
			@SuppressWarnings("rawtypes") Class clz, String resource)
			throws Throwable {
		InputStream in = null;
		try {
			in = clz.getResourceAsStream(resource);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];// 缓冲区
			int bytesRead;// 实际读取的字节数
			while ((bytesRead = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			baos.flush();
			return baos;
		} finally {
			CloseUtil.close(in);
		}
	}

	/**
	 * @param fileName
	 * @return ByteArrayOutputStream
	 * @throws IOException
	 */
	public static ByteArrayOutputStream getByteOutStream(String fileName)
			throws Throwable {
		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;//
			while ((bytesRead = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			baos.flush();
			return baos;
		} finally {
			CloseUtil.close(in);
		}
	}

	/**
	 * @param fileName
	 * @return ByteArrayOutputStream
	 * @throws IOException
	 */
	public static ByteArrayOutputStream getByteOutStream(File file)
			throws Throwable {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;//
			while ((bytesRead = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			baos.flush();
			return baos;
		} finally {
			CloseUtil.close(in);
		}
	}

	/**
	 * @param resource
	 * @param pack
	 * @return ByteArrayOutputStream
	 * @throws IOException
	 */
	public static ByteArrayOutputStream getByteOutStream(String resource,
			Package pack) throws Throwable {
		String path = pack.getName().replace('.', '/') + '/' + resource;
		// System.out.println(path);
		InputStream in = null;
		try {
			in = FileUtil.class.getClassLoader().getResourceAsStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;//
			while ((bytesRead = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			baos.flush();
			return baos;
		} finally {
			CloseUtil.close(in);
		}
	}

	/**
	 * @param fileName
	 * @return String
	 */
	public static String getFileType(String fileName) {
		if (fileName.lastIndexOf(".") != -1)
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		return "";
	}

	/**
	 * @param file
	 * @param size
	 * @return long
	 */
	public static long getFileSize(File file, long size) {
		if (!file.exists())
			return 0;
		String[] dir = file.list();
		if (dir != null)
			for (int i = 0; i < dir.length; i++) {
				File temp = new File(file, dir[i]);
				if (temp.isDirectory()) {
					size = getFileSize(temp, size);
				} else {
					size += temp.length();
				}
			}
		return size;
	}

//	/**
//	 * @param absPathName
//	 * @return String
//	 */
//	public static String getRealFileName(String absPathName) {
//		// 判断2中分隔符
//		if (absPathName != null && absPathName.indexOf("/") != -1)
//			return absPathName.substring(absPathName.lastIndexOf("/") + 1, absPathName.length());
//		else if (absPathName != null && absPathName.indexOf("\\") != -1)
//			return absPathName.substring(absPathName.lastIndexOf("\\") + 1, absPathName.length());
//		else if (absPathName.indexOf(".") >= 0)
//			return absPathName;
//		else
//			throw new Warning(new TransCode(100027, "unsupport_filepath"));
//	}

	/**
	 * @param fileName
	 * @return String
	 */
	public static String getFileName(String fileName) {
		if (fileName == null)
			return "";
		String tempValue = null;
		if (fileName.lastIndexOf("/") != -1)
			tempValue = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
		else if (fileName.lastIndexOf("\\") != -1)
			tempValue = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
		else
			tempValue = fileName;
		if (tempValue.lastIndexOf(".") != -1)
			tempValue = tempValue.substring(0, tempValue.lastIndexOf("."));
		return tempValue;
	}

	/**
	 * @param pathName
	 * @return ArrayList
	 * @throws Throwable
	 */
	private static ArrayList<File> getPattern(String pathName,
			final String filter) throws Throwable {
		File path = new File(pathName);
		if (!path.exists())
			throw new Warning("文件不存在:" + pathName);
		// can return file[],not list
		final ArrayList<File> list = new ArrayList<File>();
		File[] xmls = path.listFiles(new FileFilter() {
			public boolean accept(File f) {
				if (f.isFile() && f.getName().toLowerCase().endsWith(filter)) {
					return true;
				} else {
					if (f.isDirectory() && f.getName().indexOf("@deprecated") < 0) {
						try {
							list.addAll(getPattern(f.getAbsolutePath(), filter));
						} catch (Throwable e) {
							DebugUtil.error(e);
						}
					}
					return false;
				}
			}
		});
		for (int i = 0; i < xmls.length; i++) {
			list.add(xmls[i]);
		}
		return list;
	}
	/**
	 * @param pathName
	 * @return File[]
	 * @throws Throwable
	 */
	public static File[] getFilesByPath(String pathName, String filter)
			throws Throwable {
		ArrayList<File> list = getPattern(pathName, filter);
		Collections.sort(list, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		// File tFile = null;
		// for (int i = 0; i < list.size() - 1; i++)
		// for (int j = i + 1; j < list.size(); j++)
		// if (list.get(i).getName().compareTo(list.get(j).getName()) > 0) {
		// tFile = list.get(i);
		// list.set(i, list.get(j));
		// list.set(j, tFile);
		// }
		if (list.size() == 0) {
			// throw new Warning(new TransCode(100033, "file_not_found",
			// new String[] { pathName }));
			return new File[0];
		}
		return list.toArray(new File[0]);
	}
	/**
	 * @param destFile
	 * @param bytes
	 * @throws Throwable
	 */
	public static void write(File destFile, byte[] bytes) throws Throwable {
		File parentFile = destFile.getParentFile();
		if (!parentFile.exists())
			if (!parentFile.mkdirs())
				return;
		if (!destFile.exists())
			if (!destFile.createNewFile())
				return;
		OutputStream out = null;
		try {
			out = new FileOutputStream(destFile);
			out.write(bytes);
			out.flush();
		}
		// catch (Throwable ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(out);
		}
	}

	/**
	 * @param byes
	 * @throws Throwable
	 */
	public static byte[] read(File file) throws Throwable {
		if (!file.exists())
			return new byte[0];
		
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 创建缓存
		byte[] bytes = new byte[1024];
		// 字节读取大小标记
		int cha = -1;
		try {
			is = new FileInputStream(file);
			// DebugUtil.debug(">参数length=" + Integer.valueOf(length));
			while ((cha = is.read(bytes)) != -1) {
				//
				baos.write(bytes, 0, cha);
			}
			// baos.flush();
			return baos.toByteArray();
		}
		// catch (IOException ex) {
		// throw ex;
		// }
		finally {
			CloseUtil.close(is);
			CloseUtil.close(baos);
		}
	}

}