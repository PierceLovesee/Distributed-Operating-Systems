// Pierce Lovesee; CSCI 6220 Fig 4.6 Code for HW2 - February 16th, 2022
import java.net.*;
import java.io.*;
public class TCPServer {
	public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort); // the program opens a server socket on 7896
			System.out.println("Server Started.");
			System.out.println("Press 'Ctrl + C' to Exit at Any Time.");
			System.out.println("Listening at port " + serverPort + "."); // user interface to show server has started
			System.out.println();
			while(true) {
				Socket clientSocket = listenSocket.accept(); // and listens for connect requests
				Connection c = new Connection(clientSocket); // when one arrives, it makes a new thread in which to commincate with the client
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
}
class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection (Socket aClientSocket) { // Connection object (or is this a constructor??) is created taking in an a client socket as argument
		try {
			clientSocket = aClientSocket; // input client socket is renamed
			in = new DataInputStream( clientSocket.getInputStream()); // input
			out =new DataOutputStream( clientSocket.getOutputStream()); // and output streams are created from the socket
			this.start(); // keywork this refers to "this current object in this method or constructor"; calling "start" creats a new thread and then the run() method is executed
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){ // when the start() method is called, a new thread is created with the current Connection object, and then this run() method is envoked
		try {
			System.out.println("TCP Connection Established with Client");
			System.out.println();
			while (true) {																	// allows for continuos evaluation of the input stream
				String data = in.readUTF();	                  // read a line of data from the input stream
				// if (data.equalsIgnoreCase("exit-session")){  // this break is not working for some reason, is it because the thread is still running?
				// 	this.close(); 							//
				// 	break;
				// }
				System.out.println("Received from Client: " + data); // user interface
				System.out.println();																	// user interface
				out.writeUTF("**Messsage Received**");			// let the client know the message was received.
			}

		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}


	}
}
