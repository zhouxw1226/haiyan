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
 * workFlow
 * 
 * @version $Revision$ $Date$
 */
public class WorkFlow implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name.
     */
    private java.lang.String _name;

    /**
     * Field _itemCount.
     */
    private int _itemCount;

    /**
     * keeps track of state for field: _itemCount
     */
    private boolean _has_itemCount;

    /**
     * Field _operatorFldKeys.
     */
    private java.lang.String _operatorFldKeys;

    /**
     * Field _optTimeFldKeys.
     */
    private java.lang.String _optTimeFldKeys;

    /**
     * Field _statusFieldKey.
     */
    private java.lang.String _statusFieldKey;

    /**
     * workFlowItem
     */
    private java.util.Vector _workFlowItemList;


      //----------------/
     //- Constructors -/
    //----------------/

    public WorkFlow() {
        super();
        this._workFlowItemList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vWorkFlowItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWorkFlowItem(
            final haiyan.config.castorgen.WorkFlowItem vWorkFlowItem)
    throws java.lang.IndexOutOfBoundsException {
        this._workFlowItemList.addElement(vWorkFlowItem);
    }

    /**
     * 
     * 
     * @param index
     * @param vWorkFlowItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWorkFlowItem(
            final int index,
            final haiyan.config.castorgen.WorkFlowItem vWorkFlowItem)
    throws java.lang.IndexOutOfBoundsException {
        this._workFlowItemList.add(index, vWorkFlowItem);
    }

    /**
     */
    public void deleteItemCount(
    ) {
        this._has_itemCount= false;
    }

    /**
     * Method enumerateWorkFlowItem.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.WorkFlowItem elements
     */
    public java.util.Enumeration enumerateWorkFlowItem(
    ) {
        return this._workFlowItemList.elements();
    }

    /**
     * Returns the value of field 'itemCount'.
     * 
     * @return the value of field 'ItemCount'.
     */
    public int getItemCount(
    ) {
        return this._itemCount;
    }

    /**
     * Returns the value of field 'name'.
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'operatorFldKeys'.
     * 
     * @return the value of field 'OperatorFldKeys'.
     */
    public java.lang.String getOperatorFldKeys(
    ) {
        return this._operatorFldKeys;
    }

    /**
     * Returns the value of field 'optTimeFldKeys'.
     * 
     * @return the value of field 'OptTimeFldKeys'.
     */
    public java.lang.String getOptTimeFldKeys(
    ) {
        return this._optTimeFldKeys;
    }

    /**
     * Returns the value of field 'statusFieldKey'.
     * 
     * @return the value of field 'StatusFieldKey'.
     */
    public java.lang.String getStatusFieldKey(
    ) {
        return this._statusFieldKey;
    }

    /**
     * Method getWorkFlowItem.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.WorkFlowItem at the given index
     */
    public haiyan.config.castorgen.WorkFlowItem getWorkFlowItem(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._workFlowItemList.size()) {
            throw new IndexOutOfBoundsException("getWorkFlowItem: Index value '" + index + "' not in range [0.." + (this._workFlowItemList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.WorkFlowItem) _workFlowItemList.get(index);
    }

    /**
     * Method getWorkFlowItem.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.WorkFlowItem[] getWorkFlowItem(
    ) {
        haiyan.config.castorgen.WorkFlowItem[] array = new haiyan.config.castorgen.WorkFlowItem[0];
        return (haiyan.config.castorgen.WorkFlowItem[]) this._workFlowItemList.toArray(array);
    }

    /**
     * Method getWorkFlowItemCount.
     * 
     * @return the size of this collection
     */
    public int getWorkFlowItemCount(
    ) {
        return this._workFlowItemList.size();
    }

    /**
     * Method hasItemCount.
     * 
     * @return true if at least one ItemCount has been added
     */
    public boolean hasItemCount(
    ) {
        return this._has_itemCount;
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
    public void removeAllWorkFlowItem(
    ) {
        this._workFlowItemList.clear();
    }

    /**
     * Method removeWorkFlowItem.
     * 
     * @param vWorkFlowItem
     * @return true if the object was removed from the collection.
     */
    public boolean removeWorkFlowItem(
            final haiyan.config.castorgen.WorkFlowItem vWorkFlowItem) {
        boolean removed = _workFlowItemList.remove(vWorkFlowItem);
        return removed;
    }

    /**
     * Method removeWorkFlowItemAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.WorkFlowItem removeWorkFlowItemAt(
            final int index) {
        java.lang.Object obj = this._workFlowItemList.remove(index);
        return (haiyan.config.castorgen.WorkFlowItem) obj;
    }

    /**
     * Sets the value of field 'itemCount'.
     * 
     * @param itemCount the value of field 'itemCount'.
     */
    public void setItemCount(
            final int itemCount) {
        this._itemCount = itemCount;
        this._has_itemCount = true;
    }

    /**
     * Sets the value of field 'name'.
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'operatorFldKeys'.
     * 
     * @param operatorFldKeys the value of field 'operatorFldKeys'.
     */
    public void setOperatorFldKeys(
            final java.lang.String operatorFldKeys) {
        this._operatorFldKeys = operatorFldKeys;
    }

    /**
     * Sets the value of field 'optTimeFldKeys'.
     * 
     * @param optTimeFldKeys the value of field 'optTimeFldKeys'.
     */
    public void setOptTimeFldKeys(
            final java.lang.String optTimeFldKeys) {
        this._optTimeFldKeys = optTimeFldKeys;
    }

    /**
     * Sets the value of field 'statusFieldKey'.
     * 
     * @param statusFieldKey the value of field 'statusFieldKey'.
     */
    public void setStatusFieldKey(
            final java.lang.String statusFieldKey) {
        this._statusFieldKey = statusFieldKey;
    }

    /**
     * 
     * 
     * @param index
     * @param vWorkFlowItem
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setWorkFlowItem(
            final int index,
            final haiyan.config.castorgen.WorkFlowItem vWorkFlowItem)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._workFlowItemList.size()) {
            throw new IndexOutOfBoundsException("setWorkFlowItem: Index value '" + index + "' not in range [0.." + (this._workFlowItemList.size() - 1) + "]");
        }
        
        this._workFlowItemList.set(index, vWorkFlowItem);
    }

    /**
     * 
     * 
     * @param vWorkFlowItemArray
     */
    public void setWorkFlowItem(
            final haiyan.config.castorgen.WorkFlowItem[] vWorkFlowItemArray) {
        //-- copy array
        _workFlowItemList.clear();
        
        for (int i = 0; i < vWorkFlowItemArray.length; i++) {
                this._workFlowItemList.add(vWorkFlowItemArray[i]);
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
     * @return the unmarshaled haiyan.config.castorgen.WorkFlow
     */
    public static haiyan.config.castorgen.WorkFlow unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.WorkFlow) Unmarshaller.unmarshal(haiyan.config.castorgen.WorkFlow.class, reader);
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
