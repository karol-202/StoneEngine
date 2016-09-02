#version 330

in vec2 uv;

uniform sampler2D src;

layout(location = 0) out vec3 fragColor;
layout(location = 1) out vec3 bright;

void main()
{
	vec3 color = texture2D(src, uv).rgb;
	fragColor = color;
	float brightness = 0.2126 * color.r + 0.7152 * color.g + 0.0722 * color.b;
	if(brightness > 1.0) bright = color;
}