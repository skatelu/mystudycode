package com.example.demo.builder.improve;

/**
 * 指挥者，这里去动态的指定
 */
public class HouseDirector {
	HouseBuilder builder = null;

	// 构造器传入 houseBuilder

	public HouseDirector(HouseBuilder builder) {
		this.builder = builder;
	}

	// 通过setter 传入 houseBuilder
	public void setBuilder(HouseBuilder builder) {
		this.builder = builder;
	}

	//如何处理建造房子的流程，交给指挥者
	public House constructHouse(){
		builder.buildBasic();
		builder.buildWalls();
		builder.roofed();
		return builder.buildHouse();
	}
}
