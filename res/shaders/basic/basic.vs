#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;

uniform mat4 MVP;

out vec4 _color;

void main()
{
	gl_Position = MVP * vec4(position, 1.0);
	_color = vec4(color, 1);
}