package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private static ServerSocket serverSocket;
	private static ArrayList<String> taskList = new ArrayList<>();

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(5678);
			System.out.println("Servidor esperando conexiones...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());
				new ServerThread(clientSocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class ServerThread extends Thread {
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;

		public ServerThread(Socket socket) {
			this.clientSocket = socket;
			try {
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					System.out.println("Recibido: " + inputLine);
					processCommand(inputLine);
				}

				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void processCommand(String command) {
			String[] parts = command.split(" ", 2);
			String operation = parts[0].toLowerCase();

			switch (operation) {
			case "add":
				if (parts.length == 2) {
					taskList.add(parts[1]);
					out.println("Tarea añadida con éxito.");
				} else {
					out.println("Formato incorrecto para 'add'.");
				}
				break;
			case "remove":
				if (parts.length == 2) {
					if (taskList.remove(parts[1])) {
						out.println("Tarea eliminada con éxito.");
					} else {
						out.println("La tarea no existe.");
					}
				} else {
					out.println("Formato incorrecto para 'remove'.");
				}
				break;
			case "list":
				for (int i = 0; i < taskList.size(); i++) {
					out.println((i + 1) + ". " + taskList.get(i));
				}
				break;
			case "count":
				out.println("Número de tareas pendientes: " + taskList.size());
				break;
			case "end":
				out.println("Fin de la conexión.");
				break;
			default:
				out.println("Comando no reconocido.");
			}
		}
	}
}
