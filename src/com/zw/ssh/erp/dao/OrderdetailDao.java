package com.zw.ssh.erp.dao;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Orderdetail;
@Component
public class OrderdetailDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public Orderdetail get(Long uuid) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Orderdetail.class, uuid);
	}
	public long getCount(Orderdetail queryParam, Object object, Object object2) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(Orderdetail.class);
		dc.setProjection(Projections.rowCount());
		if(queryParam!=null){
			if(null != queryParam.getGoodsname() && queryParam.getGoodsname().trim().length()>0){
				dc.add(Restrictions.like("goodsname", queryParam.getGoodsname(), MatchMode.ANYWHERE));
			}
			//根据状态查询
			if(null != queryParam.getState() && queryParam.getState().trim().length()>0){
				dc.add(Restrictions.eq("state", queryParam.getState()));
			}
			//根据订单查询
			if(null != queryParam.getOrders() && null != queryParam.getOrders().getUuid()){
				dc.add(Restrictions.eq("orders", queryParam.getOrders()));
			}

		}
		return (long) hibernateTemplate.findByCriteria(dc).get(0);
	}

}
