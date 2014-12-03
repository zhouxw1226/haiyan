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
 * field rule define
 * 
 * @version $Revision$ $Date$
 */
public class Rule implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * srcTableIndex
     */
    private int _srcTableIndex;

    /**
     * keeps track of state for field: _srcTableIndex
     */
    private boolean _has_srcTableIndex;

    /**
     * some table's field name
     */
    private java.lang.String _field;

    /**
     * destTableIndex
     */
    private int _destTableIndex;

    /**
     * keeps track of state for field: _destTableIndex
     */
    private boolean _has_destTableIndex;

    /**
     * data formula
     */
    private java.lang.String _formula;


      //----------------/
     //- Constructors -/
    //----------------/

    public Rule() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteDestTableIndex(
    ) {
        this._has_destTableIndex= false;
    }

    /**
     */
    public void deleteSrcTableIndex(
    ) {
        this._has_srcTableIndex= false;
    }

    /**
     * Returns the value of field 'destTableIndex'. The field
     * 'destTableIndex' has the following description:
     * destTableIndex
     * 
     * @return the value of field 'DestTableIndex'.
     */
    public int getDestTableIndex(
    ) {
        return this._destTableIndex;
    }

    /**
     * Returns the value of field 'field'. The field 'field' has
     * the following description: some table's field name
     * 
     * @return the value of field 'Field'.
     */
    public java.lang.String getField(
    ) {
        return this._field;
    }

    /**
     * Returns the value of field 'formula'. The field 'formula'
     * has the following description: data formula
     * 
     * @return the value of field 'Formula'.
     */
    public java.lang.String getFormula(
    ) {
        return this._formula;
    }

    /**
     * Returns the value of field 'srcTableIndex'. The field
     * 'srcTableIndex' has the following description: srcTableIndex
     * 
     * @return the value of field 'SrcTableIndex'.
     */
    public int getSrcTableIndex(
    ) {
        return this._srcTableIndex;
    }

    /**
     * Method hasDestTableIndex.
     * 
     * @return true if at least one DestTableIndex has been added
     */
    public boolean hasDestTableIndex(
    ) {
        return this._has_destTableIndex;
    }

    /**
     * Method hasSrcTableIndex.
     * 
     * @return true if at least one SrcTableIndex has been added
     */
    public boolean hasSrcTableIndex(
    ) {
        return this._has_srcTableIndex;
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
     * Sets the value of field 'destTableIndex'. The field
     * 'destTableIndex' has the following description:
     * destTableIndex
     * 
     * @param destTableIndex the value of field 'destTableIndex'.
     */
    public void setDestTableIndex(
            final int destTableIndex) {
        this._destTableIndex = destTableIndex;
        this._has_destTableIndex = true;
    }

    /**
     * Sets the value of field 'field'. The field 'field' has the
     * following description: some table's field name
     * 
     * @param field the value of field 'field'.
     */
    public void setField(
            final java.lang.String field) {
        this._field = field;
    }

    /**
     * Sets the value of field 'formula'. The field 'formula' has
     * the following description: data formula
     * 
     * @param formula the value of field 'formula'.
     */
    public void setFormula(
            final java.lang.String formula) {
        this._formula = formula;
    }

    /**
     * Sets the value of field 'srcTableIndex'. The field
     * 'srcTableIndex' has the following description: srcTableIndex
     * 
     * @param srcTableIndex the value of field 'srcTableIndex'.
     */
    public void setSrcTableIndex(
            final int srcTableIndex) {
        this._srcTableIndex = srcTableIndex;
        this._has_srcTableIndex = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.Rule
     */
    public static haiyan.config.castorgen.Rule unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Rule) Unmarshaller.unmarshal(haiyan.config.castorgen.Rule.class, reader);
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
