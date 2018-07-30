package BoundingBoxTest;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class BBrenderer {
	private RawModel rawModel;
	private BBShader shader = new BBShader();
	private BoundingBox bb;

	
	
	
	public BBrenderer(Loader loader ,BoundingBox bb,Matrix4f projection){
		//loader = new Loader();
		this.bb = bb;
		this.rawModel = loader.loadToVAO(bb.getVertices(), bb.getIndices());
		shader.start();
		shader.loadProjectionMatrix(projection);
		shader.stop();
		
	}
	
	public void renderer(Camera camera) {
		shader.start();
		shader.loadViewMatrix(camera);
	//	GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		Matrix4f transformation = Maths.createTransformationMatrix(bb.getPosition(),0,0,0, 1);
		shader.loadTrasformationMatrix(transformation);
		//GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
		//GL11.glPolygonMode(GL11.POLY);
		
		
	}
	public void cleanUp() {
		shader.cleanUp();
		
	}
	
	
	
	
	
	
	
	
	

}
