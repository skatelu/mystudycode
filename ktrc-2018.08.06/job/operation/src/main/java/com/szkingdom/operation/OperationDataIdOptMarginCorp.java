/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdOptMarginCorp
 * Author:   ZhengMingjie
 * Date:     2018/8/27
 * Description: 风控接口公司保证金查询OPT_MARGIN_CORP的数据类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

public class OperationDataIdOptMarginCorp extends OperationBase {

    private String dataId;
    private String stkbd;
    private String marginCorpAcct;
    private Character currency;
    private long marginUsed;
    private long marginAvl;
    private long marginTotalAmt;

    public OperationDataIdOptMarginCorp() {
        init();
    }

    public OperationDataIdOptMarginCorp(JSONObject json) {
    }

    private void init(){
        this.dataId = "";
        this.stkbd = "";
        this.marginCorpAcct = "";
        this.currency = null;
        this.marginUsed = 0L;
        this.marginAvl = 0L;
        this.marginTotalAmt = 0L;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getStkbd() {
        return stkbd;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public String getMarginCorpAcct() {
        return marginCorpAcct;
    }

    public void setMarginCorpAcct(String marginCorpAcct) {
        this.marginCorpAcct = marginCorpAcct;
    }

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public long getMarginUsed() {
        return marginUsed;
    }

    public void setMarginUsed(long marginUsed) {
        this.marginUsed = marginUsed;
    }

    public long getMarginAvl() {
        return marginAvl;
    }

    public void setMarginAvl(long marginAvl) {
        this.marginAvl = marginAvl;
    }

    public long getMarginTotalAmt() {
        return marginTotalAmt;
    }

    public void setMarginTotalAmt(long marginTotalAmt) {
        this.marginTotalAmt = marginTotalAmt;
    }
}
