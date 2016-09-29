package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class ChatClientThread extends Thread{

	private BufferedReader bufferedReader;
	
	public ChatClientThread(BufferedReader bufferedReader){
		this.bufferedReader=bufferedReader;
		
	}

	@Override
	public void run() {
		String message;
		try {
			while(true){
				message = bufferedReader.readLine();
				if (message == null) {
					System.out.println("[client] closed by server");
					break;
				}
				System.out.println(message);
			}
		}catch(SocketException e){
			System.out.println("Client 종료");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
