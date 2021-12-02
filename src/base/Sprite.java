package base;

import java.awt.image.BufferedImage;

import display.Camera;
import display.SpriteMask;

public abstract class Sprite implements Cloneable {
	public int x,y,w,h;
	public int rw,rh;
	public float ax,ay,hSpeed,vSpeed;
	protected int t;
	protected float rotate;
	protected float[] rotateCenter;
	protected float[] scaleCenter;
	protected float wScale,hScale;
	public boolean visible;
	public boolean frozen;
	protected int frame;
	protected float opacity;
	public SpriteMask mask;
	protected BufferedImage images[];
	protected int imageNumber;
	
	public Sprite(int x, int y, int w, int h, BufferedImage[] images) {
		this.ax = this.x = x;
		this.ay = this.y = y;
		this.rw = this.w = w;
		this.rh = this.h = h;
		this.t = 0;
		this.rotateCenter = new float[2];
		this.scaleCenter = new float[2];
		this.wScale = 1;
		this.hScale = 1;
		this.scaleCenter[0] = this.rotateCenter[0] = this.x+this.w/2;
		this.scaleCenter[1] = this.rotateCenter[1] = this.y+this.h/2;
		this.rotate = 0;
		this.visible = true;
		this.opacity = 1;
		this.mask = null;
		this.imageNumber = 0;
		this.images = images;
	}
	public Sprite(BufferedImage[] images) {
		this.ax = this.x = 0;
		this.ay = this.y = 0;
		this.rw = this.w = images[0].getWidth();
		this.rh = this.h = images[0].getHeight();
		this.t = 0;
		this.rotateCenter = new float[2];
		this.scaleCenter = new float[2];
		this.wScale = 1;
		this.hScale = 1;
		this.scaleCenter[0] = this.rotateCenter[0] = this.x+this.w/2;
		this.scaleCenter[1] = this.rotateCenter[1] = this.y+this.h/2;
		this.rotate = 0;
		this.visible = true;
		this.opacity = 1;
		this.mask = null;
		this.imageNumber = 0;
		this.images = images;
	}
	
	public BufferedImage show() {
		return this.image(imageNumber);
	}
	
	public void update() {
		if(!this.frozen) {frame++;}
		this.ax+=this.hSpeed;
		this.ay+=this.vSpeed;
		this.w = (int) (rw*wScale);
		this.h = (int) (rh*hScale);
		this.x = (int)(ax+(rw-w)/2);
		this.y = (int)(ay+(rh-h)/2);
		if(this.mask!=null) {this.mask.update();}
	}
	public void flip(boolean horizontal) {
		if(horizontal) {
			this.wScale*=-1;
		}else {
			this.hScale*=-1;
		}
	}
	
	public void rotate(float theta) {
		this.rotate = theta;
	}
	public float getRotate() {
		return this.rotate;
	}
	public float[] getRotateCenter() {
		return this.rotateCenter;
		
	}
	public void updateRotateCenter() {
		this.rotateCenter[0] = this.x+this.w/2;
		this.rotateCenter[1] = this.y+this.h/2;
	}
	public void updateScaleCenter() {
		this.scaleCenter[0] = this.x+this.w/2;
		this.scaleCenter[1] = this.y+this.h/2;
	}
	public void synchronizeCenters(boolean isBasedOnScale) {
		if(isBasedOnScale) {
			this.rotateCenter = this.scaleCenter;
		}else {
			this.rotateCenter = this.scaleCenter;
		}
	}

	public Sprite clone() throws CloneNotSupportedException {
		Sprite sp = (Sprite)(super.clone());
		sp.rotateCenter = new float[2];
		sp.scaleCenter = new float[2];
		sp.scaleCenter[0] = sp.rotateCenter[0] = sp.x+sp.w/2;
		sp.scaleCenter[1] = sp.rotateCenter[1] = sp.y+sp.h/2;
		if(this.mask==null) {
			sp.mask = null;
		}else {
			sp.mask = this.mask.clone();
			sp.mask.setSurface(sp);
		}
		return sp;
	}
	public boolean isInSccreen() {
		float x = this.ax-Camera.x;
		float y = this.ay-Camera.y;
		if(x>960||x<-rw||y<-rh||y>540) {
			return false;
		}else {
			return true;
		}
	}
	
	public float getScale(boolean horizontal) {
		if(horizontal) {
			return wScale;
		}else {
			return hScale;
		}
	}

	public void setScale(float horizontal, float vertival) {
		this.wScale = horizontal;
		this.hScale = vertival;
	}
	public void setScale() {
		this.wScale = 1;
		this.hScale = 1;
	}
	public void scale(float horizontal, float vertival) {
		this.wScale *= horizontal;
		this.hScale *= vertival;
	}
	public float getOpacity() {
		return this.opacity;
	}
	public int getImageNumber(){
		return imageNumber;
	}
	public BufferedImage[] getImages(){
		return images;
	}
	public BufferedImage image(int num) {
		this.imageNumber = num;
		return this.images[num];
	}
	public static float changeAbsolute(float f, float change) {
		if(f>0) {return f+change;}
		else if(f<0) {return f-change;}
		else {return 0;}
	}
	public static float setAbsolute(float f, float target) {
		if(f>0) {return Math.abs(target);}
		else if(f<0) {return -Math.abs(target);}
		else {return 0;}
	}
	public void waggle(int time, float speed) {
		this.t++;
		if(this.t<=time) {
			this.wScale = changeAbsolute(this.wScale,0.04f*speed);
			this.hScale = changeAbsolute(this.hScale,-0.04f*speed);
		}else if(this.t<=time*3) {
			this.wScale = changeAbsolute(this.wScale,-0.04f*speed);
			this.hScale = changeAbsolute(this.hScale,0.04f*speed);
		}else if(this.t<=time*4) {
			this.wScale = changeAbsolute(this.wScale,0.04f*speed);
			this.hScale = changeAbsolute(this.hScale,-0.04f*speed);
		}else {
			t=0;
		}
	}
	public void wave() {
		//this.t++;
		if(this.t<=7) {
			this.rotate+=0.03f;
		}else if(this.t<=22) {
			this.rotate-=0.03f;
		}else if(this.t<=37) {
			this.rotate+=0.03f;
		}else if(this.t<=52) {
			this.rotate-=0.03f;
		}else if(this.t<60) {
			this.rotate+=0.03f;
		}
	}
}
