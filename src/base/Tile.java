package base;

import java.awt.Image;

public class Tile implements Cloneable {
	public Image image;
	public int x,y,w,h;
	public boolean visible;
	
	public Tile(int x, int y, int w, int h, Image image) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.visible = true;//»ù±¾ÊôÐÔ
	}
	public Tile clone() throws CloneNotSupportedException {
		return (Tile)(super.clone());
	}

}
