package enemy;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import game.Collision;

public class EnemyCollision extends Collision {
	public int type;
	private float rotate;

	public float getRotate() {
		return rotate;
	}
	public void setRotate(float rotate) {
		this.rotate = rotate;
	}
	public EnemyCollision(Enemy e,int type) {
		super(e,0,0);
		this.type = type;
		this.rotate = body.getRotate();
	}
	public EnemyCollision(EnemyBullet e,int type) {
		super(e,0,0);
		this.type = type;
		this.rotate = body.getRotate();
	}
	public EnemyCollision(Enemy e, int type, int ax, int ay, int rw, int rh) {
		super(e,ax,ay,rw,rh);
		this.type = type;
		this.rotate = body.getRotate();
	}
	public EnemyCollision(EnemyBullet eb, int type, int ax, int ay, int rw, int rh) {
		super(eb,ax,ay,rw,rh);
		this.type = type;
		this.rotate = body.getRotate();
	}
	public boolean intersects(Graphics2D detector, Collision collision) {
		if(this.type==0) {
			return detector.hit(collision.rigidBody, this.rigidBody, false);
		}else {
			return detector.hit(collision.rigidBody, new Ellipse2D.Float(this.rigidBody.x, this.rigidBody.y, this.rigidBody.width, this.rigidBody.height),false);
		}
	}
	public void update(Graphics2D g) {
		rigidBody.x = (int)body.ax+this.ax;
		rigidBody.y = (int)body.ay+this.ay;
		if(rotate!=0) {
			float[] rc = {rigidBody.x+rigidBody.width/2,rigidBody.y+rigidBody.height/2};
			g.rotate(rotate, rc[0], rc[1]);
		}
	}
}
