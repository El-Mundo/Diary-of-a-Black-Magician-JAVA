package base;

import display.Display;
import game.Game;

public class GameThread extends Thread {
	private Display display;
	public static boolean isRunning = true;
	public GameThread(Display display) {
		this.display = display;
	}
	
	public void run() {
		while(true) {
			synchronized(this){
				if(isRunning) {
					Game.upadate();
					System.out.println(Game.sprites.size());
					game.CollisionDetector.paint();
				}
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(isRunning) {
					this.display.repaint();
				}
			}
		}
	}

}
