#version 330

in vec4 _color;

out vec4 fragColor;

void main()
{
	fragColor = (_color * 0.86) + 0.5;
}