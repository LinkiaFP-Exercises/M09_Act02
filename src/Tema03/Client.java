package Tema03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private static Socket clientSocket;
	private static PrintWriter out;
	private static BufferedReader in;

	public static void main(String[] args) {
		try {
			clientSocket = new Socket("localhost", 5678);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String userInput;

			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				String serverResponse = in.readLine();
				System.out.println("Servidor: " + serverResponse);

				if ("Fin de la conexión.".equals(serverResponse)) {
					break;
				}
			}

			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
