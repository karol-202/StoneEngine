package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextureRenderer extends GameComponent
{
	private Vector2f start;
	private Vector2f end;
	private Mesh mesh;
	private Texture2D texture;
	
	public TextureRenderer(Vector2f start, Vector2f end, Texture2D texture)
	{
		super();
		this.start = start;
		this.end = end;
		this.texture = texture;
		createQuad();
	}
	
	@Override
	public void init() { }
	
	@Override
	public void update() { }
	
	public void render(Shader shader)
	{
		glActiveTexture(GL_TEXTURE0);
		texture.bind();
		
		shader.bind();
		shader.updateShader(null, null, null, null);
		mesh.render();
	}
	
	private void createQuad()
	{
		Vector3f normal = new Vector3f();
		Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(start.getX(), start.getY(), 0f), new Vector2f(0f, 0f), normal),
										   new Vertex(new Vector3f(end.getX(), start.getY(), 0f), new Vector2f(1f, 0f), normal),
										   new Vertex(new Vector3f(end.getX(), end.getY(), 0f), new Vector2f(1f, 1f), normal),
										   new Vertex(new Vector3f(start.getX(), end.getY(), 0f), new Vector2f(0f, 1f), normal) };
		int[] indices = new int[] { 0, 2, 1, 0, 3, 2 };
		mesh = new Mesh(vertices, indices, true);
	}
	
	public Texture2D getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture2D texture)
	{
		this.texture = texture;
	}
}
