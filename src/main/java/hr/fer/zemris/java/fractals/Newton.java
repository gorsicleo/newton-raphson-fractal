package hr.fer.zemris.java.fractals;

import java.util.Scanner;

import hr.fer.zemris.math.Complex;

public class Newton {

	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		String line;
		Complex root;
		int count = 1;

		System.out.printf("Root %d> ", count);

		while (sc.hasNextLine()) {
			System.out.printf("Root %d> ", count);

			line = sc.nextLine();

			if (line.equalsIgnoreCase("done"))
				break;

			root = parseComplex(line);
			System.out.println(root);
			count++;

		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		sc.close();
	}

	private static Complex parseComplex(String line) {
		String[] parts = line.split("i");

		if (parts.length == 0) {
			return new Complex(0.0, 1.0);
		}

		if (parts.length > 2) {
			throw new IllegalArgumentException("Parse error: please enter valid complex number format.");
		}

		String real = parts[0].trim().substring(0, parts[0].trim().length() - parts.length + 1);
		String imaginary = line.contains("i")
				? parts[0].trim().charAt(parts[0].trim().length()-1)
						+ parts[1].trim().substring(0, parts[1].trim().length())
				: "0.0";

		if (real.isBlank() && imaginary.isBlank()) {
			throw new IllegalArgumentException(
					"Parse error: you must not drop real and imaginary part at the same time.");
		}

		try {
			double realDouble = real.isEmpty() ? 0.0 : Double.parseDouble(real);
			double imaginaryDouble = imaginary.isEmpty() ? 1.0 : Double.parseDouble(imaginary);
			return new Complex(realDouble, imaginaryDouble);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Parse error: provided real and imaginary parts cannot be parsed to double.");
		}
	}
}
