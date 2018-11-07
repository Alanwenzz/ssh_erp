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
import com.opensymphony.xwork2.ActionSupport;
import com.zw.ssh.erp.pojo.Emp;
import com.zw.ssh.erp.service.EmpService;

public class EmpAction extends ActionSupport{
	private EmpService empService;
	private Emp t;
	private Emp t1;
	private Emp t2;
	private int page;
	private int rows;
	private String oldPwd;
	private String newPwd;

	public String getOldPwd() {
		return oldPwd;
	}


	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}


	public String getNewPwd() {
		return newPwd;
	}


	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}


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

	public Emp getT() {
		return t;
	}


	public void setT(Emp t) {
		this.t = t;
	}


	public Emp getE1() {
		return t1;
	}


	public void setE1(Emp t1) {
		this.t1 = t1;
	}


	public Emp getE2() {
		return t2;
	}


	public void setE2(Emp t2) {
		this.t2 = t2;
	}

	@Resource
	public void setEmpService(EmpService empService) {
		this.empService = empService;
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
	//显示所有用户
	public void findAll() {
		int firstResult = (page -1) * rows;
		List<Emp> le = empService.findAll(t1,t2,firstResult, rows);
		//关闭循环引用SerializerFeature.DisableCircularReferenceDetect
		String listString = JSON.toJSONString(le, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	//显示要修改的用户
	public void get() {
		t=empService.get(t.getUuid());
		//强转日期
		String jsonString = JSON.toJSONStringWithDateFormat(t,"yyyy-MM-dd");
		//添加前缀
		String jsonStringAfter = mapData(jsonString, "t");
		write(jsonStringAfter);
	}
	//更新用户数据
	public void update() {
		try {
			empService.update(t);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改失败");
		}
	}
	//删除用户数据sout
		public void delete() {
			t=empService.get(t.getUuid());
			System.out.println(t.getUuid());
			try {
				empService.delete(t);
				ajaxReturn(true, "删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				ajaxReturn(false, "删除失败");
			}
		}
	//添加用户
	public void add() {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			empService.add(t);
			rtn.put("success",true);
			rtn.put("message","添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			rtn.put("success",false);
			rtn.put("message","添加失败");
		}
		write(JSON.toJSONString(rtn));
	}
	//修改密码
	public void updatePwd(){
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("loginUser");
		//session鏄惁浼氳秴鏃讹紝鐢ㄦ埛鏄惁鐧婚檰杩囦簡
		if(null == loginUser){
			ajaxReturn(false, "请重新登录");
			return;
		}
		try {
			empService.updatePwd(loginUser.getUuid(), oldPwd, newPwd);
			ajaxReturn(true, "修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改密码失败");
		}
	}
	
	//重置密码
	public void updatePwd_reset(){	
		try {
			System.out.println(newPwd+t.getUuid());
			empService.updatePwd_reset(t.getUuid(), newPwd);
			ajaxReturn(true, "重置成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "重置失败");
		}
	}
	//ajax返回
	public void ajaxReturn(boolean success, String message){
		
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success",success);
		rtn.put("message",message);
		write(JSON.toJSONString(rtn));
	}
	//数据加前缀
	public String mapData(String jsonString, String prefix){
		//原数据转Map
		Map<String, Object> map = JSON.parseObject(jsonString);	
		//构造新Map
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//缁欐瘡key鍊煎姞涓婂墠缂�
		for(String key : map.keySet()){
			if(map.get(key) instanceof Map){
				//key鍊艰繘琛屾嫾鎺�
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
}
