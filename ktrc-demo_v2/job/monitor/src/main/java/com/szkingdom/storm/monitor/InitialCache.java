package com.szkingdom.storm.monitor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.Tools;
import com.szkingdom.dao.DaoOptCustPosiLmt;
import com.szkingdom.dao.DaoOptPosiLmt;
import com.szkingdom.dao.DaoOptClsPosiLmt;
import com.szkingdom.dao.DaoOptTotalPosiLmt;
import com.szkingdom.data.DataOptClsPosiLmt;
import com.szkingdom.data.DataOptCustPosiLmt;
import com.szkingdom.data.DataOptPosiLmt;
import com.szkingdom.data.DataOptTotalPosiLmt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangch
 * @date 2018/7/4 15:03
 */
public class InitialCache {

    protected static Logger logger = LoggerFactory.getLogger(InitialCache.class);

    private static Map<String, Serializable> mapOptLmt;

    public static void initOptCache() throws Exception {
        mapOptLmt = new ConcurrentHashMap<String, Serializable>();

        // LOAD OPT_CUST_POSI_LMT
        try {
            loadOptCustPosiLmt();
        }catch (Exception e){
            throw e;
        }

        // LOAD OPT_POSI_LMT
        try {
            loadOptPosiLmt();
        }catch (Exception e){
            throw e;
        }

        // LOAD OPT_CLS_POSI_LMT
        try {
            loadOptClsPosiLmt();
        }catch (Exception e){
            throw e;
        }

        // LOAD OPT_TOTAL_POSI_LMT
        try {
            loadOptTotalPosiLmt();
        }catch (Exception e){
            throw e;
        }
    }

    private static void loadOptCustPosiLmt() throws Exception {
        StringBuffer key = new StringBuffer();

        DaoOptCustPosiLmt daoOptCustPosiLmt = new DaoOptCustPosiLmt();
        try {
            List<DataOptCustPosiLmt> listOptCustPosiLmt = daoOptCustPosiLmt.selectOptCustPosiLmtList();
            for (DataOptCustPosiLmt dataOptCustPosiLmt : listOptCustPosiLmt) {
                key.setLength(0);

                key.append(dataOptCustPosiLmt.getCustCode());
                key.append(dataOptCustPosiLmt.getTrdacct());
                key.append(dataOptCustPosiLmt.getStkbd());
                key.append(dataOptCustPosiLmt.getOptUndlCls());
                key.append(dataOptCustPosiLmt.getOptUndlCode());
                key.append(dataOptCustPosiLmt.getLsFlag());
                key.append(dataOptCustPosiLmt.getCustLmtAttr());

                mapOptLmt.put(ConstantUtil.TABLE_OPT_CUST_POSI_LMT + "_" + key.toString(), dataOptCustPosiLmt);
            }
        } catch (Exception e) {
            logger.error("Exception TABLE_OPT_CUST_POSI_LMT ERROR [ " + e + "]\n");
            throw e;
        }
    }

    private static void loadOptPosiLmt() throws Exception {
        StringBuffer key = new StringBuffer();

        DaoOptPosiLmt daoOptPosiLmt = new DaoOptPosiLmt();
        try {
            List<DataOptPosiLmt> listOptPosiLmt = daoOptPosiLmt.selectOptPosiLmtList();
            for (DataOptPosiLmt dataOptPosiLmt : listOptPosiLmt) {
                key.setLength(0);

                key.append(dataOptPosiLmt.getStkbd());
                key.append(dataOptPosiLmt.getOptUndlCode());
                key.append(dataOptPosiLmt.getLsFlag());
                key.append(dataOptPosiLmt.getPosiLmtAttr());

                mapOptLmt.put(ConstantUtil.TABLE_OPT_POSI_LMT + "_" + key.toString(), dataOptPosiLmt);
            }
        } catch (Exception e) {
            logger.error("Exception TABLE_OPT_POSI_LMT ERROR [ " + e + "]\n");
            throw e;
        }
    }

