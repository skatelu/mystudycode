package com.szkingdom.factor;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.Tools;

/**
 * 所有的因子的超类，约束所有因子的实现方法
 * 
 * @author yinhl
 *
 */
public abstract class FactorBase implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String factorId;
	protected String factorName;
	protected String bizAttr;
	protected String factorCls;
	protected String factorSource;
	protected String factorClass;
	protected String factorRemark;

	public FactorBase() {

	}

	public String getFactorId() {
		return factorId;
	}

	public void setFactorId(String factorId) {
		this.factorId = factorId;
	}

	public String getFactorName() {
		return factorName;
	}

	public void setFactorName(String factorName) {
		this.factorName = factorName;
	}

	public String getBizAttr() {
		return bizAttr;
	}

	public void setBizAttr(String bizAttr) {
		this.bizAttr = bizAttr;
	}

	public String getFactorCls() {
		return factorCls;
	}

	public void setFactorCls(String factorCls) {
		this.factorCls = factorCls;
	}

	public String getFactorSource() {
		return factorSource;
	}

	public void setFactorSource(String factorSource) {
		this.factorSource = factorSource;
	}

	public String getFactorClass() {
		return factorClass;
	}

	public void setFactorClass(String factorClass) {
		this.factorClass = factorClass;
	}

	public String getFactorRemark() {
		return factorRemark;
	}

	public void setFactorRemark(String factorRemark) {
		this.factorRemark = factorRemark;
	}

	/**
	 * 解析Json标准字符串
	 * 
	 * @param json
	 */
	public abstract void resolveParameter(JSONObject json) throws Exception;

	/**
	 * 处理因子
	 */
	public abstract void handleFactor() throws Exception;

	/**
	 * 获取因子处理结果，及指标结果
	 */
	public abstract Object getResult() throws Exception;

	@Override
	public String toString() {
		return "FactorBase [factorId=" + factorId + ", factorName=" + factorName + "]";
	}

	/**
	 * 深度克隆一个新的自己
	 */
	public FactorBase clone() throws CloneNotSupportedException {
		return (FactorBase) Tools.deepClone(this);
	}

}
