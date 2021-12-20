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

	private static final String TRACKS_ARGUMENT_SHORT = "-t ";
	private static final String TRACKS_ARGUMENT_LONG = "--tracks=";
	private static final String WORKERS_ARGUMENT_SHORT = "-w ";
	private static final String WORKERS_ARGUMENT_LONG = "--workers=";

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

		public CalculationJob() {}
		
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

		@Override
		public void run() {
			NewtonParallel.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel, poly);
		}
		
	}

	
	
	private static class FractalProducerParallelImpl implements IFractalProducer {
		
		private static final String CALCULATION_FINISHED_MESSAGE = "Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!";
		private static final String CALCULATION_START_PRINT = "Zapocinjem izracun na kojem radi: %d dretvi, broj poslova je: %d";
		private ComplexRootedPolynomial rootedPoly;
		private int numberOfWorkers;
		private int numberOfJobs;

		public FractalProducerParallelImpl(ComplexRootedPolynomial roots, int[] args) {
			rootedPoly = roots;
			numberOfWorkers = args[0];
			numberOfJobs = args[1];
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println(String.format(CALCULATION_START_PRINT, numberOfWorkers, numberOfJobs));
			
			int iteartions = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int tracks = numberOfJobs;
			int yPerTrack = height / tracks;
			ComplexPolynomial f = rootedPoly.toComplexPolynom();

			final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();

			Thread[] workers = createAndStartWorkers(queue);

			createJobForEachWorker(reMin, reMax, imMin, imMax, width, height, cancel, iteartions, data, tracks,
					yPerTrack, queue);
			
			putToQueue(queue, workers);

			waitForAllWorkersToFinish(workers);
			
			System.out.println(CALCULATION_FINISHED_MESSAGE);

			observer.acceptResult(data, (short) (f.order() + 1), requestNo);

		}

		private void waitForAllWorkersToFinish(Thread[] workers) {
			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
		}

		private void putToQueue(final BlockingQueue<CalculationJob> queue, Thread[] workers) {
			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						queue.put(CalculationJob.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
		}

		private void createJobForEachWorker(double reMin, double reMax, double imMin, double imMax, int width,
				int height, AtomicBoolean cancel, int iteartions, short[] data, final int tracks, int yPerTrack,
				final BlockingQueue<CalculationJob> queue) {
			
			for (int i = 0; i < tracks; i++) {
				int yMin = i * yPerTrack;
				int yMax = (i + 1) * yPerTrack - 1;
				if (i == tracks - 1) {
					yMax = height - 1;
				}
				CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, iteartions,
						data, cancel, rootedPoly);
				while (true) {
					try {
						queue.put(job);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
		}

		private Thread[] createAndStartWorkers(final BlockingQueue<CalculationJob> queue) {
			
			Thread[] workers = new Thread[numberOfWorkers];
			
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							CalculationJob p = null;
							try {
								p = queue.take();
								if (p == CalculationJob.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			
			for (int i = 0; i < workers.length; i++) {
				workers[i].start();
			}
			return workers;
		}

		
	}

	public static int[] parseArgs(String[] args) {

		return new int[]{handleNumberOfWorkers(args),handleNumberOfTracks(args)};
	}

	
	private static int handleNumberOfTracks(String[] args) {

		for (int i = 0; i < args.length; i++) {
			
			if (args[i].startsWith(TRACKS_ARGUMENT_LONG)) {
				return Integer.parseInt(args[i].substring(TRACKS_ARGUMENT_LONG.length())) < 1 ? 16
						: Integer.parseInt(args[i].substring(TRACKS_ARGUMENT_LONG.length()));
			}

			if (args[i].startsWith(TRACKS_ARGUMENT_SHORT)) {
				return Integer.parseInt(args[i].substring(TRACKS_ARGUMENT_SHORT.length())) < 1 ? 16
						: Integer.parseInt(args[i].substring(TRACKS_ARGUMENT_SHORT.length()));
			}
		}
		
		
		return 4 * Runtime.getRuntime().availableProcessors();
	}

	
	private static int handleNumberOfWorkers(String[] args) {
		
		for (int i = 0; i < args.length; i++) { 
			if (args[i].startsWith(WORKERS_ARGUMENT_LONG)) {
				return Integer.parseInt(args[i].substring(WORKERS_ARGUMENT_LONG.length()));
			}

			if (args[i].startsWith(WORKERS_ARGUMENT_SHORT)) {
				return Integer.parseInt(args[i].substring(WORKERS_ARGUMENT_SHORT.length()));
			}
		}
		

		return Runtime.getRuntime().availableProcessors();
	}

	public static void main(String[] args) {
		FractalViewer.show(new FractalProducerParallelImpl(Newton.inputRoots(), parseArgs(args)));
	}

	public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int m,
			int yMin, int yMax, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rootedPoly) {

		Complex c;
		Complex zn;
		Complex znOld;
		ComplexPolynomial f = rootedPoly.toComplexPolynom();
		int offset = yMin * width;
		for (int y = yMin; y <= yMax; y++) {
			if(cancel.get()) break;
			for (int x = 0; x < width; x++) {

				c = Newton.mapToComplexPlain(x, y, 0, width, 0, height, reMin, reMax, imMin, imMax);
				zn = c;
				int iter = 0;
				do {
					znOld = zn;
					zn = zn.sub(f.apply(zn).divide(f.derive().apply(zn)));
					iter++;
				} while (iter < m && zn.sub(znOld).module() > 0.001);
				int index = rootedPoly.indexOfClosestRootFor(zn, 0.002);
				data[offset] = (short) (index + 1);
				offset++;
			}
		}

	}
}
