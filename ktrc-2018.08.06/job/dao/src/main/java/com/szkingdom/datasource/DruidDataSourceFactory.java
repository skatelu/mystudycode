/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DataSourceUtil
 * Author:   yinhl
 * Date:     2018/7/10 15:44
 * Description: druid连接池封装集成到mybais里面
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.szkingdom.common.ConstantUtil;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.szkingdom.common.ConfigFileUtil;

/**
 * mybaits 中使用Druid连接池
 * 
 * @author yinhl
 *
 */
public class DruidDataSourceFactory implements DataSourceFactory {

	private static Logger logger = LoggerFactory.getLogger(DruidDataSourceFactory.class);

	private Properties props;

	@Override
	public void setProperties(Properties props) {
		this.props = props;
	}

	@Override
	public DataSource getDataSource() {
		InputStream input = null;
		DruidDataSource dds = new DruidDataSource();
		if (props == null || props.getProperty("driverClassName") == null) {
			try {
				input = ConfigFileUtil.readPropFileOutJar(ConstantUtil.DRUID_CONFIG);
			} catch (Exception e) {
				logger.error("未能成功外部读取/config/druid.properties，下面尝试内部读取");
			}
			try {
				if (input == null) {
					input = Resources.getResourceAsStream("druid.properties");
				}

				props = new Properties();
				props.load(input);
				dds.setDriverClassName(props.getProperty("driverClassName"));
				dds.setUrl(props.getProperty("url"));
				dds.setUsername(props.getProperty("username"));
				dds.setPassword(props.getProperty("password"));
				dds.setInitialSize(Integer.parseInt(props.getProperty("initialSize")));
				dds.setMaxActive(Integer.parseInt(props.getProperty("poolMaxSize")));
				dds.setMaxWait(Long.parseLong(props.getProperty("maxWait")));
				try {
					dds.init();
				} catch (Exception e) {
					logger.error("Druid数据库连接池初始化失败" + e.getMessage(), e);
				}

			} catch (Exception e) {
				return null;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
			}
		}

		return dds;
	}
}
