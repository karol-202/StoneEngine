package pl.karol202.stoneengine.core;

import pl.karol202.stoneengine.component.GameComponent;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;

import java.util.ArrayList;

public class GameObject
{
	private GameObject parent;
	private Transform transform;
	private Matrix4f transformation;
	private boolean enabled;
	private ArrayList<GameComponent> components;
	private ArrayList<GameObject> children;
	private Transform previousTransform;
	
	public GameObject()
	{
		parent = null;
		transform = new Transform();
		transformation = transform.getTransformation();
		enabled = true;
		components = new ArrayList<>();
		children = new ArrayList<>();
		previousTransform = new Transform(transform);
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
		if(!previousTransform.equals(transform))
		{
			transformation = transform.getTransformation();
			previousTransform = new Transform(transform);
		}
		
		components.forEach((comp) -> { if(comp.isEnabled()) comp.update(); });
		children.forEach(GameObject::update);
	}
	
	public void render(Shader shader, Light light)
	{
		if(!enabled) return;
		components.forEach((comp) -> { if(comp.isEnabled()) comp.render(shader, light); });
		children.forEach((object) -> object.render(shader, light));
	}
	
	public void addChild(GameObject object)
	{
		children.add(object);
		object.parent = this;
	}
	
	public void addComponent(GameComponent component)
	{
		components.add(component);
		component.setGameObject(this);
	}
	
	public void removeChild(GameObject object)
	{
		children.remove(object);
		object.parent = null;
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
	
	public Transform getTransform()
	{
		return transform;
	}
	
	public Matrix4f getTransformation()
	{
		return transformation;
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