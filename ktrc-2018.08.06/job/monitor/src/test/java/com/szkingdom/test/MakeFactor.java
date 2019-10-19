package com.szkingdom.test;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.szkingdom.common.StringUtils;
import com.szkingdom.datasource.DataSourceUtil;

public class MakeFactor {

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws Exception {
		Connection con = DataSourceUtil.getSqlSession(true).getConnection();

		String sql = "select * from risk_factors";

		PreparedStatement statement = con.prepareStatement(sql);
		ResultSet set = statement.executeQuery();

		String line = "\r\n";
		int i = 0;
		while (set.next()) {
			i++;
			StringBuffer sb = new StringBuffer();
			sb.append("/**\r\n" + 
					" * Copyright (C), 2018, 深圳市金证科技股份有限公司\r\n" + 
					" * FileName: "+set.getString("factor_class")+"\r\n" + 
					" * Author:   yinhl\r\n" + 
					" * Date:     "+df.format(new Date())+"\r\n" + 
					" * Description: \r\n" + 
					" * Version:  0.1.0\r\n" + 
					" * History:\r\n" + 
					" * <author>          <time>                <version>          <desc>\r\n" + 
					" */").append(line);
			sb.append("package com.szkingdom.factor").append(";").append(line);
			
			
			sb.append("import com.alibaba.fastjson.JSONObject;").append(line);
			
			String remark = set.getString("factor_remark");
			sb.append("/**").append(StringUtils.isEmpty(remark) ? set.getString("factor_name") : remark)
					.append("\r\n@author yinhl").append("*/").append(line);
			sb.append("public class ").append(set.getString("factor_class")).append(" extends FactorBase {")
					.append(line);
			
			java.util.Random random = new java.util.Random();
			sb.append("private static final long serialVersionUID = "+random.nextLong()+"L;");

			sb.append("public " + set.getString("factor_class") + "() {\r\n" + "		super();\r\n" + "	}")
					.append(line);

			sb.append("@Override\r\n" + "	public void resolveParameter(JSONObject json) throws Exception{\r\n"
					+ "		// TODO Auto-generated method stub\r\n" + "		\r\n" + "	}").append(line);

			sb.append("@Override\r\n" + "	public void handleFactor() throws Exception{\r\n"
					+ "		// TODO Auto-generated method stub\r\n" + "	}").append(line);
			
			sb.append("@Override\r\n" + "	public Object getResult() throws Exception{\r\n"
					+ "		// TODO Auto-generated method stub\r\n" + "	return new Double(0.0d);}").append(line);

			sb.append("}");

			File file = new File(
					"F:\\eclipse_work_jz\\ktrc\\job\\factor\\src\\main\\java\\com\\szkingdom\\factor\\"
							+ set.getString("factor_class") + ".java");
			if(file.exists()) {
				continue;
			}
			
			FileOutputStream output = new FileOutputStream(file);
			output.write(sb.toString().getBytes());
			output.flush();
			output.close();
		}
	}
}
