package haiyan.orm.database.sql.page;
// /*
// * Created on 2004-10-13
// */
// package com.haiyan.genmis.core.paging;
//
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Iterator;
//
// import com.haiyan.genmis.core.IRecord;
//
// /**
// * @author zhouxw
// */
// class CollectionPageFactory extends PageFactory {
//
// //
// private static final long serialVersionUID = 1L;
//
// //
// private Collection<IRecord> data;
//
// /**
// * @param data
// */
// CollectionPageFactory(Collection<IRecord> data) {
// this.data = data;
// }
//
// @Override
// public void dealPage(IPageCallBack callback) throws Throwable {
// Iterator<IRecord> itr = data.iterator();
// while (itr.hasNext()) {
// callback.deal(itr.next());
// }
// }
//
// @Override
// @SuppressWarnings("unchecked")
// public Page getPage(int maxPageRecordCount, int currPageNO)
// throws Throwable {
// ArrayList<IRecord> pageData = createList(maxPageRecordCount,
// currPageNO);
// Iterator<IRecord> itr = data.iterator();
// while (itr.hasNext()) {
// pageData.add(itr.next());
// }
// return new ArrayListPageFactory(pageData).getPage(maxPageRecordCount,
// currPageNO);
// }
//
// }
