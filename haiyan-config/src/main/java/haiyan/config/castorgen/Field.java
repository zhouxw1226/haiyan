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
 * table's db field
 * 
 * @version $Revision$ $Date$
 */
public class Field extends haiyan.config.castorgen.AbstractField 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * updateable
     */
    private boolean _updateable = true;

    /**
     * keeps track of state for field: _updateable
     */
    private boolean _has_updateable;

    /**
     * createable
     */
    private boolean _createable = true;

    /**
     * keeps track of state for field: _createable
     */
    private boolean _has_createable;

    /**
     * lazyLoad
     */
    private boolean _lazyLoad = false;

    /**
     * keeps track of state for field: _lazyLoad
     */
    private boolean _has_lazyLoad;

    /**
     * loadType
     */
    private java.lang.String _loadType;

    /**
     * @deprecated sumable
     */
    private boolean _sumable = false;

    /**
     * keeps track of state for field: _sumable
     */
    private boolean _has_sumable;

    /**
     * numbergrouping, example:10,332.01
     *  
     */
    private boolean _numberGrouping = false;

    /**
     * keeps track of state for field: _numberGrouping
     */
    private boolean _has_numberGrouping;

    /**
     * multipleSelect
     */
    private boolean _multipleSelect = false;

    /**
     * keeps track of state for field: _multipleSelect
     */
    private boolean _has_multipleSelect;

    /**
     * query fields of this field, diliver by " ",
     *  must
     *  mapping with restrictiveFields
     *  
     */
    private java.util.Vector _conditionFields;

    /**
     * field's values to restrict this field value,
     *  diliver by " "
     *  
     */
    private java.util.Vector _restrictiveFields;

    /**
     * associated fields, diliver by ","
     *  
     */
    private java.lang.String _associatedFields;

    /**
     * display reference field, diliver by ","
     *  
     */
    private java.lang.String _displayReferenceField;

    /**
     * quick query condition field, diliver by ","
     *  
     */
    private java.lang.String _quickQueryConditionField;

    /**
     * reference field
     */
    private java.lang.String _referenceField;

    /**
     * reference table
     */
    private java.lang.String _referenceTable;

    /**
     * visual tables, diliver by " "
     *  
     */
    private java.util.Vector _visualTable;

    /**
     * mapping table name
     */
    private java.lang.String _mappingTable;

    /**
     * one2one table name
     */
    private java.lang.String _one2oneTable;

    /**
     * one2one field name
     */
    private java.lang.String _one2oneField;

    /**
     * sub query sql
     */
    private java.lang.String _subQuerySQL;

    /**
     * Field _queryCondition.
     */
    private haiyan.config.castorgen.QueryCondition _queryCondition;


      //----------------/
     //- Constructors -/
    //----------------/

    public Field() {
        super();
        this._conditionFields = new java.util.Vector();
        this._restrictiveFields = new java.util.Vector();
        this._visualTable = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vConditionFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addConditionFields(
            final java.lang.String vConditionFields)
    throws java.lang.IndexOutOfBoundsException {
        this._conditionFields.addElement(vConditionFields);
    }

    /**
     * 
     * 
     * @param index
     * @param vConditionFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addConditionFields(
            final int index,
            final java.lang.String vConditionFields)
    throws java.lang.IndexOutOfBoundsException {
        this._conditionFields.add(index, vConditionFields);
    }

    /**
     * 
     * 
     * @param vRestrictiveFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRestrictiveFields(
            final java.lang.String vRestrictiveFields)
    throws java.lang.IndexOutOfBoundsException {
        this._restrictiveFields.addElement(vRestrictiveFields);
    }

    /**
     * 
     * 
     * @param index
     * @param vRestrictiveFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRestrictiveFields(
            final int index,
            final java.lang.String vRestrictiveFields)
    throws java.lang.IndexOutOfBoundsException {
        this._restrictiveFields.add(index, vRestrictiveFields);
    }

    /**
     * 
     * 
     * @param vVisualTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addVisualTable(
            final java.lang.String vVisualTable)
    throws java.lang.IndexOutOfBoundsException {
        this._visualTable.addElement(vVisualTable);
    }

    /**
     * 
     * 
     * @param index
     * @param vVisualTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addVisualTable(
            final int index,
            final java.lang.String vVisualTable)
    throws java.lang.IndexOutOfBoundsException {
        this._visualTable.add(index, vVisualTable);
    }

    /**
     */
    public void deleteCreateable(
    ) {
        this._has_createable= false;
    }

    /**
     */
    public void deleteLazyLoad(
    ) {
        this._has_lazyLoad= false;
    }

    /**
     */
    public void deleteMultipleSelect(
    ) {
        this._has_multipleSelect= false;
    }

    /**
     */
    public void deleteNumberGrouping(
    ) {
        this._has_numberGrouping= false;
    }

    /**
     */
    public void deleteSumable(
    ) {
        this._has_sumable= false;
    }

    /**
     */
    public void deleteUpdateable(
    ) {
        this._has_updateable= false;
    }

    /**
     * Method enumerateConditionFields.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration enumerateConditionFields(
    ) {
        return this._conditionFields.elements();
    }

    /**
     * Method enumerateRestrictiveFields.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration enumerateRestrictiveFields(
    ) {
        return this._restrictiveFields.elements();
    }

    /**
     * Method enumerateVisualTable.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration enumerateVisualTable(
    ) {
        return this._visualTable.elements();
    }

    /**
     * Returns the value of field 'associatedFields'. The field
     * 'associatedFields' has the following description: associated
     * fields, diliver by ","
     *  
     * 
     * @return the value of field 'AssociatedFields'.
     */
    public java.lang.String getAssociatedFields(
    ) {
        return this._associatedFields;
    }

    /**
     * Method getConditionFields.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getConditionFields(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._conditionFields.size()) {
            throw new IndexOutOfBoundsException("getConditionFields: Index value '" + index + "' not in range [0.." + (this._conditionFields.size() - 1) + "]");
        }
        
        return (java.lang.String) _conditionFields.get(index);
    }

    /**
     * Method getConditionFields.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getConditionFields(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._conditionFields.toArray(array);
    }

    /**
     * Method getConditionFieldsCount.
     * 
     * @return the size of this collection
     */
    public int getConditionFieldsCount(
    ) {
        return this._conditionFields.size();
    }

    /**
     * Returns the value of field 'createable'. The field
     * 'createable' has the following description: createable
     * 
     * @return the value of field 'Createable'.
     */
    public boolean getCreateable(
    ) {
        return this._createable;
    }

    /**
     * Returns the value of field 'displayReferenceField'. The
     * field 'displayReferenceField' has the following description:
     * display reference field, diliver by ","
     *  
     * 
     * @return the value of field 'DisplayReferenceField'.
     */
    public java.lang.String getDisplayReferenceField(
    ) {
        return this._displayReferenceField;
    }

    /**
     * Returns the value of field 'lazyLoad'. The field 'lazyLoad'
     * has the following description: lazyLoad
     * 
     * @return the value of field 'LazyLoad'.
     */
    public boolean getLazyLoad(
    ) {
        return this._lazyLoad;
    }

    /**
     * Returns the value of field 'loadType'. The field 'loadType'
     * has the following description: loadType
     * 
     * @return the value of field 'LoadType'.
     */
    public java.lang.String getLoadType(
    ) {
        return this._loadType;
    }

    /**
     * Returns the value of field 'mappingTable'. The field
     * 'mappingTable' has the following description: mapping table
     * name
     * 
     * @return the value of field 'MappingTable'.
     */
    public java.lang.String getMappingTable(
    ) {
        return this._mappingTable;
    }

    /**
     * Returns the value of field 'multipleSelect'. The field
     * 'multipleSelect' has the following description:
     * multipleSelect
     * 
     * @return the value of field 'MultipleSelect'.
     */
    public boolean getMultipleSelect(
    ) {
        return this._multipleSelect;
    }

    /**
     * Returns the value of field 'numberGrouping'. The field
     * 'numberGrouping' has the following description:
     * numbergrouping, example:10,332.01
     *  
     * 
     * @return the value of field 'NumberGrouping'.
     */
    public boolean getNumberGrouping(
    ) {
        return this._numberGrouping;
    }

    /**
     * Returns the value of field 'one2oneField'. The field
     * 'one2oneField' has the following description: one2one field
     * name
     * 
     * @return the value of field 'One2oneField'.
     */
    public java.lang.String getOne2oneField(
    ) {
        return this._one2oneField;
    }

    /**
     * Returns the value of field 'one2oneTable'. The field
     * 'one2oneTable' has the following description: one2one table
     * name
     * 
     * @return the value of field 'One2oneTable'.
     */
    public java.lang.String getOne2oneTable(
    ) {
        return this._one2oneTable;
    }

    /**
     * Returns the value of field 'queryCondition'.
     * 
     * @return the value of field 'QueryCondition'.
     */
    public haiyan.config.castorgen.QueryCondition getQueryCondition(
    ) {
        return this._queryCondition;
    }

    /**
     * Returns the value of field 'quickQueryConditionField'. The
     * field 'quickQueryConditionField' has the following
     * description: quick query condition field, diliver by ","
     *  
     * 
     * @return the value of field 'QuickQueryConditionField'.
     */
    public java.lang.String getQuickQueryConditionField(
    ) {
        return this._quickQueryConditionField;
    }

    /**
     * Returns the value of field 'referenceField'. The field
     * 'referenceField' has the following description: reference
     * field
     * 
     * @return the value of field 'ReferenceField'.
     */
    public java.lang.String getReferenceField(
    ) {
        return this._referenceField;
    }

    /**
     * Returns the value of field 'referenceTable'. The field
     * 'referenceTable' has the following description: reference
     * table
     * 
     * @return the value of field 'ReferenceTable'.
     */
    public java.lang.String getReferenceTable(
    ) {
        return this._referenceTable;
    }

    /**
     * Method getRestrictiveFields.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getRestrictiveFields(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._restrictiveFields.size()) {
            throw new IndexOutOfBoundsException("getRestrictiveFields: Index value '" + index + "' not in range [0.." + (this._restrictiveFields.size() - 1) + "]");
        }
        
        return (java.lang.String) _restrictiveFields.get(index);
    }

    /**
     * Method getRestrictiveFields.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getRestrictiveFields(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._restrictiveFields.toArray(array);
    }

    /**
     * Method getRestrictiveFieldsCount.
     * 
     * @return the size of this collection
     */
    public int getRestrictiveFieldsCount(
    ) {
        return this._restrictiveFields.size();
    }

    /**
     * Returns the value of field 'subQuerySQL'. The field
     * 'subQuerySQL' has the following description: sub query sql
     * 
     * @return the value of field 'SubQuerySQL'.
     */
    public java.lang.String getSubQuerySQL(
    ) {
        return this._subQuerySQL;
    }

    /**
     * Returns the value of field 'sumable'. The field 'sumable'
     * has the following description: 
     * 
     * @deprecated sumable
     * 
     * @return the value of field 'Sumable'.
     */
    public boolean getSumable(
    ) {
        return this._sumable;
    }

    /**
     * Returns the value of field 'updateable'. The field
     * 'updateable' has the following description: updateable
     * 
     * @return the value of field 'Updateable'.
     */
    public boolean getUpdateable(
    ) {
        return this._updateable;
    }

    /**
     * Method getVisualTable.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getVisualTable(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._visualTable.size()) {
            throw new IndexOutOfBoundsException("getVisualTable: Index value '" + index + "' not in range [0.." + (this._visualTable.size() - 1) + "]");
        }
        
        return (java.lang.String) _visualTable.get(index);
    }

    /**
     * Method getVisualTable.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getVisualTable(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._visualTable.toArray(array);
    }

    /**
     * Method getVisualTableCount.
     * 
     * @return the size of this collection
     */
    public int getVisualTableCount(
    ) {
        return this._visualTable.size();
    }

    /**
     * Method hasCreateable.
     * 
     * @return true if at least one Createable has been added
     */
    public boolean hasCreateable(
    ) {
        return this._has_createable;
    }

    /**
     * Method hasLazyLoad.
     * 
     * @return true if at least one LazyLoad has been added
     */
    public boolean hasLazyLoad(
    ) {
        return this._has_lazyLoad;
    }

    /**
     * Method hasMultipleSelect.
     * 
     * @return true if at least one MultipleSelect has been added
     */
    public boolean hasMultipleSelect(
    ) {
        return this._has_multipleSelect;
    }

    /**
     * Method hasNumberGrouping.
     * 
     * @return true if at least one NumberGrouping has been added
     */
    public boolean hasNumberGrouping(
    ) {
        return this._has_numberGrouping;
    }

    /**
     * Method hasSumable.
     * 
     * @return true if at least one Sumable has been added
     */
    public boolean hasSumable(
    ) {
        return this._has_sumable;
    }

    /**
     * Method hasUpdateable.
     * 
     * @return true if at least one Updateable has been added
     */
    public boolean hasUpdateable(
    ) {
        return this._has_updateable;
    }

    /**
     * Returns the value of field 'createable'. The field
     * 'createable' has the following description: createable
     * 
     * @return the value of field 'Createable'.
     */
    public boolean isCreateable(
    ) {
        return this._createable;
    }

    /**
     * Returns the value of field 'lazyLoad'. The field 'lazyLoad'
     * has the following description: lazyLoad
     * 
     * @return the value of field 'LazyLoad'.
     */
    public boolean isLazyLoad(
    ) {
        return this._lazyLoad;
    }

    /**
     * Returns the value of field 'multipleSelect'. The field
     * 'multipleSelect' has the following description:
     * multipleSelect
     * 
     * @return the value of field 'MultipleSelect'.
     */
    public boolean isMultipleSelect(
    ) {
        return this._multipleSelect;
    }

    /**
     * Returns the value of field 'numberGrouping'. The field
     * 'numberGrouping' has the following description:
     * numbergrouping, example:10,332.01
     *  
     * 
     * @return the value of field 'NumberGrouping'.
     */
    public boolean isNumberGrouping(
    ) {
        return this._numberGrouping;
    }

    /**
     * Returns the value of field 'sumable'. The field 'sumable'
     * has the following description: 
     * 
     * @deprecated sumable
     * 
     * @return the value of field 'Sumable'.
     */
    public boolean isSumable(
    ) {
        return this._sumable;
    }

    /**
     * Returns the value of field 'updateable'. The field
     * 'updateable' has the following description: updateable
     * 
     * @return the value of field 'Updateable'.
     */
    public boolean isUpdateable(
    ) {
        return this._updateable;
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
     */
    public void removeAllConditionFields(
    ) {
        this._conditionFields.clear();
    }

    /**
     */
    public void removeAllRestrictiveFields(
    ) {
        this._restrictiveFields.clear();
    }

    /**
     */
    public void removeAllVisualTable(
    ) {
        this._visualTable.clear();
    }

    /**
     * Method removeConditionFields.
     * 
     * @param vConditionFields
     * @return true if the object was removed from the collection.
     */
    public boolean removeConditionFields(
            final java.lang.String vConditionFields) {
        boolean removed = _conditionFields.remove(vConditionFields);
        return removed;
    }

    /**
     * Method removeConditionFieldsAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeConditionFieldsAt(
            final int index) {
        java.lang.Object obj = this._conditionFields.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * Method removeRestrictiveFields.
     * 
     * @param vRestrictiveFields
     * @return true if the object was removed from the collection.
     */
    public boolean removeRestrictiveFields(
            final java.lang.String vRestrictiveFields) {
        boolean removed = _restrictiveFields.remove(vRestrictiveFields);
        return removed;
    }

    /**
     * Method removeRestrictiveFieldsAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeRestrictiveFieldsAt(
            final int index) {
        java.lang.Object obj = this._restrictiveFields.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * Method removeVisualTable.
     * 
     * @param vVisualTable
     * @return true if the object was removed from the collection.
     */
    public boolean removeVisualTable(
            final java.lang.String vVisualTable) {
        boolean removed = _visualTable.remove(vVisualTable);
        return removed;
    }

    /**
     * Method removeVisualTableAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeVisualTableAt(
            final int index) {
        java.lang.Object obj = this._visualTable.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * Sets the value of field 'associatedFields'. The field
     * 'associatedFields' has the following description: associated
     * fields, diliver by ","
     *  
     * 
     * @param associatedFields the value of field 'associatedFields'
     */
    public void setAssociatedFields(
            final java.lang.String associatedFields) {
        this._associatedFields = associatedFields;
    }

    /**
     * 
     * 
     * @param index
     * @param vConditionFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setConditionFields(
            final int index,
            final java.lang.String vConditionFields)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._conditionFields.size()) {
            throw new IndexOutOfBoundsException("setConditionFields: Index value '" + index + "' not in range [0.." + (this._conditionFields.size() - 1) + "]");
        }
        
        this._conditionFields.set(index, vConditionFields);
    }

    /**
     * 
     * 
     * @param vConditionFieldsArray
     */
    public void setConditionFields(
            final java.lang.String[] vConditionFieldsArray) {
        //-- copy array
        _conditionFields.clear();
        
        for (int i = 0; i < vConditionFieldsArray.length; i++) {
                this._conditionFields.add(vConditionFieldsArray[i]);
        }
    }

    /**
     * Sets the value of field 'createable'. The field 'createable'
     * has the following description: createable
     * 
     * @param createable the value of field 'createable'.
     */
    public void setCreateable(
            final boolean createable) {
        this._createable = createable;
        this._has_createable = true;
    }

    /**
     * Sets the value of field 'displayReferenceField'. The field
     * 'displayReferenceField' has the following description:
     * display reference field, diliver by ","
     *  
     * 
     * @param displayReferenceField the value of field
     * 'displayReferenceField'.
     */
    public void setDisplayReferenceField(
            final java.lang.String displayReferenceField) {
        this._displayReferenceField = displayReferenceField;
    }

    /**
     * Sets the value of field 'lazyLoad'. The field 'lazyLoad' has
     * the following description: lazyLoad
     * 
     * @param lazyLoad the value of field 'lazyLoad'.
     */
    public void setLazyLoad(
            final boolean lazyLoad) {
        this._lazyLoad = lazyLoad;
        this._has_lazyLoad = true;
    }

    /**
     * Sets the value of field 'loadType'. The field 'loadType' has
     * the following description: loadType
     * 
     * @param loadType the value of field 'loadType'.
     */
    public void setLoadType(
            final java.lang.String loadType) {
        this._loadType = loadType;
    }

    /**
     * Sets the value of field 'mappingTable'. The field
     * 'mappingTable' has the following description: mapping table
     * name
     * 
     * @param mappingTable the value of field 'mappingTable'.
     */
    public void setMappingTable(
            final java.lang.String mappingTable) {
        this._mappingTable = mappingTable;
    }

    /**
     * Sets the value of field 'multipleSelect'. The field
     * 'multipleSelect' has the following description:
     * multipleSelect
     * 
     * @param multipleSelect the value of field 'multipleSelect'.
     */
    public void setMultipleSelect(
            final boolean multipleSelect) {
        this._multipleSelect = multipleSelect;
        this._has_multipleSelect = true;
    }

    /**
     * Sets the value of field 'numberGrouping'. The field
     * 'numberGrouping' has the following description:
     * numbergrouping, example:10,332.01
     *  
     * 
     * @param numberGrouping the value of field 'numberGrouping'.
     */
    public void setNumberGrouping(
            final boolean numberGrouping) {
        this._numberGrouping = numberGrouping;
        this._has_numberGrouping = true;
    }

    /**
     * Sets the value of field 'one2oneField'. The field
     * 'one2oneField' has the following description: one2one field
     * name
     * 
     * @param one2oneField the value of field 'one2oneField'.
     */
    public void setOne2oneField(
            final java.lang.String one2oneField) {
        this._one2oneField = one2oneField;
    }

    /**
     * Sets the value of field 'one2oneTable'. The field
     * 'one2oneTable' has the following description: one2one table
     * name
     * 
     * @param one2oneTable the value of field 'one2oneTable'.
     */
    public void setOne2oneTable(
            final java.lang.String one2oneTable) {
        this._one2oneTable = one2oneTable;
    }

    /**
     * Sets the value of field 'queryCondition'.
     * 
     * @param queryCondition the value of field 'queryCondition'.
     */
    public void setQueryCondition(
            final haiyan.config.castorgen.QueryCondition queryCondition) {
        this._queryCondition = queryCondition;
    }

    /**
     * Sets the value of field 'quickQueryConditionField'. The
     * field 'quickQueryConditionField' has the following
     * description: quick query condition field, diliver by ","
     *  
     * 
     * @param quickQueryConditionField the value of field
     * 'quickQueryConditionField'.
     */
    public void setQuickQueryConditionField(
            final java.lang.String quickQueryConditionField) {
        this._quickQueryConditionField = quickQueryConditionField;
    }

    /**
     * Sets the value of field 'referenceField'. The field
     * 'referenceField' has the following description: reference
     * field
     * 
     * @param referenceField the value of field 'referenceField'.
     */
    public void setReferenceField(
            final java.lang.String referenceField) {
        this._referenceField = referenceField;
    }

    /**
     * Sets the value of field 'referenceTable'. The field
     * 'referenceTable' has the following description: reference
     * table
     * 
     * @param referenceTable the value of field 'referenceTable'.
     */
    public void setReferenceTable(
            final java.lang.String referenceTable) {
        this._referenceTable = referenceTable;
    }

    /**
     * 
     * 
     * @param index
     * @param vRestrictiveFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRestrictiveFields(
            final int index,
            final java.lang.String vRestrictiveFields)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._restrictiveFields.size()) {
            throw new IndexOutOfBoundsException("setRestrictiveFields: Index value '" + index + "' not in range [0.." + (this._restrictiveFields.size() - 1) + "]");
        }
        
        this._restrictiveFields.set(index, vRestrictiveFields);
    }

    /**
     * 
     * 
     * @param vRestrictiveFieldsArray
     */
    public void setRestrictiveFields(
            final java.lang.String[] vRestrictiveFieldsArray) {
        //-- copy array
        _restrictiveFields.clear();
        
        for (int i = 0; i < vRestrictiveFieldsArray.length; i++) {
                this._restrictiveFields.add(vRestrictiveFieldsArray[i]);
        }
    }

    /**
     * Sets the value of field 'subQuerySQL'. The field
     * 'subQuerySQL' has the following description: sub query sql
     * 
     * @param subQuerySQL the value of field 'subQuerySQL'.
     */
    public void setSubQuerySQL(
            final java.lang.String subQuerySQL) {
        this._subQuerySQL = subQuerySQL;
    }

    /**
     * Sets the value of field 'sumable'. The field 'sumable' has
     * the following description: 
     * 
     * @deprecated sumable
     * 
     * @param sumable the value of field 'sumable'.
     */
    public void setSumable(
            final boolean sumable) {
        this._sumable = sumable;
        this._has_sumable = true;
    }

    /**
     * Sets the value of field 'updateable'. The field 'updateable'
     * has the following description: updateable
     * 
     * @param updateable the value of field 'updateable'.
     */
    public void setUpdateable(
            final boolean updateable) {
        this._updateable = updateable;
        this._has_updateable = true;
    }

    /**
     * 
     * 
     * @param index
     * @param vVisualTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setVisualTable(
            final int index,
            final java.lang.String vVisualTable)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._visualTable.size()) {
            throw new IndexOutOfBoundsException("setVisualTable: Index value '" + index + "' not in range [0.." + (this._visualTable.size() - 1) + "]");
        }
        
        this._visualTable.set(index, vVisualTable);
    }

    /**
     * 
     * 
     * @param vVisualTableArray
     */
    public void setVisualTable(
            final java.lang.String[] vVisualTableArray) {
        //-- copy array
        _visualTable.clear();
        
        for (int i = 0; i < vVisualTableArray.length; i++) {
                this._visualTable.add(vVisualTableArray[i]);
        }
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
     * haiyan.config.castorgen.AbstractCommonField
     */
    public static haiyan.config.castorgen.AbstractCommonField unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.AbstractCommonField) Unmarshaller.unmarshal(haiyan.config.castorgen.Field.class, reader);
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
