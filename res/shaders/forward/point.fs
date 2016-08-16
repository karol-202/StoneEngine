#version 330

in vec3 pos;
in vec2 uv;
in vec3 normal;

uniform vec3 cameraPos;
uniform vec3 diffuseColor;
uniform sampler2D diffuseTexture;
uniform vec3 specularColor;
uniform sampler2D specularTexture;
uniform vec3 lightColor;
uniform float lightIntensity;
uniform vec3 lightPos;
uniform float lightAttenLinear;
uniform float lightAttenQuadratic;
uniform float lightRange;

out vec4 fragColor;

void main()
{
	vec3 lightDirection = pos - lightPos;
	float lightDistance = length(lightDirection);
	if(lightDistance > lightRange) return;
	lightDirection = normalize(lightDirection);
	vec3 cameraDirection = normalize(cameraPos - pos);

	vec4 diffuseMaterial = texture2D(diffuseTexture, uv) * vec4(diffuseColor, 1);
	float diffuseFactor = clamp(dot(normal, -lightDirection), 0, 1);
	vec4 diffuseLight = vec4(lightColor, 1) * lightIntensity * diffuseFactor;
	vec4 diffuse = diffuseMaterial * diffuseLight;
	
	vec4 specularTexture = texture2D(specularTexture, uv);
	float specularPower = specularTexture.a * 10 + 1;
	specularTexture.a = 1;
	vec4 specularMaterial = specularTexture * vec4(specularColor, 1);
	vec3 reflection = reflect(lightDirection, normal);
	float specularFactor = clamp(dot(cameraDirection, reflection), 0, 1);
	specularFactor = pow(specularFactor, specularPower);
	vec4 specularLight = vec4(lightColor, 1) * lightIntensity * specularFactor;
	vec4 specular = specularMaterial * specularLight;
	
	float lightAtten = 1 / (1 + lightAttenLinear * lightDistance +
							   lightAttenQuadratic * pow(lightDistance, 2));
	
	fragColor = (diffuse + specular) * lightAtten;
}