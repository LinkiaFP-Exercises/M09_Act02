package Tema04;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * La interfaz CalculatorModel define los métodos remotos que pueden ser
 * invocados por clientes a través de RMI para realizar operaciones matemáticas
 * en un servidor remoto.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public interface CalculatorModel extends Remote {

	/**
	 * Convierte un número a su representación en binario.
	 *
	 * @param number El número a convertir.
	 * @return La representación en binario del número.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	String convertirABinario(int number) throws RemoteException;

	/**
	 * Verifica si un número es primo.
	 *
	 * @param number El número a verificar.
	 * @return true si el número es primo, false de lo contrario.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	boolean esPrimo(int number) throws RemoteException;

	/**
	 * Calcula el factorial de un número.
	 *
	 * @param number El número para calcular el factorial.
	 * @return El resultado del cálculo factorial.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	int calcularFactorial(int number) throws RemoteException;

	/**
	 * Calcula la suma de los números desde 1 hasta el número dado.
	 *
	 * @param number El número hasta el cual sumar.
	 * @return La suma de los números.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	int calcularSuma(int number) throws RemoteException;

	/**
	 * Calcula los divisores de un número.
	 *
	 * @param number El número para calcular sus divisores.
	 * @return Una lista de divisores del número.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	List<Integer> calcularDivisores(int number) throws RemoteException;

	// Constantes utilizadas para mensajes predefinidos
	final String PREGUNTA_INICIAL = "Escribe la operación a realizar (B)inario, (P)rimo, (F)actorial, (S)uma, (D)ivisores, (E)nd: ";
	final String ERROR_FACTORIAL = "El factorial no está definido para números negativos.";
	final String ERROR_SUMA = "La suma no está definida para números negativos.";
	final String ERROR_DIVISORES = "Los divisores no están definidos para números no positivos.";
}
