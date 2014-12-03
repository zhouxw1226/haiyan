/*
 * Copyright (c) 2001, Aslak Helles�y, BEKK Consulting
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of BEKK Consulting nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package haiyan.database;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Describe what this class does
 * 
 * @author zhouxw
 * @created 21. april 2002
 * @todo-javadoc Write javadocs
 */
public class JDBCDatabase extends SQLDatabase {

	/**
	 * @todo-javadoc Describe the column
	 */
	protected final String _driver;
	/**
	 * @todo-javadoc Describe the column
	 */
	protected final String _url;
	/**
	 * @todo-javadoc Describe the column
	 */
	protected final String _userName;
	/**
	 * @todo-javadoc Describe the column
	 */
	protected final String _password;
	/**
	 * Describe what the StandardDatabase constructor does
	 * 
	 * @param driver
	 *            Describe what the parameter does
	 * @param url
	 *            Describe what the parameter does
	 * @param userName
	 *            Describe what the parameter does
	 * @param password
	 *            Describe what the parameter does
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @todo-javadoc Write javadocs for constructor
	 */
	public JDBCDatabase(String driver, String url, String userName, String password) 
			throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		_driver = driver;
		_url = url;
		_userName = userName;
		_password = password;
		Class.forName(_driver).newInstance();
	}
	@Override
	public Connection getDBConnection() throws Throwable {
		Connection conn = null;
		if (StringUtil.isBlankOrNull(_userName) && StringUtil.isBlankOrNull(_password))
			conn = DriverManager.getConnection(_url);
		else
			conn = DriverManager.getConnection(_url, _userName, _password);
		DebugUtil.debug(">----< dbm.open.connHash." + conn.hashCode()+", thin=JDBCDatabase Driver=" + _driver + ", URL="
				+ _url + ", UserName=" + _userName + ", Password=" + _password);
		return conn;
	}
	@Override
	public String getLabel() {
		return _driver + _url + _userName + _password;
	}
	@Override
	public String getURL() {
		return _url;
	}
	
}