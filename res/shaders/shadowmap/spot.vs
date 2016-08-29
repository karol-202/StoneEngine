#version 330

layout(location = 0) in vec3 vertPos;

uniform mat4 M;

void main()
{
	gl_Position = M * vec4(vertPos, 1);
}