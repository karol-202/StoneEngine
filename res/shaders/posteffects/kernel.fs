#version 330

const int KERNEL_MAX_SIZE = 25;

in vec2 uv;

uniform sampler2D src;
uniform int kernelSize;
uniform float kernel[KERNEL_MAX_SIZE];
uniform vec2 kernelOffset[KERNEL_MAX_SIZE];

layout(location = 0) out vec3 fragColor;

void main()
{
	for(int i = 0; i < kernelSize; i++)
		fragColor += texture2D(src, uv + kernelOffset[i]).rgb * kernel[i];
}