/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  DataBase.java    
 * Author: yinhl     
 * Date:   2018年7月23日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.data;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.szkingdom.common.Tools;

/**
 * @author yinhl
 *
 */
public class DataBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	@Override
	public DataBase clone() {
		return (DataBase) Tools.deepClone(this);
	}
}
