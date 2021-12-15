package hr.fer.zemris.math;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Complex {

    public static final Complex ZERO = new Complex(0,0);
    public static final Complex ONE = new Complex(1,0);
    public static final Complex ONE_NEG = new Complex(-1,0);
    public static final Complex IM = new Complex(0,1);
    public static final Complex IM_NEG = new Complex(0,-1);

    private double real;
    private double imaginary;

    public Complex() {
        real = 0;
        imaginary = 0;
    }
    public Complex(double re, double im) {
        real = re;
        imaginary = im;
    }
    
   
    public double module() {
        return Math.sqrt(real*real + imaginary*imaginary);
    }
    // returns this*c 
    public Complex multiply(Complex c) {
        return new Complex(real*c.real - imaginary*c.imaginary,real*c.imaginary+imaginary*c.real);
     }

    // returns this/c
    public Complex divide(Complex c) {
        double denominator = c.real*c.real + c.imaginary*c.imaginary;
        double resultReal = (real*c.real+imaginary*c.imaginary)/denominator;
        double resultImaginary = (imaginary*c.real-real*c.imaginary)/denominator;
        return new Complex(resultReal,resultImaginary);
     }

    // returns this+c
    public Complex add(Complex c) { 
        return new Complex(real+c.real,imaginary+c.imaginary);
    }
    // returns this-c
    public Complex sub(Complex c) { 
        return new Complex(real-c.real,imaginary-c.imaginary);
    }

    // returns -this
    public Complex negate() {
        return new Complex(-real, -imaginary);
     }
    // returns this^n, n is non-negative integer
    public Complex power(int n) {
        int count = 1;
        
        Complex result = this;
        while (count<n){
            result = result.multiply(this);
            count++;
        }

        return result;
     }

    // returns n-th root of this, n is positive integer
    public List<Complex> root(int n) {
        double radius = Math.pow(this.module(),1.0/n);
        double angle = Math.atan(imaginary/real);

        List<Complex> result = new ArrayList<Complex>();

        for (int i=0;i<12;i++) {
            double real = radius*Math.cos((angle+2*i*Math.PI)/n);
            double imaginary = radius*Math.sin((angle+2*i*Math.PI)/n);
            result.add(new Complex(real, imaginary));
        }

        return result;
        

     }

    @Override
    public String toString() {
        return imaginary<0? "("+real+imaginary+"i)" : "("+real+"+"+imaginary+"i)";
     }

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
    
