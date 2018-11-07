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
import com.zw.ssh.erp.service.EmpService;

public class LoginAction {
	private String name;
	private String pwd;
	private EmpService empService;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public EmpService getEmpService() {
		return empService;
	}
	@Resource
	public void setEmpService(EmpService empService) {
		this.empService = empService;
	}
	//检查用户名  密码
	public void checkUser() {
		try{
			Emp loginUser = empService.findByUsernameAndPwd(name, pwd);
			if(loginUser != null){
				ActionContext.getContext().getSession().put("loginUser", loginUser);
				ajaxReturn(true, "");
			}else{
				ajaxReturn(false, "用户名或密码不正确");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			ajaxReturn(false, "登陆失败");
		}
	}
	//显示名字
	public void showName(){
		//从session中取值
		Emp emp = (Emp) ActionContext.getContext().getSession().get("loginUser");
		//session值 显示
		if(null != emp){
			ajaxReturn(true, emp.getName());
		}else{
			ajaxReturn(false, "");
		}
	}
	//退出销毁session
	public void loginOut(){
		ActionContext.getContext().getSession().remove("loginUser");
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
	
	//ajax返回
		public void ajaxReturn(boolean success, String message){
			//杩斿洖鍓嶇鐨凧SON鏁版嵁
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("success",success);
			rtn.put("message",message);
			write(JSON.toJSONString(rtn));
		}
}
