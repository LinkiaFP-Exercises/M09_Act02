package Tema04;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorManager {

	private final Logger logger = Logger.getLogger(CalculatorManager.class.getName());
	private CalculatorModel calculator;

	public CalculatorModel getCalculator() {
		return this.calculator;
	}

	public static CalculatorManager getInstance() {
		return new CalculatorManager();
	}

	public CalculatorManager getCalculator(Registry registry) {
		try {
			calculator = (CalculatorModel) registry.lookup("calculator");

		} catch (RemoteException | NotBoundException e) {
			logger.log(Level.SEVERE, "Error in getCalculator(): " + e.getMessage(), e);
		}
		return this;
	}

	public void start() {
		try (Scanner scanner = new Scanner(System.in)) {

			OperationHandler operationHandler = new OperationHandler(calculator);
			AtomicReference<String> operacion = new AtomicReference<>();

			do {

				System.out.print(CalculatorModel.PREGUNTA_INICIAL);
				operacion.set(scanner.nextLine().toUpperCase());

				if (operacion.get().matches("^[BPFSD]$")) {
					ErrorManager.ejecutar(() -> 
						operationHandler.realizarOperacion(operacion.get(), pideDecimal(scanner)));
				}

			} while (!operacion.get().equals("E"));

			System.out.println("\n¡Hasta luego!\n");

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in ejecutar(): " + e.getMessage(), e);
		}
	}

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
