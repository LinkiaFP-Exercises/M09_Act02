package Tema04;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CalculatorModel extends Remote {
	String convertToBinary(int number) throws RemoteException;

	boolean isPrime(int number) throws RemoteException;

	int calculateFactorial(int number) throws RemoteException;

	int calculateSum(int number) throws RemoteException;

	List<Integer> calculateDivisors(int number) throws RemoteException;
}
