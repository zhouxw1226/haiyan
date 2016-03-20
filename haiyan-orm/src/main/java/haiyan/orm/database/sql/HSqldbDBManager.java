/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import haiyan.common.DebugUtil;
import haiyan.common.StringUtil;
import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.sql.ISQLDatabase;
import haiyan.config.castorgen.AbstractField;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Id;
import haiyan.config.castorgen.Table;
import haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType;
import haiyan.orm.intf.database.sql.ITableSQLRender;

/**
 * HSqldb
 * 
 * @author zhouxw
 */
class HSqldbDBManager extends SQLTableDBManager {

	/**
	 * @param conn
	 * @param notSameConn
	 */
	HSqldbDBManager(ISQLDatabase db) {
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
	public String getCreateTableSQL(Table oSerTable) {
		ArrayList<String> sqls = new ArrayList<String>();
		ArrayList<String> sKeySQL = new ArrayList<String>();
		// int count = 0;
		// alter table users add CONSTRAINT PRI_USER primary key(USERID);
		String tableName = getDBName(oSerTable);
		if (oSerTable.getId() == null)
			throw new Warning("table'pk lost.");
		sKeySQL.add("alter table " + tableName + " add constraint PK_"
				+ tableName + " primary key (" + oSerTable.getId().getName()
				+ ");"); // hsql要封号
		String sCreateSQL = this.genFieldSQL(oSerTable.getId()) + ",";
		for (int i = 0; i < oSerTable.getFieldCount(); i++) {
			Field field = oSerTable.getField(i);
			if (isIgnoredField(field))
				continue;
			sCreateSQL += this.genFieldSQL(field) + ",";
			// //
			// if (field.getCommon().getReferenceTable() != null
			// && !field.getLazyLoad()) {
			// Table refTable = ConfigUtil.getTable(field.getCommon().getReferenceTable());
			// String refTableName = ConfigUtil.getRealTableName(refTable);
			// String refFieldName = field.getCommon().getReferenceField();
			// refFieldName = refFieldName == null ? refTable.getId().getName() : refFieldName;
			// // alter table dbo.mybbs add constraint FK_mybbs_author
			// // foreign key (authorId)
			// // references dbo.author([id])
			// sKeySQL.add("alter table " + tableName + " add constraint FK"
			// + (count++) + "_" + tableName + " foreign key (" + field.get Name() + ") references " + refTableName
			// + " ([" + refFieldName + "]) ");
			// // ON UPDATE CASCADE ON DELETE CASCADE
			// }
		}
		// sqls.add("DROP TABLE " + tableName + " IF EXISTS;");
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
			return "OTHER";
		} else if (fldType == AbstractCommonFieldJavaTypeType.DBCLOB) {
			return "OTHER";
		} else {
			throw new Warning("Unkown EBillFieldType=" + fldType);
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
				if (fldType == AbstractCommonFieldJavaTypeType.STRING || fldType == AbstractCommonFieldJavaTypeType.DATE) {
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
		// return "CONVERT(NOW(),'" + gsCurTime + "',21)";
		if (gsCurTime.indexOf("-") < 0 && gsCurTime.length() >= 8)
			gsCurTime = gsCurTime.substring(0, 4) + "-"
					+ gsCurTime.substring(4, 6) + "-"
					+ gsCurTime.substring(6, 8);
		return "CONVERT('" + gsCurTime + "',Date)";
	}
	@Override
	public String SQLCurrentTimeStamp(boolean stringType) {
		return stringType ? "CONVERT(VARCHAR(23),NOW(),21)" : "NOW()";
	}
	@Override
	public boolean isDBCorrect(Throwable e) {
		if (e == null)
			return false;
		if (!(e instanceof SQLException))
			return false;
		SQLException ex = (SQLException)e;
		DebugUtil.error(">--< dbm.dbcorrect.errorcode=" + ex.getErrorCode()+",DSN="+getDSN(), ex);
		if (ex.getErrorCode() == 942 || ex.getErrorCode() == 904
				|| ex.getErrorCode() == 207 || ex.getErrorCode() == 208
				|| ex.getErrorCode() == -22 || ex.getErrorCode() == -28)
			return true;
		return false;
	}
	@Override
	protected void beforeCommit() {
		// this.executeUpdate("SHUTDOWN;");
	}

}