package com.zw.ssh.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.zw.ssh.erp.pojo.Emp;
import com.zw.ssh.erp.service.OrderdetailService;

/**
 * ������ϸAction 
 * @author Administrator
 *
 */
public class OrderdetailAction {

	private OrderdetailService orderdetailService;
	
	public OrderdetailService getOrderdetailService() {
		return orderdetailService;
	}
	@Resource
	public void setOrderdetailService(OrderdetailService orderdetailService) {
		this.orderdetailService = orderdetailService;
	}

	private Long storeuuid;
	
	public Long getStoreuuid() {
		return storeuuid;
	}

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * ���
	 */
	public void doInStore(){
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//�û�û�е�½��session��ʧЧ
			ajaxReturn(false, "�ף�����û�е�½");
			return;
		}
		try {
			//������ϸ���ҵ��
			orderdetailService.doInStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "���ɹ�");
		} catch (Exception e) {
			ajaxReturn(false, "���ʧ��");
			e.printStackTrace();
		}
	}
	/**
	 * ����
	 */
	public void doOutStore(){
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//�û�û�е�½��session��ʧЧ
			ajaxReturn(false, "�ף�����û�е�½");
			return;
		}
		try {
			//������ϸ���ҵ��
			orderdetailService.doOutStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "����ɹ�");
		} catch (Exception e) {
			ajaxReturn(false, "����ʧ��");
			e.printStackTrace();
		}
	}
	//ajax����
		public void ajaxReturn(boolean success, String message){
			
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("success",success);
			rtn.put("message",message);
			write(JSON.toJSONString(rtn));
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

}
