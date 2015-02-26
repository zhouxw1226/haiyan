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

import haiyan.common.intf.config.ITableConfig;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * table config
 * 
 * @version $Revision$ $Date$
 */
public class Table implements ITableConfig, java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * table name
     */
    private java.lang.String _name;

    /**
     * table description
     */
    private java.lang.String _description;

    /**
     * table db realname
     */
    private java.lang.String _realName;

    /**
     * child tablename
     */
    private java.lang.String _childTable;

    /**
     * reference field of child table
     */
    private java.lang.String _childTableRefField;

    /**
     * parent tablename
     */
    private java.lang.String _parentTable;

    /**
     * reference field of parent table
     *  
     */
    private java.lang.String _parentTableRefField;

    /**
     * database source
     */
    private java.lang.String _dbSource;

    /**
     * table colspan
     */
    private int _colspan = 4;

    /**
     * keeps track of state for field: _colspan
     */
    private boolean _has_colspan;

    /**
     * bill module ID
     */
    private java.lang.String _modelid;

    /**
     * bill module CODE
     */
    private java.lang.String _modelcode;

    /**
     * table's pk
     */
    private haiyan.config.castorgen.Id _id;

    /**
     * table's db field
     */
    private java.util.Vector _fieldList;

    /**
     * Field _panelList.
     */
    private java.util.Vector _panelList;

    /**
     * Field _tabPanelList.
     */
    private java.util.Vector _tabPanelList;

    /**
     * display view
     */
    private java.util.Vector _pageViewList;

    /**
     * select sql
     */
    private haiyan.config.castorgen.QuerySQL _querySQL;

    /**
     * table filter
     */
    private java.util.Vector _queryFilterList;

    /**
     * system role right
     */
    private java.util.Vector _roleList;

    /**
     * javascript of page
     */
    private java.util.Vector _javascriptList;

    /**
     * singleton selectable judge plugin
     *  
     */
    private java.util.Vector _isSelectableList;

    /**
     * singleton showable judge plugin
     */
    private java.util.Vector _isShowableList;

    /**
     * intercepotor of one plugin
     */
    private java.util.Vector _pluginInterceptorList;

    /**
     * plugin parameter
     */
    private java.util.Vector _pluginOperationList;

    /**
     * operation setting
     */
    private haiyan.config.castorgen.Operation _operation;

    /**
     * data rule
     */
    private java.util.Vector _dataRulesList;

    /**
     * Field _doc.
     */
    private haiyan.config.castorgen.Doc _doc;


      //----------------/
     //- Constructors -/
    //----------------/

    public Table() {
        super();
        this._fieldList = new java.util.Vector();
        this._panelList = new java.util.Vector();
        this._tabPanelList = new java.util.Vector();
        this._pageViewList = new java.util.Vector();
        this._queryFilterList = new java.util.Vector();
        this._roleList = new java.util.Vector();
        this._javascriptList = new java.util.Vector();
        this._isSelectableList = new java.util.Vector();
        this._isShowableList = new java.util.Vector();
        this._pluginInterceptorList = new java.util.Vector();
        this._pluginOperationList = new java.util.Vector();
        this._dataRulesList = new java.util.Vector();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vDataRules
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDataRules(
            final haiyan.config.castorgen.DataRules vDataRules)
    throws java.lang.IndexOutOfBoundsException {
        this._dataRulesList.addElement(vDataRules);
    }

    /**
     * 
     * 
     * @param index
     * @param vDataRules
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addDataRules(
            final int index,
            final haiyan.config.castorgen.DataRules vDataRules)
    throws java.lang.IndexOutOfBoundsException {
        this._dataRulesList.add(index, vDataRules);
    }

    /**
     * 
     * 
     * @param vField
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addField(
            final haiyan.config.castorgen.Field vField)
    throws java.lang.IndexOutOfBoundsException {
        this._fieldList.addElement(vField);
    }

    /**
     * 
     * 
     * @param index
     * @param vField
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addField(
            final int index,
            final haiyan.config.castorgen.Field vField)
    throws java.lang.IndexOutOfBoundsException {
        this._fieldList.add(index, vField);
    }

    /**
     * 
     * 
     * @param vIsSelectable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addIsSelectable(
            final haiyan.config.castorgen.IsSelectable vIsSelectable)
    throws java.lang.IndexOutOfBoundsException {
        this._isSelectableList.addElement(vIsSelectable);
    }

    /**
     * 
     * 
     * @param index
     * @param vIsSelectable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addIsSelectable(
            final int index,
            final haiyan.config.castorgen.IsSelectable vIsSelectable)
    throws java.lang.IndexOutOfBoundsException {
        this._isSelectableList.add(index, vIsSelectable);
    }

    /**
     * 
     * 
     * @param vIsShowable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addIsShowable(
            final haiyan.config.castorgen.IsShowable vIsShowable)
    throws java.lang.IndexOutOfBoundsException {
        this._isShowableList.addElement(vIsShowable);
    }

    /**
     * 
     * 
     * @param index
     * @param vIsShowable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addIsShowable(
            final int index,
            final haiyan.config.castorgen.IsShowable vIsShowable)
    throws java.lang.IndexOutOfBoundsException {
        this._isShowableList.add(index, vIsShowable);
    }

    /**
     * 
     * 
     * @param vJavascript
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJavascript(
            final haiyan.config.castorgen.Javascript vJavascript)
    throws java.lang.IndexOutOfBoundsException {
        this._javascriptList.addElement(vJavascript);
    }

    /**
     * 
     * 
     * @param index
     * @param vJavascript
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addJavascript(
            final int index,
            final haiyan.config.castorgen.Javascript vJavascript)
    throws java.lang.IndexOutOfBoundsException {
        this._javascriptList.add(index, vJavascript);
    }

    /**
     * 
     * 
     * @param vPageView
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPageView(
            final haiyan.config.castorgen.PageView vPageView)
    throws java.lang.IndexOutOfBoundsException {
        this._pageViewList.addElement(vPageView);
    }

    /**
     * 
     * 
     * @param index
     * @param vPageView
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPageView(
            final int index,
            final haiyan.config.castorgen.PageView vPageView)
    throws java.lang.IndexOutOfBoundsException {
        this._pageViewList.add(index, vPageView);
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
     * @param vPluginInterceptor
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPluginInterceptor(
            final haiyan.config.castorgen.PluginInterceptor vPluginInterceptor)
    throws java.lang.IndexOutOfBoundsException {
        this._pluginInterceptorList.addElement(vPluginInterceptor);
    }

    /**
     * 
     * 
     * @param index
     * @param vPluginInterceptor
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPluginInterceptor(
            final int index,
            final haiyan.config.castorgen.PluginInterceptor vPluginInterceptor)
    throws java.lang.IndexOutOfBoundsException {
        this._pluginInterceptorList.add(index, vPluginInterceptor);
    }

    /**
     * 
     * 
     * @param vPluginOperation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPluginOperation(
            final haiyan.config.castorgen.PluginOperation vPluginOperation)
    throws java.lang.IndexOutOfBoundsException {
        this._pluginOperationList.addElement(vPluginOperation);
    }

    /**
     * 
     * 
     * @param index
     * @param vPluginOperation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPluginOperation(
            final int index,
            final haiyan.config.castorgen.PluginOperation vPluginOperation)
    throws java.lang.IndexOutOfBoundsException {
        this._pluginOperationList.add(index, vPluginOperation);
    }

    /**
     * 
     * 
     * @param vQueryFilter
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addQueryFilter(
            final haiyan.config.castorgen.QueryFilter vQueryFilter)
    throws java.lang.IndexOutOfBoundsException {
        this._queryFilterList.addElement(vQueryFilter);
    }

    /**
     * 
     * 
     * @param index
     * @param vQueryFilter
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addQueryFilter(
            final int index,
            final haiyan.config.castorgen.QueryFilter vQueryFilter)
    throws java.lang.IndexOutOfBoundsException {
        this._queryFilterList.add(index, vQueryFilter);
    }

    /**
     * 
     * 
     * @param vRole
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRole(
            final haiyan.config.castorgen.Role vRole)
    throws java.lang.IndexOutOfBoundsException {
        this._roleList.addElement(vRole);
    }

    /**
     * 
     * 
     * @param index
     * @param vRole
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRole(
            final int index,
            final haiyan.config.castorgen.Role vRole)
    throws java.lang.IndexOutOfBoundsException {
        this._roleList.add(index, vRole);
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
    public void deleteColspan(
    ) {
        this._has_colspan= false;
    }

    /**
     * Method enumerateDataRules.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.DataRules elements
     */
    public java.util.Enumeration enumerateDataRules(
    ) {
        return this._dataRulesList.elements();
    }

    /**
     * Method enumerateField.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.Field elements
     */
    public java.util.Enumeration enumerateField(
    ) {
        return this._fieldList.elements();
    }

    /**
     * Method enumerateIsSelectable.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.IsSelectable elements
     */
    public java.util.Enumeration enumerateIsSelectable(
    ) {
        return this._isSelectableList.elements();
    }

    /**
     * Method enumerateIsShowable.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.IsShowable elements
     */
    public java.util.Enumeration enumerateIsShowable(
    ) {
        return this._isShowableList.elements();
    }

    /**
     * Method enumerateJavascript.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.Javascript elements
     */
    public java.util.Enumeration enumerateJavascript(
    ) {
        return this._javascriptList.elements();
    }

    /**
     * Method enumeratePageView.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.PageView elements
     */
    public java.util.Enumeration enumeratePageView(
    ) {
        return this._pageViewList.elements();
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
     * Method enumeratePluginInterceptor.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.PluginInterceptor elements
     */
    public java.util.Enumeration enumeratePluginInterceptor(
    ) {
        return this._pluginInterceptorList.elements();
    }

    /**
     * Method enumeratePluginOperation.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.PluginOperation elements
     */
    public java.util.Enumeration enumeratePluginOperation(
    ) {
        return this._pluginOperationList.elements();
    }

    /**
     * Method enumerateQueryFilter.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.QueryFilter elements
     */
    public java.util.Enumeration enumerateQueryFilter(
    ) {
        return this._queryFilterList.elements();
    }

    /**
     * Method enumerateRole.
     * 
     * @return an Enumeration over all haiyan.config.castorgen.Role
     * elements
     */
    public java.util.Enumeration enumerateRole(
    ) {
        return this._roleList.elements();
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
     * Returns the value of field 'childTable'. The field
     * 'childTable' has the following description: child tablename
     * 
     * @return the value of field 'ChildTable'.
     */
    public java.lang.String getChildTable(
    ) {
        return this._childTable;
    }

    /**
     * Returns the value of field 'childTableRefField'. The field
     * 'childTableRefField' has the following description:
     * reference field of child table
     * 
     * @return the value of field 'ChildTableRefField'.
     */
    public java.lang.String getChildTableRefField(
    ) {
        return this._childTableRefField;
    }

    /**
     * Returns the value of field 'colspan'. The field 'colspan'
     * has the following description: table colspan
     * 
     * @return the value of field 'Colspan'.
     */
    public int getColspan(
    ) {
        return this._colspan;
    }

    /**
     * Method getDataRules.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.DataRules
     * at the given index
     */
    public haiyan.config.castorgen.DataRules getDataRules(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._dataRulesList.size()) {
            throw new IndexOutOfBoundsException("getDataRules: Index value '" + index + "' not in range [0.." + (this._dataRulesList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.DataRules) _dataRulesList.get(index);
    }

    /**
     * Method getDataRules.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.DataRules[] getDataRules(
    ) {
        haiyan.config.castorgen.DataRules[] array = new haiyan.config.castorgen.DataRules[0];
        return (haiyan.config.castorgen.DataRules[]) this._dataRulesList.toArray(array);
    }

    /**
     * Method getDataRulesCount.
     * 
     * @return the size of this collection
     */
    public int getDataRulesCount(
    ) {
        return this._dataRulesList.size();
    }

    /**
     * Returns the value of field 'dbSource'. The field 'dbSource'
     * has the following description: database source
     * 
     * @return the value of field 'DbSource'.
     */
    public java.lang.String getDbSource(
    ) {
        return this._dbSource;
    }

    /**
     * Returns the value of field 'description'. The field
     * 'description' has the following description: table
     * description
     * 
     * @return the value of field 'Description'.
     */
    public java.lang.String getDescription(
    ) {
        return this._description;
    }

    /**
     * Returns the value of field 'doc'.
     * 
     * @return the value of field 'Doc'.
     */
    public haiyan.config.castorgen.Doc getDoc(
    ) {
        return this._doc;
    }

    /**
     * Method getField.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Field at
     * the given index
     */
    public haiyan.config.castorgen.Field getField(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._fieldList.size()) {
            throw new IndexOutOfBoundsException("getField: Index value '" + index + "' not in range [0.." + (this._fieldList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Field) _fieldList.get(index);
    }

    /**
     * Method getField.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Field[] getField(
    ) {
        haiyan.config.castorgen.Field[] array = new haiyan.config.castorgen.Field[0];
        return (haiyan.config.castorgen.Field[]) this._fieldList.toArray(array);
    }

    /**
     * Method getFieldCount.
     * 
     * @return the size of this collection
     */
    public int getFieldCount(
    ) {
        return this._fieldList.size();
    }

    /**
     * Returns the value of field 'id'. The field 'id' has the
     * following description: table's pk
     * 
     * @return the value of field 'Id'.
     */
    public haiyan.config.castorgen.Id getId(
    ) {
        return this._id;
    }

    /**
     * Method getIsSelectable.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.IsSelectable at the given index
     */
    public haiyan.config.castorgen.IsSelectable getIsSelectable(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._isSelectableList.size()) {
            throw new IndexOutOfBoundsException("getIsSelectable: Index value '" + index + "' not in range [0.." + (this._isSelectableList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.IsSelectable) _isSelectableList.get(index);
    }

    /**
     * Method getIsSelectable.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.IsSelectable[] getIsSelectable(
    ) {
        haiyan.config.castorgen.IsSelectable[] array = new haiyan.config.castorgen.IsSelectable[0];
        return (haiyan.config.castorgen.IsSelectable[]) this._isSelectableList.toArray(array);
    }

    /**
     * Method getIsSelectableCount.
     * 
     * @return the size of this collection
     */
    public int getIsSelectableCount(
    ) {
        return this._isSelectableList.size();
    }

    /**
     * Method getIsShowable.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.IsShowable
     * at the given index
     */
    public haiyan.config.castorgen.IsShowable getIsShowable(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._isShowableList.size()) {
            throw new IndexOutOfBoundsException("getIsShowable: Index value '" + index + "' not in range [0.." + (this._isShowableList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.IsShowable) _isShowableList.get(index);
    }

    /**
     * Method getIsShowable.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.IsShowable[] getIsShowable(
    ) {
        haiyan.config.castorgen.IsShowable[] array = new haiyan.config.castorgen.IsShowable[0];
        return (haiyan.config.castorgen.IsShowable[]) this._isShowableList.toArray(array);
    }

    /**
     * Method getIsShowableCount.
     * 
     * @return the size of this collection
     */
    public int getIsShowableCount(
    ) {
        return this._isShowableList.size();
    }

    /**
     * Method getJavascript.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Javascript
     * at the given index
     */
    public haiyan.config.castorgen.Javascript getJavascript(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._javascriptList.size()) {
            throw new IndexOutOfBoundsException("getJavascript: Index value '" + index + "' not in range [0.." + (this._javascriptList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Javascript) _javascriptList.get(index);
    }

    /**
     * Method getJavascript.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Javascript[] getJavascript(
    ) {
        haiyan.config.castorgen.Javascript[] array = new haiyan.config.castorgen.Javascript[0];
        return (haiyan.config.castorgen.Javascript[]) this._javascriptList.toArray(array);
    }

    /**
     * Method getJavascriptCount.
     * 
     * @return the size of this collection
     */
    public int getJavascriptCount(
    ) {
        return this._javascriptList.size();
    }

    /**
     * Returns the value of field 'modelcode'. The field
     * 'modelcode' has the following description: bill module CODE
     * 
     * @return the value of field 'Modelcode'.
     */
    public java.lang.String getModelcode(
    ) {
        return this._modelcode;
    }

    /**
     * Returns the value of field 'modelid'. The field 'modelid'
     * has the following description: bill module ID
     * 
     * @return the value of field 'Modelid'.
     */
    public java.lang.String getModelid(
    ) {
        return this._modelid;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: table name
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'operation'. The field
     * 'operation' has the following description: operation setting
     * 
     * @return the value of field 'Operation'.
     */
    public haiyan.config.castorgen.Operation getOperation(
    ) {
        return this._operation;
    }

    /**
     * Method getPageView.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.PageView at
     * the given index
     */
    public haiyan.config.castorgen.PageView getPageView(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pageViewList.size()) {
            throw new IndexOutOfBoundsException("getPageView: Index value '" + index + "' not in range [0.." + (this._pageViewList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.PageView) _pageViewList.get(index);
    }

    /**
     * Method getPageView.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.PageView[] getPageView(
    ) {
        haiyan.config.castorgen.PageView[] array = new haiyan.config.castorgen.PageView[0];
        return (haiyan.config.castorgen.PageView[]) this._pageViewList.toArray(array);
    }

    /**
     * Method getPageViewCount.
     * 
     * @return the size of this collection
     */
    public int getPageViewCount(
    ) {
        return this._pageViewList.size();
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
     * Returns the value of field 'parentTable'. The field
     * 'parentTable' has the following description: parent
     * tablename
     * 
     * @return the value of field 'ParentTable'.
     */
    public java.lang.String getParentTable(
    ) {
        return this._parentTable;
    }

    /**
     * Returns the value of field 'parentTableRefField'. The field
     * 'parentTableRefField' has the following description:
     * reference field of parent table
     *  
     * 
     * @return the value of field 'ParentTableRefField'.
     */
    public java.lang.String getParentTableRefField(
    ) {
        return this._parentTableRefField;
    }

    /**
     * Method getPluginInterceptor.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.PluginInterceptor at the given index
     */
    public haiyan.config.castorgen.PluginInterceptor getPluginInterceptor(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pluginInterceptorList.size()) {
            throw new IndexOutOfBoundsException("getPluginInterceptor: Index value '" + index + "' not in range [0.." + (this._pluginInterceptorList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.PluginInterceptor) _pluginInterceptorList.get(index);
    }

    /**
     * Method getPluginInterceptor.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.PluginInterceptor[] getPluginInterceptor(
    ) {
        haiyan.config.castorgen.PluginInterceptor[] array = new haiyan.config.castorgen.PluginInterceptor[0];
        return (haiyan.config.castorgen.PluginInterceptor[]) this._pluginInterceptorList.toArray(array);
    }

    /**
     * Method getPluginInterceptorCount.
     * 
     * @return the size of this collection
     */
    public int getPluginInterceptorCount(
    ) {
        return this._pluginInterceptorList.size();
    }

    /**
     * Method getPluginOperation.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * haiyan.config.castorgen.PluginOperation at the given index
     */
    public haiyan.config.castorgen.PluginOperation getPluginOperation(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pluginOperationList.size()) {
            throw new IndexOutOfBoundsException("getPluginOperation: Index value '" + index + "' not in range [0.." + (this._pluginOperationList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.PluginOperation) _pluginOperationList.get(index);
    }

    /**
     * Method getPluginOperation.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.PluginOperation[] getPluginOperation(
    ) {
        haiyan.config.castorgen.PluginOperation[] array = new haiyan.config.castorgen.PluginOperation[0];
        return (haiyan.config.castorgen.PluginOperation[]) this._pluginOperationList.toArray(array);
    }

    /**
     * Method getPluginOperationCount.
     * 
     * @return the size of this collection
     */
    public int getPluginOperationCount(
    ) {
        return this._pluginOperationList.size();
    }

    /**
     * Method getQueryFilter.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.QueryFilter
     * at the given index
     */
    public haiyan.config.castorgen.QueryFilter getQueryFilter(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._queryFilterList.size()) {
            throw new IndexOutOfBoundsException("getQueryFilter: Index value '" + index + "' not in range [0.." + (this._queryFilterList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.QueryFilter) _queryFilterList.get(index);
    }

    /**
     * Method getQueryFilter.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.QueryFilter[] getQueryFilter(
    ) {
        haiyan.config.castorgen.QueryFilter[] array = new haiyan.config.castorgen.QueryFilter[0];
        return (haiyan.config.castorgen.QueryFilter[]) this._queryFilterList.toArray(array);
    }

    /**
     * Method getQueryFilterCount.
     * 
     * @return the size of this collection
     */
    public int getQueryFilterCount(
    ) {
        return this._queryFilterList.size();
    }

    /**
     * Returns the value of field 'querySQL'. The field 'querySQL'
     * has the following description: select sql
     * 
     * @return the value of field 'QuerySQL'.
     */
    public haiyan.config.castorgen.QuerySQL getQuerySQL(
    ) {
        return this._querySQL;
    }

    /**
     * Returns the value of field 'realName'. The field 'realName'
     * has the following description: table db realname
     * 
     * @return the value of field 'RealName'.
     */
    public java.lang.String getRealName(
    ) {
        return this._realName;
    }

    /**
     * Method getRole.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.Role at the
     * given index
     */
    public haiyan.config.castorgen.Role getRole(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._roleList.size()) {
            throw new IndexOutOfBoundsException("getRole: Index value '" + index + "' not in range [0.." + (this._roleList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.Role) _roleList.get(index);
    }

    /**
     * Method getRole.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.Role[] getRole(
    ) {
        haiyan.config.castorgen.Role[] array = new haiyan.config.castorgen.Role[0];
        return (haiyan.config.castorgen.Role[]) this._roleList.toArray(array);
    }

    /**
     * Method getRoleCount.
     * 
     * @return the size of this collection
     */
    public int getRoleCount(
    ) {
        return this._roleList.size();
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
     * Method hasColspan.
     * 
     * @return true if at least one Colspan has been added
     */
    public boolean hasColspan(
    ) {
        return this._has_colspan;
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
    public void removeAllDataRules(
    ) {
        this._dataRulesList.clear();
    }

    /**
     */
    public void removeAllField(
    ) {
        this._fieldList.clear();
    }

    /**
     */
    public void removeAllIsSelectable(
    ) {
        this._isSelectableList.clear();
    }

    /**
     */
    public void removeAllIsShowable(
    ) {
        this._isShowableList.clear();
    }

    /**
     */
    public void removeAllJavascript(
    ) {
        this._javascriptList.clear();
    }

    /**
     */
    public void removeAllPageView(
    ) {
        this._pageViewList.clear();
    }

    /**
     */
    public void removeAllPanel(
    ) {
        this._panelList.clear();
    }

    /**
     */
    public void removeAllPluginInterceptor(
    ) {
        this._pluginInterceptorList.clear();
    }

    /**
     */
    public void removeAllPluginOperation(
    ) {
        this._pluginOperationList.clear();
    }

    /**
     */
    public void removeAllQueryFilter(
    ) {
        this._queryFilterList.clear();
    }

    /**
     */
    public void removeAllRole(
    ) {
        this._roleList.clear();
    }

    /**
     */
    public void removeAllTabPanel(
    ) {
        this._tabPanelList.clear();
    }

    /**
     * Method removeDataRules.
     * 
     * @param vDataRules
     * @return true if the object was removed from the collection.
     */
    public boolean removeDataRules(
            final haiyan.config.castorgen.DataRules vDataRules) {
        boolean removed = _dataRulesList.remove(vDataRules);
        return removed;
    }

    /**
     * Method removeDataRulesAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.DataRules removeDataRulesAt(
            final int index) {
        java.lang.Object obj = this._dataRulesList.remove(index);
        return (haiyan.config.castorgen.DataRules) obj;
    }

    /**
     * Method removeField.
     * 
     * @param vField
     * @return true if the object was removed from the collection.
     */
    public boolean removeField(
            final haiyan.config.castorgen.Field vField) {
        boolean removed = _fieldList.remove(vField);
        return removed;
    }

    /**
     * Method removeFieldAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Field removeFieldAt(
            final int index) {
        java.lang.Object obj = this._fieldList.remove(index);
        return (haiyan.config.castorgen.Field) obj;
    }

    /**
     * Method removeIsSelectable.
     * 
     * @param vIsSelectable
     * @return true if the object was removed from the collection.
     */
    public boolean removeIsSelectable(
            final haiyan.config.castorgen.IsSelectable vIsSelectable) {
        boolean removed = _isSelectableList.remove(vIsSelectable);
        return removed;
    }

    /**
     * Method removeIsSelectableAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.IsSelectable removeIsSelectableAt(
            final int index) {
        java.lang.Object obj = this._isSelectableList.remove(index);
        return (haiyan.config.castorgen.IsSelectable) obj;
    }

    /**
     * Method removeIsShowable.
     * 
     * @param vIsShowable
     * @return true if the object was removed from the collection.
     */
    public boolean removeIsShowable(
            final haiyan.config.castorgen.IsShowable vIsShowable) {
        boolean removed = _isShowableList.remove(vIsShowable);
        return removed;
    }

    /**
     * Method removeIsShowableAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.IsShowable removeIsShowableAt(
            final int index) {
        java.lang.Object obj = this._isShowableList.remove(index);
        return (haiyan.config.castorgen.IsShowable) obj;
    }

    /**
     * Method removeJavascript.
     * 
     * @param vJavascript
     * @return true if the object was removed from the collection.
     */
    public boolean removeJavascript(
            final haiyan.config.castorgen.Javascript vJavascript) {
        boolean removed = _javascriptList.remove(vJavascript);
        return removed;
    }

    /**
     * Method removeJavascriptAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Javascript removeJavascriptAt(
            final int index) {
        java.lang.Object obj = this._javascriptList.remove(index);
        return (haiyan.config.castorgen.Javascript) obj;
    }

    /**
     * Method removePageView.
     * 
     * @param vPageView
     * @return true if the object was removed from the collection.
     */
    public boolean removePageView(
            final haiyan.config.castorgen.PageView vPageView) {
        boolean removed = _pageViewList.remove(vPageView);
        return removed;
    }

    /**
     * Method removePageViewAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.PageView removePageViewAt(
            final int index) {
        java.lang.Object obj = this._pageViewList.remove(index);
        return (haiyan.config.castorgen.PageView) obj;
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
     * Method removePluginInterceptor.
     * 
     * @param vPluginInterceptor
     * @return true if the object was removed from the collection.
     */
    public boolean removePluginInterceptor(
            final haiyan.config.castorgen.PluginInterceptor vPluginInterceptor) {
        boolean removed = _pluginInterceptorList.remove(vPluginInterceptor);
        return removed;
    }

    /**
     * Method removePluginInterceptorAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.PluginInterceptor removePluginInterceptorAt(
            final int index) {
        java.lang.Object obj = this._pluginInterceptorList.remove(index);
        return (haiyan.config.castorgen.PluginInterceptor) obj;
    }

    /**
     * Method removePluginOperation.
     * 
     * @param vPluginOperation
     * @return true if the object was removed from the collection.
     */
    public boolean removePluginOperation(
            final haiyan.config.castorgen.PluginOperation vPluginOperation) {
        boolean removed = _pluginOperationList.remove(vPluginOperation);
        return removed;
    }

    /**
     * Method removePluginOperationAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.PluginOperation removePluginOperationAt(
            final int index) {
        java.lang.Object obj = this._pluginOperationList.remove(index);
        return (haiyan.config.castorgen.PluginOperation) obj;
    }

    /**
     * Method removeQueryFilter.
     * 
     * @param vQueryFilter
     * @return true if the object was removed from the collection.
     */
    public boolean removeQueryFilter(
            final haiyan.config.castorgen.QueryFilter vQueryFilter) {
        boolean removed = _queryFilterList.remove(vQueryFilter);
        return removed;
    }

    /**
     * Method removeQueryFilterAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.QueryFilter removeQueryFilterAt(
            final int index) {
        java.lang.Object obj = this._queryFilterList.remove(index);
        return (haiyan.config.castorgen.QueryFilter) obj;
    }

    /**
     * Method removeRole.
     * 
     * @param vRole
     * @return true if the object was removed from the collection.
     */
    public boolean removeRole(
            final haiyan.config.castorgen.Role vRole) {
        boolean removed = _roleList.remove(vRole);
        return removed;
    }

    /**
     * Method removeRoleAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.Role removeRoleAt(
            final int index) {
        java.lang.Object obj = this._roleList.remove(index);
        return (haiyan.config.castorgen.Role) obj;
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
     * Sets the value of field 'childTable'. The field 'childTable'
     * has the following description: child tablename
     * 
     * @param childTable the value of field 'childTable'.
     */
    public void setChildTable(
            final java.lang.String childTable) {
        this._childTable = childTable;
    }

    /**
     * Sets the value of field 'childTableRefField'. The field
     * 'childTableRefField' has the following description:
     * reference field of child table
     * 
     * @param childTableRefField the value of field
     * 'childTableRefField'.
     */
    public void setChildTableRefField(
            final java.lang.String childTableRefField) {
        this._childTableRefField = childTableRefField;
    }

    /**
     * Sets the value of field 'colspan'. The field 'colspan' has
     * the following description: table colspan
     * 
     * @param colspan the value of field 'colspan'.
     */
    public void setColspan(
            final int colspan) {
        this._colspan = colspan;
        this._has_colspan = true;
    }

    /**
     * 
     * 
     * @param index
     * @param vDataRules
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setDataRules(
            final int index,
            final haiyan.config.castorgen.DataRules vDataRules)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._dataRulesList.size()) {
            throw new IndexOutOfBoundsException("setDataRules: Index value '" + index + "' not in range [0.." + (this._dataRulesList.size() - 1) + "]");
        }
        
        this._dataRulesList.set(index, vDataRules);
    }

    /**
     * 
     * 
     * @param vDataRulesArray
     */
    public void setDataRules(
            final haiyan.config.castorgen.DataRules[] vDataRulesArray) {
        //-- copy array
        _dataRulesList.clear();
        
        for (int i = 0; i < vDataRulesArray.length; i++) {
                this._dataRulesList.add(vDataRulesArray[i]);
        }
    }

    /**
     * Sets the value of field 'dbSource'. The field 'dbSource' has
     * the following description: database source
     * 
     * @param dbSource the value of field 'dbSource'.
     */
    public void setDbSource(
            final java.lang.String dbSource) {
        this._dbSource = dbSource;
    }

    /**
     * Sets the value of field 'description'. The field
     * 'description' has the following description: table
     * description
     * 
     * @param description the value of field 'description'.
     */
    public void setDescription(
            final java.lang.String description) {
        this._description = description;
    }

    /**
     * Sets the value of field 'doc'.
     * 
     * @param doc the value of field 'doc'.
     */
    public void setDoc(
            final haiyan.config.castorgen.Doc doc) {
        this._doc = doc;
    }

    /**
     * 
     * 
     * @param index
     * @param vField
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setField(
            final int index,
            final haiyan.config.castorgen.Field vField)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._fieldList.size()) {
            throw new IndexOutOfBoundsException("setField: Index value '" + index + "' not in range [0.." + (this._fieldList.size() - 1) + "]");
        }
        
        this._fieldList.set(index, vField);
    }

    /**
     * 
     * 
     * @param vFieldArray
     */
    public void setField(
            final haiyan.config.castorgen.Field[] vFieldArray) {
        //-- copy array
        _fieldList.clear();
        
        for (int i = 0; i < vFieldArray.length; i++) {
                this._fieldList.add(vFieldArray[i]);
        }
    }

    /**
     * Sets the value of field 'id'. The field 'id' has the
     * following description: table's pk
     * 
     * @param id the value of field 'id'.
     */
    public void setId(
            final haiyan.config.castorgen.Id id) {
        this._id = id;
    }

    /**
     * 
     * 
     * @param index
     * @param vIsSelectable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setIsSelectable(
            final int index,
            final haiyan.config.castorgen.IsSelectable vIsSelectable)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._isSelectableList.size()) {
            throw new IndexOutOfBoundsException("setIsSelectable: Index value '" + index + "' not in range [0.." + (this._isSelectableList.size() - 1) + "]");
        }
        
        this._isSelectableList.set(index, vIsSelectable);
    }

    /**
     * 
     * 
     * @param vIsSelectableArray
     */
    public void setIsSelectable(
            final haiyan.config.castorgen.IsSelectable[] vIsSelectableArray) {
        //-- copy array
        _isSelectableList.clear();
        
        for (int i = 0; i < vIsSelectableArray.length; i++) {
                this._isSelectableList.add(vIsSelectableArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vIsShowable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setIsShowable(
            final int index,
            final haiyan.config.castorgen.IsShowable vIsShowable)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._isShowableList.size()) {
            throw new IndexOutOfBoundsException("setIsShowable: Index value '" + index + "' not in range [0.." + (this._isShowableList.size() - 1) + "]");
        }
        
        this._isShowableList.set(index, vIsShowable);
    }

    /**
     * 
     * 
     * @param vIsShowableArray
     */
    public void setIsShowable(
            final haiyan.config.castorgen.IsShowable[] vIsShowableArray) {
        //-- copy array
        _isShowableList.clear();
        
        for (int i = 0; i < vIsShowableArray.length; i++) {
                this._isShowableList.add(vIsShowableArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vJavascript
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setJavascript(
            final int index,
            final haiyan.config.castorgen.Javascript vJavascript)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._javascriptList.size()) {
            throw new IndexOutOfBoundsException("setJavascript: Index value '" + index + "' not in range [0.." + (this._javascriptList.size() - 1) + "]");
        }
        
        this._javascriptList.set(index, vJavascript);
    }

    /**
     * 
     * 
     * @param vJavascriptArray
     */
    public void setJavascript(
            final haiyan.config.castorgen.Javascript[] vJavascriptArray) {
        //-- copy array
        _javascriptList.clear();
        
        for (int i = 0; i < vJavascriptArray.length; i++) {
                this._javascriptList.add(vJavascriptArray[i]);
        }
    }

    /**
     * Sets the value of field 'modelcode'. The field 'modelcode'
     * has the following description: bill module CODE
     * 
     * @param modelcode the value of field 'modelcode'.
     */
    public void setModelcode(
            final java.lang.String modelcode) {
        this._modelcode = modelcode;
    }

    /**
     * Sets the value of field 'modelid'. The field 'modelid' has
     * the following description: bill module ID
     * 
     * @param modelid the value of field 'modelid'.
     */
    public void setModelid(
            final java.lang.String modelid) {
        this._modelid = modelid;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: table name
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'operation'. The field 'operation'
     * has the following description: operation setting
     * 
     * @param operation the value of field 'operation'.
     */
    public void setOperation(
            final haiyan.config.castorgen.Operation operation) {
        this._operation = operation;
    }

    /**
     * 
     * 
     * @param index
     * @param vPageView
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPageView(
            final int index,
            final haiyan.config.castorgen.PageView vPageView)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pageViewList.size()) {
            throw new IndexOutOfBoundsException("setPageView: Index value '" + index + "' not in range [0.." + (this._pageViewList.size() - 1) + "]");
        }
        
        this._pageViewList.set(index, vPageView);
    }

    /**
     * 
     * 
     * @param vPageViewArray
     */
    public void setPageView(
            final haiyan.config.castorgen.PageView[] vPageViewArray) {
        //-- copy array
        _pageViewList.clear();
        
        for (int i = 0; i < vPageViewArray.length; i++) {
                this._pageViewList.add(vPageViewArray[i]);
        }
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
     * Sets the value of field 'parentTable'. The field
     * 'parentTable' has the following description: parent
     * tablename
     * 
     * @param parentTable the value of field 'parentTable'.
     */
    public void setParentTable(
            final java.lang.String parentTable) {
        this._parentTable = parentTable;
    }

    /**
     * Sets the value of field 'parentTableRefField'. The field
     * 'parentTableRefField' has the following description:
     * reference field of parent table
     *  
     * 
     * @param parentTableRefField the value of field
     * 'parentTableRefField'.
     */
    public void setParentTableRefField(
            final java.lang.String parentTableRefField) {
        this._parentTableRefField = parentTableRefField;
    }

    /**
     * 
     * 
     * @param index
     * @param vPluginInterceptor
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPluginInterceptor(
            final int index,
            final haiyan.config.castorgen.PluginInterceptor vPluginInterceptor)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pluginInterceptorList.size()) {
            throw new IndexOutOfBoundsException("setPluginInterceptor: Index value '" + index + "' not in range [0.." + (this._pluginInterceptorList.size() - 1) + "]");
        }
        
        this._pluginInterceptorList.set(index, vPluginInterceptor);
    }

    /**
     * 
     * 
     * @param vPluginInterceptorArray
     */
    public void setPluginInterceptor(
            final haiyan.config.castorgen.PluginInterceptor[] vPluginInterceptorArray) {
        //-- copy array
        _pluginInterceptorList.clear();
        
        for (int i = 0; i < vPluginInterceptorArray.length; i++) {
                this._pluginInterceptorList.add(vPluginInterceptorArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vPluginOperation
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPluginOperation(
            final int index,
            final haiyan.config.castorgen.PluginOperation vPluginOperation)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pluginOperationList.size()) {
            throw new IndexOutOfBoundsException("setPluginOperation: Index value '" + index + "' not in range [0.." + (this._pluginOperationList.size() - 1) + "]");
        }
        
        this._pluginOperationList.set(index, vPluginOperation);
    }

    /**
     * 
     * 
     * @param vPluginOperationArray
     */
    public void setPluginOperation(
            final haiyan.config.castorgen.PluginOperation[] vPluginOperationArray) {
        //-- copy array
        _pluginOperationList.clear();
        
        for (int i = 0; i < vPluginOperationArray.length; i++) {
                this._pluginOperationList.add(vPluginOperationArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vQueryFilter
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setQueryFilter(
            final int index,
            final haiyan.config.castorgen.QueryFilter vQueryFilter)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._queryFilterList.size()) {
            throw new IndexOutOfBoundsException("setQueryFilter: Index value '" + index + "' not in range [0.." + (this._queryFilterList.size() - 1) + "]");
        }
        
        this._queryFilterList.set(index, vQueryFilter);
    }

    /**
     * 
     * 
     * @param vQueryFilterArray
     */
    public void setQueryFilter(
            final haiyan.config.castorgen.QueryFilter[] vQueryFilterArray) {
        //-- copy array
        _queryFilterList.clear();
        
        for (int i = 0; i < vQueryFilterArray.length; i++) {
                this._queryFilterList.add(vQueryFilterArray[i]);
        }
    }

    /**
     * Sets the value of field 'querySQL'. The field 'querySQL' has
     * the following description: select sql
     * 
     * @param querySQL the value of field 'querySQL'.
     */
    public void setQuerySQL(
            final haiyan.config.castorgen.QuerySQL querySQL) {
        this._querySQL = querySQL;
    }

    /**
     * Sets the value of field 'realName'. The field 'realName' has
     * the following description: table db realname
     * 
     * @param realName the value of field 'realName'.
     */
    public void setRealName(
            final java.lang.String realName) {
        this._realName = realName;
    }

    /**
     * 
     * 
     * @param index
     * @param vRole
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRole(
            final int index,
            final haiyan.config.castorgen.Role vRole)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._roleList.size()) {
            throw new IndexOutOfBoundsException("setRole: Index value '" + index + "' not in range [0.." + (this._roleList.size() - 1) + "]");
        }
        
        this._roleList.set(index, vRole);
    }

    /**
     * 
     * 
     * @param vRoleArray
     */
    public void setRole(
            final haiyan.config.castorgen.Role[] vRoleArray) {
        //-- copy array
        _roleList.clear();
        
        for (int i = 0; i < vRoleArray.length; i++) {
                this._roleList.add(vRoleArray[i]);
        }
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
     * @return the unmarshaled haiyan.config.castorgen.Table
     */
    public static haiyan.config.castorgen.Table unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Table) Unmarshaller.unmarshal(haiyan.config.castorgen.Table.class, reader);
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
