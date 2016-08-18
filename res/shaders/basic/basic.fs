#version 330

in vec2 uv;

uniform sampler2D texture;
uniform vec3 color;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec3 texture = texture2D(texture, uv).rgb;
	fragColor = texture * color;
}