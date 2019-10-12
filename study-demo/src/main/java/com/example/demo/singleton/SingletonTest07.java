package com.example.demo.singleton;

/**
 * 使用静态内部类实现单例模式
 * 推荐使用
 */
public class SingletonTest07 {
	private static volatile SingletonTest07 instance;

	//构造器私有化
	private SingletonTest07(){

	}
	// 写一个静态内部类，该类中有一个静态属性Singleton
	private static class SingletonInstance{
		private static final SingletonTest07 INSTANCE = new SingletonTest07();
	}

	// 提供一个静态的公有方法，直接返回SingletonInstance.INSTANCE;
	public static SingletonTest07 getInstance() {
		return SingletonInstance.INSTANCE;
	}
}


