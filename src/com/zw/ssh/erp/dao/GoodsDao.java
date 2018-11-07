package com.zw.ssh.erp.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Goods;

@Component
public class GoodsDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	//查询所有商品
	public List<Goods> findAll(Goods e1, Goods e2, int firstResult, int rows) {
		DetachedCriteria dc = getDetachedCriteria(e1,e2);
		return (List<Goods>) hibernateTemplate.findByCriteria(dc,firstResult,rows);
	}
	//添加查询条件
    public DetachedCriteria getDetachedCriteria(Goods goods1,Goods goods2){
		DetachedCriteria dc=DetachedCriteria.forClass(Goods.class);
		if(goods1!=null){
			if(null != goods1.getName() && goods1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", goods1.getName(), MatchMode.ANYWHERE));
			}
		}
		
		return dc;
	}
	//添加用户
	public void add(Goods e) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(e);
	}
	public Goods get(Long long1) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Goods.class, long1);
	}
	public void update(Goods e) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(e);
	}
}
