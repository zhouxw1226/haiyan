package haiyan.common.intf.database;

import haiyan.common.intf.database.orm.IDBRecord;

public interface IPredicate {

    public boolean evaluate(IDBRecord record);
}
