package tileset;

import java.awt.image.BufferedImage;

import base.Tile;
import events.CameraEvent;
import game.Game;

public class WavingTile extends Tile {
	private Tile[] tiles;
	public CameraEvent updateWave;
	private int maximum;
	private int speed;
	private int t;

	public WavingTile(int x, int y, BufferedImage image, int interval, int maximum, int speed) {
		super(x, y, image.getWidth(), image.getHeight(), image);
		this.tiles = new Tile[image.getHeight()/interval];
		for(int i=0;i<tiles.length;i++) {
			tiles[i] = new Tile(x,y+interval*i,w,interval,image.getSubimage(0, i*interval, image.getWidth(), interval));
		}
		this.speed = speed;
		this.maximum = maximum;
		WavingTile wt = this;
		Runnable run = new Runnable() {
			@Override
			public void run() {
				t++;
				int a = 0;
				int c = 1;
				int i = wt.maximum*wt.speed;
				if(t<i) {
					a = t/wt.speed;
					c=1;
				}else if(t<=i*2) {
					a = wt.maximum-(t-i)/wt.speed;
					c=1;
				}else if(t<=i*3) {
					a = (t-i*2)/wt.speed;
					c=-1;
				}else if(t<=i*4) {
					a = wt.maximum-(t-i*3)/wt.speed;
					c=-1;
				}else {
					t=0;
				}
				int b =	wt.h/4;
				int s = wt.tiles.length/2;
				for(int n=0;n<s;n++) {
					int y1 = wt.tiles[n].y - wt.y - b;
					wt.tiles[n].x = wt.x-(int) Math.sqrt(a*a-(a*a*y1*y1)/(b*b))*c;
				}
				for(int n=s;n<wt.tiles.length;n++) {
					int y1 = wt.tiles[n].y - wt.y - b*3;
					wt.tiles[n].x = wt.x+(int) Math.sqrt(a*a-(a*a*y1*y1)/(b*b))*c;
				}
			}
		};
		this.updateWave = new CameraEvent(run, this.x-960-this.maximum, this.y-540, this.x+this.w+this.maximum, this.y+this.h);
	}

	public Tile[] getTiles() {
		return tiles;
	}
	public static void add(WavingTile tile) {
		for(int i=0;i<tile.tiles.length;i++) {
			Game.addBackground(tile.tiles[i]);
		}
		Game.events.add(tile.updateWave);
	}
	public void dispose() {
		for(Tile t : this.tiles) {
			Game.background.remove(t);
		}
		Game.remove(this.updateWave);
	}

}
