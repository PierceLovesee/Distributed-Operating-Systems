// Pierce Lovesee; CSCI 6220 Fig 4.5 Code for HW2 - February 16th, 2022
import java.net.*;
import java.io.*;
import java.util.Scanner;  //included for continuos user input.

public class TCPClient {
	public static void main (String args[]) {


		Socket s = null;
		try{
			int serverPort = 7896;
			s = new Socket("localhost", serverPort);  // the client creates a socket bound to the "localhost" an port 7896
			DataInputStream in = new DataInputStream( s.getInputStream());  // it makes DataInputeStream
			DataOutputStream out = new DataOutputStream( s.getOutputStream());  // and DataOutputStream from the socket's input and output streams

			Scanner scanner = new Scanner(System.in);  // this is a little bit of majical encantation; create an instance of a Scanner object

			System.out.println("Send unlimited messages to server."); // give
			System.out.println("To terminate, type 'exit-session'."); // user
			System.out.println();																			// instructions

			while (true) {																						// while loop to allow for continuos input
				System.out.print("Type Message to Server then (enter): "); //prompt for user
				String message = scanner.nextLine(); // reads the next line of input and stores to variable 'message'
				out.writeUTF(message);      	// then writes the message to the server to the output stream
				for (int i = 0; i <= 20; i++) {  	//
					System.out.print(". ");					// This loop is purely cosmetic
				}																	//
				System.out.println(); 								// adds line spacing for clarity
				String data = in.readUTF();	    // it then waits to read a reply from its input stream
				if (message.equalsIgnoreCase("exit-session")){				// allows means of breaking out of loop
					break;
				}
				System.out.println("Confirmation from Server: "+ data);  // this reply is then printed on the screen
				System.out.println(); 																		// line spacing for clarity
			}

		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
     }
}
