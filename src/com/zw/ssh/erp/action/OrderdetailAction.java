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
 * 订单明细Action 
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
	 * 入库
	 */
	public void doInStore(){
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//调用明细入库业务
			orderdetailService.doInStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "入库成功");
		} catch (Exception e) {
			ajaxReturn(false, "入库失败");
			e.printStackTrace();
		}
	}
	/**
	 * 出库
	 */
	public void doOutStore(){
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//调用明细入库业务
			orderdetailService.doOutStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "出库成功");
		} catch (Exception e) {
			ajaxReturn(false, "出库失败");
			e.printStackTrace();
		}
	}
	//ajax返回
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
