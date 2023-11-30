package Tema04;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends UnicastRemoteObject implements CalculatorModel {

	public Calculator() throws RemoteException {
        // Constructor predeterminado, necesario para lanzar excepciones remotas
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
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int calculateFactorial(int number) throws RemoteException {
        if (number < 0) {
            throw new RemoteException("Factorial is not defined for negative numbers.");
        }
        int result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }

    @Override
    public int calculateSum(int number) throws RemoteException {
        if (number < 0) {
            throw new RemoteException("Sum is not defined for negative numbers.");
        }
        return (number * (number + 1)) / 2;
    }

    @Override
    public List<Integer> calculateDivisors(int number) throws RemoteException {
        if (number <= 0) {
            throw new RemoteException("Divisors are not defined for non-positive numbers.");
        }
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                divisors.add(i);
            }
        }
        return divisors;
    }

	private static final long serialVersionUID = 2819903284048121137L;
}

