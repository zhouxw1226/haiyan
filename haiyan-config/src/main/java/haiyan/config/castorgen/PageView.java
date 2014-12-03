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
 * display view
 * 
 * @version $Revision$ $Date$
 */
public class PageView extends haiyan.config.castorgen.ExternalProgram 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * view title
     */
    private java.lang.String _title;

    /**
     * display field value, diliver by ","
     *  
     */
    private java.lang.String _showField;

    /**
     * view icon
     */
    private java.lang.String _img;

    /**
     * view host page
     */
    private haiyan.config.castorgen.types.PageViewHostPageType _hostPage = haiyan.config.castorgen.types.PageViewHostPageType.valueOf("query");

    /**
     * tree type
     */
    private haiyan.config.castorgen.types.PageViewTypeType _type;


      //----------------/
     //- Constructors -/
    //----------------/

    public PageView() {
        super();
        setHostPage(haiyan.config.castorgen.types.PageViewHostPageType.valueOf("query"));
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'hostPage'. The field 'hostPage'
     * has the following description: view host page
     * 
     * @return the value of field 'HostPage'.
     */
    public haiyan.config.castorgen.types.PageViewHostPageType getHostPage(
    ) {
        return this._hostPage;
    }

    /**
     * Returns the value of field 'img'. The field 'img' has the
     * following description: view icon
     * 
     * @return the value of field 'Img'.
     */
    public java.lang.String getImg(
    ) {
        return this._img;
    }

    /**
     * Returns the value of field 'showField'. The field
     * 'showField' has the following description: display field
     * value, diliver by ","
     *  
     * 
     * @return the value of field 'ShowField'.
     */
    public java.lang.String getShowField(
    ) {
        return this._showField;
    }

    /**
     * Returns the value of field 'title'. The field 'title' has
     * the following description: view title
     * 
     * @return the value of field 'Title'.
     */
    public java.lang.String getTitle(
    ) {
        return this._title;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: tree type
     * 
     * @return the value of field 'Type'.
     */
    public haiyan.config.castorgen.types.PageViewTypeType getType(
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
     * Sets the value of field 'hostPage'. The field 'hostPage' has
     * the following description: view host page
     * 
     * @param hostPage the value of field 'hostPage'.
     */
    public void setHostPage(
            final haiyan.config.castorgen.types.PageViewHostPageType hostPage) {
        this._hostPage = hostPage;
    }

    /**
     * Sets the value of field 'img'. The field 'img' has the
     * following description: view icon
     * 
     * @param img the value of field 'img'.
     */
    public void setImg(
            final java.lang.String img) {
        this._img = img;
    }

    /**
     * Sets the value of field 'showField'. The field 'showField'
     * has the following description: display field value, diliver
     * by ","
     *  
     * 
     * @param showField the value of field 'showField'.
     */
    public void setShowField(
            final java.lang.String showField) {
        this._showField = showField;
    }

    /**
     * Sets the value of field 'title'. The field 'title' has the
     * following description: view title
     * 
     * @param title the value of field 'title'.
     */
    public void setTitle(
            final java.lang.String title) {
        this._title = title;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: tree type
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final haiyan.config.castorgen.types.PageViewTypeType type) {
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
        return (haiyan.config.castorgen.ExternalProgram) Unmarshaller.unmarshal(haiyan.config.castorgen.PageView.class, reader);
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
