package pl.karol202.testgame;

import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.rendering.Mesh;
import pl.karol202.stoneengine.rendering.Vertex;
import pl.karol202.stoneengine.util.Vector3f;

public class TestGame implements Game
{
	private CoreEngine engine;
	private Mesh mesh;
	
	private TestGame()
	{
		engine = new CoreEngine(this);
		engine.start();
	}
	
	@Override
	public void init()
	{
		Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(-1, -1, 0)),
										  new Vertex(new Vector3f(0, 1, 0)),
										  new Vertex(new Vector3f(1, -1, 0))};
		mesh = new Mesh(vertices);
	}
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void render()
	{
		mesh.draw();
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
