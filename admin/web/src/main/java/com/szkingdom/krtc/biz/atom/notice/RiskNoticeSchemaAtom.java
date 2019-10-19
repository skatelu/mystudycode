/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  FrameAtom.java    
 * Author: yinhl     
 * Date:   2018年9月4日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.krtc.biz.atom.notice;

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
 * @author yinhl
 *
 */
@AtomClass
public class RiskNoticeSchemaAtom {
	
	@AtomMethod("atom_QueryRiskNoticeSchema")
	public AtomResult query(GenericRequest request) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		BusinessResult businessResult = DaoUtils.doBexInvoke("QueryRiskNoticeSchema", params);
		AtomResult result = new AtomResult();
		DataList dataList  = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		
		result.setDataList(atomDataList);
		return result;
	}

}
