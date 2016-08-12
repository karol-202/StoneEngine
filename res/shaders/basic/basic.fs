#version 330

in vec2 uv;

uniform sampler2D samplerTexture;
uniform vec3 color;

out vec4 fragColor;

void main()
{
	vec4 texture = texture2D(samplerTexture, uv);
	fragColor = texture * vec4(color, 1);
	//fragColor = vec4(color, 1);
}