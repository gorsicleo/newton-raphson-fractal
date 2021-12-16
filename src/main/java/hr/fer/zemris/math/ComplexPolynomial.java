package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.ReverbType;

public class ComplexPolynomial {
	// ...
	private List<Complex> coef;

	// constructor
	public ComplexPolynomial(Complex... factors) {
		coef = List.of(factors);
	}

	// returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	public short order() {
		return (short) (coef.size() - 1);
	}

	// computes a new polynomial this*p
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] poly = new Complex[coef.size() * p.coef.size()];
		int count = 0;
		for (Complex multiplier : coef) {
			for (Complex c : p.coef) {
				poly[count] = multiplier.multiply(c);
				count++;
			}
		}

		return new ComplexPolynomial(poly);
	}

	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {...}

	// computes polynomial value at given point z
	public Complex apply(Complex z) {...}

	@Override
	public String toString() {
		String result = coef.get(coef.size() - 1).toString()+"*z^"+coef.size();

		for (int i = coef.size() - 1; i >= 0; i--) {
			result += "*"+coef.get(i).toString()+"*z^"+i;
		}
		return result;
	}

}
