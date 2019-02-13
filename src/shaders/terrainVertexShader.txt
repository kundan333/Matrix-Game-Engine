#version 400 core
//140

in vec3 position; //input
in vec2 textureCoords;
in vec3 normals;



out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float visibility;
out vec4 shadowCoords;

uniform  mat4 transformationMatrix;
//4by4 matrix 4D

uniform  mat4 projectionMatrix;
uniform  mat4 viewMatrix;
uniform  vec3 lightPosition[4];
uniform vec4 plane ;
uniform mat4 toShadowMapSpace;
uniform float shadowDistance;

const float transitionDistance = 10.0;
const float density = 0.0035;
const float gradient =5;

void main(void){

		vec4 worldPosition = transformationMatrix * vec4(position,1.0);
		//worldPosition is position of object with transformationMatrix
		shadowCoords = toShadowMapSpace * worldPosition;
	
	
		gl_ClipDistance[0]= dot(worldPosition,plane);
		
		
	//This is important---- Matrix must be in correct order if they are not result will be different----	
	
	
	
		vec4 positionRelativeToCam = viewMatrix *  worldPosition;
	
	
		gl_Position = projectionMatrix * positionRelativeToCam;
	    
	    pass_textureCoords = textureCoords;
	    
	    
	   
	   
	    
	    
	    surfaceNormal = (transformationMatrix * vec4(normals, 0.0)).xyz;
	    
	    
	    //transformationMatrix is 4D w is included to more search on internet
	   
	   for(int i =0;i<4;i++){ 
	    toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	   }
	   
	    //worldPosition is 4D vector
	    
	    toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz; 
	    
	    float distance = length(positionRelativeToCam.xyz);
	    visibility = exp(-pow((distance*density),gradient));
	    visibility = clamp(visibility,0.0,1.0);
	    
	    distance = distance - (shadowDistance-transitionDistance);
	    distance = distance/transitionDistance;
	    shadowCoords.w =  clamp(1.0-distance,0.0,1.0);
	    





}
