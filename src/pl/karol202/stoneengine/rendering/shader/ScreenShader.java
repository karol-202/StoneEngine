package pl.karol202.stoneengine.rendering.shader;

public class ScreenShader extends Shader
{
	public ScreenShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
		addFragmentShader(loadShader("./res/shaders/screen.fs"));
		compileShader();
	}
}
