package pl.karol202.stoneengine.rendering.shader;

public class DebugTextureShader extends Shader
{
	public DebugTextureShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/debug/texture.vs"));
		addFragmentShader(loadShader("./res/shaders/debug/texture.fs"));
		compileShader();
	}
}
