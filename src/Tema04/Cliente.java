package Tema04;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		try {

			Registry registry = LocateRegistry.getRegistry("localhost", 5678);

			CalculatorModel calculator = (CalculatorModel) registry.lookup("calculator");

			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					System.out.print(
							"Escribe la operación a realizar (B)inario, (P)rimo, (F)actorial, (S)uma, (D)ivisores, (E)nd: ");
					String operacion = scanner.nextLine().toUpperCase();

					if (operacion.equals("E")) {
						break;
					}

					System.out.print("Escribe la cantidad en decimal: ");
					int numero = scanner.nextInt();
					scanner.nextLine();

					switch (operacion) {
					case "B":
						System.out.println("El número decimal " + numero + " en binario es "
								+ calculator.convertirABinario(numero));
						break;
					case "P":
						System.out.println(
								"El número " + numero + (calculator.esPrimo(numero) ? " es primo." : " no es primo."));
						break;
					case "F":
						try {
							System.out.println(
									"El factorial de " + numero + " es " + calculator.calcularFactorial(numero));
						} catch (Exception e) {
							System.out.println(ERROR_REMOTO + obtenerMensajeErrorRemoto(e));
						}
						break;
					case "S":
						try {
							System.out
									.println("La suma de 1 hasta " + numero + " es " + calculator.calcularSuma(numero));
						} catch (Exception e) {
							System.out.println(ERROR_REMOTO + obtenerMensajeErrorRemoto(e));
						}
						break;
					case "D":
						try {
							System.out.println(
									"Los divisores de " + numero + " son " + calculator.calcularDivisores(numero));
						} catch (Exception e) {
							System.out.println(ERROR_REMOTO + obtenerMensajeErrorRemoto(e));
						}
						break;
					default:
						System.out.println("Operación no válida.");
						break;
					}
				}
			}

			System.out.println("¡Hasta luego!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String obtenerMensajeErrorRemoto(Exception e) {
		String mensaje = e.getMessage();
		if (mensaje != null && mensaje.contains(CalculatorModel.FACTORIAL_ERROR)) {
			mensaje = CalculatorModel.FACTORIAL_ERROR;
		} else if (mensaje != null && mensaje.contains(CalculatorModel.SUMA_ERROR)) {
			mensaje = CalculatorModel.SUMA_ERROR;
		} else if (mensaje != null && mensaje.contains(CalculatorModel.DIVISORES_ERROR)) {
			mensaje = CalculatorModel.DIVISORES_ERROR;
		}
		return mensaje;
	}

	private static final String ERROR_REMOTO = "Error remoto: ";

}
