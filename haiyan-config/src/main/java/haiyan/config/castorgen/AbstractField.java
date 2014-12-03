/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen;

/**
 * abstract db field
 * 
 * @version $Revision$ $Date$
 */
public abstract class AbstractField extends haiyan.config.castorgen.AbstractCommonField 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * allow null
     */
    private boolean _nullAllowed = true;

    /**
     * keeps track of state for field: _nullAllowed
     */
    private boolean _has_nullAllowed;

    /**
     * default value
     */
    private java.lang.String _defaultValue;

    /**
     * field db style
     */
    private java.lang.String _dataStyle;

    /**
     * field db length
     */
    private int _length;

    /**
     * keeps track of state for field: _length
     */
    private boolean _has_length;

    /**
     * max digital
     */
    private int _maxFractionDigit = 2;

    /**
     * keeps track of state for field: _maxFractionDigit
     */
    private boolean _has_maxFractionDigit;

    /**
     * min digital
     */
    private int _minFractionDigit = 0;

    /**
     * keeps track of state for field: _minFractionDigit
     */
    private boolean _has_minFractionDigit;

    /**
     * field java type
     */
    private haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType _javaType;


      //----------------/
     //- Constructors -/
    //----------------/

    public AbstractField() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteLength(
    ) {
        this._has_length= false;
    }

    /**
     */
    public void deleteMaxFractionDigit(
    ) {
        this._has_maxFractionDigit= false;
    }

    /**
     */
    public void deleteMinFractionDigit(
    ) {
        this._has_minFractionDigit= false;
    }

    /**
     */
    public void deleteNullAllowed(
    ) {
        this._has_nullAllowed= false;
    }

    /**
     * Returns the value of field 'dataStyle'. The field
     * 'dataStyle' has the following description: field db style
     * 
     * @return the value of field 'DataStyle'.
     */
    public java.lang.String getDataStyle(
    ) {
        return this._dataStyle;
    }

    /**
     * Returns the value of field 'defaultValue'. The field
     * 'defaultValue' has the following description: default value
     * 
     * @return the value of field 'DefaultValue'.
     */
    public java.lang.String getDefaultValue(
    ) {
        return this._defaultValue;
    }

    /**
     * Returns the value of field 'javaType'. The field 'javaType'
     * has the following description: field java type
     * 
     * @return the value of field 'JavaType'.
     */
    public haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType getJavaType(
    ) {
        return this._javaType;
    }

    /**
     * Returns the value of field 'length'. The field 'length' has
     * the following description: field db length
     * 
     * @return the value of field 'Length'.
     */
    public int getLength(
    ) {
        return this._length;
    }

    /**
     * Returns the value of field 'maxFractionDigit'. The field
     * 'maxFractionDigit' has the following description: max
     * digital
     * 
     * @return the value of field 'MaxFractionDigit'.
     */
    public int getMaxFractionDigit(
    ) {
        return this._maxFractionDigit;
    }

    /**
     * Returns the value of field 'minFractionDigit'. The field
     * 'minFractionDigit' has the following description: min
     * digital
     * 
     * @return the value of field 'MinFractionDigit'.
     */
    public int getMinFractionDigit(
    ) {
        return this._minFractionDigit;
    }

    /**
     * Returns the value of field 'nullAllowed'. The field
     * 'nullAllowed' has the following description: allow null
     * 
     * @return the value of field 'NullAllowed'.
     */
    public boolean getNullAllowed(
    ) {
        return this._nullAllowed;
    }

    /**
     * Method hasLength.
     * 
     * @return true if at least one Length has been added
     */
    public boolean hasLength(
    ) {
        return this._has_length;
    }

    /**
     * Method hasMaxFractionDigit.
     * 
     * @return true if at least one MaxFractionDigit has been added
     */
    public boolean hasMaxFractionDigit(
    ) {
        return this._has_maxFractionDigit;
    }

    /**
     * Method hasMinFractionDigit.
     * 
     * @return true if at least one MinFractionDigit has been added
     */
    public boolean hasMinFractionDigit(
    ) {
        return this._has_minFractionDigit;
    }

    /**
     * Method hasNullAllowed.
     * 
     * @return true if at least one NullAllowed has been added
     */
    public boolean hasNullAllowed(
    ) {
        return this._has_nullAllowed;
    }

    /**
     * Returns the value of field 'nullAllowed'. The field
     * 'nullAllowed' has the following description: allow null
     * 
     * @return the value of field 'NullAllowed'.
     */
    public boolean isNullAllowed(
    ) {
        return this._nullAllowed;
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
     * Sets the value of field 'dataStyle'. The field 'dataStyle'
     * has the following description: field db style
     * 
     * @param dataStyle the value of field 'dataStyle'.
     */
    public void setDataStyle(
            final java.lang.String dataStyle) {
        this._dataStyle = dataStyle;
    }

    /**
     * Sets the value of field 'defaultValue'. The field
     * 'defaultValue' has the following description: default value
     * 
     * @param defaultValue the value of field 'defaultValue'.
     */
    public void setDefaultValue(
            final java.lang.String defaultValue) {
        this._defaultValue = defaultValue;
    }

    /**
     * Sets the value of field 'javaType'. The field 'javaType' has
     * the following description: field java type
     * 
     * @param javaType the value of field 'javaType'.
     */
    public void setJavaType(
            final haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType javaType) {
        this._javaType = javaType;
    }

    /**
     * Sets the value of field 'length'. The field 'length' has the
     * following description: field db length
     * 
     * @param length the value of field 'length'.
     */
    public void setLength(
            final int length) {
        this._length = length;
        this._has_length = true;
    }

    /**
     * Sets the value of field 'maxFractionDigit'. The field
     * 'maxFractionDigit' has the following description: max
     * digital
     * 
     * @param maxFractionDigit the value of field 'maxFractionDigit'
     */
    public void setMaxFractionDigit(
            final int maxFractionDigit) {
        this._maxFractionDigit = maxFractionDigit;
        this._has_maxFractionDigit = true;
    }

    /**
     * Sets the value of field 'minFractionDigit'. The field
     * 'minFractionDigit' has the following description: min
     * digital
     * 
     * @param minFractionDigit the value of field 'minFractionDigit'
     */
    public void setMinFractionDigit(
            final int minFractionDigit) {
        this._minFractionDigit = minFractionDigit;
        this._has_minFractionDigit = true;
    }

    /**
     * Sets the value of field 'nullAllowed'. The field
     * 'nullAllowed' has the following description: allow null
     * 
     * @param nullAllowed the value of field 'nullAllowed'.
     */
    public void setNullAllowed(
            final boolean nullAllowed) {
        this._nullAllowed = nullAllowed;
        this._has_nullAllowed = true;
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
