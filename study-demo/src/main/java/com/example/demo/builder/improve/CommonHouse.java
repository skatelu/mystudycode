package com.example.demo.builder.improve;

/**
 * 此处是普通房子的实例
 * 把制造过程与产品本身分开
 * 在此处： 如 地基、砌墙、封顶 是房子的属性  但是地基、砌墙、封顶具体怎么建造却是在这个类里面实现、处理的
 * 从而获得解耦
 */
public class CommonHouse extends HouseBuilder {

	@Override
	public void buildBasic() {
		house.setBaise("普通房子打地基5米");
	}

	@Override
	public void buildWalls() {
		house.setWall("普通房子砌墙20厘米");
	}

	@Override
	public void roofed() {
		house.setRoofed("普通房子封顶");
	}
}
