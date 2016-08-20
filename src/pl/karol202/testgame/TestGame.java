package pl.karol202.testgame;

import pl.karol202.stoneengine.component.FPPController;
import pl.karol202.stoneengine.component.MouseLocker;
import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.rendering.*;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.camera.DebugCamera;
import pl.karol202.stoneengine.rendering.camera.ToScreenCamera;
import pl.karol202.stoneengine.rendering.light.DirectionalLight;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL11.glClearColor;

public class TestGame implements Game
{
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private CoreEngine engine;
	private GameObject root;
	
	private TestGame()
	{
		engine = new CoreEngine(this);
		root = new GameObject();
		
		engine.setSize(WIDTH, HEIGHT);
		engine.start();
	}
	
	@Override
	public void init()
	{
		//ForwardRendering.setTestShader(new BasicShader());
		
		glClearColor(0.1f, 0.1f, 0.2f, 1f);
		ForwardRendering.setAmbientLight(new Light(new Vector3f(0.18f, 0.19f, 0.2f), 1f));
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1f, 1f, 1f), 1f);
		//PointLight pointLight = new PointLight(new Vector3f(1f, 1f, 1f), 1f, 0f, 0f, 5f);
		/*SpotLight spotLight = new SpotLight(new Vector3f(1f, 1f, 1f), 1f);
		spotLight.setAttenLinear(0f);
		spotLight.setAttenQuadratic(1f);
		spotLight.setRange(2f);
		spotLight.setInnerAngle(40f);
		spotLight.setOuterAngle(60f);*/
		GameObject lightObject = new GameObject();
		lightObject.addComponent(directionalLight);
		lightObject.getTransform().setRotation(35f, 20f, 0f);
		root.addChild(lightObject);
		
		Mesh mesh = Mesh.loadMesh("./res/meshes/scene.obj");
		Material material = new Material();
		material.setDiffuseColor(new Vector3f(1f, 1f, 1f));
		material.setDiffuseTexture(Texture.loadTexture("./res/textures/box.png"));
		material.setSpecularColor(new Vector3f(0.4f, 0.4f, 0.4f));
		material.setSpecularTexture(Texture.loadTexture("./res/textures/box_spec.png"));
		material.setAmbientOcclussionIntensity(0.5f);
		material.setAmbientOcclussionTexture(Texture.loadTexture("./res/textures/box_occ.png"));
		//material.setNormalMapIntensity(-0.35f);
		//material.setNormalMap(Texture.loadTexture("./res/textures/box_norm.png"));
		MeshRenderer renderer = new MeshRenderer(mesh, material);
		GameObject triangle = new GameObject();
		triangle.addComponent(renderer);
		//triangle.getTransform().setTranslation(0f, 0f, 2f);
		//triangle.getTransform().setScale(3f, 3f, 3f);
		root.addChild(triangle);
		
		Camera debugCamera = new DebugCamera(0, 0, 300, 300, directionalLight.getShadowmap());
		GameObject dcObject = new GameObject();
		dcObject.addComponent(debugCamera);
		root.addChild(dcObject);
		
		Camera camera = new ToScreenCamera(WIDTH, HEIGHT);
		FPPController controller = new FPPController(3f, 0.4f);
		GameObject camObject = new GameObject();
		camObject.addComponent(camera);
		camObject.addComponent(controller);
		camObject.getTransform().setTranslation(0f, 1f, -3f);
		root.addChild(camObject);
		
		MouseLocker mouseLocker = new MouseLocker(true);
		root.addComponent(mouseLocker);
		
		root.init();
		
		Input.setCursorLocked(true);
	}
	
	@Override
	public void update()
	{
		root.update();
	}
	
	@Override
	public void render()
	{
		ForwardRendering.renderAll();
	}
	
	@Override
	public String getTitle()
	{
		return "Gra testowa - StoneEngine";
	}
	
	public static void main(String[] args)
	{
		new TestGame();
	}
}
