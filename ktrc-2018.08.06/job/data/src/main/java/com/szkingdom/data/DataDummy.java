/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataDummy
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataDummy extends DataBase {

	private static final long serialVersionUID = 3015016980242052608L;
	private Character dummy;

	public DataDummy() {
	}

	public DataDummy(JSONObject json) {
	}

	public void init() {
		this.dummy = null;

	}

	public void setDummy(Character dummy) {
		this.dummy = dummy;
	}

	public Character getDummy() {
		return this.dummy;
	}

}