/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  ThresholdAtom.java    
 * Author: yinhl     
 * Date:   2018年9月11日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.krtc.biz.atom.threshold;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.szkingdom.kjdp.commons.utils.StringUtils;
import com.szkingdom.kjdp.core.atom.model.AtomResult;
import com.szkingdom.kjdp.core.business.model.BusinessResult;
import com.szkingdom.kjdp.core.config.atom.AtomClass;
import com.szkingdom.kjdp.core.config.atom.AtomMethod;
import com.szkingdom.kjdp.core.model.DataList;
import com.szkingdom.kjdp.core.model.DataMap;
import com.szkingdom.kjdp.core.model.GenericRequest;
import com.szkingdom.kjdp.core.utils.DaoUtils;

/**
 * @author yinhl
 *
 */
@AtomClass
public class ThresholdAtom {

	@AtomMethod("atom_queryThresholdSetting")
	public AtomResult query(GenericRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("BIZ_ATTR", request.getParam("BIZ_ATTR"));
		params.put("INDICATOR_CLS", request.getParam("INDICATOR_CLS"));
		params.put("INDICATOR_NAME", request.getParam("INDICATOR_NAME"));
		params.put("PAGE_NUM", request.getParam("PAGE_NUM"));
		params.put("PAGE_SIZE", request.getParam("PAGE_SIZE"));

		BusinessResult businessResult = DaoUtils.doBexInvoke("queryThresholdSetting", params);

		AtomResult result = new AtomResult(businessResult);
		return result;
	}

	@AtomMethod("atom_saveThresholdSetting")
	public AtomResult save(GenericRequest request) {

		Map<String, Object> params = new HashMap<String, Object>();
		DataMap dataMap = request.getParams();
		for (Entry<String, Object> e : dataMap.entrySet()) {
			params.put(e.getKey(), e.getValue());
			if(e.getKey().toUpperCase().startsWith("THRESHOLD")) {
				Object value = e.getValue();
				if(value == null || value.toString().trim().length() <= 0) {
					value = 0L;
				}else {
					value = Long.parseLong(value.toString().trim());
				}
				params.put(e.getKey(), value);
			}
		}

		params.put("INDICATOR_NAME", params.get("INDICATOR_ID"));
		if (!StringUtils.isEmpty(params.get("THRES1_OPER").toString()) 
				&& !StringUtils.isEmpty(params.get("THRES1_COLOR").toString())
				&& !StringUtils.isEmpty(params.get("THRES1_NAME").toString())) {
			params.put("THRES1_NO", 1);
		}else {
			params.put("THRES1_NO", 0);
			params.put("THRESHOLD1",  0);
		}
		
		if (!StringUtils.isEmpty(params.get("THRES2_OPER").toString()) 
				&& !StringUtils.isEmpty(params.get("THRES2_COLOR").toString())
				&& !StringUtils.isEmpty(params.get("THRES2_NAME").toString())) {
			params.put("THRES2_NO", 2);
		}else {
			params.put("THRES2_NO", 0);
			params.put("THRESHOLD2",  0);
		}
		
		if (!StringUtils.isEmpty(params.get("THRES3_OPER").toString()) 
				&& !StringUtils.isEmpty(params.get("THRES3_COLOR").toString())
				&& !StringUtils.isEmpty(params.get("THRES3_NAME").toString())) {
			params.put("THRES3_NO", 3);
		}else{
			params.put("THRES3_NO", 0);
			params.put("THRESHOLD3",  0);
		}
		
		if (!StringUtils.isEmpty(params.get("THRES4_OPER").toString()) 
				&& !StringUtils.isEmpty(params.get("THRES4_COLOR").toString())
				&& !StringUtils.isEmpty(params.get("THRES4_NAME").toString())) {
			params.put("THRES4_NO", 4);
		}else {
			params.put("THRES4_NO", 0);
			params.put("THRESHOLD4", 0);
		}

		AtomResult result = new AtomResult();
		BusinessResult businessResult = null;
		BusinessResult queryResult = DaoUtils.doBexInvoke("queryThresholdSetting", params);
		DataList dataList = queryResult.getDataList();
		if (dataList.isEmpty()) {
			businessResult = DaoUtils.doBexInvoke("insertThresholdSetting", params);
		} else {
			businessResult = DaoUtils.doBexInvoke("updateThresholdSetting", params);
		}

		result.setMessage(businessResult);
		return result;
	}
}
