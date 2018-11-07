package com.zw.ssh.erp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Orders;
@Component
public class OrdersDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	//添加用户
	public void add(Orders o) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(o);
	}
	public long getCount(Orders t1, Orders t2) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = getDetachedCriteria(t1,t2);
		dc.setProjection(Projections.rowCount());
		return (long) hibernateTemplate.findByCriteria(dc).get(0);
	}
	public DetachedCriteria getDetachedCriteria(Orders orders1,Orders orders2){
		DetachedCriteria dc=DetachedCriteria.forClass(Orders.class);
		if(orders1!=null){
			//根据订单类型
			if(null != orders1.getType() && orders1.getType().trim().length()>0){
				dc.add(Restrictions.eq("type", orders1.getType()));
			}
			//根据订单状态
			if(null != orders1.getState() && orders1.getState().trim().length()>0){
				dc.add(Restrictions.eq("state", orders1.getState()));
			}
			//根据订单创建者
			if(null != orders1.getCreater()){
				dc.add(Restrictions.eq("creater", orders1.getCreater()));
			}

		}
		return dc;
	}
	public List<Orders> findAll(Orders t1, Orders t2, int firstResult, int rows) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = getDetachedCriteria(t1,t2);
		return (List<Orders>) hibernateTemplate.findByCriteria(dc,firstResult,rows);	
	}
	public Orders get(Long uuid) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Orders.class, uuid);
	}
}
