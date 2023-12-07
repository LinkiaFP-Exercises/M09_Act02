package Tema04;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase OperationHandler gestiona las operaciones realizadas en la
 * calculadora remota y proporciona métodos para realizar diferentes tipos de
 * operaciones.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class OperationHandler {

	// Calculadora remota utilizada para realizar las operaciones
	private final CalculatorModel calculator;

	// Mapa que asocia las operaciones con sus respectivos manejadores
	private final Map<String, Operacion> operaciones;

	/**
	 * Constructor de OperationHandler que recibe una instancia de CalculatorModel.
	 *
	 * @param calculator La calculadora remota utilizada para realizar las
	 *                   operaciones.
	 */
	public OperationHandler(CalculatorModel calculator) {
		this.calculator = calculator;
		this.operaciones = new HashMap<>();
		inicializarOperaciones();
	}

	/**
	 * Inicializa las operaciones asociando cada operación con su respectivo manejador.
	 */
	private void inicializarOperaciones() {
		operaciones.put("B", this::mostrarBinario);
		operaciones.put("P", this::mostrarPrimo);
		operaciones.put("F", this::mostrarFactorial);
		operaciones.put("S", this::mostrarSuma);
		operaciones.put("D", this::mostrarDivisores);
	}

	/**
	 * Realiza la operación especificada en la calculadora remota con el número
	 * proporcionado. Este método actúa como un enrutador para dirigir la ejecución
	 * de operaciones específicas en la calculadora remota. Utiliza el patrón de
	 * diseño "Strategy" al emplear una interfaz funcional (Operacion) para
	 * representar las diferentes operaciones y permite cambiar dinámicamente el
	 * comportamiento en tiempo de ejecución. Si la operación proporcionada no está
	 * en el mapa, se utiliza una operación predeterminada (mostrarInvalido).
	 *
	 * @param operacion La operación a realizar.
	 * @param numero    El número sobre el cual realizar la operación.
	 * @throws RemoteException Si ocurre un error de comunicación remota durante la
	 *                         ejecución.
	 */
	public void realizarOperacion(String operacion, int numero) throws RemoteException {
		Operacion operacionHandler = operaciones.getOrDefault(operacion, this::mostrarInvalido);
		operacionHandler.ejecutar(numero);
	}

	/**
	 * Interfaz funcional que representa una operación a realizar en la calculadora remota.
	 */
	private interface Operacion {
		void ejecutar(int numero) throws RemoteException;
	}

	// Métodos privados que implementan las operaciones específicas

	private void mostrarBinario(int numero) throws RemoteException {
		System.out.println("El número decimal " + numero + " en binario es " + calculator.convertirABinario(numero));
		saltoDeLinea();
	}

	private void mostrarPrimo(int numero) throws RemoteException {
		System.out.println("El número " + numero + (calculator.esPrimo(numero) ? " es primo." : " no es primo."));
		saltoDeLinea();
	}

	private void mostrarFactorial(int numero) throws RemoteException {
		System.out.println("El factorial de " + numero + " es " + calculator.calcularFactorial(numero));
		saltoDeLinea();
	}

	private void mostrarSuma(int numero) throws RemoteException {
		System.out.println("La suma de 1 hasta " + numero + " es " + calculator.calcularSuma(numero));
		saltoDeLinea();
	}

	private void mostrarDivisores(int numero) throws RemoteException {
		System.out.println("Los divisores de " + numero + " son " + calculator.calcularDivisores(numero));
		saltoDeLinea();
	}

	private void mostrarInvalido(int numero) throws RemoteException {
		System.out.println("Operación no válida.");
		saltoDeLinea();
	}

	/**
	 * Imprime un salto de línea en la consola.
	 */
	private void saltoDeLinea() {
		System.out.println();
	}

}
