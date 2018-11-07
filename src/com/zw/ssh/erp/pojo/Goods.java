package com.zw.ssh.erp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 商品实体�?
 * @author Administrator *
 */
@Entity
public class Goods {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long uuid;
	private String name;
	private String origin;
	private String producer;
	private String unit;
	private Double inprice;
	private Double outprice;
	@ManyToOne
	private Goodstype goodstype;

	public Goodstype getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(Goodstype goodstype) {
		this.goodstype = goodstype;
	}
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
	public String getOrigin() {		
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getProducer() {		
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getUnit() {		
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getInprice() {		
		return inprice;
	}
	public void setInprice(Double inprice) {
		this.inprice = inprice;
	}
	public Double getOutprice() {		
		return outprice;
	}
	public void setOutprice(Double outprice) {
		this.outprice = outprice;
	}

}
