/*
 * Created on 2004-10-13
 */
package haiyan.orm.database.sql.page;

import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.orm.database.DBPage;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * ArrayList
 * 
 * @author zhouxw
 */
public class ArrayListPageFactory extends SQLDBPageFactory {

    private static final long serialVersionUID = 1L;

    private ArrayList<IDBRecord> data;

    /**
     * @param data
     */
    public ArrayListPageFactory(ArrayList<IDBRecord> data) {
        this.data = data;
    }

    // /**
    // * @param data
    // */
    // public ArrayListPageFactory(ArrayList<IRecord> data,
    // BusinessObjFactory factory) {
    // this.data = data;
    // this.factory = factory;
    // }

    @Override
    protected final ArrayList<IDBRecord> createList(int maxPageRecordCount,
            int currPageNO) throws Throwable {
        return new ArrayList<IDBRecord>();
        // return data; // 要重新放置数据 不能使用data
    }

    @Override
    public void dealPage(int maxPageRecordCount, int currPageNO,
            IDBRecordCallBack callback) throws Throwable {
        Iterator<IDBRecord> itr = data.iterator();
        while (itr.hasNext()) {
            callback.call(itr.next());
        }
    }

    @Override
    public DBPage getPage(int maxPageRecordCount, int currPageNO)
            throws Throwable {
        ArrayList<IDBRecord> pageData = createList(maxPageRecordCount, currPageNO);
        int count = 0;
        Iterator<IDBRecord> itr = data.iterator();
        while (itr.hasNext() && count < (maxPageRecordCount * (currPageNO))) {
            // NOTICE 对传入的data分页并构造pageData
            if (count >= (maxPageRecordCount * (currPageNO - 1))
                    && count <= (maxPageRecordCount * (currPageNO))) {
                IDBRecord form = itr.next();
                pageData.add(form);
            }
            count++;
        }
        if (data.size() == 0) {
            currPageNO = 0;
            maxPageRecordCount = 0;
        }
        DBPage page = new DBPage();
        page.setTotalRecordCount(data.size());
        page.setRecords(pageData);
        page.setCurrPageNO(currPageNO);
        page.setMaxPageRecordCount(maxPageRecordCount);
        return page;
    }

}