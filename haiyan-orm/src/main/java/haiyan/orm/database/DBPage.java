package haiyan.orm.database;

import haiyan.common.config.DataConstant;
import haiyan.common.exception.Warning;
import haiyan.common.intf.database.IPredicate;
import haiyan.common.intf.database.orm.IDBRecord;
import haiyan.common.intf.database.orm.IDBResultSet;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * DBPage Bean
 * 
 * @author zhouxw
 */
public class DBPage implements Serializable, IDBResultSet {

	private static final long serialVersionUID = 1L;
	//private static transient final String BLANK = "&nbsp;&nbsp;";
	public static transient final String PAGE_NO_PARAM_NAME = DataConstant.PAGE_NO_PARAM_NAME;
	public static transient final String MAX_COUNT_PER_PAGE_NAME = DataConstant.MAX_COUNT_PER_PAGE_NAME;
	public static transient final int[] opts = { 5, 10, 50, 100, 500, 1000 }; // 几种默认选择项
	public static transient int MAXCOUNT_PERQUERY = 500; // 每次查询最大行数
	public static transient int MAXNUM_PERPAGE = 100; // 默认每页行数
	private int pageRowCount = MAXNUM_PERPAGE;
	private int pageIndex = 1;
	private long totalRecordCount = 0;
	private String tableName;
	private List<IDBRecord> records = null; // new PageList<IRecord>();
	public DBPage() {
	}
	public DBPage(List<IDBRecord> arrayList) {
		this.records = arrayList;
	}
	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String getTableName() {
		return tableName;
	}
	/**
	 * 设置页码
	 * 
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) throws DBPageException {
		this.pageIndex = pageIndex;
	}
	/**
	 * 当前页码
	 * 
	 * @return int
	 */
	@Override
	public int getPageIndex() {
		return this.pageIndex;
	}
	/**
	 * 设置最大每页行数
	 * 
	 * @param count
	 */
	//@Override
	public void setPageRowCount(int count) {
		this.pageRowCount = count;
	}
	/**
	 * 最大每页行数
	 * 
	 * @return int
	 */
	@Override
	public int getPageRowCount() {
		return this.pageRowCount;
	}
	/**
	 * 总记录行数
	 * 
	 * @param i
	 */
	@Override
	public void setTotalRecordCount(long i) {
		this.totalRecordCount = i;
	}
	/**
	 * 查询出来的总记录行数
	 * 
	 * @return int
	 */
	@Override
	public long getTotalRecordCount() {
		return this.totalRecordCount; // db设置的值
	}
	/**
	 * 获取计算后的最大页数
	 * 
	 * @see haiyan.common.intf.database.orm.IDBResultSet#getTotalPageCount()
	 */
	@Override
	public int getTotalPageCount() {
		long total = this.getTotalRecordCount();
		long row = this.getPageRowCount();
		int pageCount = Math.round(total/row)+(total%row>0?1:0);
		return pageCount;
	}
	/**
	 * 当前可访问有效数据的行数
	 * 
	 * @return int
	 */
	public int size() {
		if (this.records != null)
			return this.records.size();
		return 0;
	}
	/**
	 * @param index
	 * @return IRecord
	 */
	@Override
	public IDBRecord getRecord(int index) {
		if (this.records.size() <= index)
			return null;
		return this.records.get(index);
	}
	/**
	 * @return ArrayList
	 */
	@Override
	public List<IDBRecord> getRecords() {
		return this.records;
	}
	/**
	 * @return int
	 */
	@Override
	public int getRecordCount() {
		return this.records!=null?this.records.size():0;
	}
	/**
	 * @param data
	 */
	public void setRecords(List<IDBRecord> data) {
		this.records = data;
	}
	/**
	 * @param index
	 * @param record
	 */
	public void addRecord(int index, IDBRecord record) {
		this.records.add(index, record);
	}
	/**
	 * @param record
	 */
	public void addRecord(IDBRecord record) {
		this.records.add(record);
	}
	/**
	 * @return String
	 */
	@Override
	public String toString() {
		return this.toJSon().toString();
	}
	@Override
	public JSONArray toJSon() {
		JSONArray list = new JSONArray();
		Iterator<?> iter = this.getRecords().iterator();
		while (iter.hasNext()) {
			IDBRecord form = (IDBRecord) iter.next();
			list.add(form.toJSon());
		}
		return list;
	}
	private int activeIndex = 0;
	@Override
	public void setActiveRecord(int index) {
		if (index>=this.size())
			throw new Warning("Out of Data's Bounds");
		this.activeIndex = index;
	}
	@Override
	public IDBRecord getActiveRecord() {
		if (activeIndex>=this.size())
			return null;
		return this.getRecord(activeIndex);
	}
	@Override
	public IDBRecord find(IPredicate predicate) {
		if ((this.records != null) && (predicate != null)) {
			for (IDBRecord item:this.records) {
				if (predicate.evaluate(item)) {
					return item;
				}
			}
		}
		return null;
	}
	@Override
	public void filter(IPredicate predicate) {
		if ((this.records != null) && (predicate != null)) {
			//List<IDBRecord> answer = new ArrayList<IDBRecord>(this.records.size());
			for (IDBRecord item:this.records) {
				if (predicate.evaluate(item)) {
					//answer.add(item);
				}
			}
			//return answer;
		}
		//return null;
	}
	@Override
	public IDBRecord insertRowAfter(int rowIndex) {
		IDBRecord record = new DBRecord();
		record.setStatus(IDBRecord.INSERT);
		int position = 0;
		if (rowIndex<this.records.size() && rowIndex>=0)
			position=rowIndex+1;
		else if (rowIndex<0)
			position=0;
		else
			position=this.records.size();
		this.records.add(position, record);
		return record;
	}
	@Override
	public IDBRecord insertRowBefore(int rowIndex) {
		IDBRecord record = new DBRecord();
		record.setStatus(IDBRecord.INSERT);
		int position = this.records.size();
		if (rowIndex<this.records.size() && rowIndex>0)
			position=rowIndex;
		else if (rowIndex>=this.records.size())
			position=this.records.size()-1;
		else 
			position=0;
		this.records.add(position, record);
		return record;
	}
	@Override
	public IDBRecord appendRow() {
		return this.insertRowAfter(this.records.size());
	}
	@Override
	public IDBRecord deleteRow(int rowIndex) {
		if (rowIndex>=this.records.size())
			return null;
		IDBRecord record = this.records.get(rowIndex);
		if (record.getStatus()==IDBRecord.INSERT) {
			record.setStatus(IDBRecord.INSERT_DELETE);
		} else {
			record.setStatus(IDBRecord.DELETE);
		}
		return record;
	}
	@Override
	public void commit() {
		int size = this.records.size();
		for (int row=size-1;row>=0;row--) {
			IDBRecord record=this.records.get(row);
			if (record.getStatus()==IDBRecord.DELETE || record.getStatus()==IDBRecord.INSERT_DELETE) {
				this.records.remove(row);
			} else if (record.getStatus()==IDBRecord.INSERT) {
				record.setStatus(IDBRecord.DEFAULT);
			} else {
				//commit.rollback(); // context->dbmanager->cachemanager中已经处理了
			}
		}
	}
	@Override
	public void rollback() {
		int size = this.records.size();
		for (int row=size-1;row>0;row--) {
			IDBRecord record=this.records.get(row);
			if (record.getStatus()==IDBRecord.INSERT || record.getStatus()==IDBRecord.INSERT_DELETE) {
				this.records.remove(row);
			} else if (record.getStatus()==IDBRecord.UPDATE) {
				record.setStatus(IDBRecord.DEFAULT);
			} else {
				//record.rollback(); // context->dbmanager->cachemanager中已经处理了
			}
		}
	}
}