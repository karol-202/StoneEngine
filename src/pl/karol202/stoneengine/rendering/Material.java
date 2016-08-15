package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Vector3f;

public class Material
{
	private Vector3f diffuseColor;
	private Texture diffuseTexture;
	
	private Vector3f specularColor;
	private Texture specularTexture;
	
	public Vector3f getDiffuseColor()
	{
		return diffuseColor;
	}
	
	public void setDiffuseColor(Vector3f diffuseColor)
	{
		this.diffuseColor = diffuseColor;
	}
	
	public Texture getDiffuseTexture()
	{
		return diffuseTexture;
	}
	
	public void setDiffuseTexture(Texture diffuseTexture)
	{
		this.diffuseTexture = diffuseTexture;
	}
	
	public Vector3f getSpecularColor()
	{
		return specularColor;
	}
	
	public void setSpecularColor(Vector3f specularColor)
	{
		this.specularColor = specularColor;
	}
	
	public Texture getSpecularTexture()
	{
		return specularTexture;
	}
	
	public void setSpecularTexture(Texture specularTexture)
	{
		this.specularTexture = specularTexture;
	}
}