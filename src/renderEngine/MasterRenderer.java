package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import normalMappingRenderer.NormalMappingRenderer;
import shaders.StaticShader;
import shaders.TerrainShader;
import skyBox.SkyBoxRenderer;
import terrains.Terrain;

public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	
	private Matrix4f projectionMatrix;
	private static final float FOV  = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	public static final float RED =0.5444f ;
	public static final float GREEN = 0.62f;
	public static final float BLUE =0.69f ;
	
	
	private EntityRenderer renderer ;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	
	
	Map<TexturedModel,List<Entity>> entities = new HashMap<TexturedModel,List<Entity>>();
	Map<TexturedModel,List<Entity>> normalMapEntities = new HashMap<TexturedModel,List<Entity>>();
	private List<Terrain> terrains= new ArrayList<Terrain>();
	
	private SkyBoxRenderer skyboxRenderer;
	private NormalMappingRenderer normalMapping;
	
	
	
	public MasterRenderer(Loader loader) {
		
		enableCulling();
		
		
		createProjectionMatrix();
		normalMapping = new NormalMappingRenderer(projectionMatrix);
		renderer = new EntityRenderer(shader,projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader,projectionMatrix);
		
		skyboxRenderer = new SkyBoxRenderer(loader,projectionMatrix);
	
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
		
	}
	
	public void renderAll(List<Light> sun,Camera camera,List<Entity> entities ,List<Entity> normalMapEntities,Terrain terrain,Vector4f clipPlane) {
			
			processTerrain(terrain);
		
			for(Entity entity:entities){
				processEntity(entity);
			}
			
			for(Entity normalMapEntity:normalMapEntities) {
				processNormalMapEntity(normalMapEntity);
			}
			
			render(sun,camera,clipPlane);
			
			
		}
	
	
	
	public void render(List<Light> sun,Camera camera,Vector4f clipPlane) {
		//renderAll(allEntities,terrain);
		
		
		prepare();
		shader.start();
		shader.loadPlane(clipPlane);
		shader.loadSkyColor(RED, GREEN, BLUE);
	
		shader.loadLight(sun);
		
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		
		normalMapping.render(normalMapEntities, clipPlane, sun, camera);
		
		
		terrainShader.start();
		terrainShader.loadPlane(clipPlane);
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		skyboxRenderer.render(camera,RED,GREEN,BLUE);
		
		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
		
	}


	
	
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		
		if(batch!=null) 
		{
			batch.add(entity);
			
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
			
			
		}
		
	}
	
	public void processNormalMapEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = normalMapEntities.get(entityModel);
		
		if(batch!=null) 
		{
			batch.add(entity);
			
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			normalMapEntities.put(entityModel, newBatch);
			
			
		}
		
		
	}
	

public void prepare() {
		
		GL11.glEnable(GL11.GL_DEPTH_TEST); //which triangle is front of each other
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);//RGB
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
		
	
		
	}
private void createProjectionMatrix() {
	
	float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
	//float aspectRatio = 1280/720;
	
	float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) );
	
	float x_scale = y_scale / aspectRatio;
	float frustum_length = FAR_PLANE - NEAR_PLANE;

	projectionMatrix = new Matrix4f();
	projectionMatrix.m00 = x_scale;
	projectionMatrix.m11 = y_scale;
	projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
	projectionMatrix.m23 = -1;
	projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
	projectionMatrix.m33 = 0;
	
	
}
	
	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
		normalMapping.cleanUp();
	
	}
	
}
