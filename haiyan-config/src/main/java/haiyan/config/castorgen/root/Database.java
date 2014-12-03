/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen.root;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * database config
 * 
 * @version $Revision$ $Date$
 */
public class Database implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * database tablename
     */
    private java.lang.String _tableName;

    /**
     * user id columnname
     */
    private java.lang.String _userIdColName;

    /**
     * user code columnname
     */
    private java.lang.String _userCodeColName;

    /**
     * user name columnname
     */
    private java.lang.String _userNameColName;

    /**
     * user pass columnname
     */
    private java.lang.String _passwordColName;

    /**
     * default user code
     */
    private java.lang.String _defaultUserCode;

    /**
     * default user pass
     */
    private java.lang.String _defaultUserPass;


      //----------------/
     //- Constructors -/
    //----------------/

    public Database() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'defaultUserCode'. The field
     * 'defaultUserCode' has the following description: default
     * user code
     * 
     * @return the value of field 'DefaultUserCode'.
     */
    public java.lang.String getDefaultUserCode(
    ) {
        return this._defaultUserCode;
    }

    /**
     * Returns the value of field 'defaultUserPass'. The field
     * 'defaultUserPass' has the following description: default
     * user pass
     * 
     * @return the value of field 'DefaultUserPass'.
     */
    public java.lang.String getDefaultUserPass(
    ) {
        return this._defaultUserPass;
    }

    /**
     * Returns the value of field 'passwordColName'. The field
     * 'passwordColName' has the following description: user pass
     * columnname
     * 
     * @return the value of field 'PasswordColName'.
     */
    public java.lang.String getPasswordColName(
    ) {
        return this._passwordColName;
    }

    /**
     * Returns the value of field 'tableName'. The field
     * 'tableName' has the following description: database
     * tablename
     * 
     * @return the value of field 'TableName'.
     */
    public java.lang.String getTableName(
    ) {
        return this._tableName;
    }

    /**
     * Returns the value of field 'userCodeColName'. The field
     * 'userCodeColName' has the following description: user code
     * columnname
     * 
     * @return the value of field 'UserCodeColName'.
     */
    public java.lang.String getUserCodeColName(
    ) {
        return this._userCodeColName;
    }

    /**
     * Returns the value of field 'userIdColName'. The field
     * 'userIdColName' has the following description: user id
     * columnname
     * 
     * @return the value of field 'UserIdColName'.
     */
    public java.lang.String getUserIdColName(
    ) {
        return this._userIdColName;
    }

    /**
     * Returns the value of field 'userNameColName'. The field
     * 'userNameColName' has the following description: user name
     * columnname
     * 
     * @return the value of field 'UserNameColName'.
     */
    public java.lang.String getUserNameColName(
    ) {
        return this._userNameColName;
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
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'defaultUserCode'. The field
     * 'defaultUserCode' has the following description: default
     * user code
     * 
     * @param defaultUserCode the value of field 'defaultUserCode'.
     */
    public void setDefaultUserCode(
            final java.lang.String defaultUserCode) {
        this._defaultUserCode = defaultUserCode;
    }

    /**
     * Sets the value of field 'defaultUserPass'. The field
     * 'defaultUserPass' has the following description: default
     * user pass
     * 
     * @param defaultUserPass the value of field 'defaultUserPass'.
     */
    public void setDefaultUserPass(
            final java.lang.String defaultUserPass) {
        this._defaultUserPass = defaultUserPass;
    }

    /**
     * Sets the value of field 'passwordColName'. The field
     * 'passwordColName' has the following description: user pass
     * columnname
     * 
     * @param passwordColName the value of field 'passwordColName'.
     */
    public void setPasswordColName(
            final java.lang.String passwordColName) {
        this._passwordColName = passwordColName;
    }

    /**
     * Sets the value of field 'tableName'. The field 'tableName'
     * has the following description: database tablename
     * 
     * @param tableName the value of field 'tableName'.
     */
    public void setTableName(
            final java.lang.String tableName) {
        this._tableName = tableName;
    }

    /**
     * Sets the value of field 'userCodeColName'. The field
     * 'userCodeColName' has the following description: user code
     * columnname
     * 
     * @param userCodeColName the value of field 'userCodeColName'.
     */
    public void setUserCodeColName(
            final java.lang.String userCodeColName) {
        this._userCodeColName = userCodeColName;
    }

    /**
     * Sets the value of field 'userIdColName'. The field
     * 'userIdColName' has the following description: user id
     * columnname
     * 
     * @param userIdColName the value of field 'userIdColName'.
     */
    public void setUserIdColName(
            final java.lang.String userIdColName) {
        this._userIdColName = userIdColName;
    }

    /**
     * Sets the value of field 'userNameColName'. The field
     * 'userNameColName' has the following description: user name
     * columnname
     * 
     * @param userNameColName the value of field 'userNameColName'.
     */
    public void setUserNameColName(
            final java.lang.String userNameColName) {
        this._userNameColName = userNameColName;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.root.Database
     */
    public static haiyan.config.castorgen.root.Database unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.Database) Unmarshaller.unmarshal(haiyan.config.castorgen.root.Database.class, reader);
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
