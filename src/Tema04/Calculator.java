package Tema04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * La clase Calculator implementa la interfaz CalculatorModel y proporciona la
 * lógica para realizar diversas operaciones matemáticas de manera remota a
 * través de RMI (Remote Method Invocation).
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class Calculator extends UnicastRemoteObject implements CalculatorModel {

	/**
	 * Constructor de la clase Calculator.
	 *
	 * @throws RemoteException Si ocurre un error durante la configuración de RMI.
	 */
	public Calculator() throws RemoteException {
		super();
	}

	/**
	 * Convierte un número a su representación en binario. Utiliza el método
	 * toBinaryString de la clase Integer para realizar la conversión a binario.
	 *
	 * @param numero El número a convertir.
	 * @return La representación en binario del número.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	@Override
	public String convertirABinario(int numero) throws RemoteException {
		return Integer.toBinaryString(numero);
	}

	/**
	 * Verifica si un número es primo. Utiliza un stream para generar los números
	 * desde 2 hasta la raíz cuadrada del número dado. Luego, verifica si alguno de
	 * estos números divide exactamente al número dado.
	 *
	 * @param numero El número a verificar.
	 * @return true si el número es primo, false de lo contrario.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	@Override
	public boolean esPrimo(int numero) throws RemoteException {
		if (numero <= 1) {
			return false;
		}
		return IntStream.rangeClosed(2, (int) Math.sqrt(numero)).noneMatch(i -> numero % i == 0);
	}

	/**
	 * Calcula el factorial de un número. Utiliza un stream para generar los números
	 * desde 2 hasta el número dado. Luego, utiliza el método reduce para
	 * multiplicar todos los números en el rango.
	 *
	 * @param numero El número para calcular el factorial.
	 * @return El resultado del cálculo factorial.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	@Override
	public int calcularFactorial(int numero) throws RemoteException {
		if (numero < 0) {
			throw new RemoteException(ERROR_FACTORIAL);
		}
		return IntStream.rangeClosed(2, numero).reduce(1, (acum, i) -> acum * i);
	}

	/**
	 * Calcula la suma de los números desde 1 hasta el número dado. Utiliza un
	 * stream para generar los números desde 1 hasta el número dado. Luego, utiliza
	 * el método sum para obtener la suma de los números en el rango.
	 *
	 * @param numero El número hasta el cual sumar.
	 * @return La suma de los números.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	@Override
	public int calcularSuma(int numero) throws RemoteException {
		if (numero < 0) {
			throw new RemoteException(ERROR_SUMA);
		}
		return IntStream.rangeClosed(1, numero).sum();
	}

	/**
	 * Calcula los divisores de un número. Utiliza un stream para generar los
	 * números desde 1 hasta el número dado. Luego, utiliza el método filter para
	 * seleccionar solo los números que son divisores de 'numero'. Finalmente,
	 * utiliza el método collect para almacenar los resultados en una lista.
	 *
	 * @param numero El número para calcular sus divisores.
	 * @return Una lista de divisores del número.
	 * @throws RemoteException Si ocurre un error durante la comunicación remota.
	 */
	@Override
	public List<Integer> calcularDivisores(int numero) throws RemoteException {
		if (numero <= 0) {
			throw new RemoteException(ERROR_DIVISORES);
		}
		return IntStream.rangeClosed(1, numero).filter(i -> numero % i == 0).boxed().collect(Collectors.toList());
	}

	// SerialVersionUID para garantizar la consistencia durante la serialización
	private static final long serialVersionUID = 2819903284048121137L;
}
