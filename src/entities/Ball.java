package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import physics.BoundingBox;
import physics.SphereCollider;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Ball extends Entity{
	
	
	private float upwardspeed =0;
	private float pushStrength = 50;
	public  static final float GRAVITY = -50;
	private boolean inAir = false;
	private boolean hasStrength = false;
	
	private SphereCollider sphereCollider;
	

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,SphereCollider sp) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
		this.sphereCollider = sp;
	}

	
	public void actions(Terrain terrain) {
		checkInputs();
		
		upwardspeed += GRAVITY*DisplayManager.getFrameTimeSeconds();
		
		//increase y position of ball
		super.increasePosition(0, upwardspeed*DisplayManager.getFrameTimeSeconds(), 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight) {
			upwardspeed =0;
			inAir=false;
			super.getPosition().y = terrainHeight;
			
		}
		if(hasStrength) {	
			bounce();
		}
		
		
		
		
		
		
	}
	
	
	
	private void checkInputs() {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
			bounce();
		}
		
		
	}
	
	private void bounce() {
		if(!inAir) {
			this.upwardspeed = pushStrength;
			updatePushStrength();
			inAir = true; 
		}
		
	}
	
	private void updatePushStrength(){
		pushStrength = 0.8f * pushStrength;
		hasStrength = true;
		if(pushStrength<50*0.1) {
			hasStrength=false;
			pushStrength=50;
		}
		
	}


	public SphereCollider getSphereCollider() {
		return sphereCollider;
	}
	
	
	
	
	
}
