package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;

public class PESScreen extends Shader
{
	public PESScreen()
	{
		super();
		addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
		addFragmentShader(loadShader("./res/shaders/posteffects/screen.fs"));
		compileShader();
	}
}
