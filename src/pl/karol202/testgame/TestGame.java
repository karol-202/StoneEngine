package pl.karol202.testgame;

import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;

public class TestGame implements Game
{
	private CoreEngine engine;
	
	private TestGame()
	{
		engine = new CoreEngine(this);
		engine.start();
	}
	
	@Override
	public void update()
	{
		try
		{
			Thread.sleep(1);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void render()
	{
		try
		{
			Thread.sleep(1);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
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
