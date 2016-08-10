package pl.karol202.stoneengine.rendering.shader;

public class BasicShader extends Shader
{
	public BasicShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/basic/basic.vs"));
		addFragmentShader(loadShader("./res/shaders/basic/basic.fs"));
		compileShader();
	}
}
