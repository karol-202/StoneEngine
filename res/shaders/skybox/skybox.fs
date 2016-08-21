#version 330

in vec3 cubemapDir;

uniform samplerCube cubemap;

layout(location = 0) out vec3 fragColor;

void main()
{
	fragColor = texture(cubemap, cubemapDir).rgb;
}