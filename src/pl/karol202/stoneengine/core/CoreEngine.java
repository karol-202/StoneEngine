package pl.karol202.stoneengine.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import pl.karol202.stoneengine.util.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS;
import static org.lwjgl.system.MemoryUtil.*;

public class CoreEngine
{
	private Game game;
	private boolean running;
	private long window;
	private int width;
	private int height;
	private Input inputHandler;
	
	public CoreEngine(Game game)
	{
		this.game = game;
		this.running = false;
	}
	
	public void start()
	{
		try
		{
			if(running) return;
			running = true;
			init();
			game.init();
			run();
		}
		finally
		{
			glfwTerminate();
			glfwSetErrorCallback(null).free();
			glfwSetKeyCallback(window, null);
		}
	}
	
	public void stop()
	{
		if(!running) return;
		running = false;
	}
	
	private void init()
	{
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()) throw new RuntimeException("Unable to initialize GLFW.");
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, game.getTitle(), NULL, NULL);
		if(window == NULL) throw new RuntimeException("Unable to create window.");
		GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (mode.width() - width) / 2, (mode.height() - height) / 2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
		createCapabilities();
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		
		glEnable(GL_MULTISAMPLE);
		glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		glFrontFace(GL_CW);
		
		inputHandler = new Input();
		inputHandler.init(window, width, height);
	}
	
	private void run()
	{
		int fps = 0;
		long framesCounter = 0;
		long lastTime = System.nanoTime();
		
		while(running)
		{
			long passedTime = System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			framesCounter += passedTime;
			if(glfwWindowShouldClose(window))
			{
				stop();
				break;
			}
			Time.setDelta(passedTime);
			update();
			render();
			fps++;
			if(framesCounter >= Time.SECOND)
			{
				Time.setFPS(fps);
				fps = 0;
				framesCounter = 0;
			}
		}
	}
	
	private void update()
	{
		glfwPollEvents();
		game.update();
		inputHandler.update();
	}
	
	private void render()
	{
		game.render();
		glfwSwapBuffers(window);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
}
