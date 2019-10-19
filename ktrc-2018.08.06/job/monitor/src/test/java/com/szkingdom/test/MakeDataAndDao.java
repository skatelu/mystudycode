package com.szkingdom.test;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.szkingdom.datasource.DataSourceUtil;

public class MakeDataAndDao {

	private static String daoPath = "F:\\eclipse_work_jz\\ktrc\\job\\dao\\src\\main\\java\\com\\szkingdom\\dao";
	private static String xmlPath = "F:\\eclipse_work_jz\\ktrc\\job\\data\\src\\main\\java\\com\\szkingdom\\sqlmapper";
	private static String dataPath = "F:\\eclipse_work_jz\\ktrc\\job\\data\\src\\main\\java\\com\\szkingdom\\data";
	private static String line = "\r\n";
	private static String className;
	private static String tableName;
	private static List<String> colNameList;
	private static List<String> porList;
	private static List<String> classTypeList;
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		table();
	}
	

	private static void table() throws Exception {
		Set<String> tableSet = new HashSet<>(); 
		Connection con = DataSourceUtil.getSqlSession(true).getConnection();
		PreparedStatement statement = con.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			String tableName = set.getString(1);
			if(tableName.equalsIgnoreCase("Sync_Acct_Info")) {
				continue;
			}
			if(tableName.toLowerCase().startsWith("uc") || 
					tableName.toLowerCase().startsWith("upm") ||
					tableName.toLowerCase().startsWith("uem")||
					tableName.toLowerCase().startsWith("uum")) {
				continue;
			}
			tableSet.add(set.getString(1));
		}
		
		/*for(String table : tableSet) {
			statement = con.prepareStatement("select * from " + table);
			set = statement.executeQuery();
			
			ResultSetMetaData resultSetMetaData = set.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				colTypeSet.add(resultSetMetaData.getColumnTypeName(i));
			}
		}*/
		
		con.close();
		
		tableSet.remove("SyncAcctInfo");
		
		for (String string : tableSet) {
			tableName = string.toUpperCase();
			code(string);
		}
	}

	private static void code(String table) throws Exception {

		String[] names = table.toLowerCase().split("_");
		className = names[0];
		for (int i = 1; i < names.length; i++) {
			className += names[i].substring(0, 1).toUpperCase() + names[i].substring(1);
		}

		className = className.trim();
		className = className.substring(0, 1).toUpperCase() + className.substring(1);

		Connection con = DataSourceUtil.getSqlSession(true).getConnection();
		PreparedStatement statement = con.prepareStatement("select * from " + table);
		ResultSet set = statement.executeQuery();

		colNameList = new ArrayList<String>();
		porList = new ArrayList<String>();
		classTypeList = new ArrayList<String>();

		ResultSetMetaData resultSetMetaData = set.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			colNameList.add(resultSetMetaData.getColumnName(i));
			classTypeList.add(resultSetMetaData.getColumnTypeName(i) + "-" + resultSetMetaData.getColumnDisplaySize(i));
			
			if("numeric".equals(resultSetMetaData.getColumnTypeName(i))) {
				System.out.println(resultSetMetaData.getColumnTypeName(i) + "-" + resultSetMetaData.getColumnDisplaySize(i));
			}
			
			String[] temp = resultSetMetaData.getColumnName(i).split("_");
			String pro = temp[0];
			for (int j = 1; j < temp.length; j++) {
				pro += temp[j].substring(0, 1).toUpperCase() + temp[j].substring(1);
			}
			porList.add(pro);
		}
		
		//System.out.println(classTypeList);

		makeData();

		makeMapper();

		makeDao();
		con.close();
	}

	private static void makeDao() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("/**\r\n" + 
				" * Copyright (C), 2018, 深圳市金证科技股份有限公司\r\n" + 
				" * FileName: Dao"+className+"\r\n" + 
				" * Author:   yinhl\r\n" + 
				" * Date:     "+df.format(new Date())+"\r\n" + 
				" * Description: \r\n" + 
				" * Version:  0.1.0\r\n" + 
				" * History:\r\n" + 
				" * <author>          <time>                <version>          <desc>\r\n" + 
				" */").append(line);
		String packageDao = "package com.szkingdom.dao";
		
		sb.append(packageDao).append(";").append(line);
		
		sb.append("import java.util.List;").append(line);
		sb.append("import org.apache.ibatis.session.SqlSession;").append(line);
		sb.append("import com.szkingdom.data.Data"+className).append(";").append(line);

		sb.append("public class Dao" + className).append(" extends DaoBase {").append(line).append(line);
		
		
		sb.append("    public Data"+ className).append(" selectUnique (Data" + className).append(" data" + className).append(")throws Exception{").append(line);
		sb.append("        return null;").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append("    public Data"+ className).append(" selectLock (Data" + className).append(" data" + className);
		sb.append(" , SqlSession session");
		sb.append(")throws Exception{").append(line);
		sb.append("        return null;").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append("    public List<Data"+className+">").append(" selectList (Data" + className).append(" data" + className).append(")throws Exception{").append(line);
		sb.append("        return null;").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append("    public int update(Data" + className).append(" data" + className).append(")throws Exception{").append(line);
		sb.append("        return 0;").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append("    public int update(Data" + className).append(" data" + className);
		sb.append(" , SqlSession session");
		sb.append(")throws Exception{").append(line);
		sb.append("        return 0;").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append("    public int insert(Data" + className).append(" data" + className).append(")throws Exception{").append(line);
		sb.append("        return 0;").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append("    public int insert(Data" + className).append(" data" + className);
		sb.append(" , SqlSession session");
		sb.append(")throws Exception{").append(line);
		sb.append("        return 0;").append(line);
		sb.append("    }").append(line).append(line);
		
		
		sb.append("}");
		System.out.println(sb);
		File f = new File(daoPath);
		f.mkdirs();
		
		File currFile = new File(f,"Dao" + className + ".java");
		if(currFile.exists()) {
			return;
		}
		
		FileOutputStream out = new FileOutputStream(currFile);
		out.write(sb.toString().getBytes());
		out.flush();
		out.close();
		
		
		
		
	}

	private static void makeMapper() throws Exception {
		String packageMapper = "com.szkingdom.sqlmapper.";
		StringBuffer sb = new StringBuffer(
				"<!DOCTYPE mapper\r\n" + "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n"
						+ "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + "\r\n"
						+ "<mapper namespace=\"" + packageMapper + className + "\">").append(line);

		sb.append("    <resultMap id=\"" + className + "Map\" type=\"com.szkingdom.data.Data" + className + "\">")
				.append(line);
		int i = 0;
		for (String string : colNameList) {
			sb.append("        <result column=\"" + string.toUpperCase() + "\"  property=\"" + porList.get(i) + "\"  />")
					.append(line);
			i++;
		}
		sb.append("    </resultMap>").append(line);
		
		String id = tableName +"_SELECT";
		String resultMap = className + "Map";
		String parameterType = "com.szkingdom.data.Data" + className;
		sb.append(line).append(getSelect(id,resultMap,parameterType)).append(line);
		id = tableName +"_INSERT";
		sb.append(line).append(getInsert(id,parameterType)).append(line);
		
		id = tableName +"_UPDATE";
		sb.append(line).append(getUpdate(id,parameterType)).append(line);

		sb.append(line).append("</mapper>");

		System.out.println(sb.toString());
		
		File f = new File(xmlPath);
		f.mkdirs();
		
		File currFile = new File(f,className + ".xml");
		if(currFile.exists()) {
			return;
		}
		
		FileOutputStream out = new FileOutputStream(currFile);
		out.write(sb.toString().getBytes());
		out.flush();
		out.close();
		
	}
	
	private static String getSelect(String id,String resultMap,String parameterType) {
		StringBuffer sb = new StringBuffer();
		sb.append(line);
		sb.append("    <select id=\""+id+"\" resultMap=\""+resultMap+"\" parameterType=\""+parameterType+"\">");
		int i = 0;
		sb.append(line).append("        select");
		for (String string : colNameList) {
			if(i % 8 == 0) {
				sb.append(line).append("        ");
			}
			sb.append(string.toUpperCase());
			if(colNameList.size() -1 > i) {
				sb.append(",");
			}
			
			i++;
		}
		sb.append(line).append("        from ").append(tableName);
		sb.append(line).append("    </select>");
		return sb.toString();
	}
	
	private static String getInsert(String id,String parameterType) {
		StringBuffer sb = new StringBuffer();
		sb.append(line);
		sb.append("    <insert id=\""+id+"\" parameterType=\""+parameterType+"\">");
		int i = 0;
		sb.append(line).append("        insert into "+tableName+"(");
		for (String string : colNameList) {
			if(i % 8 == 0) {
				sb.append(line).append("        ");
			}
			sb.append(string.toUpperCase());
			if(colNameList.size() -1 > i) {
				sb.append(",");
			}
			
			i++;
		}
		sb.append(")");
		sb.append(line).append("        values ( ");
		i = 0;
		for (String string : porList) {
			if(i % 8 == 0) {
				sb.append(line).append("        ");
			}
			sb.append("#{").append(string).append("}");
			if(colNameList.size() -1 > i) {
				sb.append(",");
			}
			
			i++;
		}
		sb.append(")");
		sb.append(line).append("    </insert>").append(line);
		return sb.toString();
	}
	
	private static String getUpdate(String id,String parameterType) {
		StringBuffer sb = new StringBuffer();
		sb.append(line);
		sb.append("    <update id=\""+id+"\" parameterType=\""+parameterType+"\">");
		int i = 0;
		sb.append(line).append("        update "+tableName+" set");
		for (String string : colNameList) {
			if(i % 3 == 0) {
				sb.append(line).append("        ");
			}
			sb.append(string.toUpperCase()).append(" = ").append("#{").append(porList.get(i)).append("}");
			if(colNameList.size() -1 > i) {
				sb.append(",");
			}
			
			i++;
		}
		
		sb.append(line).append("    </update>").append(line);
		return sb.toString();
	}

	private static void makeData() throws Exception {
		String packageData = "package com.szkingdom.data";
		StringBuffer sb = new StringBuffer();
		sb.append("/**\r\n" + 
				" * Copyright (C), 2018, 深圳市金证科技股份有限公司\r\n" + 
				" * FileName: Data"+className+"\r\n" + 
				" * Author:   yinhl\r\n" + 
				" * Date:     "+df.format(new Date())+"\r\n" + 
				" * Description: \r\n" + 
				" * Version:  0.1.0\r\n" + 
				" * History:\r\n" + 
				" * <author>          <time>                <version>          <desc>\r\n" + 
				" */").append(line);
		sb.append(packageData).append(";").append(line);

		sb.append("import com.alibaba.fastjson.JSONObject;").append(line);
		sb.append("import java.util.Date;").append(line);
		sb.append("import java.sql.Timestamp;").append(line);

		sb.append("public class Data" + className).append(" extends DataBase {").append(line).append(line);
		
		java.util.Random random = new java.util.Random();
		
		sb.append("    private static final long serialVersionUID = "+random.nextLong()+"L;").append(line);

		int index = 0;
		for (String classType : classTypeList) {
			sb.append("    private ").append(getClassType(classType)).append(" ").append(porList.get(index)).append(";")
					.append(line);
			index++;
		}

		sb.append(line).append("    public Data").append(className).append("(){").append(line);
		sb.append("    }").append(line).append(line);

		sb.append("    public Data").append(className).append("(JSONObject json){").append(line);
		sb.append("    }").append(line).append(line);
		
		sb.append(line);
		sb.append(getInit());
		sb.append(line);

		index = 0;
		for (String classType : classTypeList) {

			sb.append(getset(porList.get(index), classType));

			index++;
		}

		sb.append("}");

		File f = new File(dataPath);
		f.mkdirs();
		
		File currFile = new File(f,"Data" + className + ".java");
		if(currFile.exists()) {
			return;
		}
		
		FileOutputStream out = new FileOutputStream(currFile);
		out.write(sb.toString().getBytes());
		out.flush();
		out.close();
		System.out.println(sb.toString());
	}
	
	private static String getInit() {
		StringBuffer sb = new StringBuffer();
		sb.append("    public void init(){").append(line);
		int i = 0;
		for (String string : classTypeList) {
			if(getClassType(string).equalsIgnoreCase("string")) {
				sb.append("        this."+porList.get(i)+" = \"\";").append(line);
			}else if(getClassType(string).equalsIgnoreCase("Character")) {
				sb.append("        this."+porList.get(i)+" = null;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("long")) {
				sb.append("        this."+porList.get(i)+" = 0L;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("int")) {
				sb.append("        this."+porList.get(i)+" = 0;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("short")) {
				sb.append("        this."+porList.get(i)+" = 0;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("float")) {
				sb.append("        this."+porList.get(i)+" = 0.0F;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("double")) {
				sb.append("        this."+porList.get(i)+" = 0.0D;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("date")) {
				sb.append("        this."+porList.get(i)+" = null;").append(line);
			}else if(getClassType(string).equalsIgnoreCase("timestamp")) {
				sb.append("        this."+porList.get(i)+" = null;").append(line);
			}
			i ++;
		}
		sb.append(line);
		sb.append("    }");
		sb.append(line);
		return sb.toString();
	}

	private static String getset(String pro, String classType) {
		StringBuffer sb = new StringBuffer();
		sb.append("    public void set").append(getPro(pro)).append("(").append(getClassType(classType) + " " + pro)
				.append("){").append(line);
		sb.append("    ").append("    this." + pro).append(" = " + pro).append(";").append(line);
		sb.append("    }").append(line).append(line);
		sb.append("    public ").append(getClassType(classType)).append(" ").append(" get" + getPro(pro)).append("(){")
				.append(line);
		sb.append("    ").append("    return this.").append(pro).append(";").append(line);
		sb.append("    }").append(line).append(line);
		return sb.toString();
	}

	public static String getClassType(String type) {
		
		String [] tem = type.split("-");
		String classType = tem[0];
		
		if (classType.indexOf("char") != -1) {
			if(Integer.valueOf(tem[1]) == 1) {
				return "Character";
			}
			return "String";
		} else if (classType.equals("int8")) {
			return "long";
		} else if (classType.equals("int4")) {
			return "int";
		} else if(classType.equals("int2")) {
			return "short";
		}else if(classType.equals("numeric")) {
			if(Integer.valueOf(tem[1]) < 10) {
				return "float";
			}
			return "double";
		}else if(classType.equals("timestamp")) {
			return "Timestamp";
		}else if(classType.equals("date")) {
			return "Date";
		}

		return "String";
	}

	private static String getPro(String pro) {
		pro = pro.substring(0, 1).toUpperCase() + pro.substring(1);
		return pro;
	}

}
