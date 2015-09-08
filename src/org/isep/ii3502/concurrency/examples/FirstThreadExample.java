package org.isep.ii3502.concurrency.examples;

public class FirstThreadExample {
	public static void main(String[] args) {
		
		
		/* First thread prints out its name */
		Thread t1 = new Thread() {
			public void run() {
				System.out.println("I am thread 1");
			}
		};
		
		/* Second thread prints out its name */
		Thread t2 = new Thread() {
			public void run() {
				System.out.println("I am thread 2");
			}
		};
		
		// Threads are not running need to start them 1st
		t1.start(); // start t1 first
		t2.start(); 
		
		System.out.println("Threads started");
		
		
		// Wait for the threads to end
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

	}

}
