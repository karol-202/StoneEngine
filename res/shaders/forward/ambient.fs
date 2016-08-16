#version 330

in vec2 uv;

uniform vec3 diffuseColor;
uniform sampler2D diffuseTexture;
uniform float ambientOcclussionIntensity;
uniform sampler2D ambientOcclussionTexture;
uniform vec3 lightColor;
uniform float lightIntensity;

out vec4 fragColor;

void main()
{
	vec4 diffuseMaterial = texture2D(diffuseTexture, uv) * vec4(diffuseColor, 1);
	float ambientOcclussion = (1 - texture2D(ambientOcclussionTexture, uv).r) * ambientOcclussionIntensity;
	vec4 diffuseLight = vec4(lightColor, 1) * lightIntensity * (1 - ambientOcclussion);
	fragColor = diffuseMaterial * diffuseLight;
}