package com.example.demo.bridge;

/**
 * 折叠类 继承了Open
 * 此处调用的 open、close、call 三个方法 实际上是调用的父类 phone中的方法，而Phone中的方法调用的是Brand接口具体实现类的方法，
 * 其实 此处调用的是 Brand 接口实现类的方法   Phone 起到了一个桥梁/接通的作用
 */
public class FoldedPhone extends Phone {

	// 构造器
	public FoldedPhone(Brand brand) {
		super(brand);
	}

	@Override
	protected void open() {
		super.open();
		System.out.println("折叠样式手机-开机");
	}

	@Override
	protected void close() {
		super.close();
		System.out.println("折叠样式手机-关机");
	}

	@Override
	protected void call() {
		super.call();
		System.out.println("折叠样式手机-打电话");
	}
}
