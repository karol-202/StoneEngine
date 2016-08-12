package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.rendering.shader.Shader;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;
	private Shader shader;
	
	public MeshRenderer(Mesh mesh, Material material, Shader shader)
	{
		super();
		this.mesh = mesh;
		this.material = material;
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
		shader.updateShader(getGameObject().getTransform(), material);
		mesh.draw();
	}
}
