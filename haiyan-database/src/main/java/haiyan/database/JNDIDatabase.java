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

/*
 * Change log
 *
 */
package haiyan.database;

import haiyan.common.CloseUtil;
import haiyan.common.DebugUtil;
import haiyan.common.intf.database.ISOAPDataSource;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Describe what this class does
 * 
 * @author zhouxw
 * 
 * @created 21. april 2002
 * @todo-javadoc Write javadocs
 */
public class JNDIDatabase extends SQLDatabase {

	/**
	 * @todo-javadoc Describe the column
	 */
	private final String _initialContextFactory;
	/**
	 * @todo-javadoc Describe the column
	 */
	private final String _providerURL;
	/**
	 * @todo-javadoc Describe the column
	 */
	private final String _dataSourceJNDIName;
	/**
	 * 
	 */
	private DataSource ds;
	/**
	 * 
	 */
	private ISOAPDataSource ds2;
	/**
	 * Describe what the JNDIDatabase constructor does
	 * 
	 * @param initialContextFactory
	 *            Describe what the parameter does
	 * @param providerURL
	 *            Describe what the parameter does
	 * @param dataSourceJNDIName
	 *            Describe what the parameter does
	 * @throws Throwable
	 * @todo-javadoc Write javadocs for constructor
	 * @todo-javadoc Write javadocs for method parameter
	 * @todo-javadoc Write javadocs for method parameter
	 * @todo-javadoc Write javadocs for method parameter
	 */
	public JNDIDatabase(String initialContextFactory, String providerURL, String dataSourceJNDIName) throws Throwable {
		if (dataSourceJNDIName == null) {
			throw new IllegalArgumentException("dataSourceJNDIName can't be null");
		}
		_initialContextFactory = initialContextFactory;
		_providerURL = providerURL;
		_dataSourceJNDIName = dataSourceJNDIName;
		Context initContext = null;
		try {
			initContext = new InitialContext();
			ds = (DataSource) initContext.lookup(_dataSourceJNDIName);
		} finally {
			CloseUtil.close(initContext);
		}
	}
	/**
	 * @param dataSourceJNDIName
	 * @throws Throwable
	 */
	public JNDIDatabase(String dataSourceJNDIName) throws Throwable {
		if (dataSourceJNDIName == null) {
			throw new IllegalArgumentException("dataSourceJNDIName can't be null");
		}
		_initialContextFactory = null;
		_providerURL = null;
		_dataSourceJNDIName = dataSourceJNDIName;
		Context initContext = null;
		try {
//			Hashtable<String, String> ht = new Hashtable<String, String>();
//			//ht.put(Context.INITIAL_CONTEXT_FACTORY, _initialContextFactory);
//			//ht.put(Context.PROVIDER_URL, _providerURL);
//			initContext = new InitialContext(ht);
			initContext = new InitialContext();
//			Context envctx = (Context)initContext.lookup("java:comp/env") ;  
//          Object obj = envctx.lookup(_dataSourceJNDIName); 
			Object obj = initContext.lookup(_dataSourceJNDIName);
			if (obj instanceof DataSource)
				ds = (DataSource) obj;
			else if (obj instanceof ISOAPDataSource)
				ds2 = (ISOAPDataSource) obj;
		} finally {
			CloseUtil.close(initContext);
		}
	}
	@Override
	public String getURL() throws Throwable {
		if (ds2!=null)
			return ds2.getURL();
		else if (ds!=null)
			return _providerURL;
		return null;
	}
	@Override
	public Connection getDBConnection() throws Throwable {
		if (ds2!=null) {
			DebugUtil.debug(">thin=RefDatabase, url=" + ds2.getURL());
			return null;
		} else {
			Connection conn = ds.getConnection();
			DebugUtil.debug(">thin=JNDIDatabase, jndi=" + _dataSourceJNDIName+", Connection established." + conn.hashCode());
			return conn;
		}
	}
	@Override
	public String getLabel() {
		return _initialContextFactory + _providerURL + _dataSourceJNDIName;
	}
	
}