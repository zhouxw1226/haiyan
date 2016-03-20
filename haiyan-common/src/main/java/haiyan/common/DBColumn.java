/*
 * Created on 2004-11-4
 *
 */
package haiyan.common;

/**
 * @author zhouxw
 */
public class DBColumn {

	private String schema;

	private String table;

	private String column;

	private String dbtype;

	private String type;

	private String length;

	private String nullable;

	private String defaultvalue;

	private String defaultlength;

	private String id;

	private String dbnullable;

	private String description;

	private boolean isPK = false;

	private boolean isFK = false;

	private String refTable;

	private String refField;

	/**
	 * @return Returns the isFK.
	 */
	public boolean isFK() {
		return isFK;
	}

	/**
	 * @param isFK
	 *            The isFK to set.
	 */
	public void setFK(boolean isFK) {
		this.isFK = isFK;
	}

	/**
	 * @return Returns the isPK.
	 */
	public boolean isPK() {
		return isPK;
	}

	/**
	 * @param isPK
	 *            The isPK to set.
	 */
	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}

	/**
	 * @return Returns the refField.
	 */
	public String getRefField() {
		return refField;
	}

	/**
	 * @param refField
	 *            The refField to set.
	 */
	public void setRefField(String refField) {
		this.refField = refField;
	}

	/**
	 * @return Returns the refTable.
	 */
	public String getRefTable() {
		return refTable;
	}

	/**
	 * @param refTable
	 *            The refTable to set.
	 */
	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the column.
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            The column to set.
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return Returns the dbnullable.
	 */
	public String getDbnullable() {
		return dbnullable;
	}

	/**
	 * @param dbnullable
	 *            The dbnullable to set.
	 */
	public void setDbnullable(String dbnullable) {
		this.dbnullable = dbnullable;
	}

	/**
	 * @return Returns the dbtype.
	 */
	public String getDbtype() {
		return dbtype;
	}

	/**
	 * @param dbtype
	 *            The dbtype to set.
	 */
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	/**
	 * @return Returns the defaultlength.
	 */
	public String getDefaultlength() {
		return defaultlength;
	}

	/**
	 * @param defaultlength
	 *            The defaultlength to set.
	 */
	public void setDefaultlength(String defaultlength) {
		this.defaultlength = defaultlength;
	}

	/**
	 * @return Returns the defaultvalue.
	 */
	public String getDefaultvalue() {
		return defaultvalue;
	}

	/**
	 * @param defaultvalue
	 *            The defaultvalue to set.
	 */
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the length.
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @param length
	 *            The length to set.
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * @return Returns the nullable.
	 */
	public String getNullable() {
		return nullable;
	}

	/**
	 * @param nullable
	 *            The nullable to set.
	 */
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return Returns the schema.
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            The schema to set.
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return Returns the table.
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table
	 *            The table to set.
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBufferUtil sb = new StringBufferUtil("{");
		sb.append("schema:").append(schema);
		sb.append(";table:").append(table);
		sb.append(";column:").append(column);
		sb.append(";dbtype:").append(dbtype);
		sb.append(";type:").append(type);
		sb.append(";length:").append(length);
		sb.append(";nullable:").append(nullable);
		sb.append(";defaultvalue:").append(defaultvalue);
		sb.append(";defaultlength:").append(defaultlength);
		sb.append(";id:").append(id);
		sb.append(";dbnullable:").append(dbnullable);
		sb.append("}");
		return sb.toString();
	}

}