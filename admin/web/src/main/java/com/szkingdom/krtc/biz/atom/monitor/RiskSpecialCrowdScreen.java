package com.szkingdom.krtc.biz.atom.monitor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

@AtomClass
public class RiskSpecialCrowdScreen {
	
	private static final String OCC_DATE = "OCC_DATE";
	@AtomMethod("atom_RiskSpecialCrowdScreen")
	public AtomResult query(GenericRequest request) {
	    int currentTime =Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));  
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OCC_DATE,currentTime);
		BusinessResult businessResult = DaoUtils.doBexInvoke("RiskSpecialCrowdScreen_view", params);

		AtomResult result = new AtomResult();
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			//当客户联系电话未填写时，取移动电话
			if((dataList.get(i).getMap().get("TEL")==null)||(dataList.get(i).getMap().get("TEL").equals(""))) {
				if((dataList.get(i).getMap().get("MOBILE_TEL")!=null)&&(dataList.get(i).getMap().get("MOBILE_TEL").toString().length()!=0)) {
					dataList.get(i).getMap().put("TEL", dataList.get(i).getMap().get("MOBILE_TEL"));
				}
			}
			atomDataList.add(dataList.get(i).getMap());
		}
		result.setDataList(atomDataList);
		return result;
	}
	
	@AtomMethod("atom_RiskSpecialCrowdScreen_barChart")
	public AtomResult barChart(GenericRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("CUST_CODE", request.getParam("CUST_CODE"));
		AtomResult result = new AtomResult();
		BusinessResult businessResult = DaoUtils.doBexInvoke("RiskSpecialCrowdScreen_barChart",params);
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		result.setDataList(atomDataList);
		return result;
	}
	
	@AtomMethod("atom_RiskSpecialCrowdScreen_history")
	public AtomResult history(GenericRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("CUST_CODE", request.getParam("CUST_CODE"));
		AtomResult result = new AtomResult();
		BusinessResult businessResult = DaoUtils.doBexInvoke("RiskSpecialCrowdScreen_history",params);
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		result.setDataList(atomDataList);
		return result;
	}
	
}
