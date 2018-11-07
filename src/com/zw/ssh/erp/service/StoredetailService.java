package com.zw.ssh.erp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.GoodsDao;
import com.zw.ssh.erp.dao.StoreDao;
import com.zw.ssh.erp.dao.StoredetailDao;
import com.zw.ssh.erp.pojo.Storedetail;
@Component
public class StoredetailService {
	@Resource
	private StoredetailDao StoredetailDao;
	
	public StoredetailDao getStoredetailDao() {
		return StoredetailDao;
	}

	public void setStoredetailDao(StoredetailDao storedetailDao) {
		StoredetailDao = storedetailDao;
	}

	@Resource
	private StoreDao storeDao;
	@Resource
	private GoodsDao goodsDao;
	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public GoodsDao getGoodsDao() {
		return goodsDao;
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	/**
	 * ∑÷“≥≤È—Ø
	 */
	public List<Storedetail> getListByPage(Storedetail t1,int firstResult, int maxResults){
		List<Storedetail> list = StoredetailDao.getList(t1, firstResult, maxResults);
		Map<Long, String> goodsNameMap = new HashMap<Long, String>();
		Map<Long, String> storeNameMap = new HashMap<Long, String>();
		for(Storedetail sd : list){
			sd.setGoodsName(getGoodsName(sd.getGoodsuuid(),goodsNameMap));
			sd.setStoreName(getStoreName(sd.getStoreuuid(),storeNameMap));
		}
		return list;
	}
	
	private String getGoodsName(Long uuid, Map<Long, String> goodsNameMap){
		if(null == uuid){
			return null;
		}
		String goodsName = goodsNameMap.get(uuid);
		if(null == goodsName){
			goodsName = goodsDao.get(uuid).getName();
			goodsNameMap.put(uuid, goodsName);
		}
		return goodsName;
	}
	
	private String getStoreName(Long uuid, Map<Long, String> storeNameMap){
		if(null == uuid){
			return null;
		}
		String storeName = storeNameMap.get(uuid);
		if(null == storeName){
			storeName = storeDao.get(uuid).getName();
			storeNameMap.put(uuid, storeName);
		}
		return storeName;
	}

	public long getCount(Storedetail t1) {
		// TODO Auto-generated method stub
		return StoredetailDao.getCount(t1);
	}

}
