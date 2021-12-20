package hr.fer.zemris.math;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**Class that models complex number, both real and imaginary part is stored as double.
 * @author gorsicleo
 *
 */
public class Complex {

    public static final Complex ZERO = new Complex(0,0);
    public static final Complex ONE = new Complex(1,0);
    public static final Complex ONE_NEG = new Complex(-1,0);
    public static final Complex IM = new Complex(0,1);
    public static final Complex IM_NEG = new Complex(0,-1);

    private double real;
    private double imaginary;

    /**Creates complex number (0 + 0i)*/
    public Complex() {
        real = 0;
        imaginary = 0;
    }
    
    /**Creates complex number (<code>re</code> + <code>im</code>i)
     * @param re real part
     * @param im imaginary part
     * @return complex number (<code>re</code> + <code>im</code>i)
     */
    public Complex(double re, double im) {
        real = re;
        imaginary = im;
    }
    
   
    /**Returns module of complex number
     * @return module (distance of complex number from root of coordinate system)
     */
    public double module() {
        return Math.sqrt(real*real + imaginary*imaginary);
    }
    
    
    /**Multiplies two complex numbers
     * @param c another complex number for multiplication
     * @return result of multiplication
     */
    public Complex multiply(Complex c) {
        return new Complex(real*c.real - imaginary*c.imaginary,real*c.imaginary+imaginary*c.real);
     }

    
    /**Divides two complex numbers
     * @param c another complex number for division
     * @return result of division
     */
    public Complex divide(Complex c) {
        double denominator = c.real*c.real + c.imaginary*c.imaginary;
        double resultReal = (real*c.real+imaginary*c.imaginary)/denominator;
        double resultImaginary = (imaginary*c.real-real*c.imaginary)/denominator;
        return new Complex(resultReal,resultImaginary);
     }

    
    /**Adds two complex numbers
     * @param c another complex number for addition
     * @return result of addition
     */
    public Complex add(Complex c) { 
        return new Complex(real+c.real,imaginary+c.imaginary);
    }
    
    /**Subtracts two complex numbers
     * @param c another complex number for subtraction
     * @return result of subtraction
     */
    public Complex sub(Complex c) { 
        return new Complex(real-c.real,imaginary-c.imaginary);
    }

    
    /**Negates complex number
     * @return complex number in form of (-re -im i)
     */
    public Complex negate() {
        return new Complex(-real, -imaginary);
     }
    
    
    /**Multiplies complex number with itself n times
     * @param n exponent for power
     * @return result of power
     */
    public Complex power(int n) {
        int count = 1;
        
        Complex result = this;
        while (count<n){
            result = result.multiply(this);
            count++;
        }

        return result;
     }

    
    /**Returns list of n roots using Moivre's formula
     * @param n nth root number
     * @return list of n roots
     */
    public List<Complex> root(int n) {
        double radius = Math.pow(this.module(),1.0/n);
        double angle = Math.atan(imaginary/real);

        List<Complex> result = new ArrayList<Complex>();

        for (int i=0;i<n;i++) {
            double real = radius*Math.cos((angle+2*i*Math.PI)/n);
            double imaginary = radius*Math.sin((angle+2*i*Math.PI)/n);
            result.add(new Complex(real, imaginary));
        }

        return result;
        

     }

    /**Returns string representation of complex number in form of (a+bi)*/
    @Override
    public String toString() {
        return imaginary<0? "("+real+imaginary+"i)" : "("+real+"+"+imaginary+"i)";
     }

     /**Return true if and only if real and imaginary parts of two complex numbers are equal*/
    @Override
     public boolean equals(Object other) {
        if (!(other instanceof Complex)) {
            return false;
        } else {
            Complex c = (Complex) other;
            return real==c.real && imaginary==c.imaginary;
        }
     }
}
    
