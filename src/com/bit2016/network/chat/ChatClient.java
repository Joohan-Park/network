package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "192.168.1.15";
	private static final int SERVER_PORT = 9090;

	@SuppressWarnings("null")
	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = null;
		try {
			// 1. Socket 생성
			socket = new Socket();

			// 2. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] connected "+SERVER_IP +":"+ SERVER_PORT);

			// 3. IOStream 받아오기
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			//join프로토콜
			
			System.out.print("닉네임>>");
			sc = new Scanner(System.in);
			String nickname = sc.nextLine();
			pw.println("JOIN:"+nickname);
			
			//client쓰레드시작
			Thread chatClientThread = new ChatClientThread(br);
			chatClientThread.start();
			// 4. 쓰기
			while (true) {
				System.out.print(">>");
				
				String message = sc.nextLine();
				if ("QUIT".equalsIgnoreCase(message)==true) {
					System.out.println("[client] end");
					pw.println("QUIT");
					break;
				}
				pw.println("MESSAGE:"+message);

				
			}
		}catch(SocketException e){
			System.out.println("client종료"+e);
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(sc==null){
					sc.close();
				}
				if ((socket != null) && (socket.isClosed() == false)) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
