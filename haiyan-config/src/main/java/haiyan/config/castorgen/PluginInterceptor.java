/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * intercepotor of one plugin
 * 
 * @version $Revision$ $Date$
 */
public class PluginInterceptor extends haiyan.config.castorgen.ExternalProgram 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * which plugin name
     */
    private java.lang.String _pluginName;

    /**
     * intercepotor type
     */
    private haiyan.config.castorgen.types.ExternalProgramTypeType _type = haiyan.config.castorgen.types.ExternalProgramTypeType.valueOf("before");


      //----------------/
     //- Constructors -/
    //----------------/

    public PluginInterceptor() {
        super();
        setType(haiyan.config.castorgen.types.ExternalProgramTypeType.valueOf("before"));
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'pluginName'. The field
     * 'pluginName' has the following description: which plugin
     * name
     * 
     * @return the value of field 'PluginName'.
     */
    public java.lang.String getPluginName(
    ) {
        return this._pluginName;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: intercepotor type
     * 
     * @return the value of field 'Type'.
     */
    public haiyan.config.castorgen.types.ExternalProgramTypeType getType(
    ) {
        return this._type;
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
     * Sets the value of field 'pluginName'. The field 'pluginName'
     * has the following description: which plugin name
     * 
     * @param pluginName the value of field 'pluginName'.
     */
    public void setPluginName(
            final java.lang.String pluginName) {
        this._pluginName = pluginName;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: intercepotor type
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final haiyan.config.castorgen.types.ExternalProgramTypeType type) {
        this._type = type;
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
     * haiyan.config.castorgen.ExternalProgram
     */
    public static haiyan.config.castorgen.ExternalProgram unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.ExternalProgram) Unmarshaller.unmarshal(haiyan.config.castorgen.PluginInterceptor.class, reader);
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
