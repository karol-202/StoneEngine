#version 330

in vec2 uv;

uniform vec3 diffuseColor;
uniform sampler2D diffuseTexture;
uniform vec3 lightColor;
uniform float lightIntensity;

out vec4 fragColor;

void main()
{
	vec4 diffuseMaterial = texture2D(diffuseTexture, uv) * vec4(diffuseColor, 1);
	vec4 diffuseLight = vec4(lightColor, 1) * lightIntensity;
	fragColor = diffuseMaterial * diffuseLight;
}