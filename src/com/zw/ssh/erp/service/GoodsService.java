package com.zw.ssh.erp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.GoodsDao;
import com.zw.ssh.erp.pojo.Goods;

@Component
public class GoodsService {
	private GoodsDao goodsDao;
	@Resource
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	@Transactional
	public List<Goods> findAll(Goods e1, Goods e2, int firstResult, int rows){
		return goodsDao.findAll(e1,e2,firstResult,rows);
	}
	@Transactional
	public void add(Goods e) {
		goodsDao.add(e);
	}
	@Transactional
	public Goods get(Long long1) {
		// TODO Auto-generated method stub
		return goodsDao.get(long1);
	}
	@Transactional
	public void update(Goods e) {
		// TODO Auto-generated method stub
		goodsDao.update(e);
	}
}
