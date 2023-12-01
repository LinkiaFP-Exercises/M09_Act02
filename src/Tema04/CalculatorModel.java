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

	final String PREGUNTA_INICIAL = "Escribe la operación a realizar (B)inario, (P)rimo, (F)actorial, (S)uma, (D)ivisores, (E)nd: ";
	final String ERROR_FACTORIAL = "El factorial no está definido para números negativos.";
	final String ERROR_SUMA = "La suma no está definida para números negativos.";
	final String ERROR_DIVISORES = "Los divisores no están definidos para números no positivos.";
}
