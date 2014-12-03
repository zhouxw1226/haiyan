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
 * user mapping
 * 
 * @version $Revision$ $Date$
 */
public class UserRegistry implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * database config
     */
    private haiyan.config.castorgen.root.Database _database;

    /**
     * ldap catalog
     */
    private haiyan.config.castorgen.root.LDAP _LDAP;

    /**
     * cert class
     */
    private haiyan.config.castorgen.root.Customerize _customerize;


      //----------------/
     //- Constructors -/
    //----------------/

    public UserRegistry() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'choiceValue'. The field
     * 'choiceValue' has the following description: Internal choice
     * value storage
     * 
     * @return the value of field 'ChoiceValue'.
     */
    public java.lang.Object getChoiceValue(
    ) {
        return this._choiceValue;
    }

    /**
     * Returns the value of field 'customerize'. The field
     * 'customerize' has the following description: cert class
     * 
     * @return the value of field 'Customerize'.
     */
    public haiyan.config.castorgen.root.Customerize getCustomerize(
    ) {
        return this._customerize;
    }

    /**
     * Returns the value of field 'database'. The field 'database'
     * has the following description: database config
     * 
     * @return the value of field 'Database'.
     */
    public haiyan.config.castorgen.root.Database getDatabase(
    ) {
        return this._database;
    }

    /**
     * Returns the value of field 'LDAP'. The field 'LDAP' has the
     * following description: ldap catalog
     * 
     * @return the value of field 'LDAP'.
     */
    public haiyan.config.castorgen.root.LDAP getLDAP(
    ) {
        return this._LDAP;
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
     * Sets the value of field 'customerize'. The field
     * 'customerize' has the following description: cert class
     * 
     * @param customerize the value of field 'customerize'.
     */
    public void setCustomerize(
            final haiyan.config.castorgen.root.Customerize customerize) {
        this._customerize = customerize;
        this._choiceValue = customerize;
    }

    /**
     * Sets the value of field 'database'. The field 'database' has
     * the following description: database config
     * 
     * @param database the value of field 'database'.
     */
    public void setDatabase(
            final haiyan.config.castorgen.root.Database database) {
        this._database = database;
        this._choiceValue = database;
    }

    /**
     * Sets the value of field 'LDAP'. The field 'LDAP' has the
     * following description: ldap catalog
     * 
     * @param LDAP the value of field 'LDAP'.
     */
    public void setLDAP(
            final haiyan.config.castorgen.root.LDAP LDAP) {
        this._LDAP = LDAP;
        this._choiceValue = LDAP;
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
     * haiyan.config.castorgen.root.UserRegistry
     */
    public static haiyan.config.castorgen.root.UserRegistry unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.UserRegistry) Unmarshaller.unmarshal(haiyan.config.castorgen.root.UserRegistry.class, reader);
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
