package com.zw.ssh.erp.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.OrderdetailDao;
import com.zw.ssh.erp.dao.StoredetailDao;
import com.zw.ssh.erp.dao.StoreoperDao;
import com.zw.ssh.erp.pojo.Orderdetail;
import com.zw.ssh.erp.pojo.Orders;
import com.zw.ssh.erp.pojo.Storedetail;
import com.zw.ssh.erp.pojo.Storeoper;
@Component
public class OrderdetailService {
	private StoreoperDao storeoperDao;
	
	public StoreoperDao getStoreoperDao() {
		return storeoperDao;
	}
	@Resource
	public void setStoreoperDao(StoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}


	private StoredetailDao storedetailDao;
	public StoredetailDao getStoredetailDao() {
		return storedetailDao;
	}
	@Resource
	public void setStoredetailDao(StoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}


	private OrderdetailDao orderdetailDao;	
	public OrderdetailDao getOrderdetailDao() {
		return orderdetailDao;
	}

	@Resource
	public void setOrderdetailDao(OrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
	}


	//入库
	@Transactional
	public void doInStore(Long uuid,Long storeuuid, Long empuuid){
		//******** 第1步 更新商品明细**********
		//1. 获取明细信息
		Orderdetail detail = orderdetailDao.get(uuid);
		//2. 判断明细的状态，一定是未入库的
		if(!Orderdetail.STATE_NOT_IN.equals(detail.getState())){
			try {
				throw new Exception("不能重复入库");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//3. 修改状态为已入库
		detail.setState(Orderdetail.STATE_IN);
		//4. 入库时间
		detail.setEndtime(new Date());
		//5. 库管员
		detail.setEnder(empuuid);
		//6. 入到哪个仓库
		detail.setStoreuuid(storeuuid);
		
		//*******第2 步 更新商品仓库库存*********
		//1. 构建查询的条件
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		//2. 通过查询 检查是否存在库存信息
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//存在的话，则应该累加它的数量
			long num = 0;
			if(null != storeList.get(0).getNum()){
				num = storeList.get(0).getNum().longValue();
			}
			storeList.get(0).setNum(num + detail.getNum());
		}else{
			//不存在，则应该插入库存的记录
			storedetail.setNum(detail.getNum());
			storedetailDao.add(storedetail);
		}
		
		//****** 第3步 添加操作记录*****
		//1. 构建操作记录
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(Storeoper.TYPE_IN);
		//2. 保存到数据库中
		storeoperDao.add(log);
		
		//**** 第4步 是否需要更新订单的状态********
	
		//1. 构建查询条件
		Orderdetail queryParam = new Orderdetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		//2. 查询订单下是否还存在状态为0的明细
		queryParam.setState(Orderdetail.STATE_NOT_IN);
		//3. 调用 getCount方法，来计算是否存在状态为0的明细
		long count = orderdetailDao.getCount(queryParam, null, null);
		System.out.println(count);
		if(count == 0){
			//4. 所有的明细都已经入库了，则要更新订单的状态，关闭订单
			orders.setState(Orders.STATE_END);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
		
	}
	/**
	 * 出库
	 */
	@Transactional
	public void doOutStore(Long uuid,Long storeuuid, Long empuuid){
		//******** 第1步 更新商品明细**********
		//1. 获取明细信息
		Orderdetail detail = orderdetailDao.get(uuid);
		//2. 判断明细的状态，一定是未入库的
		if(!Orderdetail.STATE_NOT_OUT.equals(detail.getState())){
			try {
				throw new Exception("亲！该明细已经出库了，不能重复出库");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//3. 修改状态为已出库
		detail.setState(Orderdetail.STATE_IN);
		//4. 出库时间
		detail.setEndtime(new Date());
		//5. 库管员
		detail.setEnder(empuuid);
		//6. 从哪个仓库出
		detail.setStoreuuid(storeuuid);
		
		//*******第2 步 更新商品仓库库存*********
		//1. 构建查询的条件
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		//2. 通过查询 检查是否存在库存信息
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//存在的话，则应该累加它的数量
			Storedetail sd = storeList.get(0);
			sd.setNum(sd.getNum() - detail.getNum());
			if(sd.getNum() < 0){
				try {
					throw new Exception("库存不足");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			try {
				throw new Exception("库存不足");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//****** 第3步 添加操作记录*****
		//1. 构建操作记录
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(Storeoper.TYPE_OUT);
		//2. 保存到数据库中
		storeoperDao.add(log);
		
		//**** 第4步 是否需要更新订单的状态********
	
		//1. 构建查询条件
		Orderdetail queryParam = new Orderdetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		//2. 查询订单下是否还存在状态为0的明细
		queryParam.setState(Orderdetail.STATE_NOT_OUT);
		//3. 调用 getCount方法，来计算是否存在状态为0的明细
		long count = orderdetailDao.getCount(queryParam, null, null);
		if(count == 0){
			//4. 所有的明细都已经出库了，则要更新订单的状态，关闭订单
			orders.setState(Orders.STATE_OUT);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
	}


}
