/****************************************************************************
 * Created on 06 February 2004, 17:55
 * ZipHandler.java is part of the QuickDownloader App - A Download Manager
 * Copyright (c) 2003 - 2004 Asif Akhtar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 ******************************************************************************/
package haiyan.common.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Asif Akhtar
 * @version 5.0
 */
public class ZipHandler {

	private String destURL = "";
	private String sourceURL = "";
	private ZipEntry zipEntry;
	private BufferedInputStream bufferIn;
	private FileOutputStream fileOut;
	private ZipOutputStream zipOut;
	private FileInputStream fileIn;
	private String temp = "";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new ZipHandler("D:/desktop.ini", "D:/desktop.ini.zip").start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * The constructor used to build the class
	 * 
	 * @param location
	 *            The location of where to saved the zipped up file
	 * @param urlName
	 *            Name of the url to be zipped up
	 * @throws Throwable {
	 */
	ZipHandler(String sourceURL, String destURL) throws Throwable {
		try {
			this.bufferIn = null;
			this.sourceURL = sourceURL;
			this.destURL = destURL;
			System.out.println("sourceURL=" + sourceURL);
			System.out.println("destURL=" + destURL);
		} catch (Throwable ex) {
			throw ex;
		}
	}
	/**
	 * The Main method which is called that actually zips up the download and
	 * removes the original File
	 * 
	 * @return The success of the Zipup
	 * @throws Throwable {
	 */
	boolean start() throws Throwable {
		try {
			if (!new File(this.sourceURL).exists()) {
				return false;
			} else {
				this.fileOut = new FileOutputStream(this.destURL);
				this.zipOut = new ZipOutputStream(new BufferedOutputStream(this.fileOut));
				byte abyte0[] = new byte[4048];
				if (this.sourceURL != null) {
					this.fileIn = new FileInputStream(this.sourceURL);
					this.bufferIn = new BufferedInputStream(this.fileIn, 4048);
					this.zipEntry = new ZipEntry(temp);
					this.zipOut.putNextEntry(zipEntry);
					int i = -1;
					while ((i = this.bufferIn.read(abyte0)) != -1) {
						this.zipOut.write(abyte0, 0, i);
					}
					this.zipOut.flush();
					if (new File(this.destURL).exists()) {
						return true;
					} else
						return false;
				} else
					return false;
			}
		} catch (Throwable ex) {
			throw ex;
		} finally {
			if (bufferIn != null)
				bufferIn.close();
			if (zipOut != null)
				zipOut.close();
			if (zipEntry != null)
				zipEntry = null;
		}
	}
	/**
	 * The Name of the Zip file is created and returned
	 * 
	 * @return The name of the Zip up file
	 */
	String getZipFileName() {
		return this.destURL;
	}
}