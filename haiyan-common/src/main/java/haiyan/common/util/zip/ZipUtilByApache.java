package haiyan.common.util.zip;

import haiyan.common.CloseUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class ZipUtilByApache extends ZipUtil {
	
	public static void unzipFileByApache(InputStream is, String unzipPath) throws Throwable {
    	unzipFileByApache(is, unzipPath, true);
    }
	/**
	 * 解压缩
	 * 
	 * @param warPath
	 *            包地址
	 * @param unzipPath
	 *            解压后地址
	 * @throws Throwable 
	 */
	public static void unzipFileByApache(InputStream is, String unzipPath, boolean deep) throws Throwable {
		ArchiveInputStream in = null;
		try {
			// 获得输出流
			BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
			in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, bufferedInputStream);
			ZipArchiveEntry entry = null;
			// 循环遍历解压
			while ((entry = (ZipArchiveEntry) in.getNextEntry()) != null) {
				if (deep && entry.isDirectory()) {
					new File(unzipPath, entry.getName()).mkdir();
				} else {
					String fileName = entry.getName();
					if (!deep) {
						fileName = new File(fileName).getName();
					}
					OutputStream out = FileUtils.openOutputStream(new File(unzipPath, fileName));
					IOUtils.copy(in, out);
					out.close();
				}
			}
			in.close();
		}
		// catch (FileNotFoundException e) {
		// System.err.println("未找到war文件");
		// } catch (ArchiveException e) {
		// System.err.println("不支持的压缩格式");
		// } catch (IOException e) {
		// System.err.println("文件写入发生错误");
		// }
		finally{
			CloseUtil.close(in);
		}
	}

    public static void unzipFileByApache(File zipFile, String unzipPath) throws Throwable {
    	unzipFileByApache(zipFile, unzipPath, true);
    }
	/**
	 * 解压缩
	 * 
	 * @param warPath
	 *            包地址
	 * @param unzipPath
	 *            解压后地址
	 * @throws Throwable 
	 */
	public static void unzipFileByApache(File zipFile, String unzipPath, boolean deep) throws Throwable {
		ArchiveInputStream in = null;
		try {
			// 获得输出流
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					new FileInputStream(zipFile));
			in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, bufferedInputStream);
			ZipArchiveEntry entry = null;
			// 循环遍历解压
			while ((entry = (ZipArchiveEntry) in.getNextEntry()) != null) {
				if (deep && entry.isDirectory()) {
					new File(unzipPath, entry.getName()).mkdir();
				} else {
					String fileName = entry.getName();
					if (!deep) {
						fileName = new File(fileName).getName();
					}
					OutputStream out = FileUtils.openOutputStream(new File(unzipPath, fileName));
					IOUtils.copy(in, out);
					out.close();
				}
			}
			in.close();
		}
		// catch (FileNotFoundException e) {
		// System.err.println("未找到war文件");
		// } catch (ArchiveException e) {
		// System.err.println("不支持的压缩格式");
		// } catch (IOException e) {
		// System.err.println("文件写入发生错误");
		// }
		finally{
			CloseUtil.close(in);
		}
	}

	/**
	 * 压缩
	 * 
	 * @param destFile
	 *            创建的地址及名称
	 * @param zipDir
	 *            要打包的目录
	 * @throws Throwable 
	 */
	public static void zipFileByApache(File outFile, String zipDir) throws Throwable {
		ArchiveOutputStream out = null;
		try {
			outFile.createNewFile();
			// 创建文件
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
			out = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, bufferedOutputStream);
			if (zipDir.charAt(zipDir.length() - 1) != '/') {
				zipDir += '/';
			}
			Iterator<File> files = FileUtils.iterateFiles(new File(zipDir), null, true);
			while (files.hasNext()) {
				File file = files.next();
				ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file,
						file.getPath().replace(zipDir.replace("/", "\\"), ""));
				out.putArchiveEntry(zipArchiveEntry);
				IOUtils.copy(new FileInputStream(file), out);
				out.closeArchiveEntry();
			}
			out.finish();
		}
		// catch (FileNotFoundException e) {
		// System.err.println("未找到war文件");
		// } catch (ArchiveException e) {
		// System.err.println("不支持的压缩格式");
		// } catch (IOException e) {
		// System.err.println("文件写入发生错误");
		// }
		finally {
			CloseUtil.close(out);
		}
	}

}
