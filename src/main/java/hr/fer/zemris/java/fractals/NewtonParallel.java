package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {
	
	public static class CalculationJob implements Runnable {

		public static CalculationJob NO_JOB = new CalculationJob();
		private double reMin;
		private double reMax;
		private double imMin;
		private double imMax;
		private int width;
		private int height;
		private int yMin;
		private int yMax;
		private int m;
		private short[] data;
		private AtomicBoolean cancel;
		private ComplexRootedPolynomial poly;

		public CalculationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial poly) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.poly = poly;
		}

		public CalculationJob() {
		}

		@Override
		public void run() {
			
			NewtonParallel.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel, poly);
		}
		
	}

	private static class FractalProducerParallelImpl implements IFractalProducer {
		private ComplexRootedPolynomial rootedPoly;
		
		public FractalProducerParallelImpl(ComplexRootedPolynomial roots) {
			rootedPoly = roots;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int brojTraka = 16;
			int brojYPoTraci = height / brojTraka;
			ComplexPolynomial f = rootedPoly.toComplexPolynom();
			
			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();
			
			Thread[] radnici = new Thread[2];
			for(int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if(p==CalculationJob.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}
			
			for(int i = 0; i < brojTraka; i++) {
				int yMin = i*brojYPoTraci;
				int yMax = (i+1)*brojYPoTraci-1;
				if(i==brojTraka-1) {
					yMax = height-1;
				}
				CalculationJob posao = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel,rootedPoly);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						queue.put(CalculationJob.NO_JOB );
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < radnici.length; i++) {
				while(true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			
			observer.acceptResult(data, (short)(f.order()+1), requestNo);
			
		}

		
	}
	
	public static void main(String[] args) {
		FractalViewer.show(new FractalProducerParallelImpl(Newton.inputRoots()));
	}

	public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int m,
			int yMin, int yMax, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rootedPoly) {
		
		Complex c;
		Complex zn;
		Complex znOld;
		ComplexPolynomial f = rootedPoly.toComplexPolynom();
		int offset = yMin * width;
		for(int y = yMin; y <= yMax && 
			      !cancel.get(); y++) {
			//if(cancel.get()) break;
			for(int x = 0; x < width; x++) {
				
				c = Newton.mapToComplexPlain(x,y,0,width,0,height,reMin,reMax,imMin,imMax);
				zn = c;
				int iter = 0;
				do {
					znOld = zn;
					zn = zn.sub(f.apply(zn).divide(f.derive().apply(zn))); 
					iter++;
				} while(iter < m && zn.sub(znOld).module() > 0.001);
				int index = rootedPoly.indexOfClosestRootFor(zn, 0.002);
				data[offset] = (short) (index + 1);
				offset++;
			}
		}
		
	}
}
