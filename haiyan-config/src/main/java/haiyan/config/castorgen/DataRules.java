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
 * data rule
 * 
 * @version $Revision$ $Date$
 */
public class DataRules implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * data rules name
     */
    private java.lang.String _name;

    /**
     * data rules description
     */
    private java.lang.String _description;

    /**
     * srcTable
     */
    private java.lang.String _srcTable;

    /**
     * srcRefField
     */
    private java.lang.String _srcRefField;

    /**
     * srcExp
     */
    private java.lang.String _srcExp;

    /**
     * destTable
     */
    private java.lang.String _destTable;

    /**
     * destRefField
     */
    private java.lang.String _destRefField;

    /**
     * destExp
     */
    private java.lang.String _destExp;

    /**
     * field rule define
     */
    private java.util.Vector _ruleList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DataRules() {
        super();
        this._ruleList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vRule
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRule(
            final haiyan.config.castorgen.Rule vRule)
    throws java.lang.IndexOutOfBoundsException {
        this._ruleList.addElement(vRule);
    }

    /**
     * 
     * 
     * @param index
     * @param vRule
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRule(
            final int index,
            final haiyan.config.castorgen.Rule vRule)
    throws java.lang.IndexOutOfBoundsException {
        this._ruleList.add(index, vRule);
    }

    /**
     * Method enumerateRule.
     * 
     * @return an Enumeration over all haiyan.config.castorgen.Rule
     * elements
     */
    public java.util.Enumeration enumerateRule(
    ) {
        return this._ruleList.elements();
    }

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: data rules
     * description
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'destExp'. The field 'destExp'
     * has the following description: destExp
     * 
     * @return the value of field 'DestExp'.
     */
    public java.lang.String getDestExp(
    ) {
        return this._destExp;
    }

    /**
     * Returns the value of field 'destRefField'. The field
     * 'destRefField' has the following description: destRefField
     * 
     * @return the value of field 'DestRefField'.
     */
    public java.lang.String getDestRefField(
    ) {
        return this._destRefField;
    }

    /**
     * Returns the value of field 'destTable'. The field
     * 'destTable' has the following description: destTable
     * 
     * @return the value of field 'DestTable'.
     */
    public java.lang.String getDestTable(
    ) {
        return this._destTable;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: data rules name
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Method getRule.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Rule at the
     * given index
     */
    public haiyan.config.castorgen.Rule getRule(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._ruleList.size()) {
            throw new IndexOutOfBoundsException("getRule: Index value '" + index + "' not in range [0.." + (this._ruleList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Rule) _ruleList.get(index);
    }

    /**
     * Method getRule.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Rule[] getRule(
    ) {
        haiyan.config.castorgen.Rule[] array = new haiyan.config.castorgen.Rule[0];
        return (haiyan.config.castorgen.Rule[]) this._ruleList.toArray(array);
    }

    /**
     * Method getRuleCount.
     * 
     * @return the size of this collection
     */
    public int getRuleCount(
    ) {
        return this._ruleList.size();
    }

    /**
     * Returns the value of field 'srcExp'. The field 'srcExp' has
     * the following description: srcExp
     * 
     * @return the value of field 'SrcExp'.
     */
    public java.lang.String getSrcExp(
    ) {
        return this._srcExp;
    }

    /**
     * Returns the value of field 'srcRefField'. The field
     * 'srcRefField' has the following description: srcRefField
     * 
     * @return the value of field 'SrcRefField'.
     */
    public java.lang.String getSrcRefField(
    ) {
        return this._srcRefField;
    }

    /**
     * Returns the value of field 'srcTable'. The field 'srcTable'
     * has the following description: srcTable
     * 
     * @return the value of field 'SrcTable'.
     */
    public java.lang.String getSrcTable(
    ) {
        return this._srcTable;
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
    public void removeAllRule(
    ) {
        this._ruleList.clear();
    }

    /**
     * Method removeRule.
     * 
     * @param vRule
     * @return true if the object was removed from the collection.
     */
    public boolean removeRule(
            final haiyan.config.castorgen.Rule vRule) {
        boolean removed = _ruleList.remove(vRule);
        return removed;
    }

    /**
     * Method removeRuleAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Rule removeRuleAt(
            final int index) {
        java.lang.Object obj = this._ruleList.remove(index);
        return (haiyan.config.castorgen.Rule) obj;
    }

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: data rules
     * description
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'destExp'. The field 'destExp' has
     * the following description: destExp
     * 
     * @param destExp the value of field 'destExp'.
     */
    public void setDestExp(
            final java.lang.String destExp) {
        this._destExp = destExp;
    }

    /**
     * Sets the value of field 'destRefField'. The field
     * 'destRefField' has the following description: destRefField
     * 
     * @param destRefField the value of field 'destRefField'.
     */
    public void setDestRefField(
            final java.lang.String destRefField) {
        this._destRefField = destRefField;
    }

    /**
     * Sets the value of field 'destTable'. The field 'destTable'
     * has the following description: destTable
     * 
     * @param destTable the value of field 'destTable'.
     */
    public void setDestTable(
            final java.lang.String destTable) {
        this._destTable = destTable;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: data rules name
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
     * @param vRule
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRule(
            final int index,
            final haiyan.config.castorgen.Rule vRule)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._ruleList.size()) {
            throw new IndexOutOfBoundsException("setRule: Index value '" + index + "' not in range [0.." + (this._ruleList.size() - 1) + "]");
        }
        
        this._ruleList.set(index, vRule);
    }

    /**
     * 
     * 
     * @param vRuleArray
     */
    public void setRule(
            final haiyan.config.castorgen.Rule[] vRuleArray) {
        //-- copy array
        _ruleList.clear();
        
        for (int i = 0; i < vRuleArray.length; i++) {
                this._ruleList.add(vRuleArray[i]);
        }
    }

    /**
     * Sets the value of field 'srcExp'. The field 'srcExp' has the
     * following description: srcExp
     * 
     * @param srcExp the value of field 'srcExp'.
     */
    public void setSrcExp(
            final java.lang.String srcExp) {
        this._srcExp = srcExp;
    }

    /**
     * Sets the value of field 'srcRefField'. The field
     * 'srcRefField' has the following description: srcRefField
     * 
     * @param srcRefField the value of field 'srcRefField'.
     */
    public void setSrcRefField(
            final java.lang.String srcRefField) {
        this._srcRefField = srcRefField;
    }

    /**
     * Sets the value of field 'srcTable'. The field 'srcTable' has
     * the following description: srcTable
     * 
     * @param srcTable the value of field 'srcTable'.
     */
    public void setSrcTable(
            final java.lang.String srcTable) {
        this._srcTable = srcTable;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.DataRules
     */
    public static haiyan.config.castorgen.DataRules unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.DataRules) Unmarshaller.unmarshal(haiyan.config.castorgen.DataRules.class, reader);
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
