package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import base.Sprite;

public class Collision {
	
	protected Sprite body;
	public Rectangle rigidBody;
	public int ax, ay;
	
	public Collision(Sprite sp, int ax, int ay) {
		this.body = sp;
		this.rigidBody = new Rectangle((int)sp.ax+ax,(int)sp.ay+ay,sp.rw,sp.rh);
		this.ax = ax;
		this.ay = ay;
	}
	public Collision(Sprite sp, int ax, int ay, int rw, int rh) {
		this.body = sp;
		this.rigidBody = new Rectangle((int)sp.ax+ax,(int)sp.ay+ay,rw,rh);
		this.ax = ax;
		this.ay = ay;
	}
	public void update(Graphics2D g) {
		rigidBody.x = (int)body.ax+this.ax;
		rigidBody.y = (int)body.ay+this.ay;
	}
	public void setBody(Sprite sprite) {
		this.body = sprite;
	}
}
