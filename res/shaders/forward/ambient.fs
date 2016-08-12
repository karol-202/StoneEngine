#version 330

in vec3 pos;
in vec2 uv;
in vec3 normal;

uniform vec3 matColor;
uniform sampler2D matTexture;
uniform vec3 lightColor;
uniform float lightIntensity;

void main()
{
	vec4 material = texture2D(matTexture, uv) * vec4(matColor, 1);
	vec4 light = vec4(lightColor, 1) * lightIntensity;
	gl_FragColor = material * light;
}