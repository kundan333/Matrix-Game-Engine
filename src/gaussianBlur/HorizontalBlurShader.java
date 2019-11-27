package gaussianBlur;

import shaders.ShaderProgram;

public class HorizontalBlurShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/gaussianBlur/horizontalBlurVertex.txt";
	private static final String FRAGMENT_FILE = "src/gaussianBlur/blurFragment.txt";
	
	private int location_targetWidth;
	
	protected HorizontalBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	protected void loadTargetWidth(float width){
		super.loadFloat(location_targetWidth, width);
	}
	
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocation() {
		// TODO Auto-generated method stub
		location_targetWidth = super.getUniformLocation("targetWidth");
	}
	
}
