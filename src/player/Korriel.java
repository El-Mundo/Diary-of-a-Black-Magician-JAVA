package player;

import java.awt.image.BufferedImage;

import base.FileIO;
import sound.Sound;
import display.SpriteMask;
import game.Game;

public class Korriel extends Player {
	private final static BufferedImage[] i = FileIO.getImage("res/iclauncher.png", 1, 1);
	public static KorrielBullet kb = new KorrielBullet(0,0);
	
	public Korriel() {
		super(103, 76,i);
		this.flip(true);
		this.mask = new SpriteMask.AdvanceMask(this, -1);
		this.mask.setMaskingImage(FileIO.getImage("res/rainbow.png", 1, 1)[0]);
		this.hurtSound = Sound.ako_hurt;
	}
	public BufferedImage show() {
		return super.image(0);
	}
	public void shoot() {
		this.freezeTime = 10;
		Game.newBullet(kb, (int)this.ax+20, (int)this.ay+20);
	}
	public static class KorrielBullet extends Bullet {
		public KorrielBullet(int x, int y) {
			super(32, 32,i,2,false,6);
			this.flip(true);
			this.collision = new game.Collision(this, 0, 0);
			this.hSpeed=14;
		}
		public void update() {
			super.update();
		}
		public BufferedImage show() {
			return super.image(0);
		}
	}
}
