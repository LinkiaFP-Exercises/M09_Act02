package Tema04;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public class ErrorManager {

	private static final List<String> ERROR_LIST = List.of(
            CalculatorModel.ERROR_FACTORIAL,
            CalculatorModel.ERROR_SUMA,
            CalculatorModel.ERROR_DIVISORES
    );

	public static void ejecutar(OperacionRemota operacion) {
		try {
			operacion.ejecutar();
		} catch (RemoteException e) {
			System.out.println("Error remoto: " + obtenerMensajeErrorRemoto(e) + "\n");
		}
	}

	public interface OperacionRemota {
		void ejecutar() throws RemoteException;
	}

	private static String obtenerMensajeErrorRemoto(Exception e) {

		return Optional.ofNullable(e.getMessage())
				.map(msg -> ERROR_LIST.stream().filter(msg::contains).findFirst().orElse(msg)).get();
	}

}
