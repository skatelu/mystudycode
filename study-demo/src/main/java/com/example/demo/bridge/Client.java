package com.example.demo.bridge;

public class Client {
	public static void main(String[] args) {
		//获取折叠式手机  （样式 + 品牌）
		final FoldedPhone foldedPhone = new FoldedPhone(new XiaoMi());

		foldedPhone.open();
		foldedPhone.call();
		foldedPhone.close();

		System.out.println("===================================");
		final FoldedPhone foldedPhone1 = new FoldedPhone(new XiaoMi());

		foldedPhone1.open();
		foldedPhone1.call();
		foldedPhone1.close();
	}
}
