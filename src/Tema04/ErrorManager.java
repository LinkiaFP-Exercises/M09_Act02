package Tema04;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

/**
 * La clase ErrorManager proporciona métodos para manejar errores relacionados
 * con operaciones remotas.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 02/12/2023
 * @version 1.1
 */
public class ErrorManager {

    // Lista de mensajes de error relacionados con operaciones remotas
    private static final List<String> ERROR_LIST = List.of(
            CalculatorModel.ERROR_FACTORIAL,
            CalculatorModel.ERROR_SUMA,
            CalculatorModel.ERROR_DIVISORES
    );

    /**
     * Ejecuta una operación remota y maneja las excepciones RemoteException.
     *
     * @param operacion La operación remota a ejecutar.
     */
    public static void ejecutar(OperacionRemota operacion) {
        try {
            operacion.ejecutar();
        } catch (RemoteException e) {
            // Captura y maneja excepciones RemoteException, imprimiendo un mensaje de error específico.
            System.out.println("Error remoto: " + obtenerMensajeErrorRemoto(e) + "\n");
        }
    }

    /**
     * Interfaz funcional que representa una operación remota que puede lanzar una RemoteException.
     */
    public interface OperacionRemota {
        void ejecutar() throws RemoteException;
    }

	/**
	 * Obtiene el mensaje de error específico relacionado con una excepción
	 * RemoteException. Utiliza Optional para manejar posibles valores nulos en el
	 * mensaje de la excepción. Después el método map para transformar el mensaje de
	 * la excepción, al final el método get para obtener el resultado final del
	 * Optional.
	 *
	 * @param e La excepción RemoteException.
	 * @return El mensaje de error específico.
	 */
    private static String obtenerMensajeErrorRemoto(Exception e) {
        return Optional.ofNullable(e.getMessage())
                .map(msg -> ERROR_LIST.stream()
                		.filter(msg::contains).findFirst().orElse(msg))
                .get();
    }
}
