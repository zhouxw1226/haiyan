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
 * resource config
 * 
 * @version $Revision$ $Date$
 */
public class Resource implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * system title
     */
    private java.lang.String _title = "GenMis";

    /**
     * left page
     */
    private java.lang.String _leftFrameFile = "/comResource/jsp/genmis/left.jsp";

    /**
     * top page
     */
    private java.lang.String _topFrameFile = "/comResource/jsp/genmis/top.jsp";

    /**
     * main page
     */
    private java.lang.String _mainFrameFile = "/comResource/jsp/genmis/main.jsp";

    /**
     * login page
     */
    private java.lang.String _loginFile = "/comResource/jsp/genmis/login.jsp";

    /**
     * bottom page
     */
    private java.lang.String _bottomFrameFile = "/comResource/jsp/genmis/bottom.jsp";


      //----------------/
     //- Constructors -/
    //----------------/

    public Resource() {
        super();
        setTitle("GenMis");
        setLeftFrameFile("/comResource/jsp/genmis/left.jsp");
        setTopFrameFile("/comResource/jsp/genmis/top.jsp");
        setMainFrameFile("/comResource/jsp/genmis/main.jsp");
        setLoginFile("/comResource/jsp/genmis/login.jsp");
        setBottomFrameFile("/comResource/jsp/genmis/bottom.jsp");
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'bottomFrameFile'. The field
     * 'bottomFrameFile' has the following description: bottom page
     * 
     * @return the value of field 'BottomFrameFile'.
     */
    public java.lang.String getBottomFrameFile(
    ) {
        return this._bottomFrameFile;
    }

    /**
     * Returns the value of field 'leftFrameFile'. The field
     * 'leftFrameFile' has the following description: left page
     * 
     * @return the value of field 'LeftFrameFile'.
     */
    public java.lang.String getLeftFrameFile(
    ) {
        return this._leftFrameFile;
    }

    /**
     * Returns the value of field 'loginFile'. The field
     * 'loginFile' has the following description: login page
     * 
     * @return the value of field 'LoginFile'.
     */
    public java.lang.String getLoginFile(
    ) {
        return this._loginFile;
    }

    /**
     * Returns the value of field 'mainFrameFile'. The field
     * 'mainFrameFile' has the following description: main page
     * 
     * @return the value of field 'MainFrameFile'.
     */
    public java.lang.String getMainFrameFile(
    ) {
        return this._mainFrameFile;
    }

    /**
     * Returns the value of field 'title'. The field 'title' has
     * the following description: system title
     * 
     * @return the value of field 'Title'.
     */
    public java.lang.String getTitle(
    ) {
        return this._title;
    }

    /**
     * Returns the value of field 'topFrameFile'. The field
     * 'topFrameFile' has the following description: top page
     * 
     * @return the value of field 'TopFrameFile'.
     */
    public java.lang.String getTopFrameFile(
    ) {
        return this._topFrameFile;
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
     * Sets the value of field 'bottomFrameFile'. The field
     * 'bottomFrameFile' has the following description: bottom page
     * 
     * @param bottomFrameFile the value of field 'bottomFrameFile'.
     */
    public void setBottomFrameFile(
            final java.lang.String bottomFrameFile) {
        this._bottomFrameFile = bottomFrameFile;
    }

    /**
     * Sets the value of field 'leftFrameFile'. The field
     * 'leftFrameFile' has the following description: left page
     * 
     * @param leftFrameFile the value of field 'leftFrameFile'.
     */
    public void setLeftFrameFile(
            final java.lang.String leftFrameFile) {
        this._leftFrameFile = leftFrameFile;
    }

    /**
     * Sets the value of field 'loginFile'. The field 'loginFile'
     * has the following description: login page
     * 
     * @param loginFile the value of field 'loginFile'.
     */
    public void setLoginFile(
            final java.lang.String loginFile) {
        this._loginFile = loginFile;
    }

    /**
     * Sets the value of field 'mainFrameFile'. The field
     * 'mainFrameFile' has the following description: main page
     * 
     * @param mainFrameFile the value of field 'mainFrameFile'.
     */
    public void setMainFrameFile(
            final java.lang.String mainFrameFile) {
        this._mainFrameFile = mainFrameFile;
    }

    /**
     * Sets the value of field 'title'. The field 'title' has the
     * following description: system title
     * 
     * @param title the value of field 'title'.
     */
    public void setTitle(
            final java.lang.String title) {
        this._title = title;
    }

    /**
     * Sets the value of field 'topFrameFile'. The field
     * 'topFrameFile' has the following description: top page
     * 
     * @param topFrameFile the value of field 'topFrameFile'.
     */
    public void setTopFrameFile(
            final java.lang.String topFrameFile) {
        this._topFrameFile = topFrameFile;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.root.Resource
     */
    public static haiyan.config.castorgen.root.Resource unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.Resource) Unmarshaller.unmarshal(haiyan.config.castorgen.root.Resource.class, reader);
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
