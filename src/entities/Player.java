package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import audio.AudioMaster;
import audio.AudioSource;
import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.Lerp;

public class Player extends Entity {
	

	private float RUN_SPEED_GOAL = 0;
	private static final float RUN_SPEED = 50;
	//private float TURN_SPEED_GOAL ;
	private static final float TURN_SPEED = 160;
	public  static final float GRAVITY = -80;
	private static final float JUMP_POWER = 30;
	//private static final float TERRAIN_HEIGHT =0;
	
	
	private float currentSpeed = 0;
	private float currentTurnSpeed =0; 
	
	private float upwardsSpeed = 0;   
	
	private boolean inAir = false;
	
	private AudioSource playerAudioSource;
	private int buffer;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,int playerAudioBuffer) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
		playerAudioSource = new AudioSource();
		this.buffer = playerAudioBuffer;
		
	}
	
	public void move(Terrain terrain) {
		checkInputs();
		
		currentSpeed = Lerp.interpolate(RUN_SPEED_GOAL, currentSpeed, DisplayManager.getFrameTimeSeconds()*120f);
		//currentTurnSpeed= Lerp.interpolate(TURN_SPEED_GOAL, currentTurnSpeed, DisplayManager.getFrameTimeSeconds()*80f);
		super.increaseRotation(0,currentTurnSpeed * DisplayManager.getFrameTimeSeconds()  , 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds()  ;
		
		//Now we will find the dx and dz with sin and cos function for finding player translation   
	
		float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY()))) ;
		float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY()))) ;
		super.increasePosition(dx, 0, dz);
		
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		
		super.increasePosition(0,upwardsSpeed *DisplayManager.getFrameTimeSeconds() , 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		
		if(super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			inAir = false;
			super.getPosition().y = terrainHeight;
		}
		
	}
	
	private void jump() {
		if(!inAir) {
		this.upwardsSpeed = JUMP_POWER;
		inAir = true;
		AudioMaster.setListenerData(getPosition());
		playerAudioSource.setPosition(this.getPosition());
		//System.out.println(this.getPosition());
		playerAudioSource.play(buffer);
		
		}
	}
	
	private void checkInputs() {
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			//this.currentSpeed = RUN_SPEED;
			this.RUN_SPEED_GOAL = RUN_SPEED;
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			//this.currentSpeed = -RUN_SPEED;
			this.RUN_SPEED_GOAL =  -RUN_SPEED;
			
		}else {
			
			//this.currentSpeed = 0;
			this.RUN_SPEED_GOAL = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			
			this.currentTurnSpeed = -TURN_SPEED;
			//this.TURN_SPEED_GOAL = -TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
			//this.TURN_SPEED_GOAL = TURN_SPEED;
			
			
		}else {
			//this.TURN_SPEED_GOAL =0;
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			
			jump();
		}
		
		
		
		
	}
	
	
	
	
	

}
