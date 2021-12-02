package events;

import display.Camera;
import game.GameEvent;

public class CameraEvent extends GameEvent {
	private int startX, startY, endX, endY;
	
	public CameraEvent(Runnable run, int startX,int startY, int endX, int endY) {
		super(run);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	public boolean trigger(){
		if(Camera.x>=startX&&Camera.x<=endX) {
			if(Camera.y>=startY&&Camera.y<=endY) {
				return true;
			}
		}
		return false;
	}
}
