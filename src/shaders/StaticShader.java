package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {
	

	private static final int MAX_LIGHT = 4;
	
	private static final String VERTEX_FILE ="src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE ="src/shaders/fragmentShader.txt";
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_fakeLighting;
	private int location_skyColor;
	private int location_rows;
	private int location_offSet;
	private int location_plane;
	
	
	public StaticShader() {
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
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		//location_lightPosition = super.getUnitformLocation("lightPosition");
		//location_lightColor = super.getUnitformLocation("lightColor");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_fakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColor = super.getUniformLocation("skyColor");
		location_rows = super.getUniformLocation("numberOfRows");
		location_offSet = super.getUniformLocation("OffSet");
		location_plane = super.getUniformLocation("plane");
		
		
		location_lightPosition = new int[MAX_LIGHT];
		location_lightColor = new int[MAX_LIGHT];
		location_attenuation = new int[MAX_LIGHT];
		
		for(int i=0;i<MAX_LIGHT;i++) {
			
			location_lightPosition[i] =super.getUniformLocation("lightPosition["+i+"]");
			location_lightColor[i] = super.getUniformLocation("lightColor["+i+"]");
			location_attenuation[i] = super.getUniformLocation("attenuation["+i+"]");
			
		}
		
		
		
		
		

	}
	
	public void loadPlane(Vector4f plane) {
		super.loadVector4f(location_plane, plane);
		
	}
	
	
	public void loadNumberOfRows(int rows) {
		//System.out.println("rows");
		super.loadInt(location_rows, rows);
		
	}
	
	public void loadOffSet(float x,float y) {
		super.load2DVector(location_offSet, new Vector2f(x,y));
		
	}
	
	public void loadFakeLighting(boolean fakeLighting) {
		super.loadBoolean(location_fakeLighting, fakeLighting);
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
	
	public void loadLight(List<Light> lights) {
		for(int i=0;i<MAX_LIGHT;i++) {
			//may be if lights are less then four
		if(i<lights.size()) {	
		super.loadVector(location_lightPosition[i],lights.get(i).getPosition());
		super.loadVector(location_lightColor[i],lights.get(i).getColor());
		super.loadVector(location_attenuation[i],lights.get(i).getAttenuation());
		
		}else {
			super.loadVector(location_lightPosition[i], new Vector3f(0,0,0));
			super.loadVector(location_lightColor[i], new Vector3f(0,0,0));
			super.loadVector(location_attenuation[i],new Vector3f(1,0,0));
			
		}
		
		}
	}
	
	public void loadShineVariable(float damper,float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
		
	}
	
	public void loadSkyColor(float r,float g, float b) {
		
		super.loadVector(location_skyColor,new Vector3f(r,g,b));
	}
	
	
	
	
	
	

}
