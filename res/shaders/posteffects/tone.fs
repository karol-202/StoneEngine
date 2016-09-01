#version 330

in vec2 uv;

uniform sampler2D src;
uniform float exposure;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec3 color = texture2D(src, uv).rgb;
	fragColor = vec3(1) - exp(-color * exposure);
}