/*
 // $Id: //open/mondrian/src/main/mondrian/olap/ConnectionBase.java#19 $
 // This software is subject to the terms of the Common Public License
 // Agreement, available at the following URL:
 // http://www.opensource.org/licenses/cpl.html.
 // Copyright (C) 2001-2002 Kana Software, Inc.
 // Copyright (C) 2001-2007 Julian Hyde and others
 // All Rights Reserved.
 // You must accept the terms of that agreement to use this software.
 //
 // jhyde, 6 August, 2001
 */

package mondrian.olap;

import mondrian.resource.MondrianResource;

import org.apache.log4j.Logger;

/**
 * <code>ConnectionBase</code> implements some of the methods in
 * {@link Connection}.
 * 
 * @author jhyde
 * @since 6 August, 2001
 * @version $Id: //open/mondrian/src/main/mondrian/olap/ConnectionBase.java#19 $
 */
public abstract class ConnectionBase implements Connection {

	public static void memoryUsageNotification(Query query, String msg) {
		query.setOutOfMemory(msg);
	}

	protected ConnectionBase() {
	}

	protected abstract Logger getLogger();

	public String getFullConnectString() {
		String s = getConnectString();
		String catalogName = getCatalogName();
		if (catalogName != null) {
			int len = s.length() + catalogName.length() + 32;
			StringBuilder buf = new StringBuilder(len);
			buf.append(s);
			if (!s.endsWith(";")) {
				buf.append(';');
			}
			buf.append("Initial Catalog=");
			buf.append(catalogName);
			buf.append(';');
			s = buf.toString();
		}
		return s;
	}

	public Query parseQuery(String s) {
		return parseQuery(s, false);
	}

	public Query parseQuery(String s, boolean load) {
		boolean debug = false;
		if (getLogger().isDebugEnabled()) {
			// debug = true;
			StringBuilder buf = new StringBuilder(256);
			buf.append(Util.nl);
			buf.append(s);
			getLogger().debug(buf.toString());
		}
		try {
			// zhouxw 20080326
			System.out.println(s);
			Parser parser = new Parser();
			final FunTable funTable = getSchema().getFunTable();
			Query q = parser.parseInternal(this, s, debug, funTable, load);
			return q;
		} catch (Throwable e) {
			throw MondrianResource.instance().FailedToParseQuery.ex(s, e);
		}
	}

	public Exp parseExpression(String s) {
		boolean debug = false;
		if (getLogger().isDebugEnabled()) {
			// debug = true;
			StringBuilder buf = new StringBuilder(256);
			buf.append(Util.nl);
			buf.append(s);
			getLogger().debug(buf.toString());
		}
		try {
			Parser parser = new Parser();
			final FunTable funTable = getSchema().getFunTable();
			Exp q = parser.parseExpression(this, s, debug, funTable);
			return q;
		} catch (Throwable e) {
			throw MondrianResource.instance().FailedToParseQuery.ex(s, e);
		}
	}
}

// End ConnectionBase.java
