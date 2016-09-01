package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;

public class PEInvert extends PostEffect
{
	private class ShaderInvert extends Shader
	{
		public ShaderInvert()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/invert.fs"));
			compileShader();
		}
	}
	
	private ShaderInvert shaderInvert;
	
	public PEInvert()
	{
		shaderInvert = new ShaderInvert();
	}
	
	@Override
	public void render(PEManager manager)
	{
		manager.render(shaderInvert);
	}
}
