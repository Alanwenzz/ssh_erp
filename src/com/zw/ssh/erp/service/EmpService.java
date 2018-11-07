package com.zw.ssh.erp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

import com.zw.ssh.erp.dao.EmpDao;
import com.zw.ssh.erp.pojo.Emp;

@Component
public class EmpService {
	public int hashIterations=2;
	private EmpDao empDao;
	@Resource
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}
	@Transactional
	public List<Emp> findAll(Emp e1, Emp e2, int firstResult, int rows){
		return empDao.findAll(e1,e2,firstResult,rows);
	}
	@Transactional
	public void add(Emp e) {
		// TODO Auto-generated method stub
		String pwd="123456";
		//source:ԭ����
		//salt:������
		//hash...:ɢ�д���		
		String newPwd=encrypt(pwd,e.getName());
		e.setPassword(newPwd);
		empDao.add(e);
	}
	@Transactional
	public Emp get(long l) {
		// TODO Auto-generated method stub
		return empDao.get(l);
	}
	@Transactional
	public void update(Emp e) {
		// TODO Auto-generated method stub
		empDao.update(e);
	}
	@Transactional
	public void delete(Emp e) {
		// TODO Auto-generated method stub
		empDao.delete(e);
	}
	@Transactional
	public void updatePwd(long l, String oldPwd, String newPwd) throws Exception {
		//ȡ��Ա����Ϣ
		Emp emp = empDao.get(l);
		//���ܾ�����
		String encrypted = encrypt(oldPwd, emp.getName());
		//�������Ƿ���ȷ��ƥ��
		if(!encrypted.equals(emp.getPassword())){
			//�׳� �Զ����쳣
			throw new Exception();
		}		
		empDao.updatePwd(l, encrypt(newPwd,emp.getName()));
	}
	@Transactional
	public void updatePwd_reset(long l,String newPwd) {
		//ȡ��Ա����Ϣ
		Emp emp = empDao.get(l);
		empDao.updatePwd(l, encrypt(newPwd,emp.getName()));
	}
	@Transactional
	public Emp findByUsernameAndPwd(String name, String pwd) {
		// TODO Auto-generated method stub
		pwd=encrypt(pwd, name);
		System.out.println(pwd);
		return empDao.findByUsernameAndPwd(name,pwd);
	}
	//�������
	private String encrypt(String source,String salt) {
		Md5Hash md5=new Md5Hash(source,salt,hashIterations);
		return md5.toString();
	}
}
