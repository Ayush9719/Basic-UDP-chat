import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class MulticastSocketServerFinal {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;	
    public static void main(String[] args) throws UnknownHostException, InterruptedException {

        // Get the address that we are going to connect to.
        Scanner s= new Scanner(System.in);
        char x;
        InetAddress addr = InetAddress.getByName(INET_ADDR);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));  
   
        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            while (true)
             {   System.out.print("Enter a message: ");
                 String msg = bufferedReader.readLine();

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, addr, PORT);
                serverSocket.send(msgPacket);
     			if(msg.equals("End"))
                {
                	System.out.println("Client has been terminated.");
                	System.out.println("Do you want to continue(Y/N): ");
					x = s.next().charAt(0);
					if(x=='N')
					{
						System.out.println("Server terminated.");
						System.exit(0);
					}
				}
				else
                	System.out.println("Server: " + msg);
                Thread.sleep(500);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}