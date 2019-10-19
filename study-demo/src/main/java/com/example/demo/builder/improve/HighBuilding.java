package com.example.demo.builder.improve;

public class HighBuilding extends HouseBuilder{
	@Override
	public void buildBasic() {
		house.setBaise("高楼打地基 10 米");
	}

	@Override
	public void buildWalls() {
		house.setWall("高楼砌墙 60 厘米");
	}

	@Override
	public void roofed() {
		house.setRoofed("高楼透明屋顶");
	}
}
