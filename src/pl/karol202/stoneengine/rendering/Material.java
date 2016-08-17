package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Vector3f;

public class Material
{
	private Vector3f diffuseColor;
	private Texture diffuseTexture;
	
	private Vector3f specularColor;
	private Texture specularTexture;
	
	private float ambientOcclussionIntensity;
	private Texture ambientOcclussionTexture;
	
	private float normalMapIntensity;
	private Texture normalMap;
	
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
	
	public float getAmbientOcclussionIntensity()
	{
		return ambientOcclussionIntensity;
	}
	
	public void setAmbientOcclussionIntensity(float aoIntensity)
	{
		this.ambientOcclussionIntensity = aoIntensity;
	}
	
	public Texture getAmbientOcclussionTexture()
	{
		return ambientOcclussionTexture;
	}
	
	public void setAmbientOcclussionTexture(Texture aoTexture)
	{
		this.ambientOcclussionTexture = aoTexture;
	}
	
	public float getNormalMapIntensity()
	{
		return normalMapIntensity;
	}
	
	public void setNormalMapIntensity(float normalMapIntensity)
	{
		this.normalMapIntensity = normalMapIntensity;
	}
	
	public Texture getNormalMap()
	{
		return normalMap;
	}
	
	public void setNormalMap(Texture normalMap)
	{
		this.normalMap = normalMap;
	}
}