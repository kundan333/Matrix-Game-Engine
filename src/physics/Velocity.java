package physics;

import org.lwjgl.util.vector.Vector3f;

public class Velocity {
	private float speed;
	private Vector3f direction ;
	
	

	public Velocity(float speed, Vector3f direction) {

		this.speed = speed;
		this.direction = direction;
	}

	
	
	
	public float getSpeed() {
		return speed;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
	
	
	
	
	
	
	

}
