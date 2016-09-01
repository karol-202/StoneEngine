#version 330

in vec2 uv;

uniform sampler2D src;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec4 color = texture2D(src, uv);
	fragColor = vec3(0.2126 * color.r + 0.7152 * color.g + 0.0722 * color.b);
}