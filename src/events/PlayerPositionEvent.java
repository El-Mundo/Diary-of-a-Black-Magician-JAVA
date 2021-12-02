package events;

import game.GameEvent;

public class PlayerPositionEvent extends GameEvent {
	//private int hRange;
	//private int vRange;
	
	public PlayerPositionEvent(Runnable run) {
		super(run);
	}
	public boolean trigger(){
		return false;
	}

}
