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
 * Class Panel.
 * 
 * @version $Revision$ $Date$
 */
public class Panel implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _order.
     */
    private int _order;

    /**
     * keeps track of state for field: _order
     */
    private boolean _has_order;

    /**
     * Field _style.
     */
    private java.lang.String _style;

    /**
     * Field _name.
     */
    private java.lang.String _name;

    /**
     * Field _description.
     */
    private java.lang.String _description;

    /**
     * Group, diliver by " "
     */
    private java.util.Vector _fields;

    /**
     * Field _panelList.
     */
    private java.util.Vector _panelList;

    /**
     * Field _groupList.
     */
    private java.util.Vector _groupList;

    /**
     * Field _tabPanelList.
     */
    private java.util.Vector _tabPanelList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Panel() {
        super();
        this._fields = new java.util.Vector();
        this._panelList = new java.util.Vector();
        this._groupList = new java.util.Vector();
        this._tabPanelList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addFields(
            final java.lang.String vFields)
    throws java.lang.IndexOutOfBoundsException {
        this._fields.addElement(vFields);
    }

    /**
     * 
     * 
     * @param index
     * @param vFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addFields(
            final int index,
            final java.lang.String vFields)
    throws java.lang.IndexOutOfBoundsException {
        this._fields.add(index, vFields);
    }

    /**
     * 
     * 
     * @param vGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addGroup(
            final haiyan.config.castorgen.Group vGroup)
    throws java.lang.IndexOutOfBoundsException {
        this._groupList.addElement(vGroup);
    }

    /**
     * 
     * 
     * @param index
     * @param vGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addGroup(
            final int index,
            final haiyan.config.castorgen.Group vGroup)
    throws java.lang.IndexOutOfBoundsException {
        this._groupList.add(index, vGroup);
    }

    /**
     * 
     * 
     * @param vPanel
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPanel(
            final haiyan.config.castorgen.Panel vPanel)
    throws java.lang.IndexOutOfBoundsException {
        this._panelList.addElement(vPanel);
    }

    /**
     * 
     * 
     * @param index
     * @param vPanel
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPanel(
            final int index,
            final haiyan.config.castorgen.Panel vPanel)
    throws java.lang.IndexOutOfBoundsException {
        this._panelList.add(index, vPanel);
    }

    /**
     * 
     * 
     * @param vTabPanel
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTabPanel(
            final haiyan.config.castorgen.TabPanel vTabPanel)
    throws java.lang.IndexOutOfBoundsException {
        this._tabPanelList.addElement(vTabPanel);
    }

    /**
     * 
     * 
     * @param index
     * @param vTabPanel
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTabPanel(
            final int index,
            final haiyan.config.castorgen.TabPanel vTabPanel)
    throws java.lang.IndexOutOfBoundsException {
        this._tabPanelList.add(index, vTabPanel);
    }

    /**
     */
    public void deleteOrder(
    ) {
        this._has_order= false;
    }

    /**
     * Method enumerateFields.
     * 
     * @return an Enumeration over all java.lang.String elements
     */
    public java.util.Enumeration enumerateFields(
    ) {
        return this._fields.elements();
    }

    /**
     * Method enumerateGroup.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.Group elements
     */
    public java.util.Enumeration enumerateGroup(
    ) {
        return this._groupList.elements();
    }

    /**
     * Method enumeratePanel.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.Panel elements
     */
    public java.util.Enumeration enumeratePanel(
    ) {
        return this._panelList.elements();
    }

    /**
     * Method enumerateTabPanel.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.TabPanel elements
     */
    public java.util.Enumeration enumerateTabPanel(
    ) {
        return this._tabPanelList.elements();
    }

    /**
     * Returns the value of field 'description'.
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Method getFields.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the java.lang.String at the given index
     */
    public java.lang.String getFields(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._fields.size()) {
            throw new IndexOutOfBoundsException("getFields: Index value '" + index + "' not in range [0.." + (this._fields.size() - 1) + "]");
        }
        
        return (java.lang.String) _fields.get(index);
    }

    /**
     * Method getFields.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public java.lang.String[] getFields(
    ) {
        java.lang.String[] array = new java.lang.String[0];
        return (java.lang.String[]) this._fields.toArray(array);
    }

    /**
     * Method getFieldsCount.
     * 
     * @return the size of this collection
     */
    public int getFieldsCount(
    ) {
        return this._fields.size();
    }

    /**
     * Method getGroup.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Group at
     * the given index
     */
    public haiyan.config.castorgen.Group getGroup(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._groupList.size()) {
            throw new IndexOutOfBoundsException("getGroup: Index value '" + index + "' not in range [0.." + (this._groupList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Group) _groupList.get(index);
    }

    /**
     * Method getGroup.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Group[] getGroup(
    ) {
        haiyan.config.castorgen.Group[] array = new haiyan.config.castorgen.Group[0];
        return (haiyan.config.castorgen.Group[]) this._groupList.toArray(array);
    }

    /**
     * Method getGroupCount.
     * 
     * @return the size of this collection
     */
    public int getGroupCount(
    ) {
        return this._groupList.size();
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
     * Returns the value of field 'order'.
     * 
     * @return the value of field 'Order'.
     */
    public int getOrder(
    ) {
        return this._order;
    }

    /**
     * Method getPanel.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Panel at
     * the given index
     */
    public haiyan.config.castorgen.Panel getPanel(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._panelList.size()) {
            throw new IndexOutOfBoundsException("getPanel: Index value '" + index + "' not in range [0.." + (this._panelList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Panel) _panelList.get(index);
    }

    /**
     * Method getPanel.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Panel[] getPanel(
    ) {
        haiyan.config.castorgen.Panel[] array = new haiyan.config.castorgen.Panel[0];
        return (haiyan.config.castorgen.Panel[]) this._panelList.toArray(array);
    }

    /**
     * Method getPanelCount.
     * 
     * @return the size of this collection
     */
    public int getPanelCount(
    ) {
        return this._panelList.size();
    }

    /**
     * Returns the value of field 'style'.
     * 
     * @return the value of field 'Style'.
     */
    public java.lang.String getStyle(
    ) {
        return this._style;
    }

    /**
     * Method getTabPanel.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.TabPanel at
     * the given index
     */
    public haiyan.config.castorgen.TabPanel getTabPanel(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._tabPanelList.size()) {
            throw new IndexOutOfBoundsException("getTabPanel: Index value '" + index + "' not in range [0.." + (this._tabPanelList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.TabPanel) _tabPanelList.get(index);
    }

    /**
     * Method getTabPanel.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.TabPanel[] getTabPanel(
    ) {
        haiyan.config.castorgen.TabPanel[] array = new haiyan.config.castorgen.TabPanel[0];
        return (haiyan.config.castorgen.TabPanel[]) this._tabPanelList.toArray(array);
    }

    /**
     * Method getTabPanelCount.
     * 
     * @return the size of this collection
     */
    public int getTabPanelCount(
    ) {
        return this._tabPanelList.size();
    }

    /**
     * Method hasOrder.
     * 
     * @return true if at least one Order has been added
     */
    public boolean hasOrder(
    ) {
        return this._has_order;
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
    public void removeAllFields(
    ) {
        this._fields.clear();
    }

    /**
     */
    public void removeAllGroup(
    ) {
        this._groupList.clear();
    }

    /**
     */
    public void removeAllPanel(
    ) {
        this._panelList.clear();
    }

    /**
     */
    public void removeAllTabPanel(
    ) {
        this._tabPanelList.clear();
    }

    /**
     * Method removeFields.
     * 
     * @param vFields
     * @return true if the object was removed from the collection.
     */
    public boolean removeFields(
            final java.lang.String vFields) {
        boolean removed = _fields.remove(vFields);
        return removed;
    }

    /**
     * Method removeFieldsAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public java.lang.String removeFieldsAt(
            final int index) {
        java.lang.Object obj = this._fields.remove(index);
        return (java.lang.String) obj;
    }

    /**
     * Method removeGroup.
     * 
     * @param vGroup
     * @return true if the object was removed from the collection.
     */
    public boolean removeGroup(
            final haiyan.config.castorgen.Group vGroup) {
        boolean removed = _groupList.remove(vGroup);
        return removed;
    }

    /**
     * Method removeGroupAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Group removeGroupAt(
            final int index) {
        java.lang.Object obj = this._groupList.remove(index);
        return (haiyan.config.castorgen.Group) obj;
    }

    /**
     * Method removePanel.
     * 
     * @param vPanel
     * @return true if the object was removed from the collection.
     */
    public boolean removePanel(
            final haiyan.config.castorgen.Panel vPanel) {
        boolean removed = _panelList.remove(vPanel);
        return removed;
    }

    /**
     * Method removePanelAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Panel removePanelAt(
            final int index) {
        java.lang.Object obj = this._panelList.remove(index);
        return (haiyan.config.castorgen.Panel) obj;
    }

    /**
     * Method removeTabPanel.
     * 
     * @param vTabPanel
     * @return true if the object was removed from the collection.
     */
    public boolean removeTabPanel(
            final haiyan.config.castorgen.TabPanel vTabPanel) {
        boolean removed = _tabPanelList.remove(vTabPanel);
        return removed;
    }

    /**
     * Method removeTabPanelAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.TabPanel removeTabPanelAt(
            final int index) {
        java.lang.Object obj = this._tabPanelList.remove(index);
        return (haiyan.config.castorgen.TabPanel) obj;
    }

    /**
     * Sets the value of field 'description'.
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * 
     * 
     * @param index
     * @param vFields
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setFields(
            final int index,
            final java.lang.String vFields)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._fields.size()) {
            throw new IndexOutOfBoundsException("setFields: Index value '" + index + "' not in range [0.." + (this._fields.size() - 1) + "]");
        }
        
        this._fields.set(index, vFields);
    }

    /**
     * 
     * 
     * @param vFieldsArray
     */
    public void setFields(
            final java.lang.String[] vFieldsArray) {
        //-- copy array
        _fields.clear();
        
        for (int i = 0; i < vFieldsArray.length; i++) {
                this._fields.add(vFieldsArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vGroup
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setGroup(
            final int index,
            final haiyan.config.castorgen.Group vGroup)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._groupList.size()) {
            throw new IndexOutOfBoundsException("setGroup: Index value '" + index + "' not in range [0.." + (this._groupList.size() - 1) + "]");
        }
        
        this._groupList.set(index, vGroup);
    }

    /**
     * 
     * 
     * @param vGroupArray
     */
    public void setGroup(
            final haiyan.config.castorgen.Group[] vGroupArray) {
        //-- copy array
        _groupList.clear();
        
        for (int i = 0; i < vGroupArray.length; i++) {
                this._groupList.add(vGroupArray[i]);
        }
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
     * Sets the value of field 'order'.
     * 
     * @param order the value of field 'order'.
     */
    public void setOrder(
            final int order) {
        this._order = order;
        this._has_order = true;
    }

    /**
     * 
     * 
     * @param index
     * @param vPanel
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPanel(
            final int index,
            final haiyan.config.castorgen.Panel vPanel)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._panelList.size()) {
            throw new IndexOutOfBoundsException("setPanel: Index value '" + index + "' not in range [0.." + (this._panelList.size() - 1) + "]");
        }
        
        this._panelList.set(index, vPanel);
    }

    /**
     * 
     * 
     * @param vPanelArray
     */
    public void setPanel(
            final haiyan.config.castorgen.Panel[] vPanelArray) {
        //-- copy array
        _panelList.clear();
        
        for (int i = 0; i < vPanelArray.length; i++) {
                this._panelList.add(vPanelArray[i]);
        }
    }

    /**
     * Sets the value of field 'style'.
     * 
     * @param style the value of field 'style'.
     */
    public void setStyle(
            final java.lang.String style) {
        this._style = style;
    }

    /**
     * 
     * 
     * @param index
     * @param vTabPanel
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTabPanel(
            final int index,
            final haiyan.config.castorgen.TabPanel vTabPanel)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._tabPanelList.size()) {
            throw new IndexOutOfBoundsException("setTabPanel: Index value '" + index + "' not in range [0.." + (this._tabPanelList.size() - 1) + "]");
        }
        
        this._tabPanelList.set(index, vTabPanel);
    }

    /**
     * 
     * 
     * @param vTabPanelArray
     */
    public void setTabPanel(
            final haiyan.config.castorgen.TabPanel[] vTabPanelArray) {
        //-- copy array
        _tabPanelList.clear();
        
        for (int i = 0; i < vTabPanelArray.length; i++) {
                this._tabPanelList.add(vTabPanelArray[i]);
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
     * @return the unmarshaled haiyan.config.castorgen.Panel
     */
    public static haiyan.config.castorgen.Panel unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Panel) Unmarshaller.unmarshal(haiyan.config.castorgen.Panel.class, reader);
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
