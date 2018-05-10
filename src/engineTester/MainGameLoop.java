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
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {
	public static long lastFPS;
	static int fps;
	
	
	public static void main(String[] args){
		
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
        RawModel model = OBJLoader.loadObjModel("tree", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		
		RawModel grass = OBJLoader.loadObjModel("grassModel", loader);
		
		TexturedModel grassModel = new TexturedModel(grass,new ModelTexture(loader.loadTexture("grassTexture")));
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setFakeLighting(true);
		RawModel fern = OBJLoader.loadObjModel("fern", loader);
		
		TexturedModel fernModel = new TexturedModel(fern,new ModelTexture(loader.loadTexture("fern")));
		fernModel.getTexture().setHasTransparency(true);
		//fernModel.getTexture().setFakeLighting(true);
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<500;i++){
			
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 ,0,random.nextFloat() * -600),0,0,0,3));
			entities.add(new Entity(grassModel,new Vector3f(random.nextFloat()*700,0,random.nextFloat() * -500),0,0,0,1));
			entities.add(new Entity(fernModel,new Vector3f(random.nextFloat()*900,0,random.nextFloat() * -700),0,0,0,1));
		
		}
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();	
		MasterRenderer renderer = new MasterRenderer();
		
		lastFPS = getTime();
		
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.02f);
			//entity.increaseRotation(1, 0, 1);

			camera.move();
			
			
			
	
			//Game Loop
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
		
			for(Entity entity:entities){
				renderer.processEntity(entity);
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