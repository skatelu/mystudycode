/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: ConstantUtil
 * Author:   ZhangMaohua
 * Date:     2018/7/24
 * Description: 常量封装
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.common;

public class ConstantUtil {
    /**
     * 期权持仓限额参数表
     */
    public static final String TABLE_OPT_CUST_POSI_LMT = "OPT_CUST_POSI_LMT";
    public static final String TABLE_OPT_POSI_LMT = "OPT_POSI_LMT";
    public static final String TABLE_OPT_CLS_POSI_LMT = "OPT_CLS_POSI_LMT";
    public static final String TABLE_OPT_TOTAL_POSI_LMT = "OPT_TOTAL_POSI_LMT";
    public static final String TABLE_OPT_ASSET = "OPT_ASSET";

    /**
     * 流水表
     */
    public static final String TABLE_OPT_ORDER = "OPT_ORDER";
    public static final String TABLE_OPT_MATCHING = "OPT_MATCHING";
    public static final String TABLE_CUACCT_LOG = "CUACCT_LOG";
    public static final String TABLE_STK_ASSET_LOG = "STK_ASSET_LOG";

    public static final String TABLE_STK_ORDER = "STK_ORDER";
    public static final String TABLE_STK_MATCHING = "STK_MATCHING";

    /**
     * 风险表
     */
    public static final String TABLE_RISK_INFO_ACCT = "RISK_INFO_ACCT";
    public static final String TABLE_RISK_INFO_FUND = "RISK_INFO_FUND";
    public static final String TABLE_RISK_INFO_ASSET = "RISK_INFO_ASSET";
    public static final String TABLE_RISK_INFO_EXE_FUND = "RISK_INFO_EXE_FUND";
    public static final String TABLE_RISK_INFO_EXE_STK = "RISK_INFO_EXE_STK";
    public static final String TABLE_RISK_INFO_CORP_FUND = "RISK_INFO_CORP_FUND";
    public static final String TABLE_RISK_INFO_BOND_PLEDGE = "RISK_INFO_BOND_PLEDGE";

    public static final String TABLE_CUST_INDICATOR_THRES = "CUST_INDICATOR_THRES";
    public static final String TABLE_INDICATOR_THRESHOLD = "INDICATOR_THRESHOLD";
    public static final String TABLE_INDICATOR_IDS_CFG = "INDICATOR_IDS_CFG";
    
    /**
     * 除权除息表
     */
    public static final String TABLE_STK_RIGHT_PLAN = "STK_RIGHT_PLAN";

    /**
     * 股票期权基础信息
     */
    public static final String TABLE_OPT_INFO = "OPT_INFO";

    /**
     * cache常量，去重使用
     */
    public static final String CACHE_VALUES = "1";

    /**
     * 期权数据来源类型
     */
    public static final String SPOT_QUOTATION = "15";
    public static final String OPT_QUOTATION = "16";
    public static final String OPT_TRADE = "10";
    public static final String OPT_MATCH = "11";
    public static final String OPT_FUND = "12";
    public static final String OPT_ASSET = "13";
    public static final String OPT_MARGIN_CORP = "14";

    /**
     * 集中交易数据来源类型
     */
    public static final String STK_TRADE = "40";
    public static final String STK_MATCH = "41";
    public static final String STK_FUND = "42";
    public static final String STK_ASSET = "43";

    /**
     * 约定购回数据来源类型
     */
    public static final String AGR_BUYBK_CORP_QUOTA = "AGR_BUYBK_CORP_QUOTA";
    public static final String CUST_AUDIT_QUOTA = "CUST_AUDIT_QUOTA";
    public static final String AGR_BUYBK_UNDL_INFO = "AGR_BUYBK_UNDL_INFO";
    public static final String AGR_BUYBK_VART_INFO = "AGR_BUYBK_VART_INFO";
    public static final String AGR_BUYBK_CONTRACTS = "AGR_BUYBK_CONTRACTS";

