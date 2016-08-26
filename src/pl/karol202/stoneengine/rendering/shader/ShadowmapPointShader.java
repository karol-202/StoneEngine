package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.util.Matrix4f;

public class ShadowmapPointShader extends Shader
{
	public ShadowmapPointShader()
	{
		super();
		addVertexShader(loadShader("./res/shaders/shadowmap/point.vs"));
		addGeomatryShader(loadShader("./res/shaders/shadowmap/point.gs"));
		addFragmentShader(loadShader("./res/shaders/shadowmap/point.fs"));
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
		if(!(light instanceof PointLight))
			throw new RuntimeException("Error during updating shader's uniforms: light passed to shader is of invalid type.");
		PointLight pointLight = (PointLight) light;
		
		setUniform("M", transformation, true);
		setUniform("lightPos", light.getGameObject().getTransform().getTranslation());
		setUniform("shadowRange", pointLight.getShadowRange());
		setUniform("shadowZNear", pointLight.getShadowZNear());
		for(int i = 0; i < 6; i++)
			setUniform("viewProjectionMatrices[" + i + "]", pointLight.getShadowmapViewProjectionMatrices()[i], true);
	}
}