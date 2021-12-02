package player;

import java.awt.image.BufferedImage;

import base.Sprite;
import game.Collision;
import game.Game;

public abstract class Bullet extends Sprite {
	public Collision collision;//碰撞体
	public boolean penetrate;//是否可穿透敌人不消失
	public int damage;//对敌伤害
	public int immune;//造成敌人无敌时间
	
	public Bullet(int x, int y, int w, int h, BufferedImage[] images) {
		super(x, y, w, h, images);
		this.collision = new Collision(this,0,0);
		this.penetrate = false;
		this.damage = 1;
		this.immune = 3;
	}
	public Bullet(int w, int h, BufferedImage[] images, int damage, boolean penetrate, int immune) {
		super(0, 0, w, h, images);
		this.collision = new Collision(this,0,0);
		this.penetrate = penetrate;
		this.damage = damage;
		this.immune = immune;
	}
	public void update() {
		super.update();
		if(!this.isInSccreen()) {
			this.dispose();
		}
	}
	public Bullet clone() throws CloneNotSupportedException {
		Bullet b = (Bullet) super.clone();
		b.collision = new Collision(b,collision.ax,collision.ay,collision.rigidBody.width,collision.rigidBody.height);
		return b;
	}
	public void dispose() {
		Game.bullets.remove(this);
		Game.remove(this);
	}
}
