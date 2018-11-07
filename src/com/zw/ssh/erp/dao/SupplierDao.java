package com.zw.ssh.erp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Supplier;

@Component
public class SupplierDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	//查询所有用户
	public List<Supplier> findAll(Supplier e1, Supplier e2, int firstResult, int rows) {
		DetachedCriteria dc = getDetachedCriteria(e1,e2);
		return (List<Supplier>) hibernateTemplate.findByCriteria(dc,firstResult,rows);
	}
	//添加查询条件
	public DetachedCriteria getDetachedCriteria(Supplier supplier1,Supplier supplier2){
		DetachedCriteria dc=DetachedCriteria.forClass(Supplier.class);
		if(supplier1!=null){
			if(null != supplier1.getName() && supplier1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", supplier1.getName(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getAddress() && supplier1.getAddress().trim().length()>0){
				dc.add(Restrictions.like("address", supplier1.getAddress(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getContact() && supplier1.getContact().trim().length()>0){
				dc.add(Restrictions.like("contact", supplier1.getContact(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getTele() && supplier1.getTele().trim().length()>0){
				dc.add(Restrictions.like("tele", supplier1.getTele(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getEmail() && supplier1.getEmail().trim().length()>0){
				dc.add(Restrictions.like("email", supplier1.getEmail(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getType() && supplier1.getType().trim().length()>0){
				dc.add(Restrictions.eq("type", supplier1.getType()));
			}

		}
		
		return dc;
	}
	//添加用户
	public void add(Supplier e) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(e);
	}
	public Supplier get(Long long1) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Supplier.class, long1);
	}
	public void update(Supplier e) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(e);
	}
	public void updatePwd(int eid,String newPwd) {
		String sql="update Supplier set password=?0 where eid=?1";
		hibernateTemplate.bulkUpdate(sql,newPwd,eid);
		
	}
	public Supplier findByUsernameAndPwd(String name, String pwd) {
		// TODO Auto-generated method stub
		String hql = "from Supplier where name=?0 and password=?1";		
		List<Supplier> list = (List<Supplier>) hibernateTemplate.find(hql, name, pwd);
		//取第一条记录
		if(list.size() > 0){
			return list.get(0);
		}
		//返回空值
		return null;
	}
	public void delete(Supplier t) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(t);
	}
	
}
