package com.ideamov.core.bean.demo;

import java.util.Date;

/**
 * Demo entity. @author MyEclipse Persistence Tools
 */

public class Demo implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Integer age;
	private String memoText;
	private String imgUrl;
	private Integer demoType;
	private Integer demoState;
	private Double balanceMoney;
	private Date birthdayDate;
	private Date createTime;

	// Constructors

	/** default constructor */
	public Demo() {
	}

	/** full constructor */
	public Demo(String name, Integer age, String memoText, String imgUrl, Integer demoType, Integer demoState,
			Double balanceMoney, Date birthdayDate, Date createTime) {
		this.name = name;
		this.age = age;
		this.memoText = memoText;
		this.imgUrl = imgUrl;
		this.demoType = demoType;
		this.demoState = demoState;
		this.balanceMoney = balanceMoney;
		this.birthdayDate = birthdayDate;
		this.createTime = createTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMemoText() {
		return this.memoText;
	}

	public void setMemoText(String memoText) {
		this.memoText = memoText;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getDemoType() {
		return this.demoType;
	}

	public void setDemoType(Integer demoType) {
		this.demoType = demoType;
	}

	public Integer getDemoState() {
		return this.demoState;
	}

	public void setDemoState(Integer demoState) {
		this.demoState = demoState;
	}

	public Double getBalanceMoney() {
		return this.balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public Date getBirthdayDate() {
		return this.birthdayDate;
	}

	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}