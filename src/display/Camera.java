package display;

public class Camera {
	public static int x,y;
	private static boolean AutoMode;
	public static boolean isAutoMode() {
		return AutoMode;
	}
	private static float vSpeed,hSpeed,ax,ay;
	
	public static void setCameraMode(String setMode) {
		if(setMode=="MOVE") {
			AutoMode = false;
		}else if(setMode=="SCOLL") {
			AutoMode = true;
		}
	}
	public static void moveCamera(int x, int y) {
		Camera.ax = Camera.x = x;
		Camera.ay = Camera.y = y;
	}
	public static void scrollCamera(float horizontal, float vertical) {
		Camera.ax += horizontal;
		Camera.ay += vertical;
		Camera.x = (int)Camera.ax;
		Camera.y = (int)Camera.ay;
	}
	public static void update() {
		if(AutoMode==true) {
			Camera.scrollCamera(Camera.hSpeed, Camera.vSpeed);
		}
	}
	public static void swithcMode() {
		if(AutoMode==true) {AutoMode = false;}
		else {AutoMode = true;}
	}
	public static float getVSpeed() {
		return vSpeed;
	}
	public static void setVSpeed(float vSpeed) {
		Camera.vSpeed = vSpeed;
	}
	public static float getHSpeed() {
		return hSpeed;
	}
	public static void setHSpeed(float hSpeed) {
		Camera.hSpeed = hSpeed;
	}
	public static void setSpeed(float hSpeed, float vSpeed) {
		Camera.hSpeed = hSpeed;
		Camera.vSpeed = vSpeed;
	}

}
