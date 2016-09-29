package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// Server로 넘어온 Client 정보
			InetSocketAddress inS = (InetSocketAddress) socket.getRemoteSocketAddress();

			System.out.println("[server" +getId() +"] connected by client[" + inS.getAddress().getHostAddress() + " : " + inS.getPort() + "]");
			
			// 4. IOStream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader((socket.getInputStream()), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter((socket.getOutputStream()), "UTF-8"), true); // true : 버퍼가 차면 자동으로 빼줘라
																								
			while (true) {
				// 5. data 읽기
				String data = br.readLine();
				if (data == null) {
					System.out.println("[server] closed by client");
					break;
				}
				System.out.println("[server] received: " + data);
				
				// 6. 쓰기
				pw.println(data);
			}
		} catch (SocketException e) {
			System.out.println("[server] abnormal closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 6. 자원정리(소켓 닫기)
				socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
