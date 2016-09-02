#version 330

in vec2 uv;

uniform sampler2D src;
uniform float gamma;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec3 color = texture2D(src, uv).rgb;
	fragColor = pow(color, vec3(1 / gamma));
}