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

import haiyan.common.intf.config.IBillConfig;

import haiyan.common.intf.config.IBillConfig;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * bill config
 * 
 * @version $Revision$ $Date$
 */
public class Bill implements java.io.Serializable, IBillConfig {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * bill name
     */
    private java.lang.String _name;

    /**
     * bill realName
     */
    private java.lang.String _realName;

    /**
     * bill description
     */
    private java.lang.String _description;

    /**
     * database source
     */
    private java.lang.String _dbSource;

    /**
     * bill module ID
     */
    private java.lang.String _modelid;

    /**
     * bill module CODE
     */
    private java.lang.String _modelcode;

    /**
     * bill's table
     */
    private java.util.Vector _billTableList;

    /**
     * bill's pk
     */
    private java.util.Vector _billIDList;

    /**
     * bill's field
     */
    private java.util.Vector _billFieldList;

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

    public Bill() {
        super();
        this._billTableList = new java.util.Vector();
        this._billIDList = new java.util.Vector();
        this._billFieldList = new java.util.Vector();
        this._queryFilterList = new java.util.Vector();
        this._roleList = new java.util.Vector();
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
     * @param vBillField
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBillField(
            final haiyan.config.castorgen.BillField vBillField)
    throws java.lang.IndexOutOfBoundsException {
        this._billFieldList.addElement(vBillField);
    }

    /**
     * 
     * 
     * @param index
     * @param vBillField
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBillField(
            final int index,
            final haiyan.config.castorgen.BillField vBillField)
    throws java.lang.IndexOutOfBoundsException {
        this._billFieldList.add(index, vBillField);
    }

    /**
     * 
     * 
     * @param vBillID
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBillID(
            final haiyan.config.castorgen.BillID vBillID)
    throws java.lang.IndexOutOfBoundsException {
        this._billIDList.addElement(vBillID);
    }

    /**
     * 
     * 
     * @param index
     * @param vBillID
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBillID(
            final int index,
            final haiyan.config.castorgen.BillID vBillID)
    throws java.lang.IndexOutOfBoundsException {
        this._billIDList.add(index, vBillID);
    }

    /**
     * 
     * 
     * @param vBillTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBillTable(
            final haiyan.config.castorgen.BillTable vBillTable)
    throws java.lang.IndexOutOfBoundsException {
        this._billTableList.addElement(vBillTable);
    }

    /**
     * 
     * 
     * @param index
     * @param vBillTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBillTable(
            final int index,
            final haiyan.config.castorgen.BillTable vBillTable)
    throws java.lang.IndexOutOfBoundsException {
        this._billTableList.add(index, vBillTable);
    }

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
     * Method enumerateBillField.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.BillField elements
     */
    public java.util.Enumeration enumerateBillField(
    ) {
        return this._billFieldList.elements();
    }

    /**
     * Method enumerateBillID.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.BillID elements
     */
    public java.util.Enumeration enumerateBillID(
    ) {
        return this._billIDList.elements();
    }

    /**
     * Method enumerateBillTable.
     * 
     * @return an Enumeration over all
     * haiyan.config.castorgen.BillTable elements
     */
    public java.util.Enumeration enumerateBillTable(
    ) {
        return this._billTableList.elements();
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
     * Method getBillField.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.BillField
     * at the given index
     */
    public haiyan.config.castorgen.BillField getBillField(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billFieldList.size()) {
            throw new IndexOutOfBoundsException("getBillField: Index value '" + index + "' not in range [0.." + (this._billFieldList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.BillField) _billFieldList.get(index);
    }

    /**
     * Method getBillField.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.BillField[] getBillField(
    ) {
        haiyan.config.castorgen.BillField[] array = new haiyan.config.castorgen.BillField[0];
        return (haiyan.config.castorgen.BillField[]) this._billFieldList.toArray(array);
    }

    /**
     * Method getBillFieldCount.
     * 
     * @return the size of this collection
     */
    public int getBillFieldCount(
    ) {
        return this._billFieldList.size();
    }

    /**
     * Method getBillID.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.BillID at
     * the given index
     */
    public haiyan.config.castorgen.BillID getBillID(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billIDList.size()) {
            throw new IndexOutOfBoundsException("getBillID: Index value '" + index + "' not in range [0.." + (this._billIDList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.BillID) _billIDList.get(index);
    }

    /**
     * Method getBillID.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.BillID[] getBillID(
    ) {
        haiyan.config.castorgen.BillID[] array = new haiyan.config.castorgen.BillID[0];
        return (haiyan.config.castorgen.BillID[]) this._billIDList.toArray(array);
    }

    /**
     * Method getBillIDCount.
     * 
     * @return the size of this collection
     */
    public int getBillIDCount(
    ) {
        return this._billIDList.size();
    }

    /**
     * Method getBillTable.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the haiyan.config.castorgen.BillTable
     * at the given index
     */
    public haiyan.config.castorgen.BillTable getBillTable(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billTableList.size()) {
            throw new IndexOutOfBoundsException("getBillTable: Index value '" + index + "' not in range [0.." + (this._billTableList.size() - 1) + "]");
        }
        
        return (haiyan.config.castorgen.BillTable) _billTableList.get(index);
    }

    /**
     * Method getBillTable.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public haiyan.config.castorgen.BillTable[] getBillTable(
    ) {
        haiyan.config.castorgen.BillTable[] array = new haiyan.config.castorgen.BillTable[0];
        return (haiyan.config.castorgen.BillTable[]) this._billTableList.toArray(array);
    }

    /**
     * Method getBillTableCount.
     * 
     * @return the size of this collection
     */
    public int getBillTableCount(
    ) {
        return this._billTableList.size();
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
     * 'description' has the following description: bill
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
     * following description: bill name
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
     * has the following description: bill realName
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
    public void removeAllBillField(
    ) {
        this._billFieldList.clear();
    }

    /**
     */
    public void removeAllBillID(
    ) {
        this._billIDList.clear();
    }

    /**
     */
    public void removeAllBillTable(
    ) {
        this._billTableList.clear();
    }

    /**
     */
    public void removeAllDataRules(
    ) {
        this._dataRulesList.clear();
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
     * Method removeBillField.
     * 
     * @param vBillField
     * @return true if the object was removed from the collection.
     */
    public boolean removeBillField(
            final haiyan.config.castorgen.BillField vBillField) {
        boolean removed = _billFieldList.remove(vBillField);
        return removed;
    }

    /**
     * Method removeBillFieldAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.BillField removeBillFieldAt(
            final int index) {
        java.lang.Object obj = this._billFieldList.remove(index);
        return (haiyan.config.castorgen.BillField) obj;
    }

    /**
     * Method removeBillID.
     * 
     * @param vBillID
     * @return true if the object was removed from the collection.
     */
    public boolean removeBillID(
            final haiyan.config.castorgen.BillID vBillID) {
        boolean removed = _billIDList.remove(vBillID);
        return removed;
    }

    /**
     * Method removeBillIDAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.BillID removeBillIDAt(
            final int index) {
        java.lang.Object obj = this._billIDList.remove(index);
        return (haiyan.config.castorgen.BillID) obj;
    }

    /**
     * Method removeBillTable.
     * 
     * @param vBillTable
     * @return true if the object was removed from the collection.
     */
    public boolean removeBillTable(
            final haiyan.config.castorgen.BillTable vBillTable) {
        boolean removed = _billTableList.remove(vBillTable);
        return removed;
    }

    /**
     * Method removeBillTableAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public haiyan.config.castorgen.BillTable removeBillTableAt(
            final int index) {
        java.lang.Object obj = this._billTableList.remove(index);
        return (haiyan.config.castorgen.BillTable) obj;
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
     * 
     * 
     * @param index
     * @param vBillField
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setBillField(
            final int index,
            final haiyan.config.castorgen.BillField vBillField)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billFieldList.size()) {
            throw new IndexOutOfBoundsException("setBillField: Index value '" + index + "' not in range [0.." + (this._billFieldList.size() - 1) + "]");
        }
        
        this._billFieldList.set(index, vBillField);
    }

    /**
     * 
     * 
     * @param vBillFieldArray
     */
    public void setBillField(
            final haiyan.config.castorgen.BillField[] vBillFieldArray) {
        //-- copy array
        _billFieldList.clear();
        
        for (int i = 0; i < vBillFieldArray.length; i++) {
                this._billFieldList.add(vBillFieldArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vBillID
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setBillID(
            final int index,
            final haiyan.config.castorgen.BillID vBillID)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billIDList.size()) {
            throw new IndexOutOfBoundsException("setBillID: Index value '" + index + "' not in range [0.." + (this._billIDList.size() - 1) + "]");
        }
        
        this._billIDList.set(index, vBillID);
    }

    /**
     * 
     * 
     * @param vBillIDArray
     */
    public void setBillID(
            final haiyan.config.castorgen.BillID[] vBillIDArray) {
        //-- copy array
        _billIDList.clear();
        
        for (int i = 0; i < vBillIDArray.length; i++) {
                this._billIDList.add(vBillIDArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vBillTable
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setBillTable(
            final int index,
            final haiyan.config.castorgen.BillTable vBillTable)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._billTableList.size()) {
            throw new IndexOutOfBoundsException("setBillTable: Index value '" + index + "' not in range [0.." + (this._billTableList.size() - 1) + "]");
        }
        
        this._billTableList.set(index, vBillTable);
    }

    /**
     * 
     * 
     * @param vBillTableArray
     */
    public void setBillTable(
            final haiyan.config.castorgen.BillTable[] vBillTableArray) {
        //-- copy array
        _billTableList.clear();
        
        for (int i = 0; i < vBillTableArray.length; i++) {
                this._billTableList.add(vBillTableArray[i]);
        }
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
     * 'description' has the following description: bill
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
     * following description: bill name
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
     * the following description: bill realName
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
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.Bill
     */
    public static haiyan.config.castorgen.Bill unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Bill) Unmarshaller.unmarshal(haiyan.config.castorgen.Bill.class, reader);
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
