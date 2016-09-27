package com.bit2016.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {

		Scanner sc = null;
		

		try {
			while (true) {
				System.out.print(">>");
				sc = new Scanner(System.in);
				String hostName = sc.nextLine();
				if ("exit".equals(hostName)) {
					sc.close();
					System.out.println("종료");
					return;
				}
				InetAddress[] inetAddresses = InetAddress.getAllByName(hostName);

				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(hostName + " : " + inetAddress.getHostAddress());
				}
			}
		} catch (UnknownHostException e) {
			System.out.println("error" + e);
		} finally {
			if (sc != null) {
				sc.close();
			}
		}

	}
}