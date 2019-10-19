/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002009
 * Author:   ZhengMingjie
 * Date:     2018-08-01
 * Description: 单账户备兑仓所需标的数量（按新合约单位）
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.data.*;
import com.szkingdom.operation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Factor10002009 extends FactorBase {
	private static final long serialVersionUID = -2476962341526145808L;
	private Logger logger = LoggerFactory.getLogger(Factor10002009.class);
	private long cvtQtyNeed = 0L;
	private String stkCls = "";

	// 因子所需参数对象
	private DataOptAsset dataOptAsset;
	private DataStkMktinfo dataStkMktinfo;
	private DataStkRightPlan dataStkRightPlan;

	public Factor10002009() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		this.dataOptAsset = new DataOptAsset();
		this.dataStkRightPlan = new DataStkRightPlan();
		this.dataStkMktinfo = new DataStkMktinfo();

		// 参数解析
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		String stkbd = data.getString("STKBD");
		String optUndlCode = data.getString("OPT_UNDL_CODE");
		this.stkCls = data.getString("OPT_UNDL_CLS");

		this.dataOptAsset.setTrdacct(data.getString("TRDACCT"));
		this.dataOptAsset.setStkbd(stkbd);
		this.dataOptAsset.setOptUndlCode(optUndlCode);
		this.dataOptAsset.setOptSide(ConstantUtil.OPT_COVERED);

		this.dataStkMktinfo.setStkCode(optUndlCode);
		this.dataStkMktinfo.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(stkbd));

		this.dataStkRightPlan.setStkCode(optUndlCode);
		this.dataStkRightPlan.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(stkbd));

	}

	@Override
	public void handleFactor() throws Exception {
		// 获取除权除息日
		long dateOfXrDr = 0L;
		DataStkRightPlan dataStkRightPlan = OperationStkRightPlan.getStkRightPlanDataWithCache(this.dataStkRightPlan);
		if (dataStkRightPlan != null) {
			dateOfXrDr = dataStkRightPlan.getDividendDate();
		}
		else {
			throw new Exception("查无除权信息数据，不进行备兑监控");
		}

		try {
			// 判断若当日为除权除息日，则使用最新的合约单位来计算，否则采用提前计算方式；
			long dateCurrent = DateUtils.getCurrDate();
			if (dateCurrent > dateOfXrDr && dateOfXrDr != 0) {
				throw new Exception("除权日期已过，无需继续监控该因子");
			}
			else if (dateCurrent == dateOfXrDr && dateOfXrDr != 0) {
				this.cvtQtyNeed = OperationOptAsset.getCvtQtyNeedByOptAsset(this.dataOptAsset);
			}
			else {
				// 提前计算时，新合约单位 ＝ 原合约单位 × 调整系数（R）
				long cvtQtyOld = OperationOptAsset.getCvtQtyNeedByOptAsset(this.dataOptAsset);
				double closingPrice = 0.0;
				double regulationRatio = 0.0;

				closingPrice = OperationStkMktInfo.getClosingPrice(this.dataStkMktinfo);
				regulationRatio = OperationStkRightPlan.getRegulationRatio(dataStkRightPlan, closingPrice, this.stkCls);

				this.cvtQtyNeed = Math.round(cvtQtyOld * regulationRatio) ;
			}
		} catch (Exception e) {
			throw new Exception("获取所需备兑标的证券数量异常", e);
		}
	}

	@Override
	public Object getResult() throws Exception {
		return this.cvtQtyNeed;
	}

}