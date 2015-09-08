package org.isep.ii3502.concurrency.examples;

public class NaiveCounter extends Thread {
	
	// shared counter variable
	static class Counter {
			int value = 0;
			// increase the value of the static counter
			public  void increment() {
				value++;
			}
		}
	
	private Counter counter;
	private int nbIncrements; 
	
	public NaiveCounter(Counter counter, int nbIncrements) {
		this.counter = counter;
		this.nbIncrements = nbIncrements;
	}
	
	
	
	// increments counter nbIncrement times
	public void run() {
		
		while(nbIncrements-- !=0) {
			counter.increment();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {

		
		
		int nbThreads    =5; // We'll create nbThreads threads  
		int nbIncrements = 1000;  // Each will increment the counter variable nbIncrement times 
		
		// Create a thread array for running nbThreads in parallel
		NaiveCounter[] counters  = new NaiveCounter[nbThreads];
		
		Counter counter = new Counter();
		
		// create and start threads 
		for(int i = 0; i < nbThreads; i++) {
			counters[i] = new NaiveCounter(counter,nbIncrements);
			counters[i].start();
		}
		
		// Wait for all nbThreads to end
		for(int i = 0; i < nbThreads; i++) {
			counters[i].join();
			System.out.println(counters[i].getState());
		}
		
		System.out.println("\\o/ We counted up to nbThread * nbIncrement in parallel \\o/");
		System.out.println("Result : " + counter.value);
		System.out.println("Wait,,,,, WTF ??????");
	}
}
