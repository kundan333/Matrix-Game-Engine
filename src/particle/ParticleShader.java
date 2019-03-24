package particle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class ParticleShader  extends ShaderProgram{
	private static final String VERTEX_FILE = "src/particle/particleVShader.txt" ;
	private static final String FRAGMENT_FILE = "src/particle/particleFShader.txt";
	
	
	private int location_projectionMatrix;
	
	private int location_NoOfRows;
	
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_NoOfRows = super.getUniformLocation("NoOfRows");
		
		
		
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");
		
	}
	
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);		
	}
	
	
	
	public void loadNoOFRows(float numberofRows) {
		super.loadFloat(location_NoOfRows, numberofRows);
		
	}
	
	

}
