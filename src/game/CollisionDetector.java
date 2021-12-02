package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import base.GameThread;
import player.Bullet;

public class CollisionDetector {
		public static CollisionThread cThread = new CollisionThread();
		public CollisionDetector() {
			//cThread.start();
		}
		public static synchronized void paint() {
			BufferedImage bi = new BufferedImage(1,1,BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = bi.createGraphics();
			g.setColor(new Color(120,120,120));
			g.fillRect(0, 0, 960, 540);
			
			g.setColor(Color.red);
			
			if(null!=Game.player1) {
				Game.player1.damageCollision.update(g);
			}
			if(null!=Game.player2) {
				Game.player2.damageCollision.update(g);
			}
			if(null!=Game.bullets) {
				for(int i=0;i<Game.bullets.size();i++) {
					player.Bullet b = Game.bullets.get(i);
					if(null!=b) {
						b.collision.update(g);
					}
				}
			}
			if(null!=Game.enemies) {
				for(int i=0;i<Game.enemies.size();i++) {
					enemy.Enemy e = Game.enemies.get(i);
					if(null!=e) {
						e.damageCollision.update(g);
						if(null!=Game.player1) {
							if(e.damageCollision.intersects(g, Game.player1.damageCollision)) {Game.player1.hurt(e);}
						}
						if(null!=Game.player2) {
							if(e.damageCollision.intersects(g, Game.player2.damageCollision)) {Game.player2.hurt(e);}
						}
						for(int n=0;n<Game.bullets.size();n++) {
							Bullet b = Game.bullets.get(n);
							if(b.collision!=null) {
								if(e.damageCollision.intersects(g, b.collision)) {
									e.damage(b);
									if(e.damageCollision==null) {break;}
								}
							}
						}
						g.setTransform(AffineTransform.getRotateInstance(0));
					}
				}
			}
			if(null!=Game.eBullets) {
				for(int i=0;i<Game.eBullets.size();i++) {
					enemy.EnemyBullet e = Game.eBullets.get(i);
					if(null!=e) {
						e.collision.update(g);
						if(null!=Game.player1) {
							if(e.collision.intersects(g, Game.player1.damageCollision)) {Game.player1.hurt(e);}
						}
						if(null!=Game.player2) {
							if(e.collision.intersects(g, Game.player2.damageCollision)) {Game.player2.hurt(e);}
						}
						g.setTransform(AffineTransform.getRotateInstance(0));
					}
				}
			}
			g.dispose();
			bi.flush();
		}
		public static class CollisionThread extends Thread {
			public void run() {
				while(true) {
					try {
						Thread.sleep(16);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(GameThread.isRunning) {
						CollisionDetector.paint();
					}
				}
			}
		}
}
