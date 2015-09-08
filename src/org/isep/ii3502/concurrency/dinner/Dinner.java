package org.isep.ii3502.concurrency.dinner;

public class Dinner {
	
	private final static int NB_PHILOS = 5;
	
	
	public static void main(String[] args) {
		
		Philosopher[] philos = new Philosopher[NB_PHILOS]; 
		
		Chopstick [] chopsticks = new Chopstick[NB_PHILOS];
		for(int i= 0; i<NB_PHILOS;i++) {
			chopsticks[i] = new Chopstick(i);
		}
		
		philos[0] = new Philosopher(0, chopsticks[0], chopsticks[1]);
		
		
		for(int i = 1; i< NB_PHILOS-1; i++)
			philos[i] = new Philosopher(i, chopsticks[i], chopsticks[i-1]);
		
		philos[NB_PHILOS-1] = new Philosopher(NB_PHILOS-1, chopsticks[0], chopsticks[NB_PHILOS-1]); // Ring of philosopher complete !
		
		for(Philosopher p: philos)
			p.start();
		
	}

}
