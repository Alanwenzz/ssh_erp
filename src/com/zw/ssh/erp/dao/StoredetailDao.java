package com.zw.ssh.erp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Storedetail;
@Component
public class StoredetailDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public List<Storedetail> getList(Storedetail storedetail, Object object, Object object2) {
		// TODO Auto-generated method stub
		DetachedCriteria dc=DetachedCriteria.forClass(Storedetail.class);
		if(storedetail!=null){
			if(null != storedetail.getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storedetail.getGoodsuuid()));
			}
			if(null != storedetail.getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storedetail.getStoreuuid()));
			}
		}
		return (List<Storedetail>) hibernateTemplate.findByCriteria(dc);
	}
	public void add(Storedetail s) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(s);
	}
	public long getCount(Storedetail storedetail) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(Storedetail.class);
		dc.setProjection(Projections.rowCount());
		if(storedetail!=null){
			if(null != storedetail.getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storedetail.getGoodsuuid()));
			}
			if(null != storedetail.getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storedetail.getStoreuuid()));
			}
		}
		return (long) hibernateTemplate.findByCriteria(dc).get(0);
	}
}
