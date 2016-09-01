#version 330

in vec2 uv;

uniform sampler2D src;

layout(location = 0) out vec3 fragColor;

void main()
{
	fragColor = 1.0 - texture2D(src, uv).rgb;
}