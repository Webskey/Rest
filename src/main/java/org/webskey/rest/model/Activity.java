package org.webskey.rest.model;

public class Activity {

	private String name;
	private String desc;
	private int num;
	
	public Activity() {}
	public Activity(String name, String desc, int num) {
		this.name = name;
		this.desc = desc;
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}	
}