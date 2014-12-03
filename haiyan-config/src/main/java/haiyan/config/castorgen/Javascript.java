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
 * javascript of page
 * 
 * @version $Revision$ $Date$
 */
public class Javascript implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * parameter
     */
    private java.lang.String _parameter;

    /**
     * js src
     */
    private java.lang.String _src;

    /**
     * page of host
     */
    private haiyan.config.castorgen.types.JavascriptHostPageType _hostPage = haiyan.config.castorgen.types.JavascriptHostPageType.valueOf("query");

    /**
     * internal content storage
     */
    private java.lang.String _content = "";


      //----------------/
     //- Constructors -/
    //----------------/

    public Javascript() {
        super();
        setHostPage(haiyan.config.castorgen.types.JavascriptHostPageType.valueOf("query"));
        setContent("");
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'content'. The field 'content'
     * has the following description: internal content storage
     * 
     * @return the value of field 'Content'.
     */
    public java.lang.String getContent(
    ) {
        return this._content;
    }

    /**
     * Returns the value of field 'hostPage'. The field 'hostPage'
     * has the following description: page of host
     * 
     * @return the value of field 'HostPage'.
     */
    public haiyan.config.castorgen.types.JavascriptHostPageType getHostPage(
    ) {
        return this._hostPage;
    }

    /**
     * Returns the value of field 'parameter'. The field
     * 'parameter' has the following description: parameter
     * 
     * @return the value of field 'Parameter'.
     */
    public java.lang.String getParameter(
    ) {
        return this._parameter;
    }

    /**
     * Returns the value of field 'src'. The field 'src' has the
     * following description: js src
     * 
     * @return the value of field 'Src'.
     */
    public java.lang.String getSrc(
    ) {
        return this._src;
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
     * Sets the value of field 'content'. The field 'content' has
     * the following description: internal content storage
     * 
     * @param content the value of field 'content'.
     */
    public void setContent(
            final java.lang.String content) {
        this._content = content;
    }

    /**
     * Sets the value of field 'hostPage'. The field 'hostPage' has
     * the following description: page of host
     * 
     * @param hostPage the value of field 'hostPage'.
     */
    public void setHostPage(
            final haiyan.config.castorgen.types.JavascriptHostPageType hostPage) {
        this._hostPage = hostPage;
    }

    /**
     * Sets the value of field 'parameter'. The field 'parameter'
     * has the following description: parameter
     * 
     * @param parameter the value of field 'parameter'.
     */
    public void setParameter(
            final java.lang.String parameter) {
        this._parameter = parameter;
    }

    /**
     * Sets the value of field 'src'. The field 'src' has the
     * following description: js src
     * 
     * @param src the value of field 'src'.
     */
    public void setSrc(
            final java.lang.String src) {
        this._src = src;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.Javascript
     */
    public static haiyan.config.castorgen.Javascript unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Javascript) Unmarshaller.unmarshal(haiyan.config.castorgen.Javascript.class, reader);
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
