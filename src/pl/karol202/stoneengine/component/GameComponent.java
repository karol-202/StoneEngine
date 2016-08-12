package pl.karol202.stoneengine.component;

import pl.karol202.stoneengine.core.GameObject;

public abstract class GameComponent
{
	private GameObject gameObject;
	private boolean enabled;
	
	public GameComponent()
	{
		enabled = true;
	}
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render();
	
	public GameObject getGameObject()
	{
		return gameObject;
	}
	
	public void setGameObject(GameObject gameObject)
	{
		this.gameObject = gameObject;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}