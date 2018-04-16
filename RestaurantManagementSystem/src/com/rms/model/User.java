package com.rms.model;

public class User {

	private String id;
	private String username;
	private String password;
	private Integer age;
	private String contract_num;
	private String role;
	private String updatetm;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getContract_num() {
		return contract_num;
	}
	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUpdatetm() {
		return updatetm;
	}
	public void setUpdatetm(String updatetm) {
		this.updatetm = updatetm;
	}
	
}
