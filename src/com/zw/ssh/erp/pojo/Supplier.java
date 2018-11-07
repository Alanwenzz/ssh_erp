package com.zw.ssh.erp.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Supplier {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long uuid;
	private String name;
	private String address;
	private String contact;
	private String tele;
	private String email;
	private String type;

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
	public String getAddress() {		
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {		
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTele() {		
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getEmail() {		
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {		
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}

