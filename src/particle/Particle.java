package particle;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Player;
import renderEngine.DisplayManager;

public class Particle {
	
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	
	private ParticleTexture texture;
	
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;
	
	
	private float elapsedTime=0;
	
	private float distance;

	public Particle(ParticleTexture texture,Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}
	
	
	
	public float getDistance() {
		return distance;
	}

	protected boolean update(Camera camera) {
		
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		Vector3f change  = new Vector3f(velocity); 
		change.scale(DisplayManager.getFrameTimeSeconds()); 
		//Means Multiplying vector by value of DisplayManager.getFrameTimeSeconds()
		Vector3f.add(change, position, position);
		
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		
		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
		
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}
	
	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		
		//lifeFactor is between 0 and 1 as time goes it will decrease to in %--->100..99..98...0 
		//so we calculate atlasProgession with this
		
		float atlasProgression = lifeFactor * stageCount;
		//atlasProgression have two parts fraction and number part...
		//number part is stage fractional part is how far that stage is near to next stage
		//example 4.5 is forth stage and .5 is how close it is to next stage .5 is--> 50 % we use this as blend factor
		
		int index1 = (int)Math.floor(atlasProgression);
		
		//next stage is simply we add 1 to this index ...if current stage is last stage then we do not
		
		int index2 = index1<stageCount-1? index1+1 :index1;
		
		this.blend = atlasProgression%1;
		setTextureOffset(texOffset1,index1);
		setTextureOffset(texOffset2,index2);
	
	
	}
	
	private void setTextureOffset(Vector2f offset,int index) {
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		
		offset.x =(float) column / texture.getNumberOfRows();
		offset.y =(float) row /texture.getNumberOfRows();
		
		
	}
	
	
	
	
	

}
