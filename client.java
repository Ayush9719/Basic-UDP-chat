import java.io.*;
import java.net.*;
import java.util.*;
//import java.util.Scanner;
class client{
	public static void main(String[] arstring)
	{
		try
		{
			DatagramSocket socket= new DatagramSocket();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = "Test msg";
			Scanner s= new Scanner(System.in);
			char x; 
			byte[] arrby = msg.getBytes();
			InetAddress host = InetAddress.getByName("localhost");
			int serverSocket = 4445;
			while(true)
			{
				DatagramPacket request = new DatagramPacket (arrby , arrby.length , host , serverSocket);
				socket.send(request);
				byte[] buffer = new byte[1024];
				DatagramPacket reply = new DatagramPacket(buffer , buffer.length);
				socket.receive(reply);
				String string = new String(reply.getData()).trim();
				if (string.equals("End"))
				{	
					System.out.println("A");
					socket.close();
					System.exit(0);
				}
				System.out.println("Client recieved : " + string);
				System.out.println("Do you want to continue(Y/N): ");
				x = s.next().charAt(0);
				if(x=='N')
				{		System.out.println("Client signed out ");

						socket.close();
						System.exit(0);
				}

			}
		}
		catch(Exception ex){}
	}
}