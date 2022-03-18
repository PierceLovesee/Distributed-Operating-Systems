// Pierce Lovesee | March 17th, 2022

import java.rmi.Remote;
import java.rmi.RemoteException;

// This is the remote interface for the RMI application.
public interface RemoteInterface extends Remote {
   void connectionMessage() throws RemoteException;
   int maxFive(int[] input) throws RemoteException;
   double activityCalories(String activity, int min) throws RemoteException;
   int rideShare(int[] input) throws RemoteException;
}
