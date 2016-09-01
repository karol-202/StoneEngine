package pl.karol202.stoneengine.rendering.postprocess;

public class PESInvert extends PostEffectShader
{
	public PESInvert()
	{
		super();
		addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
		addFragmentShader(loadShader("./res/shaders/posteffects/invert.fs"));
		compileShader();
	}
	
	@Override
	protected void updateEffect()
	{
	}
}
