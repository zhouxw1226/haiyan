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
 * Haiyan Root Config
 * 
 * @version $Revision$ $Date$
 */
public class Config implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * system webapp path.example:"/webcontent/"
     */
    private haiyan.config.castorgen.root.WebApp _webApp;

    /**
     * functions scan classes
     */
    private haiyan.config.castorgen.root.Functions _functions;

    /**
     * system config file path.example:"/test/"
     */
    private haiyan.config.castorgen.root.TableConfigFilePath _tableConfigFilePath;

    /**
     * jdbc connect parameter
     */
    private haiyan.config.castorgen.root.JdbcURL _jdbcURL;

    /**
     * jndi connect parameter
     */
    private haiyan.config.castorgen.root.DataSource _dataSource;

    /**
     * security config
     */
    private haiyan.config.castorgen.root.Security _security;

    /**
     * resource config
     */
    private haiyan.config.castorgen.root.Resource _resource;

    /**
     * other database
     */
    private haiyan.config.castorgen.root.DbSource _dbSource;


      //----------------/
     //- Constructors -/
    //----------------/

    public Config() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'dataSource'. The field
     * 'dataSource' has the following description: jndi connect
     * parameter
     * 
     * @return the value of field 'DataSource'.
     */
    public haiyan.config.castorgen.root.DataSource getDataSource(
    ) {
        return this._dataSource;
    }

    /**
     * Returns the value of field 'dbSource'. The field 'dbSource'
     * has the following description: other database
     * 
     * @return the value of field 'DbSource'.
     */
    public haiyan.config.castorgen.root.DbSource getDbSource(
    ) {
        return this._dbSource;
    }

    /**
     * Returns the value of field 'functions'. The field
     * 'functions' has the following description: functions scan
     * classes
     * 
     * @return the value of field 'Functions'.
     */
    public haiyan.config.castorgen.root.Functions getFunctions(
    ) {
        return this._functions;
    }

    /**
     * Returns the value of field 'jdbcURL'. The field 'jdbcURL'
     * has the following description: jdbc connect parameter
     * 
     * @return the value of field 'JdbcURL'.
     */
    public haiyan.config.castorgen.root.JdbcURL getJdbcURL(
    ) {
        return this._jdbcURL;
    }

    /**
     * Returns the value of field 'resource'. The field 'resource'
     * has the following description: resource config
     * 
     * @return the value of field 'Resource'.
     */
    public haiyan.config.castorgen.root.Resource getResource(
    ) {
        return this._resource;
    }

    /**
     * Returns the value of field 'security'. The field 'security'
     * has the following description: security config
     * 
     * @return the value of field 'Security'.
     */
    public haiyan.config.castorgen.root.Security getSecurity(
    ) {
        return this._security;
    }

    /**
     * Returns the value of field 'tableConfigFilePath'. The field
     * 'tableConfigFilePath' has the following description: system
     * config file path.example:"/test/"
     * 
     * @return the value of field 'TableConfigFilePath'.
     */
    public haiyan.config.castorgen.root.TableConfigFilePath getTableConfigFilePath(
    ) {
        return this._tableConfigFilePath;
    }

    /**
     * Returns the value of field 'webApp'. The field 'webApp' has
     * the following description: system webapp
     * path.example:"/webcontent/"
     * 
     * @return the value of field 'WebApp'.
     */
    public haiyan.config.castorgen.root.WebApp getWebApp(
    ) {
        return this._webApp;
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
     * Sets the value of field 'dataSource'. The field 'dataSource'
     * has the following description: jndi connect parameter
     * 
     * @param dataSource the value of field 'dataSource'.
     */
    public void setDataSource(
            final haiyan.config.castorgen.root.DataSource dataSource) {
        this._dataSource = dataSource;
    }

    /**
     * Sets the value of field 'dbSource'. The field 'dbSource' has
     * the following description: other database
     * 
     * @param dbSource the value of field 'dbSource'.
     */
    public void setDbSource(
            final haiyan.config.castorgen.root.DbSource dbSource) {
        this._dbSource = dbSource;
    }

    /**
     * Sets the value of field 'functions'. The field 'functions'
     * has the following description: functions scan classes
     * 
     * @param functions the value of field 'functions'.
     */
    public void setFunctions(
            final haiyan.config.castorgen.root.Functions functions) {
        this._functions = functions;
    }

    /**
     * Sets the value of field 'jdbcURL'. The field 'jdbcURL' has
     * the following description: jdbc connect parameter
     * 
     * @param jdbcURL the value of field 'jdbcURL'.
     */
    public void setJdbcURL(
            final haiyan.config.castorgen.root.JdbcURL jdbcURL) {
        this._jdbcURL = jdbcURL;
    }

    /**
     * Sets the value of field 'resource'. The field 'resource' has
     * the following description: resource config
     * 
     * @param resource the value of field 'resource'.
     */
    public void setResource(
            final haiyan.config.castorgen.root.Resource resource) {
        this._resource = resource;
    }

    /**
     * Sets the value of field 'security'. The field 'security' has
     * the following description: security config
     * 
     * @param security the value of field 'security'.
     */
    public void setSecurity(
            final haiyan.config.castorgen.root.Security security) {
        this._security = security;
    }

    /**
     * Sets the value of field 'tableConfigFilePath'. The field
     * 'tableConfigFilePath' has the following description: system
     * config file path.example:"/test/"
     * 
     * @param tableConfigFilePath the value of field
     * 'tableConfigFilePath'.
     */
    public void setTableConfigFilePath(
            final haiyan.config.castorgen.root.TableConfigFilePath tableConfigFilePath) {
        this._tableConfigFilePath = tableConfigFilePath;
    }

    /**
     * Sets the value of field 'webApp'. The field 'webApp' has the
     * following description: system webapp
     * path.example:"/webcontent/"
     * 
     * @param webApp the value of field 'webApp'.
     */
    public void setWebApp(
            final haiyan.config.castorgen.root.WebApp webApp) {
        this._webApp = webApp;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.root.Config
     */
    public static haiyan.config.castorgen.root.Config unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.Config) Unmarshaller.unmarshal(haiyan.config.castorgen.root.Config.class, reader);
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
