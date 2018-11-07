package com.zw.ssh.erp.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.zw.ssh.erp.pojo.Menu;
import com.zw.ssh.erp.service.MenuService;

public class MenuAction {
	private MenuService menuService;

	public MenuService getMenuService() {
		return menuService;
	}
	@Resource
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public void getMenuTree(){
		//从0读取整个表
		Menu menu = menuService.get("0");
		write(JSON.toJSONString(menu));
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
