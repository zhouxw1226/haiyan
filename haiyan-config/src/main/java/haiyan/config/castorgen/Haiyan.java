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
 * Haiyan Config
 * 
 * @version $Revision$ $Date$
 */
public class Haiyan implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * bill config
     */
    private java.util.Vector _billList;

    /**
     * table config
     */
    private java.util.Vector _tableList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Haiyan() {
        super();
        this._billList = new java.util.Vector();
        this._tableList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vBill
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBill(
            final haiyan.config.castorgen.Bill vBill)
    throws java.lang.IndexOutOfBoundsException {
        this._billList.addElement(vBill);
    }

    /**
     * 
     * 
     * @param index
     * @param vBill
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBill(
            final int index,
            final haiyan.config.castorgen.Bill vBill)
    throws java.lang.IndexOutOfBoundsException {
        this._billList.add(index, vBill);
    }

    /**
     * 
     * 
     * @param vTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTable(
            final haiyan.config.castorgen.Table vTable)
    throws java.lang.IndexOutOfBoundsException {
        this._tableList.addElement(vTable);
    }

    /**
     * 
     * 
     * @param index
     * @param vTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTable(
            final int index,
            final haiyan.config.castorgen.Table vTable)
    throws java.lang.IndexOutOfBoundsException {
        this._tableList.add(index, vTable);
    }

    /**
     * Method enumerateBill.
     * 
     * @return an Enumeration over all haiyan.config.castorgen.Bill
     * elements
     */
    public java.util.Enumeration enumerateBill(
    ) {
        return this._billList.elements();
    }

    /**
     * Method enumerateTable.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.Table elements
     */
    public java.util.Enumeration enumerateTable(
    ) {
        return this._tableList.elements();
    }

    /**
     * Method getBill.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Bill at the
     * given index
     */
    public haiyan.config.castorgen.Bill getBill(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billList.size()) {
            throw new IndexOutOfBoundsException("getBill: Index value '" + index + "' not in range [0.." + (this._billList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Bill) _billList.get(index);
    }

    /**
     * Method getBill.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Bill[] getBill(
    ) {
        haiyan.config.castorgen.Bill[] array = new haiyan.config.castorgen.Bill[0];
        return (haiyan.config.castorgen.Bill[]) this._billList.toArray(array);
    }

    /**
     * Method getBillCount.
     * 
     * @return the size of this collection
     */
    public int getBillCount(
    ) {
        return this._billList.size();
    }

    /**
     * Method getTable.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Table at
     * the given index
     */
    public haiyan.config.castorgen.Table getTable(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._tableList.size()) {
            throw new IndexOutOfBoundsException("getTable: Index value '" + index + "' not in range [0.." + (this._tableList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Table) _tableList.get(index);
    }

    /**
     * Method getTable.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Table[] getTable(
    ) {
        haiyan.config.castorgen.Table[] array = new haiyan.config.castorgen.Table[0];
        return (haiyan.config.castorgen.Table[]) this._tableList.toArray(array);
    }

    /**
     * Method getTableCount.
     * 
     * @return the size of this collection
     */
    public int getTableCount(
    ) {
        return this._tableList.size();
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
    public void removeAllBill(
    ) {
        this._billList.clear();
    }

    /**
     */
    public void removeAllTable(
    ) {
        this._tableList.clear();
    }

    /**
     * Method removeBill.
     * 
     * @param vBill
     * @return true if the object was removed from the collection.
     */
    public boolean removeBill(
            final haiyan.config.castorgen.Bill vBill) {
        boolean removed = _billList.remove(vBill);
        return removed;
    }

    /**
     * Method removeBillAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Bill removeBillAt(
            final int index) {
        java.lang.Object obj = this._billList.remove(index);
        return (haiyan.config.castorgen.Bill) obj;
    }

    /**
     * Method removeTable.
     * 
     * @param vTable
     * @return true if the object was removed from the collection.
     */
    public boolean removeTable(
            final haiyan.config.castorgen.Table vTable) {
        boolean removed = _tableList.remove(vTable);
        return removed;
    }

    /**
     * Method removeTableAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Table removeTableAt(
            final int index) {
        java.lang.Object obj = this._tableList.remove(index);
        return (haiyan.config.castorgen.Table) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vBill
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setBill(
            final int index,
            final haiyan.config.castorgen.Bill vBill)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billList.size()) {
            throw new IndexOutOfBoundsException("setBill: Index value '" + index + "' not in range [0.." + (this._billList.size() - 1) + "]");
        }
        
        this._billList.set(index, vBill);
    }

    /**
     * 
     * 
     * @param vBillArray
     */
    public void setBill(
            final haiyan.config.castorgen.Bill[] vBillArray) {
        //-- copy array
        _billList.clear();
        
        for (int i = 0; i < vBillArray.length; i++) {
                this._billList.add(vBillArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTable(
            final int index,
            final haiyan.config.castorgen.Table vTable)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._tableList.size()) {
            throw new IndexOutOfBoundsException("setTable: Index value '" + index + "' not in range [0.." + (this._tableList.size() - 1) + "]");
        }
        
        this._tableList.set(index, vTable);
    }

    /**
     * 
     * 
     * @param vTableArray
     */
    public void setTable(
            final haiyan.config.castorgen.Table[] vTableArray) {
        //-- copy array
        _tableList.clear();
        
        for (int i = 0; i < vTableArray.length; i++) {
                this._tableList.add(vTableArray[i]);
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
     * @return the unmarshaled haiyan.config.castorgen.Haiyan
     */
    public static haiyan.config.castorgen.Haiyan unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Haiyan) Unmarshaller.unmarshal(haiyan.config.castorgen.Haiyan.class, reader);
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
