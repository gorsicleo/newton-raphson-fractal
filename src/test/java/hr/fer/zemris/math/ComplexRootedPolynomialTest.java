package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void creationTest() {
		ComplexRootedPolynomial rootedPoly = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4), new Complex(5, 5), new Complex(6, 6), new Complex(7, 7),
				new Complex(8, 8), new Complex(9, 9));

		String correct = "(1.0+1.0i)*(z-(2.0+2.0i))*(z-(3.0+3.0i))*(z-(4.0+4.0i))*(z-(5.0+5.0i))*(z-(6.0+6.0i))*"
				+ "(z-(7.0+7.0i))*(z-(8.0+8.0i))*(z-(9.0+9.0i))";
		assertEquals(correct, rootedPoly.toString());
	}
	
	@Test
	public void indexOfClosestRootForTest() {
		ComplexRootedPolynomial rootedPoly = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4), new Complex(5, 5), new Complex(6, 6), new Complex(7, 7),
				new Complex(8, 8), new Complex(9, 9));
		
		assertEquals(7, rootedPoly.indexOfClosestRootFor(new Complex(10,10), 5.0));
		assertEquals(0, rootedPoly.indexOfClosestRootFor(new Complex(0,0), 2.83));
		assertEquals(-1, rootedPoly.indexOfClosestRootFor(new Complex(5,6), 0.1));
		assertEquals(2, rootedPoly.indexOfClosestRootFor(new Complex(4.3,4.5), 1.0));
	}
	
	@Test
	public void applyTest() {
		ComplexRootedPolynomial rootedPoly = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4), new Complex(5, 5), new Complex(6, 6), new Complex(7, 7),
				new Complex(8, 8), new Complex(9, 9));
		assertEquals(new Complex(5806080,5806080),rootedPoly.apply(new Complex()));
		
		rootedPoly =  new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));
		assertEquals(new Complex(96,0),rootedPoly.apply(new Complex()));
	}
	
	@Test
	public void toComplexPolynomTest() {
		ComplexRootedPolynomial rootedPoly =  new ComplexRootedPolynomial(new Complex(1, 1), new Complex(2, 2),
				new Complex(3, 3), new Complex(4, 4));
		String result = "(1.0+1.0i)*z^3+(0.0-18.0i)*z^2+(-52.0+52.0i)*z^1+(96.0+0.0i)";
		assertEquals(result, rootedPoly.toComplexPolynom().toString());
	}

}
