package haiyan.orm.intf.database;

import haiyan.common.intf.database.IDBTemplate;
import haiyan.config.castorgen.Field;
import haiyan.config.castorgen.Table;

public interface ITableDBTemplate extends IDBTemplate { 

	void dealWithMappingField(Table table, Field field, Object[] globalVars) throws Throwable;
	void dealWithLazyLoadField(Table table, Field field, Object[] globalVars) throws Throwable;
	void dealWithIdField(Table table, int index, Object[] globalVars) throws Throwable;
	void dealWithAssociatedField(Table table, int tableIndex, int index, 
			Field mainField, Field associatedField, Object[] globalVars) throws Throwable;
	void dealWithGeneralField(Table table, int tableIndex, Field field, Object[] globalVars)
			throws Throwable;
	void dealWithComputeField(Table table, int tableIndex, Field field, Object[] globalVars) 
			throws Throwable;
	void dealWithReferenceTable(Table table, int tableIndex, Field field, Object[] globalVars) 
			throws Throwable;
	int dealWithDisplayField(Table table, int tableIndex, int index, Field field, Object[] globalVars) 
			throws Throwable;
}
