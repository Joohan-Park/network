package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerThread extends Thread {

	private Socket socket;
	private String name;
	private List<PrintWriter> listPrintWriter;
	
	public ChatServerThread(Socket socket, List<PrintWriter> listPrintWriter){
		this.socket=socket;
		this.listPrintWriter = listPrintWriter;
		
	}
	@Override
	public void run() {
		
		try {
			//1.Client information
			InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			ChatServer.consoleLog("connecteed by client["+remoteSocketAddress.getAddress().getHostAddress()+":"+remoteSocketAddress.getPort()+"]");
			
			//2.Create Stream( From Bascic Stream )
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
			
			//3.processing........
			while(true){
				// 읽기					
				String line = br.readLine();
				if(line == null){
					doQuit(pw);
					break;
				}
				String[] tokens = line.split(":");
				
				if("JOIN".equals(tokens[0])){
					doJoin(tokens[1], pw);
				}else if("MESSAGE".equals(tokens[0])){
					doMessage(tokens[1]);
				}else if("QUIT".equals(tokens[0])){
					doQuit(pw);
				}
			}
			
		}catch(SocketException e){
			System.out.println("client종료");
		}
		catch (UnsupportedEncodingException e) {
			ChatServer.consoleLog("error"+e);
		} catch (IOException e) {
			ChatServer.consoleLog("error"+e);
		}finally{
			try {
				if(socket != null && socket.isClosed()==false){
					socket.close();
				}
			} catch (IOException e) {
				ChatServer.consoleLog("error"+e);
			}
		}
	}
	
	private void doJoin(String name, PrintWriter printWriter){
		//1. save nickname
		this.name = name;
		
		//2. broadcasting...
		String message = name + "님이 입장했습니다.";
		broadcastMessage(message);
		System.out.println(name+"들어옴");
		//3. add PrintWriter
		addPrintWriter(printWriter);
		
		//4. ack
		printWriter.println("JOIN:OK");
	}
	
	private void doMessage(String line){
		String message = name +" : "+line;
		
		broadcastMessage(message);
		System.out.println(message);
		//addPrintWriter(printWriter);
	}
	
	private void doQuit(PrintWriter printWriter){
		String message = name +"님이 나갔습니다";
		broadcastMessage(message);
		deletePrintWriter(printWriter);
	}
	
	private void addPrintWriter(PrintWriter printWriter){
		synchronized (listPrintWriter) {
			listPrintWriter.add(printWriter);
		}
	}
	
	private void deletePrintWriter(PrintWriter printWriter){
		synchronized (listPrintWriter) {
			System.out.println(name+"나감");
			listPrintWriter.remove(printWriter);
		}
	}
	
	private void broadcastMessage(String message){
		synchronized (listPrintWriter) {
			for(PrintWriter printWriter : listPrintWriter){
				printWriter.println(message);
			}
		}
	}
}
