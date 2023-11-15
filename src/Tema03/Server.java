package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
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
		System.out.println("... Servidor a la Espera ...");
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
		String operation = parts[0].toLowerCase();

		switch (operation) {
		case "add":
			if (parts.length == 2) {
				taskList.add(parts[1]);
				enviarAlCliente.println("Tarea añadida con éxito.");
			} else {
				printInvalidFormatMsg();
			}
			break;
		case "remove":
			if (parts.length == 2) {
				if (taskList.remove(parts[1])) {
					enviarAlCliente.println("Tarea eliminada con éxito.");
				} else {
					enviarAlCliente.println("La tarea no existe.");
				}
			} else {
				printInvalidFormatMsg();
			}
			break;
		case "list":
			for (int i = 0; i < taskList.size(); i++) {
				enviarAlCliente.println((i + 1) + ". " + taskList.get(i));
			}
			break;
		case "count":
			break;
		}
		printQuestionClient();
	}
}