    /**
     * 因子来源
     */
    public static final String  FACTOR_SOURCES_OPT_FUND_BIZ = "100";
    public static final String  FACTOR_SOURCES_OPT_FUND_BIZ_ACTION = "100";
    public static final String  FACTOR_SOURCES_OPT_ASSET_BIZ = "101";
    public static final String  FACTOR_SOURCES_OPT_ASSET_BIZ_ACTION = "101";
    // 参数类占位 "04"
    public static final String  FACTOR_SOURCES_OPT_QUOTATION = "102";
    public static final String  FACTOR_SOURCES_SPOT_QUOTATION = "103";
    public static final String  FACTOR_SOURCES_OPT_MARGIN_CORP_BIZ = "424";
    public static final String  FACTOR_SOURCES_OPT_MARGIN_CORP_BIZ_ACTION = "100";

    /**
     * 保证金策略
     */
    public static final Character MARGIN_STRATEGY_NONE = '@';
    public static final Character MARGIN_STRATEGY_HOLLIDAY = 'H';
    public static final Character MARGIN_STRATEGY_EXERCISE = 'E';
    public static final Character MARGIN_STRATEGY_MONEYNESS = 'M';
    public static final Character[] MarginStrategy = new Character[]{MARGIN_STRATEGY_MONEYNESS,
                                                                    MARGIN_STRATEGY_EXERCISE,
                                                                    MARGIN_STRATEGY_HOLLIDAY,
                                                                    MARGIN_STRATEGY_NONE};

    /**
     * 证券业务STK_BIZ
     * 400 买入开仓
     * 401 卖出平仓
     * 402 卖出开仓
     * 403 买入平仓
     * 404 备兑开仓
     * 405 备兑平仓
     * 406 认购行权
     * 407 认沽行权
     */
    public static final short STK_BIZ_BUY_OPEN_POSITION = 400;
    public static final short STK_BIZ_SELL_CLOSE_POSITION = 401;
    public static final short STK_BIZ_SELL_OPEN_POSITION = 402;
    public static final short STK_BIZ_BUY_CLOSE_POSITION = 403;
    public static final short STK_BIZ_COVERED_CALL_POSITION = 404;
    public static final short STK_BIZ_COVERED_CLOSE_POSITION = 405;
    public static final short STK_BIZ_EXE_CALL_OPTION = 406;
    public static final short STK_BIZ_EXE_PUT_OPTION  = 407;
    /**
     * 110	质押入库
     * 111	质押出库
     * 164	债券质押回购融资（正回购）
     * 165	债券质押回购融券（逆回购）
     */
    public static final short STK_BIZ_BOND_PLEDGE_IN = 110;
    public static final short STK_BIZ_BOND_PLEDGE_OUT = 111;
    public static final short STK_BIZ_BOND_PLEDGE_FINANCING = 164;
    public static final short STK_BIZ_BOND_PLEDGE_LENDING = 165;

    /**
     * STK_BIZ_ACTION
     * 100  委托申报
     * 101	撤单申报
     * 102	发送标志回转
     * 103	合法标志回转
     * 104	委托成交回转
     * 105	撤单成交回转
     * 106	可交易数量计算
     * 121	市价最优五档即时成交剩余撤销
     * 122	市价全额成交或撤销
     * 123	市价本方最优价格
     * 124	市价对手方最优价格
     * 125	市价即时成交剩余撤销
     * 130	股票期权申报-限价GFD
     * 131	股票期权申报-限价FOK
     * 132	股票期权申报-市价剩转限价GFD
     * 133	股票期权申报-市价FOK
     * 134	股票期权申报-市价IOC
     */
    public static final short STK_BIZ_ACTION_LIMIT_OFFER      = 100;
    public static final short STK_BIZ_ACTION_CANCEL_OFFER     = 101;
    public static final short STK_BIZ_ACTION_SENDFLAG_RETURN  = 102;
    public static final short STK_BIZ_ACTION_VALID_RETURN     = 103;
    public static final short STK_BIZ_ACTION_ORDER_MATCHING   = 104;
    public static final short STK_BIZ_ACTION_CANCEL_MATCHING  = 105;
    public static final short STK_BIZ_ACTION_TRADE_QTY_CALC   = 106;

