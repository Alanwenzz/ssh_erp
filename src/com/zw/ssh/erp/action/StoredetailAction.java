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
import com.zw.ssh.erp.pojo.Storedetail;
import com.zw.ssh.erp.service.StoredetailService;

public class StoredetailAction {
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
	private Storedetail t;
	private Storedetail t1;
	
	public Storedetail getT() {
		return t;
	}
	public void setT(Storedetail t) {
		this.t = t;
	}
	public Storedetail getT1() {
		return t1;
	}
	public void setT1(Storedetail t1) {
		this.t1 = t1;
	}
	public StoredetailService getStoredetailService() {
		return storedetailService;
	}
	public void setStoredetailService(StoredetailService storedetailService) {
		this.storedetailService = storedetailService;
	}
	@Resource
	private StoredetailService storedetailService;
	
	public void listByPage() {
		int firstResult = (page -1) * rows;
		List<Storedetail> le=storedetailService.getListByPage(t1,firstResult, rows);
		long total = storedetailService.getCount(t1);
		System.out.println();
		//{total: total, rows:[]}
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("total", total);
		mapData.put("rows", le);
		//关闭循环引用SerializerFeature.DisableCircularReferenceDetect
		String listString = JSON.toJSONString(mapData, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	//json返回
    public void write(String jsonString){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8"); 
			response.getWriter().write(jsonString);
		} catch (IOException e) {
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
}
