package Guis;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {
	
	private static final String VERTEX_SHADER = "src/Guis/vs.txt";
	private static final String FRAGMENT_SHADER = "src/Guis/fs.txt";
	
	private int location_transformationMatrix;

	
	public GuiShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		location_transformationMatrix = super.getUniformLocation("transformation");
		
	}


	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		
		super.bindAttribute(0, "positions");
		
	}
	
	public void loadMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
		
	}
	
	
	

}
