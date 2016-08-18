#version 330

in vec2 uv;

uniform vec3 diffuseColor;
uniform sampler2D diffuseTexture;
uniform float ambientOcclussionIntensity;
uniform sampler2D ambientOcclussionTexture;
uniform vec3 lightColor;
uniform float lightIntensity;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec3 diffuseMaterial = texture2D(diffuseTexture, uv).rgb * diffuseColor;
	float ambientOcclussion = (1 - texture2D(ambientOcclussionTexture, uv).r) * ambientOcclussionIntensity;
	vec3 diffuseLight = lightColor * lightIntensity * (1 - ambientOcclussion);
	fragColor = diffuseMaterial * diffuseLight;
}