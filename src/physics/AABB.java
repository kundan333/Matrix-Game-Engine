package physics;

import org.lwjgl.util.vector.Vector3f;

public class AABB {

	private static float minX;
	private static float minY;
	private static float minZ;
	private static float maxX;
	private static float maxY;
	private static float maxZ;
	//private static int i=0;

	
	public static BoundingBox calculateBoundingBox(float[] vertices,float scale,Vector3f position) {
		//this condition will always outside the loop

		minX = maxX = vertices[0];
		minY = maxY = vertices[1];
		minZ = maxZ = vertices[2];
		
		int i=0;
		while( i<vertices.length) {
			
		
			if(vertices[i]<minX) {
				minX = vertices[i];			
			}
			
			if(vertices[i]>maxX) {
				maxX = vertices[i];
			}
			
			//for Y
			if(vertices[i+1]<minY) {
				minY = vertices[i+1];			
			}
			if(vertices[i+1]>maxY) {
				maxY = vertices[i+1];
			}
			
			//for Z
			if(vertices[i+2]<minZ) {
				minZ = vertices[i+2];			
			}
			if(vertices[i+2]>maxZ) {
				maxZ = vertices[i+2];
			}
			
			i = i +3;
			
		}
		
		
		
		return new BoundingBox(position.x + minX/scale , position.y + minY/scale, position.z + minZ/scale,
								position.x +maxX/scale,position.y+  maxY/scale,position.z+ maxZ/scale);
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
