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
	private static ArrayList<String> taskList = new ArrayList<>();
	private static PrintStream enviarAlCliente;
	private static BufferedReader brClient;
	private static String clientOutput, regexEnd = "\\b(?:end)\\b",
			regexComandParameter = "\\b(?:(?:add|remove)(?: - .+)?|(?:list|count))\\b",
			QUESTION_CLIENTE = "Que operación deseas realizar (add, count, list, remove, end):->",
			inavlidFormatMsg = "Formato de entrada incorrecto. Use el formato 'orden - parámetro'.";

	public static void main(String[] args) {
		try {

			InetSocketAddress addrLocal5678 = new InetSocketAddress("localhost", 5678);
			serverSocket = new ServerSocket();
			serverSocket.bind(addrLocal5678);
			System.out.println("¡CONECTADO AL CONTROL DE TAREAS!");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());
			enviarAlCliente = new PrintStream(clientSocket.getOutputStream(), true);
			brClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

			try {

				enviarAlCliente.println(QUESTION_CLIENTE);
				while ((clientOutput = brClient.readLine()) != null && !clientOutput.equals("end")) {

					if (isEndInValidFormat(clientOutput))
						break;
					else if (isComandInValidFormat(clientOutput))
						processCommand(clientOutput);
					else
						printInvalidFormatMsg();
				}

			} catch (NullPointerException e) {
				System.out.println("FALLO TRY 02 - MAIN SERVER");
				e.printStackTrace();
			}
			clientSocket.close();
			System.out.println("¡DESCONECTADO AL CONTROL DE TAREAS!");
		} catch (IOException e) {
			System.out.println("FALLO TRY 01 - MAIN SERVER");
			e.printStackTrace();
		}

	}

	private static boolean isComandInValidFormat(String input) {
		return input.strip().matches(regexComandParameter);
	}

	private static boolean isEndInValidFormat(String input) {
		return input.strip().matches(regexEnd);
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
			enviarAlCliente.println("Número de tareas pendientes: " + taskList.size());
			break;
		}
		enviarAlCliente.println(QUESTION_CLIENTE);
	}

	private static void printInvalidFormatMsg() {
		enviarAlCliente.println(inavlidFormatMsg);
	}
}
