#version 330

layout(location = 0) in vec3 vertPos;
layout(location = 1) in vec2 vertUv;
layout(location = 2) in vec3 vertNormal;
layout(location = 3) in vec3 vertTangent;
layout(location = 4) in vec3 vertBitangent;

uniform mat4 MVP;
uniform mat4 M;
uniform mat4 lightSpotDirectionMat;

out vec3 pos;
out vec2 uv;
out vec3 normalSurf;

out vec3 lightSpotDirection;

//out mat3 TBN;

void main()
{
	gl_Position = MVP * vec4(vertPos, 1);
	pos = (M * vec4(vertPos, 1)).xyz;
	uv = vec2(vertUv.x, 1 - vertUv.y);
	normalSurf = normalize((M * vec4(vertNormal, 0)).xyz);
	
	//Direction of light's spot.
    lightSpotDirection = normalize((lightSpotDirectionMat * vec4(0, 0, 1, 0)).xyz);
	
	vec3 tangent = normalize(vertTangent);
	vec3 bitangent = normalize(vertBitangent);
	TBN = transpose(mat3(tangent, bitangent, normalSurf));
}