    public static final short STK_BIZ_ACTION_UMARKET_OFFER    = 120;
    public static final short STK_BIZ_ACTION_VMARKET_OFFER    = 121;
    public static final short STK_BIZ_ACTION_WMARKET_OFFER    = 122;
    public static final short STK_BIZ_ACTION_XMARKET_OFFER    = 123;
    public static final short STK_BIZ_ACTION_YMARKET_OFFER    = 124;
    public static final short STK_BIZ_ACTION_ZMARKET_OFFER    = 125;

    public static final short STK_BIZ_ACTION_LGFD_OFFER       = 130;
    public static final short STK_BIZ_ACTION_LFOK_OFFER       = 131;
    public static final short STK_BIZ_ACTION_MGFD_OFFER       = 132;
    public static final short STK_BIZ_ACTION_MFOK_OFFER       = 133;
    public static final short STK_BIZ_ACTION_MIOC_OFFER       = 134;

    /**
     * 期权持仓方向 ：L-权利方，S-义务方，C-备兑策略持仓
     */
    public static Character OPT_LONG = 'L';
    public static Character OPT_SHORT = 'S';
    public static Character OPT_COVERED = 'C';

    /**
     * 合约类型：C-认购 P-认沽
     */
    public static Character OPT_CALL = 'C';
    public static Character OPT_PUT = 'P';

    /**
     * 证券类型：U-期权 A-股票 D-基金
     */
    public static Character STK_CLS_OPT = 'U';
    public static Character STK_CLS_STOCK = 'A';
    public static Character STK_CLS_ETF = 'D';

    /**
     * 用户类型：0-个人 1-机构 2-设备
     */
    public static Character OPT_CUST_TYPE_PERSON = '0';
    public static Character OPT_CUST_TYPE_ORG = '1';
    public static Character OPT_CUST_TYPE_EQUIP = '2';

    /**
     * 客户类型：0-普通 1-自营 2-资管 3-QFII
     */
    public static Character CUST_TYPE_NORMAL = '0';
    public static Character CUST_TYPE_SELF_BUSI = '1';
    public static Character CUST_TYPE_MKTMAKER = '2';
    public static Character CUST_TYPE_QFII = '3';

    /**
     * 板块类型：00-深圳A股 01-深圳B股 02-三板(A) 03-三板B 05-深圳股票期权
     *           10-上海A股 11-上海B股 15-上海股票期权
     */
    public static String STKBD_SZAG  = "00";
    public static String STKBD_SZBG  = "01";
    public static String STKBD_SZQQ  = "05";
    public static String STKBD_SHAG = "10";
    public static String STKBD_SHBG = "11";
    public static String STKBD_SHQQ = "15";

