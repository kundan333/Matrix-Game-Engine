package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0 ;
	
	
	
	private Vector3f position =new Vector3f(0,0,0);
	private float pitch=10; //rotation in x,y,z
	private float yaw; //Camera aim left right
	private float roll; // roll 180 up side down
	
	private Player player;
	
	
	public Camera(Player player) {
		this.player = player;
		
		
	}
	
	
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float varticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance,varticalDistance);
		this.yaw = 180 - (player.getRotY()+angleAroundPlayer);

		
		
		
		
	/*	
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			position.z -= 0.22f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.22f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 0.22f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.22f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			position.y += 0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
			position.y -= 0.02f;
		}
		*/
		
		
		
	}
	
	
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}

	

	private void calculateCameraPosition(float horizontalDistance,float varticalDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		
		position.x = player.getPosition().x - offsetX;
		// minus offset because camera is in negative x,z direction
		position.z = player.getPosition().z - offsetZ;
		
		position.y = player.getPosition().y + varticalDistance;
		
		
	}
	
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
		
	}
	
	private void calculatePitch() {
		
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
			
		}
		
	}
	
	
	private void calculateAngleAroundPlayer() {
		
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.1f;
			angleAroundPlayer -= angleChange;
			
		}
		
	}
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	
	
	
	
	
	

}
