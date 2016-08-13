#version 330

layout(location = 0) in vec3 vertPos;
layout(location = 1) in vec2 vertUv;
layout(location = 2) in vec3 vertNormal;

uniform mat4 MVP;

out vec3 pos;
out vec2 uv;
out vec3 normal;

void main()
{
	gl_Position = MVP * vec4(vertPos, 1);
	pos = vertPos;
	uv = vec2(vertUv.x, 1 - vertUv.y);
	normal = vertNormal;
}