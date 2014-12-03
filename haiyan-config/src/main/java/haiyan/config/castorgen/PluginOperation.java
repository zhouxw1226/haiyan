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
 * plugin parameter
 * 
 * @version $Revision$ $Date$
 */
public class PluginOperation implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * plugin cmd
     */
    private java.lang.String _name;

    /**
     * plugin display name
     */
    private java.lang.String _oprName;

    /**
     * extenal interface, must extends
     *  com.haiyan.plugin.OperationPlugin
     *  
     */
    private java.lang.String _className;

    /**
     * access key of button
     */
    private java.lang.String _accessKey;

    /**
     * style of button
     */
    private java.lang.String _style;

    /**
     * button of page
     */
    private haiyan.config.castorgen.types.PluginOperationHostPageType _hostPage = haiyan.config.castorgen.types.PluginOperationHostPageType.valueOf("query");

    /**
     * toolbar level
     */
    private int _tblevel = 1;

    /**
     * keeps track of state for field: _tblevel
     */
    private boolean _has_tblevel;

    /**
     * toolbar icon
     */
    private java.lang.String _img = "other";

    /**
     * web action
     */
    private java.lang.String _action;

    /**
     * has unvisible component
     */
    private boolean _isNoVisibleContent = false;

    /**
     * keeps track of state for field: _isNoVisibleContent
     */
    private boolean _has_isNoVisibleContent;

    /**
     * internal content storage
     */
    private java.lang.String _content = "";


      //----------------/
     //- Constructors -/
    //----------------/

    public PluginOperation() {
        super();
        setHostPage(haiyan.config.castorgen.types.PluginOperationHostPageType.valueOf("query"));
        setImg("other");
        setContent("");
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteIsNoVisibleContent(
    ) {
        this._has_isNoVisibleContent= false;
    }

    /**
     */
    public void deleteTblevel(
    ) {
        this._has_tblevel= false;
    }

    /**
     * Returns the value of field 'accessKey'. The field
     * 'accessKey' has the following description: access key of
     * button
     * 
     * @return the value of field 'AccessKey'.
     */
    public java.lang.String getAccessKey(
    ) {
        return this._accessKey;
    }

    /**
     * Returns the value of field 'action'. The field 'action' has
     * the following description: web action
     * 
     * @return the value of field 'Action'.
     */
    public java.lang.String getAction(
    ) {
        return this._action;
    }

    /**
     * Returns the value of field 'className'. The field
     * 'className' has the following description: extenal
     * interface, must extends
     *  com.haiyan.plugin.OperationPlugin
     *  
     * 
     * @return the value of field 'ClassName'.
     */
    public java.lang.String getClassName(
    ) {
        return this._className;
    }

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
     * has the following description: button of page
     * 
     * @return the value of field 'HostPage'.
     */
    public haiyan.config.castorgen.types.PluginOperationHostPageType getHostPage(
    ) {
        return this._hostPage;
    }

    /**
     * Returns the value of field 'img'. The field 'img' has the
     * following description: toolbar icon
     * 
     * @return the value of field 'Img'.
     */
    public java.lang.String getImg(
    ) {
        return this._img;
    }

    /**
     * Returns the value of field 'isNoVisibleContent'. The field
     * 'isNoVisibleContent' has the following description: has
     * unvisible component
     * 
     * @return the value of field 'IsNoVisibleContent'.
     */
    public boolean getIsNoVisibleContent(
    ) {
        return this._isNoVisibleContent;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: plugin cmd
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'oprName'. The field 'oprName'
     * has the following description: plugin display name
     * 
     * @return the value of field 'OprName'.
     */
    public java.lang.String getOprName(
    ) {
        return this._oprName;
    }

    /**
     * Returns the value of field 'style'. The field 'style' has
     * the following description: style of button
     * 
     * @return the value of field 'Style'.
     */
    public java.lang.String getStyle(
    ) {
        return this._style;
    }

    /**
     * Returns the value of field 'tblevel'. The field 'tblevel'
     * has the following description: toolbar level
     * 
     * @return the value of field 'Tblevel'.
     */
    public int getTblevel(
    ) {
        return this._tblevel;
    }

    /**
     * Method hasIsNoVisibleContent.
     * 
     * @return true if at least one IsNoVisibleContent has been adde
     */
    public boolean hasIsNoVisibleContent(
    ) {
        return this._has_isNoVisibleContent;
    }

    /**
     * Method hasTblevel.
     * 
     * @return true if at least one Tblevel has been added
     */
    public boolean hasTblevel(
    ) {
        return this._has_tblevel;
    }

    /**
     * Returns the value of field 'isNoVisibleContent'. The field
     * 'isNoVisibleContent' has the following description: has
     * unvisible component
     * 
     * @return the value of field 'IsNoVisibleContent'.
     */
    public boolean isIsNoVisibleContent(
    ) {
        return this._isNoVisibleContent;
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
     * Sets the value of field 'accessKey'. The field 'accessKey'
     * has the following description: access key of button
     * 
     * @param accessKey the value of field 'accessKey'.
     */
    public void setAccessKey(
            final java.lang.String accessKey) {
        this._accessKey = accessKey;
    }

    /**
     * Sets the value of field 'action'. The field 'action' has the
     * following description: web action
     * 
     * @param action the value of field 'action'.
     */
    public void setAction(
            final java.lang.String action) {
        this._action = action;
    }

    /**
     * Sets the value of field 'className'. The field 'className'
     * has the following description: extenal interface, must
     * extends
     *  com.haiyan.plugin.OperationPlugin
     *  
     * 
     * @param className the value of field 'className'.
     */
    public void setClassName(
            final java.lang.String className) {
        this._className = className;
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
     * the following description: button of page
     * 
     * @param hostPage the value of field 'hostPage'.
     */
    public void setHostPage(
            final haiyan.config.castorgen.types.PluginOperationHostPageType hostPage) {
        this._hostPage = hostPage;
    }

    /**
     * Sets the value of field 'img'. The field 'img' has the
     * following description: toolbar icon
     * 
     * @param img the value of field 'img'.
     */
    public void setImg(
            final java.lang.String img) {
        this._img = img;
    }

    /**
     * Sets the value of field 'isNoVisibleContent'. The field
     * 'isNoVisibleContent' has the following description: has
     * unvisible component
     * 
     * @param isNoVisibleContent the value of field
     * 'isNoVisibleContent'.
     */
    public void setIsNoVisibleContent(
            final boolean isNoVisibleContent) {
        this._isNoVisibleContent = isNoVisibleContent;
        this._has_isNoVisibleContent = true;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: plugin cmd
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'oprName'. The field 'oprName' has
     * the following description: plugin display name
     * 
     * @param oprName the value of field 'oprName'.
     */
    public void setOprName(
            final java.lang.String oprName) {
        this._oprName = oprName;
    }

    /**
     * Sets the value of field 'style'. The field 'style' has the
     * following description: style of button
     * 
     * @param style the value of field 'style'.
     */
    public void setStyle(
            final java.lang.String style) {
        this._style = style;
    }

    /**
     * Sets the value of field 'tblevel'. The field 'tblevel' has
     * the following description: toolbar level
     * 
     * @param tblevel the value of field 'tblevel'.
     */
    public void setTblevel(
            final int tblevel) {
        this._tblevel = tblevel;
        this._has_tblevel = true;
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
     * haiyan.config.castorgen.PluginOperation
     */
    public static haiyan.config.castorgen.PluginOperation unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.PluginOperation) Unmarshaller.unmarshal(haiyan.config.castorgen.PluginOperation.class, reader);
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
