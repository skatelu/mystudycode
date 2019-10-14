package com.example.demo.adapter.objectadapter;

/**
 * 适配器类 继承了被适配的类
 */
public class VoltageAdapter implements IVoltage5V {

	private Voltage220V voltage220V;

	/**
	 * 通过构造器传入一个 voltage220V 的属性
	 * @param voltage220V
	 */
	public VoltageAdapter(Voltage220V voltage220V) {
		this.voltage220V = voltage220V;
	}

	@Override
	public int output5V() {
		int dst = 0;
		if (null != voltage220V) {
			int src = voltage220V.output220V();// 获取220V 电压
			dst = src / 44;
			System.out.println("对象适配器转换完成");
		}
		return dst;
	}

}
