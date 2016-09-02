package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;

public class PEGamma extends PostEffect
{
	private class ShaderGamma extends Shader
	{
		public ShaderGamma()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/gamma.fs"));
			compileShader();
			addUniform("gamma");
		}
		
		public void updateShader(float gamma)
		{
			setUniform("gamma", gamma);
		}
	}
	
	private ShaderGamma shaderGamma;
	private float gamma;
	
	public PEGamma(float gamma)
	{
		this.shaderGamma = new ShaderGamma();
		this.gamma = gamma;
	}
	
	@Override
	public void render(PEManager manager)
	{
		shaderGamma.bind();
		shaderGamma.updateShader(gamma);
		manager.render(shaderGamma, false);
	}
}
