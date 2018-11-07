package com.zw.ssh.erp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Emp;
@Component
public class EmpDao{
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	//查询所有用户
	public List<Emp> findAll(Emp e1, Emp e2, int firstResult, int rows) {
		DetachedCriteria dc = getDetachedCriteria(e1,e2);
		return (List<Emp>) hibernateTemplate.findByCriteria(dc,firstResult,rows);
	}
	//添加查询条件
	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2){
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		dc.addOrder(Order.asc("uuid"));
		if(emp1!=null){
			if(null != emp1.getPassword() && emp1.getPassword().trim().length()>0){
				dc.add(Restrictions.like("pwd", emp1.getPassword(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getName() && emp1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getGender()){
				dc.add(Restrictions.eq("gender", emp1.getGender()));
			}
			if(null != emp1.getEmail() && emp1.getEmail().trim().length()>0){
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getTele() && emp1.getTele().trim().length()>0){
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getAddress() && emp1.getAddress().trim().length()>0){
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));
			}

			if(null != emp1.getBirthday()){
				dc.add(Restrictions.ge("birthday", emp1.getBirthday()));
			}

		}
		if(null != emp2){

			if(null != emp2.getBirthday()){
				dc.add(Restrictions.le("birthday", emp2.getBirthday()));
			}
		}
		
		return dc;
	}
	//添加用户
	public void add(Emp e) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(e);
	}
	public Emp get(long l) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Emp.class, l);
	}
	public void update(Emp e) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(e);
	}
	public void delete(Emp e) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(e);
	}
	public void updatePwd(long l,String newPwd) {
		String sql="update Emp set password=?0 where uuid=?1";
		hibernateTemplate.bulkUpdate(sql,newPwd,l);
		
	}
	public Emp findByUsernameAndPwd(String name, String pwd) {
		// TODO Auto-generated method stub
		String hql = "from Emp where name=?0 and password=?1";		
		List<Emp> list = (List<Emp>) hibernateTemplate.find(hql, name, pwd);
		//取第一条记录
		if(list.size() > 0){
			return list.get(0);
		}
		//返回空值
		return null;
	}
	
}
