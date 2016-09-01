#version 330

layout(location = 0) in vec3 vertPos;
layout(location = 1) in vec2 vertUv;

out vec3 pos;
out vec2 uv;

void main()
{
	pos = vertPos;
	uv = vertUv;

	gl_Position = vec4(pos, 1);
}