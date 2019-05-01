package physics;

import org.lwjgl.util.vector.Vector3f;

public class BoundingBox {

	private float minX;
	private float minY;
	private float minZ;
	private float maxX;
	private float maxY;
	private float maxZ;
	

	public BoundingBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public float getMinX() {
		return minX;
	}

	public float getMinY() {
		return minY;
	}

	public float getMinZ() {
		return minZ;
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMaxY() {
		return maxY;
	}

	public float getMaxZ() {
		return maxZ;
	}

	public boolean overlap(BoundingBox other) {

		if ((this.minX <= other.maxX && this.maxX >= other.minX) && (this.minY <= other.maxY && this.maxY >= other.minY)
				&& (this.minZ <= other.maxZ && this.maxZ >= other.minZ)) {

			return true;
		}

		return false;
	}
	
	public Vector3f overlaps(BoundingBox other) {
		float x;
		float y;
		float z;
		if(this.overlap(other)) {
			
		x = Math.min(this.maxX, other.maxX) - Math.max(this.minX, other.minX);
		y = Math.min(this.maxY, other.maxY) - Math.max(this.minY, other.minY);
		z = Math.min(this.maxZ, other.maxZ) - Math.max(this.minZ, other.minZ);
			
		}else {
			return new Vector3f(0,0,0);
			
		}
		//and player kept trying  to  overlap then make its velocity sideways
		
		return new Vector3f(x,y,z);
		
		
		
		
	}
	
	
	
	//public Vector3f intersection() {
	
	//}

	

}
