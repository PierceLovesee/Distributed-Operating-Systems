// Pierce Lovesee | March 17th, 2022

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
   private Client() {}
   public static void main(String[] args) {
      try {
         // Obtains the RMI registry and assigns it to a new Registry object "registry"
         Registry registry = LocateRegistry.getRegistry(null);

         // Assigns the RemoteInterface accessed on the registry to the RemoteInterface instance called stub
         RemoteInterface stub = (RemoteInterface) registry.lookup("RemoteInterface");

         // testing RMI connection and envoking first method via RMI
         // This is simply a test method to print some informatoin on the server
         // to show that the connnection was succesful and RMI is working.
         stub.connectionMessage();

         // these three invocations pass in the RemoteInteface object, stub,
         // and then collect the user input within the client that will be
         // sent to the server.  The input objects are passed to the server, the
         // computational load is placed on the server, and then the result is
         // returned from the server to the client for display.

         callToMaxFive(stub);

         // Loop for activityCalorie to enable multiple iterations of method
         Scanner scanner = new Scanner(System.in);
         boolean finished = false;
         while(!finished){
           callToActivityCalories(stub);
           makeSpace(1);
           System.out.println("Would you like to perform another calculation?");
           System.out.println("1 = Yes | 2 = No:");
           int answer = 0;
           while((answer != 1) && (answer != 2)){
             answer = getIntInput();
           }
           if(answer == 2){
             break;
           }
         }

         callToRideShare(stub);

        }catch (Exception e) {
         System.err.println("Client exception: " + e.toString());
         e.printStackTrace();
      }
   }

   public static int getIntInput() {
     // Simple method to collect and guarantee integer input from user.
     Scanner scanner = new Scanner(System.in);
     System.out.println("Please enter an integer: ");
     while (!scanner.hasNextInt()) {
       System.out.println("Only integer values are accepted: ");
       scanner.nextLine();
     }
     return(scanner.nextInt());
   }

   public static void makeSpace(int a) {
     // Method for consistant formatting on user Interface
     for (int i = 0; i < a; i++){
       System.out.println();
     }
   }

   public static void lineBreak() {
     // Method for consistant formatting on user interface
     System.out.println("-----------------------------------------------------------");
   }

   public static void callToMaxFive(RemoteInterface stub) {
     // Method to create input object to pass to maxFive in Server via RMI
     try {
       lineBreak();
       makeSpace(1);
       System.out.println("**** Begin maxFive RMI ****");
       makeSpace(1);

       // input collection for maxFive
       int input[] = new int[5];
       for (int i = 0; i < 5; i++) {
         input[i] = getIntInput();
       }
       // call and result of maxFive via RMI (stub.maxFive)
       makeSpace(2);
       System.out.println("The Max of the given integers: " + stub.maxFive(input));
       makeSpace(1);
     }catch (Exception e) {
       System.err.println("Client exception: " + e.toString());
       e.printStackTrace();
     }
   }

   public static void callToActivityCalories(RemoteInterface stub) {
     // Method to create input object to pass to activityCalories in Server via RMI
     try{
       lineBreak();
       makeSpace(1);
       System.out.println("**** Begin activityCalories RMI ****");
       makeSpace(1);

       // input collection for activityCalories
       String[] valid = {"sit", "walk", "jog", "bike", "swim"};
       int minInput = 0;
       String activityInput = null;
       boolean badInput = true;
       Scanner scanner = new Scanner(System.in);

       while (badInput) {
         System.out.println("Please enter one of the following activities.");
         System.out.println("Options: Sit, Walk, Jog, Bike, or Swim");
         activityInput = scanner.nextLine();
         for (int i = 0; i < valid.length; i++) {
           if (activityInput.equalsIgnoreCase(valid[i])){
             badInput = false;
             break;
           }
         }
       }

       makeSpace(1);
       System.out.println("Enter a Time in Minutes for the Activity. ");
       minInput = getIntInput();
       makeSpace(1);
       // making RMI to activityCalories and printing the result
       System.out.println("Total calories burned during a " + minInput +
                "min. " + activityInput + ": " +
                stub.activityCalories(activityInput, minInput) + "cals");
     }catch (Exception e) {
       System.err.println("Client exception: " + e.toString());
       e.printStackTrace();
    }
   }

   public static void callToRideShare(RemoteInterface stub) {
     // Method to create input object to pass to rideShare in Server via RMI
     try{
       lineBreak();
       makeSpace(1);
       System.out.println("**** Begin rideShare RMI ****");
       makeSpace(1);

       int input[] = new int[8];
       String[] xy = {"X", "Y"};
       String title = "Customer";
       int driverNum = 0;
       Scanner scanner = new Scanner(System.in);

       for (int i = 0; i < 8; i++) {
         if (i > 1) {
           driverNum = (i / 2);
           title = "Driver " + driverNum;
         }
         System.out.println("Provide " + xy[(i % 2)] + " Coodinate for " + title + ".");
         input[i] = getIntInput();
         makeSpace(1);
       }
       makeSpace(1);
       // Making RMI on rideShare and printing result.
       System.out.println("Estimated time to pickup: " + stub.rideShare(input) + " minutes.");
       makeSpace(1);
     }catch (Exception e) {
       System.err.println("Client exception: " + e.toString());
       e.printStackTrace();
    }
   }
}
