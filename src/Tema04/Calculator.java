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
    public String convertToBinary(int number) throws RemoteException {
        return Integer.toBinaryString(number);
    }

    @Override
    public boolean isPrime(int number) throws RemoteException {
        if (number <= 1) {
            return false;
        }
        return IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(i -> number % i == 0);
    }

    @Override
    public int calculateFactorial(int number) throws RemoteException {
        if (number < 0) {
            throw new RemoteException("Factorial is not defined for negative numbers.");
        }
        return IntStream.rangeClosed(2, number)
                .reduce(1, (acc, i) -> acc * i);
    }

    @Override
    public int calculateSum(int number) throws RemoteException {
        if (number < 0) {
            throw new RemoteException("Sum is not defined for negative numbers.");
        }
        return IntStream.rangeClosed(1, number)
                .sum();
    }

    @Override
    public List<Integer> calculateDivisors(int number) throws RemoteException {
        if (number <= 0) {
            throw new RemoteException("Divisors are not defined for non-positive numbers.");
        }
        return IntStream.rangeClosed(1, number)
                .filter(i -> number % i == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public String listAvailableOperations() throws RemoteException {
        return "Available operations:\n" +
                "- convertToBinary(int number)\n" +
                "- isPrime(int number)\n" +
                "- calculateFactorial(int number)\n" +
                "- calculateSum(int number)\n" +
                "- calculateDivisors(int number)";
    }

	private static final long serialVersionUID = 2819903284048121137L;
}
