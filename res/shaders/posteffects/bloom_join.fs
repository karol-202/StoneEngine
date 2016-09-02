#version 330

in vec2 uv;

uniform sampler2D srcColor;
uniform sampler2D srcBlur;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec3 color = texture2D(srcColor, uv).rgb;
	vec3 blur = texture2D(srcBlur, uv).rgb;
	fragColor = color + blur;
}