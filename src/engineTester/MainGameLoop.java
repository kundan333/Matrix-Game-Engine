package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
	
	
	public static void main(String[] args){
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		StaticShader  shader= new StaticShader();

		Renderer renderer = new Renderer(shader);
		
		RawModel model = OBJLoader.loadObjModel("bnny", loader);
		ModelTexture texture = new  ModelTexture(loader.loadTexture("white"));
		TexturedModel texturedModel = new TexturedModel(model,texture);
		
		/* Speculer Light   */
		
		ModelTexture textureWithoutConstractor = texturedModel.getTexture();
		textureWithoutConstractor.setShineDamper(10);
		textureWithoutConstractor.setReflectivity(1);
		
		
		
		Entity entity = new Entity(texturedModel,new Vector3f(0,-5,-35),0,0,0,1);

		Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1,0.6f,0.2f));
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.02f);
			entity.increaseRotation(0, 1, 0);

			camera.move();
			renderer.prepare();
			shader.start();
			
			shader.loadLight(light);
			
			shader.loadViewMatrix(camera);
	
			//Game Loop	
			renderer.render(entity, shader);
			shader.stop();
			
			DisplayManager.updateDisplay();
		
		}
		shader.cleanUp();
		
		loader.cleanUP();
		DisplayManager.closeDisplay();
		
	}
	

}
