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
uniform samplerCube shadowmap;
uniform float shadowBias;
uniform float shadowRange;
uniform float shadowZNear;
uniform float shadowSoftness;
uniform int shadowSamples;

layout(location = 0) out vec3 fragColor;

void main()
{
	//Direction from light position to position of current fragment.
	vec3 lightDirectionWS = pos - lightPos;
	vec3 lightDirection = TBN * lightDirectionWS;
	float lightDistance = length(lightDirectionWS);
	if(lightDistance > lightRange) return;
	lightDirectionWS = normalize(lightDirectionWS);
	lightDirection = normalize(lightDirection);
	vec3 cameraDirection = TBN * normalize(cameraPos - pos);
	vec3 normal = normalize(mix(vec3(0, 0, 1), texture2D(normalMap, uv).rgb * 2.0 - 1.0, normalMapIntensity));
	
	float spotCosine = clamp(dot(lightSpotDirection, lightDirection), 0, 1);
	if(spotCosine < lightOuterAngle) return;
	float spotFactor = 1;
	if(spotCosine < lightInnerAngle) spotFactor = 1 - ((lightInnerAngle - spotCosine) / (lightInnerAngle - lightOuterAngle));
	
	float bias = clamp(shadowBias * tan(acos(dot(normal, -lightDirection))), 0, 0.05);
   	float visiblity = 1;
   	if(shadowSoftness == 0 || shadowSamples == 1)
   	{
   		if(texture(shadowmap, lightDirectionWS + vec3(shadowSoftness, shadowSoftness, shadowSoftness)).z * shadowRange + shadowZNear < lightDistance - bias) return;
   	}
   	else
   	{
   		float sampleInfluence = 1.0 / pow(shadowSamples, 3);
   	    for(float x = -shadowSoftness; x <= shadowSoftness; x += shadowSoftness * 2 / (shadowSamples - 1))
			for(float y = -shadowSoftness; y <= shadowSoftness; y += shadowSoftness * 2 / (shadowSamples - 1))
				for(float z = -shadowSoftness; z <= shadowSoftness; z += shadowSoftness * 2 / (shadowSamples - 1))
  				{
   					float sampl = texture(shadowmap, lightDirectionWS + vec3(x, y, z)).r * shadowRange + shadowZNear;
   					if(sampl < lightDistance - bias) visiblity -= sampleInfluence;
   				}
   	}

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
	
	fragColor = (diffuse + specular) * lightAtten * spotFactor * visiblity;
}