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
		//1. ���ö�����״̬
		orders.setState(Orders.STATE_CREATE);
		//2. ����������
		orders.setType(Orders.TYPE_IN);
		//3. �µ�ʱ��
		orders.setCreatetime(new Date());
		
		// �ϼƽ��
		double total = 0;
		
		for(Orderdetail detail : orders.getOrderDetails()){
			//�ۼƽ��
			total += detail.getMoney();
			//��ϸ��״̬
			detail.setState(Orderdetail.STATE_NOT_IN);
			//�������Ĺ�ϵ
			detail.setOrders(orders);
		}
		//���ö����ܽ��
		orders.setTotalmoney(total);
		
		//���浽DB 
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
		//����Ա�������Ա�������ƣ�key=Ա���ı�ţ�value=Ա��������
		Map<Long,String> empNameMap=new HashMap<Long,String>();
		//���湩Ӧ��
		Map<Long, String> supplierNameMap = new HashMap<Long, String>();
		//ѭ������ȡԱ��������
		for(Orders o:lo) {
			//�ӻ�����ȡ��Ա������
			o.setCreaterName(getEmpName(o.getCreater(),empNameMap));
			o.setCheckerName(getEmpName(o.getChecker(),empNameMap));
			o.setStarterName(getEmpName(o.getStarter(),empNameMap));
			o.setEnderName(getEmpName(o.getEnder(),empNameMap));
			
			//�ӻ�����ȡ����Ӧ������
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
	 * ���
	 * @param uuid �������
	 * @param empUuid ���Ա
	 */
	@Transactional
	public void doCheck(Long uuid, Long empUuid){
		
		//��ȡ����������־û�״̬
		Orders orders = ordersDao.get(uuid);
		//������״̬
		if(!Orders.STATE_CREATE.equals(orders.getState())){
			try {
				throw new Exception("�ף��ö����Ѿ���˹���");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//1. �޸Ķ�����״̬
		orders.setState(Orders.STATE_CHECK);
		//2. ��˵�ʱ��
		orders.setChecktime(new Date());
		//3. �����
		orders.setChecker(empUuid);
	}
	@Transactional
	public void doStart(long uuid, long empUuid) {
		// TODO Auto-generated method stub
		//��ȡ����������־û�״̬
		Orders orders = ordersDao.get(uuid);
		//������״̬
		if(!Orders.STATE_CHECK.equals(orders.getState())){
			try {
				throw new Exception("�ף��ö����Ѿ�ȷ�Ϲ���");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//1. �޸Ķ�����״̬
		orders.setState(Orders.STATE_START);
		//2. ȷ�ϵ�ʱ��
		orders.setStarttime(new Date());
		//3. ȷ����
		orders.setStarter(empUuid);
	}

}
