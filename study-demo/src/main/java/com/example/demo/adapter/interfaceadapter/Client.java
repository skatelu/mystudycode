package com.example.demo.adapter.interfaceadapter;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * 接口适配器模式测试类
 */
public class Client {
	public static void main(String[] args) {
		AbsAdapter absAdapter = new AbsAdapterExt();

		absAdapter.m1();
		absAdapter.m2();
		absAdapter.m3();
		absAdapter.m4();

	}
}
