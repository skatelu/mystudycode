/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataMarginTemplate
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataMarginTemplate extends DataBase {

	private static final long serialVersionUID = -3883266535789753514L;
	private Character strategyTemplate;
	private Character templateCfgLvl;
	private Character stkex;
	private String stkbd;
	private Character optUndlCls;
	private String optUndlCode;
	private String optNum;
	private Character optType;
	private Character marginStrategy;
	private String combStraCode;
	private int scopeNo;
	private int paramNo;

    public DataMarginTemplate(){
        init();
    }

	public DataMarginTemplate(JSONObject json) {
	}

	public void init() {
		this.strategyTemplate = null;
		this.templateCfgLvl = null;
		this.stkex = null;
		this.stkbd = "";
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optNum = "";
		this.optType = null;
		this.marginStrategy = null;
		this.combStraCode = "";
		this.scopeNo = 0;
		this.paramNo = 0;
	}

    public void setStrategyTemplate(Character strategyTemplate){
        this.strategyTemplate = strategyTemplate;
    }

    public Character  getStrategyTemplate(){
        return this.strategyTemplate;
    }

    public void setTemplateCfgLvl(Character templateCfgLvl){
        this.templateCfgLvl = templateCfgLvl;
    }

    public Character  getTemplateCfgLvl(){
        return this.templateCfgLvl;
    }

    public void setStkex(Character stkex){
        this.stkex = stkex;
    }

    public Character  getStkex(){
        return this.stkex;
    }

    public void setStkbd(String stkbd){
        this.stkbd = stkbd;
    }

    public String  getStkbd(){
        return this.stkbd;
    }

    public void setOptUndlCls(Character optUndlCls){
        this.optUndlCls = optUndlCls;
    }

    public Character  getOptUndlCls(){
        return this.optUndlCls;
    }

    public void setOptUndlCode(String optUndlCode){
        this.optUndlCode = optUndlCode;
    }

    public String  getOptUndlCode(){
        return this.optUndlCode;
    }

    public void setOptNum(String optNum){
        this.optNum = optNum;
    }

    public String  getOptNum(){
        return this.optNum;
    }

    public void setOptType(Character optType){
        this.optType = optType;
    }

    public Character  getOptType(){
        return this.optType;
    }

    public void setMarginStrategy(Character marginStrategy){
        this.marginStrategy = marginStrategy;
    }

    public Character  getMarginStrategy(){
        return this.marginStrategy;
    }

    public void setCombStraCode(String combStraCode){
        this.combStraCode = combStraCode;
    }

    public String  getCombStraCode(){
        return this.combStraCode;
    }

    public void setScopeNo(int scopeNo){
        this.scopeNo = scopeNo;
    }

    public int  getScopeNo(){
        return this.scopeNo;
    }

    public void setParamNo(int paramNo){
        this.paramNo = paramNo;
    }

    public int  getParamNo(){
        return this.paramNo;
    }

}