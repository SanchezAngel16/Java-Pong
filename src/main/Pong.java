package main;

import swing.Frame;

public class Pong {
	
	public Pong() {
		init();
	}
	
	void init() {
		new Frame(800,600, "Pong");
	}

	public static void main(String[] args) {
		new Pong();

	}

}
