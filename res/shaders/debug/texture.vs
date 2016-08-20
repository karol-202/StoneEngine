#version 330

layout(location = 0) in vec3 vertPos;
layout(location = 1) in vec2 vertUv;

out vec2 uv;

void main()
{
	gl_Position = vec4(vertPos, 1);
	uv = vertUv;
}