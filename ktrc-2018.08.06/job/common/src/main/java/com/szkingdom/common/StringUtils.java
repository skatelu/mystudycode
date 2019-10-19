/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: StringUtils
 * Author:   ZhangChangHong
 * Date:     2018/7/24
 * Description: 字符串工具类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.common;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StringUtils {
	public static final String EMPTY = "";

	/** 字符串为空 **/
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/** 字符串不为空 **/
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/** 空字符串转换 **/
	public static String emptyConvert(String str, String str1) {
		return isNotEmpty(str) ? str : str1;
	}

	/** 字符串去空格 **/
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 将字符串进行解析例如：AVAILABLE:11.5,TRD_FRZ:12,OUTSTANDING:34,OTD_AVL:26.8
	 *
	 * @param string
	 *            *格式 AVAILABLE:11.5,TRD_FRZ:12,OUTSTANDING:34,OTD_AVL:26.8
	 * @param field
	 * @return
	 */
	public static String getFieldStrValue(String strInfo, String fieldName) {

		if (strInfo != null && !strInfo.isEmpty() && strInfo.length() > 0 && fieldName != null && !fieldName.isEmpty()
				&& fieldName.length() > 0) {
			int index = strInfo.indexOf(fieldName);
			int length = fieldName.length() + 1;
			int index1 = strInfo.indexOf(",", index + length);
			String fieldStrValue = strInfo.substring(index + length, index1);

			return fieldStrValue;
		} else {
			return null;
		}
	}

	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	/** 替换字符串 **/
	public static String replace(String text, String searchString, String replacement, int max) {
		if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
			int start = 0;
			int end = text.indexOf(searchString, start);
			if (end == -1) {
				return text;
			} else {
				int replLength = searchString.length();
				int increase = replacement.length() - replLength;
				increase = increase < 0 ? 0 : increase;
				increase *= max < 0 ? 16 : (max > 64 ? 64 : max);

				StringBuffer buf;
				for (buf = new StringBuffer(text.length() + increase); end != -1; end = text.indexOf(searchString,
						start)) {
					buf.append(text.substring(start, end)).append(replacement);
					start = end + replLength;
					--max;
					if (max == 0) {
						break;
					}
				}

				buf.append(text.substring(start));
				return buf.toString();
			}
		} else {
			return text;
		}
	}

	public static String join(String[] array) {
		return join(array, (String) null);
	}

	/** 连接字符串 **/
	public static String join(String[] list, String separator) {
		separator = separator == null ? "" : separator;
		StringBuffer buff = new StringBuffer(5 * list.length);

		for (int i = 0; i < list.length; ++i) {
			String s = list[i];
			if (i > 0) {
				buff.append(separator);
			}

			if (s != null) {
				buff.append(s);
			}
		}

		return buff.toString();
	}

	public static String[] split2Array(String s, char separatorChar) {
		return split2Array(s, separatorChar, false);
	}

	public static String[] split2Array(String s, char separatorChar, boolean trim) {
		if (s == null) {
			return null;
		} else if (s.length() == 0) {
			return new String[0];
		} else {
			ArrayList list = new ArrayList();
			StringBuffer buff = new StringBuffer(s.length());

			for (int e = 0; e < s.length(); ++e) {
				char array = s.charAt(e);
				if (array == separatorChar) {
					String e1 = buff.toString();
					list.add(trim ? e1.trim() : e1);
					buff.setLength(0);
				} else if (array == 92 && e < s.length() - 1) {
					++e;
					buff.append(s.charAt(e));
				} else {
					buff.append(array);
				}
			}

			String arg7 = buff.toString();
			list.add(trim ? arg7.trim() : arg7);
			String[] arg8 = new String[list.size()];
			list.toArray(arg8);
			return arg8;
		}
	}

	/** 中文检查 **/
	private static final boolean isChinese(char c) {
		UnicodeBlock ub = UnicodeBlock.of(c);
		return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	public static final int getLengthBy2Ch(String strName) {
		int length = strName.length();
		char[] ch = strName.toCharArray();

		for (int i = 0; i < ch.length; ++i) {
			char c = ch[i];
			if (isChinese(c)) {
				++length;
			}
		}

		return length;
	}

	public static String[] getStrArray(Object[] args) {
		String[] str = null;
		if (null != args) {
			str = new String[args.length];

			for (int i = 0; i < args.length; ++i) {
				str[i] = args[i].toString();
			}
		}

		return str;
	}

	/** 获取文件拓展名 **/
	public static String getExtensionName(String filename) {
		if (filename != null && filename.length() > 0) {
			int dot = filename.lastIndexOf(46);
			if (dot > -1 && dot < filename.length() - 1) {
				return filename.substring(dot + 1);
			}
		}

		return filename;
	}

	/** 检测变量是否为数字或数字字符串 **/
	public static boolean isNumeric(String val) {
		Pattern p = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		return p.matcher(val).matches();
	}

}