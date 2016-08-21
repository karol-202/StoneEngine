package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class SkyboxRenderer
{
	private Cubemap cubemap;
	private Mesh mesh;
	
	public SkyboxRenderer(Cubemap cubemap)
	{
		this.cubemap = cubemap;
		createCube();
	}
	
	public void render(Shader shader, Camera camera)
	{
		glActiveTexture(GL_TEXTURE0);
		cubemap.bind();
		shader.bind();
		shader.updateShader(null, null, null, camera);
		mesh.render();
	}
	
	private void createCube()
	{
		Vector2f uv = new Vector2f();
		Vector3f normal = new Vector3f();
		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1f, -1f, -1f), uv, normal),
										   new Vertex(new Vector3f(-1f, -1f, 1f), uv, normal),
										   new Vertex(new Vector3f(1f, -1, -1f), uv, normal),
										   new Vertex(new Vector3f(1f, -1f, 1f), uv, normal),
										   new Vertex(new Vector3f(-1f, 1f, -1f), uv, normal),
										   new Vertex(new Vector3f(-1f, 1f, 1f), uv, normal),
										   new Vertex(new Vector3f(1f, 1, -1f), uv, normal),
										   new Vertex(new Vector3f(1f, 1f, 1f), uv, normal)};
		int[] indices = new int[] { 0, 1, 3,
									0, 3, 2,
									7, 5, 4,
									7, 4, 6,
									0, 6, 4,
									0, 2, 6,
									1, 5, 7,
									1, 7, 3,
									0, 4, 5,
									0, 5, 1,
									3, 7, 6,
									3, 6, 2};
		mesh = new Mesh(vertices, indices, true);
	}
	
	public Cubemap getCubemap()
	{
		return cubemap;
	}
	
	public void setCubemap(Cubemap cubemap)
	{
		this.cubemap = cubemap;
	}
}
