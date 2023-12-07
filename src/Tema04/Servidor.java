package Tema04;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase Servidor implementa el servidor RMI (Remote Method Invocation) para
 * la aplicación de la calculadora. Permite a los clientes invocar métodos
 * remotos en la instancia de Calculator para realizar operaciones matemáticas.
 *
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class Servidor {

	// Configuración del registro de log para el servidor
	private static final Logger logger = Logger.getLogger(Servidor.class.getName());

	/**
	 * El método principal que inicia el servidor RMI y registra la instancia de la
	 * calculadora para ser accedida remotamente.
	 *
	 * @param args Los argumentos de la línea de comandos (no utilizados).
	 */
	public static void main(String[] args) {

		try {
			// Crea un registro RMI en el puerto predeterminado (Registry.REGISTRY_PORT)
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			// Crea una instancia de la calculadora que implementa las operaciones remotas
			Calculator calculator = new Calculator();

			// Vincula la instancia de la calculadora al registro RMI con el nombre "calculator"
			registry.rebind("calculator", calculator);

			// Registra un mensaje informativo indicando que el servidor está en línea
			logger.log(Level.INFO, "-- Servidor en línea --");

		} catch (RemoteException e) {
			// Captura y registra cualquier excepción relacionada con la comunicación remota
			logger.log(Level.SEVERE, "Error de Servidor: ", e);

		} catch (Exception e) {
			// Captura y registra cualquier otra excepción inesperada
			logger.log(Level.SEVERE, "Error no esperado: ", e);

		}
	}
}
