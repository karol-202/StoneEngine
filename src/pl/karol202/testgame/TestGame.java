package pl.karol202.testgame;

import pl.karol202.stoneengine.component.FPPController;
import pl.karol202.stoneengine.component.MouseLocker;
import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.rendering.*;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.SpotLight;
import pl.karol202.stoneengine.rendering.shader.BasicShader;
import pl.karol202.stoneengine.rendering.shader.DebugNormalShader;
import pl.karol202.stoneengine.rendering.shader.Shader;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL11.glClearColor;

public class TestGame implements Game
{
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private static DebugNormalShader debugNormalShader;
	
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
		debugNormalShader = new DebugNormalShader();
		
		glClearColor(0.1f, 0.1f, 0.2f, 1f);
		ForwardRendering.setAmbientLight(new Light(new Vector3f(0.18f, 0.19f, 0.2f), 1f));
		//DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1f, 1f, 1f), 1f);
		//PointLight pointLight = new PointLight(new Vector3f(1f, 1f, 1f), 1f, 0f, 1f, 2f);
		SpotLight spotLight = new SpotLight(new Vector3f(1f, 1f, 1f), 1f);
		spotLight.setAttenLinear(0f);
		spotLight.setAttenQuadratic(1f);
		spotLight.setRange(2f);
		spotLight.setInnerAngle(35f);
		spotLight.setOuterAngle(45f);
		GameObject lightObject = new GameObject();
		//lightObject.addComponent(directionalLight);
		lightObject.getTransform().setTranslation(0f, 1f, 2f);
		lightObject.getTransform().setRotation(90f, 0f, 0f);
		lightObject.addComponent(spotLight);
		root.addChild(lightObject);
		
		Mesh mesh = Mesh.loadMesh("./res/meshes/hammer.obj");
		Material material = new Material();
		material.setDiffuseColor(new Vector3f(1f, 1f, 1f));
		material.setDiffuseTexture(Texture.loadTexture("./res/textures/hammer.png"));
		material.setSpecularColor(new Vector3f(1f, 1f, 1f));
		material.setSpecularTexture(Texture.loadTexture("./res/textures/hammer_spec.png"));
		Shader shader = new BasicShader();
		MeshRenderer renderer = new MeshRenderer(mesh, material, shader);
		GameObject triangle = new GameObject();
		triangle.addComponent(renderer);
		triangle.getTransform().setTranslation(0f, 0f, 2f);
		triangle.getTransform().setScale(3f, 3f, 3f);
		root.addChild(triangle);
		
		Camera camera = new Camera(70f, 0.1f, 100f, (float) WIDTH / HEIGHT);
		FPPController controller = new FPPController(3f, 0.4f);
		GameObject camObject = new GameObject();
		camObject.addComponent(camera);
		camObject.addComponent(controller);
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
		ForwardRendering.render(root);
		//root.render(debugNormalShader, null);
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
