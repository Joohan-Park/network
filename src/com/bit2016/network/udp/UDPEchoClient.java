package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {

	private static final String SERVER_IP = "192.168.1.15";
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scanner = null;
		try {

			// 스케너 생성
			scanner = new Scanner(System.in);

			// 소켓생성
			socket = new DatagramSocket();

			while (true) {
				// 사용자 입력받음
				System.out.println(">>");
				String message = scanner.nextLine();
				if(message ==null||"".equals(message)){
					continue;
				}
				
				//메세지 전송
				byte[] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));

				socket.send(sendPacket);
				
				//메세지 수신
				DatagramPacket receivePacket = new DatagramPacket( new byte[BUFFER_SIZE],BUFFER_SIZE);
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8");
				System.out.println((">>")+message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(scanner != null){
				scanner.close();
			}
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}

	}

}
