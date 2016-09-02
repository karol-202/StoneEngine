#version 330

in vec2 uv;

uniform sampler2D srcColor;
uniform sampler2D srcBright;
uniform bool vertical;
uniform float blurWeights[5];

layout(location = 0) out vec3 fragColor;
layout(location = 1) out vec3 blur;

void main()
{
	vec2 pixel = 1.0 / textureSize(srcBright, 0);
	blur = texture2D(srcBright, uv).rgb * blurWeights[0];
	for(int i = 1; i < 5; i++)
	{
		if(vertical)
		{
			blur += texture2D(srcBright, uv - vec2(0, pixel.y * i)).rgb * blurWeights[i];
			blur += texture2D(srcBright, uv + vec2(0, pixel.y * i)).rgb * blurWeights[i];
		}
		else
        {
       		blur += texture2D(srcBright, uv - vec2(pixel.x * i, 0)).rgb * blurWeights[i];
       		blur += texture2D(srcBright, uv + vec2(pixel.x * i, 0)).rgb * blurWeights[i];
       	}
	}
	fragColor = texture2D(srcColor, uv).rgb;
}