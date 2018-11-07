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
import com.zw.ssh.erp.pojo.Goods;
import com.zw.ssh.erp.service.GoodsService;

public class GoodsAction {
	@Resource
	private GoodsService goodsService;
	private Goods t;
	private Goods t1;
	private Goods t2;
	private int page;
	private int rows;
	private String oldPwd;
	private String newPwd;
	private int id;
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


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


	public Goods getT() {
		return t;
	}


	public void setT(Goods t) {
		this.t = t;
	}


	public Goods getT1() {
		return t1;
	}


	public void setT1(Goods t1) {
		this.t1 = t1;
	}


	public Goods getT2() {
		return t2;
	}


	public void setT2(Goods t2) {
		this.t2 = t2;
	}

	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
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
		List<Goods> le = goodsService.findAll(t1,t2,firstResult, rows);
		//关闭循环引用SerializerFeature.DisableCircularReferenceDetect
		String listString = JSON.toJSONString(le, SerializerFeature.DisableCircularReferenceDetect);
		write(listString);
	}
	//显示要修改的用户
	public void get() {
		t=goodsService.get(t.getUuid());
		//强转日期
		String jsonString = JSON.toJSONStringWithDateFormat(t,"yyyy-MM-dd");
		//添加前缀
		String jsonStringAfter = mapData(jsonString, "t");
		write(jsonStringAfter);
	}
	//更新用户数据
	public void update() {
		try {
			goodsService.update(t);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改失败");
		}
	}
	//添加用户
	public void add() {
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			goodsService.add(t);
			rtn.put("success",true);
			rtn.put("message","添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			rtn.put("success",false);
			rtn.put("message","添加失败");
		}
		write(JSON.toJSONString(rtn));
	}
	//ajax返回
	public void ajaxReturn(boolean success, String message){
		//杩斿洖鍓嶇鐨凧SON鏁版嵁
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
		for(String key : map.keySet()){
			if(map.get(key) instanceof Map){
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
