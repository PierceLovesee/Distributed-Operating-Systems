// Pierce Lovesee | March 17th, 2022

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.lang.Math;

public class Server implements RemoteInterface {
   public Server() {}
   public static void main(String args[]) {
      try {
         // create an instance of the Server class.
         Server myServerObject = new Server();

         // Exporting the remote object to the stub
         RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(myServerObject, 0);

         // Binding the remote object in the rmiregistry called on the Server machine
         Registry registry = LocateRegistry.getRegistry();

         registry.bind("RemoteInterface", stub);
         lineBreak();
         System.out.println("The Remote Interface bound to RMI Registry succesfully.");
         System.out.println();
         System.err.println("Server is ready for client connection.");
      } catch (Exception e) {
         System.err.println("Server exception: " + e.toString());
         e.printStackTrace();
      }
   }

   public void connectionMessage() {
     // method to print output when the Client/Server connection is established
     lineBreak();
     System.out.println("Connection established with Client.");
     System.out.println("RMI working correctly.");
     System.out.println("Begin execution of assigned methods via RMI.");
     lineBreak();
   }

   public int maxFive(int[] input) {
     // simple method to return the max of 5 integer values
     int len = input.length;
     int max = input[0];
     for (int i = 1; i < len; i++) {
       max = Math.max(max, input[i]);
     }
     return(max);
   }

   public double activityCalories(String activity, int min) {
     // Method to return the calories burned for a specified activity over a
     // given amount of time.
     double metric = 0.0;

     if (activity.equalsIgnoreCase("sit")) {
       metric = 1.4;
     } else if (activity.equalsIgnoreCase("walk")) {
       metric = 5.4;
     } else if (activity.equalsIgnoreCase("jog")) {
       metric = 13.0;
     } else if (activity.equalsIgnoreCase("bike")) {
       metric = 6.8;
     } else if (activity.equalsIgnoreCase("swim")) {
       metric = 8.7;
     } else {
       System.out.println("An error has occurred in activity selection");
     }

     return(min * metric);
   }

   public int rideShare(int[] input) {
     // Method to return the estimated arrival time of the closest of 3 drivers
     // to a given customer.  Location of all 4 entities (Customer and 3 Drivers)
     // is expressed in X,Y coordinates.  Time is returned in minutes.
     int[] customer = {input[0], input[1]};
     int[] result = new int[3];
     int sum = 0;

     for(int i = 2; i < 7; i = i + 2) {
       sum = 0;
       sum = (Math.abs(customer[0] - input[i]) + Math.abs(customer[1]
                  - input[i+1]));
      result[(i / 2) - 1] = sum;

     }
     int minDist = result[0];
     for(int i = 1; i < result.length; i++){
       minDist = Math.min(minDist, result[i]);
     }
     return(minDist * 2);
   }

   public static void lineBreak() {
     // Simple method for consistant user interface formatting.
     System.out.println("-------------------------------------------------------");
   }
}
