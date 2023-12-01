package Tema04;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

	private static final Logger logger = Logger.getLogger(Servidor.class.getName());

	public static void main(String[] args) {

		try {
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			Calculator calculator = new Calculator();

			registry.rebind("calculator", calculator);

			logger.log(Level.INFO, "-- Servidor en línea --");

		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error de Servidor: ", e);

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error no esperado: ", e);

		}
	}
}
