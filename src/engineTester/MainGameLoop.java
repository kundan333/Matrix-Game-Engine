package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Guis.GuiTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {
	public static long lastFPS;
	static int fps;
	public static boolean closeDisplay =false;
	
	
	public static void main(String[] args){
		
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		TextMaster.init(loader);
		
		FontType font = new FontType(loader.loadTextureForFont("sans"),new File("res/sans.fnt"));
		GUIText text = new GUIText("The Quick Brown fox jumps over the lazy dog ! 1234567890" , 1,font,new Vector2f(0.5f,0.5f),0.5f,true);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		
		ModelData data = OBJFileLoader.loadOBJ("tree");
		
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
	
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
		
		//RawModel grass = OBJLoader.loadObjModel("grassModel", loader);
		
		//TexturedModel grassModel = new TexturedModel(grass,new ModelTexture(loader.loadTexture("grassTexture")));
		
		
		//grassModel.getTexture().setHasTransparency(true);
		//grassModel.getTexture().setFakeLighting(true);
		
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		
		
	//	BoundingBox bb = AABB.calculateBoundingBox(fernData.getVertices());
		
	//	System.out.println("minX = " +bb.getMinX() +"minY = "+ bb.getMinY()+"minZ = "+ bb.getMinZ() +"maxX = "+ bb.getMaxX()+"maxY = "+ bb.getMaxY()
		//+"maxZ = "+ bb.getMaxZ()	
			//	);
		
		
		RawModel fern = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		
		
		
		ModelTexture fernTexture =new ModelTexture(loader.loadTexture("fern3"));
		fernTexture.setNumberOfRows(2);
		TexturedModel fernModel = new TexturedModel(fern,fernTexture);
		fernModel.getTexture().setHasTransparency(true);
		
		
			
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");
		RawModel rawLamp = loader.loadToVAO(lampData.getVertices(),lampData.getTextureCoords(),lampData.getNormals(),lampData.getIndices());
		
		TexturedModel  lampModel = new TexturedModel(rawLamp,new ModelTexture(loader.loadTexture("lamp")));
		
		//fernModel.getTexture().setFakeLighting(true);
		
		ModelData playerData = OBJFileLoader.loadOBJ("lowpolychar");

//		System.out.println("minX = " +playerBB.getMinX() +"minY = "+ playerBB.getMinY()+"minZ = "+ playerBB.getMinZ() 
	//	+"maxX = "	+ playerBB.getMaxX()+"maxY = "+ playerBB.getMaxY() +"maxZ = "+ playerBB.getMaxZ()	
		//		);
		
		
		RawModel rawPlayer = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
		
		TexturedModel playerModel = new TexturedModel(rawPlayer,new ModelTexture(loader.loadTexture("lowpolycharuv")));
		

		Player player = new Player(playerModel,new Vector3f(0,0,0),0,0,0,1);

		/*
		
		ModelData dataBunny = OBJFileLoader.loadOBJ("bnny");
		
		RawModel modelBunny = loader.loadToVAO(dataBunny.getVertices(), dataBunny.getTextureCoords(), 
				dataBunny.getNormals(), dataBunny.getIndices());
	
		TexturedModel staticModelBunny = new TexturedModel(modelBunny,new ModelTexture(loader.loadTexture("white")));
	
		Entity bunny =new Entity(staticModelBunny,new Vector3f(100,-15,-50),0,0,0,1); 
	
		entities.add(bunny);

		BoundingBox BunnyBB = AABB.calculateBoundingBox(dataBunny.getVertices(),bunny.getScale(),bunny.getPosition());
*/		
		
		
		
		
		
		//entities.add(new Entity(playerModel,new Vector3f(0,0,0),0,0,0,1));

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blend"));
		
		Terrain terrain2 = new Terrain(0,-1,loader,new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture),blendMap,"heightmap");
		
		Random random = new Random();
		for(int i=0;i<300;i++){
			
			
			float x = random.nextFloat()* 800 ;
			float z = random.nextFloat() * -600;
			float y = terrain2.getHeightOfTerrain(x, z);
			float xf = random.nextFloat()* 700 ;
			float zf = random.nextFloat() * -600;
			float yf = terrain2.getHeightOfTerrain(xf, zf);
			
			if(!(y<=10 || yf<=10)) {
			entities.add(new Entity(staticModel, new Vector3f(x,y,z),0,0,0,4));
			
			entities.add(new Entity(fernModel,random.nextInt(4),new Vector3f(xf,yf,zf),0,0,0,0.5f));
			}
			
			
		}
		
		List<Light> lights = new ArrayList<Light>();
		//System.out.println("Height -- "+terrain2.getHeightOfTerrain(200, -200));
		
		Light light = new Light(new Vector3f(1025,1000,-1025),new Vector3f(1f,1f,1f));
		lights.add(light);
		
		//create a class lamp with leight
		//lights.add(new Light(new Vector3f(200,15,-200),new Vector3f(10,0,0),new Vector3f(1,0.1f,0.002f)));
	//	lights.add(new Light(new Vector3f(55.5f,-19,-55.5f),new Vector3f(0,5,0),new Vector3f(1,0.1f,0.002f)));
		
	//	entities.add(new Entity(lampModel,new Vector3f(210,6,-202),0,0,0,0.7f));
		//entities.add(new Entity(lampModel,new Vector3f(62,-23,-53),0,0,0,0.7f));
		
		entities.add(player);
		
		
		//Terrain terrain = new Terrain(-1,-1,loader,new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture),blendMap,"heightmap");
		
		Camera camera = new Camera(player);	
		MasterRenderer renderer = new MasterRenderer(loader);
		
		
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(),terrain2);
		
		//BBrenderer bbrenderer = new BBrenderer(loader,bbPlayer,renderer.getProjectionMatrix());
		
		lastFPS = getTime();
		/*
		 * collision handle
		BBrenderer bbrenderer =null ;
		HandleCollision handleCollision = new HandleCollision(player);
		
		*/
		//****************************water render**************
		WaterFrameBuffers fbos = new WaterFrameBuffers();
		
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader,waterShader,renderer.getProjectionMatrix(),fbos);
		
		List<WaterTile> waters = new ArrayList <WaterTile>();
		WaterTile water =new WaterTile(425,-425,5); 
		waters.add(water);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),new ModelTexture(loader.loadTexture("barrel")) );
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelN3"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		TexturedModel crate = new TexturedModel(NormalMappedObjLoader.loadOBJ("crate", loader),new ModelTexture(loader.loadTexture("crate")) );
		crate.getTexture().setNormalMap(loader.loadTexture("crateNormal"));
		crate.getTexture().setShineDamper(10);
		crate.getTexture().setReflectivity(0.5f);
		
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(100,39,-200),0,0,0,1));
		
		normalMapEntities.add(new Entity(crate, new Vector3f(115,39,-200),0,0,0,0.05f));
		
		/*

		GuiTexture gui = new GuiTexture(fbos.getReflectionTexture(),new Vector2f(0.5f,0.5f),new Vector2f(0.25f,0.25f));
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		guis.add(gui);
		guis.add( new GuiTexture(fbos.getRefractionTexture(),new Vector2f(-0.5f,0.5f),new Vector2f(0.25f,0.25f)));
		
		*/
		
		while(!closeDisplay && !Display.isCloseRequested() ){
		//	entity.increasePosition(0, 0, -0.02f);
			//entity.increaseRotation(1, 0, 1);

		
			
			player.move(terrain2);
		
			//	System.out.println(" player x "+player.getPosition().getX()+ "player y "+player.getPosition().getY()+" player z"+player.getPosition().getX()); 
			
			camera.move();
			
			picker.update();
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			fbos.bindReflectionFrameBuffer();
			float distance = 2 *(camera.getPosition().y -water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderAll(lights, camera,entities,normalMapEntities,terrain2,new Vector4f(0,1,0,-water.getHeight()+1) );
			fbos.unbindCurrentFrameBuffer();
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//refraction fbo
			fbos.bindRefractionFrameBuffer();
			renderer.renderAll(lights, camera,entities,normalMapEntities,terrain2,new Vector4f(0,-1,0,water.getHeight()+1));
			fbos.unbindCurrentFrameBuffer();
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			
			
			
			renderer.renderAll(lights, camera,entities,normalMapEntities,terrain2,new Vector4f(0,1,0,20000));
		
			
			/*
			BoundingBox playerBB = AABB.calculateBoundingBox(playerData.getVertices(),player.getScale(),player.getPosition());
			
			
			handleCollision.CheckPlayerCollision(playerBB, BunnyBB);

			
			BoundingBoxTest.BoundingBox bbBunny = new BoundingBoxTest.BoundingBox(
					new Vector3f(BunnyBB.getMinX(),BunnyBB.getMinY(),BunnyBB.getMinZ()),
					new Vector3f(BunnyBB.getMaxX(),BunnyBB.getMaxY(),BunnyBB.getMaxZ()),
					bunny.getPosition(),bunny.getScale());
			bbrenderer = new BBrenderer(loader,bbBunny,renderer.getProjectionMatrix());
			bbrenderer.renderer(camera);
			*/
			waterRenderer.render(waters, camera,light);
			TextMaster.render();
			
			/* shader.stop(); */
			//guiRenderer.render(guis);
			updateFPS();
			DisplayManager.updateDisplay();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				 closeDisplay = true;
			}
			
			
		}
		TextMaster.cleanUp();
		fbos.cleanUp();
		waterShader.cleanUp();
		//bbrenderer.cleanUp();
		//guiRenderer.cleanUp();
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