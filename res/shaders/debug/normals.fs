#version 330

in vec3 pos;
in vec2 uv;
in vec3 normal;

layout(location = 0) out vec3 fragColor;

void main()
{
	fragColor = normal;
}