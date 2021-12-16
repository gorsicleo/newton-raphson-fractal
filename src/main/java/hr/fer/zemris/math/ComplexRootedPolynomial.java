package hr.fer.zemris.math;

public class ComplexRootedPolynomial {

	private Complex[] roots;
	private Complex constant;
	
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.roots = roots;
		this.constant = constant;
	}

	// computes polynomial value at given point z
	public Complex apply(Complex z) {
		Complex result = constant;
		for (int i=0;i<roots.length;i++) {
			Complex temp = z.sub(roots[i]);
			result = result.multiply(temp);
		}
		return result;
	}

	// converts this representation to ComplexPolynomial type
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial poly = new ComplexPolynomial(constant);
		
		for (int i = 0; i < roots.length; i++) {
			poly = poly.multiply(new ComplexPolynomial(roots[i].negate(),new Complex(1,0)));
		}
		
		return poly;
	}

	@Override
	public String toString() {
		String poly = constant.toString()+"*";
		
		for (Complex root : roots) {
			poly += "(z-"+root.toString()+")*";
		}
		return poly.substring(0, poly.length()-1);
	}

	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	// first root has index 0, second index 1, etc
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double minValue = Math.abs(roots[0].module()-z.module());
		int minIndex = -1;
		
		for (int i=0;i<roots.length;i++) {
			double distance = Math.abs(roots[i].module()-z.module());
			if (distance <=minValue) {
				minValue = distance;
				minIndex = i;
			}
		}
		return minValue<=treshold? minIndex : -1;
	}

}
