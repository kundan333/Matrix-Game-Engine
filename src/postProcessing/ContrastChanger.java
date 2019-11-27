package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class ContrastChanger {
	
	
	private ImageRenderer renderer;
	private ContrastShader contrastShader;
	public ContrastChanger() {
		
		contrastShader = new ContrastShader();
		renderer = new ImageRenderer();
		
		
	}
	
	
	public void render(int texture) {
		contrastShader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		contrastShader.stop();
		
		
	}
	
	public void cleanUp() {
		renderer.cleanUp();
		contrastShader.cleanUp();
	}
	
	
	
	
	
	

}
