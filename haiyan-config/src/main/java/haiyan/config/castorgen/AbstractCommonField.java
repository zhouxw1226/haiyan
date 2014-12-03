/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen;

/**
 * abstract common field
 * 
 * @version $Revision$ $Date$
 */
public abstract class AbstractCommonField implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * field name
     */
    private java.lang.String _name;

    /**
     * ui name
     */
    private java.lang.String _uiname;

    /**
     * field description
     */
    private java.lang.String _description;

    /**
     * validator
     */
    private java.lang.String _validator;

    /**
     * validatorText
     */
    private java.lang.String _validatorText;

    /**
     * readonly
     */
    private boolean _readOnly = false;

    /**
     * keeps track of state for field: _readOnly
     */
    private boolean _has_readOnly;

    /**
     * parameter of common
     */
    private java.lang.String _parameter;

    /**
     * if true it's visual
     */
    private boolean _visual = false;

    /**
     * keeps track of state for field: _visual
     */
    private boolean _has_visual;

    /**
     * dsn
     */
    private java.lang.String _dsn;

    /**
     * common option
     */
    private java.util.Vector _optionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public AbstractCommonField() {
        super();
        this._optionList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vOption
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addOption(
            final haiyan.config.castorgen.Option vOption)
    throws java.lang.IndexOutOfBoundsException {
        this._optionList.addElement(vOption);
    }

    /**
     * 
     * 
     * @param index
     * @param vOption
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addOption(
            final int index,
            final haiyan.config.castorgen.Option vOption)
    throws java.lang.IndexOutOfBoundsException {
        this._optionList.add(index, vOption);
    }

    /**
     */
    public void deleteReadOnly(
    ) {
        this._has_readOnly= false;
    }

    /**
     */
    public void deleteVisual(
    ) {
        this._has_visual= false;
    }

    /**
     * Method enumerateOption.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.Option elements
     */
    public java.util.Enumeration enumerateOption(
    ) {
        return this._optionList.elements();
    }

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: field
     * description
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'dsn'. The field 'dsn' has the
     * following description: dsn
     * 
     * @return the value of field 'Dsn'.
     */
    public java.lang.String getDsn(
    ) {
        return this._dsn;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: field name
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Method getOption.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Option at
     * the given index
     */
    public haiyan.config.castorgen.Option getOption(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._optionList.size()) {
            throw new IndexOutOfBoundsException("getOption: Index value '" + index + "' not in range [0.." + (this._optionList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Option) _optionList.get(index);
    }

    /**
     * Method getOption.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Option[] getOption(
    ) {
        haiyan.config.castorgen.Option[] array = new haiyan.config.castorgen.Option[0];
        return (haiyan.config.castorgen.Option[]) this._optionList.toArray(array);
    }

    /**
     * Method getOptionCount.
     * 
     * @return the size of this collection
     */
    public int getOptionCount(
    ) {
        return this._optionList.size();
    }

    /**
     * Returns the value of field 'parameter'. The field
     * 'parameter' has the following description: parameter of
     * common
     * 
     * @return the value of field 'Parameter'.
     */
    public java.lang.String getParameter(
    ) {
        return this._parameter;
    }

    /**
     * Returns the value of field 'readOnly'. The field 'readOnly'
     * has the following description: readonly
     * 
     * @return the value of field 'ReadOnly'.
     */
    public boolean getReadOnly(
    ) {
        return this._readOnly;
    }

    /**
     * Returns the value of field 'uiname'. The field 'uiname' has
     * the following description: ui name
     * 
     * @return the value of field 'Uiname'.
     */
    public java.lang.String getUiname(
    ) {
        return this._uiname;
    }

    /**
     * Returns the value of field 'validator'. The field
     * 'validator' has the following description: validator
     * 
     * @return the value of field 'Validator'.
     */
    public java.lang.String getValidator(
    ) {
        return this._validator;
    }

    /**
     * Returns the value of field 'validatorText'. The field
     * 'validatorText' has the following description: validatorText
     * 
     * @return the value of field 'ValidatorText'.
     */
    public java.lang.String getValidatorText(
    ) {
        return this._validatorText;
    }

    /**
     * Returns the value of field 'visual'. The field 'visual' has
     * the following description: if true it's visual
     * 
     * @return the value of field 'Visual'.
     */
    public boolean getVisual(
    ) {
        return this._visual;
    }

    /**
     * Method hasReadOnly.
     * 
     * @return true if at least one ReadOnly has been added
     */
    public boolean hasReadOnly(
    ) {
        return this._has_readOnly;
    }

    /**
     * Method hasVisual.
     * 
     * @return true if at least one Visual has been added
     */
    public boolean hasVisual(
    ) {
        return this._has_visual;
    }

    /**
     * Returns the value of field 'readOnly'. The field 'readOnly'
     * has the following description: readonly
     * 
     * @return the value of field 'ReadOnly'.
     */
    public boolean isReadOnly(
    ) {
        return this._readOnly;
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
     * Returns the value of field 'visual'. The field 'visual' has
     * the following description: if true it's visual
     * 
     * @return the value of field 'Visual'.
     */
    public boolean isVisual(
    ) {
        return this._visual;
    }

    /**
     */
    public void removeAllOption(
    ) {
        this._optionList.clear();
    }

    /**
     * Method removeOption.
     * 
     * @param vOption
     * @return true if the object was removed from the collection.
     */
    public boolean removeOption(
            final haiyan.config.castorgen.Option vOption) {
        boolean removed = _optionList.remove(vOption);
        return removed;
    }

    /**
     * Method removeOptionAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Option removeOptionAt(
            final int index) {
        java.lang.Object obj = this._optionList.remove(index);
        return (haiyan.config.castorgen.Option) obj;
    }

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: field
     * description
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'dsn'. The field 'dsn' has the
     * following description: dsn
     * 
     * @param dsn the value of field 'dsn'.
     */
    public void setDsn(
            final java.lang.String dsn) {
        this._dsn = dsn;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: field name
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * 
     * 
     * @param index
     * @param vOption
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setOption(
            final int index,
            final haiyan.config.castorgen.Option vOption)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._optionList.size()) {
            throw new IndexOutOfBoundsException("setOption: Index value '" + index + "' not in range [0.." + (this._optionList.size() - 1) + "]");
        }
        
        this._optionList.set(index, vOption);
    }

    /**
     * 
     * 
     * @param vOptionArray
     */
    public void setOption(
            final haiyan.config.castorgen.Option[] vOptionArray) {
        //-- copy array
        _optionList.clear();
        
        for (int i = 0; i < vOptionArray.length; i++) {
                this._optionList.add(vOptionArray[i]);
        }
    }

    /**
     * Sets the value of field 'parameter'. The field 'parameter'
     * has the following description: parameter of common
     * 
     * @param parameter the value of field 'parameter'.
     */
    public void setParameter(
            final java.lang.String parameter) {
        this._parameter = parameter;
    }

    /**
     * Sets the value of field 'readOnly'. The field 'readOnly' has
     * the following description: readonly
     * 
     * @param readOnly the value of field 'readOnly'.
     */
    public void setReadOnly(
            final boolean readOnly) {
        this._readOnly = readOnly;
        this._has_readOnly = true;
    }

    /**
     * Sets the value of field 'uiname'. The field 'uiname' has the
     * following description: ui name
     * 
     * @param uiname the value of field 'uiname'.
     */
    public void setUiname(
            final java.lang.String uiname) {
        this._uiname = uiname;
    }

    /**
     * Sets the value of field 'validator'. The field 'validator'
     * has the following description: validator
     * 
     * @param validator the value of field 'validator'.
     */
    public void setValidator(
            final java.lang.String validator) {
        this._validator = validator;
    }

    /**
     * Sets the value of field 'validatorText'. The field
     * 'validatorText' has the following description: validatorText
     * 
     * @param validatorText the value of field 'validatorText'.
     */
    public void setValidatorText(
            final java.lang.String validatorText) {
        this._validatorText = validatorText;
    }

    /**
     * Sets the value of field 'visual'. The field 'visual' has the
     * following description: if true it's visual
     * 
     * @param visual the value of field 'visual'.
     */
    public void setVisual(
            final boolean visual) {
        this._visual = visual;
        this._has_visual = true;
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
