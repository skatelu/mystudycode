/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStrategyScope
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStrategyScope extends DataBase {

	private static final long serialVersionUID = 7986007404597948467L;
	private Character marginStrategy;
	private int scopeNo;
	private int edayScopeNo;
	private Character scopeType;
	private long scopeBgn;
	private Character bgnInclude;
	private long scopeEnd;
	private Character endInclude;

	public DataStrategyScope() {
        init();
	}

	public DataStrategyScope(JSONObject json) {
	}

	public void init() {
		this.marginStrategy = null;
		this.scopeNo = 0;
		this.edayScopeNo = 0;
		this.scopeType = null;
		this.scopeBgn = 0L;
		this.bgnInclude = null;
		this.scopeEnd = 0L;
		this.endInclude = null;

	}

	public void setMarginStrategy(Character marginStrategy) {
		this.marginStrategy = marginStrategy;
	}

	public Character getMarginStrategy() {
		return this.marginStrategy;
	}

	public void setScopeNo(int scopeNo) {
		this.scopeNo = scopeNo;
	}

	public int getScopeNo() {
		return this.scopeNo;
	}

	public void setEdayScopeNo(int edayScopeNo) {
		this.edayScopeNo = edayScopeNo;
	}

	public int getEdayScopeNo() {
		return this.edayScopeNo;
	}

	public void setScopeType(Character scopeType) {
		this.scopeType = scopeType;
	}

	public Character getScopeType() {
		return this.scopeType;
	}

	public void setScopeBgn(long scopeBgn) {
		this.scopeBgn = scopeBgn;
	}

	public long getScopeBgn() {
		return this.scopeBgn;
	}

	public void setBgnInclude(Character bgnInclude) {
		this.bgnInclude = bgnInclude;
	}

	public Character getBgnInclude() {
		return this.bgnInclude;
	}

	public void setScopeEnd(long scopeEnd) {
		this.scopeEnd = scopeEnd;
	}

	public long getScopeEnd() {
		return this.scopeEnd;
	}

	public void setEndInclude(Character endInclude) {
		this.endInclude = endInclude;
	}

	public Character getEndInclude() {
		return this.endInclude;
	}

}