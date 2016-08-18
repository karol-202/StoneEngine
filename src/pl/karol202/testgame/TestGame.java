package pl.karol202.testgame;

import pl.karol202.stoneengine.component.FPPController;
import pl.karol202.stoneengine.component.MouseLocker;
import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.rendering.*;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.rendering.shader.BasicShader;
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
		ForwardRendering.setTestShader(new BasicShader());
		
		glClearColor(0.1f, 0.1f, 0.2f, 1f);
		ForwardRendering.setAmbientLight(new Light(new Vector3f(0.18f, 0.19f, 0.2f), 1f));
		//DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1f, 1f, 1f), 1f);
		PointLight pointLight = new PointLight(new Vector3f(1f, 1f, 1f), 1f, 1f, 0f, 5f);
		/*SpotLight spotLight = new SpotLight(new Vector3f(1f, 1f, 1f), 1f);
		spotLight.setAttenLinear(0f);
		spotLight.setAttenQuadratic(1f);
		spotLight.setRange(2f);
		spotLight.setInnerAngle(40f);
		spotLight.setOuterAngle(60f);*/
		GameObject lightObject = new GameObject();
		lightObject.addComponent(pointLight);
		lightObject.getTransform().setTranslation(0f, 1f, 2f);
		root.addChild(lightObject);
		
		RenderToTextureCamera rttCamera = new RenderToTextureCamera(70f, 0.1f, 100f, 1024, 1024);
		GameObject rttCamObject = new GameObject();
		rttCamObject.addComponent(rttCamera);
		rttCamObject.getTransform().setTranslation(0f, 3f, 4f);
		rttCamObject.getTransform().setRotation(10f, 180f, 0f);
		root.addChild(rttCamObject);
		
		Mesh mesh = Mesh.loadMesh("./res/meshes/scene.obj");
		Material material = new Material();
		material.setDiffuseColor(new Vector3f(1f, 1f, 1f));
		material.setDiffuseTexture(Texture.loadTexture("./res/textures/box.png"));
		//material.setDiffuseTexture(rttCamera.getRenderTexture());
		material.setSpecularColor(new Vector3f(0.7f, 0.7f, 0.7f));
		material.setSpecularTexture(Texture.loadTexture("./res/textures/box_spec.png"));
		material.setAmbientOcclussionIntensity(0.5f);
		material.setAmbientOcclussionTexture(Texture.loadTexture("./res/textures/box_occ.png"));
		material.setNormalMapIntensity(-0.35f);
		material.setNormalMap(Texture.loadTexture("./res/textures/box_norm.png"));
		MeshRenderer renderer = new MeshRenderer(mesh, material);
		GameObject triangle = new GameObject();
		triangle.addComponent(renderer);
		triangle.getTransform().setTranslation(0f, 0f, 2f);
		//triangle.getTransform().setScale(3f, 3f, 3f);
		root.addChild(triangle);
		
		Mesh meshPlane = Mesh.loadMesh("./res/meshes/plane.obj");
		Material material2 = new Material();
		material2.setDiffuseColor(new Vector3f(1f, 1f, 1f));
		material2.setDiffuseTexture(rttCamera.getRenderTexture());
		material2.setSpecularColor(new Vector3f(0f, 0f, 0f));
		material2.setAmbientOcclussionIntensity(0f);
		material2.setNormalMapIntensity(0f);
		MeshRenderer renderer2 = new MeshRenderer(meshPlane, material2);
		GameObject plane = new GameObject();
		plane.addComponent(renderer2);
		plane.getTransform().setTranslation(-3f, 1f, 3f);
		plane.getTransform().setRotation(0f, -90f, 0f);
		root.addChild(plane);
		
		Camera camera = new Camera(70f, 0.1f, 100f, WIDTH, HEIGHT);
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
