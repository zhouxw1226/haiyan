/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import haiyan.common.StringUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.config.intf.database.sql.ITableSQLRender;
import haiyan.config.util.ConfigUtil;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQLServer
 * 
 * @author zhouxw
 */
public class SqlServerDBManager extends SQLTableDBManager {

	/**
	 * @param conn
	 * @param notSameConn
	 */
	protected SqlServerDBManager(ISQLDatabase db) {
		super(db);
	}
	@Override
	public ITableSQLRender getSQLRender() {
		if (sqlRender == null)
			sqlRender = new SqlServerSQLRender();
		return sqlRender;
	}
	@Override
	public String getValidateTablesSQL(String[] tableNames) {
		String filter = "";
		int i = 0;
		for (; i < tableNames.length; i++) {
			if (i != 0)
				filter += " or ";
			filter += " name='" + tableNames[i] + "' ";
		}
		if (i > 0)
			filter = " AND (" + filter + ")";
		return "SELECT name FROM sysobjects WHERE xtype='U' " + filter;
		// AS TNAME
	}
	@Override
	public String getValidateTablesSQL(String tableNames) {
		String tableSql = StringUtil.replaceAll(tableNames, ",", "','");
		return "SELECT name FROM sysobjects WHERE xtype='U' AND name IN ('"
				+ tableSql + "')";
	}
	@Override
	public String getCreateTableSQL(Table oSerTable) {
		ArrayList<String> sqls = new ArrayList<String>();
		ArrayList<String> sKeySQL = new ArrayList<String>();
		// int count = 0;
		// alter table users add CONSTRAINT PRI_USER primary key(USERID);
		String tableName = ConfigUtil.getRealTableName(oSerTable);
		if (oSerTable.getId() == null)
			throw new Warning("table'pk lost.");
		sKeySQL.add("alter table " + tableName + " add constraint PK_"
				+ tableName + " primary key (" + oSerTable.getId().getName()
				+ ")");
		String sCreateSQL = this.genFieldSQL(oSerTable.getId()) + ",";
		for (int i = 0; i < oSerTable.getFieldCount(); i++) {
			Field field = oSerTable.getField(i);
			if (isIgnoredField(field))
				continue;
			sCreateSQL += this.genFieldSQL(field) + ",";
			// if (field.getCommon().getReferenceTable() != null && !field.getLazyLoad()) {
			// Table refTable = ConfigUtil.getTable(field.getCommon().getReferenceTable());
			// String refTableName = ConfigUtil.getRealTableName(refTable);
			// String refFieldName = field.getCommon().getReferenceField();
			// refFieldName = refFieldName == null ? refTable.getId().getName() : refFieldName;
			// // alter table dbo.mybbs add constraint FK_mybbs_author
			// // foreign key (authorId)
			// // references dbo.author([id])
			// // TODO
			// sKeySQL.add("alter table " + tableName + " add constraint FK"
			// + (count++) + "_" + tableName + " foreign key (" + field.getName() + ") references " + refTableName
			// + " ([" + refFieldName + "]) ");
			// // ON UPDATE CASCADE ON DELETE CASCADE
			// }
		}
		sqls.add("CREATE TABLE " + tableName + "("
				+ StringUtil.left(sCreateSQL, sCreateSQL.length() - 1) + ")");
		sqls.addAll(sKeySQL);
		String[] strArray = (String[]) (sqls.toArray(new String[0]));
		return StringUtil.join(strArray, DataConstant.SQL_DIM, "");
	}
	@Override
	public String genTypeSQL(AbstractField field) {
		AbstractCommonFieldJavaTypeType fldType = field.getJavaType();
		if (fldType == AbstractCommonFieldJavaTypeType.STRING) {
			long len = field.getLength() <= 0 ? 50 : field.getLength();
            if (len <= 100
                && (field.getJavaType()  == AbstractCommonFieldJavaTypeType.BLOB 
                || field.getJavaType() == AbstractCommonFieldJavaTypeType.DBBLOB))
                len = 255;
            return "nvarchar(" + len + ")";// varchar(255) nvarchar(255)
		} else if (fldType == AbstractCommonFieldJavaTypeType.BLOB) {
			long len = field.getLength() <= 0 ? 255 : field.getLength();
			return "nvarchar(" + len + ")";// varchar(255) nvarchar(255)
		} else if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
			long len = field.getLength() <= 0 ? 18 : field.getLength();
			if (field.hasMinFractionDigit())
				return "numeric(" + len + "," + field.getMinFractionDigit() + ")";
			else if (field.hasMaxFractionDigit())
				return "numeric(" + len + "," + field.getMaxFractionDigit() + ")";
			return "numeric";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DATE) {
			return "datetime";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DBBLOB) {
			return "image";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DBCLOB) {
			return "text";
		} else {
			throw new Warning("Unknown AbstractCommonFieldJavaTypeType=" + fldType);
		}
	}
	@Override
	public String genFieldSQL(AbstractField field) {
		AbstractCommonFieldJavaTypeType fldType = field.getJavaType();
		// boolean bDefaultValue = false;
		// DebugUtil.debug(">" + (eDBType.get() == EnumDBType.edbtDB2));
		String generateSQL = this.genFieldName(field) + " "
				+ this.genTypeSQL(field);
		if (field instanceof Id) {
			generateSQL += " not null";
		} else {
			// Field field = (Field) fld;
			if (field.getDefaultValue() != null) {
				if (fldType == AbstractCommonFieldJavaTypeType.STRING
				    || fldType == AbstractCommonFieldJavaTypeType.DATE) {
					generateSQL += " default '" + field.getDefaultValue() + "'";
				} else {
					if (StringUtil.isBlankOrNull(field.getDefaultValue())) {
						// generateSQL = generateSQL;
					} else {
						generateSQL += " default " + field.getDefaultValue();
					}
				}
			} else {
				if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
					generateSQL += " default -1";
				}
			}
			if (!field.getNullAllowed()) {
				if (generateSQL.indexOf(" default ") != -1)
					// generateSQL += " default ''";
					generateSQL += " not null";
			}
		}
		return generateSQL;
	}
	@Override
	public String SQLDateTimeFromStr(String gsCurTime) {
		return "CONVERT(DATETIME,'" + gsCurTime + "',21)";
	}
	@Override
	public String SQLCurrentTimeStamp(boolean stringType) {
		return stringType ? "CONVERT(VARCHAR(23),GETDATE(),21)" : "GETDATE()";
	}
	@Override
	public boolean isDBCorrect(Throwable e) {
		if (e == null)
			return false;
		if (!(e instanceof SQLException))
			return false;
		SQLException ex = (SQLException)e;
		if (ex.getErrorCode() == 942 || ex.getErrorCode() == 904
			|| ex.getErrorCode() == 207 || ex.getErrorCode() == 208)
			return true;
		// Throwable e = Warning.getSourceEx(ex);
		// String mes = e.getMessage();
		// if (mes == null)
		return false;
	}
}