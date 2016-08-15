#version 330

in vec3 pos;
in vec2 uv;
in vec3 normal;

out vec4 fragColor;

void main()
{
	fragColor = vec4(normal, 1);
}