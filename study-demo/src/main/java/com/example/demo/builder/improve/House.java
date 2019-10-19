package com.example.demo.builder.improve;

/**
 * 产品
 * 即 需要构建的对象，将大体框架写出来，具体的部件处理交给 builder
 * 相当于这个是房子的图纸，还没有创建
 */
public class House {

	private String baise;
	private String wall;
	private String roofed;

	public String getBaise() {
		return baise;
	}

	public void setBaise(String baise) {
		this.baise = baise;
	}

	public String getWall() {
		return wall;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	public String getRoofed() {
		return roofed;
	}

	public void setRoofed(String roofed) {
		this.roofed = roofed;
	}

	@Override
	public String toString() {
		return "House{" +
				"baise='" + baise + '\'' +
				", wall='" + wall + '\'' +
				", roofed='" + roofed + '\'' +
				'}';
	}
}
