package com.bit2016.netowrk.thread;

public class DigitThread extends Thread {

	@Override
	public void run() {
		
		for (int i = 1; i <= 26; i++) {
			System.out.print(i);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
