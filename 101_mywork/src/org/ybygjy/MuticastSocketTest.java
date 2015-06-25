package org.ybygjy;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MuticastSocketTest {
	public static void main(String[] args) {
		try {
			NetworkInterface networkInterfaceTmp = NetworkInterface.getByName("eth1");
			System.out.println(networkInterfaceTmp);
			Enumeration<NetworkInterface> networkEnum = NetworkInterface.getNetworkInterfaces();
			while (networkEnum.hasMoreElements()) {
				NetworkInterface networkInterface = networkEnum.nextElement();
				System.out.println(networkInterface.toString());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		/*
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName("224.0.2.0");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int multicastPort = 8080;
		try {
			//NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
			MulticastSocket msObj = new MulticastSocket(multicastPort);
			msObj.joinGroup(new InetSocketAddress(inetAddress, 4455), null);
			byte[] byteBuff = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(byteBuff, byteBuff.length);
			while (!msObj.isClosed()) {
				msObj.receive(datagramPacket);
				System.out.println(datagramPacket.getAddress());
				System.out.println(datagramPacket.getData());
				msObj.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
