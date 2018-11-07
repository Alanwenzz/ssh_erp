package com.zw.ssh.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.zw.ssh.erp.pojo.Emp;
import com.zw.ssh.erp.pojo.Orders;
import com.zw.ssh.erp.pojo.Store;
import com.zw.ssh.erp.service.StoreService;

/**
 * �ֿ�Action 
 * @author Administrator
 *
 */
public class StoreAction {
	private Store t;
	private Store t1;
	public Store getT() {
		return t;
	}
	public void setT(Store t) {
		this.t = t;
	}
	public Store getT1() {
		return t1;
	}
	public void setT1(Store t1) {
		this.t1 = t1;
	}
	private int page;
	private int rows;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	private StoreService storeService;	
	public StoreService getStoreService() {
		return storeService;
	}
	@Resource
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	private Store store;
	

	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	/**
	 * ֻ��ʾ��ǰ��½�û��µĲֿ�
	 */
	public void myList(){
		if(null == getStore()){
			//������ѯ����
			setStore(new Store());
		}
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");;
		//���ҵ�ǰ��½�û��µĲֿ�
		getStore().setEmpuuid(loginUser.getUuid());
		List<Store> list = storeService.getList(store);
		//
		String listString = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	//��ʾ���вֿ�
	public void findAll() {
		int firstResult = (page -1) * rows;
		List<Store> list = storeService.findAll(t1,firstResult, rows);
		long total = storeService.getCount(t1);
		//{total: total, rows:[]}
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("total", total);
		mapData.put("rows", list);
		String listString = JSON.toJSONString(mapData, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	public void listAll() {
		List<Store> list = storeService.getList(t1);
		//{total: total, rows:[]}
		String listString = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	public void write(String jsonString){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8"); 
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//��ʾҪ�޸ĵ��û�
	public void get() {
		t=storeService.get(t.getUuid());
		//ǿת����
		String jsonString = JSON.toJSONStringWithDateFormat(t,"yyyy-MM-dd");
		//���ǰ׺
		String jsonStringAfter = mapData(jsonString, "t");
		write(jsonStringAfter);
	}
	//�����û�����
	public void update() {
		try {
			storeService.update(t);
			ajaxReturn(true, "�޸ĳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "�޸�ʧ��");
		}
	}
	//ɾ���û�����sout
		public void delete() {
			t=storeService.get(t.getUuid());
			System.out.println(t.getUuid());
			try {
				storeService.delete(t);
				ajaxReturn(true, "ɾ���ɹ�");
			} catch (Exception e) {
				e.printStackTrace();
				ajaxReturn(false, "ɾ��ʧ��");
			}
		}
	//����û�
	public void add() {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			storeService.add(t);
			rtn.put("success",true);
			rtn.put("message","��ӳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			rtn.put("success",false);
			rtn.put("message","���ʧ��");
		}
		write(JSON.toJSONString(rtn));
	}
	//���ݼ�ǰ׺
	public String mapData(String jsonString, String prefix){
		//ԭ����תMap
		Map<String, Object> map = JSON.parseObject(jsonString);	
		//������Map
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for(String key : map.keySet()){
			if(map.get(key) instanceof Map){
				//key
				Map<String,Object> m2 = (Map<String,Object>)map.get(key);
				for(String key2 : m2.keySet()){
					dataMap.put(prefix + "." + key + "." + key2, m2.get(key2));
				}
			}else{
				dataMap.put(prefix + "." + key, map.get(key));
			}
		}
		return JSON.toJSONString(dataMap);
	}
	//ajax����
	public void ajaxReturn(boolean success, String message){
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success",success);
		rtn.put("message",message);
		write(JSON.toJSONString(rtn));
	}
}

