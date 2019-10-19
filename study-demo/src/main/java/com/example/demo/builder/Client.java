package com.example.demo.builder;

/**
 * 建造者模式--测试类
 */
public class Client {

	public static void main(String[] args) {
		CommonHouse commonHouse = new CommonHouse();
		commonHouse.build();
	}
}
