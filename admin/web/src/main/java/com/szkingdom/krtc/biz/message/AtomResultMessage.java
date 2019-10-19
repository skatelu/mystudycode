/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  ThresholdAtomMessage.java    
 * Author: yinhl     
 * Date:   2018年9月13日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.krtc.biz.message;

import com.szkingdom.kjdp.core.atom.model.AtomMessage;

/**
 * @author yinhl
 *
 */
public class AtomResultMessage implements AtomMessage {

	private String msgCode;
	private String msgText;
	private Object data;
	
	@Override
	public String getMsgCode() {
		return this.msgCode;
	}

	@Override
	public String getMsgText() {
		// TODO Auto-generated method stub
		return this.msgText;
	}

	@Override
	public String getMsgText(Object... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
