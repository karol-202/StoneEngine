#version 330

in mat3 TBN;
in vec3 pos;
in vec2 uv;

uniform vec3 cameraPos;
uniform vec3 diffuseColor;
uniform sampler2D diffuseTexture;
//Material specular color * skybox specular intensity
uniform vec3 specularColor;
uniform sampler2D specularTexture;
uniform float ambientOcclussionIntensity;
uniform sampler2D ambientOcclussionTexture;
uniform float normalMapIntensity;
uniform sampler2D normalMap;
uniform vec3 emissiveColor;
uniform sampler2D emissiveTexture;
uniform vec3 lightColor;
uniform float lightIntensity;
uniform samplerCube skybox;

layout(location = 0) out vec3 fragColor;

void main()
{
	vec3 cameraDirection = TBN * normalize(pos - cameraPos);
	vec3 normal = normalize(mix(vec3(0, 0, 1), texture2D(normalMap, uv).rgb * 2.0 - 1.0, normalMapIntensity));

	vec3 diffuseMaterial = texture2D(diffuseTexture, uv).rgb * diffuseColor;
	vec3 diffuseLight = lightColor * lightIntensity;
	vec3 diffuse = diffuseMaterial * diffuseLight;
	
	vec4 specularTexture = texture2D(specularTexture, uv);
	//float specularPower = specularTexture.a * 5 + 1;
	vec3 specularMaterial = specularTexture.rgb * specularColor;
	vec3 reflection = reflect(cameraDirection, normal);
	//Reflection from skybox
	vec3 specularLight = texture(skybox, inverse(TBN) * reflection).rgb;
	vec3 specular = specularMaterial * specularLight;
	
	float ambientOcclussion = (1 - texture2D(ambientOcclussionTexture, uv).r) * ambientOcclussionIntensity;
	
	vec3 emissive = texture2D(emissiveTexture, uv).rgb * emissiveColor;
	
	fragColor = (diffuse + specular) * (1 - ambientOcclussion) + emissive;
}