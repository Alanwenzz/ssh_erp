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


	//���
	@Transactional
	public void doInStore(Long uuid,Long storeuuid, Long empuuid){
		//******** ��1�� ������Ʒ��ϸ**********
		//1. ��ȡ��ϸ��Ϣ
		Orderdetail detail = orderdetailDao.get(uuid);
		//2. �ж���ϸ��״̬��һ����δ����
		if(!Orderdetail.STATE_NOT_IN.equals(detail.getState())){
			try {
				throw new Exception("�����ظ����");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//3. �޸�״̬Ϊ�����
		detail.setState(Orderdetail.STATE_IN);
		//4. ���ʱ��
		detail.setEndtime(new Date());
		//5. ���Ա
		detail.setEnder(empuuid);
		//6. �뵽�ĸ��ֿ�
		detail.setStoreuuid(storeuuid);
		
		//*******��2 �� ������Ʒ�ֿ���*********
		//1. ������ѯ������
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		//2. ͨ����ѯ ����Ƿ���ڿ����Ϣ
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//���ڵĻ�����Ӧ���ۼ���������
			long num = 0;
			if(null != storeList.get(0).getNum()){
				num = storeList.get(0).getNum().longValue();
			}
			storeList.get(0).setNum(num + detail.getNum());
		}else{
			//�����ڣ���Ӧ�ò�����ļ�¼
			storedetail.setNum(detail.getNum());
			storedetailDao.add(storedetail);
		}
		
		//****** ��3�� ��Ӳ�����¼*****
		//1. ����������¼
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(Storeoper.TYPE_IN);
		//2. ���浽���ݿ���
		storeoperDao.add(log);
		
		//**** ��4�� �Ƿ���Ҫ���¶�����״̬********
	
		//1. ������ѯ����
		Orderdetail queryParam = new Orderdetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		//2. ��ѯ�������Ƿ񻹴���״̬Ϊ0����ϸ
		queryParam.setState(Orderdetail.STATE_NOT_IN);
		//3. ���� getCount�������������Ƿ����״̬Ϊ0����ϸ
		long count = orderdetailDao.getCount(queryParam, null, null);
		System.out.println(count);
		if(count == 0){
			//4. ���е���ϸ���Ѿ�����ˣ���Ҫ���¶�����״̬���رն���
			orders.setState(Orders.STATE_END);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
		
	}
	/**
	 * ����
	 */
	@Transactional
	public void doOutStore(Long uuid,Long storeuuid, Long empuuid){
		//******** ��1�� ������Ʒ��ϸ**********
		//1. ��ȡ��ϸ��Ϣ
		Orderdetail detail = orderdetailDao.get(uuid);
		//2. �ж���ϸ��״̬��һ����δ����
		if(!Orderdetail.STATE_NOT_OUT.equals(detail.getState())){
			try {
				throw new Exception("�ף�����ϸ�Ѿ������ˣ������ظ�����");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//3. �޸�״̬Ϊ�ѳ���
		detail.setState(Orderdetail.STATE_IN);
		//4. ����ʱ��
		detail.setEndtime(new Date());
		//5. ���Ա
		detail.setEnder(empuuid);
		//6. ���ĸ��ֿ��
		detail.setStoreuuid(storeuuid);
		
		//*******��2 �� ������Ʒ�ֿ���*********
		//1. ������ѯ������
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		//2. ͨ����ѯ ����Ƿ���ڿ����Ϣ
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//���ڵĻ�����Ӧ���ۼ���������
			Storedetail sd = storeList.get(0);
			sd.setNum(sd.getNum() - detail.getNum());
			if(sd.getNum() < 0){
				try {
					throw new Exception("��治��");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			try {
				throw new Exception("��治��");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//****** ��3�� ��Ӳ�����¼*****
		//1. ����������¼
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(Storeoper.TYPE_OUT);
		//2. ���浽���ݿ���
		storeoperDao.add(log);
		
		//**** ��4�� �Ƿ���Ҫ���¶�����״̬********
	
		//1. ������ѯ����
		Orderdetail queryParam = new Orderdetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		//2. ��ѯ�������Ƿ񻹴���״̬Ϊ0����ϸ
		queryParam.setState(Orderdetail.STATE_NOT_OUT);
		//3. ���� getCount�������������Ƿ����״̬Ϊ0����ϸ
		long count = orderdetailDao.getCount(queryParam, null, null);
		if(count == 0){
			//4. ���е���ϸ���Ѿ������ˣ���Ҫ���¶�����״̬���رն���
			orders.setState(Orders.STATE_OUT);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
	}


}
