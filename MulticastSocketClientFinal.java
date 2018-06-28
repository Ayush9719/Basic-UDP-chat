import java.io.*;
import java.util.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class MulticastSocketClientFinal {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException {
        // Get the address that we are going to connect to.
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));  
        
        InetAddress address = InetAddress.getByName(INET_ADDR);
        Scanner s= new Scanner(System.in);
        char x;

        // Create a buffer of bytes, which will be used to store
        // the incoming bytes containing the information from the server.
        // Since the message is small here, 256 bytes should be enough.
        
        
        // Create a new Multicast socket (that will allow other sockets/programs
        // to join it as well.
        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
            //Join the Multicast group.
            clientSocket.joinGroup(address);
            System.out.println("Enter nickname:");
            String name = bufferedReader.readLine();
     
            while (true) {
                // Receive the information and print it.
                System.out.println("Enter choice: ");
                System.out.println("1)Send to all\n2)Recieve\n3)Exit");
                x = s.next().charAt(0);
                if(x=='1')
                {
                    try (DatagramSocket serverSocket = new DatagramSocket()) {
                        System.out.print("Enter a message: ");
                        String msg = bufferedReader.readLine();
                        DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                                    msg.getBytes().length, address, PORT);
                        serverSocket.send(msgPacket);
                        System.out.println(name+":" + msg);
                        
                    }
                }
                else if(x=='2')
                {
                    byte[] buf = new byte[256];
                    DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                    clientSocket.receive(msgPacket);
                    String string = new String(msgPacket.getData()).trim();
                    String[] parts=string.split(" ");
                    if (parts[0].equals("Kill"))
                    {
                        if(parts[1].equals(name))
                        {
                            System.out.println("Client killed by server");
                            clientSocket.close();
                            System.exit(0);
                        }
                    }
                    else if (string.equals("End"))
                    {   
                        System.out.println("Broadcast terminated by server");
                        clientSocket.close();
                        System.exit(0);
                    }
                    if(parts[0].equals("Kill"))
                        System.out.println(parts[1]+" has been killed by Server");
                    else
                        System.out.println("Client recieved : " + string);
                }
                else if(x=='3')
                {       System.out.println("Client signed out ");

                        clientSocket.close();
                        System.exit(0);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}