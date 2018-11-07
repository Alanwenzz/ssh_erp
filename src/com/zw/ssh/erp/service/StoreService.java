package com.zw.ssh.erp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.StoreDao;
import com.zw.ssh.erp.pojo.Orders;
import com.zw.ssh.erp.pojo.Store;
@Component
public class StoreService {
	private StoreDao storeDao;
	
	public StoreDao getStoreDao() {
		return storeDao;
	}
	@Resource
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}
	@Transactional
	public List<Store> getList(Store store) {
		// TODO Auto-generated method stub
		return storeDao.getList(store);
	}
	@Transactional
	public List<Store> findAll(Store t1, int firstResult, int rows) {
		// TODO Auto-generated method stub
		return storeDao.findAll(t1,firstResult,rows);
	}
	@Transactional
	public long getCount(Store t1) {
		// TODO Auto-generated method stub
		return storeDao.getCount(t1);
	}
	@Transactional
	public Store get(Long uuid) {
		// TODO Auto-generated method stub
		return storeDao.get(uuid);
	}
	@Transactional
	public void update(Store t) {
		// TODO Auto-generated method stub
		storeDao.update(t);
	}
	@Transactional
	public void delete(Store t) {
		// TODO Auto-generated method stub
		storeDao.delete(t);
	}
	@Transactional
	public void add(Store t) {
		// TODO Auto-generated method stub
		storeDao.add(t);
	}

}
