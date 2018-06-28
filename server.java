import java.io.*;
import java.net.*;
import java.util.*;
class server {
    public static void main(String[] arrstring)
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            DatagramSocket socket= new DatagramSocket(4445);
            byte[] buffer = new byte[1024];
            Scanner s= new Scanner(System.in);
			char x;
            System.out.println("Type 'End' to terminate.");
            while(true)
            {
                DatagramPacket request = new DatagramPacket(buffer , buffer.length);
                socket.receive(request);
                System.out.print("Enter a message: ");
                String string = bufferedReader.readLine();
                byte[] sendMsg = string.getBytes();
                DatagramPacket reply = new DatagramPacket(sendMsg , sendMsg.length , request.getAddress() , request.getPort());
                socket.send(reply);
                if(string.equals("End"))
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
			}
        }
        catch(Exception ex){}
    }
}