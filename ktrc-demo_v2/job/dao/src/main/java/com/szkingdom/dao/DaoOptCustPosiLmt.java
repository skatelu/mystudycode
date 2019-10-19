package com.szkingdom.dao;

import java.util.List;
import java.util.Map;
import com.szkingdom.data.DataOptCustPosiLmt;

/**
 * @author zhangch
 * @date 2018/7/7 17:19
 */
public class DaoOptCustPosiLmt extends DaoBase {

//    public Map<Object, Object> selectOptCustPosiLmt() throws Exception {
//        return selectMap("COMMON_OPT_CUST_POST_LMT_SELECT", null);
//    }

    public List<DataOptCustPosiLmt> selectOptCustPosiLmtList() throws Exception {
        return (List<DataOptCustPosiLmt>)selectList("COMMON_OPT_CUST_POSI_LMT_SELECT", null);
    }

    public List<DataOptCustPosiLmt> selectOptCustPosiLmtList(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
        return (List<DataOptCustPosiLmt>)selectList("COMMON_OPT_CUST_POSI_LMT_SELECT", dataOptCustPosiLmt);
    }

//    public List<?> findOneCustomer(int cust_code) throws Exception {
//        Customer cus = new Customer();
//        cus.setCustCode(cust_code);
//        return selectList("common_customer_byid", cus);
//    }
//
//    public List<?> findCustomerList(List<Integer> custList) throws Exception {
//        Customer cus = new Customer();
//        // cus.setCustCode(cust_code);
//        cus.setCustList(custList);
//        return selectList("common_customer_byid", cus);
//
//        //return findList("common_customer_bylist", custList);
//    }
//
//
//
//    public int insertList(List<Customer> custList) throws Exception {
//        SqlSession session = null;
//        try {
//            session = connect(false);
//            int t = session.insert("common_customer_insert_batch", custList);
//            session.commit();
//            return t;
//        } catch (Exception e) {
//            if (session != null) {
//                session.rollback();
//            }
//            throw e;
//        } finally {
//            close(session);
//        }
//    }
//
//    public int delete(Customer cus) throws Exception {
//        SqlSession session = null;
//        try {
//            session = connect(false);
//            int t = session.delete("common_customer_delete", cus);
//            session.commit();
//            return t;
//        } catch (Exception e) {
//            if (session != null) {
//                session.rollback();
//            }
//            throw e;
//        } finally {
//            close(session);
//        }
//    }
//
//    public int deleteList(List<Customer> custList) throws Exception {
//        SqlSession session = null;
//        try {
//            session = connect(false);
//            int t = session.delete("common_customer_delete_batch", custList);
//            session.commit();
//            return t;
//        } catch (Exception e) {
//            if (session != null) {
//                session.rollback();
//            }
//            throw e;
//        } finally {
//            close(session);
//        }
//    }
//
//    public int update(Customer cust) throws Exception {
//        SqlSession session = null;
//        try {
//            session = connect(false);
//            int t = session.delete("common_customer_update", cust);
//            session.commit();
//            return t;
//        } catch (Exception e) {
//            if (session != null) {
//                session.rollback();
//            }
//            throw e;
//        } finally {
//            close(session);
//        }
//    }
}

