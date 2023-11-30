package Tema04;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CalculatorModel extends Remote {
	String convertirABinario(int number) throws RemoteException;

	boolean esPrimo(int number) throws RemoteException;

	int calcularFactorial(int number) throws RemoteException;

	int calcularSuma(int number) throws RemoteException;

	List<Integer> calcularDivisores(int number) throws RemoteException;

	public String listarOperacionesDisponibles() throws RemoteException;
}
