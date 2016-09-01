package pl.karol202.stoneengine.rendering.postprocess;

import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector2f;

public class PEKernel extends PostEffect
{
	private class ShaderKernel extends Shader
	{
		public ShaderKernel()
		{
			super();
			addVertexShader(loadShader("./res/shaders/posteffects/posteffect.vs"));
			addFragmentShader(loadShader("./res/shaders/posteffects/kernel.fs"));
			compileShader();
			addUniform("kernelSize");
			for(int i = 0; i < 25; i++) addUniform("kernel[" + i + "]");
			for(int i = 0; i < 25; i++) addUniform("kernelOffset[" + i + "]");
		}
		
		public void updateShader(float[][] kernel, Vector2f[][] kernelOffset)
		{
			setUniform("kernelSize", kernel.length * kernel[0].length);
			for(int i = 0; i < kernel.length; i++)
			{
				for(int j = 0; j < kernel[0].length; j++)
				{
					setUniform("kernel[" + (i * kernel[0].length + j) + "]", kernel[i][j]);
					setUniform("kernelOffset[" + (i * kernel[0].length + j) + "]", kernelOffset[i][j]);
				}
			}
		}
	}
	
	private ShaderKernel shaderKernel;
	private float[][] kernel;
	private Vector2f[][] kernelOffset;
	
	public PEKernel()
	{
		shaderKernel = new ShaderKernel();
		initIdentity(1 / 300f);
	}
	
	@Override
	public void render(PEManager manager)
	{
		shaderKernel.bind();
		shaderKernel.updateShader(kernel, kernelOffset);
		manager.render(shaderKernel);
	}
	
	public PEKernel initIdentity(float value)
	{
		kernel = new float[3][3];
		kernel[0][0] = 0; kernel[1][0] = 0; kernel[2][0] = 0;
		kernel[0][1] = 0; kernel[1][1] = 1; kernel[2][1] = 0;
		kernel[0][2] = 0; kernel[1][2] = 0; kernel[2][2] = 0;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel initSharpen(float value)
	{
		kernel = new float[3][3];
		kernel[0][0] = 0; kernel[1][0] = -1; kernel[2][0] = 0;
		kernel[0][1] = -1; kernel[1][1] = 5; kernel[2][1] = -1;
		kernel[0][2] = 0; kernel[1][2] = -1; kernel[2][2] = 0;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel initEdgeDetection1(float value)
	{
		kernel = new float[3][3];
		kernel[0][0] = 1; kernel[1][0] = 0; kernel[2][0] = -1;
		kernel[0][1] = 0; kernel[1][1] = 0; kernel[2][1] = 0;
		kernel[0][2] = -1; kernel[1][2] = 0; kernel[2][2] = 1;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel initEdgeDetection2(float value)
	{
		kernel = new float[3][3];
		kernel[0][0] = 0; kernel[1][0] = 1; kernel[2][0] = 0;
		kernel[0][1] = 1; kernel[1][1] = -4; kernel[2][1] = 1;
		kernel[0][2] = 0; kernel[1][2] = 1; kernel[2][2] = 0;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel initEdgeDetection3(float value)
	{
		kernel = new float[3][3];
		kernel[0][0] = -1; kernel[1][0] = -1; kernel[2][0] = -1;
		kernel[0][1] = -1; kernel[1][1] = 8; kernel[2][1] = -1;
		kernel[0][2] = -1; kernel[1][2] = -1; kernel[2][2] = -1;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel initBoxBlur(float value)
	{
		kernel = new float[3][3];
		float fract = 1 / 9f;
		kernel[0][0] = fract; kernel[1][0] = fract; kernel[2][0] = fract;
		kernel[0][1] = fract; kernel[1][1] = fract; kernel[2][1] = fract;
		kernel[0][2] = fract; kernel[1][2] = fract; kernel[2][2] = fract;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel initGaussianBlur(float value)
	{
		kernel = new float[3][3];
		kernel[0][0] = 1 / 16f; kernel[1][0] = 2 / 16f; kernel[2][0] = 1 / 16f;
		kernel[0][1] = 2 / 16f; kernel[1][1] = 4 / 16f; kernel[2][1] = 2 / 16f;
		kernel[0][2] = 1 / 16f; kernel[1][2] = 2 / 16f; kernel[2][2] = 1 / 16f;
		setDefaultOffset(value);
		return this;
	}
	
	public PEKernel setKernel(float[][] kernel, Vector2f[][] offset)
	{
		if(offset.length != kernel.length || offset[0].length != kernel[0].length)
			throw new RuntimeException("Cannot set kernel matrix: invalid size of offset array.");
		this.kernel = kernel;
		this.kernelOffset = offset;
		return this;
	}
	
	public Vector2f[][] getOffset()
	{
		return kernelOffset;
	}
	
	public PEKernel setOffset(Vector2f[][] offset)
	{
		if(offset.length != kernel.length || offset[0].length != kernel[0].length)
			throw new RuntimeException("Cannot set kernel matrix: invalid size of offset array.");
		kernelOffset = offset;
		return this;
	}
	
	public PEKernel setDefaultOffset(float value)
	{
		kernelOffset = new Vector2f[kernel.length][kernel[0].length];
		kernelOffset[0][0] = new Vector2f(-value, -value);
		kernelOffset[1][0] = new Vector2f(0, -value);
		kernelOffset[2][0] = new Vector2f(value, -value);
		kernelOffset[0][1] = new Vector2f(-value, 0);
		kernelOffset[1][1] = new Vector2f(0, 0);
		kernelOffset[2][1] = new Vector2f(value, 0);
		kernelOffset[0][2] = new Vector2f(-value, value);
		kernelOffset[1][2] = new Vector2f(0, value);
		kernelOffset[2][2] = new Vector2f(value, value);
		return this;
	}
	
	public Vector2f getKernelSize()
	{
		return new Vector2f(kernel.length, kernel[0].length);
	}
}