package com.example.demo.builder.improve;

public class Client {
	public static void main(String[] args) {
		// 盖普通房子
		CommonHouse commonHouse = new CommonHouse();
		// 准备创建房子的指挥者
		HouseDirector houseDirector = new HouseDirector(commonHouse);

		House house = houseDirector.constructHouse();
		System.out.println(house.toString());

		// 盖高楼
		HighBuilding highBuilding = new HighBuilding();

		houseDirector.setBuilder(highBuilding);

		House house1 = houseDirector.constructHouse();
		System.out.println(house1.toString());

	}
}
