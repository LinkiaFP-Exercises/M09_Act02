package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	private static Socket clientSocket;
	private static PrintStream enviarAlServidor;
	private static BufferedReader brClient, brServer;
	private static String clientImput, serverOutput;
	private static Thread serverResponseThread;

	public static void main(String[] args) {
		InetSocketAddress addrLocal5678 = new InetSocketAddress("localhost", 5678);

		try {
			clientSocket = new Socket();
			clientSocket.connect(addrLocal5678);
			enviarAlServidor = new PrintStream(clientSocket.getOutputStream(), true);
			brClient = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			brServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

			reciveFromServer().start();
			while ((clientImput = brClient.readLine()) != null && !clientImput.equals("end")) {
				enviarAlServidor.println(clientImput);
			}
			reciveFromServer().interrupt();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static Thread reciveFromServer() throws IOException {
		if (serverResponseThread == null) {
			serverResponseThread = new Thread(() -> {
				try {
					while ((serverOutput = brServer.readLine()) != null) {
						System.out.println(serverOutput);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		return serverResponseThread;
	}

}
