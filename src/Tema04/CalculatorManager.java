package Tema04;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase CalculatorManager proporciona métodos para gestionar la conexión con
 * el servicio de la calculadora remota y manejar las operaciones solicitadas
 * por el usuario.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class CalculatorManager {

	// Configuración del registro de log para la clase CalculatorManager
	private final Logger logger = Logger.getLogger(CalculatorManager.class.getName());

	// Representa la instancia de la calculadora remota
	private CalculatorModel calculator;

	/**
	 * Obtiene la instancia actual de la calculadora remota.
	 *
	 * @return La instancia de CalculatorModel.
	 */
	public CalculatorModel getCalculator() {
		return this.calculator;
	}

	/**
	 * Obtiene una instancia de CalculatorManager.
	 *
	 * @return Una nueva instancia de CalculatorManager.
	 */
	public static CalculatorManager getInstance() {
		return new CalculatorManager();
	}

	/**
	 * Obtiene la instancia de la calculadora remota desde el registro RMI
	 * proporcionado.
	 *
	 * @param registry El registro RMI que contiene la calculadora remota.
	 * @return La instancia de CalculatorManager configurada con la calculadora
	 *         remota.
	 */
	public CalculatorManager getCalculator(Registry registry) {
		try {
			// Utiliza el método lookup del registro RMI para obtener la instancia de la calculadora.
			calculator = (CalculatorModel) registry.lookup("calculator");

		} catch (RemoteException | NotBoundException e) {
			// Registra cualquier excepción relacionada con la conexión a la calculadora remota.
			logger.log(Level.SEVERE, "Error in getCalculator(): " + e.getMessage(), e);
		}
		return this;
	}

	/**
	 * Es el punto de entrada de la aplicación de la calculadora remota, donde se
	 * interactúa con el usuario para realizar operaciones hasta que decide salir.
	 * Además, se utiliza el manejo de errores centralizado mediante ErrorManager
	 * para gestionar excepciones durante la ejecución.
	 */
	public void start() {
		try (Scanner scanner = new Scanner(System.in)) {

			// Crea una instancia de OperationHandler para manejar las operaciones.
			OperationHandler operationHandler = new OperationHandler(calculator);
			// Utiliza un AtomicReference para almacenar la operación introducida por el usuario.
			AtomicReference<String> operacion = new AtomicReference<>();

			do {
				System.out.print(CalculatorModel.PREGUNTA_INICIAL);
				operacion.set(scanner.nextLine().toUpperCase());

				// Verifica si la operación introducida por el usuario es válida y realiza la operación correspondiente.
				if (operacion.get().matches("^[BPFSD]$")) {
					ErrorManager // "Manejo de Errores o Excepciones Funcionales"
							.ejecutar(() -> operationHandler.realizarOperacion(operacion.get(), pideDecimal(scanner)));
				}

			} while (!operacion.get().equals("E"));

			System.out.println("\n¡Hasta luego!\n");

		} catch (Exception e) {
			// Registra cualquier excepción durante la ejecución de la aplicación de la calculadora.
			logger.log(Level.SEVERE, "Error in start(): " + e.getMessage(), e);
		}
	}

	/**
	 * Solicita al usuario que introduzca un número decimal y devuelve su valor
	 * entero.
	 *
	 * @param scanner El objeto Scanner para la entrada del usuario.
	 * @return El valor entero introducido por el usuario.
	 */
	private int pideDecimal(Scanner scanner) {
		String userEntryValue;
		String regex = "^-?\\d+$";

		do {
			System.out.print("Escribe la cantidad en decimal: ");
			userEntryValue = scanner.nextLine().replace(",", ".");

		} while (!userEntryValue.matches(regex));

		return Integer.parseInt(userEntryValue);
	}
}
