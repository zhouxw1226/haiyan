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
 * statistics view
 * 
 * @version $Revision$ $Date$
 */
public class StatisticsView implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * field level code
     */
    private java.lang.String _levelCodeField;

    /**
     * field writable
     */
    private java.lang.String _canWrittenField;

    /**
     * field retractable
     */
    private java.lang.String _retractField;

    /**
     * sum field
     */
    private java.lang.String _sumDetailField;


      //----------------/
     //- Constructors -/
    //----------------/

    public StatisticsView() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'canWrittenField'. The field
     * 'canWrittenField' has the following description: field
     * writable
     * 
     * @return the value of field 'CanWrittenField'.
     */
    public java.lang.String getCanWrittenField(
    ) {
        return this._canWrittenField;
    }

    /**
     * Returns the value of field 'levelCodeField'. The field
     * 'levelCodeField' has the following description: field level
     * code
     * 
     * @return the value of field 'LevelCodeField'.
     */
    public java.lang.String getLevelCodeField(
    ) {
        return this._levelCodeField;
    }

    /**
     * Returns the value of field 'retractField'. The field
     * 'retractField' has the following description: field
     * retractable
     * 
     * @return the value of field 'RetractField'.
     */
    public java.lang.String getRetractField(
    ) {
        return this._retractField;
    }

    /**
     * Returns the value of field 'sumDetailField'. The field
     * 'sumDetailField' has the following description: sum field
     * 
     * @return the value of field 'SumDetailField'.
     */
    public java.lang.String getSumDetailField(
    ) {
        return this._sumDetailField;
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
     * Sets the value of field 'canWrittenField'. The field
     * 'canWrittenField' has the following description: field
     * writable
     * 
     * @param canWrittenField the value of field 'canWrittenField'.
     */
    public void setCanWrittenField(
            final java.lang.String canWrittenField) {
        this._canWrittenField = canWrittenField;
    }

    /**
     * Sets the value of field 'levelCodeField'. The field
     * 'levelCodeField' has the following description: field level
     * code
     * 
     * @param levelCodeField the value of field 'levelCodeField'.
     */
    public void setLevelCodeField(
            final java.lang.String levelCodeField) {
        this._levelCodeField = levelCodeField;
    }

    /**
     * Sets the value of field 'retractField'. The field
     * 'retractField' has the following description: field
     * retractable
     * 
     * @param retractField the value of field 'retractField'.
     */
    public void setRetractField(
            final java.lang.String retractField) {
        this._retractField = retractField;
    }

    /**
     * Sets the value of field 'sumDetailField'. The field
     * 'sumDetailField' has the following description: sum field
     * 
     * @param sumDetailField the value of field 'sumDetailField'.
     */
    public void setSumDetailField(
            final java.lang.String sumDetailField) {
        this._sumDetailField = sumDetailField;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.StatisticsVie
     */
    public static haiyan.config.castorgen.StatisticsView unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.StatisticsView) Unmarshaller.unmarshal(haiyan.config.castorgen.StatisticsView.class, reader);
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
