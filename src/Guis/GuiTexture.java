package Guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	
	private int textureId;
	private Vector2f position;
	private Vector2f scale;
	
	
	public GuiTexture(int textureId ,Vector2f pos,Vector2f scale) {
		this.textureId = textureId;
		this.position = pos;
		this.scale = scale;
		
	}
	
	
	
	public int getTextureId() {
		return textureId;
	}
	public Vector2f getPosition() {
		return position;
	}
	public Vector2f getScale() {
		return scale;
	}
	
	
	
	
	

}
