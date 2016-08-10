package pl.karol202.testgame;

import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.rendering.Mesh;
import pl.karol202.stoneengine.rendering.MeshRenderer;
import pl.karol202.stoneengine.rendering.Vertex;
import pl.karol202.stoneengine.rendering.shader.BasicShader;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL11.glClearColor;

public class TestGame implements Game
{
	private CoreEngine engine;
	private GameObject root;
	
	private TestGame()
	{
		engine = new CoreEngine(this);
		root = new GameObject();
		engine.start();
	}
	
	@Override
	public void init()
	{
		glClearColor(0.1f, 0.1f, 0.2f, 1f);
		Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-1, -1, 0)),
										  new Vertex(new Vector3f(0, 1, 0)),
										  new Vertex(new Vector3f(1, -1, 0))};
		Mesh mesh = new Mesh(vertices);
		Shader shader = new BasicShader();
		MeshRenderer renderer = new MeshRenderer(mesh, shader);
		GameObject triangle = new GameObject();
		triangle.addComponent(renderer);
		root.addChild(triangle);
		
		root.init();
	}
	
	@Override
	public void update()
	{
		root.update();
	}
	
	@Override
	public void render()
	{
		root.render();
	}
	
	@Override
	public String getTitle()
	{
		return "Gra testowa - StoneEngine";
	}
	
	public static void main(String[] args)
	{
		new TestGame();
	}
}
