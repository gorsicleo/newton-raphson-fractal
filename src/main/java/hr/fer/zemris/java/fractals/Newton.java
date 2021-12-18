package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class Newton {
	
	
	public static class FractalProducerImpl implements IFractalProducer {
		private ComplexRootedPolynomial rootedPoly;
		
		public FractalProducerImpl(ComplexRootedPolynomial roots) {
			rootedPoly = roots;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			int offset = 0;
			short[] data = new short[width * height];
			Complex c;
			Complex zn;
			Complex znOld;
			ComplexPolynomial f = rootedPoly.toComplexPolynom();
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					c = mapToComplexPlain(x,y,0,width,0,height,reMin,reMax,imMin,imMax);
					zn = c;
					int iter = 0;
					do {
						znOld = zn;
						zn = zn.sub(f.apply(zn).divide(f.derive().apply(zn))); 
						iter++;
					} while(iter < m && zn.sub(znOld).module() > 0.001);
					int index = rootedPoly.indexOfClosestRootFor(zn, 0.002);
					data[offset] = (short) (index + 1);
					offset++;
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			
			observer.acceptResult(data, (short)(f.order()+1), requestNo);
			
		}

		private Complex mapToComplexPlain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin, double reMax,
				double imMin, double imMax) {
			double real = x / (xMax-1.0) * (reMax - reMin) + reMin;
			double imaginary = (yMax-1.0-y) / (yMax-1) * (imMax - imMin) + imMin;
			return new Complex(real, imaginary);
		}
		
	}

	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);
		String line;
		List<Complex> roots = new ArrayList<>();
		int count = 1;

		System.out.printf("Root %d> ", count);

		while (sc.hasNextLine()) {
			System.out.printf("Root %d> ", count);

			line = sc.nextLine();

			if (line.equalsIgnoreCase("done"))
				break;

			roots.add(parseComplex(line));
			count++;

		}
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Complex[] polynomRots = new Complex[roots.size()];
		roots.toArray(polynomRots);
		ComplexRootedPolynomial rootedPoly = new ComplexRootedPolynomial(new Complex(1,0),polynomRots);
		FractalViewer.show(new FractalProducerImpl(rootedPoly));
		sc.close();
	}

	private static Complex parseComplex(String line) {
		String[] parts = line.split("i");
		String real;
		String imaginary;
		char operator;
		
		switch (parts.length) {
		case 0:
			return new Complex(0.0,1.0);
		case 1:
			real = parts[0].trim();
			imaginary = "";
			break;
		case 2:
			real = parts[0].isEmpty()? "0.0" : parts[0].trim().substring(0, parts[0].trim().length() - parts.length + 1);
			operator = parts[0].isEmpty()? '+' : parts[0].trim().charAt(parts[0].trim().length() - 1);
			imaginary = operator + parts[1].trim().substring(0, parts[1].trim().length());
			break;
		default:
			throw new IllegalArgumentException("Parse error: please enter valid complex number format.");
		}

		if (real.isBlank() && imaginary.isBlank()) {
			throw new IllegalArgumentException(
					"Parse error: you must not drop real and imaginary part at the same time.");
		}

		try {
			double realDouble = real.isEmpty()? 0.0 : Double.parseDouble(real);
			double imaginaryDouble = imaginary.isEmpty() ? 0.0 : Double.parseDouble(imaginary);
			return new Complex(realDouble, imaginaryDouble);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Parse error: provided real and imaginary parts cannot be parsed to double.");
		}
	}
}
