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
import com.zw.ssh.erp.pojo.Orderdetail;
import com.zw.ssh.erp.pojo.Orders;
import com.zw.ssh.erp.service.OrdersService;


public class OrdersAction {
	@Resource
	private OrdersService ordersService;
	
	public OrdersService getOrdersService() {
		return ordersService;
	}
	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private Orders t;
	private Orders t1;
	private Orders t2;
	public Orders getT1() {
		return t1;
	}
	public void setT1(Orders t1) {
		this.t1 = t1;
	}
	public Orders getT2() {
		return t2;
	}
	public void setT2(Orders t2) {
		this.t2 = t2;
	}
	public Orders getT() {
		return t;
	}
	public void setT(Orders t) {
		this.t = t;
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
	//接收订单明细的json格式的字符,数组形式的json字符串,里面的元素应该是每个订单明细
	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	//显示所有订单
	public void findAll() {
		int firstResult = (page -1) * rows;
		List<Orders> list = ordersService.findAll(t1,t2,firstResult, rows);
		long total = ordersService.getCount(t1,t2);
		//{total: total, rows:[]}
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("total", total);
		mapData.put("rows", list);
		String listString = JSON.toJSONString(mapData, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	/**
	 * 添加订单
	 */

	public void add() {
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//System.out.println(json);
			Orders orders = getT();
			//订单创建者
			orders.setCreater(loginUser.getUuid());
			//JSON.parseArray  把json字符串转数组
			List<Orderdetail> detailList = JSON.parseArray(json,Orderdetail.class);
			//订单明细
			orders.setOrderDetails(detailList);
			//System.out.println(detailList.size());
			ordersService.add(orders);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			ajaxReturn(false, "添加订单失败");
			e.printStackTrace();
		}
	}
	//审核
	public void doCheck(){
		//获取当前登陆用户
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//调用审核业务
			ordersService.doCheck(getId(), loginUser.getUuid());
			ajaxReturn(true, "审核成功");
		} catch (Exception e) {
			ajaxReturn(false, "审核失败");
			e.printStackTrace();
		}
	}
	/**
	 * 采购订单确认
	 */
	public void doStart(){
		//获取当前登陆用户
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		try {
			//调用审核业务
			ordersService.doStart(getId(), loginUser.getUuid());
			ajaxReturn(true, "确认成功");
		} catch (Exception e) {
			ajaxReturn(false, "确认失败");
			e.printStackTrace();
		}
	}
	
	//我的订单
	public void myListByPage() {
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		if(null == loginUser){
			//用户没有登陆，session已失效
			ajaxReturn(false, "亲！您还没有登陆");
			return;
		}
		if(null == getT1()){
			setT1(new Orders());
		}
		getT1().setCreater(loginUser.getUuid());
		
		this.findAll();
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
