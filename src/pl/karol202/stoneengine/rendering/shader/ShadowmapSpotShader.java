package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.util.Matrix4f;

public class ShadowmapSpotShader extends Shader
{
	public ShadowmapSpotShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/shadowmap/spot.vs"));
		addGeometryShader(loadShader("./res/shaders/shadowmap/spot.gs"));
		addFragmentShader(loadShader("./res/shaders/shadowmap/spot.fs"));
		compileShader();
		addUniform("M");
		addUniform("lightPos");
		addUniform("shadowRange");
		addUniform("shadowZNear");
		for(int i = 0; i < 6; i++) addUniform("viewProjectionMatrices[" + i + "]");
	}
	
	@Override
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		super.updateShader(transformation, material, light, camera);
		if(!(light instanceof SpotLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		SpotLight pointLight = (SpotLight) light;
		
		setUniform("M", transformation, true);
		setUniform("lightPos", light.getGameObject().getTransform().getTranslation());
		setUniform("shadowRange", pointLight.getShadowRange());
		setUniform("shadowZNear", pointLight.getShadowZNear());
		for(int i = 0; i < 6; i++)
			setUniform("viewProjectionMatrices[" + i + "]", pointLight.getShadowmapViewProjectionMatrices()[i], true);
	}
}
