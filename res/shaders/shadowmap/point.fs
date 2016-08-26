#version 330

in vec4 pos;

uniform vec3 lightPos;
uniform float shadowRange;
uniform float shadowZNear;

void main()
{
	float distance = length(pos.xyz - lightPos);
	distance = clamp((distance - shadowZNear) / shadowRange, 0, 1);
	gl_FragDepth = distance;
}