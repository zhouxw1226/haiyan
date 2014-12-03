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
 * field order config for query page
 *  
 * 
 * @version $Revision$ $Date$
 */
public class Order implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * this field need order
     */
    private boolean _needOrder = true;

    /**
     * keeps track of state for field: _needOrder
     */
    private boolean _has_needOrder;


      //----------------/
     //- Constructors -/
    //----------------/

    public Order() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteNeedOrder(
    ) {
        this._has_needOrder= false;
    }

    /**
     * Returns the value of field 'needOrder'. The field
     * 'needOrder' has the following description: this field need
     * order
     * 
     * @return the value of field 'NeedOrder'.
     */
    public boolean getNeedOrder(
    ) {
        return this._needOrder;
    }

    /**
     * Method hasNeedOrder.
     * 
     * @return true if at least one NeedOrder has been added
     */
    public boolean hasNeedOrder(
    ) {
        return this._has_needOrder;
    }

    /**
     * Returns the value of field 'needOrder'. The field
     * 'needOrder' has the following description: this field need
     * order
     * 
     * @return the value of field 'NeedOrder'.
     */
    public boolean isNeedOrder(
    ) {
        return this._needOrder;
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
     * Sets the value of field 'needOrder'. The field 'needOrder'
     * has the following description: this field need order
     * 
     * @param needOrder the value of field 'needOrder'.
     */
    public void setNeedOrder(
            final boolean needOrder) {
        this._needOrder = needOrder;
        this._has_needOrder = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.Order
     */
    public static haiyan.config.castorgen.Order unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Order) Unmarshaller.unmarshal(haiyan.config.castorgen.Order.class, reader);
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
