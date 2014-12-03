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
 * jdbc connect parameter
 * 
 * @version $Revision$ $Date$
 */
public class JdbcURL implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * jdbc name
     */
    private java.lang.String _name;

    /**
     * Field _dbType.
     */
    private java.lang.String _dbType = "genaralDB";

    /**
     * jdbc driver name.example:oracle
     * "oracle.jdbc.driver.OracleDriver", Access
     * "sun.jdbc.odbc.JdbcOdbcDriver"
     */
    private java.lang.String _driver;

    /**
     * jdbc url.example:oracle
     * "jdbc:oracle:thin:@10.168.3.14:1521:sinopec", Access
     * "jdbc:odbc:driver={Microsoft Access Driver
     * (.mdb)};DBQ=E:\\LiweiWork\\temp\\test.mdb"
     */
    private java.lang.String _url;

    /**
     * jdbc username
     */
    private java.lang.String _username;

    /**
     * jdbc userpass
     */
    private java.lang.String _password;

    /**
     * use dbcp
     */
    private boolean _dbcp = false;

    /**
     * keeps track of state for field: _dbcp
     */
    private boolean _has_dbcp;

    /**
     * dbcpProperty FileName
     */
    private java.lang.String _dbcpProperty;


      //----------------/
     //- Constructors -/
    //----------------/

    public JdbcURL() {
        super();
        setDbType("genaralDB");
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteDbcp(
    ) {
        this._has_dbcp= false;
    }

    /**
     * Returns the value of field 'dbType'.
     * 
     * @return the value of field 'DbType'.
     */
    public java.lang.String getDbType(
    ) {
        return this._dbType;
    }

    /**
     * Returns the value of field 'dbcp'. The field 'dbcp' has the
     * following description: use dbcp
     * 
     * @return the value of field 'Dbcp'.
     */
    public boolean getDbcp(
    ) {
        return this._dbcp;
    }

    /**
     * Returns the value of field 'dbcpProperty'. The field
     * 'dbcpProperty' has the following description: dbcpProperty
     * FileName
     * 
     * @return the value of field 'DbcpProperty'.
     */
    public java.lang.String getDbcpProperty(
    ) {
        return this._dbcpProperty;
    }

    /**
     * Returns the value of field 'driver'. The field 'driver' has
     * the following description: jdbc driver name.example:oracle
     * "oracle.jdbc.driver.OracleDriver", Access
     * "sun.jdbc.odbc.JdbcOdbcDriver"
     * 
     * @return the value of field 'Driver'.
     */
    public java.lang.String getDriver(
    ) {
        return this._driver;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: jdbc name
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'password'. The field 'password'
     * has the following description: jdbc userpass
     * 
     * @return the value of field 'Password'.
     */
    public java.lang.String getPassword(
    ) {
        return this._password;
    }

    /**
     * Returns the value of field 'url'. The field 'url' has the
     * following description: jdbc url.example:oracle
     * "jdbc:oracle:thin:@10.168.3.14:1521:sinopec", Access
     * "jdbc:odbc:driver={Microsoft Access Driver
     * (.mdb)};DBQ=E:\\LiweiWork\\temp\\test.mdb"
     * 
     * @return the value of field 'Url'.
     */
    public java.lang.String getUrl(
    ) {
        return this._url;
    }

    /**
     * Returns the value of field 'username'. The field 'username'
     * has the following description: jdbc username
     * 
     * @return the value of field 'Username'.
     */
    public java.lang.String getUsername(
    ) {
        return this._username;
    }

    /**
     * Method hasDbcp.
     * 
     * @return true if at least one Dbcp has been added
     */
    public boolean hasDbcp(
    ) {
        return this._has_dbcp;
    }

    /**
     * Returns the value of field 'dbcp'. The field 'dbcp' has the
     * following description: use dbcp
     * 
     * @return the value of field 'Dbcp'.
     */
    public boolean isDbcp(
    ) {
        return this._dbcp;
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
     * Sets the value of field 'dbType'.
     * 
     * @param dbType the value of field 'dbType'.
     */
    public void setDbType(
            final java.lang.String dbType) {
        this._dbType = dbType;
    }

    /**
     * Sets the value of field 'dbcp'. The field 'dbcp' has the
     * following description: use dbcp
     * 
     * @param dbcp the value of field 'dbcp'.
     */
    public void setDbcp(
            final boolean dbcp) {
        this._dbcp = dbcp;
        this._has_dbcp = true;
    }

    /**
     * Sets the value of field 'dbcpProperty'. The field
     * 'dbcpProperty' has the following description: dbcpProperty
     * FileName
     * 
     * @param dbcpProperty the value of field 'dbcpProperty'.
     */
    public void setDbcpProperty(
            final java.lang.String dbcpProperty) {
        this._dbcpProperty = dbcpProperty;
    }

    /**
     * Sets the value of field 'driver'. The field 'driver' has the
     * following description: jdbc driver name.example:oracle
     * "oracle.jdbc.driver.OracleDriver", Access
     * "sun.jdbc.odbc.JdbcOdbcDriver"
     * 
     * @param driver the value of field 'driver'.
     */
    public void setDriver(
            final java.lang.String driver) {
        this._driver = driver;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: jdbc name
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'password'. The field 'password' has
     * the following description: jdbc userpass
     * 
     * @param password the value of field 'password'.
     */
    public void setPassword(
            final java.lang.String password) {
        this._password = password;
    }

    /**
     * Sets the value of field 'url'. The field 'url' has the
     * following description: jdbc url.example:oracle
     * "jdbc:oracle:thin:@10.168.3.14:1521:sinopec", Access
     * "jdbc:odbc:driver={Microsoft Access Driver
     * (.mdb)};DBQ=E:\\LiweiWork\\temp\\test.mdb"
     * 
     * @param url the value of field 'url'.
     */
    public void setUrl(
            final java.lang.String url) {
        this._url = url;
    }

    /**
     * Sets the value of field 'username'. The field 'username' has
     * the following description: jdbc username
     * 
     * @param username the value of field 'username'.
     */
    public void setUsername(
            final java.lang.String username) {
        this._username = username;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.root.JdbcURL
     */
    public static haiyan.config.castorgen.root.JdbcURL unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.JdbcURL) Unmarshaller.unmarshal(haiyan.config.castorgen.root.JdbcURL.class, reader);
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
