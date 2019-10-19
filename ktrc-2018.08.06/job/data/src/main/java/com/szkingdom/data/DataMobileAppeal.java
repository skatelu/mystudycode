/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataMobileAppeal
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import java.sql.Timestamp;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class DataMobileAppeal extends DataBase {

	private static final long serialVersionUID = 9115285474088531082L;
	private float appealCode;
	private double userId;
	private String mobile;
	private Character appealStatus;
	private String appealReason;
	private Date appealDate;
	private double oppUserId;
	private String oppMobile;
	private String handleOpinion;
	private Date handleDate;
	private double handler;
	private Timestamp createTime;
	private String updateTime;

	public DataMobileAppeal() {
	}

	public DataMobileAppeal(JSONObject json) {
	}

	public void init() {
		this.appealCode = 0.0F;
		this.userId = 0.0D;
		this.mobile = "";
		this.appealStatus = null;
		this.appealReason = "";
		this.appealDate = null;
		this.oppUserId = 0.0D;
		this.oppMobile = "";
		this.handleOpinion = "";
		this.handleDate = null;
		this.handler = 0.0D;
		this.createTime = null;
		this.updateTime = "";

	}

	public void setAppealCode(float appealCode) {
		this.appealCode = appealCode;
	}

	public float getAppealCode() {
		return this.appealCode;
	}

	public void setUserId(double userId) {
		this.userId = userId;
	}

	public double getUserId() {
		return this.userId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setAppealStatus(Character appealStatus) {
		this.appealStatus = appealStatus;
	}

	public Character getAppealStatus() {
		return this.appealStatus;
	}

	public void setAppealReason(String appealReason) {
		this.appealReason = appealReason;
	}

	public String getAppealReason() {
		return this.appealReason;
	}

	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
	}

	public Date getAppealDate() {
		return this.appealDate;
	}

	public void setOppUserId(double oppUserId) {
		this.oppUserId = oppUserId;
	}

	public double getOppUserId() {
		return this.oppUserId;
	}

	public void setOppMobile(String oppMobile) {
		this.oppMobile = oppMobile;
	}

	public String getOppMobile() {
		return this.oppMobile;
	}

	public void setHandleOpinion(String handleOpinion) {
		this.handleOpinion = handleOpinion;
	}

	public String getHandleOpinion() {
		return this.handleOpinion;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public Date getHandleDate() {
		return this.handleDate;
	}

	public void setHandler(double handler) {
		this.handler = handler;
	}

	public double getHandler() {
		return this.handler;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

}