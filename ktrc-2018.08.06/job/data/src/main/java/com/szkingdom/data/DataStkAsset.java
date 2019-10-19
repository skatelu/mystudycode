/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkAsset
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkAsset extends DataBase {

	private static final long serialVersionUID = -1857665724534560469L;
	private long custCode;
	private long cuacctCode;
	private short intOrg;
	private Character stkex;
	private String stkbd;
	private String stkpbu;
	private String trdacct;
	private Character currency;
	private String stkCode;
	private long stkBln;
	private long stkAvl;
	private long stkFrz;
	private long stkUfz;
	private long stkNtrd;
	private long stkTrdFrz;
	private long stkTrdUfz;
	private long stkTrdOtd;
	private long stkOtdAvl;
	private long stkTrdBln;
	private short subsysSn;

    public DataStkAsset(){
        init();
    }

	public DataStkAsset(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.cuacctCode = 0L;
		this.intOrg = 0;
		this.stkex = null;
		this.stkbd = "";
		this.stkpbu = "";
		this.trdacct = "";
		this.currency = null;
		this.stkCode = "";
		this.stkBln = 0L;
		this.stkAvl = 0L;
		this.stkFrz = 0L;
		this.stkUfz = 0L;
		this.stkNtrd = 0L;
		this.stkTrdFrz = 0L;
		this.stkTrdUfz = 0L;
		this.stkTrdOtd = 0L;
		this.stkOtdAvl = 0L;
		this.stkTrdBln = 0L;
		this.subsysSn = 0;

	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setStkex(Character stkex) {
		this.stkex = stkex;
	}

	public Character getStkex() {
		return this.stkex;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setStkBln(long stkBln) {
		this.stkBln = stkBln;
	}

	public long getStkBln() {
		return this.stkBln;
	}

	public void setStkAvl(long stkAvl) {
		this.stkAvl = stkAvl;
	}

	public long getStkAvl() {
		return this.stkAvl;
	}

	public void setStkFrz(long stkFrz) {
		this.stkFrz = stkFrz;
	}

	public long getStkFrz() {
		return this.stkFrz;
	}

	public void setStkUfz(long stkUfz) {
		this.stkUfz = stkUfz;
	}

	public long getStkUfz() {
		return this.stkUfz;
	}

	public void setStkNtrd(long stkNtrd) {
		this.stkNtrd = stkNtrd;
	}

	public long getStkNtrd() {
		return this.stkNtrd;
	}

	public void setStkTrdFrz(long stkTrdFrz) {
		this.stkTrdFrz = stkTrdFrz;
	}

	public long getStkTrdFrz() {
		return this.stkTrdFrz;
	}

	public void setStkTrdUfz(long stkTrdUfz) {
		this.stkTrdUfz = stkTrdUfz;
	}

	public long getStkTrdUfz() {
		return this.stkTrdUfz;
	}

	public void setStkTrdOtd(long stkTrdOtd) {
		this.stkTrdOtd = stkTrdOtd;
	}

	public long getStkTrdOtd() {
		return this.stkTrdOtd;
	}

	public void setStkOtdAvl(long stkOtdAvl) {
		this.stkOtdAvl = stkOtdAvl;
	}

	public long getStkOtdAvl() {
		return this.stkOtdAvl;
	}

	public void setStkTrdBln(long stkTrdBln) {
		this.stkTrdBln = stkTrdBln;
	}

	public long getStkTrdBln() {
		return this.stkTrdBln;
	}

	public void setSubsysSn(short subsysSn) {
		this.subsysSn = subsysSn;
	}

	public short getSubsysSn() {
		return this.subsysSn;
	}

}