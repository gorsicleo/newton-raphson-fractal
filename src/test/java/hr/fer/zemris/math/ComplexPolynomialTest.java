package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {

	@Test
	public void creationTest() {
		ComplexPolynomial complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));

		String correct = "(4.0+4.0i)*z^3+(3.0+3.0i)*z^2+(2.0+2.0i)*z^1+(1.0+1.0i)";
		assertEquals(correct, complexPoly.toString());
	}
	
	@Test
	public void orderTest() {
		ComplexPolynomial complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));
		
		assertEquals(3, complexPoly.order());
		
		complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4), new Complex(5, 5), new Complex(6, 6), new Complex(7, 7),
				new Complex(8, 8), new Complex(9, 9));
		
		assertEquals(8, complexPoly.order());
	}
	
	@Test
	public void multiplyTest() {
		ComplexPolynomial complexPoly1 = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2));
		
		ComplexPolynomial complexPoly2 = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2));
		
		assertEquals("(0.0+8.0i)*z^2+(0.0+8.0i)*z^1+(0.0+2.0i)", complexPoly1.multiply(complexPoly2).toString());
		
		complexPoly1 = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));
		
		complexPoly2 = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2));
		
		assertEquals("(0.0+16.0i)*z^4+(0.0+20.0i)*z^3+(0.0+14.0i)*z^2+(0.0+8.0i)*z^1+(0.0+2.0i)", complexPoly1.multiply(complexPoly2).toString());
	}
	
	@Test
	public void deriveTest() {
		ComplexPolynomial complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2));
		
		assertEquals("(2.0+2.0i)", complexPoly.derive().toString());
		
		complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));
		
		
		assertEquals("(12.0+12.0i)*z^2+(6.0+6.0i)*z^1+(2.0+2.0i)", complexPoly.derive().toString());
	}
	
	@Test
	public void applyTest() {
		ComplexPolynomial complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2));
		
		assertEquals("(1.0+21.0i)", complexPoly.apply(new Complex(5,5)).toString());
		
		complexPoly = new ComplexPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));
		
		
		assertEquals("(-2149.0+171.0i)", complexPoly.apply(new Complex(5,5)).toString());
	}
	

}
