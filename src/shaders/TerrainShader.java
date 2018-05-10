package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
	
	
	private static final String VERTEX_FILE ="src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE ="src/shaders/terrainFragmentShader.txt";
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
	
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normals");
	}

	@Override
	protected void getAllUniformLocation() {
		
		// TODO Auto-generated method stub
		location_transformationMatrix = super.getUnitformLocation("transformationMatrix");
		location_projectionMatrix = super.getUnitformLocation("projectionMatrix");
		location_viewMatrix = super.getUnitformLocation("viewMatrix");
		location_lightPosition = super.getUnitformLocation("lightPosition");
		location_lightColor = super.getUnitformLocation("lightColor");
		location_shineDamper = super.getUnitformLocation("shineDamper");
		location_reflectivity = super.getUnitformLocation("reflectivity");
		location_skyColor = super.getUnitformLocation("skyColor");

	}
	
	public void loadTransfromationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
		
	}
	
	public void loadShineVariable(float damper,float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
		
	}
	public void loadSkyColor(float r,float g,float b) 
	{
		super.loadVector(location_skyColor,new Vector3f(r,g,b));
	}
	
	

}
