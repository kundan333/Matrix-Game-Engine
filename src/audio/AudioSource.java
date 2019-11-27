package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class AudioSource {
	
	private int sourceId;
	private float volume;
	private float pitch;
	private Vector3f position;
	
	
	public AudioSource() {
		sourceId = AL10.alGenSources();
		
		
	
		
		
	}
	
	public void play(int buffer) {
		stop();
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		continuePlaying();
	}
	
	public void delete() {
		stop();
		AL10.alDeleteSources(sourceId);
	}
	
	public void setVelocity(Vector3f velocity) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
		
	}
	
	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop?AL10.AL_TRUE:AL10.AL_FALSE);
	}
	
	public void pause() {
		AL10.alSourcePause(sourceId);
		
	}
	
	
	public void continuePlaying() {
		AL10.alSourcePlay(sourceId);
	}
	
	public void stop() {
		AL10.alSourceStop(sourceId);
	}
	
	
	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceId,AL10.AL_SOURCE_STATE)==AL10.AL_PLAYING;
		
	}

	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
		this.volume = volume;
	}

	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
		this.pitch = pitch;
	}

	public void setPosition(Vector3f position) {
		AL10.alSource3f(sourceId, AL10.AL_POSITION,position.x,position.y,position.z);
		this.position = position;
	}
	
	
	
	

}
