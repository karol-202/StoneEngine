#version 330

in vec3 pos;
in vec2 uv;
in vec3 normal;

uniform vec3 cameraPos;
uniform vec3 matColor;
uniform sampler2D matTexture;
uniform vec3 lightColor;
uniform float lightIntensity;
uniform mat4 lightRotation;

out vec4 fragColor;

void main()
{
	vec4 material = texture2D(matTexture, uv) * vec4(matColor, 1);
	
	vec3 lightDirection = normalize((lightRotation * vec4(0, 0, 1, 0)).xyz);
	
	float diffuseFactor = clamp(dot(normal, -lightDirection), 0, 1);
	vec4 lightDiffuse = vec4(lightColor, 1) * lightIntensity * diffuseFactor;
	
	vec3 reflection = reflect(lightDirection, normal);
	vec3 cameraDirection = normalize(cameraPos - pos);
	float specularFactor = clamp(dot(cameraDirection, reflection), 0, 1);
	specularFactor = pow(specularFactor, 5);
	vec4 lightSpecular = vec4(lightColor, 1) * lightIntensity * specularFactor;
	
	vec4 light = lightDiffuse + lightSpecular;
	
	fragColor = material * light;
}