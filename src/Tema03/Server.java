package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Clase que representa un servidor de tareas que interactúa con un cliente.
 * Permite recibir comandos del cliente, procesarlos y enviar respuestas.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 15/10/2023
 * @version 1.1
 */
public class Server {

	public static void main(String[] args) {
		try {

			inicializeVariables();

			// Imprime la pregunta inicial al cliente
			printQuestionClient();

			// Continúa procesando comandos del cliente mientras la entrada sea válida
			while (clientInputIsValid()) {

				if (comandIsValid(clientOutput))
					processCommand(clientOutput);
				else
					printInvalidFormatMsg();
			}

			// Cierra las variables y desconecta al cliente cuando la entrada no es válida
			closeVariables();

		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa las variables necesarias para la conexión y comunicación con el
	 * cliente.
	 * 
	 * @throws IOException Si hay un error de entrada/salida durante la
	 *                     inicialización.
	 */
	private static void inicializeVariables() throws IOException {
		addrLocal5678 = new InetSocketAddress("localhost", 5678);
		serverSocket = new ServerSocket();
		serverSocket.bind(addrLocal5678);
		System.out.println("... Servidor de Tareas Waiting ...");
		clientSocket = serverSocket.accept();
		System.out.println("... Servidor de Tareas Online ...");
		enviarAlCliente = new PrintStream(clientSocket.getOutputStream(), true);
		brClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
		enviarAlCliente.println("¡CONECTADO AL CONTROL DE TAREAS!");

	}

	/**
	 * Cierra las variables y desconecta al cliente.
	 * 
	 * @throws IOException Si hay un error de entrada/salida durante el cierre.
	 */
	private static void closeVariables() throws IOException {
		enviarAlCliente.println("¡DESCONECTADO DEL CONTROL DE TAREAS!");
		clientSocket.close();
		serverSocket.close();
		System.out.println("... Servidor de Tareas Offline ...");
	}

	/**
	 * Imprime la pregunta al cliente.
	 * 
	 * @see Server#QUESTION_CLIENT
	 * @see Server#OPTION_CLIENT
	 */
	private static void printQuestionClient() {
		enviarAlCliente.println(QUESTION_CLIENT + " " + OPTION_CLIENT);
	}

	/**
	 * Verifica si la entrada del cliente es válida.
	 * 
	 * @return true si la entrada no es nula y no es igual a "end", false de lo
	 *         contrario.
	 * @throws IOException Si hay un error de entrada/salida durante la lectura de
	 *                     la entrada del cliente.
	 * 
	 * @see Server#isEndInValidFormat(String)
	 */
	private static boolean clientInputIsValid() throws IOException {
		clientOutput = brClient.readLine();
		return clientOutput != null && isEndInValidFormat(clientOutput);
	}

	/**
	 * Imprime un mensaje al cliente informándole que el formato de entrada es
	 * inválido.
	 * 
	 * @see Server#INVALID_FORMAT
	 */
	private static void printInvalidFormatMsg() {
		enviarAlCliente.println(INVALID_FORMAT);
	}

	/**
	 * Verifica si un comando es válido.
	 * 
	 * @param input El comando a verificar.
	 * @return true si el comando es válido, false de lo contrario.
	 * @see Server#REGEX_COMAND
	 */
	private static boolean comandIsValid(String input) {
		return input.strip().matches(REGEX_COMAND);
	}

	/**
	 * Verifica si el formato de finalización es válido.
	 * 
	 * @param input La entrada a verificar.
	 * @return true si el formato de finalización es válido, false de lo contrario.
	 * @see Server#REGEX_END
	 */
	private static boolean isEndInValidFormat(String input) {
		return !input.strip().matches(REGEX_END);
	}

	/**
	 * Procesa un comando recibido del cliente.
	 * 
	 * @param command El comando a procesar.
	 * @see Server#processAdd(String)
	 * @see Server#processRemove(String)
	 * @see Server#processList(String)
	 * @see Server#processCount(String)
	 */
	private static void processCommand(String command) {
		String[] parts = command.split(" - ", 2);
		String operation = parts[0].toLowerCase().strip();

		// Verificar si la operación existe en el Map
		if (operationMap.containsKey(operation)) {
			// Llamar a la función correspondiente
			operationMap.get(operation).accept(parts.length == 2 ? parts[1].strip() : null);
		} else {
			printInvalidFormatMsg();
		}

		// Volver a imprimir la pregunta al cliente
		printQuestionClient();
	}

	// Funciones específicas para cada operación

	/**
	 * Procesa la operación "add" del cliente.
	 * 
	 * @param parameter El parámetro de la operación.
	 */
	private static void processAdd(String parameter) {
		if (parameter != null) {
			taskList.add(parameter);
			enviarAlCliente.println("Tarea añadida con éxito.");
		} else {
			printInvalidFormatMsg();
		}
	}

	/**
	 * Procesa la operación "remove" del cliente.
	 * 
	 * @param parameter El parámetro de la operación.
	 */
	private static void processRemove(String parameter) {
		if (parameter != null) {
			if (taskList.remove(parameter)) {
				enviarAlCliente.println("Tarea eliminada con éxito.");
			} else {
				enviarAlCliente.println("La tarea no existe.");
			}
		} else {
			printInvalidFormatMsg();
		}
	}

	/**
	 * Procesa la operación "list" del cliente.
	 * 
	 * @param parameter El parámetro de la operación.
	 */
	private static void processList(String parameter) {
		for (int i = 0; i < taskList.size(); i++) {
			enviarAlCliente.println((i + 1) + ". " + taskList.get(i));
		}
	}

	/**
	 * Procesa la operación "count" del cliente.
	 * 
	 * @param parameter El parámetro de la operación.
	 */
	private static void processCount(String parameter) {
		enviarAlCliente.println("Número de tareas pendientes: " + taskList.size());
	}

	// Variables de la clase

	private static ServerSocket serverSocket;
	private static InetSocketAddress addrLocal5678;
	private static Socket clientSocket;
	private static ArrayList<String> taskList = new ArrayList<>();
	private static PrintStream enviarAlCliente;
	private static BufferedReader brClient;
	private static String clientOutput;
	private static final String REGEX_END = "\\b(?:end)\\b",
			REGEX_COMAND = "\\b(?:(?:add|remove)(?: - .+)?|(?:list|count))\\b",
			QUESTION_CLIENT = "¿Que operación deseas realizar?", OPTION_CLIENT = "(add, count, list, remove, end):-> ",
			INVALID_FORMAT = "Formato de entrada incorrecto. Use el formato 'orden - parámetro'.";

	private static final Map<String, Consumer<String>> operationMap = new HashMap<>();

	static {
		// Inicializar el Map con operaciones y sus funciones correspondientes
		operationMap.put("add", Server::processAdd);
		operationMap.put("remove", Server::processRemove);
		operationMap.put("list", Server::processList);
		operationMap.put("count", Server::processCount);
	}
}
