package water;

import org.lwjgl.util.vector.Matrix4f;
import shaders.ShaderProgram;
import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/water/waterVertex.txt";
	private final static String FRAGMENT_FILE = "src/water/waterFragment.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflection;
	private int location_refraction;
	private int location_dudv;
	private int move_factor;
	private int location_camera;
	private int location_normalMap;
	private int location_lightColor;
	private int location_lightPosition;
	private int location_depthMap;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}


	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_camera, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}
	
	public void loadMoveFactor(float moveFactor) {
		super.loadFloat(move_factor, moveFactor);
	}
	
	public void loadLight(Light sun) {
		
		super.loadVector(location_lightColor, sun.getColor());
		super.loadVector(location_lightPosition, sun.getPosition());
		
	}
	
	

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		location_projectionMatrix =super.getUniformLocation("projectionMatrix"); 
		location_viewMatrix = 	   super.getUniformLocation("viewMatrix");
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_reflection = super.getUniformLocation("reflection");
		location_refraction = super.getUniformLocation("refraction");
		location_dudv = super.getUniformLocation("dudvMap");
		move_factor = super.getUniformLocation("moveFactor");
		location_camera = super.getUniformLocation("cameraPosition");
		location_normalMap = super.getUniformLocation("normalMap");
		location_lightColor=super.getUniformLocation("lightColor");
		location_lightPosition =super.getUniformLocation("lightPosition");
		location_depthMap = super.getUniformLocation("depthMap");
		
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_reflection, 0);
		super.loadInt(location_refraction, 1);
		super.loadInt(location_dudv, 2);
		super.loadInt(location_normalMap, 3);
		super.loadInt(location_depthMap, 4);
			
	}
	

}
