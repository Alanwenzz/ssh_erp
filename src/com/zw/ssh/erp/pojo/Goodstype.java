package com.zw.ssh.erp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 商品分类实体�?
 * @author Administrator *
 */
@Entity
public class Goodstype {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long uuid;
	private String name;

	public Long getUuid() {		
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
