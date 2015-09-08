package org.isep.ii3502.concurrency.dinner;

public class Dinner {
	
	private final static int NB_PHILOS = 5;
	
	
	public static void main(String[] args) {
		
		Philosopher[] philos = new Philosopher[NB_PHILOS]; 
		
		philos[0] = new Philosopher(0, new Chopstick(0), new Chopstick(1));
		
		for(int i = 1; i< NB_PHILOS-1; i++)
			philos[i] = new Philosopher(i, new Chopstick(i+1), philos[i-1].left);
		
		philos[NB_PHILOS-1] = new Philosopher(NB_PHILOS-1, philos[0].right, philos[NB_PHILOS-2].left); // Ring of philosopher complete !
		
		for(Philosopher p: philos)
			p.start();
		
	}

}
