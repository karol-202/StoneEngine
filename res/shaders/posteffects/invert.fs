#version 330

in vec2 uv;

uniform sampler2D sr;

layout(location = 0) out vec3 fragColor;

void main()
{
	fragColor = 1.0 - texture2D(sr, uv).rgb;
}