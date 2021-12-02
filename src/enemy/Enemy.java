package enemy;

import java.awt.image.BufferedImage;

import base.Sprite;
import game.Game;
import player.Bullet;

public abstract class Enemy extends Sprite {
	public EnemyCollision damageCollision;//相交判断体,种类可为矩形(type=0)或圆形(type!=0),可旋转,碰到玩家造成伤害,碰到玩家子弹敌人受伤害
	public int hp;//体力
	private int score;//每次击中得分
	protected int immune;//免疫时间决定被击中是否判断为受伤
	public int strength;//力量决定给主角造成伤害
	protected int state;//状态数值决定行为模式
	protected int timer;//计算状态更新用时间
	
	public static Enemy ee = new enemy.Enemy.test(base.FileIO.getImage("res/rainbow.png", 2, 2));
	public Enemy(BufferedImage[] images, int hp, int score, int strength) {
		super(images);
		this.hp = hp;
		this.score = score;
		this.immune = 0;
		this.strength = strength;
	}
	public void update() {
		if(this.immune>0) {
			this.immune--;
		}
		if(this.mask.opacity>0) {this.mask.opacity-=0.1f;}
		this.updateScaleCenter();
		this.synchronizeCenters(true);
		super.update();
	}
	public Enemy clone() throws CloneNotSupportedException {
		Enemy e = (Enemy) super.clone();
		EnemyCollision ec = this.damageCollision;
		e.damageCollision = new EnemyCollision(e,ec.type,ec.ax,ec.ay,ec.rigidBody.width,ec.rigidBody.height);
		return e;
	}
	public void spawn(int x, int y) {
		Game.spawn(this, x, y);
	}
	public void dispose() {
		Game.remove(this);
		Game.enemies.remove(this);
		this.damageCollision = null;
		this.mask = null;
	}
	public static class test extends Enemy {
		public static EnemyBullet eeb = new enemy.EnemyBullet.test(base.FileIO.getImage("res/rainbow.png", 1, 1));
		public test(BufferedImage[] images) {
			super(images,10,10,5);
			this.damageCollision = new EnemyCollision(this, 0);
			//this.hSpeed = -1.3f;
			this.mask = new display.SpriteMask(this,-1);
			this.mask.opacity = 1;
			timer = 88;
		}
		public void update() {
			this.timer--;
			if(this.timer==0) {					
				Game.newEBullet(enemy.EnemyBullet.mssi, ax, ay+20, -6, -6);
				Game.newEBullet(enemy.EnemyBullet.mssi, ax, ay+20, -6, 0);
				Game.newEBullet(enemy.EnemyBullet.mssi, ax, ay+20, -6, 6);timer = 88;}
			super.update();
			//this.rotate+=0.02f;
			//this.damageCollision.setRotate(this.rotate);
		}
		public BufferedImage show() {
			if(this.frame<6) {return this.image(0);}
			else if(this.frame<=12) {return this.image(1);}
			else if(this.frame<=18) {return this.image(2);}
			else if(this.frame<=24) {return this.image(3);}
			else {frame=0;return this.image(0);}
		}
	}
	public void damage(Bullet bullet) {
		if(immune<=0) {
			this.hp -= bullet.damage;
			this.immune = bullet.immune;
			Game.score += this.score;
			this.mask.opacity = 1;
			if(!bullet.penetrate) {
				bullet.dispose();
			}
			if(this.hp<=0) {
				this.defeat();
			}
		}
	}
	public void defeat() {
		this.dispose();
	}
}
