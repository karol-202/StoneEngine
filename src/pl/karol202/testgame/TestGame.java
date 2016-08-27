package pl.karol202.testgame;

import pl.karol202.stoneengine.component.FPPController;
import pl.karol202.stoneengine.component.MouseLocker;
import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.rendering.*;
import pl.karol202.stoneengine.rendering.camera.ToScreenCamera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.rendering.light.PointLight;
import pl.karol202.stoneengine.util.Vector3f;

import static org.lwjgl.opengl.GL11.glClearColor;

public class TestGame implements Game
{
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private CoreEngine engine;
	private GameObject root;
	
	private PointLight pointLight;
	
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
		glClearColor(0.1f, 0.1f, 0.2f, 1f);
		ForwardRendering.setAmbientLight(new Light(new Vector3f(0.18f, 0.19f, 0.2f), 1f));
		Cubemap skybox = new Cubemap(512, 512);
		skybox.addTexture(Cubemap.POS_X, "./res/textures/skybox_posx.png");
		skybox.addTexture(Cubemap.POS_Y, "./res/textures/skybox_posy.png");
		skybox.addTexture(Cubemap.POS_Z, "./res/textures/skybox_posz.png");
		skybox.addTexture(Cubemap.NEG_X, "./res/textures/skybox_negx.png");
		skybox.addTexture(Cubemap.NEG_Y, "./res/textures/skybox_negy.png");
		skybox.addTexture(Cubemap.NEG_Z, "./res/textures/skybox_negz.png");
		SkyboxRenderer sr = new SkyboxRenderer(skybox, 0.5f);
		ForwardRendering.setSkyboxRenderer(sr);
		
		//DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1f, 1f, 1f), 1f);
		pointLight = new PointLight(new Vector3f(1f, 1f, 1f), 1f, 1f, 0f, 8f);
		/*SpotLight spotLight = new SpotLight(new Vector3f(1f, 1f, 1f), 1f);
		spotLight.setAttenLinear(0f);
		spotLight.setAttenQuadratic(1f);
		spotLight.setRange(2f);
		spotLight.setInnerAngle(40f);
		spotLight.setOuterAngle(60f);*/
		GameObject lightObject = new GameObject();
		lightObject.addComponent(pointLight);
		lightObject.getTransform().setTranslation(0.5f, 1.5f, 1f);
		//lightObject.getTransform().setRotation(35f, 20f, 0f);
		root.addChild(lightObject);
		
		Mesh mesh = Mesh.loadMesh("./res/meshes/scene.obj");
		Material material = new Material();
		material.setDiffuseColor(new Vector3f(1f, 1f, 1f));
		material.setDiffuseTexture(Texture2D.loadTexture("./res/textures/box.png"));
		material.setSpecularColor(new Vector3f(0.3f, 0.3f, 0.3f));
		material.setSpecularTexture(Texture2D.loadTexture("./res/textures/box_spec.png"));
		material.setAmbientOcclussionIntensity(0.5f);
		material.setAmbientOcclussionTexture(Texture2D.loadTexture("./res/textures/box_occ.png"));
		material.setNormalMapIntensity(-0.35f);
		material.setNormalMap(Texture2D.loadTexture("./res/textures/box_norm.png"));
		MeshRenderer renderer = new MeshRenderer(mesh, material);
		GameObject triangle = new GameObject();
		triangle.addComponent(renderer);
		//triangle.getTransform().setTranslation(0f, 0f, 2f);
		//triangle.getTransform().setScale(3f, 3f, 3f);
		root.addChild(triangle);
		
		ToScreenCamera camera = new ToScreenCamera(WIDTH, HEIGHT);
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
		//System.out.println(Math.sin((System.currentTimeMillis() % 150000) / 23880f));
		//pointLight.setShadowSoftness(0.005f * (float) Math.sin((System.currentTimeMillis() % 150000) / 23880f));
		
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
