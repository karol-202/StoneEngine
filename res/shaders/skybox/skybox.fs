#version 330

in vec3 skyboxDir;

uniform samplerCube skybox;

layout(location = 0) out vec3 fragColor;

void main()
{
	fragColor = texture(skybox, skyboxDir).rgb;
}