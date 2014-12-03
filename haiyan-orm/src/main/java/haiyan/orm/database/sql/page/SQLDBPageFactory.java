/*
 * Created on 2004-10-13
 */
package haiyan.orm.database.sql.page;

import haiyan.common.intf.database.orm.IDBRecordCallBack;
import haiyan.common.intf.database.orm.IDBResultSetFactory;
import haiyan.common.intf.database.sql.ISQLRecordFactory;
import haiyan.orm.database.DBPage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * @author zhouxw
 */
public abstract class SQLDBPageFactory implements IDBResultSetFactory, java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// protected transient PrimaryTable pTable;

	protected transient PreparedStatement countPS;

	protected transient PreparedStatement selectPS;

	protected transient ISQLRecordFactory factory; // 可以拿到pTable
	/**
	 * @param maxPageRecordCount
	 * @param currPageNO
	 * @return Page
	 * @throws Throwable
	 */
	public abstract DBPage getPage(int maxPageRecordCount, int currPageNO) throws Throwable;
	/**
	 * @param currPageNO
	 * @param maxPageRecordCount
	 * @param callback
	 * @throws Throwable
	 */
	public abstract void dealPage(int maxPageRecordCount, int currPageNO, IDBRecordCallBack callback) throws Throwable;
	/**
	 * @param maxPageRecordCount
	 * @param currPageNO
	 * @return ArrayList
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	protected ArrayList createList(int maxPageRecordCount, int currPageNO) throws Throwable {
		ArrayList<Object> list = null;
		// if (maxPageRecordCount > Page.MAXCOUNTPERQUERY) {
		// if (DBManager.USECACHE == 0)
		// list = new PageList(); // list index list
		// else
		// list = new IndexPageList(factory); // id index list
		// // if (this.factory != null)
		// // this.factory.addCacheID(((PageList) list).getID());
		// }
//		if (DBManager.USECACHE != 0)
//			list = new IndexPageList<Object>(factory); // id index list
//		else
			list = new ArrayList<Object>();
		return list;
	}

//	/**
//	 * 存放data.ID索引 按form.id缓存数据
//	 * 
//	 * @author zhouxw
//	 * 
//	 * @param <E>
//	 */
//	@SuppressWarnings("unchecked")
//	static class IndexPageList<E> extends ArrayList<Object> {
//
//		private static final long serialVersionUID = 1L;
//
//		private transient String cacheID;
//
//		private transient String IDName;
//
//		IndexPageList(BusinessObjFactory factory) {
//			if (factory == null)
//				throw new Warning("BusinessObjFactory Lost!");
//			this.cacheID = factory.getTableName();
//			this.IDName = factory.getIDName();
//		}
//
//		String getCacheID() {
//			return this.cacheID;
//		}
//
//		String getIDName() {
//			return this.IDName;
//		}
//
//		/**
//		 * @deprecated
//		 */
//		@Override
//		public Object[] toArray() {
//			Object[] arr = new Object[this.size()];
//			for (int i = 0; i < arr.length; i++) {
//				// IRecord form = (IRecord) this.get(i);
//				// String IDVal = form.get(IDName);
//				// (IRecord) this.get(i);
//				// arr[i] = MyCache.getData(cacheID, IDVal);
//				arr[i] = this.get(i);
//			}
//			return arr;
//		}
//
//		/**
//		 * @deprecated
//		 */
//		@Override
//		public Object[] toArray(Object[] a) {
//			throw new Warning("toArray(Object[]) not support and not impl");
//		}
//
//		/**
//		 * @deprecated
//		 */
//		@Override
//		public Object clone() {
//			throw new Warning("clone() not impl");
//		}
//
//		@Override
//		public int indexOf(Object o) {
//			IRecord form = (IRecord) o;
//			String IDVal = form.get(getIDName());
//			return super.indexOf(IDVal);
//		}
//
//		@Override
//		public int lastIndexOf(Object o) {
//			IRecord form = (IRecord) o;
//			String IDVal = form.get(getIDName());
//			return super.lastIndexOf(IDVal);
//		}
//
//		// Positional Access Operations
//
//		private void debug(String IDVal) {
//			// DebugUtil.asert("7e6b103a-911d-40d4-8fa9-1bc87a7dd23f"
//			// .equals(IDVal));
//		}
//
//		@Override
//		public Object get(int index) {
//			String IDVal = (String) super.get(index);
//			debug(IDVal);
//			return HyCache.getListData(getCacheID(), IDVal);
//		}
//
//		@Override
//		public Object set(int index, Object element) {
//			IRecord form = (IRecord) element;
//			String IDVal = form.get(getIDName());
//			debug(IDVal);
//			HyCache.setListData(getCacheID(), IDVal, element);
//			super.set(index, IDVal);
//			return element;
//		}
//
//		@Override
//		public void add(int index, Object element) {
//			IRecord form = (IRecord) element;
//			String IDVal = form.get(getIDName());
//			debug(IDVal);
//			HyCache.setListData(getCacheID(), IDVal, element);
//			super.add(index, IDVal);
//		}
//
//		@Override
//		public boolean add(Object element) {
//			IRecord form = (IRecord) element;
//			String IDVal = form.get(getIDName());
//			debug(IDVal);
//			HyCache.setListData(getCacheID(), IDVal, element);
//			return super.add(IDVal);
//		}
//
//		@Override
//		public Object remove(int index) {
//			// 只删除索引
//			String IDVal = (String) super.remove(index);
//			if (IDVal == null)
//				return false;
//			debug(IDVal);
//			return HyCache.getListData(getCacheID(), IDVal);
//		}
//
//		@Override
//		public boolean remove(Object o) {
//			// 只删除索引
//			IRecord form = (IRecord) o;
//			String IDVal = form.get(getIDName());
//			debug(IDVal);
//			return super.remove(IDVal);
//		}
//
//		@Override
//		public boolean addAll(Collection<?> c) {
//			ArrayList<String> ids = new ArrayList<String>();
//			IRecord form;
//			String IDVal;
//			for (Object o : c) {
//				form = ((IRecord) o);
//				IDVal = form.get(getIDName());
//				ids.add(IDVal);
//				HyCache.setListData(getCacheID(), IDVal, form);
//			}
//			return super.addAll(ids);
//		}
//
//		@Override
//		public boolean addAll(int index, Collection<?> c) {
//			ArrayList<String> ids = new ArrayList<String>();
//			IRecord form;
//			String IDVal;
//			for (Object o : c) {
//				form = ((IRecord) o);
//				IDVal = form.get(getIDName());
//				ids.add(IDVal);
//				HyCache.setListData(getCacheID(), IDVal, form);
//			}
//			return super.addAll(index, ids);
//		}
//
//	}
//
//	/**
//	 * 存放磁盘form 按list.index缓存数据 没法管理
//	 * 
//	 * @deprecated 大数据量BSH编程有用
//	 * @author zhouxw
//	 * 
//	 * @param <E>
//	 */
//	static class PageList<E> extends ArrayList<Object> {
//
//		private static final long serialVersionUID = 1L;
//
//		private transient String cacheID;
//
//		PageList() {
//			this.cacheID = UUID.randomUUID().toString(); // 临时缓冲区，查询出来的所有VO不放在内存中
//		}
//
////		String getID() {
////			return this.cacheID;
////		}
//
//		public void trimToSize() {
//			throw new Warning("trimToSize() not impl");
//			// modCount++;
//			// int oldCapacity = elementData.length;
//			// if (size < oldCapacity) {
//			// elementData = Arrays.copyOf(elementData, size);
//			// }
//		}
//
//		public void ensureCapacity(int minCapacity) {
//			throw new Warning("ensureCapacity(minCapacity) not impl");
//			// modCount++;
//			// int oldCapacity = elementData.length;
//			// if (minCapacity > oldCapacity) {
//			// Object oldData[] = elementData;
//			// int newCapacity = (oldCapacity * 3)/2 + 1;
//			// if (newCapacity < minCapacity)
//			// newCapacity = minCapacity;
//			// // minCapacity is usually close to size, so this is a win:
//			// elementData = Arrays.copyOf(elementData, newCapacity);
//			// }
//		}
//
//		public int size() {
//			return HyCache.getDataSize(cacheID);
//			// return size;
//		}
//
//		public boolean isEmpty() {
//			return size() == 0;
//			// return size == 0;
//		}
//
//		public boolean contains(Object o) {
//			return indexOf(o) >= 0;
//		}
//
//		public int indexOf(Object o) {
//			return HyCache.getIndexOf(cacheID, o);
//			// if (o == null) {
//			// for (int i = 0; i < size; i++)
//			// if (elementData[i]==null)
//			// return i;
//			// } else {
//			// for (int i = 0; i < size; i++)
//			// if (o.equals(elementData[i]))
//			// return i;
//			// }
//			// return -1;
//		}
//
//		public int lastIndexOf(Object o) {
//			return HyCache.getLastIndexOf(cacheID, o);
//			// if (o == null) {
//			// for (int i = size-1; i >= 0; i--)
//			// if (elementData[i]==null)
//			// return i;
//			// } else {
//			// for (int i = size-1; i >= 0; i--)
//			// if (o.equals(elementData[i]))
//			// return i;
//			// }
//			// return -1;
//		}
//
//		public Object clone() {
//			throw new Warning("clone() not impl");
//			// try {
//			// ArrayList<E> v = (ArrayList<E>) super.clone();
//			// v.elementData = Arrays.copyOf(elementData, size);
//			// v.modCount = 0;
//			// return v;
//			// } catch (CloneNotSupportedException e) {
//			// // this shouldn't happen, since we are Cloneable
//			// throw new InternalError();
//			// }
//		}
//
//		/**
//		 * @deprecated
//		 */
//		public Object[] toArray() {
//			// throw new Warning("toArray() not support and not impl");
//			// return Arrays.copyOf(elementData, size);
//			Object[] arr = new Object[HyCache.getDataSize(cacheID)];
//			for (int i = 0; i < arr.length; i++) {
//				arr[i] = HyCache.getListData(cacheID, i);
//			}
//			return arr;
//		}
//
//		/**
//		 * @deprecated
//		 */
//		@SuppressWarnings("unchecked")
//		public Object[] toArray(Object[] a) {
//			throw new Warning("toArray(Object[]) not support and not impl");
//			// if (a.length < size)
//			// // Make a new array of a's runtime type, but my contents:
//			// return (T[]) Arrays.copyOf(elementData, size, a.getClass());
//			// System.arraycopy(elementData, 0, a, 0, size);
//			// if (a.length > size)
//			// a[size] = null;
//			// return a;
//		}
//
//		// Positional Access Operations
//
//		public Object get(int index) {
//			RangeCheck(index);
//			return HyCache.getListData(cacheID, index);
//			// RangeCheck(index);
//			// return (E) elementData[index];
//		}
//
//		public Object set(int index, Object element) {
//			RangeCheck(index);
//			return HyCache.setListData(cacheID, index, element);
//			// RangeCheck(index);
//			// E oldValue = (E) elementData[index];
//			// elementData[index] = element;
//			// return oldValue;
//		}
//
//		public boolean add(Object element) {
//			return HyCache.addListData(cacheID, element);
//			// ensureCapacity(size + 1); // Increments modCount!!
//			// elementData[size++] = e;
//			// return true;
//		}
//
//		public void add(int index, Object element) {
//			int size = size();
//			if (index > size || index < 0)
//				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
//			HyCache.setListData(cacheID, index, element);
//			// if (index > size || index < 0)
//			// throw new IndexOutOfBoundsException("Index: " + index
//			// + ", Size: " + size);
//			//
//			// ensureCapacity(size + 1); // Increments modCount!!
//			// System.arraycopy(elementData, index, elementData, index + 1, size
//			// - index);
//			// elementData[index] = element;
//			// size++;
//		}
//
//		public Object remove(int index) {
//			RangeCheck(index);
//			// return MyCache.removeData(cacheID, index); // ERROR
//			Object o = this.get(index);
//			return HyCache.removeListData(cacheID, o);
//			// RangeCheck(index);
//			//
//			// modCount++;
//			// E oldValue = (E) elementData[index];
//			//
//			// int numMoved = size - index - 1;
//			// if (numMoved > 0)
//			// System.arraycopy(elementData, index + 1, elementData, index,
//			// numMoved);
//			// elementData[--size] = null; // Let gc do its work
//			//
//			// return oldValue;
//		}
//
//		public boolean remove(Object o) {
//			// if (o == null) {
//			// for (int index = 0; index < size; index++)
//			// if (elementData[index] == null) {
//			// fastRemove(index);
//			// return true;
//			// }
//			// } else {
//			// for (int index = 0; index < size; index++)
//			// if (o.equals(elementData[index])) {
//			// fastRemove(index);
//			// return true;
//			// }
//			// }
//			// return false;
//			return HyCache.removeListData(cacheID, o);
//		}
//
//		// /*
//		// * Private remove method that skips bounds checking and does not
//		// return
//		// * the value removed.
//		// */
//		// private void fastRemove(int index) {
//		// modCount++;
//		// int numMoved = size - index - 1;
//		// if (numMoved > 0)
//		// System.arraycopy(elementData, index + 1, elementData, index,
//		// numMoved);
//		// elementData[--size] = null; // Let gc do its work
//		// }
//
//		public void clear() {
//			// modCount++;
//			// // Let gc do its work
//			// for (int i = 0; i < size; i++)
//			// elementData[i] = null;
//			// size = 0;
//			HyCache.clearData(cacheID);
//		}
//
//		public boolean addAll(Collection<?> c) {
//			// Object[] a = c.toArray();
//			// int numNew = a.length;
//			// ensureCapacity(size + numNew); // Increments modCount
//			// System.arraycopy(a, 0, elementData, size, numNew);
//			// size += numNew;
//			// return numNew != 0;
//			return HyCache.addListData(cacheID, c);
//		}
//
//		public boolean addAll(int index, Collection<?> c) {
//			// if (index > size || index < 0)
//			// throw new IndexOutOfBoundsException("Index: " + index
//			// + ", Size: " + size);
//			//
//			// Object[] a = c.toArray();
//			// int numNew = a.length;
//			// ensureCapacity(size + numNew); // Increments modCount
//			//
//			// int numMoved = size - index;
//			// if (numMoved > 0)
//			// System.arraycopy(elementData, index, elementData, index
//			// + numNew, numMoved);
//			//
//			// System.arraycopy(a, 0, elementData, index, numNew);
//			// size += numNew;
//			// return numNew != 0;
//			throw new Warning("addAll(index, Collection) not support and not impl");
//		}
//
//		protected void removeRange(int fromIndex, int toIndex) {
//			// modCount++;
//			// int numMoved = size - toIndex;
//			// System.arraycopy(elementData, toIndex, elementData, fromIndex,
//			// numMoved);
//			//
//			// // Let gc do its work
//			// int newSize = size - (toIndex - fromIndex);
//			// while (size != newSize)
//			// elementData[--size] = null;
//			// return MyCache.removeDataRange(ID, fromIndex, toIndex);
//			throw new Warning("removeRange(fromIndex, toIndex) not impl");
//		}
//
//		private void RangeCheck(int index) {
//			int size = size();
//			if (index >= size)
//				throw new IndexOutOfBoundsException("Index: " + index
//						+ ", Size: " + size);
//		}
//
//	}
	
}
