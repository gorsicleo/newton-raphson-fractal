package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexTest {

	@Test
	public void complexMultiplyTest() {

		Complex c1 = new Complex(4.34, 2.08);
		Complex c2 = new Complex(2, -5);

		assertEquals(new Complex(19.08, 17.54), c1.multiply(c2));

		c1 = new Complex(4.34, 2.08);
		c2 = new Complex(0, 0);

		assertEquals(new Complex(0, 0), c1.multiply(c2));

		c1 = new Complex(2, -1);
		c2 = new Complex(8.4, 0.1);

		assertEquals(new Complex(16.9, -8.2), c1.multiply(c2));
	}

	@Test
	public void complexDivideTest() {

		Complex c1 = new Complex(4.34, 2.08);
		Complex c2 = new Complex(2, -5);

		assertEquals(new Complex(-0.0593103, 0.891724), c1.divide(c2));

		c1 = new Complex(4.34, 2.08);
		c2 = new Complex(0, 0);

		assertEquals(new Complex(0, 0), c2.divide(c1));

		c1 = new Complex(2, -1);
		c2 = new Complex(8.4, 0.1);

		assertEquals(new Complex(0.236644, -0.121865), c1.divide(c2));
	}
	
	@Test
	public void complexAdditionTest() {

		Complex c1 = new Complex(4.34, 2.08);
		Complex c2 = new Complex(2, -5);

		assertEquals(new Complex(6.34,-2.92), c1.add(c2));

		c1 = new Complex(4.34, 2.08);
		c2 = new Complex(0, 0);

		assertEquals(new Complex(4.34, 2.08), c1.add(c2));

		c1 = new Complex(2, -1);
		c2 = new Complex(8.4, 0.1);

		assertEquals(new Complex(10.4,-0.9), c1.add(c2));
	}
	
	@Test
	public void complexSubstractionTest() {

		Complex c1 = new Complex(4.34, 2.08);
		Complex c2 = new Complex(2, -5);

		assertEquals(new Complex(2.34,7.08), c1.sub(c2));

		c1 = new Complex(4.34, 2.08);
		c2 = new Complex(0, 0);

		assertEquals(new Complex(4.34, 2.08), c1.sub(c2));

		c1 = new Complex(2, -1);
		c2 = new Complex(8.4, 0.1);

		assertEquals(new Complex(-6.4,-1.1), c1.sub(c2));
	}
	
	@Test
	public void complexModuleTest() {

		Complex c1 = new Complex(4.34, 2.08);

		assertEquals(4.812691555, c1.module());

		c1 = new Complex(0, 0);

		assertEquals(0.0, c1.module());

		c1 = new Complex(2, -1);

		assertEquals(Math.sqrt(5), c1.module());
	}
	
	@Test
	public void complexNegateTest() {

		Complex c1 = new Complex(4.34, 2.08);

		assertEquals(new Complex(-4.34,-2.08), c1.negate());

		c1 = new Complex(0, 0);

		assertEquals(0.0, c1.negate());

		c1 = new Complex(2, -1);

		assertEquals(new Complex(-2,1), c1.negate());
	}
	
	
	
	
}
