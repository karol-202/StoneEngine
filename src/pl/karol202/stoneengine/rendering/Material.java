package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Vector3f;

public class Material
{
	private Vector3f diffuseColor;
	private Texture2D diffuseTexture;
	
	private Vector3f specularColor;
	private Texture2D specularTexture;
	
	private float ambientOcclussionIntensity;
	private Texture2D ambientOcclussionTexture;
	
	private float normalMapIntensity;
	private Texture2D normalMap;
	
	private Vector3f emissiveColor;
	private Texture2D emissiveTexture;
	
	public Vector3f getDiffuseColor()
	{
		return diffuseColor;
	}
	
	public void setDiffuseColor(Vector3f diffuseColor)
	{
		this.diffuseColor = diffuseColor;
	}
	
	public Texture2D getDiffuseTexture()
	{
		return diffuseTexture;
	}
	
	public void setDiffuseTexture(Texture2D diffuseTexture)
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
	
	public Texture2D getSpecularTexture()
	{
		return specularTexture;
	}
	
	public void setSpecularTexture(Texture2D specularTexture)
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
	
	public Texture2D getAmbientOcclussionTexture()
	{
		return ambientOcclussionTexture;
	}
	
	public void setAmbientOcclussionTexture(Texture2D aoTexture)
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
	
	public Texture2D getNormalMap()
	{
		return normalMap;
	}
	
	public void setNormalMap(Texture2D normalMap)
	{
		this.normalMap = normalMap;
	}
	
	public Vector3f getEmissiveColor()
	{
		return emissiveColor;
	}
	
	public void setEmissiveColor(Vector3f emissiveColor)
	{
		this.emissiveColor = emissiveColor;
	}
	
	public Texture2D getEmissiveTexture()
	{
		return emissiveTexture;
	}
	
	public void setEmissiveTexture(Texture2D emissiveTexture)
	{
		this.emissiveTexture = emissiveTexture;
	}
}