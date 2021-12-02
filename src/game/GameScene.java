package game;

import display.Camera;

public class GameScene {
	public static void introScene() {
		Game.addSprite(Game.player1);
		Game.player1.ax = 20;
		Game.player1.ay = 20;
		new game.CollisionDetector();
		Camera.swithcMode();
		Camera.setHSpeed(1);
		base.Tile t = new base.Tile(0, 0, 960, 540, base.FileIO.getImage("res/StarFox-1.png", 1, 1)[0]);
		base.Tile t2 = new base.Tile(960, 0, 960, 540, base.FileIO.getImage("res/StarFox-1.png", 1, 1)[0]);
		Game.addBackground(t);
		Game.addBackground(t2);
		Runnable r = new Runnable() {public void run() {if(Camera.x%1920==0) {t2.x=Camera.x+960;}else if(Camera.x%960==0) {t.x=Camera.x+960;}}};
		Game.events.add(new events.AutoEvent(r));
		Game.spawn(new enemy.Enemy.test(base.FileIO.getImage("res/rainbow.png", 2, 2)),960,300);
	}
	public static void dispose() {
		Game.background.clear();
		Game.sprites.clear();
		Game.foreground.clear();
		Game.events.clear();
	}

}
