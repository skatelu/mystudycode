package com.example.demo.adapter.interfaceadapter;

/**
 * 在AbsAdapter 我们将 Interface4中的方法进行默认实现
 */
public class AbsAdapter implements Interface4 {


	@Override
	public void m1() {
		System.out.println("不允许使用！");
	}

	@Override
	public void m2() {
		System.out.println("不允许使用！");
	}

	@Override
	public void m3() {
		System.out.println("不允许使用！");
	}

	@Override
	public void m4() {
		System.out.println("不允许使用！");
	}
}
