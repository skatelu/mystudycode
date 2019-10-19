/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  DaoBase.java    
 * Author: yinhl     
 * Date:   2018年7月23日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.dao;

import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;

import com.szkingdom.datasource.DataSourceUtil;

/**
 * @author yinhl
 *
 */
public abstract class DaoBase {
	/**
	 * 增
	 * 
	 * @param mybatisSqlId
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int insert(String mybatisSqlId, Object para) throws Exception {
		SqlSession session = null;
		try {
			session = connect(false);
			int t = session.insert(mybatisSqlId, para);
			session.commit();
			return t;
		} catch (Exception e) {
			rollback(session);
			throw e;
		} finally {
			close(session);
		}
	}

	/**
	 * 删
	 * 
	 * @param mybatisSqlId
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int delete(String mybatisSqlId, Object para) throws Exception {
		SqlSession session = null;
		try {
			session = connect(false);
			int t = session.delete(mybatisSqlId, para);
			session.commit();
			return t;
		} catch (Exception e) {
			rollback(session);
			throw e;
		} finally {
			close(session);
		}
	}

	/**
	 * 改
	 * 
	 * @param mybatisSqlId
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int update(String mybatisSqlId, Object para) throws Exception {
		SqlSession session = null;
		try {
			session = connect(false);
			int t = session.update(mybatisSqlId, para);
			session.commit();
			return t;
		} catch (Exception e) {
			rollback(session);
			throw e;
		} finally {
			close(session);
		}
	}

	/**
	 * 查询返回集合
	 * 
	 * @param mybatisSqlId
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public List<?> selectList(String mybatisSqlId, Object para) throws Exception {
		SqlSession session = null;
		try {
			session = connect();
			if (para != null) {
				return session.selectList(mybatisSqlId, para);
			} else {
				return session.selectList(mybatisSqlId);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(session);
		}
	}

	/**
	 * 返回游标，需要自己迭代结果
	 * 
	 * @param mybatisSqlId
	 * @param para
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public Cursor<Object> selectCursor(String mybatisSqlId, Object para) throws Exception {
		SqlSession session = null;
		try {
			session = connect();
			if (para != null) {
				return session.selectCursor(mybatisSqlId, para);
			} else {
				return session.selectCursor(mybatisSqlId);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(session);
		}
	}

	/**
	 * 查询一条数据
	 * 
	 * @param mybatisSqlId
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public Object selectOne(String mybatisSqlId, Object para) throws Exception {
		SqlSession session = null;
		try {
			session = connect();
			return session.selectOne(mybatisSqlId, para);
		} catch (Exception e) {
			throw e;
		} finally {
			close(session);
		}
	}

	/**
	 * 获取事物，默认为否不自动提交
	 * 
	 * @return
	 * @throws Exception
	 */
	protected SqlSession connect() throws Exception {
		try {
			return connect(false);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取
	 * 
	 * @return
	 * @throws Exception
	 */
	protected SqlSession connect(boolean isAuto) throws Exception {
		try {
			return DataSourceUtil.getSqlSession(isAuto);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 关闭session
	 * 
	 * @param session
	 */
	public void close(SqlSession session) {
		DataSourceUtil.closeSession(session);
	}

	/**
	 * 提交session
	 * 
	 * @param session
	 */
	public void commit(SqlSession session) {
		DataSourceUtil.commitSession(session);
	}

	/**
	 * 回滚session
	 * 
	 * @param session
	 */
	public void rollback(SqlSession session) {
		DataSourceUtil.rollbackSession(session);
	}
}
