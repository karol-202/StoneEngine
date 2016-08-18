package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.rendering.shader.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ForwardRendering
{
	private static ForwardAmbientShader ambientShader = new ForwardAmbientShader();
	private static ForwardDirectionalShader directionalShader = new ForwardDirectionalShader();
	private static ForwardPointShader pointShader = new ForwardPointShader();
	private static ForwardSpotShader spotShader = new ForwardSpotShader();
	
	private static Light ambientLight;
	private static ArrayList<DirectionalLight> directionalLights = new ArrayList<>();
	private static ArrayList<PointLight> pointLights = new ArrayList<>();
	private static ArrayList<SpotLight> spotLights = new ArrayList<>();
	
	private static ArrayList<MeshRenderer> meshRenderers = new ArrayList<>();
	
	private static ArrayList<Camera> cameras = new ArrayList<>();
	
	private static Shader testShader;
	
	public static void renderAll()
	{
		cameras.forEach(Camera::render);
	}
	
	public static void renderCamera(Camera camera)
	{
		if(testShader != null) renderShader(testShader, null, camera);
		else
		{
			renderShader(ambientShader, ambientLight, camera);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);
			glDepthMask(false);
			glDepthFunc(GL_EQUAL);
			
			directionalLights.forEach(light -> { if(light.isEnabled()) renderShader(directionalShader, light, camera); });
			pointLights.forEach(light -> { if(light.isEnabled()) renderShader(pointShader, light, camera); });
			spotLights.forEach(light -> { if(light.isEnabled()) renderShader(spotShader, light, camera); });
			
			glDepthFunc(GL_LESS);
			glDepthMask(true);
			glDisable(GL_BLEND);
		}
	}
	
	public static void renderShader(Shader shader, Light light, Camera camera)
	{
		meshRenderers.forEach(meshRenderer -> meshRenderer.render(shader, light, camera));
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
	
	public static void addMeshRenderer(MeshRenderer renderer)
	{
		meshRenderers.add(renderer);
	}
	
	public static void addCamera(Camera camera)
	{
		cameras.add(camera);
	}
	
	public static void setTestShader(Shader shader)
	{
		testShader = shader;
	}
}