    private static void loadOptClsPosiLmt() throws Exception {
        StringBuffer key = new StringBuffer();

        DaoOptClsPosiLmt daoOptClsPosiLmt = new DaoOptClsPosiLmt();
        try {
            List<DataOptClsPosiLmt> listOptClsPosiLmt = daoOptClsPosiLmt.selectOptClsPosiLmtList();
            for (DataOptClsPosiLmt dataOptClsPosiLmt : listOptClsPosiLmt) {
                key.setLength(0);

                key.append(dataOptClsPosiLmt.getStkbd());
                key.append(dataOptClsPosiLmt.getOptUndlCls());
                key.append(dataOptClsPosiLmt.getLsFlag());
                key.append(dataOptClsPosiLmt.getPosiLmtAttr());

                mapOptLmt.put(ConstantUtil.TABLE_OPT_CLS_POSI_LMT + "_" + key, dataOptClsPosiLmt);
            }
        } catch (Exception e) {
            logger.error("Exception TABLE_OPT_CLS_POSI_LMT ERROR [ " + e + "]\n");
            throw e;
        }
    }

    private static void loadOptTotalPosiLmt() throws Exception {
        StringBuffer key = new StringBuffer();

        DaoOptTotalPosiLmt daoOptTotalPosiLmt = new DaoOptTotalPosiLmt();
        try {
            List<DataOptTotalPosiLmt> listOptTotalPosiLmt = daoOptTotalPosiLmt.selectOptTotalPosiLmtList();
            for (DataOptTotalPosiLmt dataOptTotalPosiLmt : listOptTotalPosiLmt) {
                key.setLength(0);

                key.append(dataOptTotalPosiLmt.getStkbd());
                key.append(dataOptTotalPosiLmt.getTotalLmtAttr());

                mapOptLmt.put(ConstantUtil.TABLE_OPT_TOTAL_POSI_LMT + "_" + key.toString(), dataOptTotalPosiLmt);
            }
        } catch (Exception e) {
            logger.error("Exception TABLE_OPT_TOTAL_POSI_LMT ERROR [ " + e + "]\n");
            throw e;
        }
    }

    public static Object getMapOptPosiLmtDataByKey(String key) {

        if (mapOptLmt != null  && !mapOptLmt.isEmpty() && !"".equals(key)){
            Serializable serializable = mapOptLmt.get(key);

            if (serializable != null){
                return Tools.deepClone(serializable);
            }

        }else {
            logger.error("InitialCache::getMapOptPosiLmtDataByKey mapOptLmt["
                         +  mapOptLmt + "] key [" + key + "] is null ");
        }

        return null;
    }

    public static boolean add2MapOptPosiLmt(String key, Serializable serializable) {

        if (!"".equals(key) && serializable != null && mapOptLmt != null){
            mapOptLmt.put(key, serializable);
            return true;
        }else {
            logger.error("InitialCache::getMapOptPosiLmtDataByKey mapOptLmt["
                    +  mapOptLmt + "] key [" + key + "] serializable[" + serializable + "] is null ");
        }

        return false;
    }

    public static boolean delMapOptPosiLmtDataByKey(String key) {

        if (!"".equals(key) && mapOptLmt != null  && !mapOptLmt.isEmpty()) {
            mapOptLmt.remove(key);
            return true;
        }else {
            logger.error("InitialCache::getMapOptPosiLmtDataByKey mapOptLmt["
                                +  mapOptLmt + "] key [" + key + "] is null ");
        }

        return false;
    }

    public static boolean updateMapOptPosiLmtDataByKey(String key, Serializable serializable) {

        if (!"".equals(key) && serializable != null && mapOptLmt != null  && !mapOptLmt.isEmpty()){
            mapOptLmt.put(key, serializable);
            return true;
        }else {
            logger.error("InitialCache::getMapOptPosiLmtDataByKey mapOptLmt["
                    +  mapOptLmt + "] key [" + key + "] serializable[" + serializable + "] is null ");
        }

        return false;
    }
}
