package physics;

import java.util.Vector;

import org.lwjgl.util.vector.Vector3f;

public class SphereCollider {
	
	private float radius;
	private Vector3f position;
	private Vector3f sphereCenter;
	
	
	
	public SphereCollider(float radius, Vector3f position) {
		this.radius = radius/2;
		this.position = position;
		this.sphereCenter = (Vector3f) position.scale(radius);
	}


	public float getRadius() {
		return radius;
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setRadius(float radius) {
		this.radius = radius;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	
	
	
	public boolean overlap(SphereCollider sphereOtherCollider) {
		
		
		
		double distance = getDistance(sphereCenter,sphereOtherCollider.sphereCenter);
		//distance < r1+r2
		
		if(distance<this.radius+sphereOtherCollider.radius) {
			return true;
		}
		
		return false;
	}
	
	
	private double getDistance(Vector3f thisSphere,Vector3f otherSphere) {
	
		float dx=thisSphere.x - otherSphere.x;
		float dy=thisSphere.y - otherSphere.y;
		float dz=thisSphere.z - otherSphere.z;
		
		return Math.sqrt(dx*dx+dy*dy+dz*dz);
	}
	
	
	
	

}