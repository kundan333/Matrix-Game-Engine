package particle;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class ParticleShader  extends ShaderProgram{
	private static final String VERTEX_FILE = "src/particle/particleVShader.txt" ;
	private static final String FRAGMENT_FILE = "src/particle/particleFShader.txt";
	
	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_txtOffset1;
	private int location_txtOffset2;
	private int location_blendAndRows;
	
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_txtOffset1 = super.getUniformLocation("txtOffset1");
		location_txtOffset2 = super.getUniformLocation("txtOffset2");
		location_blendAndRows = super.getUniformLocation("rowsAndBlend");
		
		
		
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		
	}
	
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);		
	}
	
	public void loadModelViewMatrix(Matrix4f modelViewMatrix) {
		super.loadMatrix(location_modelViewMatrix,modelViewMatrix);
	}
	
	public void loadTextureInfo(Vector2f txtoffset1,Vector2f txtoffset2,float numberofRows,float blendFactor ) {
		super.load2DVector(location_txtOffset1, txtoffset1);
		super.load2DVector(location_txtOffset2, txtoffset2);
		super.load2DVector(location_blendAndRows, new Vector2f(numberofRows,blendFactor));
		
	}
	
	

}
