package pl.karol202.stoneengine.core;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.util.Transform;

import java.util.ArrayList;

public class GameObject
{
	private GameObject parent;
	private Transform transform;
	private boolean enabled;
	private ArrayList<GameComponent> components;
	private ArrayList<GameObject> children;
	
	public GameObject()
	{
		parent = null;
		transform = new Transform();
		enabled = true;
		components = new ArrayList<>();
		children = new ArrayList<>();
	}
	
	public void init()
	{
		if(!enabled) return;
		components.forEach((comp) -> { if(comp.isEnabled()) comp.init(); });
		children.forEach(GameObject::init);
	}
	
	public void update()
	{
		if(!enabled) return;
		components.forEach((comp) -> { if(comp.isEnabled()) comp.update(); });
		children.forEach(GameObject::update);
	}
	
	public void render()
	{
		if(!enabled) return;
		components.forEach((comp) -> { if(comp.isEnabled()) comp.render(); });
		children.forEach(GameObject::render);
	}
	
	public void addChild(GameObject object)
	{
		children.add(object);
		object.setParent(this);
	}
	
	public void addComponent(GameComponent component)
	{
		components.add(component);
		component.setGameObject(this);
	}
	
	public void removeChild(GameObject object)
	{
		children.remove(object);
		object.setParent(null);
	}
	
	public void removeComponent(GameComponent component)
	{
		components.remove(component);
		component.setGameObject(null);
	}
	
	public GameObject getParent()
	{
		return parent;
	}
	
	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}
	
	public Transform getTransform()
	{
		return transform;
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