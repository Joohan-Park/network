package com.bit2016.netowrk.thread;

public class AlphabetThread extends Thread {

	@Override
	public void run() {
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
