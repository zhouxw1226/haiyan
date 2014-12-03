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
 * jndi connect parameter
 * 
 * @version $Revision$ $Date$
 */
public class DataSource implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * jndi name
     */
    private java.lang.String _name;

    /**
     * Field _dbType.
     */
    private java.lang.String _dbType = "genaralDB";

    /**
     * jndi resource
     */
    private java.lang.String _jndi;


      //----------------/
     //- Constructors -/
    //----------------/

    public DataSource() {
        super();
        setDbType("genaralDB");
    }


      //-----------/
     //- Methods -/
    //-----------/

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
     * Returns the value of field 'jndi'. The field 'jndi' has the
     * following description: jndi resource
     * 
     * @return the value of field 'Jndi'.
     */
    public java.lang.String getJndi(
    ) {
        return this._jndi;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: jndi name
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
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
     * Sets the value of field 'jndi'. The field 'jndi' has the
     * following description: jndi resource
     * 
     * @param jndi the value of field 'jndi'.
     */
    public void setJndi(
            final java.lang.String jndi) {
        this._jndi = jndi;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: jndi name
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * haiyan.config.castorgen.root.DataSource
     */
    public static haiyan.config.castorgen.root.DataSource unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.DataSource) Unmarshaller.unmarshal(haiyan.config.castorgen.root.DataSource.class, reader);
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
