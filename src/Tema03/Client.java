package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Clase que representa un cliente para interactuar con un servidor de tareas.
 * Permite enviar comandos al servidor y recibir respuestas.
 * 
 * @author <a href="https://about.me/prof.guazina">Fauno Guazina</a>
 * @since 15/10/2023
 * @version 1.1
 */
public class Client {

	public static void main(String[] args) {

		try {
			inicializeVariables();

			// Inicia un hilo para manejar las respuestas del servidor en segundo plano.
			serverResponseThread.start();

			do {demandComandAndSendToServer();} while (clientInputIsValid());

			// Espera a que el hilo de respuesta del servidor termine antes de cerrar la
			// conexión.
			serverResponseThread.join();

			// Cierra el socket del cliente.
			clientSocket.close();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void inicializeVariables() throws IOException {
	/**
	 * Inicializa las variables necesarias para la conexión y comunicación con el
	 * servidor.
	 * 
	 * @throws IOException Si hay un error de entrada/salida durante la
	 *                     inicialización.
	 */
		addrLocal5678 = new InetSocketAddress("localhost", 5678);
		clientSocket = new Socket();
		clientSocket.connect(addrLocal5678);

		enviarAlServidor = new PrintStream(clientSocket.getOutputStream(), true);
		brClient = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		brServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

		// Inicia un hilo para manejar las respuestas del servidor en segundo plano.
		serverResponseThread = new Thread(() -> {
			brServer.lines().forEachOrdered(System.out::println);
		});
	}

	private static void demandComandAndSendToServer() throws IOException {
	/**
	 * Solicita al usuario ingresar un comando y lo envía al servidor.
	 * 
	 * @throws IOException Si hay un error de entrada/salida durante la lectura del
	 *                     comando.
	 */
		clientInput = brClient.readLine();
		enviarAlServidor.println(clientInput);
	}

	private static boolean clientInputIsValid() throws IOException {
		return clientInput != null && !clientInput.equals("end");
	}

	// Variables de la clase
	private static Socket clientSocket;
	private static PrintStream enviarAlServidor;
	private static BufferedReader brClient, brServer;
	private static String clientInput;
	private static Thread serverResponseThread;
	private static InetSocketAddress addrLocal5678;
}
