package com.zw.ssh.erp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.StoreoperDao;
import com.zw.ssh.erp.pojo.Storeoper;
@Component
public class StoreoperService {
	@Resource
	private StoreoperDao storeoperDao;
	public StoreoperDao getStoreoperDao() {
		return storeoperDao;
	}
	public void setStoreoperDao(StoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}
	@Transactional
	public long getCount(Storeoper t1, Storeoper t2) {
		// TODO Auto-generated method stub
		return storeoperDao.getCount(t1,t2);
	}
	@Transactional
	public List<Storeoper> listByPage(Storeoper t1, Storeoper t2, int firstResult, int rows) {
		// TODO Auto-generated method stub
		return storeoperDao.listByPage(t1,t2,firstResult,rows);
	}

}
