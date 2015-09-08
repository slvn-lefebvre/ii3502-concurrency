package org.isep.ii3502.concurrency.dinner;

import java.util.Random;

/**
 * A dining philosopher that has two states : Eating or thinking. He needs to
 * have two chopsticks to eat !!!
 * 
 * @author slefebvr
 *
 */
public class Philosopher extends Thread {
	public static int EAT_TIME = 500;
	public static int THINK_TIME = 500;

	public Chopstick first, second;
	private Random rand;
	private int id;

	
	public Philosopher(int id, Chopstick left, Chopstick right) {
		this.id = id;
		if(left.getId() < right.getId()) {
		this.first = left; 
		this.second = right;
		} else {
			this.first = right; 
			this.second = left;
		}
			
	
		
		this.rand = new Random(System.nanoTime());

	}

	public void run() {

		while (true) {

			int thinkFor = rand.nextInt(THINK_TIME);
			System.out.println(" Philosopher " + id + " gonna think for "
					+ thinkFor + "ms");
		
			try {
				Thread.sleep(thinkFor);
		

			int eatFor = rand.nextInt(EAT_TIME);
	
				System.out.println(" Philosopher " + 
									id + " gonna eat for "
									+ eatFor * 2 + "ms");
				
				synchronized (first) {
					Thread.sleep(eatFor);
					synchronized (second) {

						Thread.sleep(eatFor);

					}

				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
