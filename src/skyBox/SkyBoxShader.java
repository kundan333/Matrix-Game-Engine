package skyBox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyBoxShader extends ShaderProgram{

	private final static String vertexFile ="src/skyBox/skyBoxVertexShader.txt";
	private final static String fragmentFile = "src/skyBox/skyBoxFragmentShader.txt";
	
	private int location_ProjectionMatrix;
	private int location_ViewMatrix;
	private int location_fogColor;
	
	
	public SkyBoxShader() {
		
		super(vertexFile, fragmentFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		
		location_ProjectionMatrix = super.getUnitformLocation("projection_matrix");
		location_ViewMatrix = super.getUnitformLocation("viewMatrix");
		location_fogColor =super.getUnitformLocation("fogColor");
		
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "positoins");
		
		
		
	}
	
	public void loadViewMatrix(Camera camera) {
		
		Matrix4f matrix = Maths.createViewMatrix(camera);
		
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		
		super.loadMatrix(location_ViewMatrix, matrix);
		
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_ProjectionMatrix, matrix);
		
	}
	
	public void loadFogColor(Vector3f fogColor) {
		
		super.loadVector(location_fogColor, fogColor);
		
	}
	
	
	
	
	
	

}
