package Tema04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Calculator extends UnicastRemoteObject implements CalculatorModel {

    public Calculator() throws RemoteException {
        super();
    }

    @Override
	public String convertirABinario(int numero) throws RemoteException {
		return Integer.toBinaryString(numero);
    }

    @Override
	public boolean esPrimo(int numero) throws RemoteException {
		if (numero <= 1) {
            return false;
        }
		return IntStream.rangeClosed(2, (int) Math.sqrt(numero)).noneMatch(i -> numero % i == 0);
    }

    @Override
	public int calcularFactorial(int numero) throws RemoteException {
		if (numero < 0) {
			throw new RemoteException("El factorial no está definido para números negativos.");
        }
		return IntStream.rangeClosed(2, numero).reduce(1, (acum, i) -> acum * i);
    }

    @Override
	public int calcularSuma(int numero) throws RemoteException {
		if (numero < 0) {
			throw new RemoteException("La suma no está definida para números negativos.");
        }
		return IntStream.rangeClosed(1, numero)
                .sum();
    }

    @Override
	public List<Integer> calcularDivisores(int numero) throws RemoteException {
		if (numero <= 0) {
			throw new RemoteException("Los divisores no están definidos para números no positivos.");
        }
		return IntStream.rangeClosed(1, numero).filter(i -> numero % i == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
	public String listarOperacionesDisponibles() throws RemoteException {
        return "Available operations:\n" +
                "- convertToBinary(int number)\n" +
                "- isPrime(int number)\n" +
                "- calculateFactorial(int number)\n" +
                "- calculateSum(int number)\n" +
                "- calculateDivisors(int number)";
    }


	private static final long serialVersionUID = 2819903284048121137L;
}
