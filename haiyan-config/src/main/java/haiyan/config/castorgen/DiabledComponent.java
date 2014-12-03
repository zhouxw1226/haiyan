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
 * diabled component
 * 
 * @version $Revision$ $Date$
 */
public class DiabledComponent implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * component name
     */
    private java.util.Vector _name;


      //----------------/
     //- Constructors -/
    //----------------/

    public DiabledComponent() {
        super();
        this._name = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

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
     * Method enumerateName.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration enumerateName(
    ) {
        return this._name.elements();
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
    public void removeAllName(
    ) {
        this._name.clear();
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
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * haiyan.config.castorgen.DiabledComponent
     */
    public static haiyan.config.castorgen.DiabledComponent unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.DiabledComponent) Unmarshaller.unmarshal(haiyan.config.castorgen.DiabledComponent.class, reader);
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
