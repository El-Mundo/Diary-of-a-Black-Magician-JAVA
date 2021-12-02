package player;

import java.awt.image.BufferedImage;

import sound.Sound;
import base.Sprite;
import display.Camera;
import display.Display;
import enemy.Enemy;
import enemy.EnemyBullet;
import game.Collision;

public abstract class Player extends Sprite {
	public static boolean controllable = true;
	public static Korriel korriel = new Korriel();
	protected int freezeTime;
	protected float currentSpeed;
	protected float maxSpeed = 1.7f;
	protected float directX;//谜之重名
	protected float directY;
	public Collision damageCollision;
	public int hp,invincible,opacityTime;
	protected Sound hurtSound;
	private float velocityH,velocityV,baseSpeedH,baseSpeedV,targetX, targetY;
	private final static float movingSpeed = 0.08f;
	private final static float characterSpeed = 5;
	private final static float shiftSpeed = 0.15f;
	private float rawAxisHorizon;

	public Player(int w, int h, BufferedImage[] images) {
		super(-64, -64, w, h, images);
		this.freezeTime = 0;
		this.damageCollision = new Collision(this,0,0);
		this.hp = 120;
		this.invincible = 0;
		this.opacityTime = 0;
		this.baseSpeedH = this.baseSpeedV = this.velocityH = this.velocityV = 0;
		this.rawAxisHorizon = 0;
	}
	
	public void update() {
		//基础速度(游戏相机速度)
		if(Camera.isAutoMode()) {
			this.baseSpeedH = Camera.getHSpeed();
			this.baseSpeedV = Camera.getVSpeed();
		}
		//按键施加的速度
		if(controllable) {
			this.controlling();
		}
		
		this.hSpeed = baseSpeedH + velocityH * characterSpeed;
		this.vSpeed = baseSpeedV + velocityV * characterSpeed;
		targetX = ax + hSpeed;
		targetY = ay + vSpeed;
		this.setInScreen();
		//总体更新
		if(invincible>0) {invincible--;}
		this.controlOpacity();
		this.updateRotateCenter();
		super.update();
	}
	public void shoot() {
		return;
	}
	public void controlOpacity() {
		if(opacityTime>0) {opacityTime--;}
		if(this.invincible>0) {
			if(opacityTime>=10) {
				this.opacity = 0.8f;
			}else {
				this.opacity += 0.02f;
			}
			if(opacityTime>=100) {
				this.mask.opacity-=0.05f;
				this.waggle(10,2);
			}else if(opacityTime>=80) {
				this.mask.opacity+=0.05f;
				this.hScale=Sprite.setAbsolute(hScale, 1);
				this.wScale=Sprite.setAbsolute(this.wScale, 1);
				this.t = 0;
			}else if(opacityTime>=75) {
				this.mask.opacity=1;
			}else if(opacityTime>=55) {
				this.mask.opacity-=0.05f;
			}else if(opacityTime>=35) {
				this.mask.opacity+=0.05f;
			}else if(opacityTime>=30) {
				this.mask.opacity=1;
			}else if(opacityTime>=10) {
				this.mask.opacity-=0.05f;
			}
		}else {
			this.opacity = 1;
			this.mask.opacity = 0;
		}
	}
	public void hurt(Enemy e) {
		if(invincible<=0) {
			this.hp -= e.strength;
			opacityTime = invincible = 120;
			this.mask.opacity = 1;
			this.hurtSound.play();
		}
	}
	public void hurt(EnemyBullet eb) {
		if(invincible<=0) {
			this.hp -= eb.strength;
			opacityTime = invincible = 120;
			this.mask.opacity = 1;
			this.hurtSound.play();
		}
	}
	public void setInScreen() {
		if(this.targetX<Camera.x) {
			this.ax = Camera.x;velocityH = 0;hSpeed = 0;
		}else if(this.targetX>Camera.x+860) {
			this.ax = Camera.x+860;velocityH = 0;hSpeed = 0;
		}
		if(this.targetY<Camera.y) {
			this.ay = Camera.y;velocityV = 0;vSpeed = 0;
		}else if(this.targetY>Camera.y+440) {
			this.ay = Camera.y+440;velocityV = 0;vSpeed = 0;
		}
	}
	public void controlling() {
		if(Display.up) {
			this.velocityV -= movingSpeed;
		}else if(Display.down) {
			this.velocityV += movingSpeed;
		}else {
			this.velocityV = easing(velocityV);
		}
		velocityV = maximum(velocityV,this.maxSpeed);
		
		if(Display.left) {
			this.rawAxisHorizon -= movingSpeed;
			this.velocityH -= movingSpeed;
		}else if(Display.right) {
			this.rawAxisHorizon += movingSpeed;
			this.velocityH += movingSpeed;
		}else {
			this.velocityH = easing(velocityH);
			this.rawAxisHorizon = easing(rawAxisHorizon);
		}
		velocityH = maximum(velocityH,this.maxSpeed);
		rawAxisHorizon = maximum(rawAxisHorizon, 1.3f);
		
		this.rotate = rawAxisHorizon*0.3f;
		
		if(freezeTime>0) {freezeTime--;}
		if(Display.A&&freezeTime==0&&invincible<=0) {
			this.shoot();
		}
	}
	public static float easing(float f) {
		if(f>shiftSpeed) {return f-shiftSpeed;}
		else if(f<-shiftSpeed) {return f+shiftSpeed;}
		else {return 0;}
	}
	public static float maximum(float f, float max) {
		if(f>max) {return max;}
		else if(f<-max) {return -max;}
		else {return f;}
	}
}
