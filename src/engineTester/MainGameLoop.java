package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Guis.GuiRenderer;
import Guis.GuiTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;

public class MainGameLoop {
	public static long lastFPS;
	static int fps;
	
	
	public static void main(String[] args){
		
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		ModelData data = OBJFileLoader.loadOBJ("tree");
		
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		
      //  RawModel model = OBJLoader.loadObjModel("tree", loader);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		//RawModel grass = OBJLoader.loadObjModel("grassModel", loader);
		
		//TexturedModel grassModel = new TexturedModel(grass,new ModelTexture(loader.loadTexture("grassTexture")));
		
		
		//grassModel.getTexture().setHasTransparency(true);
		//grassModel.getTexture().setFakeLighting(true);
		
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		
		RawModel fern = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		
		
		ModelTexture fernTexture =new ModelTexture(loader.loadTexture("fern3"));
		fernTexture.setNumberOfRows(2);
		TexturedModel fernModel = new TexturedModel(fern,fernTexture);
		fernModel.getTexture().setHasTransparency(true);
		
		
		ModelData playerData = OBJFileLoader.loadOBJ("lowpolychar");
		
		RawModel rawPlayer = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
		
		TexturedModel playerModel = new TexturedModel(rawPlayer,new ModelTexture(loader.loadTexture("lowpolycharuv")));
		
	
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");
		RawModel rawLamp = loader.loadToVAO(lampData.getVertices(),lampData.getTextureCoords(),lampData.getNormals(),lampData.getIndices());
		
		TexturedModel  lampModel = new TexturedModel(rawLamp,new ModelTexture(loader.loadTexture("lamp")));
		
		//fernModel.getTexture().setFakeLighting(true);
		List<Entity> entities = new ArrayList<Entity>();
		
		
		Player player = new Player(playerModel,new Vector3f(0,0,0),0,0,0,1);
		
		
		//entities.add(new Entity(playerModel,new Vector3f(0,0,0),0,0,0,1));

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blend"));
		
		Terrain terrain2 = new Terrain(0,-1,loader,new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture),blendMap,"heightmap");
		
		Random random = new Random();
		for(int i=0;i<500;i++){
			float x = random.nextFloat()* 800 ;
			float z = random.nextFloat() * -600;
			float y = terrain2.getHeightOfTerrain(x, z);
			float xf = random.nextFloat()* 700 ;
			float zf = random.nextFloat() * -600;
			float yf = terrain2.getHeightOfTerrain(xf, zf);
			
			entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,4));
			
			entities.add(new Entity(fernModel,random.nextInt(4),new Vector3f(xf,yf,zf),0,0,0,0.5f));
		
		}
		
		List<Light> lights = new ArrayList<Light>();
		//System.out.println("Height -- "+terrain2.getHeightOfTerrain(200, -200));
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1f,1f,1f));
		lights.add(light);
		lights.add(new Light(new Vector3f(200,15,-200),new Vector3f(10,0,0),new Vector3f(1,0.1f,0.002f)));
		lights.add(new Light(new Vector3f(55.5f,-19,-55.5f),new Vector3f(0,5,0),new Vector3f(1,0.1f,0.002f)));
		
		entities.add(new Entity(lampModel,new Vector3f(210,6,-202),0,0,0,0.7f));
		entities.add(new Entity(lampModel,new Vector3f(62,-23,-53),0,0,0,0.7f));
		
		
		//Terrain terrain = new Terrain(-1,-1,loader,new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture),blendMap,"heightmap");
		
		Camera camera = new Camera(player);	
		MasterRenderer renderer = new MasterRenderer(loader);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		GuiTexture gui = new GuiTexture(loader.loadTexture("fern"),new Vector2f(0.5f,0.5f),new Vector2f(0.25f,0.25f));
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		guis.add(gui);
		
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		lastFPS = getTime();
		
		while(!Display.isCloseRequested()){
		//	entity.increasePosition(0, 0, -0.02f);
			//entity.increaseRotation(1, 0, 1);

		
			
			player.move(terrain2);
		//	System.out.println(" player x "+player.getPosition().getX()+ "player y "+player.getPosition().getY()+" player z"+player.getPosition().getX()); 
			camera.move();
			
			picker.update();
			
			System.out.println(picker.getCurrentRay());
			
			renderer.processEntity(player);
	
			//Game Loop
			//renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
		
			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			
			renderer.render(lights, camera);
			/* shader.stop(); */
			guiRenderer.render(guis);
			updateFPS();
			DisplayManager.updateDisplay();
			
		}
		
		guiRenderer.cleanUp();
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