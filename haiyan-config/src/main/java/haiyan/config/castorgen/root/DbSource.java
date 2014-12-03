/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen.root;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * other database
 * 
 * @version $Revision$ $Date$
 */
public class DbSource implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * jdbc connect parameter
     */
    private java.util.Vector _jdbcURLList;

    /**
     * jndi connect parameter
     */
    private java.util.Vector _dataSourceList;


      //----------------/
     //- Constructors -/
    //----------------/

    public DbSource() {
        super();
        this._jdbcURLList = new java.util.Vector();
        this._dataSourceList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vDataSource
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDataSource(
            final haiyan.config.castorgen.root.DataSource vDataSource)
    throws java.lang.IndexOutOfBoundsException {
        this._dataSourceList.addElement(vDataSource);
    }

    /**
     * 
     * 
     * @param index
     * @param vDataSource
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDataSource(
            final int index,
            final haiyan.config.castorgen.root.DataSource vDataSource)
    throws java.lang.IndexOutOfBoundsException {
        this._dataSourceList.add(index, vDataSource);
    }

    /**
     * 
     * 
     * @param vJdbcURL
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJdbcURL(
            final haiyan.config.castorgen.root.JdbcURL vJdbcURL)
    throws java.lang.IndexOutOfBoundsException {
        this._jdbcURLList.addElement(vJdbcURL);
    }

    /**
     * 
     * 
     * @param index
     * @param vJdbcURL
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJdbcURL(
            final int index,
            final haiyan.config.castorgen.root.JdbcURL vJdbcURL)
    throws java.lang.IndexOutOfBoundsException {
        this._jdbcURLList.add(index, vJdbcURL);
    }

    /**
     * Method enumerateDataSource.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.root.DataSource elements
     */
    public java.util.Enumeration enumerateDataSource(
    ) {
        return this._dataSourceList.elements();
    }

    /**
     * Method enumerateJdbcURL.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.root.JdbcURL elements
     */
    public java.util.Enumeration enumerateJdbcURL(
    ) {
        return this._jdbcURLList.elements();
    }

    /**
     * Method getDataSource.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.root.DataSource at the given index
     */
    public haiyan.config.castorgen.root.DataSource getDataSource(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._dataSourceList.size()) {
            throw new IndexOutOfBoundsException("getDataSource: Index value '" + index + "' not in range [0.." + (this._dataSourceList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.root.DataSource) _dataSourceList.get(index);
    }

    /**
     * Method getDataSource.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.root.DataSource[] getDataSource(
    ) {
        haiyan.config.castorgen.root.DataSource[] array = new haiyan.config.castorgen.root.DataSource[0];
        return (haiyan.config.castorgen.root.DataSource[]) this._dataSourceList.toArray(array);
    }

    /**
     * Method getDataSourceCount.
     * 
     * @return the size of this collection
     */
    public int getDataSourceCount(
    ) {
        return this._dataSourceList.size();
    }

    /**
     * Method getJdbcURL.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.root.JdbcURL at the given index
     */
    public haiyan.config.castorgen.root.JdbcURL getJdbcURL(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._jdbcURLList.size()) {
            throw new IndexOutOfBoundsException("getJdbcURL: Index value '" + index + "' not in range [0.." + (this._jdbcURLList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.root.JdbcURL) _jdbcURLList.get(index);
    }

    /**
     * Method getJdbcURL.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.root.JdbcURL[] getJdbcURL(
    ) {
        haiyan.config.castorgen.root.JdbcURL[] array = new haiyan.config.castorgen.root.JdbcURL[0];
        return (haiyan.config.castorgen.root.JdbcURL[]) this._jdbcURLList.toArray(array);
    }

    /**
     * Method getJdbcURLCount.
     * 
     * @return the size of this collection
     */
    public int getJdbcURLCount(
    ) {
        return this._jdbcURLList.size();
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
    public void removeAllDataSource(
    ) {
        this._dataSourceList.clear();
    }

    /**
     */
    public void removeAllJdbcURL(
    ) {
        this._jdbcURLList.clear();
    }

    /**
     * Method removeDataSource.
     * 
     * @param vDataSource
     * @return true if the object was removed from the collection.
     */
    public boolean removeDataSource(
            final haiyan.config.castorgen.root.DataSource vDataSource) {
        boolean removed = _dataSourceList.remove(vDataSource);
        return removed;
    }

    /**
     * Method removeDataSourceAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.root.DataSource removeDataSourceAt(
            final int index) {
        java.lang.Object obj = this._dataSourceList.remove(index);
        return (haiyan.config.castorgen.root.DataSource) obj;
    }

    /**
     * Method removeJdbcURL.
     * 
     * @param vJdbcURL
     * @return true if the object was removed from the collection.
     */
    public boolean removeJdbcURL(
            final haiyan.config.castorgen.root.JdbcURL vJdbcURL) {
        boolean removed = _jdbcURLList.remove(vJdbcURL);
        return removed;
    }

    /**
     * Method removeJdbcURLAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.root.JdbcURL removeJdbcURLAt(
            final int index) {
        java.lang.Object obj = this._jdbcURLList.remove(index);
        return (haiyan.config.castorgen.root.JdbcURL) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vDataSource
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setDataSource(
            final int index,
            final haiyan.config.castorgen.root.DataSource vDataSource)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._dataSourceList.size()) {
            throw new IndexOutOfBoundsException("setDataSource: Index value '" + index + "' not in range [0.." + (this._dataSourceList.size() - 1) + "]");
        }
        
        this._dataSourceList.set(index, vDataSource);
    }

    /**
     * 
     * 
     * @param vDataSourceArray
     */
    public void setDataSource(
            final haiyan.config.castorgen.root.DataSource[] vDataSourceArray) {
        //-- copy array
        _dataSourceList.clear();
        
        for (int i = 0; i < vDataSourceArray.length; i++) {
                this._dataSourceList.add(vDataSourceArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vJdbcURL
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setJdbcURL(
            final int index,
            final haiyan.config.castorgen.root.JdbcURL vJdbcURL)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._jdbcURLList.size()) {
            throw new IndexOutOfBoundsException("setJdbcURL: Index value '" + index + "' not in range [0.." + (this._jdbcURLList.size() - 1) + "]");
        }
        
        this._jdbcURLList.set(index, vJdbcURL);
    }

    /**
     * 
     * 
     * @param vJdbcURLArray
     */
    public void setJdbcURL(
            final haiyan.config.castorgen.root.JdbcURL[] vJdbcURLArray) {
        //-- copy array
        _jdbcURLList.clear();
        
        for (int i = 0; i < vJdbcURLArray.length; i++) {
                this._jdbcURLList.add(vJdbcURLArray[i]);
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
     * @return the unmarshaled haiyan.config.castorgen.root.DbSource
     */
    public static haiyan.config.castorgen.root.DbSource unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.root.DbSource) Unmarshaller.unmarshal(haiyan.config.castorgen.root.DbSource.class, reader);
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