    /**
     * A	单标的单账户个人总持仓限额
     * B	单标的单账户个人权利仓持仓限额
     * C	单标的单账户一般机构总持仓限额
     * D	单标的单账户一般机构权利仓持仓限额
     * E	单标的单账户自营总持仓限额
     * F	单标的单账户自营权利仓持仓限额
     * G	单标的单账户做市商总持仓限额
     * H	单标的单账户做市商权利仓持仓限额
     * I	单标的会员经纪总持仓限额
     * J	单标的会员自营总持仓限额
     * K	单标的单账户个人当日累计买入开仓限额
     * L	单标的单账户一般机构当日累计买入开仓限额
     * M	单标的单账户自营当日累计买入开仓限额
     * N	单标的单账户做市商当日累计买入开仓限额
     * O	单标的单账户个人当日累计开仓限额
     * P	单标的单账户一般机构当日累计开仓限额
     * Q	单标的单账户自营当日累计开仓限额
     * R	单标的单账户做市商当日累计开仓限额
     */
    public static Character PLA_SINGLE_PERSON = 'A';
    public static Character PLA_SINGLE_PERSON_LONG = 'B';
    public static Character PLA_SINGLE_INST = 'C';
    public static Character PLA_SINGLE_INST_LONG = 'D';
    public static Character PLA_SINGLE_SECU = 'E';
    public static Character PLA_SINGLE_SECU_LONG = 'F';
    public static Character PLA_SINGLE_MKTMAKER = 'G';
    public static Character PLA_SINGLE_MKTMAKER_LONG = 'H';
    public static Character PLA_MEMBER_BROKER = 'I';
    public static Character PLA_MEMBER_SECU = 'J';
    public static Character PLA_SINGLE_PERSON_DAILY_BUY = 'K';
    public static Character PLA_SINGLE_INST_DAILY_BUY = 'L';
    public static Character PLA_SINGLE_SECU_DAILY_BUY = 'M';
    public static Character PLA_SINGLE_MKTMAKER_DAILY_BUY = 'N';
    public static Character PLA_SINGLE_PERSON_DAILY = 'O';
    public static Character PLA_SINGLE_INST_DAILY = 'P';
    public static Character PLA_SINGLE_SECU_DAILY = 'Q';
    public static Character PLA_SINGLE_MKTMAKER_DAILY = 'R';

    /**
     * CONFIG FILE PATH
     */
    public static String CONFIG = "/config/config.properties";
    public static String DRUID_CONFIG = "/config/druid.properties";

    /**
     * KAFKA TOPIC
     * 期权topic 部署时提交脚本创建，名称固定
     */
    public static final String TOPIC_OPTION_TRADE = "TOPIC_OPTION_TRADE";
    public static final String TOPIC_OPTION_QUOTATION = "TOPIC_OPTION_QUOTATION";
    public static final String TOPIC_SPOT_QUOTATION = "TOPIC_SPOT_QUOTATION";

    public static final String[] TOPIC_OPT = new String[]{TOPIC_OPTION_TRADE,
                                                          TOPIC_OPTION_QUOTATION,
                                                          TOPIC_SPOT_QUOTATION};

    /**
     * JSON解析使用的key值
     */
    public static final String JSON_KTRC = "KTRC";
    public static final String JSON_INDICATORINFO = "INDICATORINFO";
    public static final String JSON_CUACCT_FUND = "CUACCT_FUND";
    public static final String JSON_OPT_ORDER = "OPT_ORDER";

    /**
     * BOLT声明字段
     */
    public static final String MAINTAIN_REALTIME_BOLT_DECLARE_FIELDS = "INDICATORS";
    public static final String CALC_RISK_INDICATORS_BOLT_DECLARE_FIELDS = "INDICATOR_RESULT";

    /**
     * 期权收集对冲或非对冲,挂单或非挂单数据标记
     */
    public static final boolean BOOLEAN_HEDGED = true;
    public static final boolean BOOLEAN_NOT_HEDGED = false;
    public static final boolean BOOLEAN_ENTRY_ORDERS = true;
    public static final boolean BOOLEAN_NOT_ENTRY_ORDERS = false;

    /**
     * 风险结果写表所需备兑不足业务指标与因子判断
     */
    public static final String INDICATOR_CVT = "60005001";
    public static final String FACTOR_CVT_QTY_NEED = "10002009";
    public static final String FACTOR_CVT_QTY_AVL = "10002010";

