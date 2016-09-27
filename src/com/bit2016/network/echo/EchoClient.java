package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "192.168.1.15";
	private static final int SERVER_PORT = 5500;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = null;
		try {
			// 1. Socket 생성
			socket = new Socket();

			// 2. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] connected");

			// 3. IOStream 받아오기

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			// 4. 쓰기
			while (true) {
				System.out.print(">>");
				sc = new Scanner(System.in);
				String data = sc.nextLine();
				if ("exit".equals(data)) {
					System.out.println("[client] end");
					return;
				}
				pw.println(data);

				// 5. 읽기
				String redata = br.readLine();
				if (redata == null) {
					System.out.println("[client] closed by server");
					return;
				}
				System.out.println("<<" + redata);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if ((socket != null) && (socket.isClosed() == false)) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
