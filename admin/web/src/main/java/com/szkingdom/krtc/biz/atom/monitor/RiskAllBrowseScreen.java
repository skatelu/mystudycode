/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  RiskAllBrowseScreen.java    
 * Author: yinhl     
 * Date:   2018年9月15日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.krtc.biz.atom.monitor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class RiskAllBrowseScreen {
	
	private static DateFormat df = new SimpleDateFormat("yyyyMMdd");
	
	@AtomMethod("atom_RiskAllBrowseScreen_gaugeChart")
	public AtomResult gaugeChart(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MARGIN_CORP_ACCT", request.getParam("MARGIN_CORP_ACCT"));
		params.put("CURRENCY", request.getParam("CURRENCY"));
		params.put("OCC_DATE", Integer.parseInt(df.format(Calendar.getInstance().getTime())));
		BusinessResult businessResult = DaoUtils.doBexInvoke("RiskAllBrowseScreen_gaugeChart", params);
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		
		if(atomDataList.isEmpty()) {
			Map m = new HashMap();
			m.put("OPEN_AMT_USEED_RATE", 0);
			m.put("MARGIN_TOTAL_AMT", 0);
			m.put("MARGIN_USED", 0);
			m.put("MARGIN_AVL", 0);
			m.put("MARGIN_AVL_MIN", 0);
			atomDataList.add(m);
		}
		
		result.setDataList(atomDataList);
		return result;
	}

	@AtomMethod("atom_RiskAllBrowseScreen_pieChart")
	public AtomResult pieChart(GenericRequest request) {
		AtomResult result = new AtomResult();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("INDICATOR_ID", request.getParam("INDICATOR_ID"));
		BusinessResult businessResult = DaoUtils.doBexInvoke("RiskAllBrowseScreen_pieChart", params);
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		
		if(atomDataList.isEmpty()) {
			Map m = new HashMap();
			m.put("RISK_HIGHT_COUNT", 0);
			m.put("RISK_LOW_COUNT", 0);
			m.put("TOTAL_COUNT", 0);
			m.put("RISK_WARN_COUNT", 0);
		}
		
		Map m = new HashMap();
		m.put("RISK_HIGHT_COUNT_NAME", "强平客户数");
		m.put("RISK_LOW_COUNT_NAME", "正常客户数");
		m.put("TOTAL_COUNT_NAME", "所有告警总数");
		m.put("RISK_WARN_COUNT_NAME", "警戒客户数");
		atomDataList.add(m);
		
		result.setDataList(atomDataList);
		return result;
	}

	@AtomMethod("atom_RiskAllBrowseScreen_lineChart")
	public AtomResult lineChart(GenericRequest request) {
		AtomResult result = new AtomResult();
		
		List<Map> atomDataList = new ArrayList<Map>();
		Map<String, ArrayList> map = new HashMap<>();
		atomDataList.add(map);
		result.setDataList(atomDataList);

		ArrayList<String> categoryMapList = new ArrayList<>();
		ArrayList<List> valuesMapList = new ArrayList<List>();
		map.put("category", categoryMapList);
		map.put("values", valuesMapList);
		
		categoryMapList.add("9:30");
		categoryMapList.add("10:30");
		categoryMapList.add("11:30");
		categoryMapList.add("12:30");
		categoryMapList.add("13:30");
		categoryMapList.add("14:30");
		categoryMapList.add("15:00");
		
		List value = new ArrayList<>();
		value.add(70);
		value.add(35);
		value.add(38);
		value.add(45);
		value.add(45);
		value.add(45);
		value.add(45);
		value.add(45);
		valuesMapList.add(value);
		value = new ArrayList<>();
		value.add(20);
		value.add(45);
		value.add(38);
		value.add(55);
		value.add(45);
		value.add(45);
		value.add(90);
		value.add(45);
		value.add(45);
		valuesMapList.add(value);
		
		return result;
	}

	@AtomMethod("atom_RiskAllBrowseScreen_barChart")
	public AtomResult barChart(GenericRequest request) {
		AtomResult result = new AtomResult();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("OPT_UNDL_CODE", request.getParam("OPT_UNDL_CODE"));
		params.put("OPT_UNDL_CLS", request.getParam("OPT_UNDL_CLS"));
		BusinessResult businessResult = DaoUtils.doBexInvoke("RiskAllBrowseScreen_barChart", params);
		DataList dataList = businessResult.getDataList();
		List<Map> atomDataList = new ArrayList<Map>();
		for (int i = 0; i < dataList.size(); i++) {
			atomDataList.add(dataList.get(i).getMap());
		}
		
		if(dataList.isEmpty()) {
			Map m = new HashMap();
			m.put("ORG_NAME", "高新南路");
			m.put("TOTAL_POSI", 3000);
			atomDataList.add(m);
			m = new HashMap();
			m.put("ORG_NAME", "泰然九路");
			m.put("TOTAL_POSI", 2500);
			atomDataList.add(m);
			m = new HashMap();
			m.put("ORG_NAME", "深南大道");
			m.put("TOTAL_POSI", 1500);
			atomDataList.add(m);
			m = new HashMap();
			m.put("ORG_NAME", "科苑北");
			m.put("TOTAL_POSI", 1000);
			atomDataList.add(m);
			m = new HashMap();
			m.put("ORG_NAME", "学府路");
			m.put("TOTAL_POSI", 100);
			atomDataList.add(m);
			m = new HashMap();
			m.put("ORG_NAME", "其他");
			m.put("TOTAL_POSI", 5000);
			atomDataList.add(m);
		};
		
		result.setDataList(atomDataList);
		return result;
	}

	@AtomMethod("atom_RiskAllBrowseScreen_tableChart")
	public AtomResult tableChart(GenericRequest request) {
		AtomResult result = new AtomResult();
		
		List<Map> atomDataList = new ArrayList<Map>();
		Map<String, String> map = new HashMap<>();
		atomDataList.add(map);
		result.setDataList(atomDataList);
		
		map.put("BUSINESS_RATE", "50%");
		map.put("ETF50_STANDBY_POSI", "0");
		map.put("CUST_WITHDRAW_AMT", "1,000,000");
		map.put("ETF50_LONG_POSI", "17,800");
		map.put("EXERCISE_SECURITY_RISK_RATE", "50%");
		map.put("EXCEPTION_ACC_POSI", "23");
		map.put("ETF50_SELL_POSI", "50,000");
		map.put("RECH_WITH_GAP_POSI", "-1,710,100");
		map.put("CUST_RECHARGE_AMT", "3,892,900");
		map.put("ETF50_BUG_POSI", "17,800");
		map.put("EXERCISE_PAYER_RISK_RATE", "30%");
		map.put("FORCE_OPEN_ACC_POSI", "0");
		
		return result;
	}
}
