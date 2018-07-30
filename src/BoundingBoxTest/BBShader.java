package BoundingBoxTest;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class BBShader extends ShaderProgram {
	
	private static final String vertexFile ="src/BoundingBoxTest/vs.txt";
	private static final String fragmentFile = "src/BoundingBoxTest/fs.txt";
	
	private static int location_Projection;
	private static int location_View;
	private static int location_Transformation;
	
	
	public BBShader() {
		super(vertexFile, fragmentFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		
		location_Projection = super.getUniformLocation("projectionMatrix");
		location_View = super.getUniformLocation("viewMatrix");
		location_Transformation = super.getUniformLocation("transformationMatrix");
	
		
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "positions");
		
	}
	
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f view = Maths.createViewMatrix(camera);
		super.loadMatrix(location_View, view);
		
	}
	
	public void loadProjectionMatrix(Matrix4f proj) {
		super.loadMatrix(location_Projection, proj);
		
	}
	
	public void loadTrasformationMatrix(Matrix4f transfromation) {
		super.loadMatrix(location_Transformation, transfromation);
		
	}
	
	
	
	
	
	

}