    /**
     * 风险结果写表所需持仓限额业务指标与因子判断
     */
    public static final String INDICATOR_POSI_LONG = "60003001";
    public static final String FACTOR_POSI_QTY_LONG = "10002002";
    public static final String FACTOR_POSI_LQTY_LONG = "10000001";
    public static final String INDICATOR_POSI_TOTAL = "60003002";
    public static final String FACTOR_POSI_QTY_TOTAL = "10002004";
    public static final String FACTOR_POSI_LQTY_TOTAL = "10000002";
    public static final String INDICATOR_POSI_DAILY = "60003003";
    public static final String FACTOR_POSI_QTY_DAILY = "10002006";
    public static final String FACTOR_POSI_LQTY_DAILY = "10000003";
    public static final String INDICATOR_MEMBER_POSI_TOTAL = "60003004";
    public static final String FACTOR_MEMBER_POSI_QTY_TOTAL = "10002008";
    public static final String FACTOR_MEMBER_POSI_LQTY_TOTAL = "10000004";
    public static final String INDICATOR_QUOTA = "60002001";
    public static final String FACTOR_QUOTA_VAL_USED = "10003019";
    public static final String FACTOR_QUOTA_VAL = "10000005";

    public static final String INDICATOR_EXE_STK_RISK1 = "60007001";
    public static final String FACTOR_EXE_NEED_STK = "10002011";
    public static final String FACTOR_EXE_STK_BLN_CVD = "10002012";
    public static final String INDICATOR_EXE_STK_RISK2 = "60007002";
    public static final String FACTOR_EXE_STK_BLN = "10002013";
    public static final String INDICATOR_EXE_FUND_RISK = "60007003";
    public static final String FACTOR_CUST_FUND_AVL_OPT = "10003008";
    public static final String FACTOR_CUST_FUND_EXE_FRZ = "10003021";
    public static final String FACTOR_CUST_SETT_AMT = "10003022";

    /**
     * 保证金业务因子编号
     */
    public static final String FACTOR_MARGIN_USED_RLT = "10003009";

    /**
     * 债券质押指标号、因子号
     */
    public static final String INDICATOR_STD_BOND_USED_RATE = "60501001";
    public static final String INDICATOR_BOND_AMT_RATE = "60501002";
    public static final String INDICATOR_CUST_BOND_AMT_RATE = "60501003";
    public static final String INDICATOR_BOND_OWE_AMT = "60502001";
    public static final String INDICATOR_ACCT_OWE_AMT = "60503001";
    public static final String FACTOR_BOND_MATCH_AMT = "10501001";
    public static final String FACTOR_CUST_REPCH_AMT = "10501004";
    public static final String FACTOR_CUST_BUYBK_REPCH_AMT = "10501005";
    public static final String FACTOR_STD_BOND_AMT = "10502001";
    public static final String FACTOR_CUST_BOND_AMT = "10002002";
    public static final String FACTOR_CUST_STK_AVL = "10502003";
    public static final String FACTOR_CUST_FUND_AVL_STK = "10502004";
    public static final String FACTOR_BOND_TOTAL_TRUST = "10503001";


    /**
     * 备兑不足类型：0-预测备兑不足 1-备兑不足 2-无备兑不足
     */
    public static Character CVD_SHORT_TYPE_PRE_LACK = '0';
    public static Character CVD_SHORT_TYPE_LACK = '1';
    public static Character CVD_SHORT_TYPE_NORMAL = '2';

    /**
     * 风险有效标志：0-正常 1-风险
     */
    public static Character RISK_VALID_FLAG_NORMAL = '0';
    public static Character RISK_VALID_FLAG_RISK = '1';
	
    /**
     * 字典类型
     * 非备兑 NOT_CVD
     */
    public static Character NOT_CVD = '0';
    public static Character CVD = '1';
    public static Character FRZ_FLAG = '0';
    public static Character UFZ_FLAG = '1';
    public static Character RED_FLAG = '2';
    public static Character BLUE_FLAG = '3';
    public static Character NOT_WITHDRAW = 'F';
    public static Character WITHDRAW = 'T';
}
