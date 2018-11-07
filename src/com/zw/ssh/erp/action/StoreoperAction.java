package com.zw.ssh.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zw.ssh.erp.pojo.Storeoper;
import com.zw.ssh.erp.service.StoreoperService;

public class StoreoperAction {
	@Resource
	private StoreoperService storeoperService;
	private Storeoper t1;
	private Storeoper t2;
	public Storeoper getT2() {
		return t2;
	}
	public void setT2(Storeoper t2) {
		this.t2 = t2;
	}
	public Storeoper getT1() {
		return t1;
	}
	public StoreoperService getStoreoperService() {
		return storeoperService;
	}
	public void setStoreoperService(StoreoperService storeoperService) {
		this.storeoperService = storeoperService;
	}
	public Storeoper getST1() {
		return t1;
	}
	public void setT1(Storeoper t1) {
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
	public void listByPage() {
		int firstResult = (page -1) * rows;
		List<Storeoper> le = storeoperService.listByPage(t1,t2,firstResult, rows);
		long ls=storeoperService.getCount(t1,t2);
		Map<String,Object> ms=new HashMap<String,Object>();
		ms.put("total", ls);
		ms.put("rows", le);
		//关闭循环引用SerializerFeature.DisableCircularReferenceDetect
		String listString = JSON.toJSONString(ms, SerializerFeature.DisableCircularReferenceDetect);
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
}
