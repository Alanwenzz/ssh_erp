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
	//����û���  ����
	public void checkUser() {
		try{
			Emp loginUser = empService.findByUsernameAndPwd(name, pwd);
			if(loginUser != null){
				ActionContext.getContext().getSession().put("loginUser", loginUser);
				ajaxReturn(true, "");
			}else{
				ajaxReturn(false, "�û��������벻��ȷ");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			ajaxReturn(false, "��½ʧ��");
		}
	}
	//��ʾ����
	public void showName(){
		//��session��ȡֵ
		Emp emp = (Emp) ActionContext.getContext().getSession().get("loginUser");
		//sessionֵ ��ʾ
		if(null != emp){
			ajaxReturn(true, emp.getName());
		}else{
			ajaxReturn(false, "");
		}
	}
	//�˳�����session
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
	
	//ajax����
		public void ajaxReturn(boolean success, String message){
			//返回前端的JSON数据
			Map<String, Object> rtn = new HashMap<String, Object>();
			rtn.put("success",success);
			rtn.put("message",message);
			write(JSON.toJSONString(rtn));
		}
}
