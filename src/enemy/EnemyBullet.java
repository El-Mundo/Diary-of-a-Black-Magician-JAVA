package enemy;

import java.awt.image.BufferedImage;

import base.Sprite;
import game.Game;

public abstract class EnemyBullet extends Sprite {
	public EnemyCollision collision;//碰撞体,同敌人
	protected int state;//行为状态数
	protected int timer;//计时器计算状态
	public int strength;
	
	public static Missile mssi = new Missile();
	
	public EnemyBullet(BufferedImage[] images,int strength) {
		super(images);
		this.strength = strength;
		this.collision = new EnemyCollision(this, 1);
	} 
	public EnemyBullet clone() throws CloneNotSupportedException {
		EnemyBullet b = (EnemyBullet) super.clone();
		EnemyCollision ec = this.collision;
		b.collision = new EnemyCollision(b,ec.type,ec.ax,ec.ay,ec.rigidBody.width,ec.rigidBody.height);
		return b;
	}
	public void update() {
		this.updateRotateCenter();
		super.update();
		if(!this.isInSccreen()) {
			this.dispose();
		}
	}
	public void dispose() {
		Game.eBullets.remove(this);
		Game.remove(this);
	}
	public static class test extends EnemyBullet{
		public test(BufferedImage[] images) {
			super(images, 5);
			this.hSpeed = -2;
		}
		
	}
	public static class Missile extends EnemyBullet {
		private static final BufferedImage[] images = base.FileIO.getImage("res/Enemy/Bullets/test-mssi.png", 1, 3);
		public Missile() {
			super(images, 5);
		}
		public BufferedImage show() {
			if(frame<6) {return super.image(0);}
			else if(frame<=12) {return super.image(1);}
			else if(frame<=18) {return super.image(2);}
			else if(frame<=12) {return super.image(1);}
			else {frame=0;return super.image(0);}
		}
		public void update() {
			this.hSpeed-=0.1f;
			//if(Game.player1.ay+48>=this.ay+24) {this.vSpeed+=0.08f;}else {this.vSpeed-=0.08f;}
			this.vSpeed+=(Game.player1.ay-this.ay)*0.001f;
			this.rotate = (float) Math.atan(this.vSpeed/this.hSpeed);
			this.collision.setRotate(this.rotate);
			super.update();
		}
	}
}
