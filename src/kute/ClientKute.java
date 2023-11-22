package kute;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientKute {
	public static void main(String[] args) {
		try {
			// Crear un socket del cliente
			Socket socket = new Socket();

			// Conectar el socket a la dirección local en el puerto 5678
			InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 5678);
			socket.connect(inetSocketAddress);

			// Establecer flujos de entrada/salida con el servidor y el cliente
			PrintStream ps = new PrintStream(socket.getOutputStream(), true);
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			BufferedReader inputFromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "UTF-8"));

			// Variables para almacenar la entrada del cliente y la salida del servidor
			String client;
			String server;

			// Imprimir el mensaje inicial del servidor
			System.out.println(inputFromServer.readLine());

			// Leer comandos del usuario y enviarlos al servidor
			while ((client = inputFromClient.readLine()) != null && !client.equals("end")) {
				ps.println(client);

				// Leer y mostrar la respuesta del servidor hasta que se reciba el mensaje de
				// solicitud del próximo comando
				do {
					server = inputFromServer.readLine();
					System.out.println(server);
				} while (server != null && !server.equals("¿add, remove, count, list, end?"));
			}

			// Cerrar el socket del cliente
			socket.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
