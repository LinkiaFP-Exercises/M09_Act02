package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {

		try {
			inicializeVariables();

			serverResponseThread.start();

			do {demandComandAndSendToServer();} while (clientInputIsValid());

			serverResponseThread.join();

			clientSocket.close();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void inicializeVariables() throws IOException {
		addrLocal5678 = new InetSocketAddress("localhost", 5678);
		clientSocket = new Socket();
		clientSocket.connect(addrLocal5678);

		enviarAlServidor = new PrintStream(clientSocket.getOutputStream(), true);
		brClient = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		brServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));

		serverResponseThread = new Thread(() -> {
			brServer.lines().forEachOrdered(System.out::println);
		});
	}

	private static void demandComandAndSendToServer() throws IOException {
		clientInput = brClient.readLine();
		enviarAlServidor.println(clientInput);
	}

	private static boolean clientInputIsValid() throws IOException {
		return clientInput != null && !clientInput.equals("end");
	}

	private static Socket clientSocket;
	private static PrintStream enviarAlServidor;
	private static BufferedReader brClient, brServer;
	private static String clientInput;
	private static Thread serverResponseThread;
	private static InetSocketAddress addrLocal5678;
}
