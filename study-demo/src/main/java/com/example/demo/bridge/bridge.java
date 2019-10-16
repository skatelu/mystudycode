package com.example.demo.bridge;

import java.util.HashMap;

public class bridge {

	public static void main(String[] args) {
		HashMap<String, String> a = new HashMap<>();

		final String s = a.get("123");
		System.out.println(s);

	}
}
