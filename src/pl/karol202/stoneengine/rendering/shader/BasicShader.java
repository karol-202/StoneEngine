package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class BasicShader extends Shader
{
	public BasicShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/basic/basic.vs"));
		addFragmentShader(loadShader("./res/shaders/basic/basic.fs"));
		compileShader();
		addUniform("MVP");
		addUniform("texture");
		addUniform("color");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		if(material.getDiffuseTexture() != null)
		{
			glActiveTexture(GL_TEXTURE0);
			material.getDiffuseTexture().bind();
		}
		
		Matrix4f MVP = camera.getViewProjectionMatrix().mul(transformation);
		setUniform("MVP", MVP);
		setUniform("texture", 0);
		setUniform("color", material.getDiffuseColor());
	}
}
