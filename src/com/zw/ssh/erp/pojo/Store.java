package com.zw.ssh.erp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * �ֿ�ʵ����
 * @author Administrator *
 */
@Entity
public class Store {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long uuid;//���
	private String name;//����
	private Long empuuid;//Ա�����

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
	public Long getEmpuuid() {		
		return empuuid;
	}
	public void setEmpuuid(Long empuuid) {
		this.empuuid = empuuid;
	}

}

