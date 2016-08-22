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
	private static ForwardAmbientShader ambientShader = new ForwardAmbientShader();
	private static ForwardDirectionalShader directionalShader = new ForwardDirectionalShader();
	private static ForwardPointShader pointShader = new ForwardPointShader();
	private static ForwardSpotShader spotShader = new ForwardSpotShader();
	private static ShadowmapShader shadowmapShader = new ShadowmapShader();
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
			renderMeshes(ambientShader, ambientLight, camera);
			
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
	
	public static void renderShadowmap(Camera camera)
	{
		renderMeshes(shadowmapShader, null, camera);
	}
	
	public static void renderDebugTexture(TextureRenderer renderer)
	{
		renderer.render(debugTextureShader);
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
	
	public static Cubemap getSkybox()
	{
		return skyboxRenderer != null ? skyboxRenderer.getCubemap() : null;
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