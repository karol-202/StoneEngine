package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.*;

public class PEBloom extends PostEffect
{
	private class ShaderExtractBright extends Shader
	{
		public ShaderExtractBright()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/bloom_extract.fs"));
			compileShader();
		}
	}
	
	private class ShaderGaussianBlur extends Shader
	{
		public ShaderGaussianBlur()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/bloom_blur.fs"));
			compileShader();
			addUniform("srcColor");
			addUniform("srcBright");
			addUniform("vertical");
			for(int i = 0; i < 5; i++) addUniform("blurWeights[" + i + "]");
		}
		
		public void updateShader(boolean vertical, float[] weights)
		{
			setUniform("srcColor", 0);
			setUniform("srcBright", 1);
			setUniform("vertical", vertical);
			for(int i = 0; i < 5; i++) setUniform("blurWeights[" + i + "]", weights[i]);
		}
	}
	
	private class ShaderJoin extends Shader
	{
		public ShaderJoin()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/bloom_join.fs"));
			compileShader();
			addUniform("srcColor");
			addUniform("srcBlur");
		}
		
		public void updateShader()
		{
			setUniform("srcColor", 0);
			setUniform("srcBlur", 1);
		}
	}
	
	private ShaderExtractBright shaderExtractBright;
	private ShaderGaussianBlur shaderGaussianBlur;
	private ShaderJoin shaderJoin;
	private float[] weights;
	private int iterations;
	
	public PEBloom(int iterations)
	{
		this.shaderExtractBright = new ShaderExtractBright();
		this.shaderGaussianBlur = new ShaderGaussianBlur();
		this.shaderJoin = new ShaderJoin();
		this.weights = new float[] { 0.227027f, 0.1945946f, 0.1216216f, 0.054054f, 0.016216f };
		this.iterations = iterations;
	}
	
	@Override
	public void render(PEManager manager)
	{
		manager.render(shaderExtractBright, false);
		
		shaderGaussianBlur.bind();
		for(int i = 0; i < iterations * 2; i++)
		{
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, manager.getCurrentFramebuffer().getColorTexture());
			glActiveTexture(GL_TEXTURE1);
			glBindTexture(GL_TEXTURE_2D, manager.getCurrentFramebuffer().getColorTextureSecond());
			shaderGaussianBlur.updateShader(i % 2 == 1, weights);
			manager.render(shaderGaussianBlur, true);
		}
		
		shaderJoin.bind();
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, manager.getCurrentFramebuffer().getColorTexture());
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, manager.getCurrentFramebuffer().getColorTextureSecond());
		shaderJoin.updateShader();
		manager.render(shaderJoin, true);
	}
	
	public float[] getWeights()
	{
		return weights;
	}
	
	public void setWeights(float[] weights)
	{
		this.weights = weights;
	}
	
	public int getIterations()
	{
		return iterations;
	}
	
	public void setIterations(int iterations)
	{
		this.iterations = iterations;
	}
}