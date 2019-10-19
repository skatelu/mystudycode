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

import com.szkingdom.kjdp.commons.utils.StringUtils;
import com.szkingdom.kjdp.core.atom.model.AtomResult;
import com.szkingdom.kjdp.core.business.model.BusinessResult;
import com.szkingdom.kjdp.core.config.atom.AtomClass;
import com.szkingdom.kjdp.core.config.atom.AtomMethod;
import com.szkingdom.kjdp.core.model.DataList;
import com.szkingdom.kjdp.core.model.GenericRequest;
import com.szkingdom.kjdp.core.utils.DaoUtils;

/**
 * 盯市监控详细屏
 * 
 * @author yinhl
 *
 */
@AtomClass
public class RiskMarkDetailScreen {

	private static final String INDICATOR_ID = "INDICATOR_ID";
	private static final String RISK_VALID_FLAG = "RISK_VALID_FLAG";

	@AtomMethod("atom_RiskMarkDetailScreen")
	public AtomResult query(GenericRequest request) {

		AtomResult result = new AtomResult();
		String indicatorId = request.getParam(INDICATOR_ID);
		
		if(indicatorId == null || request.getParam("PAGE_NUM") == null) {
			return result;
		}

		Map<String, String> map = new HashMap<>();
		String bex = getBexAndDatagrid(indicatorId, map);

		// 没有取到相关的指标对应道的风险信息
		if(StringUtils.isEmpty(bex)) {
			return result;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(INDICATOR_ID, indicatorId);
		params.put(RISK_VALID_FLAG, request.getParam(RISK_VALID_FLAG));
		params.put("PAGE_NUM", request.getParam("PAGE_NUM"));
		params.put("PAGE_SIZE", request.getParam("PAGE_SIZE"));
		BusinessResult businessResult = DaoUtils.doBexInvoke(bex, params);

		result = new AtomResult(businessResult);
		
		return result;
	}

	private String getBexAndDatagrid(String indicatorId, Map<String, String> map) {
		String bex = null;
		switch (indicatorId) {
		// 账户类风险信息表
		case "60001001":
			bex = "RiskMarkDetailScreen_RISK_INFO_ACCT";
			break;
		// 资金类风险信息表
		 case "60004001":
         case "60004002":
         case "60004003":
			bex = "RiskMarkDetailScreen_RISK_INFO_FUND";
			break;
		// 持仓类风险信息表
         case "60002001":
         case "60003001":
         case "60003002":
         case "60003003":
         case "60003004":
         case "60005001":
         case "60501001":
         case "60501002":
         case "60501003":
         case "60502001":
         case "60503001":
			bex = "RiskMarkDetailScreen_RISK_INFO_ASSET";
			break;
		// 行权资金类风险信息表
         case "60007003":
			bex = "RiskMarkDetailScreen_RISK_INFO_EXE_FUND";
			break;
		// 行权证券类风险信息表
         case "60007001":
         case "60007002":
			bex = "RiskMarkDetailScreen_RISK_INFO_EXE_STK";
			break;
		// 经济业务类资金风险信息表
         case "60006001":
			bex = "RiskMarkDetailScreen_RISK_INFO_CORP_FUND";
			break;
		// 其他
		default:
			// riskTableName = "";
			break;
		}

		return bex;
	}
}
