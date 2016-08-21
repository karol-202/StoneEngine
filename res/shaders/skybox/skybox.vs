#version 330

layout(location = 0) in vec3 vertPos;

uniform mat4 V;
uniform mat4 P;

out vec3 cubemapDir;

void main()
{
	mat4 view = mat4(mat3(V));
	gl_Position = (P * view * vec4(vertPos, 1)).xyww;
	cubemapDir = vertPos;
}