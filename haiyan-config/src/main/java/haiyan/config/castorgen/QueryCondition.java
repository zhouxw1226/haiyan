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
 * Class QueryCondition.
 * 
 * @version $Revision$ $Date$
 */
public class QueryCondition implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * query condition type
     */
    private haiyan.config.castorgen.types.QueryConditionTypeType _type;

    /**
     * can order
     */
    private boolean _order = true;

    /**
     * keeps track of state for field: _order
     */
    private boolean _has_order;


      //----------------/
     //- Constructors -/
    //----------------/

    public QueryCondition() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteOrder(
    ) {
        this._has_order= false;
    }

    /**
     * Returns the value of field 'order'. The field 'order' has
     * the following description: can order
     * 
     * @return the value of field 'Order'.
     */
    public boolean getOrder(
    ) {
        return this._order;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: query condition type
     * 
     * @return the value of field 'Type'.
     */
    public haiyan.config.castorgen.types.QueryConditionTypeType getType(
    ) {
        return this._type;
    }

    /**
     * Method hasOrder.
     * 
     * @return true if at least one Order has been added
     */
    public boolean hasOrder(
    ) {
        return this._has_order;
    }

    /**
     * Returns the value of field 'order'. The field 'order' has
     * the following description: can order
     * 
     * @return the value of field 'Order'.
     */
    public boolean isOrder(
    ) {
        return this._order;
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
     * Sets the value of field 'order'. The field 'order' has the
     * following description: can order
     * 
     * @param order the value of field 'order'.
     */
    public void setOrder(
            final boolean order) {
        this._order = order;
        this._has_order = true;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: query condition type
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final haiyan.config.castorgen.types.QueryConditionTypeType type) {
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
     * @return the unmarshaled haiyan.config.castorgen.QueryConditio
     */
    public static haiyan.config.castorgen.QueryCondition unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.QueryCondition) Unmarshaller.unmarshal(haiyan.config.castorgen.QueryCondition.class, reader);
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
