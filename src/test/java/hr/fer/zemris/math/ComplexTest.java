package hr.fer.zemris.math;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexTest {

	@Test
	public void complexMultiplyTest() {

		Complex c1 = new Complex(4.34, 2.08);
		Complex c2 = new Complex(2, -5);

		assertEquals(new Complex(19.08, -17.54), c1.multiply(c2));

		c1 = new Complex(4.34, 2.08);
		c2 = new Complex(0, 0);

		assertEquals(new Complex(0, 0), c1.multiply(c2));

		c1 = new Complex(2, -1);
		c2 = new Complex(8.4, 0.1);

		assertEquals(new Complex(16.900000000000002, -8.200000000000001), c1.multiply(c2));
		
		c1 = new Complex(1,1);
		c2 = new Complex(2,2);
		Complex c3 = new Complex(3,3);
		
		assertEquals(new Complex(-12,12),c1.multiply(c2).multiply(c3));
	}

	@Test
	public void complexDivideTest() {

		Complex c1 = new Complex(4.34, 2.08);
		Complex c2 = new Complex(2, -5);

		assertEquals(new Complex(-0.05931034482758623, 0.8917241379310344), c1.divide(c2));

		c1 = new Complex(4.34, 2.08);
		c2 = new Complex(0, 0);

		assertEquals(new Complex(0, 0), c2.divide(c1));

		c1 = new Complex(2, -1);
		c2 = new Complex(8.4, 0.1);

		assertEquals(new Complex(0.23664446648717583, -0.12186481507722827), c1.divide(c2));
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

		assertEquals(4.81, Math.round(c1.module() * 100.0) / 100.0);

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

		assertEquals(new Complex(0,0), c1.negate());

		c1 = new Complex(2, -1);

		assertEquals(new Complex(-2,1), c1.negate());
	}

	@Test
	public void complexPowerTest() {

		Complex c1 = new Complex(4.34, 2.08);

		assertEquals(new Complex(14.5092,18.0544), c1.power(2));

		c1 = new Complex(0, 0);

		assertEquals(new Complex(0,0), c1.power(5));

		c1 = new Complex(2, -1);

		assertEquals(new Complex(11753,10296), c1.power(12));
		
		assertEquals(new Complex(3601.3282306973288,-5358.352815787487),new Complex(1.87765,0.88858).power(12));
	}

	@Test
	public void complexRootTest() {
		
		Complex c1 = new Complex(14.5092,18.0544);
		List<Complex> results = c1.root(2);
		assertEquals(true, results.contains(new Complex(4.34, 2.08)));

	}
	
	
	
	
}
