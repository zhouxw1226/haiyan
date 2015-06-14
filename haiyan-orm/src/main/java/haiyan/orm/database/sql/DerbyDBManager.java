/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.orm.intf.database.sql.ITableSQLRender;

import java.sql.SQLException;

/**
 * Derby
 * 
 * @author zhouxw
 */
class DerbyDBManager extends SQLTableDBManager {

	/**
	 * @param conn
	 * @param notSameConn
	 */
	DerbyDBManager(ISQLDatabase db) {
		super(db);
	}
	@Override
	public ITableSQLRender getSQLRender() {
		if (sqlRender == null)
			sqlRender = new SQLRender();
		return sqlRender;
	}
	@Override
	public String getValidateTablesSQL(String[] tableNames) {
		return null;
	}
	@Override
	public String getValidateTablesSQL(String tableNames) {
		return null;
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
			return "varchar(" + len + ")";// varchar(50) nvarchar(50)
		} else if (fldType == AbstractCommonFieldJavaTypeType.BLOB) {
			long len = field.getLength() <= 0 ? 255 : field.getLength();
			return "varchar(" + len + ")";// varchar(255) nvarchar(255)
		} else if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
			long len = field.getLength() <= 0 ? 18 : field.getLength();
			if (field.hasMinFractionDigit())
				return "decimal(" + len + "," + field.getMinFractionDigit() + ")";
			else if (field.hasMaxFractionDigit())
				return "decimal(" + len + "," + field.getMaxFractionDigit() + ")";
			return "decimal";
		} else if (fldType == AbstractCommonFieldJavaTypeType.INTEGER) {
			return "decimal";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DATE) {
			return "date";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DBBLOB) {
			return "blob";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DBCLOB) {
			return "text";
		} else {
			throw new Warning("Unkown AbstractCommonFieldJavaTypeType=" + fldType);
		}
	}
	@Override
	public String genFieldSQL(AbstractField field) {
		AbstractCommonFieldJavaTypeType fldType = field.getJavaType();
		String generateSQL = this.genFieldName(field) + " " + this.genTypeSQL(field);
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
				} else if (fldType == AbstractCommonFieldJavaTypeType.INTEGER) {
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
		if (gsCurTime.indexOf("-") < 0 && gsCurTime.length() >= 8)
			gsCurTime = gsCurTime.substring(0, 4) + "-"
					+ gsCurTime.substring(4, 6) + "-"
					+ gsCurTime.substring(6, 8);
		if (gsCurTime.indexOf(" ") > 0)
			return "TIMESTAMP('" + gsCurTime + "','"
					+ gsCurTime.substring(gsCurTime.indexOf(" ") + 1) + "')";
		else
			return "Date('" + gsCurTime + "')";
	}
	@Override
	public String SQLCurrentTimeStamp(boolean stringType) {
		return stringType ? "VARCHAR(DATE)" : "DATE";
	}
	@Override
	public boolean isDBCorrect(Throwable e) {
		if (e == null)
			return false;
		if (!(e instanceof SQLException))
			return false;
		SQLException ex = (SQLException)e;
		DebugUtil.error(">--< dbm.dbcorrect.errorcode=" + ex.getErrorCode()+",DSN="+getDSN(), ex);
		if (ex.getErrorCode() == -1 || ex.getErrorCode() == 30000)
			return true;
		return false;
	}
	@Override
	protected void beforeCommit() throws Throwable {
		// this.executeUpdate("SHUTDOWN;");
	}

}