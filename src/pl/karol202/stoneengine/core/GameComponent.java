package pl.karol202.stoneengine.core;

public abstract class GameComponent
{
	private GameObject gameObject;
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render();
	
	public GameObject getGameObject()
	{
		return gameObject;
	}
	
	void setGameObject(GameObject gameObject)
	{
		this.gameObject = gameObject;
	}
}