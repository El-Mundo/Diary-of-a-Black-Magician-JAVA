package events;

import game.GameEvent;

public class AutoEvent extends GameEvent {
	
	public AutoEvent(Runnable run, int time) {
		super(run, time);
	}
	public AutoEvent(Runnable run) {
		super(run);
	}
	public boolean trigger(){
		return true;
	}
}
