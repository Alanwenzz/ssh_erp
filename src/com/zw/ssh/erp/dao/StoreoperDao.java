package com.zw.ssh.erp.dao;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.pojo.Storeoper;
@Component
public class StoreoperDao {
	private HibernateTemplate hibernateTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public void add(Storeoper s) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(s);
	}
	public long getCount(Storeoper t1, Storeoper t2) {
		// TODO Auto-generated method stub
		DetachedCriteria dc=getDetachedCriteria(t1, t2);
		dc.setProjection(Projections.rowCount());
		return (long) hibernateTemplate.findByCriteria(dc).get(0);
	}
	public List<Storeoper> listByPage(Storeoper t1, Storeoper t2, int firstResult, int rows) {
		// TODO Auto-generated method stub
		DetachedCriteria dc=getDetachedCriteria(t1, t2);
		return (List<Storeoper>) hibernateTemplate.findByCriteria(dc,firstResult,rows);
	}
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Storeoper storeoper1,Storeoper storeoper2){
		DetachedCriteria dc=DetachedCriteria.forClass(Storeoper.class);
		if(storeoper1!=null){
			//根据类型查询
			if(null != storeoper1.getType() && storeoper1.getType().trim().length()>0){
				dc.add(Restrictions.eq("type", storeoper1.getType()));
			}
			//商品查询
			if(null != storeoper1.getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storeoper1.getGoodsuuid()));
			}
			//仓库
			if(null != storeoper1.getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storeoper1.getStoreuuid()));
			}
			//员工
			if(null != storeoper1.getEmpuuid()){
				dc.add(Restrictions.eq("empuuid", storeoper1.getEmpuuid()));
			}
			//操作时间 开始
			if(null != storeoper1.getOpertime()){
				Calendar car = Calendar.getInstance();
				car.setTime(storeoper1.getOpertime());
				car.set(Calendar.HOUR, 0);
				car.set(Calendar.MINUTE, 0);
				car.set(Calendar.SECOND, 0);
				car.set(Calendar.MILLISECOND,0);
				//2017-02-01 15:30:05 => 2017-02-01 00:00:00
				dc.add(Restrictions.ge("opertime", car.getTime()));
			}
		}
		if(storeoper2!=null){
			//操作时间 结束
			if(null != storeoper2.getOpertime()){
				Calendar car = Calendar.getInstance();
				car.setTime(storeoper2.getOpertime());
				car.set(Calendar.HOUR, 23);
				car.set(Calendar.MINUTE, 59);
				car.set(Calendar.SECOND, 59);
				car.set(Calendar.MILLISECOND,999);
				//2017-02-01 15:30:05 => 2017-02-01 23:59:59.999
				dc.add(Restrictions.le("opertime", car.getTime()));
			}
		}
		return dc;
	}
}
