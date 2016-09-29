package com.bit2016.netowrk.thread;

public class UppercaseAlphabet{
	public void print() {
		for (char c = 'A'; c <= 'Z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
