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
 * wfResult
 * 
 * @version $Revision$ $Date$
 */
public class WfResult implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _resultCondition.
     */
    private java.lang.String _resultCondition;

    /**
     * Field _nextStatusKey.
     */
    private java.lang.String _nextStatusKey;

    /**
     * Field _index.
     */
    private int _index;

    /**
     * keeps track of state for field: _index
     */
    private boolean _has_index;


      //----------------/
     //- Constructors -/
    //----------------/

    public WfResult() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteIndex(
    ) {
        this._has_index= false;
    }

    /**
     * Returns the value of field 'index'.
     * 
     * @return the value of field 'Index'.
     */
    public int getIndex(
    ) {
        return this._index;
    }

    /**
     * Returns the value of field 'nextStatusKey'.
     * 
     * @return the value of field 'NextStatusKey'.
     */
    public java.lang.String getNextStatusKey(
    ) {
        return this._nextStatusKey;
    }

    /**
     * Returns the value of field 'resultCondition'.
     * 
     * @return the value of field 'ResultCondition'.
     */
    public java.lang.String getResultCondition(
    ) {
        return this._resultCondition;
    }

    /**
     * Method hasIndex.
     * 
     * @return true if at least one Index has been added
     */
    public boolean hasIndex(
    ) {
        return this._has_index;
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
     * Sets the value of field 'index'.
     * 
     * @param index the value of field 'index'.
     */
    public void setIndex(
            final int index) {
        this._index = index;
        this._has_index = true;
    }

    /**
     * Sets the value of field 'nextStatusKey'.
     * 
     * @param nextStatusKey the value of field 'nextStatusKey'.
     */
    public void setNextStatusKey(
            final java.lang.String nextStatusKey) {
        this._nextStatusKey = nextStatusKey;
    }

    /**
     * Sets the value of field 'resultCondition'.
     * 
     * @param resultCondition the value of field 'resultCondition'.
     */
    public void setResultCondition(
            final java.lang.String resultCondition) {
        this._resultCondition = resultCondition;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.WfResult
     */
    public static haiyan.config.castorgen.WfResult unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.WfResult) Unmarshaller.unmarshal(haiyan.config.castorgen.WfResult.class, reader);
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
