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
 * system role right
 * 
 * @version $Revision$ $Date$
 */
public class Role implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * several role name
     */
    private java.util.Vector _name;

    /**
     * view right
     */
    private java.lang.String _view;

    /**
     * diabled component
     */
    private java.util.Vector _diabledComponentList;

    /**
     * readOnly component
     */
    private java.util.Vector _readOnlyComponentList;

    /**
     * diabled operation
     */
    private java.util.Vector _disabledOperationList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Role() {
        super();
        this._name = new java.util.Vector();
        this._diabledComponentList = new java.util.Vector();
        this._readOnlyComponentList = new java.util.Vector();
        this._disabledOperationList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vDiabledComponent
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDiabledComponent(
            final haiyan.config.castorgen.DiabledComponent vDiabledComponent)
    throws java.lang.IndexOutOfBoundsException {
        this._diabledComponentList.addElement(vDiabledComponent);
    }

    /**
     * 
     * 
     * @param index
     * @param vDiabledComponent
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDiabledComponent(
            final int index,
            final haiyan.config.castorgen.DiabledComponent vDiabledComponent)
    throws java.lang.IndexOutOfBoundsException {
        this._diabledComponentList.add(index, vDiabledComponent);
    }

    /**
     * 
     * 
     * @param vDisabledOperation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDisabledOperation(
            final haiyan.config.castorgen.DisabledOperation vDisabledOperation)
    throws java.lang.IndexOutOfBoundsException {
        this._disabledOperationList.addElement(vDisabledOperation);
    }

    /**
     * 
     * 
     * @param index
     * @param vDisabledOperation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDisabledOperation(
            final int index,
            final haiyan.config.castorgen.DisabledOperation vDisabledOperation)
    throws java.lang.IndexOutOfBoundsException {
        this._disabledOperationList.add(index, vDisabledOperation);
    }

    /**
     * 
     * 
     * @param vName
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addName(
            final java.lang.String vName)
    throws java.lang.IndexOutOfBoundsException {
        this._name.addElement(vName);
    }

    /**
     * 
     * 
     * @param index
     * @param vName
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addName(
            final int index,
            final java.lang.String vName)
    throws java.lang.IndexOutOfBoundsException {
        this._name.add(index, vName);
    }

    /**
     * 
     * 
     * @param vReadOnlyComponent
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addReadOnlyComponent(
            final haiyan.config.castorgen.ReadOnlyComponent vReadOnlyComponent)
    throws java.lang.IndexOutOfBoundsException {
        this._readOnlyComponentList.addElement(vReadOnlyComponent);
    }

    /**
     * 
     * 
     * @param index
     * @param vReadOnlyComponent
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addReadOnlyComponent(
            final int index,
            final haiyan.config.castorgen.ReadOnlyComponent vReadOnlyComponent)
    throws java.lang.IndexOutOfBoundsException {
        this._readOnlyComponentList.add(index, vReadOnlyComponent);
    }

    /**
     * Method enumerateDiabledComponent.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.DiabledComponent elements
     */
    public java.util.Enumeration enumerateDiabledComponent(
    ) {
        return this._diabledComponentList.elements();
    }

    /**
     * Method enumerateDisabledOperation.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.DisabledOperation elements
     */
    public java.util.Enumeration enumerateDisabledOperation(
    ) {
        return this._disabledOperationList.elements();
    }

    /**
     * Method enumerateName.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration enumerateName(
    ) {
        return this._name.elements();
    }

    /**
     * Method enumerateReadOnlyComponent.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.ReadOnlyComponent elements
     */
    public java.util.Enumeration enumerateReadOnlyComponent(
    ) {
        return this._readOnlyComponentList.elements();
    }

    /**
     * Method getDiabledComponent.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.DiabledComponent at the given index
     */
    public haiyan.config.castorgen.DiabledComponent getDiabledComponent(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._diabledComponentList.size()) {
            throw new IndexOutOfBoundsException("getDiabledComponent: Index value '" + index + "' not in range [0.." + (this._diabledComponentList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.DiabledComponent) _diabledComponentList.get(index);
    }

    /**
     * Method getDiabledComponent.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.DiabledComponent[] getDiabledComponent(
    ) {
        haiyan.config.castorgen.DiabledComponent[] array = new haiyan.config.castorgen.DiabledComponent[0];
        return (haiyan.config.castorgen.DiabledComponent[]) this._diabledComponentList.toArray(array);
    }

    /**
     * Method getDiabledComponentCount.
     * 
     * @return the size of this collection
     */
    public int getDiabledComponentCount(
    ) {
        return this._diabledComponentList.size();
    }

    /**
     * Method getDisabledOperation.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.DisabledOperation at the given index
     */
    public haiyan.config.castorgen.DisabledOperation getDisabledOperation(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._disabledOperationList.size()) {
            throw new IndexOutOfBoundsException("getDisabledOperation: Index value '" + index + "' not in range [0.." + (this._disabledOperationList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.DisabledOperation) _disabledOperationList.get(index);
    }

    /**
     * Method getDisabledOperation.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.DisabledOperation[] getDisabledOperation(
    ) {
        haiyan.config.castorgen.DisabledOperation[] array = new haiyan.config.castorgen.DisabledOperation[0];
        return (haiyan.config.castorgen.DisabledOperation[]) this._disabledOperationList.toArray(array);
    }

    /**
     * Method getDisabledOperationCount.
     * 
     * @return the size of this collection
     */
    public int getDisabledOperationCount(
    ) {
        return this._disabledOperationList.size();
    }

    /**
     * Method getName.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getName(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._name.size()) {
            throw new IndexOutOfBoundsException("getName: Index value '" + index + "' not in range [0.." + (this._name.size() - 1) + "]");
        }
        
        return (java.lang.String) _name.get(index);
    }

    /**
     * Method getName.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getName(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._name.toArray(array);
    }

    /**
     * Method getNameCount.
     * 
     * @return the size of this collection
     */
    public int getNameCount(
    ) {
        return this._name.size();
    }

    /**
     * Method getReadOnlyComponent.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.ReadOnlyComponent at the given index
     */
    public haiyan.config.castorgen.ReadOnlyComponent getReadOnlyComponent(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._readOnlyComponentList.size()) {
            throw new IndexOutOfBoundsException("getReadOnlyComponent: Index value '" + index + "' not in range [0.." + (this._readOnlyComponentList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.ReadOnlyComponent) _readOnlyComponentList.get(index);
    }

    /**
     * Method getReadOnlyComponent.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.ReadOnlyComponent[] getReadOnlyComponent(
    ) {
        haiyan.config.castorgen.ReadOnlyComponent[] array = new haiyan.config.castorgen.ReadOnlyComponent[0];
        return (haiyan.config.castorgen.ReadOnlyComponent[]) this._readOnlyComponentList.toArray(array);
    }

    /**
     * Method getReadOnlyComponentCount.
     * 
     * @return the size of this collection
     */
    public int getReadOnlyComponentCount(
    ) {
        return this._readOnlyComponentList.size();
    }

    /**
     * Returns the value of field 'view'. The field 'view' has the
     * following description: view right
     * 
     * @return the value of field 'View'.
     */
    public java.lang.String getView(
    ) {
        return this._view;
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
    public void removeAllDiabledComponent(
    ) {
        this._diabledComponentList.clear();
    }

    /**
     */
    public void removeAllDisabledOperation(
    ) {
        this._disabledOperationList.clear();
    }

    /**
     */
    public void removeAllName(
    ) {
        this._name.clear();
    }

    /**
     */
    public void removeAllReadOnlyComponent(
    ) {
        this._readOnlyComponentList.clear();
    }

    /**
     * Method removeDiabledComponent.
     * 
     * @param vDiabledComponent
     * @return true if the object was removed from the collection.
     */
    public boolean removeDiabledComponent(
            final haiyan.config.castorgen.DiabledComponent vDiabledComponent) {
        boolean removed = _diabledComponentList.remove(vDiabledComponent);
        return removed;
    }

    /**
     * Method removeDiabledComponentAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.DiabledComponent removeDiabledComponentAt(
            final int index) {
        java.lang.Object obj = this._diabledComponentList.remove(index);
        return (haiyan.config.castorgen.DiabledComponent) obj;
    }

    /**
     * Method removeDisabledOperation.
     * 
     * @param vDisabledOperation
     * @return true if the object was removed from the collection.
     */
    public boolean removeDisabledOperation(
            final haiyan.config.castorgen.DisabledOperation vDisabledOperation) {
        boolean removed = _disabledOperationList.remove(vDisabledOperation);
        return removed;
    }

    /**
     * Method removeDisabledOperationAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.DisabledOperation removeDisabledOperationAt(
            final int index) {
        java.lang.Object obj = this._disabledOperationList.remove(index);
        return (haiyan.config.castorgen.DisabledOperation) obj;
    }

    /**
     * Method removeName.
     * 
     * @param vName
     * @return true if the object was removed from the collection.
     */
    public boolean removeName(
            final java.lang.String vName) {
        boolean removed = _name.remove(vName);
        return removed;
    }

    /**
     * Method removeNameAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeNameAt(
            final int index) {
        java.lang.Object obj = this._name.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * Method removeReadOnlyComponent.
     * 
     * @param vReadOnlyComponent
     * @return true if the object was removed from the collection.
     */
    public boolean removeReadOnlyComponent(
            final haiyan.config.castorgen.ReadOnlyComponent vReadOnlyComponent) {
        boolean removed = _readOnlyComponentList.remove(vReadOnlyComponent);
        return removed;
    }

    /**
     * Method removeReadOnlyComponentAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.ReadOnlyComponent removeReadOnlyComponentAt(
            final int index) {
        java.lang.Object obj = this._readOnlyComponentList.remove(index);
        return (haiyan.config.castorgen.ReadOnlyComponent) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vDiabledComponent
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setDiabledComponent(
            final int index,
            final haiyan.config.castorgen.DiabledComponent vDiabledComponent)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._diabledComponentList.size()) {
            throw new IndexOutOfBoundsException("setDiabledComponent: Index value '" + index + "' not in range [0.." + (this._diabledComponentList.size() - 1) + "]");
        }
        
        this._diabledComponentList.set(index, vDiabledComponent);
    }

    /**
     * 
     * 
     * @param vDiabledComponentArray
     */
    public void setDiabledComponent(
            final haiyan.config.castorgen.DiabledComponent[] vDiabledComponentArray) {
        //-- copy array
        _diabledComponentList.clear();
        
        for (int i = 0; i < vDiabledComponentArray.length; i++) {
                this._diabledComponentList.add(vDiabledComponentArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vDisabledOperation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setDisabledOperation(
            final int index,
            final haiyan.config.castorgen.DisabledOperation vDisabledOperation)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._disabledOperationList.size()) {
            throw new IndexOutOfBoundsException("setDisabledOperation: Index value '" + index + "' not in range [0.." + (this._disabledOperationList.size() - 1) + "]");
        }
        
        this._disabledOperationList.set(index, vDisabledOperation);
    }

    /**
     * 
     * 
     * @param vDisabledOperationArray
     */
    public void setDisabledOperation(
            final haiyan.config.castorgen.DisabledOperation[] vDisabledOperationArray) {
        //-- copy array
        _disabledOperationList.clear();
        
        for (int i = 0; i < vDisabledOperationArray.length; i++) {
                this._disabledOperationList.add(vDisabledOperationArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vName
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setName(
            final int index,
            final java.lang.String vName)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._name.size()) {
            throw new IndexOutOfBoundsException("setName: Index value '" + index + "' not in range [0.." + (this._name.size() - 1) + "]");
        }
        
        this._name.set(index, vName);
    }

    /**
     * 
     * 
     * @param vNameArray
     */
    public void setName(
            final java.lang.String[] vNameArray) {
        //-- copy array
        _name.clear();
        
        for (int i = 0; i < vNameArray.length; i++) {
                this._name.add(vNameArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vReadOnlyComponent
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setReadOnlyComponent(
            final int index,
            final haiyan.config.castorgen.ReadOnlyComponent vReadOnlyComponent)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._readOnlyComponentList.size()) {
            throw new IndexOutOfBoundsException("setReadOnlyComponent: Index value '" + index + "' not in range [0.." + (this._readOnlyComponentList.size() - 1) + "]");
        }
        
        this._readOnlyComponentList.set(index, vReadOnlyComponent);
    }

    /**
     * 
     * 
     * @param vReadOnlyComponentArray
     */
    public void setReadOnlyComponent(
            final haiyan.config.castorgen.ReadOnlyComponent[] vReadOnlyComponentArray) {
        //-- copy array
        _readOnlyComponentList.clear();
        
        for (int i = 0; i < vReadOnlyComponentArray.length; i++) {
                this._readOnlyComponentList.add(vReadOnlyComponentArray[i]);
        }
    }

    /**
     * Sets the value of field 'view'. The field 'view' has the
     * following description: view right
     * 
     * @param view the value of field 'view'.
     */
    public void setView(
            final java.lang.String view) {
        this._view = view;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.Role
     */
    public static haiyan.config.castorgen.Role unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Role) Unmarshaller.unmarshal(haiyan.config.castorgen.Role.class, reader);
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
