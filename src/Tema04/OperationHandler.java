package Tema04;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class OperationHandler {

	private final CalculatorModel calculator;
	private final Map<String, Operacion> operaciones;

	public OperationHandler(CalculatorModel calculator) {
		this.calculator = calculator;
		this.operaciones = new HashMap<>();
		inicializarOperaciones();
	}

	private void inicializarOperaciones() {
		operaciones.put("B", this::mostrarBinario);
		operaciones.put("P", this::mostrarPrimo);
		operaciones.put("F", this::mostrarFactorial);
		operaciones.put("S", this::mostrarSuma);
		operaciones.put("D", this::mostrarDivisores);
	}

	public void realizarOperacion(String operacion, int numero) throws RemoteException {
		Operacion operacionHandler = operaciones.getOrDefault(operacion, this::mostrarInvalido);
		operacionHandler.ejecutar(numero);
	}

	private interface Operacion {
		void ejecutar(int numero) throws RemoteException;
	}

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

	private void saltoDeLinea() {
		System.out.println();
	}

}
