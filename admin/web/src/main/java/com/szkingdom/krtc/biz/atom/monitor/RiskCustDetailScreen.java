/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  RiskMarkDetailScreen.java    
 * Author: yinhl     
 * Date:   2018年9月7日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.krtc.biz.atom.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.szkingdom.kjdp.core.atom.model.AtomResult;
import com.szkingdom.kjdp.core.business.model.BusinessResult;
import com.szkingdom.kjdp.core.config.atom.AtomClass;
import com.szkingdom.kjdp.core.config.atom.AtomMethod;
import com.szkingdom.kjdp.core.model.DataList;
import com.szkingdom.kjdp.core.model.GenericRequest;
import com.szkingdom.kjdp.core.utils.DaoUtils;

/**
 * 客户详细屏
 * 
 * @author yinhl
 *
 */
@AtomClass
public class RiskCustDetailScreen {
	
	@AtomMethod("atom_RiskCustDetailScreen_custInfo")
	public AtomResult custInfo(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		BusinessResult businessResult = DaoUtils.doBexInvoke("risk-custdetailscreen-custInfo", params);
		
		return result;
	}

	@AtomMethod("atom_RiskCustDetailScreen_baseInfo")
	public AtomResult baseInfo(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("CUST_CODE", request.getParam("CUST_CODE"));
		BusinessResult businessResult = DaoUtils.doBexInvoke("risk-custdetailscreen-baseInfo", params);
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		
		result.setDataList(atomDataList);
		return result;
	}

	@AtomMethod("atom_RiskCustDetailScreen_riskIndicatorDetail")
	public AtomResult riskIndicatorDetail(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		BusinessResult businessResult = DaoUtils.doBexInvoke("risk-custdetailscreen-riskIndicatorDetail", params);
		
		return result;
	}

	@AtomMethod("atom_RiskCustDetailScreen_historyRiskPic")
	public AtomResult historyRiskPic(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		BusinessResult businessResult = DaoUtils.doBexInvoke("risk-custdetailscreen-historyRiskPic", params);
		
		
		return result;
	}

	@AtomMethod("atom_RiskCustDetailScreen_historySiskTrigger")
	public AtomResult historySiskTrigger(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		BusinessResult businessResult = DaoUtils.doBexInvoke("risk-custdetailscreen-historySiskTrigger", params);
		
		return result;
	}

}
