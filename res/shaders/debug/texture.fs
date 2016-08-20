#version 330

in vec2 uv;

uniform sampler2D texture;

layout(location = 0) out vec3 fragColor;

void main()
{
	fragColor = texture2D(texture, uv).rgb;
}