package Tema04;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorConnector {

	private final Logger logger = Logger.getLogger(CalculatorConnector.class.getName());
	private Registry registry;

	public static CalculatorConnector getInstance() {
		return new CalculatorConnector();
	}

	public CalculatorConnector getLocalConnection() {
		return connectTo("localhost", OptionalInt.empty());
	}

	public CalculatorConnector connectTo(String IP) {
		return connectTo(IP, OptionalInt.empty());
	}

	public CalculatorConnector connectTo(String IP, OptionalInt port) {
		final int PORT = port.orElse(Registry.REGISTRY_PORT);

		try {
			registry = LocateRegistry.getRegistry(IP, PORT);

		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error in connectTo(): " + e.getMessage(), e);
		}
		return this;
	}

	public void execute() {
		try {
			CalculatorManager.getInstance().getCalculator(registry).start();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in executeCalculator(): " + e.getMessage(), e);
		}
	}
}
