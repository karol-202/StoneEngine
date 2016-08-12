package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.shader.ForwardAmbientShader;

public class ForwardRendering
{
	private static ForwardAmbientShader ambientShader = new ForwardAmbientShader();
	
	private static Light ambientLight;
	
	public static void render(GameObject gameObject)
	{
		gameObject.render(ambientShader, ambientLight);
	}
	
	public static Light getAmbientLight()
	{
		return ambientLight;
	}
	
	public static void setAmbientLight(Light ambientLight)
	{
		ForwardRendering.ambientLight = ambientLight;
	}
}
