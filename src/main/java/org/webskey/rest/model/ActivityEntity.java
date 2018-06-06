package org.webskey.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "activityentity")
public class ActivityEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String des;
	
	@Column
	private int num;
	
	public ActivityEntity() {}
	public ActivityEntity(String name, String desc, int num) {
		this.name = name;
		this.des = desc;
		this.num = num;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return des;
	}
	public void setDesc(String desc) {
		this.des = desc;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}	
}