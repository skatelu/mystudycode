/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskNoticeSchema
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskNoticeSchema extends DataBase {

	private static final long serialVersionUID = 5374468279585045935L;
	private String indicatorId;
	private Character noticeUserRole;
	private Character preThresNo;
	private Character thresNo;
	private String schemaName;
	private String noticeChnl;
	private Character schemaValide;
	private String schemaCont;
	private Character noticeCls;
	private Character schemaBack;
	private String qmName;
	private String queueName;

	public DataRiskNoticeSchema() {
	}

	public DataRiskNoticeSchema(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.noticeUserRole = null;
		this.preThresNo = null;
		this.thresNo = null;
		this.schemaName = "";
		this.noticeChnl = "";
		this.schemaValide = null;
		this.schemaCont = "";
		this.noticeCls = null;
		this.schemaBack = null;
		this.qmName = "";
		this.queueName = "";

	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorId() {
		return this.indicatorId;
	}

	public void setNoticeUserRole(Character noticeUserRole) {
		this.noticeUserRole = noticeUserRole;
	}

	public Character getNoticeUserRole() {
		return this.noticeUserRole;
	}

	public void setPreThresNo(Character preThresNo) {
		this.preThresNo = preThresNo;
	}

	public Character getPreThresNo() {
		return this.preThresNo;
	}

	public void setThresNo(Character thresNo) {
		this.thresNo = thresNo;
	}

	public Character getThresNo() {
		return this.thresNo;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getSchemaName() {
		return this.schemaName;
	}

	public void setNoticeChnl(String noticeChnl) {
		this.noticeChnl = noticeChnl;
	}

	public String getNoticeChnl() {
		return this.noticeChnl;
	}

	public void setSchemaValide(Character schemaValide) {
		this.schemaValide = schemaValide;
	}

	public Character getSchemaValide() {
		return this.schemaValide;
	}

	public void setSchemaCont(String schemaCont) {
		this.schemaCont = schemaCont;
	}

	public String getSchemaCont() {
		return this.schemaCont;
	}

	public void setNoticeCls(Character noticeCls) {
		this.noticeCls = noticeCls;
	}

	public Character getNoticeCls() {
		return this.noticeCls;
	}

	public void setSchemaBack(Character schemaBack) {
		this.schemaBack = schemaBack;
	}

	public Character getSchemaBack() {
		return this.schemaBack;
	}

	public void setQmName(String qmName) {
		this.qmName = qmName;
	}

	public String getQmName() {
		return this.qmName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueueName() {
		return this.queueName;
	}

}