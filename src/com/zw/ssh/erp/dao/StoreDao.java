package com.zw.ssh.erp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Store;
@Component
public class StoreDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public List<Store> getList(Store store) {
		// TODO Auto-generated method stub
		DetachedCriteria dc =DetachedCriteria.forClass(Store.class);
		if(store!=null){
			if(null != store.getName()){
				dc.add(Restrictions.eq("name", store.getName()));
			}
			if(null != store.getEmpuuid()){
				dc.add(Restrictions.eq("empuuid", store.getEmpuuid()));
			}
		}
		return (List<Store>) hibernateTemplate.findByCriteria(dc);
	}
	public Store get(Long long1) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Store.class, long1);
	}
	public List<Store> findAll(Store store, int firstResult, int rows) {
		// TODO Auto-generated method stub
		DetachedCriteria dc =DetachedCriteria.forClass(Store.class);
		if(store!=null){
			if(null != store.getName()&&store.getName().trim().length()>0){
				dc.add(Restrictions.eq("name", store.getName()));
			}
			if(null != store.getEmpuuid()&&store.getEmpuuid()!=0){
				dc.add(Restrictions.eq("empuuid", store.getEmpuuid()));
			}
		}
		return (List<Store>) hibernateTemplate.findByCriteria(dc,firstResult,rows);
	}
	public long getCount(Store store) {
		// TODO Auto-generated method stub
		DetachedCriteria dc =DetachedCriteria.forClass(Store.class);
		dc.setProjection(Projections.rowCount());
		if(store!=null){
			if(null != store.getName()&&store.getName().trim().length()>0){
				dc.add(Restrictions.eq("name", store.getName()));
			}
			if(null != store.getEmpuuid()&&store.getEmpuuid()!=0){
				dc.add(Restrictions.eq("empuuid", store.getEmpuuid()));
			}
		}
		return (long) hibernateTemplate.findByCriteria(dc).get(0);
	}
	public void update(Store t) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(t);
	}
	public void delete(Store t) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(t);
	}
	public void add(Store t) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(t);
	}
}
