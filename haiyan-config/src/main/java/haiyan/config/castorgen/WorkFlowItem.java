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
 * workFlowItem
 * 
 * @version $Revision$ $Date$
 */
public class WorkFlowItem implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _statusKey.
     */
    private java.lang.String _statusKey;

    /**
     * Field _condition.
     */
    private java.lang.String _condition;

    /**
     * Field _optKey.
     */
    private java.lang.String _optKey;

    /**
     * Field _resultItemCount.
     */
    private int _resultItemCount;

    /**
     * keeps track of state for field: _resultItemCount
     */
    private boolean _has_resultItemCount;

    /**
     * Field _index.
     */
    private int _index;

    /**
     * keeps track of state for field: _index
     */
    private boolean _has_index;

    /**
     * wfResult
     */
    private java.util.Vector _wfResultList;


      //----------------/
     //- Constructors -/
    //----------------/

    public WorkFlowItem() {
        super();
        this._wfResultList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vWfResult
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWfResult(
            final haiyan.config.castorgen.WfResult vWfResult)
    throws java.lang.IndexOutOfBoundsException {
        this._wfResultList.addElement(vWfResult);
    }

    /**
     * 
     * 
     * @param index
     * @param vWfResult
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWfResult(
            final int index,
            final haiyan.config.castorgen.WfResult vWfResult)
    throws java.lang.IndexOutOfBoundsException {
        this._wfResultList.add(index, vWfResult);
    }

    /**
     */
    public void deleteIndex(
    ) {
        this._has_index= false;
    }

    /**
     */
    public void deleteResultItemCount(
    ) {
        this._has_resultItemCount= false;
    }

    /**
     * Method enumerateWfResult.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.WfResult elements
     */
    public java.util.Enumeration enumerateWfResult(
    ) {
        return this._wfResultList.elements();
    }

    /**
     * Returns the value of field 'condition'.
     * 
     * @return the value of field 'Condition'.
     */
    public java.lang.String getCondition(
    ) {
        return this._condition;
    }

    /**
     * Returns the value of field 'index'.
     * 
     * @return the value of field 'Index'.
     */
    public int getIndex(
    ) {
        return this._index;
    }

    /**
     * Returns the value of field 'optKey'.
     * 
     * @return the value of field 'OptKey'.
     */
    public java.lang.String getOptKey(
    ) {
        return this._optKey;
    }

    /**
     * Returns the value of field 'resultItemCount'.
     * 
     * @return the value of field 'ResultItemCount'.
     */
    public int getResultItemCount(
    ) {
        return this._resultItemCount;
    }

    /**
     * Returns the value of field 'statusKey'.
     * 
     * @return the value of field 'StatusKey'.
     */
    public java.lang.String getStatusKey(
    ) {
        return this._statusKey;
    }

    /**
     * Method getWfResult.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.WfResult at
     * the given index
     */
    public haiyan.config.castorgen.WfResult getWfResult(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wfResultList.size()) {
            throw new IndexOutOfBoundsException("getWfResult: Index value '" + index + "' not in range [0.." + (this._wfResultList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.WfResult) _wfResultList.get(index);
    }

    /**
     * Method getWfResult.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.WfResult[] getWfResult(
    ) {
        haiyan.config.castorgen.WfResult[] array = new haiyan.config.castorgen.WfResult[0];
        return (haiyan.config.castorgen.WfResult[]) this._wfResultList.toArray(array);
    }

    /**
     * Method getWfResultCount.
     * 
     * @return the size of this collection
     */
    public int getWfResultCount(
    ) {
        return this._wfResultList.size();
    }

    /**
     * Method hasIndex.
     * 
     * @return true if at least one Index has been added
     */
    public boolean hasIndex(
    ) {
        return this._has_index;
    }

    /**
     * Method hasResultItemCount.
     * 
     * @return true if at least one ResultItemCount has been added
     */
    public boolean hasResultItemCount(
    ) {
        return this._has_resultItemCount;
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
    public void removeAllWfResult(
    ) {
        this._wfResultList.clear();
    }

    /**
     * Method removeWfResult.
     * 
     * @param vWfResult
     * @return true if the object was removed from the collection.
     */
    public boolean removeWfResult(
            final haiyan.config.castorgen.WfResult vWfResult) {
        boolean removed = _wfResultList.remove(vWfResult);
        return removed;
    }

    /**
     * Method removeWfResultAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.WfResult removeWfResultAt(
            final int index) {
        java.lang.Object obj = this._wfResultList.remove(index);
        return (haiyan.config.castorgen.WfResult) obj;
    }

    /**
     * Sets the value of field 'condition'.
     * 
     * @param condition the value of field 'condition'.
     */
    public void setCondition(
            final java.lang.String condition) {
        this._condition = condition;
    }

    /**
     * Sets the value of field 'index'.
     * 
     * @param index the value of field 'index'.
     */
    public void setIndex(
            final int index) {
        this._index = index;
        this._has_index = true;
    }

    /**
     * Sets the value of field 'optKey'.
     * 
     * @param optKey the value of field 'optKey'.
     */
    public void setOptKey(
            final java.lang.String optKey) {
        this._optKey = optKey;
    }

    /**
     * Sets the value of field 'resultItemCount'.
     * 
     * @param resultItemCount the value of field 'resultItemCount'.
     */
    public void setResultItemCount(
            final int resultItemCount) {
        this._resultItemCount = resultItemCount;
        this._has_resultItemCount = true;
    }

    /**
     * Sets the value of field 'statusKey'.
     * 
     * @param statusKey the value of field 'statusKey'.
     */
    public void setStatusKey(
            final java.lang.String statusKey) {
        this._statusKey = statusKey;
    }

    /**
     * 
     * 
     * @param index
     * @param vWfResult
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setWfResult(
            final int index,
            final haiyan.config.castorgen.WfResult vWfResult)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wfResultList.size()) {
            throw new IndexOutOfBoundsException("setWfResult: Index value '" + index + "' not in range [0.." + (this._wfResultList.size() - 1) + "]");
        }
        
        this._wfResultList.set(index, vWfResult);
    }

    /**
     * 
     * 
     * @param vWfResultArray
     */
    public void setWfResult(
            final haiyan.config.castorgen.WfResult[] vWfResultArray) {
        //-- copy array
        _wfResultList.clear();
        
        for (int i = 0; i < vWfResultArray.length; i++) {
                this._wfResultList.add(vWfResultArray[i]);
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
     * @return the unmarshaled haiyan.config.castorgen.WorkFlowItem
     */
    public static haiyan.config.castorgen.WorkFlowItem unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.WorkFlowItem) Unmarshaller.unmarshal(haiyan.config.castorgen.WorkFlowItem.class, reader);
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
