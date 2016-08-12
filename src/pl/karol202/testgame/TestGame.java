package pl.karol202.testgame;

import pl.karol202.stoneengine.component.FPPController;
import pl.karol202.stoneengine.component.MouseLocker;
import pl.karol202.stoneengine.core.CoreEngine;
import pl.karol202.stoneengine.core.Game;
import pl.karol202.stoneengine.core.GameObject;
import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.rendering.Camera;
import pl.karol202.stoneengine.rendering.Mesh;
import pl.karol202.stoneengine.rendering.MeshRenderer;
import pl.karol202.stoneengine.rendering.shader.BasicShader;
import pl.karol202.stoneengine.rendering.shader.Shader;

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
		glClearColor(0.1f, 0.1f, 0.2f, 1f);
		Mesh mesh = Mesh.loadMesh("./res/meshes/box.obj");
		Shader shader = new BasicShader();
		MeshRenderer renderer = new MeshRenderer(mesh, shader);
		GameObject triangle = new GameObject();
		triangle.addComponent(renderer);
		triangle.getTransform().setTranslation(0f, 0f, 2f);
		root.addChild(triangle);
		
		Camera camera = new Camera(70f, 0.1f, 100f, (float) WIDTH / HEIGHT);
		FPPController controller = new FPPController(true, true, true, 3f, 0.4f);
		GameObject camObject = new GameObject();
		camObject.addComponent(camera);
		camObject.addComponent(controller);
		//camObject.getTransform().setTranslation(-1f, 0f, -1f);
		//camObject.getTransform().setRotation(0f, 40f, 0f);
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
		root.render();
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
