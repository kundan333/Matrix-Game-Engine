package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class MainGameLoop {
	public static long lastFPS;
	static int fps;
	
	public static void main(String[] args){
		
		
		DisplayManager.createDisplay();
		
		//System.out.println(Display.getWidth());
		//System.out.println(Display.getHeight());
		Loader loader = new Loader();
		
		/*
		StaticShader  shader= new StaticShader();

		Renderer renderer = new Renderer(shader);
		*/
	
		
		
		
		RawModel model = OBJLoader.loadObjModel("box", loader);
		ModelTexture texture = new  ModelTexture(loader.loadTexture("water"));
		TexturedModel texturedModel = new TexturedModel(model,texture);
		
		/* Specular Light   */
		
		ModelTexture textureWithoutConstractor = texturedModel.getTexture();
		textureWithoutConstractor.setShineDamper(10);
		textureWithoutConstractor.setReflectivity(1);
		
		List<Entity> ec = new ArrayList<Entity>();
		
		Random rd = new Random();
		for(int i=0;i<200;i++) {
			float x = rd.nextFloat()*100-50 ;
			float y = rd.nextFloat()*100-50 ;
			float z = rd.nextFloat()* -300 ;
			
			ec.add(new Entity(texturedModel,new Vector3f(x,y,z),rd.nextFloat()*180f,rd.nextFloat()*180f,0,1 ));
			
			
		}
		
		
		
		//Entity entity = new Entity(texturedModel,new Vector3f(0,0,-15),0,0,0,1);
        //1,0.6f,0.2f
		Light light = new Light(new Vector3f(2000,2000,2000),new Vector3f(1,1,1));
		Camera camera = new Camera();
		lastFPS = getTime();
	
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.02f);
			//entity.increaseRotation(1, 0, 1);

			camera.move();
			/*
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			shader.loadLight(light);
			*/
			
	
			//Game Loop	
			for(Entity cube:ec) {
			
				renderer.processEntity(cube);
				
				/*	renderer.render(cube, shader);	*/
			
			
			
			}
			renderer.render(light, camera);
			/* shader.stop(); */
			updateFPS();
			DisplayManager.updateDisplay();
			
		
		}
		
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
		
	}
	public static long getTime() {
		return (Sys.getTime()*1000)/Sys.getTimerResolution();
	}
	public static void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
	
	

}
