package com.zw.ssh.erp.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.MenuDao;
import com.zw.ssh.erp.pojo.Menu;
@Component
public class MenuService {
	private MenuDao menuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}
	@Resource
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	@Transactional
	public Menu get(String mid) {
		return menuDao.get(mid);
	}
}
