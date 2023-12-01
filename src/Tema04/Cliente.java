package Tema04;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

	private static final String ERROR_REMOTO = "Error remoto: ";

	public static void main(String[] args) {
		try {
			Cliente cliente = new Cliente();
			cliente.ejecutarCliente();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ejecutarCliente() {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
			CalculatorModel calculator = (CalculatorModel) registry.lookup("calculator");

			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					System.out.print(
							"Escribe la operación a realizar (B)inario, (P)rimo, (F)actorial, (S)uma, (D)ivisores, (E)nd: ");
					String operacion = scanner.nextLine().toUpperCase();

					if (operacion.equals("E")) {
						break;
					}

					int numero = pideDecimal(scanner);

					ManejadorErrores manejador = new ManejadorErrores();
					manejador.ejecutar(() -> realizarOperacion(calculator, operacion, numero));
				}
			}

			System.out.println("¡Hasta luego!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void realizarOperacion(CalculatorModel calculator, String operacion, int numero) throws RemoteException {
		switch (operacion) {
		case "B":
			System.out
					.println("El número decimal " + numero + " en binario es " + calculator.convertirABinario(numero));
			break;
		case "P":
			System.out.println("El número " + numero + (calculator.esPrimo(numero) ? " es primo." : " no es primo."));
			break;
		case "F":
			System.out.println("El factorial de " + numero + " es " + calculator.calcularFactorial(numero));
			break;
		case "S":
			System.out.println("La suma de 1 hasta " + numero + " es " + calculator.calcularSuma(numero));
			break;
		case "D":
			System.out.println("Los divisores de " + numero + " son " + calculator.calcularDivisores(numero));
			break;
		default:
			System.out.println("Operación no válida.");
			break;
		}
	}

	private static int pideDecimal(Scanner scanner) {

		String userEntryValue;
		String regex = "^-?\\d+(\\.\\d+)?$";

		do {

			System.out.print("Escribe la cantidad en decimal: ");

			userEntryValue = scanner.nextLine().replace(",", ".");

		} while (!userEntryValue.matches(regex));

		return Integer.parseInt(userEntryValue);
	}

	private class ManejadorErrores {
		public void ejecutar(OperacionRemota operacion) {
			try {
				operacion.ejecutar();
			} catch (RemoteException e) {
				System.out.println(ERROR_REMOTO + obtenerMensajeErrorRemoto(e));
			}
		}
	}

	private interface OperacionRemota {
		void ejecutar() throws RemoteException;
	}

	private String obtenerMensajeErrorRemoto(Exception e) {
		String mensaje = e.getMessage();
		if (mensaje != null && mensaje.contains(CalculatorModel.ERROR_FACTORIAL)) {
			mensaje = CalculatorModel.ERROR_FACTORIAL;
		} else if (mensaje != null && mensaje.contains(CalculatorModel.ERROR_SUMA)) {
			mensaje = CalculatorModel.ERROR_SUMA;
		} else if (mensaje != null && mensaje.contains(CalculatorModel.ERROR_DIVISORES)) {
			mensaje = CalculatorModel.ERROR_DIVISORES;
		}
		return mensaje;
	}

}
