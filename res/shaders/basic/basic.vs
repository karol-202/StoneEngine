#version 330

layout (location = 0) in vec3 vertPosition;
layout (location = 1) in vec2 vertUv;
layout (location = 2) in vec3 vertNormal;

uniform mat4 MVP;

out vec2 uv;

void main()
{
	gl_Position = MVP * vec4(vertPosition, 1.0);
	uv = vec2(vertUv.x, 1 - vertUv.y);
}