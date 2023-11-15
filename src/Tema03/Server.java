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

public class Server {

	public static void main(String[] args) {
		try {

			inicializeVariables();

			printQuestionClient();
			while ((clientOutput = brClient.readLine()) != null && isEndInValidFormat(clientOutput)) {
				if (isComandInValidFormat(clientOutput))
					processCommand(clientOutput);
				else
					printInvalidFormatMsg();
			}

			closeVariables();

		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

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

	private static void closeVariables() throws IOException {
		enviarAlCliente.println("¡DESCONECTADO DEL CONTROL DE TAREAS!");
		clientSocket.close();
		serverSocket.close();
		System.out.println("... Servidor de Tareas Offline ...");
	}

	private static void printQuestionClient() {
		enviarAlCliente.println(QUESTION_CLIENT + " " + OPTION_CLIENT);
	}

	private static void printInvalidFormatMsg() {
		enviarAlCliente.println(INVALID_FORMAT);
	}

	private static boolean isComandInValidFormat(String input) {
		return input.strip().matches(REGEX_COMAND);
	}

	private static boolean isEndInValidFormat(String input) {
		return !input.strip().matches(REGEX_END);
	}

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
	private static void processAdd(String parameter) {
		if (parameter != null) {
			taskList.add(parameter);
			enviarAlCliente.println("Tarea añadida con éxito.");
		} else {
			printInvalidFormatMsg();
		}
	}

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

	private static void processList(String parameter) {
		for (int i = 0; i < taskList.size(); i++) {
			enviarAlCliente.println((i + 1) + ". " + taskList.get(i));
		}
	}

	private static void processCount(String parameter) {
		enviarAlCliente.println("Número de tareas pendientes: " + taskList.size());
	}

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
