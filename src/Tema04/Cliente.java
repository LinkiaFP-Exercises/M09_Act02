package Tema04;

/**
 * La clase Cliente representa la entrada principal de la aplicación del cliente
 * de la calculadora remota. Inicia la conexión con el servidor de la
 * calculadora y ejecuta la interfaz de usuario para realizar operaciones
 * remotas.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class Cliente {

	/**
	 * El método principal que inicia la aplicación del cliente.
	 *
	 * @param args Argumentos de la línea de comandos (no utilizado en este caso).
	 */
	public static void main(String[] args) {
		// Se utiliza la clase CalculatorConnector para obtener una conexión local a la calculadora remota
		// y ejecutar la interfaz de usuario para realizar operaciones remotas.
		CalculatorConnector.getInstance().getLocalConnection().execute();
	}
}
