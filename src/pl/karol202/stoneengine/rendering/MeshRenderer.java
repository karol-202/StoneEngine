package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.shader.Shader;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material)
	{
		super();
		this.mesh = mesh;
		this.material = material;
	}
	
	@Override
	public void init() { }
	
	@Override
	public void update() { }
	
	@Override
	public void render(Shader shader, Light light)
	{
		shader.bind();
		shader.updateShader(getGameObject().getTransformation(), material, light);
		mesh.draw();
	}
}
