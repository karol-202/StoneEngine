#version 330

in mat3 TBN;
in vec3 pos;
in vec2 uv;

in vec3 lightSpotDirection;

uniform vec3 cameraPos;
uniform vec3 diffuseColor;
uniform sampler2D diffuseTexture;
uniform vec3 specularColor;
uniform sampler2D specularTexture;
uniform float normalMapIntensity;
uniform sampler2D normalMap;
uniform vec3 lightColor;
uniform float lightIntensity;
uniform vec3 lightPos;
uniform float lightAttenLinear;
uniform float lightAttenQuadratic;
uniform float lightRange;
uniform float lightInnerAngle;
uniform float lightOuterAngle;

out vec3 fragColor;

void main()
{
	//Direction from light position to position of current fragment.
	vec3 lightDirection = TBN * (pos - lightPos);
	float lightDistance = length(lightDirection);
	if(lightDistance > lightRange) return;
	lightDirection = normalize(lightDirection);
	vec3 cameraDirection = TBN * normalize(cameraPos - pos);
	vec3 normal = normalize(mix(vec3(0, 0, 1), texture2D(normalMap, uv).rgb * 2.0 - 1.0, normalMapIntensity));
	
	float spotCosine = clamp(dot(lightSpotDirection, lightDirection), 0, 1);
	if(spotCosine < lightOuterAngle) return;
	float spotFactor = 1;
	if(spotCosine < lightInnerAngle) spotFactor = 1 - ((lightInnerAngle - spotCosine) / (lightInnerAngle - lightOuterAngle));

	vec3 diffuseMaterial = texture2D(diffuseTexture, uv).rgb * diffuseColor;
	float diffuseFactor = clamp(dot(normal, -lightDirection), 0, 1);
	vec3 diffuseLight = lightColor * lightIntensity * diffuseFactor;
	vec3 diffuse = diffuseMaterial * diffuseLight;
	
	vec4 specularTexture = texture2D(specularTexture, uv);
	float specularPower = (specularTexture.a * 5) + 1;
	vec3 specularMaterial = specularTexture.rgb * specularColor;
	vec3 reflection = reflect(lightDirection, normal);
	float specularFactor = clamp(dot(cameraDirection, reflection), 0, 1);
	specularFactor = pow(specularFactor, specularPower);
	vec3 specularLight = lightColor * lightIntensity * specularFactor;
	vec3 specular = specularMaterial * specularLight;
	
	float lightAtten = 1 / (1 + lightAttenLinear * lightDistance +
							   lightAttenQuadratic * pow(lightDistance, 2));
	
	fragColor = (diffuse + specular) * lightAtten * spotFactor;
}