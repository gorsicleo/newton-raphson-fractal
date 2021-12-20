package hr.fer.zemris.math;

/**
 * Models complex polynomial in format of polynomial with factorized roots.
 * 
 * @author gorsicleo
 *
 */
public class ComplexRootedPolynomial {

	/** List of polynomial roots */
	private Complex[] roots;
	/** List of polynomial constants */
	private Complex constant;

	/** Constructs new rooted polynomial using <b>given constant</b> and roots */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.roots = roots;
		this.constant = constant;
	}

	/**
	 * computes polynomial value at given point z
	 * 
	 * @param z point for evaluation
	 * @return value of polynomial in point z
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (int i = 0; i < roots.length; i++) {
			Complex temp = z.sub(roots[i]);
			result = result.multiply(temp);
		}
		return result;
	}

	/**
	 * converts this representation to ComplexPolynomial type
	 * 
	 * @return ComplexPolynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial poly = new ComplexPolynomial(constant);

		for (int i = 0; i < roots.length; i++) {
			poly = poly.multiply(new ComplexPolynomial(roots[i].negate(), new Complex(1, 0)));
		}

		return poly;
	}

	@Override
	public String toString() {
		String poly = constant.toString() + "*";

		for (Complex root : roots) {
			poly += "(z-" + root.toString() + ")*";
		}
		return poly.substring(0, poly.length() - 1);
	}

	/**
	 * finds index of closest root for given complex number z that is
	 * within treshold 
	 * if there is no such root, returns -1 first root has index 0,
	 * second index 1, etc
	 * 
	 * @param z complex number
	 * @param treshold 
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double minValue = roots[0].sub(z).module();
		int minIndex = -1;

		for (int i = 0; i < roots.length; i++) {
			double distance = roots[i].sub(z).module();
			if (distance <= minValue) {
				minValue = distance;
				minIndex = i;
			}
		}
		return minValue <= treshold ? minIndex : -1;
	}

}
