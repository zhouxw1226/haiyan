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
 * operation setting
 * 
 * @version $Revision$ $Date$
 */
public class Operation implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * can save
     */
    private boolean _save = true;

    /**
     * keeps track of state for field: _save
     */
    private boolean _has_save;

    /**
     * can save and add new
     */
    private boolean _saveAndAdd = true;

    /**
     * keeps track of state for field: _saveAndAdd
     */
    private boolean _has_saveAndAdd;

    /**
     * can save and copy new
     */
    private boolean _saveAndAddCopy = true;

    /**
     * keeps track of state for field: _saveAndAddCopy
     */
    private boolean _has_saveAndAddCopy;

    /**
     * can editOne
     */
    private boolean _editOne = true;

    /**
     * keeps track of state for field: _editOne
     */
    private boolean _has_editOne;

    /**
     * can queryOne
     */
    private boolean _queryOne = true;

    /**
     * keeps track of state for field: _queryOne
     */
    private boolean _has_queryOne;

    /**
     * can add
     */
    private boolean _queryPageAdd = true;

    /**
     * keeps track of state for field: _queryPageAdd
     */
    private boolean _has_queryPageAdd;

    /**
     * can delete
     */
    private boolean _queryPageDelete = true;

    /**
     * keeps track of state for field: _queryPageDelete
     */
    private boolean _has_queryPageDelete;

    /**
     * show print view
     */
    private boolean _showPrintPage = true;

    /**
     * keeps track of state for field: _showPrintPage
     */
    private boolean _has_showPrintPage;

    /**
     * filterDesign
     */
    private boolean _filterDesign = true;

    /**
     * keeps track of state for field: _filterDesign
     */
    private boolean _has_filterDesign;

    /**
     * filterby
     */
    private boolean _filterby = true;

    /**
     * keeps track of state for field: _filterby
     */
    private boolean _has_filterby;

    /**
     * quickQuery
     */
    private boolean _quickQuery = true;

    /**
     * keeps track of state for field: _quickQuery
     */
    private boolean _has_quickQuery;

    /**
     * use workflow
     */
    private boolean _inWorkFlow = true;

    /**
     * keeps track of state for field: _inWorkFlow
     */
    private boolean _has_inWorkFlow;

    /**
     * use security parameter (__procID)
     *  
     */
    private boolean _security = false;

    /**
     * keeps track of state for field: _security
     */
    private boolean _has_security;

    /**
     * user define display field (@deprecated)
     *  
     */
    private boolean _customizeColumns = true;

    /**
     * keeps track of state for field: _customizeColumns
     */
    private boolean _has_customizeColumns;

    /**
     * hidden parameter (__selectedID)
     *  
     */
    private boolean _selectedHidden = false;

    /**
     * keeps track of state for field: _selectedHidden
     */
    private boolean _has_selectedHidden;


      //----------------/
     //- Constructors -/
    //----------------/

    public Operation() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteCustomizeColumns(
    ) {
        this._has_customizeColumns= false;
    }

    /**
     */
    public void deleteEditOne(
    ) {
        this._has_editOne= false;
    }

    /**
     */
    public void deleteFilterDesign(
    ) {
        this._has_filterDesign= false;
    }

    /**
     */
    public void deleteFilterby(
    ) {
        this._has_filterby= false;
    }

    /**
     */
    public void deleteInWorkFlow(
    ) {
        this._has_inWorkFlow= false;
    }

    /**
     */
    public void deleteQueryOne(
    ) {
        this._has_queryOne= false;
    }

    /**
     */
    public void deleteQueryPageAdd(
    ) {
        this._has_queryPageAdd= false;
    }

    /**
     */
    public void deleteQueryPageDelete(
    ) {
        this._has_queryPageDelete= false;
    }

    /**
     */
    public void deleteQuickQuery(
    ) {
        this._has_quickQuery= false;
    }

    /**
     */
    public void deleteSave(
    ) {
        this._has_save= false;
    }

    /**
     */
    public void deleteSaveAndAdd(
    ) {
        this._has_saveAndAdd= false;
    }

    /**
     */
    public void deleteSaveAndAddCopy(
    ) {
        this._has_saveAndAddCopy= false;
    }

    /**
     */
    public void deleteSecurity(
    ) {
        this._has_security= false;
    }

    /**
     */
    public void deleteSelectedHidden(
    ) {
        this._has_selectedHidden= false;
    }

    /**
     */
    public void deleteShowPrintPage(
    ) {
        this._has_showPrintPage= false;
    }

    /**
     * Returns the value of field 'customizeColumns'. The field
     * 'customizeColumns' has the following description: user
     * define display field (@deprecated)
     *  
     * 
     * @return the value of field 'CustomizeColumns'.
     */
    public boolean getCustomizeColumns(
    ) {
        return this._customizeColumns;
    }

    /**
     * Returns the value of field 'editOne'. The field 'editOne'
     * has the following description: can editOne
     * 
     * @return the value of field 'EditOne'.
     */
    public boolean getEditOne(
    ) {
        return this._editOne;
    }

    /**
     * Returns the value of field 'filterDesign'. The field
     * 'filterDesign' has the following description: filterDesign
     * 
     * @return the value of field 'FilterDesign'.
     */
    public boolean getFilterDesign(
    ) {
        return this._filterDesign;
    }

    /**
     * Returns the value of field 'filterby'. The field 'filterby'
     * has the following description: filterby
     * 
     * @return the value of field 'Filterby'.
     */
    public boolean getFilterby(
    ) {
        return this._filterby;
    }

    /**
     * Returns the value of field 'inWorkFlow'. The field
     * 'inWorkFlow' has the following description: use workflow
     * 
     * @return the value of field 'InWorkFlow'.
     */
    public boolean getInWorkFlow(
    ) {
        return this._inWorkFlow;
    }

    /**
     * Returns the value of field 'queryOne'. The field 'queryOne'
     * has the following description: can queryOne
     * 
     * @return the value of field 'QueryOne'.
     */
    public boolean getQueryOne(
    ) {
        return this._queryOne;
    }

    /**
     * Returns the value of field 'queryPageAdd'. The field
     * 'queryPageAdd' has the following description: can add
     * 
     * @return the value of field 'QueryPageAdd'.
     */
    public boolean getQueryPageAdd(
    ) {
        return this._queryPageAdd;
    }

    /**
     * Returns the value of field 'queryPageDelete'. The field
     * 'queryPageDelete' has the following description: can delete
     * 
     * @return the value of field 'QueryPageDelete'.
     */
    public boolean getQueryPageDelete(
    ) {
        return this._queryPageDelete;
    }

    /**
     * Returns the value of field 'quickQuery'. The field
     * 'quickQuery' has the following description: quickQuery
     * 
     * @return the value of field 'QuickQuery'.
     */
    public boolean getQuickQuery(
    ) {
        return this._quickQuery;
    }

    /**
     * Returns the value of field 'save'. The field 'save' has the
     * following description: can save
     * 
     * @return the value of field 'Save'.
     */
    public boolean getSave(
    ) {
        return this._save;
    }

    /**
     * Returns the value of field 'saveAndAdd'. The field
     * 'saveAndAdd' has the following description: can save and add
     * new
     * 
     * @return the value of field 'SaveAndAdd'.
     */
    public boolean getSaveAndAdd(
    ) {
        return this._saveAndAdd;
    }

    /**
     * Returns the value of field 'saveAndAddCopy'. The field
     * 'saveAndAddCopy' has the following description: can save and
     * copy new
     * 
     * @return the value of field 'SaveAndAddCopy'.
     */
    public boolean getSaveAndAddCopy(
    ) {
        return this._saveAndAddCopy;
    }

    /**
     * Returns the value of field 'security'. The field 'security'
     * has the following description: use security parameter
     * (__procID)
     *  
     * 
     * @return the value of field 'Security'.
     */
    public boolean getSecurity(
    ) {
        return this._security;
    }

    /**
     * Returns the value of field 'selectedHidden'. The field
     * 'selectedHidden' has the following description: hidden
     * parameter (__selectedID)
     *  
     * 
     * @return the value of field 'SelectedHidden'.
     */
    public boolean getSelectedHidden(
    ) {
        return this._selectedHidden;
    }

    /**
     * Returns the value of field 'showPrintPage'. The field
     * 'showPrintPage' has the following description: show print
     * view
     * 
     * @return the value of field 'ShowPrintPage'.
     */
    public boolean getShowPrintPage(
    ) {
        return this._showPrintPage;
    }

    /**
     * Method hasCustomizeColumns.
     * 
     * @return true if at least one CustomizeColumns has been added
     */
    public boolean hasCustomizeColumns(
    ) {
        return this._has_customizeColumns;
    }

    /**
     * Method hasEditOne.
     * 
     * @return true if at least one EditOne has been added
     */
    public boolean hasEditOne(
    ) {
        return this._has_editOne;
    }

    /**
     * Method hasFilterDesign.
     * 
     * @return true if at least one FilterDesign has been added
     */
    public boolean hasFilterDesign(
    ) {
        return this._has_filterDesign;
    }

    /**
     * Method hasFilterby.
     * 
     * @return true if at least one Filterby has been added
     */
    public boolean hasFilterby(
    ) {
        return this._has_filterby;
    }

    /**
     * Method hasInWorkFlow.
     * 
     * @return true if at least one InWorkFlow has been added
     */
    public boolean hasInWorkFlow(
    ) {
        return this._has_inWorkFlow;
    }

    /**
     * Method hasQueryOne.
     * 
     * @return true if at least one QueryOne has been added
     */
    public boolean hasQueryOne(
    ) {
        return this._has_queryOne;
    }

    /**
     * Method hasQueryPageAdd.
     * 
     * @return true if at least one QueryPageAdd has been added
     */
    public boolean hasQueryPageAdd(
    ) {
        return this._has_queryPageAdd;
    }

    /**
     * Method hasQueryPageDelete.
     * 
     * @return true if at least one QueryPageDelete has been added
     */
    public boolean hasQueryPageDelete(
    ) {
        return this._has_queryPageDelete;
    }

    /**
     * Method hasQuickQuery.
     * 
     * @return true if at least one QuickQuery has been added
     */
    public boolean hasQuickQuery(
    ) {
        return this._has_quickQuery;
    }

    /**
     * Method hasSave.
     * 
     * @return true if at least one Save has been added
     */
    public boolean hasSave(
    ) {
        return this._has_save;
    }

    /**
     * Method hasSaveAndAdd.
     * 
     * @return true if at least one SaveAndAdd has been added
     */
    public boolean hasSaveAndAdd(
    ) {
        return this._has_saveAndAdd;
    }

    /**
     * Method hasSaveAndAddCopy.
     * 
     * @return true if at least one SaveAndAddCopy has been added
     */
    public boolean hasSaveAndAddCopy(
    ) {
        return this._has_saveAndAddCopy;
    }

    /**
     * Method hasSecurity.
     * 
     * @return true if at least one Security has been added
     */
    public boolean hasSecurity(
    ) {
        return this._has_security;
    }

    /**
     * Method hasSelectedHidden.
     * 
     * @return true if at least one SelectedHidden has been added
     */
    public boolean hasSelectedHidden(
    ) {
        return this._has_selectedHidden;
    }

    /**
     * Method hasShowPrintPage.
     * 
     * @return true if at least one ShowPrintPage has been added
     */
    public boolean hasShowPrintPage(
    ) {
        return this._has_showPrintPage;
    }

    /**
     * Returns the value of field 'customizeColumns'. The field
     * 'customizeColumns' has the following description: user
     * define display field (@deprecated)
     *  
     * 
     * @return the value of field 'CustomizeColumns'.
     */
    public boolean isCustomizeColumns(
    ) {
        return this._customizeColumns;
    }

    /**
     * Returns the value of field 'editOne'. The field 'editOne'
     * has the following description: can editOne
     * 
     * @return the value of field 'EditOne'.
     */
    public boolean isEditOne(
    ) {
        return this._editOne;
    }

    /**
     * Returns the value of field 'filterDesign'. The field
     * 'filterDesign' has the following description: filterDesign
     * 
     * @return the value of field 'FilterDesign'.
     */
    public boolean isFilterDesign(
    ) {
        return this._filterDesign;
    }

    /**
     * Returns the value of field 'filterby'. The field 'filterby'
     * has the following description: filterby
     * 
     * @return the value of field 'Filterby'.
     */
    public boolean isFilterby(
    ) {
        return this._filterby;
    }

    /**
     * Returns the value of field 'inWorkFlow'. The field
     * 'inWorkFlow' has the following description: use workflow
     * 
     * @return the value of field 'InWorkFlow'.
     */
    public boolean isInWorkFlow(
    ) {
        return this._inWorkFlow;
    }

    /**
     * Returns the value of field 'queryOne'. The field 'queryOne'
     * has the following description: can queryOne
     * 
     * @return the value of field 'QueryOne'.
     */
    public boolean isQueryOne(
    ) {
        return this._queryOne;
    }

    /**
     * Returns the value of field 'queryPageAdd'. The field
     * 'queryPageAdd' has the following description: can add
     * 
     * @return the value of field 'QueryPageAdd'.
     */
    public boolean isQueryPageAdd(
    ) {
        return this._queryPageAdd;
    }

    /**
     * Returns the value of field 'queryPageDelete'. The field
     * 'queryPageDelete' has the following description: can delete
     * 
     * @return the value of field 'QueryPageDelete'.
     */
    public boolean isQueryPageDelete(
    ) {
        return this._queryPageDelete;
    }

    /**
     * Returns the value of field 'quickQuery'. The field
     * 'quickQuery' has the following description: quickQuery
     * 
     * @return the value of field 'QuickQuery'.
     */
    public boolean isQuickQuery(
    ) {
        return this._quickQuery;
    }

    /**
     * Returns the value of field 'save'. The field 'save' has the
     * following description: can save
     * 
     * @return the value of field 'Save'.
     */
    public boolean isSave(
    ) {
        return this._save;
    }

    /**
     * Returns the value of field 'saveAndAdd'. The field
     * 'saveAndAdd' has the following description: can save and add
     * new
     * 
     * @return the value of field 'SaveAndAdd'.
     */
    public boolean isSaveAndAdd(
    ) {
        return this._saveAndAdd;
    }

    /**
     * Returns the value of field 'saveAndAddCopy'. The field
     * 'saveAndAddCopy' has the following description: can save and
     * copy new
     * 
     * @return the value of field 'SaveAndAddCopy'.
     */
    public boolean isSaveAndAddCopy(
    ) {
        return this._saveAndAddCopy;
    }

    /**
     * Returns the value of field 'security'. The field 'security'
     * has the following description: use security parameter
     * (__procID)
     *  
     * 
     * @return the value of field 'Security'.
     */
    public boolean isSecurity(
    ) {
        return this._security;
    }

    /**
     * Returns the value of field 'selectedHidden'. The field
     * 'selectedHidden' has the following description: hidden
     * parameter (__selectedID)
     *  
     * 
     * @return the value of field 'SelectedHidden'.
     */
    public boolean isSelectedHidden(
    ) {
        return this._selectedHidden;
    }

    /**
     * Returns the value of field 'showPrintPage'. The field
     * 'showPrintPage' has the following description: show print
     * view
     * 
     * @return the value of field 'ShowPrintPage'.
     */
    public boolean isShowPrintPage(
    ) {
        return this._showPrintPage;
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
     * Sets the value of field 'customizeColumns'. The field
     * 'customizeColumns' has the following description: user
     * define display field (@deprecated)
     *  
     * 
     * @param customizeColumns the value of field 'customizeColumns'
     */
    public void setCustomizeColumns(
            final boolean customizeColumns) {
        this._customizeColumns = customizeColumns;
        this._has_customizeColumns = true;
    }

    /**
     * Sets the value of field 'editOne'. The field 'editOne' has
     * the following description: can editOne
     * 
     * @param editOne the value of field 'editOne'.
     */
    public void setEditOne(
            final boolean editOne) {
        this._editOne = editOne;
        this._has_editOne = true;
    }

    /**
     * Sets the value of field 'filterDesign'. The field
     * 'filterDesign' has the following description: filterDesign
     * 
     * @param filterDesign the value of field 'filterDesign'.
     */
    public void setFilterDesign(
            final boolean filterDesign) {
        this._filterDesign = filterDesign;
        this._has_filterDesign = true;
    }

    /**
     * Sets the value of field 'filterby'. The field 'filterby' has
     * the following description: filterby
     * 
     * @param filterby the value of field 'filterby'.
     */
    public void setFilterby(
            final boolean filterby) {
        this._filterby = filterby;
        this._has_filterby = true;
    }

    /**
     * Sets the value of field 'inWorkFlow'. The field 'inWorkFlow'
     * has the following description: use workflow
     * 
     * @param inWorkFlow the value of field 'inWorkFlow'.
     */
    public void setInWorkFlow(
            final boolean inWorkFlow) {
        this._inWorkFlow = inWorkFlow;
        this._has_inWorkFlow = true;
    }

    /**
     * Sets the value of field 'queryOne'. The field 'queryOne' has
     * the following description: can queryOne
     * 
     * @param queryOne the value of field 'queryOne'.
     */
    public void setQueryOne(
            final boolean queryOne) {
        this._queryOne = queryOne;
        this._has_queryOne = true;
    }

    /**
     * Sets the value of field 'queryPageAdd'. The field
     * 'queryPageAdd' has the following description: can add
     * 
     * @param queryPageAdd the value of field 'queryPageAdd'.
     */
    public void setQueryPageAdd(
            final boolean queryPageAdd) {
        this._queryPageAdd = queryPageAdd;
        this._has_queryPageAdd = true;
    }

    /**
     * Sets the value of field 'queryPageDelete'. The field
     * 'queryPageDelete' has the following description: can delete
     * 
     * @param queryPageDelete the value of field 'queryPageDelete'.
     */
    public void setQueryPageDelete(
            final boolean queryPageDelete) {
        this._queryPageDelete = queryPageDelete;
        this._has_queryPageDelete = true;
    }

    /**
     * Sets the value of field 'quickQuery'. The field 'quickQuery'
     * has the following description: quickQuery
     * 
     * @param quickQuery the value of field 'quickQuery'.
     */
    public void setQuickQuery(
            final boolean quickQuery) {
        this._quickQuery = quickQuery;
        this._has_quickQuery = true;
    }

    /**
     * Sets the value of field 'save'. The field 'save' has the
     * following description: can save
     * 
     * @param save the value of field 'save'.
     */
    public void setSave(
            final boolean save) {
        this._save = save;
        this._has_save = true;
    }

    /**
     * Sets the value of field 'saveAndAdd'. The field 'saveAndAdd'
     * has the following description: can save and add new
     * 
     * @param saveAndAdd the value of field 'saveAndAdd'.
     */
    public void setSaveAndAdd(
            final boolean saveAndAdd) {
        this._saveAndAdd = saveAndAdd;
        this._has_saveAndAdd = true;
    }

    /**
     * Sets the value of field 'saveAndAddCopy'. The field
     * 'saveAndAddCopy' has the following description: can save and
     * copy new
     * 
     * @param saveAndAddCopy the value of field 'saveAndAddCopy'.
     */
    public void setSaveAndAddCopy(
            final boolean saveAndAddCopy) {
        this._saveAndAddCopy = saveAndAddCopy;
        this._has_saveAndAddCopy = true;
    }

    /**
     * Sets the value of field 'security'. The field 'security' has
     * the following description: use security parameter (__procID)
     *  
     * 
     * @param security the value of field 'security'.
     */
    public void setSecurity(
            final boolean security) {
        this._security = security;
        this._has_security = true;
    }

    /**
     * Sets the value of field 'selectedHidden'. The field
     * 'selectedHidden' has the following description: hidden
     * parameter (__selectedID)
     *  
     * 
     * @param selectedHidden the value of field 'selectedHidden'.
     */
    public void setSelectedHidden(
            final boolean selectedHidden) {
        this._selectedHidden = selectedHidden;
        this._has_selectedHidden = true;
    }

    /**
     * Sets the value of field 'showPrintPage'. The field
     * 'showPrintPage' has the following description: show print
     * view
     * 
     * @param showPrintPage the value of field 'showPrintPage'.
     */
    public void setShowPrintPage(
            final boolean showPrintPage) {
        this._showPrintPage = showPrintPage;
        this._has_showPrintPage = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled haiyan.config.castorgen.Operation
     */
    public static haiyan.config.castorgen.Operation unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (haiyan.config.castorgen.Operation) Unmarshaller.unmarshal(haiyan.config.castorgen.Operation.class, reader);
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
