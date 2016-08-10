package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.core.GameComponent;
import pl.karol202.stoneengine.rendering.shader.Shader;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Shader shader;
	
	public MeshRenderer(Mesh mesh, Shader shader)
	{
		this.mesh = mesh;
		this.shader = shader;
	}
	
	@Override
	public void init() { }
	
	@Override
	public void update() { }
	
	@Override
	public void render()
	{
		shader.bind();
		mesh.draw();
	}
}
