package com.zw.ssh.erp.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 鑿滃崟瀹炰綋绫�
 * @author Administrator *
 */
@Entity
public class Menu {	
	@Id
	private String menuid;//菜单ID
	private String menuname;//菜单名称
	private String icon;//菜单图标
	private String url;//URL
	private String pid;//上一级编号
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	// cascade 缓存       fetch = FetchType.EAGER 解决懒加载问题           mappedBy="pid" 指明关联列
	@OneToMany(targetEntity = Menu.class, cascade = { CascadeType.ALL },fetch = FetchType.EAGER,mappedBy="pid")  
	private List<Menu> menus;

	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public String getMenuid() {		
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {		
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getIcon() {		
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {		
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}