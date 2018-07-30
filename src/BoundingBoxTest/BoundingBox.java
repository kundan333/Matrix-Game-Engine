package BoundingBoxTest;

import org.lwjgl.util.vector.Vector3f;

public class BoundingBox {
	
	private float[] vertices;
	private Vector3f position;
	private float scale;
	
	private int[] indices = {
			0,1,3,	
			3,1,2,	
			4,5,7,
			7,5,6,
			8,9,11,
			11,9,10,
			12,13,15,
			15,13,14,	
			16,17,19,
			19,17,18,
			20,21,23,
			23,21,22

	};
	
	public BoundingBox(Vector3f min , Vector3f max,Vector3f position,float scale){
		
		
	this.vertices =	createVecticesArray(new Vector3f(min.x,min.y,min.z),
			new Vector3f(max.x,max.y,max.z));
	//this.vertices =	createVecticesArray(new Vector3f(position.x + min.x/scale,position.y + min.y/scale,position.z +min.z/scale),
		//	new Vector3f(position.x +max.x/scale,position.y +max.y/scale,position.z +max.z/scale));
	
	this.position = position;
	this.scale = scale;

		
	}
	
	
	public float getScale() {
		return scale;
	}


	public Vector3f getPosition() {
		return position;
	}

	private float[] createVecticesArray(Vector3f Min , Vector3f Max) {
		
	float[]	vertices = {
	            Min.x, Min.y, Max.z, 
	            Max.x, Min.y, Max.z, 
	            Max.x, Max.y, Max.z, 
	            Min.x, Max.y, Max.z, 

	            Max.x, Min.y, Max.z, 
	            Max.x, Min.y, Min.z, 
	            Max.x, Max.y, Min.z, 
	            Max.x, Max.y, Max.z, 

	            Min.x, Max.y, Max.z, 
	            Max.x, Max.y, Max.z, 
	            Max.x, Max.y, Min.z, 
	            Min.x, Max.y, Min.z, 

	            Min.x, Min.y, Min.z, 
	            Min.x, Max.y, Min.z, 
	            Max.x, Max.y, Min.z, 
	            Max.x, Min.y, Min.z, 

	            Min.x, Min.y, Min.z, 
	            Max.x, Min.y, Min.z, 
	            Max.x, Min.y, Max.z, 
	            Min.x, Min.y, Max.z, 

	            Min.x, Min.y, Min.z, 
	            Min.x, Min.y, Max.z, 
	            Min.x, Max.y, Max.z, 
	            Min.x, Max.y, Min.z, 
		};
	
	return vertices;
		
		
	}

	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}
	
	
	
	
	
	

}
