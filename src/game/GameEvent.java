package game;

public abstract class GameEvent {
	protected Runnable run;
	protected int timer;
	
	public GameEvent(Runnable run){
		this.run = run;
		this.timer = -1;
	}
	public GameEvent(Runnable run, int time){
		this.run = run;
		this.timer = time;
	}
	public void run(){
		this.run.run();
	}
	public boolean trigger(){
		return false;
	}
	public void update(){
		if(this.trigger()){
			this.run();
		}
		if(timer>0){timer--;}
		if(this.timer==0){
			this.end();
		}
	}
	public void start(){
		Game.events.add(this);
	}
	public void end(){
		Game.remove(this);
	}
}
