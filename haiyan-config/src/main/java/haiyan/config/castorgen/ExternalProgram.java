/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen;

/**
 * abstract external
 * 
 * @version $Revision$ $Date$
 */
public abstract class ExternalProgram implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * abstract external class name
     */
    private java.lang.String _className;

    /**
     * abstract external method name
     */
    private java.lang.String _methodName;

    /**
     * abstract external parameter
     */
    private java.lang.String _parameter;

    /**
     * internal content storage
     */
    private java.lang.String _content = "";


      //----------------/
     //- Constructors -/
    //----------------/

    public ExternalProgram() {
        super();
        setContent("");
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'className'. The field
     * 'className' has the following description: abstract external
     * class name
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
     * Returns the value of field 'methodName'. The field
     * 'methodName' has the following description: abstract
     * external method name
     * 
     * @return the value of field 'MethodName'.
     */
    public java.lang.String getMethodName(
    ) {
        return this._methodName;
    }

    /**
     * Returns the value of field 'parameter'. The field
     * 'parameter' has the following description: abstract external
     * parameter
     * 
     * @return the value of field 'Parameter'.
     */
    public java.lang.String getParameter(
    ) {
        return this._parameter;
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
     * Sets the value of field 'className'. The field 'className'
     * has the following description: abstract external class name
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
     * Sets the value of field 'methodName'. The field 'methodName'
     * has the following description: abstract external method name
     * 
     * @param methodName the value of field 'methodName'.
     */
    public void setMethodName(
            final java.lang.String methodName) {
        this._methodName = methodName;
    }

    /**
     * Sets the value of field 'parameter'. The field 'parameter'
     * has the following description: abstract external parameter
     * 
     * @param parameter the value of field 'parameter'.
     */
    public void setParameter(
            final java.lang.String parameter) {
        this._parameter = parameter;
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
