/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen;

/**
 * abstract bill field
 * 
 * @version $Revision$ $Date$
 */
public abstract class AbstractBillField extends haiyan.config.castorgen.AbstractCommonField 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * db name
     */
    private java.lang.String _dbName;

    /**
     * tableIndex
     */
    private int _tableIndex;

    /**
     * keeps track of state for field: _tableIndex
     */
    private boolean _has_tableIndex;


      //----------------/
     //- Constructors -/
    //----------------/

    public AbstractBillField() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteTableIndex(
    ) {
        this._has_tableIndex= false;
    }

    /**
     * Returns the value of field 'dbName'. The field 'dbName' has
     * the following description: db name
     * 
     * @return the value of field 'DbName'.
     */
    public java.lang.String getDbName(
    ) {
        return this._dbName;
    }

    /**
     * Returns the value of field 'tableIndex'. The field
     * 'tableIndex' has the following description: tableIndex
     * 
     * @return the value of field 'TableIndex'.
     */
    public int getTableIndex(
    ) {
        return this._tableIndex;
    }

    /**
     * Method hasTableIndex.
     * 
     * @return true if at least one TableIndex has been added
     */
    public boolean hasTableIndex(
    ) {
        return this._has_tableIndex;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Sets the value of field 'dbName'. The field 'dbName' has the
     * following description: db name
     * 
     * @param dbName the value of field 'dbName'.
     */
    public void setDbName(
            final java.lang.String dbName) {
        this._dbName = dbName;
    }

    /**
     * Sets the value of field 'tableIndex'. The field 'tableIndex'
     * has the following description: tableIndex
     * 
     * @param tableIndex the value of field 'tableIndex'.
     */
    public void setTableIndex(
            final int tableIndex) {
        this._tableIndex = tableIndex;
        this._has_tableIndex = true;
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
