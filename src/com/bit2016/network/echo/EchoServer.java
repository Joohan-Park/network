package com.bit2016.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 5500;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();

			// 2. binding (소켓에 소켓주소(IP+port)을 바인딩한다)
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostAddress = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));

			System.out.println("[server] binding from " + hostAddress + " : " + PORT);

			// 3. accept(client로 부터 연결 요청을 기다린다)
			while(true){
				Socket socket = serverSocket.accept(); // block 프로그램 정지(client가
													// 요청할때까지)
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if ((serverSocket != null) && (serverSocket.isClosed() == false)) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
