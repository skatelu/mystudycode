package com.szkingdom.factor.classload;

import java.lang.reflect.Constructor;

import com.szkingdom.factor.FactorBase;

/**
 * 通过反射生成相应的对象
 * 
 * @author yinhl
 *
 */
@SuppressWarnings("rawtypes")
public class ClassLoadFactors {

	public static void classLoad() {

	}

	/**
	 * 通过因子反射出相关的对象
	 * 
	 * @param factors
	 * @return
	 * @throws Exception
	 */
	public static FactorBase getFactorObj(String factorId) throws Exception {

		String className = "com.szkingdom.factor.Factor" + factorId;
		Class cl = Class.forName(className);
		if (cl != null) {
			@SuppressWarnings("unchecked")
			Constructor constructor = cl.getConstructor();
			Object o = constructor.newInstance();
			FactorBase factorsObj = (FactorBase) o;
			factorsObj.setFactorId(factorId);
			return factorsObj;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		long t = System.currentTimeMillis();
		FactorBase fb = getFactorObj("10000001");
		System.out.println(fb);
		System.out.println(System.currentTimeMillis() - t);
	}
}
