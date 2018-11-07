package com.zw.ssh.erp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.SupplierDao;
import com.zw.ssh.erp.pojo.Supplier;

@Component
public class SupplierService {
	public int hashIterations=2;
	private SupplierDao supplierDao;
	@Resource
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	@Transactional
	public List<Supplier> findAll(Supplier e1, Supplier e2, int firstResult, int rows){
		return supplierDao.findAll(e1,e2,firstResult,rows);
	}
	@Transactional
	public void add(Supplier e) {
		supplierDao.add(e);
	}
	@Transactional
	public Supplier get(Long long1) {
		// TODO Auto-generated method stub
		return supplierDao.get(long1);
	}
	@Transactional
	public void update(Supplier e) {
		// TODO Auto-generated method stub
		supplierDao.update(e);
	}
	@Transactional
	public void delete(Supplier t) {
		// TODO Auto-generated method stub
		supplierDao.delete(t);
	}

}
