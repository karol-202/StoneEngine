package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;

public class PEToneMapping extends PostEffect
{
	private class ShaderToneMapping extends Shader
	{
		public ShaderToneMapping()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/tone.fs"));
			compileShader();
			addUniform("exposure");
		}
		
		public void updateShader(float exposure)
		{
			setUniform("exposure", exposure);
		}
	}
	
	private ShaderToneMapping shaderToneMapping;
	private float exposure;
	
	public PEToneMapping()
	{
		shaderToneMapping = new ShaderToneMapping();
		exposure = 1f;
	}
	
	@Override
	public void render(PEManager manager)
	{
		shaderToneMapping.bind();
		shaderToneMapping.updateShader(exposure);
		manager.render(shaderToneMapping, false);
	}
	
	public float getExposure()
	{
		return exposure;
	}
	
	public void setExposure(float exposure)
	{
		this.exposure = exposure;
	}
}
