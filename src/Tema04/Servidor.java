package Tema04;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

	private static final Logger logger = Logger.getLogger(Servidor.class.getName());

	public static void main(String[] args) {

		Registry registry = null;

		try {
			// Crear el registro en el puerto 5556
			registry = LocateRegistry.createRegistry(5556);

			// Crear la instancia del objeto remoto
			Calculator calculator = new Calculator();

			// Publicar el objeto en el registro
			registry.rebind("calculator", calculator);

			logger.log(Level.INFO, "-- Servidor en línea --");

		} catch (RemoteException e) {
			// Manejar excepciones específicas de RemoteException
			logger.log(Level.SEVERE, "Error de Servidor: ", e);

		} catch (Exception e) {
			// Manejar otras excepciones
			logger.log(Level.SEVERE, "Error no esperado: ", e);

		} finally {
			// Cerrar el registro en caso de que algo salga mal o al finalizar
			try {
				UnicastRemoteObject.unexportObject(registry, true);
				logger.log(Level.INFO, "Registro cerrado.");
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error al cerrar el registro: ", e);
			}
		}
	}
}
