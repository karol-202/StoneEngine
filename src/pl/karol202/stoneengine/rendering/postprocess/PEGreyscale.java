package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;

public class PEGreyscale extends PostEffect
{
	private class ShaderGreyscale extends Shader
	{
		public ShaderGreyscale()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/greyscale.fs"));
			compileShader();
		}
	}
	
	private ShaderGreyscale shaderGreyscale;
	
	public PEGreyscale()
	{
		shaderGreyscale = new ShaderGreyscale();
	}
	
	@Override
	public void render(PEManager manager)
	{
		manager.render(shaderGreyscale);
	}
}
