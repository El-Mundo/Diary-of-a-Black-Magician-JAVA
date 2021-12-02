package game;

import java.util.ArrayList;

import sound.Sound;
import base.Sprite;
import base.Tile;
import display.Camera;
import enemy.Enemy;
import enemy.EnemyBullet;
import player.Bullet;
import player.Player;

public class Game {
	public static ArrayList<Tile> background = new ArrayList<Tile>();
	public static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	public static ArrayList<Tile> foreground = new ArrayList<Tile>();
	public static ArrayList<GameEvent> events = new ArrayList<GameEvent>();
	
	public static ArrayList<Sprite> spritesToDispose = new ArrayList<Sprite>();
	public static ArrayList<GameEvent> eventsToDispose = new ArrayList<GameEvent>();
	
	public static player.Player player1;
	public static player.Player player2;
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static ArrayList<EnemyBullet> eBullets = new ArrayList<EnemyBullet>();
	public static int score = 0;
	
	public static void upadate() {
		Camera.update();
		
		for(int i=0;i<eventsToDispose.size();i++) {
			events.remove(eventsToDispose.get(i));
		}
		for(int i=0;i<spritesToDispose.size();i++) {
			sprites.remove(spritesToDispose.get(i));
		}
		
		for(int i=0;i<events.size();i++) {
			events.get(i).update();
		}
		for(int i=0;i<sprites.size();i++) {
			sprites.get(i).update();
		}
	}
	public static void newBackground(Tile tile, int x, int y) {
		Tile t = null;
		try {
			t = tile.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(t!=null) {
			t.x = x;
			t.y = y;
			background.add(t);
		}
	}
	public static void newSprite(Sprite sprite, float x, float y) {
		Sprite sp = null;
		try {
			sp = sprite.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(sp!=null) {
			sp.ax = x;
			sp.ay = y;
			sprites.add(sp);
		}
	}
	public static void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	public static void newForeground(Tile tile, int x, int y) {
		Tile t = null;
		try {
			t = tile.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(t!=null) {
			t.x = x;
			t.y = y;
			foreground.add(t);
		}
	}
	public static void spawn(Enemy enemy, int x, int y) {
		Enemy e = null;
		try {
			e = enemy.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		if(e!=null) {
			e.ax = x;
			e.ay = y;
			sprites.add(e);
			enemies.add(e);
		}
	}
	public static void addBackground(Tile tile) {
		background.add(tile);
	}
	public static void remove(Sprite sp) {
		Game.spritesToDispose.add(sp);
	}
	public static void remove(GameEvent e) {
		Game.eventsToDispose.add(e);
	}
	public static void init() {
		sound.Sound.sounds = new ArrayList<Sound>();
		sound.Music.BGM(sound.Music.introBGM);
		player1 = Player.korriel;
	}
	public static void newBullet(Bullet bullet, float x, float y) {
		Bullet b = null;
		try {
			b = bullet.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(b!=null) {
			b.ax = x;
			b.ay = y;
			sprites.add(b);
			bullets.add(b);
		}
	}
	public static void newEBullet(EnemyBullet bullet, float x, float y) {
		EnemyBullet b = null;
		try {
			b = bullet.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(b!=null) {
			b.ax = x;
			b.ay = y;
			sprites.add(b);
			eBullets.add(b);
		}
	}
	public static void newEBullet(EnemyBullet bullet, float x, float y, float hSpeed, float vSpeed) {
		EnemyBullet b = null;
		try {
			b = bullet.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if(b!=null) {
			b.ax = x;
			b.ay = y;
			b.hSpeed = hSpeed;
			b.vSpeed = vSpeed;
			sprites.add(b);
			eBullets.add(b);
		}
	}
}
