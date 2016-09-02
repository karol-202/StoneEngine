package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.rendering.shader.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ForwardRendering
{
	private static ForwardBaseShader baseShader = new ForwardBaseShader();
	private static ForwardDirectionalShader directionalShader = new ForwardDirectionalShader();
	private static ForwardPointShader pointShader = new ForwardPointShader();
	private static ForwardSpotShader spotShader = new ForwardSpotShader();
	private static ShadowmapDirectionalShader shadowmapDirectionalShader = new ShadowmapDirectionalShader();
	private static ShadowmapPointShader shadowmapPointShader = new ShadowmapPointShader();
	private static ShadowmapSpotShader shadowmapSpotShader = new ShadowmapSpotShader();
	private static DebugTextureShader debugTextureShader = new DebugTextureShader();
	private static SkyboxShader skyboxShader = new SkyboxShader();
	
	private static Light ambientLight;
	private static ArrayList<DirectionalLight> directionalLights = new ArrayList<>();
	private static ArrayList<PointLight> pointLights = new ArrayList<>();
	private static ArrayList<SpotLight> spotLights = new ArrayList<>();
	
	private static ArrayList<Camera> cameras = new ArrayList<>();
	private static ArrayList<MeshRenderer> meshRenderers = new ArrayList<>();
	private static SkyboxRenderer skyboxRenderer;
	
	private static Shader testShader;
	
	public static void renderAll()
	{
		cameras.forEach(Camera::render);
	}
	
	public static void renderCamera(Camera camera)
	{
		if(testShader != null) renderMeshes(testShader, null, camera);
		else
		{
			renderMeshes(baseShader, ambientLight, camera);
			
			glDepthMask(false);
			glDepthFunc(GL_EQUAL);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);
			
			directionalLights.forEach(light -> { if(light.isEnabled()) renderMeshes(directionalShader, light, camera); });
			pointLights.forEach(light -> { if(light.isEnabled()) renderMeshes(pointShader, light, camera); });
			spotLights.forEach(light -> { if(light.isEnabled()) renderMeshes(spotShader, light, camera); });
			
			glDisable(GL_BLEND);
			glDepthFunc(GL_LEQUAL);
			
			if(skyboxRenderer != null) skyboxRenderer.render(skyboxShader, camera);
			
			glDepthMask(true);
		}
	}
	
	public static void renderShadowmapDirectional(Camera camera)
	{
		renderMeshes(shadowmapDirectionalShader, null, camera);
	}
	
	public static void renderShadowmapPoint(Camera camera, PointLight light)
	{
		renderMeshes(shadowmapPointShader, light, camera);
	}
	
	public static void renderShadowmapSpot(Camera camera, SpotLight light)
	{
		renderMeshes(shadowmapSpotShader, light, camera);
	}
	
	public static void renderTextureToScreen(TextureRenderer renderer)
	{
		renderer.render(debugTextureShader, true);
	}
	
	private static void renderMeshes(Shader shader, Light light, Camera camera)
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
	
	public static SkyboxRenderer getSkyboxRenderer()
	{
		return skyboxRenderer;
	}
	
	public static void setSkyboxRenderer(SkyboxRenderer skyboxRenderer)
	{
		ForwardRendering.skyboxRenderer = skyboxRenderer;
	}
	
	public static void setTestShader(Shader shader)
	{
		testShader = shader;
	}
}