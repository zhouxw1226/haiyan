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
 * security config
 * 
 * @version $Revision$ $Date$
 */
public class Security implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * enable cert
     */
    private boolean _enabled = false;

    /**
     * keeps track of state for field: _enabled
     */
    private boolean _has_enabled;

    /**
     * certificate need this config:security.enabled=true. Must
     * instance com.haiyan.genmis.core.right.CertificateMapping
     */
    private haiyan.config.castorgen.root.CertificateMapping _certificateMapping;

    /**
     * user mapping
     */
    private haiyan.config.castorgen.root.UserRegistry _userRegistry;


      //----------------/
     //- Constructors -/
    //----------------/

    public Security() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteEnabled(
    ) {
        this._has_enabled= false;
    }

    /**
     * Returns the value of field 'certificateMapping'. The field
     * 'certificateMapping' has the following description:
     * certificate need this config:security.enabled=true. Must
     * instance com.haiyan.genmis.core.right.CertificateMapping
     * 
     * @return the value of field 'CertificateMapping'.
     */
    public haiyan.config.castorgen.root.CertificateMapping getCertificateMapping(
    ) {
        return this._certificateMapping;
    }

    /**
     * Returns the value of field 'enabled'. The field 'enabled'
     * has the following description: enable cert
     * 
     * @return the value of field 'Enabled'.
     */
    public boolean getEnabled(
    ) {
        return this._enabled;
    }

    /**
     * Returns the value of field 'userRegistry'. The field
     * 'userRegistry' has the following description: user mapping
     * 
     * @return the value of field 'UserRegistry'.
     */
    public haiyan.config.castorgen.root.UserRegistry getUserRegistry(
    ) {
        return this._userRegistry;
    }

    /**
     * Method hasEnabled.
     * 
     * @return true if at least one Enabled has been added
     */
    public boolean hasEnabled(
    ) {
        return this._has_enabled;
    }

    /**
     * Returns the value of field 'enabled'. The field 'enabled'
     * has the following description: enable cert
     * 
     * @return the value of field 'Enabled'.
     */
    public boolean isEnabled(
    ) {
        return this._enabled;
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
     * Sets the value of field 'certificateMapping'. The field
     * 'certificateMapping' has the following description:
     * certificate need this config:security.enabled=true. Must
     * instance com.haiyan.genmis.core.right.CertificateMapping
     * 
     * @param certificateMapping the value of field
     * 'certificateMapping'.
     */
    public void setCertificateMapping(
            final haiyan.config.castorgen.root.CertificateMapping certificateMapping) {
        this._certificateMapping = certificateMapping;
    }

    /**
     * Sets the value of field 'enabled'. The field 'enabled' has
     * the following description: enable cert
     * 
     * @param enabled the value of field 'enabled'.
     */
    public void setEnabled(
            final boolean enabled) {
        this._enabled = enabled;
        this._has_enabled = true;
    }

    /**
     * Sets the value of field 'userRegistry'. The field
     * 'userRegistry' has the following description: user mapping
     * 
     * @param userRegistry the value of field 'userRegistry'.
     */
    public void setUserRegistry(
            final haiyan.config.castorgen.root.UserRegistry userRegistry) {
        this._userRegistry = userRegistry;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.root.Security
     */
    public static haiyan.config.castorgen.root.Security unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.Security) Unmarshaller.unmarshal(haiyan.config.castorgen.root.Security.class, reader);
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
