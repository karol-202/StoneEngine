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
	public void init()
	{
		ForwardRendering.addMeshRenderer(this);
	}
	
	@Override
	public void update() { }
	
	public void render(Shader shader, Light light, Camera camera)
	{
		shader.bind();
		shader.updateShader(getGameObject().getTransformation(), material, light, camera);
		mesh.draw();
	}
}
