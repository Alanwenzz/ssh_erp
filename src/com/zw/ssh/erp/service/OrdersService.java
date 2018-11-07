package com.zw.ssh.erp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.EmpDao;
import com.zw.ssh.erp.dao.OrdersDao;
import com.zw.ssh.erp.dao.SupplierDao;
import com.zw.ssh.erp.pojo.Orderdetail;
import com.zw.ssh.erp.pojo.Orders;
@Component
public class OrdersService {
	@Resource
	private OrdersDao ordersDao;
	@Resource
	private EmpDao empDao;
	@Resource
	private SupplierDao supplierDao;
	public SupplierDao getSupplierDao() {
		return supplierDao;
	}

	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public EmpDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

	public OrdersDao getOrdersDao() {
		return ordersDao;
	}

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	@Transactional
	public void add(Orders orders) {
		//1. 设置订单的状态
		orders.setState(Orders.STATE_CREATE);
		//2. 订单的类型
		orders.setType(Orders.TYPE_IN);
		//3. 下单时间
		orders.setCreatetime(new Date());
		
		// 合计金额
		double total = 0;
		
		for(Orderdetail detail : orders.getOrderDetails()){
			//累计金额
			total += detail.getMoney();
			//明细的状态
			detail.setState(Orderdetail.STATE_NOT_IN);
			//跟订单的关系
			detail.setOrders(orders);
		}
		//设置订单总金额
		orders.setTotalmoney(total);
		
		//保存到DB 
		ordersDao.add(orders);
	}
	@Transactional
	public long getCount(Orders t1, Orders t2) {
		// TODO Auto-generated method stub
		return ordersDao.getCount(t1,t2);
	}
	@Transactional
	public List<Orders> findAll(Orders t1, Orders t2, int firstResult, int rows) {
		// TODO Auto-generated method stub
		List<Orders> lo= ordersDao.findAll(t1,t2,firstResult,rows);
		//缓存员工编号与员工的名称，key=员工的编号，value=员工的名称
		Map<Long,String> empNameMap=new HashMap<Long,String>();
		//缓存供应商
		Map<Long, String> supplierNameMap = new HashMap<Long, String>();
		//循环，获取员工的名称
		for(Orders o:lo) {
			//从缓存中取出员工名称
			o.setCreaterName(getEmpName(o.getCreater(),empNameMap));
			o.setCheckerName(getEmpName(o.getChecker(),empNameMap));
			o.setStarterName(getEmpName(o.getStarter(),empNameMap));
			o.setEnderName(getEmpName(o.getEnder(),empNameMap));
			
			//从缓存中取出供应商名称
			o.setSupplierName(getSupplierName(o.getSupplieruuid(),supplierNameMap));
		}
		return lo;
	}
	public String getEmpName(Long uuid,Map<Long,String> empNameMap) {
		if(null == uuid){
			return null;
		}
		String empName = empNameMap.get(uuid);
		if(null == empName){
			empName = empDao.get(uuid).getName();
			empNameMap.put(uuid, empName);
		}
		return empName;
	}
	private String getSupplierName(Long uuid, Map<Long, String> supplierNameMap){
		if(null == uuid){
			return null;
		}
		String supplierName = supplierNameMap.get(uuid);
		if(null == supplierName){
			supplierName = supplierDao.get(uuid).getName();
			supplierNameMap.put(uuid, supplierName);
		}
		return supplierName;
	}

	/**
	 * 审核
	 * @param uuid 订单编号
	 * @param empUuid 审核员
	 */
	@Transactional
	public void doCheck(Long uuid, Long empUuid){
		
		//获取订单，进入持久化状态
		Orders orders = ordersDao.get(uuid);
		//订单的状态
		if(!Orders.STATE_CREATE.equals(orders.getState())){
			try {
				throw new Exception("亲！该订单已经审核过了");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//1. 修改订单的状态
		orders.setState(Orders.STATE_CHECK);
		//2. 审核的时间
		orders.setChecktime(new Date());
		//3. 审核人
		orders.setChecker(empUuid);
	}
	@Transactional
	public void doStart(long uuid, long empUuid) {
		// TODO Auto-generated method stub
		//获取订单，进入持久化状态
		Orders orders = ordersDao.get(uuid);
		//订单的状态
		if(!Orders.STATE_CHECK.equals(orders.getState())){
			try {
				throw new Exception("亲！该订单已经确认过了");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//1. 修改订单的状态
		orders.setState(Orders.STATE_START);
		//2. 确认的时间
		orders.setStarttime(new Date());
		//3. 确认人
		orders.setStarter(empUuid);
	}

}
