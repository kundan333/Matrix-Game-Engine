package physics;

import org.lwjgl.util.vector.Vector3f;

import entities.Player;

public class HandleCollision {
	
	BoundingBox A;
	BoundingBox B;
	private boolean crash = false;
	Player player;
	Vector3f previousPos;
	
	
	public HandleCollision(Player player) {
		this.player = player;
		this.previousPos = player.getPosition();
		
	}
	
	public void CheckPlayerCollision(BoundingBox A,BoundingBox B) {
		 crash = A.overlap(B);
		
		 if(crash) {
			 Vector3f offsetPosition = A.overlaps(B);
			 
			 player.setPosition(new Vector3f(previousPos.x -offsetPosition.x ,previousPos.y -offsetPosition.y,previousPos.z -offsetPosition.z));
			
			 crash = false;
		 }else {
			 this.previousPos = player.getPosition();
			 
		 }
		 
		
	}
	
	
	
	
	
	

}
