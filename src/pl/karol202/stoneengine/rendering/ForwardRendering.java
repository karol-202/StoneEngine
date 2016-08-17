package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.rendering.shader.ForwardAmbientShader;
import pl.karol202.stoneengine.rendering.shader.ForwardSpotShader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ForwardRendering
{
	private static ForwardAmbientShader ambientShader = new ForwardAmbientShader();
	//private static ForwardDirectionalShader directionalShader = new ForwardDirectionalShader();
	//private static ForwardPointShader pointShader = new ForwardPointShader();
	private static ForwardSpotShader spotShader = new ForwardSpotShader();
	
	private static Light ambientLight;
	private static ArrayList<DirectionalLight> directionalLights = new ArrayList<>();
	private static ArrayList<PointLight> pointLights = new ArrayList<>();
	private static ArrayList<SpotLight> spotLights = new ArrayList<>();
	
	public static void render(GameObject gameObject)
	{
		gameObject.render(ambientShader, ambientLight);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		//directionalLights.forEach((light) -> { if(light.isEnabled()) gameObject.render(directionalShader, light); });
		//pointLights.forEach((light) -> { if(light.isEnabled()) gameObject.render(pointShader, light); });
		spotLights.forEach((light) -> { if(light.isEnabled()) gameObject.render(spotShader, light); });
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	public static Light getAmbientLight()
	{
		return ambientLight;
	}
	
	public static void setAmbientLight(Light light)
	{
		ambientLight = light;
	}
	
	public static void addDirectionalLight(DirectionalLight light)
	{
		directionalLights.add(light);
	}
	
	public static void addPointLight(PointLight light)
	{
		pointLights.add(light);
	}
	
	public static void addSpotLight(SpotLight light)
	{
		spotLights.add(light);
	}
}
