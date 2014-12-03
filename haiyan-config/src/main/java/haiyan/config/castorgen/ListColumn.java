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
 * grid view setting
 * 
 * @version $Revision$ $Date$
 */
public class ListColumn implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * column width
     */
    private java.lang.String _width;

    /**
     * row height
     */
    private java.lang.String _height;

    /**
     * list style
     */
    private java.lang.String _style;

    /**
     * cell nowrap
     */
    private boolean _nowrap = false;

    /**
     * keeps track of state for field: _nowrap
     */
    private boolean _has_nowrap;

    /**
     * can't edit
     */
    private boolean _noedit = false;

    /**
     * keeps track of state for field: _noedit
     */
    private boolean _has_noedit;

    /**
     * can't query
     */
    private boolean _noquery = false;

    /**
     * keeps track of state for field: _noquery
     */
    private boolean _has_noquery;


      //----------------/
     //- Constructors -/
    //----------------/

    public ListColumn() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteNoedit(
    ) {
        this._has_noedit= false;
    }

    /**
     */
    public void deleteNoquery(
    ) {
        this._has_noquery= false;
    }

    /**
     */
    public void deleteNowrap(
    ) {
        this._has_nowrap= false;
    }

    /**
     * Returns the value of field 'height'. The field 'height' has
     * the following description: row height
     * 
     * @return the value of field 'Height'.
     */
    public java.lang.String getHeight(
    ) {
        return this._height;
    }

    /**
     * Returns the value of field 'noedit'. The field 'noedit' has
     * the following description: can't edit
     * 
     * @return the value of field 'Noedit'.
     */
    public boolean getNoedit(
    ) {
        return this._noedit;
    }

    /**
     * Returns the value of field 'noquery'. The field 'noquery'
     * has the following description: can't query
     * 
     * @return the value of field 'Noquery'.
     */
    public boolean getNoquery(
    ) {
        return this._noquery;
    }

    /**
     * Returns the value of field 'nowrap'. The field 'nowrap' has
     * the following description: cell nowrap
     * 
     * @return the value of field 'Nowrap'.
     */
    public boolean getNowrap(
    ) {
        return this._nowrap;
    }

    /**
     * Returns the value of field 'style'. The field 'style' has
     * the following description: list style
     * 
     * @return the value of field 'Style'.
     */
    public java.lang.String getStyle(
    ) {
        return this._style;
    }

    /**
     * Returns the value of field 'width'. The field 'width' has
     * the following description: column width
     * 
     * @return the value of field 'Width'.
     */
    public java.lang.String getWidth(
    ) {
        return this._width;
    }

    /**
     * Method hasNoedit.
     * 
     * @return true if at least one Noedit has been added
     */
    public boolean hasNoedit(
    ) {
        return this._has_noedit;
    }

    /**
     * Method hasNoquery.
     * 
     * @return true if at least one Noquery has been added
     */
    public boolean hasNoquery(
    ) {
        return this._has_noquery;
    }

    /**
     * Method hasNowrap.
     * 
     * @return true if at least one Nowrap has been added
     */
    public boolean hasNowrap(
    ) {
        return this._has_nowrap;
    }

    /**
     * Returns the value of field 'noedit'. The field 'noedit' has
     * the following description: can't edit
     * 
     * @return the value of field 'Noedit'.
     */
    public boolean isNoedit(
    ) {
        return this._noedit;
    }

    /**
     * Returns the value of field 'noquery'. The field 'noquery'
     * has the following description: can't query
     * 
     * @return the value of field 'Noquery'.
     */
    public boolean isNoquery(
    ) {
        return this._noquery;
    }

    /**
     * Returns the value of field 'nowrap'. The field 'nowrap' has
     * the following description: cell nowrap
     * 
     * @return the value of field 'Nowrap'.
     */
    public boolean isNowrap(
    ) {
        return this._nowrap;
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
     * Sets the value of field 'height'. The field 'height' has the
     * following description: row height
     * 
     * @param height the value of field 'height'.
     */
    public void setHeight(
            final java.lang.String height) {
        this._height = height;
    }

    /**
     * Sets the value of field 'noedit'. The field 'noedit' has the
     * following description: can't edit
     * 
     * @param noedit the value of field 'noedit'.
     */
    public void setNoedit(
            final boolean noedit) {
        this._noedit = noedit;
        this._has_noedit = true;
    }

    /**
     * Sets the value of field 'noquery'. The field 'noquery' has
     * the following description: can't query
     * 
     * @param noquery the value of field 'noquery'.
     */
    public void setNoquery(
            final boolean noquery) {
        this._noquery = noquery;
        this._has_noquery = true;
    }

    /**
     * Sets the value of field 'nowrap'. The field 'nowrap' has the
     * following description: cell nowrap
     * 
     * @param nowrap the value of field 'nowrap'.
     */
    public void setNowrap(
            final boolean nowrap) {
        this._nowrap = nowrap;
        this._has_nowrap = true;
    }

    /**
     * Sets the value of field 'style'. The field 'style' has the
     * following description: list style
     * 
     * @param style the value of field 'style'.
     */
    public void setStyle(
            final java.lang.String style) {
        this._style = style;
    }

    /**
     * Sets the value of field 'width'. The field 'width' has the
     * following description: column width
     * 
     * @param width the value of field 'width'.
     */
    public void setWidth(
            final java.lang.String width) {
        this._width = width;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.ListColumn
     */
    public static haiyan.config.castorgen.ListColumn unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.ListColumn) Unmarshaller.unmarshal(haiyan.config.castorgen.ListColumn.class, reader);
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
