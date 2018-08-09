package fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontMeshCreator.TextMeshData;
import renderEngine.Loader;

public class TextMaster {
	
	private static Loader loader;
	private static Map<FontType,List<GUIText> > texts = new HashMap<FontType,List<GUIText> >();
	private static FontRenderer renderer;

	
	public static void init(Loader theloader) {
		renderer = new FontRenderer();
		loader = theloader;
	}
	
	public static void render() {
		renderer.render(texts);
	}
	
	
	public static void loadText(GUIText guiText) {
		FontType font = guiText.getFont();
		TextMeshData data = font.loadText(guiText);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		guiText.setMeshInfo(vao,data.getVertexCount());
		List <GUIText> textBatch = texts.get(font);
		if(textBatch==null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font,textBatch);
		}
		textBatch.add(guiText);
		
	}
	
	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()) {
			texts.remove(textBatch);
		}
		
		
	}
	
	
	public static void cleanUp() {
		renderer.cleanUp();
		
	}
	
	
	
	
	
}
