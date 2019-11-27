package terrains;

import java.util.Random;

public class HeightsGenerator {

	private static final float APMLITUDE = 70f;
	private static final float ROUGHNESS = 0.3f;
	private static final int OCTIVES = 4;
	
	private Random random = new Random();
	
	private int seed ;
	
	public HeightsGenerator() {
		this.seed = random.nextInt(1000000000);
		
	}
	
	public float generateHeight(int x,int z) {
		/*
		float total = getInterpolatedNoise(x/8f,z/8f) * APMLITUDE;
		total +=getInterpolatedNoise(x/4f,z/4f) * APMLITUDE/3f;
		total+=getInterpolatedNoise(x/2f,z/2f) * APMLITUDE/9f;
		total+= getInterpolatedNoise(x,z) * APMLITUDE/27f;
		*/
		float total =0 ;
		float d = (float)Math.pow(2,OCTIVES-1);
		
		for(int i=0;i<OCTIVES;i++) {
			float frq = (float)Math.pow(2, i)/d;
			float am=(float)Math.pow(ROUGHNESS, i);
			total +=getInterpolatedNoise(x*frq,z*frq) * (APMLITUDE*am);
		}
		
		
		return total;
	}
	
	private float getInterpolatedNoise(float x,float z) {

		int intX = (int)x;
		int intZ = (int)z;
		
		float fracX = x - intX;
		float fracZ =z-intZ;
		
		float v1 = getSmoothNoise(intX,intZ);
		float v2 = getSmoothNoise(intX +1,intZ);
		float v3 = getSmoothNoise(intX,intZ+1);
		float v4 = getSmoothNoise(intX+1,intZ+1);
		
		float i1 = interpolated(v1,v2,fracX);
		float i2 = interpolated(v3,v4,fracX);
		
		return interpolated(i1,i2,fracZ);
		
	}
	
	
	private float interpolated(float a, float b,float blend) {
		double theta = blend * Math.PI;
		//(1f-Math.cos(theta) will return value 0 to 2 then we multiply it by 0.5 so we get values 0,1 
		float f = (float)((1f-Math.cos(theta))) * 0.5f ;
		return a * (1f - f) + b*f;
		
	}
	
	private float getSmoothNoise(int x,int z) {
		float corners = (getNoise(x-1, z-1)+getNoise(x+1, z-1)+getNoise(x-1, z+1)+getNoise(x+1, z+1))/16f;
		float sides = (getNoise(x, z-1)+getNoise(x+1, z)+getNoise(x-1, z)+getNoise(x, z+1))/8f;
		float center = getNoise(x,z)/4f;
		
		return corners+sides +center;
	}
	
	public float getNoise(int x,int z) {
		random.setSeed(x*43254+z*645451+seed);
		return random.nextFloat() *2f- 1;
	}
}
