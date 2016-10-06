package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	private static final int PORT = 9090;
	private static final int BUFFER_SIZE = 0;

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			// 1. socket생성
			socket = new DatagramSocket(PORT);
			while (true) {
				// 2. 요청 패킷 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
			
				// 3. 시간 데이터 응답
				String now = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss.SSS").format(new Date());
				System.out.println(now);
				byte[] sendData = now.getBytes(); 
				System.out.println(receivePacket.getAddress());
				System.out.println(receivePacket.getPort());
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),receivePacket.getPort());
				socket.send(sendPacket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}
}
