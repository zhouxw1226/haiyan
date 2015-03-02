package haiyan.common.util.zip;

import haiyan.common.CloseUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 单个文件压缩和普通zip操作
 * 
 * @author zhouxw
 */
public class ZipUtil {
	private static int MAX_BUF_SIZE = 1024;
	/**
	 * @param data
	 * @param off
	 * @param len
	 * @return
	 * @throws Throwable
	 */
	public static byte[] zip(byte[] data, int off, int len) throws Throwable {
		// Decompress the bytes
		byte[] buf = new byte[MAX_BUF_SIZE];
		Deflater compresser = new Deflater();
		while (compresser.needsInput())
			compresser.setInput(data, off, len);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// DeflaterOutputStream os = new DeflaterOutputStream(bos, compresser);
		int size = 0;
		while (!compresser.finished()) {
			int count = compresser.deflate(buf);
			if (count == 0)
				break;
			bos.write(buf, size, count);
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
	public static byte[] zip(byte[] data) throws Throwable {
		return zip(data, 0, data.length);
	}
	/**
	 * @param data
	 * @param off
	 * @param len
	 * @return
	 * @throws Throwable
	 */
	public static byte[] unzip(byte[] data, int off, int len) throws Throwable {
		// Decompress the bytes
		byte[] buf = new byte[MAX_BUF_SIZE];
		Inflater decompressor = new Inflater();
		while (decompressor.needsInput())
			decompressor.setInput(data, off, len);
		// decompressor.end();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
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
	public static byte[] unzip(byte[] data) throws Throwable {
		return unzip(data, 0, data.length);
	}
	public static int BUFF_SIZE = 512; // size of bytes
	public static void zipFile(File[] files, File zipFile) throws Throwable {//zipDirectoryPath:需要压缩的文件夹名 
		zipFile(files, zipFile, true);
	}
    //压缩指定的文件 (无依赖jar)
    public static void zipFile(File[] files, File zipFile, boolean deep) throws Throwable {//zipDirectoryPath:需要压缩的文件夹名 
    	ZipOutputStream zipOut = null;
        try{ 
        	zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile))); 
            handleDir(files , zipOut, deep); 
        }finally{
        	CloseUtil.close(zipOut);
        }
    }  
    //压缩文件夹内的文件 (无依赖jar)
    public static void zipFile(File zipDir) throws Throwable {//zipDirectoryPath:需要压缩的文件夹名 
    	ZipOutputStream zipOut = null;
        try{ 
            zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipDir))); 
            handleDir(zipDir , zipOut, true); 
        }finally{
        	CloseUtil.close(zipOut);
        }
    } 
    //由doZip调用,递归完成目录文件读取 (无依赖jar)
    private static void handleDir(File[] files, ZipOutputStream zipOut, boolean deep) throws Throwable { 
    	byte[] buf = new byte[BUFF_SIZE];
        FileInputStream fileIn; 
        for(File file : files){ 
            if(file.isDirectory()){ 
                handleDir(file , zipOut, deep); 
            } 
            else{ 
                fileIn = new FileInputStream(file); 
                String fileName = deep?file.toString():file.getName();
                zipOut.putNextEntry(new ZipEntry(fileName));
                int r = -1;
                while((r=fileIn.read(buf))>0){ 
                    zipOut.write(buf,0,r); 
                } 
                zipOut.closeEntry(); 
            } 
        } 
    } 
    //由doZip调用,递归完成目录文件读取 (无依赖jar)
    private static void handleDir(File dir, ZipOutputStream zipOut, boolean deep) throws Throwable {
        File[] files; 
        files = dir.listFiles(); 
        if(files.length == 0){//如果目录为空,则单独创建之. 
            //ZipEntry的isDirectory()方法中,目录以"/"结尾. 
            //String fileName = deep?file.toString():file.getName();
            zipOut.putNextEntry(new ZipEntry(dir.toString() + File.separator)); 
            zipOut.closeEntry(); 
        } 
        else{//如果目录不为空,则分别处理目录和文件. 
        	handleDir(files, zipOut, deep);
        } 
    } 
//    //解压指定zip文件 (无依赖jar)
//    public static void unzipFile(File unZipFile, File unZipDir) throws Throwable {//unZipfileName需要解压的zip文件名 
//    	byte[] buf = new byte[BUFF_SIZE];
//        ZipFile zipFile = new ZipFile(unZipFile);
//    	ZipInputStream zipIn = null;
//        try {
//            zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipFile)));
//        	ZipEntry zipEntry;
//	        while((zipEntry = zipIn.getNextEntry()) != null){ 
//	        	String fileName = new File(zipEntry.getName()).getName();
//	            File file = new File(unZipDir + File.separator + fileName); 
//	            if (zipEntry.isDirectory()){ 
//	                file.mkdirs(); 
//	            }
//	            else{ 
//	                //如果指定文件的目录不存在,则创建之. 
//	                File parent = file.getParentFile(); 
//	                if(!parent.exists()){ 
//	                    parent.mkdirs(); 
//	                }
//	                OutputStream out = null; 
//	                InputStream is = null;
//	                try {
//	                	is = new BufferedInputStream(zipFile.getInputStream(zipEntry));
//	                	out = new FileOutputStream(file);
//		                int r = -1;
//		                while((r=is.read(buf))>0){ 
//		                	out.write(buf,0,r); 
//		                } 
//	                }finally{
//	                	CloseUtil.close(is);
//	                	CloseUtil.close(out);
//	                }
//	            }     
//	        } 
//        }finally{
//        	if (zipIn!=null)
//	        	try {
//	        		zipIn.closeEntry(); 
//	        	}catch(Throwable e){
//	        		e.printStackTrace();
//	        	}
//        	CloseUtil.close(zipFile);
//        	CloseUtil.close(zipIn);
//        }
//    } 

}
