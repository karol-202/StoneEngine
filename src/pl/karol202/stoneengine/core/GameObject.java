package pl.karol202.stoneengine.core;

import pl.karol202.stoneengine.util.Transform;

import java.util.ArrayList;

public class GameObject
{
	private Transform transform;
	private ArrayList<GameComponent> components;
	private ArrayList<GameObject> children;
	
	public GameObject()
	{
		transform = new Transform();
		components = new ArrayList<>();
		children = new ArrayList<>();
	}
	
	public void init()
	{
		components.forEach(GameComponent::init);
		children.forEach(GameObject::init);
	}
	
	public void update()
	{
		components.forEach(GameComponent::update);
		children.forEach(GameObject::update);
	}
	
	public void render()
	{
		components.forEach(GameComponent::render);
		children.forEach(GameObject::render);
	}
	
	public void addChild(GameObject object)
	{
		children.add(object);
	}
	
	public void addComponent(GameComponent component)
	{
		components.add(component);
	}
	
	public void removeChild(GameObject object)
	{
		children.remove(object);
	}
	
	public void removeComponent(GameComponent component)
	{
		components.remove(component);
	}
	
	public Transform getTransform()
	{
		return transform;
	}
}