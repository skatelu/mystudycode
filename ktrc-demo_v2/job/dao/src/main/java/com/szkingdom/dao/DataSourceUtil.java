/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DataSourceUtil
 * Author:   zmh
 * Date:     2018/7/4 15:44
 * Description: mybatis数据源
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.szkingdom.data.mybatisbuilder.KingdomXMLConfigBuilder;

/**
 * 〈连接数据库〉
 * 
 * @author zmh
 * @create 2018/7/4
 * @since 1.0.0
 */
public class DataSourceUtil {

	private static SqlSessionFactory sqlSessionFactory;
	private static Lock lock = new java.util.concurrent.locks.ReentrantLock();

	private static void getSessionFactory() throws Exception {
		InputStream inputStream = null;
		try {
			String resource = "mybatis-config.xml";
			inputStream = Resources.getResourceAsStream(resource);
			KingdomXMLConfigBuilder builder = new KingdomXMLConfigBuilder(inputStream);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(builder.parse());
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error building SqlSession.", e);
		} finally {
			ErrorContext.instance().reset();
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 根据类型获取数据源
	 * 
	 * @param sourceType
	 *            数据库类型
	 * @return druid或者dbcp数据源
	 * @throws Exception
	 *             the exception
	 */
	private static final SqlSessionFactory getDataSource() throws Exception {
		if (sqlSessionFactory == null) {
			try {
				lock.lock();
				if (sqlSessionFactory != null) {
					return sqlSessionFactory;
				}
				getSessionFactory();
			} finally {
				lock.unlock();
			}
		}

		return sqlSessionFactory;
	}

	/**
	 * 关闭session
	 * 
	 * @param session
	 */
	public static void closeSession(SqlSession session) {
		if (session != null) {
			session.close();
		}
	}

	/**
	 * 
	 * @param isAuto
	 *            是否自动提交，true自动，false否
	 * @return
	 * @throws Exception
	 */
	public static SqlSession getSqlSession(boolean isAuto) throws Exception {
		try {
			SqlSessionFactory sqlSessionFactory = getDataSource();
			SqlSession SqlSession = sqlSessionFactory.openSession(isAuto);
			return SqlSession;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 提交事务
	 * 
	 * @param session
	 */
	public static void commitSession(SqlSession session) {
		if (session != null) {
			session.commit();
		}
	}

	/**
	 * 回滚事务
	 * 
	 * @param session
	 */
	public static void rollbackSession(SqlSession session) {
		if (session != null) {
			session.rollback();
		}
	}
}
