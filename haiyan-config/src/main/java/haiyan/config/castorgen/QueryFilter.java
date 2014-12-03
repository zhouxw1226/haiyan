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
 * table filter
 * 
 * @version $Revision$ $Date$
 */
public class QueryFilter implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * role group
     */
    private java.lang.String _role;

    /**
     * filter sql
     */
    private java.util.Vector _pluggedFilterList;


      //----------------/
     //- Constructors -/
    //----------------/

    public QueryFilter() {
        super();
        this._pluggedFilterList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPluggedFilter
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPluggedFilter(
            final haiyan.config.castorgen.PluggedFilter vPluggedFilter)
    throws java.lang.IndexOutOfBoundsException {
        this._pluggedFilterList.addElement(vPluggedFilter);
    }

    /**
     * 
     * 
     * @param index
     * @param vPluggedFilter
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPluggedFilter(
            final int index,
            final haiyan.config.castorgen.PluggedFilter vPluggedFilter)
    throws java.lang.IndexOutOfBoundsException {
        this._pluggedFilterList.add(index, vPluggedFilter);
    }

    /**
     * Method enumeratePluggedFilter.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.PluggedFilter elements
     */
    public java.util.Enumeration enumeratePluggedFilter(
    ) {
        return this._pluggedFilterList.elements();
    }

    /**
     * Method getPluggedFilter.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.PluggedFilter at the given index
     */
    public haiyan.config.castorgen.PluggedFilter getPluggedFilter(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pluggedFilterList.size()) {
            throw new IndexOutOfBoundsException("getPluggedFilter: Index value '" + index + "' not in range [0.." + (this._pluggedFilterList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.PluggedFilter) _pluggedFilterList.get(index);
    }

    /**
     * Method getPluggedFilter.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.PluggedFilter[] getPluggedFilter(
    ) {
        haiyan.config.castorgen.PluggedFilter[] array = new haiyan.config.castorgen.PluggedFilter[0];
        return (haiyan.config.castorgen.PluggedFilter[]) this._pluggedFilterList.toArray(array);
    }

    /**
     * Method getPluggedFilterCount.
     * 
     * @return the size of this collection
     */
    public int getPluggedFilterCount(
    ) {
        return this._pluggedFilterList.size();
    }

    /**
     * Returns the value of field 'role'. The field 'role' has the
     * following description: role group
     * 
     * @return the value of field 'Role'.
     */
    public java.lang.String getRole(
    ) {
        return this._role;
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
    public void removeAllPluggedFilter(
    ) {
        this._pluggedFilterList.clear();
    }

    /**
     * Method removePluggedFilter.
     * 
     * @param vPluggedFilter
     * @return true if the object was removed from the collection.
     */
    public boolean removePluggedFilter(
            final haiyan.config.castorgen.PluggedFilter vPluggedFilter) {
        boolean removed = _pluggedFilterList.remove(vPluggedFilter);
        return removed;
    }

    /**
     * Method removePluggedFilterAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.PluggedFilter removePluggedFilterAt(
            final int index) {
        java.lang.Object obj = this._pluggedFilterList.remove(index);
        return (haiyan.config.castorgen.PluggedFilter) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPluggedFilter
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPluggedFilter(
            final int index,
            final haiyan.config.castorgen.PluggedFilter vPluggedFilter)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pluggedFilterList.size()) {
            throw new IndexOutOfBoundsException("setPluggedFilter: Index value '" + index + "' not in range [0.." + (this._pluggedFilterList.size() - 1) + "]");
        }
        
        this._pluggedFilterList.set(index, vPluggedFilter);
    }

    /**
     * 
     * 
     * @param vPluggedFilterArray
     */
    public void setPluggedFilter(
            final haiyan.config.castorgen.PluggedFilter[] vPluggedFilterArray) {
        //-- copy array
        _pluggedFilterList.clear();
        
        for (int i = 0; i < vPluggedFilterArray.length; i++) {
                this._pluggedFilterList.add(vPluggedFilterArray[i]);
        }
    }

    /**
     * Sets the value of field 'role'. The field 'role' has the
     * following description: role group
     * 
     * @param role the value of field 'role'.
     */
    public void setRole(
            final java.lang.String role) {
        this._role = role;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.QueryFilter
     */
    public static haiyan.config.castorgen.QueryFilter unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.QueryFilter) Unmarshaller.unmarshal(haiyan.config.castorgen.QueryFilter.class, reader);
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
