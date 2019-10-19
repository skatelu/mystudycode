package com.szkingdom.common;

/**
 * @author zhangch
 * @date 2018/7/13 11:03
 */
public class ConstantUtil {
    /**
     * TABLE NAME
     */
    public static final String TABLE_OPT_CUST_POSI_LMT = "OPT_CUST_POSI_LMT";

    public static final String TABLE_OPT_POSI_LMT = "OPT_POSI_LMT";

    public static final String TABLE_OPT_CLS_POSI_LMT = "OPT_CLS_POSI_LMT";

    public static final String TABLE_OPT_TOTAL_POSI_LMT = "OPT_TOTAL_POSI_LMT";

    public static final String TABLE_RISK_INFO_ASSET = "RISK_INFO_ASSET";

    public static final String TABLE_OPT_ASSET = "OPT_ASSET";

    /**
     * STK_BIZ
     */
    public enum StkBiz {
        /**
         * 400 股票期权-买入开仓
         * 401 股票期权-卖出平仓
         * 402 股票期权-卖出开仓
         * 403 股票期权-买入平仓
         * 404 股票期权-备兑开仓
         * 405 股票期权-备兑平仓
         */
        STK_BIZ_BUY_OPEN_POSITION(400),
        STK_BIZ_SELL_CLOSE_POSITION(401),
        STK_BIZ_SELL_OPEN_POSITION(402),
        STK_BIZ_BUY_CLOSE_POSITION(403),
        STK_BIZ_COVERED_CALL_POSITION(404),
        STK_BIZ_COVERED_CLOSE_POSITION(405);

        private final int value;

        //构造方法必须是private或者默认
        private StkBiz(int value) {
            this.value = value;
        }

        public StkBiz valueOf(int value) {
            switch (value) {
                case 400:
                    return StkBiz.STK_BIZ_BUY_OPEN_POSITION;
                case 401:
                    return StkBiz.STK_BIZ_SELL_CLOSE_POSITION;
                case 402:
                    return StkBiz.STK_BIZ_SELL_OPEN_POSITION;
                case 403:
                    return StkBiz.STK_BIZ_BUY_CLOSE_POSITION;
                case 404:
                    return StkBiz.STK_BIZ_COVERED_CALL_POSITION;
                case 405:
                    return StkBiz.STK_BIZ_COVERED_CLOSE_POSITION;
                default:
                    return null;
            }
        }
    }

    /**
     * CONFIG FILE PATH
     */
    public static String CONFIG = "/usr/local/apache-storm-1.1.0/config/config.properties";

    /**
     * KAFKA TOPIC
     * 期权topic 部署时提交脚本创建，名称固定
     */
    public static final String TOPIC_OPTION_TRADE = "TOPIC_OPTION_TRADE";

    public static final String TOPIC_OPTION_QUOTATION = "TOPIC_OPTION_QUOTATION";

    public static final String[] TOPIC_OPT = new String[]{TOPIC_OPTION_TRADE,TOPIC_OPTION_QUOTATION};

    /**ktrc数据库类型*/
    public static final String KTRC_DB_TYPE = "szkingdom.ktrc.dbtype";

    /**SQL etf 期权查询所有人*/
    public static final String KEY_SZKINGDOM_KTRC_EFT_PERSON = "sql.eft.person";
}
