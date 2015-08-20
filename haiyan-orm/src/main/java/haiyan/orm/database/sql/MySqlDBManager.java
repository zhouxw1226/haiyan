/*
 * Created on 2004-12-31
 */
package haiyan.orm.database.sql;

import haiyan.common.CloseUtil;
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
import haiyan.config.util.ConfigUtil;
import haiyan.exp.ExpUtil;
import haiyan.orm.intf.database.sql.ITableSQLRender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * MYSQL
 * 
 * @author zhouxw
 */
public class MySqlDBManager extends SQLTableDBManager {
    /**
     * @param conn
     */
    protected MySqlDBManager(ISQLDatabase db) {
        super(db);
    }
    @Override
    public ITableSQLRender getSQLRender() {
        if (sqlRender == null)
        	sqlRender = new MySqlSQLRender();
        return sqlRender;
    }
    @Override
    public String getValidateTablesSQL(String[] tableNames) {
        // DROP TABLE IF EXISTS table_name;
        // String sql = "";
        // for (String tableName : tableNames) {
        // if (sql.length() > 0)
        // sql += " or ";
        // sql += " like '" + tableName + "'";
        // }
        // sql = "SHOW TABLES" + sql;
        // return sql;
        return null; // use metatable
    }
    @Override
    public String getValidateTablesSQL(String tableName) {
        return "SHOW TABLES like '" + tableName + "'";
    }
    // protected void pValidateTables(final Ref<String[]> asTable,
    // final Ref<String[]> asExistTbl) throws Throwable {
    // //
    // final ArrayList<String> existList = new ArrayList<String>();
    // final ArrayList<String> notExistList = new ArrayList<String>();
    // // String sql=getSQLRender().getValidateTablesSQL(asTable.refValue);
    // for (String tName : asTable.refValue) {
    // tName = tName.toUpperCase();
    // // String sql = ;
    // DBHelper.getResultStrArray(getValidateTablesSQL(tName), 1, this
    // .getConnection(), new DBResultListener() {
    // //
    // public void whileNext(ResultSet rst) throws Throwable {
    // String trName = rst.getString(1);
    // // if (tName.equalsIgnoreCase(rst.getString(1)))
    // existList.add(trName.toUpperCase());
    // }
    // });
    // }
    // for (int i = 0; i < asTable.refValue.length; i++) {
    // String srcName = asTable.refValue[i];
    // srcName = srcName.toUpperCase();
    // if (!existList.contains(srcName.toUpperCase()))
    // notExistList.add(srcName.toUpperCase());
    // }
    // // DebugUtil.debug(notExistList.size());
    // asTable.refValue = (String[]) notExistList.toArray(new String[0]);
    // asExistTbl.refValue = (String[]) existList.toArray(new String[0]);
    // // asTable.setValue(notExistList.toArray(new String[0]));
    // // asExistTbl.setValue(existList.toArray(new String[0]));
    // }
    @Override
    public boolean isExistTable(String tableNames) throws Throwable {
        //
        String[] tNames = StringUtil.split(tableNames, ",");
        for (String tName : tNames) {
            // String sql=getSQLRender().getValidateTablesSQL(tableNames);
            String sql = getValidateTablesSQL(tName.toUpperCase());
            //
            ResultSet rst = null;
            Statement stat = null;
            try {
                DebugUtil.debug(">isExistTable().SQL=" + sql);
                stat = this.getConnection().createStatement();
                rst = stat.executeQuery(sql);
                //
                while (rst.next()) {
                    if (tName.equalsIgnoreCase(rst.getString(1)))
                        return true;
                }
            }
            // catch (Throwable ex) {
            // throw ex;
            // }
            finally {
            	CloseUtil.close(rst);
            	CloseUtil.close(stat);
            }
        }
        return false;
    }
    @Override
    public boolean isDBCorrect(Throwable e) {
		if (e == null)
			return false;
		if (!(e instanceof SQLException))
			return false;
		SQLException ex = (SQLException)e;
        DebugUtil.error(">--< dbm.dbcorrect.errorcode=" + ex.getErrorCode()+",DSN="+getDSN(), ex);
        if (ex.getErrorCode() == 1146 || ex.getErrorCode() == 1054)
            return true;
        return false;
    }
    @Override
    public String getCreateTableSQL(Table oSerTable) {
        //
        ArrayList<String> sqls = new ArrayList<String>();
        ArrayList<String> sKeySQL = new ArrayList<String>();
        // int count = 0;
        // alter table users add CONSTRAINT PRI_USER primary key(USERID);
        String tableName = ConfigUtil.getRealTableName(oSerTable);
        // tableName = "`" + tableName + "`";
        sKeySQL.add("alter table `" + tableName + "` add constraint `PK_"
                + tableName + "` primary key (`" + oSerTable.getId().getName()
                + "`)");
        String sFieldCreateSQL = this.genFieldSQL(oSerTable.getId()) + ",";
        for (int i = 0; i < oSerTable.getFieldCount(); i++) {
            Field field = oSerTable.getField(i);
            if (isIgnoredField(field))
                continue;
            sFieldCreateSQL += this.genFieldSQL(field) + ",";
            // //
            // if (!field.getLazyLoad()
            // && field.getCommon().getReferenceTable() != null
            // && !field.getCommon().getMultipleSelect()) {
            // Table refTable = ConfigUtil.getTable(field.getCommon()
            // .getReferenceTable());
            // String refTableName = ConfigUtil.getRealTableName(refTable);
            // String refFieldName = field.getCommon().getReferenceField();
            // refFieldName = refFieldName == null ? refTable.getId()
            // .getName() : refFieldName;
            // // alter table dbo.mybbs add constraint FK_mybbs_author
            // // foreign key (authorId)
            // // references dbo.author([id])
            // sKeySQL.add("alter table `" + tableName
            // + "` add constraint `FK" + (count++) + "_" + tableName
            // + "` foreign key (`" + field.getName()
            // + "`) references `" + refTableName + "` (`"
            // + refFieldName + "`) ");
            // // ON UPDATE CASCADE ON DELETE CASCADE
            // }
        }
        //
        // SET character_set_client='gbk'
        // SET character_set_connection='gbk'
        // SET character_set_results='gbk'
        sqls.add("CREATE TABLE `"
                + tableName
                + "` ("
                + StringUtil
                        .left(sFieldCreateSQL, sFieldCreateSQL.length() - 1)
                + ") ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_bin "); // gbk
        // gbk_chinese_ci
        // ENGINE=InnoDB DEFAULT CHARSET=gb2312
        sqls.addAll(sKeySQL);
        String[] strArray = (String[]) (sqls.toArray(new String[0]));
        return StringUtil.join(strArray, DataConstant.SQL_DIM, "");
    }
    @Override
    public String genFieldSQL(AbstractField field) {
        AbstractCommonFieldJavaTypeType fldType = field.getJavaType();
        String generateSQL = this.genFieldName(field) + " "
                + this.genTypeSQL(field);
        if (field instanceof Id)
            generateSQL += " not null";
        else {
            // Field field = (Field) fld;
            String defValue = field.getDefaultValue();
            if (defValue != null) {
                if (!ExpUtil.isFormula(defValue))
                    if (fldType == AbstractCommonFieldJavaTypeType.STRING
                            || fldType == AbstractCommonFieldJavaTypeType.DATE) {
                        generateSQL += " default '" + defValue + "'";
                    } else {
                        if (StringUtil.isBlankOrNull(defValue)) {
                            // generateSQL += " default null ";
                        } else {
                            generateSQL += " default " + defValue;
                        }
                    }
            } else {
                if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
                    generateSQL += " default '-1'";
                } else if (fldType == AbstractCommonFieldJavaTypeType.INTEGER) {
                    generateSQL += " default '-1'";
                } else {
                    // generateSQL += " default ``";
                    // generateSQL += " default null ";
                }
            }
            if (!field.getNullAllowed())
                generateSQL += " not null";
        }
        return generateSQL;
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
            long len = field.getLength() <= 0 ? 32767 : field.getLength();
            return "text(" + len + ")";
        } else if (fldType == AbstractCommonFieldJavaTypeType.BIGDECIMAL) {
            long len = field.getLength() <= 0 ? 18 : field.getLength();
            if (field.hasMinFractionDigit())
                return "decimal(" + len + "," + field.getMinFractionDigit() + ")";
            else if (field.hasMaxFractionDigit())
                return "decimal(" + len + "," + field.getMaxFractionDigit() + ")";
            return "decimal";
        } else if (fldType == AbstractCommonFieldJavaTypeType.INTEGER) { 
        	return "integer";
        } else if (fldType == AbstractCommonFieldJavaTypeType.DATE) {
            return "datetime";
        } else if (fldType == AbstractCommonFieldJavaTypeType.DBBLOB) {
            return "blob";
        } else if (fldType == AbstractCommonFieldJavaTypeType.DBCLOB) {
			return "text";
		} else {
            throw new Warning("Unknown EBillFieldType:" + fldType);
        }
    }
    @Override
    public String genFieldName(AbstractField field) {
        return "`" + field.getName() + "`";
    }
    @Override
    public String SQLCurrentTimeStamp(boolean stringType) {
        return stringType ? "CONVERT(VARCHAR(23),NOW(),21)" : "NOW()";
    }
    @Override
    public String SQLDateTimeFromStr(String gsCurTime) {
        //
        if (gsCurTime.length() > 10)
            return "DATE_FORMAT('" + gsCurTime + "', '%Y-%m-%d %H:%i:%s')";
        // return "CONVERT(DATETIME,'" + gsCurTime + "',21)";
        else
            return "DATE_FORMAT('" + gsCurTime + "', '%Y-%m-%d')";
        // return "CONVERT(DATETIME,'" + gsCurTime + "',21)";
    }
//	@Override
//	public IDBRecord select(ITableDBContext context, Table table, String id)
//			throws Throwable {
//		return select(context, table, id,IDBRecordCacheManager.CONTEXT_SESSION);
//	}
//	@Override
//	protected IDBRecord select(ITableDBContext context, Table table, String id,
//			short type, int... args) throws Throwable {
//		IDBRecord record = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ps = getSQLRender().getSelectPreparedStatement(context, table, id);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				record = getRecordByRow(context, table, rs, type);
//				if (record == null)
//					return null;
//			} else {
//				return null;
//			}
//			// SQLMappingTableManager.queryMappingTable(context, table, form, id, this);
//			// SQLOne2OneTableManager.queryOne2OneTable(context, table, result, id, this);
//			return record;
//		} catch (SQLException ex) {
//			if (isDBCorrect(ex)) {
//				this.tableErrHandle(getSQLRender().getSQL());
//				if (isDeep(args))
//					return select(context, table, id, type, getDeep(args));
//			}
//			throw ex;
//		} finally {
//			CloseUtil.close(rs);
//			CloseUtil.close(ps);
//		}
//	}

}