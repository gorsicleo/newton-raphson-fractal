package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.List;

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
		Complex[] poly = new Complex[coef.size()-1 + p.coef.size()-1+1];
		Arrays.fill(poly,new Complex());
		
		for (int i = 0; i < coef.size(); i++ ) {
			Complex multiplier = coef.get(i);
			for (int j = 0; j < p.coef.size(); j++) {
				Complex result = multiplier.multiply(p.coef.get(j));
				poly[i+j] = poly[i+j].add(result);
			}
		}
		return new ComplexPolynomial(poly);
	}

	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	public ComplexPolynomial derive() {
		Complex[] poly = new Complex[coef.size()-1];
		for (int i = 0; i < coef.size()-1; i++) {
			poly[poly.length-i-1] = coef.get(coef.size()-1-i).multiply(new Complex(coef.size()-1-i,0));
		}
		return new ComplexPolynomial(poly);
	}

	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = coef.get(0);

		for (int i=1;i<coef.size();i++) {
			Complex zPowered = z.power(coef.size()-i);
			Complex zCoef = coef.get(coef.size()-i);
			result = result.add(zPowered.multiply(zCoef));
		}
		
		
		return result;
	}

	@Override
	public String toString() {
		String result = String.format("%s*z^%d+",coef.get(coef.size()-1),coef.size()-1);

		for (int i = coef.size() - 2; i >= 0; i--) {
			result += String.format("%s*z^%d+",coef.get(i),i);
		}
		return result.substring(0, result.length()-5);
	}

}
