package Tema04;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase CalculatorConnector proporciona métodos para conectar y ejecutar el
 * servicio de la calculadora remota a través de RMI (Remote Method Invocation).
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class CalculatorConnector {

	// Configuración del registro de log para la clase CalculatorConnector
	private final Logger logger = Logger.getLogger(CalculatorConnector.class.getName());

	// Representa el registro RMI para la conexión
	private Registry registry;

	/**
	 * Obtiene el registro RMI actual.
	 *
	 * @return El objeto Registry (registro RMI) de la instancia actual.
	 */
	public Registry getRegistry() {
		return this.registry;
	}

	/**
	 * Obtiene una instancia de CalculatorConnector.
	 *
	 * @return Una nueva instancia de CalculatorConnector.
	 */
	public static CalculatorConnector getInstance() {
		return new CalculatorConnector();
	}

	/**
	 * Obtiene una conexión local al servicio de la calculadora. Es lo mismo que
	 * invocar {@code connectTo("localhost", OptionalInt.empty())}
	 *
	 * @return Una instancia de CalculatorConnector configurada para la conexión
	 *         local.
	 * 
	 * @see Registry#REGISTRY_PORT
	 * @see CalculatorConnector#connectTo(String, OptionalInt)
	 */
	public CalculatorConnector getLocalConnection() {
		return connectTo("localhost", OptionalInt.empty());
	}

	/**
	 * Conecta a la calculadora remota en la dirección IP especificada. Es lo mismo
	 * que invocar {@code connectTo(IP, OptionalInt.empty())}
	 *
	 * @param IP La dirección IP del servidor remoto.
	 * @return Una instancia de CalculatorConnector configurada para la conexión
	 *         remota.
	 * 
	 * @see Registry#REGISTRY_PORT
	 * @see CalculatorConnector#connectTo(String, OptionalInt)
	 */
	public CalculatorConnector connectTo(String IP) {
		return connectTo(IP, OptionalInt.empty());
	}

	/**
	 * Conecta a la calculadora remota en la dirección IP y puerto especificados. Si
	 * el puerto es {@code OptionalInt.empty()} el método asignará el puerto {@code
	 * Registry.REGISTRY_PORT}
	 *
	 * @param IP   La dirección IP del servidor remoto.
	 * 
	 * @param port El puerto del registro RMI del servidor remoto.
	 * @return Una instancia de CalculatorConnector configurada para la conexión
	 *         remota.
	 * 
	 * @see Registry#REGISTRY_PORT
	 */
	public CalculatorConnector connectTo(String IP, OptionalInt port) {
		final int PORT = port.orElse(Registry.REGISTRY_PORT);

		try {
			// Utiliza el método estático LocateRegistry.getRegistry para obtener el registro RMI en la dirección especificada.
			registry = LocateRegistry.getRegistry(IP, PORT);

		} catch (RemoteException e) {
			// Registra cualquier excepción relacionada con la comunicación remota.
			logger.log(Level.SEVERE, "Error in connectTo(): " + e.getMessage(), e);
		}
		return this;
	}

	/**
	 * Ejecuta el servicio de la calculadora si está disponible en el registro RMI
	 * actual.
	 */
	public void execute() {
		try {
			// Verifica si hay objetos registrados en el registro RMI actual.
			if (registry.list() != null)
				// Obtiene una instancia de CalculatorManager, obtiene la calculadora del registro y la inicia.
				CalculatorManager.getInstance().getCalculator(getRegistry()).start();

		} catch (Exception e) {
			// Registra cualquier excepción durante la ejecución del servicio de la calculadora.
			logger.log(Level.SEVERE, "Error in executeCalculator(): " + e.getMessage(), e);
		}
	}
}
