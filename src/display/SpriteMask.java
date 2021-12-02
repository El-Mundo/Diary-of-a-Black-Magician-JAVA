package display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import base.FileIO;
import base.Sprite;

public class SpriteMask implements Cloneable {
	private Sprite surface;
	public void setSurface(Sprite surface) {
		this.surface = surface;
	}
	private BufferedImage images[];
	public BufferedImage image;
	public float opacity;
	public static BufferedImage getMask(BufferedImage image, int RGB) {
		int wid = image.getWidth();
		int hei = image.getHeight();
		BufferedImage mask = new BufferedImage(wid,hei,BufferedImage.TYPE_INT_ARGB);
		for(int w=0;w<wid;w++) {
			for(int h=0;h<hei;h++) {
				if(((image.getRGB(w, h) >> 24) & 0xff)>0) {
					mask.setRGB(w, h, RGB);
				}
			}
		}
		return mask;
	}
	public static BufferedImage getMask(BufferedImage image) {
		return getMask(image, -1);
	}
	public SpriteMask(Sprite source, int color) {
		this.surface = source;
		this.opacity = 0;
		BufferedImage[] s = source.getImages();
		this.images = new BufferedImage[s.length];
		for(int i=0;i<s.length;i++) {
			this.images[i] = getMask(s[i],color);
		}
		this.image = images[0];
	}
	public void update() {
		this.image = images[surface.getImageNumber()];
	}
	public void draw(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.opacity));
		g.drawImage(this.image, surface.x-Camera.x, surface.y-Camera.y, surface.w, surface.h, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	public void changeHue(float[] factors) {
		for(int i=0;i<this.images.length;i++) {
			this.images[i] = FileIO.changeHue(this.images[i], factors);
		}
	}
	public void setMode(boolean isColorMode) {
		return;
	}
	public void setMaskingImage(BufferedImage image) {
		return;
	}
	public static class AdvanceMask extends SpriteMask {
		public int[] maskRGB;
		private BufferedImage maskingImage;
		private boolean colorMode;
		public AdvanceMask(Sprite source, int color) {
			super(source, color);
			maskRGB = new int[3];
			maskRGB[0] = maskRGB[1] = maskRGB[2] = 255;
			this.maskingImage = null;
			this.colorMode = true;
		}

		public void draw(Graphics2D g) {
			int ww = Math.abs(super.surface.w);
			int hh = Math.abs(super.surface.h);
			if(ww==0||hh==0) {return;}
			BufferedImage bii = new BufferedImage(ww, hh, BufferedImage.TYPE_INT_ARGB);
			Graphics2D spg = bii.createGraphics();
			spg.drawImage(super.image, 0, 0, ww, hh, null);
			spg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, this.opacity));
			if(this.maskingImage!=null&&!this.colorMode) { 
				spg.drawImage(maskingImage, 0, 0, ww, hh, null);
			}else {
				spg.setColor(new Color(maskRGB[0],maskRGB[1],maskRGB[2]));
				spg.fillRect(0, 0, ww, hh);
			}
			g.drawImage(bii, super.surface.x-Camera.x, super.surface.y-Camera.y, super.surface.w, super.surface.h, null);
			spg.dispose();
			bii.flush();
		}
		
		public void setMode(boolean isColorMode) {
			this.colorMode = isColorMode;
		}
		public void setMaskingImage(BufferedImage image) {
			this.maskingImage = image;
		}
		public AdvanceMask clone() throws CloneNotSupportedException {
			AdvanceMask am = (AdvanceMask) super.clone();
			am.maskRGB = new int[3];
			am.maskRGB[0] = this.maskRGB[0];
			am.maskRGB[1] = this.maskRGB[1];
			am.maskRGB[2] = this.maskRGB[2];
			return am;
		}
	}
	public SpriteMask clone() throws CloneNotSupportedException {
		return (SpriteMask) super.clone();
	}
}
