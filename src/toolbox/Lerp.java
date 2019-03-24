package toolbox;

public class Lerp {
	
	
	public static float interpolate(float goal,float current,float dt) {
		float difference = goal - current;
		if (difference >dt ) {
			return current + dt;
		}
		if (difference <-dt) {
			
			return current -dt;
		}
		return goal;
		
	}
	

}
