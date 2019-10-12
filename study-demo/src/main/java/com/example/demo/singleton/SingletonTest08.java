package com.example.demo.singleton;

public class SingletonTest08 {

	public static void main(String[] args) {

	}

}

enum Singleton{
	INSTANCE;
	public void sayOK(){
		System.out.println("ok~~");
	}
}