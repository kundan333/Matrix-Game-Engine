package audio;

import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

public class AudioTest {

	public static void main(String arg[]) throws IOException, InterruptedException {
		AudioMaster.init();
		AudioMaster.setListenerData(new Vector3f());
		
		int buffer = AudioMaster.loadSound("audio/bounce.wav");
		
		AudioSource source = new AudioSource();
		source.setLooping(true);
		source.play(buffer);
		
		
		Vector3f position =new Vector3f(8,0,2);
		source.setPosition(position);
		char c= ' ';
		
		while(c!='q') {
			
			//c= (char)System.in.read();
			
			position.x -= 0.03f;
			source.setPosition(position);
			Thread.sleep(10);
			/*if(c=='p') {
				if(source.isPlaying()) {
					source.pause();
				}else {
				source.continuePlaying();
				}
				
			}*/
			
		}
		
		source.delete();
		AudioMaster.cleanUp();
		
		
	}
	
	
}
