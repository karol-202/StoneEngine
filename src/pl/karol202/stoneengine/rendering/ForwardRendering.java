package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.shader.ForwardAmbientShader;
import pl.karol202.stoneengine.rendering.shader.ForwardDirectionalShader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ForwardRendering
{
	private static ForwardAmbientShader ambientShader = new ForwardAmbientShader();
	private static ForwardDirectionalShader directionalShader = new ForwardDirectionalShader();
	
	private static Light ambientLight;
	private static ArrayList<DirectionalLight> directionalLights = new ArrayList<>();
	
	public static void render(GameObject gameObject)
	{
		gameObject.render(ambientShader, ambientLight);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		directionalLights.forEach((light) -> gameObject.render(directionalShader, light));
		
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
}
