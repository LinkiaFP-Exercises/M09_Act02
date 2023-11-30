package Tema04;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		try {
			// Obtener el registro en el mismo puerto que el servidor
			Registry registry = LocateRegistry.getRegistry("localhost", 5556);

			// Obtener la instancia del objeto remoto del registro
			CalculatorModel calculator = (CalculatorModel) registry.lookup("calculator");

			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					System.out.print(
							"Escribe la operación a realizar (B)inario, (P)rimo, (F)actorial, (S)uma, (D)ivisores, (E)nd: ");
					String operacion = scanner.nextLine().toUpperCase();

					if (operacion.equals("E")) {
						// Salir del bucle si la operación es "End"
						break;
					}

					System.out.print("Escribe la cantidad en decimal: ");
					int numero = scanner.nextInt();
					scanner.nextLine(); // Consumir la nueva línea

					// Realizar la operación y mostrar el resultado
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
						System.out.println("El factorial de " + numero + " es " + calculator.calcularFactorial(numero));
						break;
					case "S":
						System.out.println("La suma de 1 hasta " + numero + " es " + calculator.calcularSuma(numero));
						break;
					case "D":
						System.out
								.println("Los divisores de " + numero + " son " + calculator.calcularDivisores(numero));
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
}
