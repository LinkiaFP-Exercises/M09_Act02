package kute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerKute {

	public static void main(String[] args) {

		try {
			// Lista para almacenar las tareas
			ArrayList<String> taskList = new ArrayList<>();

			// Crear un socket del servidor y vincularlo a la dirección local en el puerto
			// 5678
			ServerSocket server = new ServerSocket();
			InetSocketAddress localAddress = new InetSocketAddress("localhost", 5678);
			server.bind(localAddress);

			// Esperar a que un cliente se conecte
			Socket client = server.accept();

			// Establecer flujos de entrada/salida con el cliente
			PrintStream ps = new PrintStream(client.getOutputStream(), true);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(client.getInputStream(), "UTF-8"));

			// Variable para almacenar la entrada del cliente
			String comando;

			// Enviar mensaje inicial al cliente
			ps.println("¿add, remove, count, list, end?");

			// Procesar comandos del cliente en un bucle
			while ((comando = br.readLine()) != null && !comando.equals("end")) {
				// Analizar la entrada del cliente
				String[] arrayComando = comando.split(" : ");
				String respuesta = "";

				// Realizar operaciones según el comando recibido
				switch (arrayComando[0]) {
				case "add":
					taskList.add(arrayComando[1]);
					respuesta = "Tarea añadida con éxito.";
					break;
				case "remove":
					if (taskList.remove(arrayComando[1])) {
						respuesta = "Tarea eliminada con éxito.";
					} else {
						respuesta = "La tarea no existe.";
					}
					break;
				case "list":
					int i = 1;
					for (String task : taskList) {
						respuesta += i++ + " - " + task + "\n";
					}
					break;
				case "count":
					respuesta = "Número de tareas pendientes = " + taskList.size();
					break;
				default:
					respuesta = "¡FORMATO INCORRECTO! comando : tarea";
				}

				// Enviar cada línea del mensaje de resultado al cliente
				for (String s : respuesta.split("\n")) {
					ps.println(s);
				}

				// Pedir el próximo comando al cliente
				ps.println("¿add, remove, count, list, end?");
			}

			// Cerrar el socket del cliente y del servidor
			client.close();
			server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
