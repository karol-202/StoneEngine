package pl.karol202.stoneengine.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import pl.karol202.stoneengine.util.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.system.MemoryUtil.*;

public class CoreEngine
{
	private Game game;
	private boolean running;
	private long window;
	private int width;
	private int height;
	
	public CoreEngine(Game game)
	{
		this.game = game;
		this.running = false;
		this.width = 800;
		this.height = 600;
	}
	
	public void start()
	{
		if(running) return;
		running = true;
		init();
		run();
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
		
		window = glfwCreateWindow(width, height, game.getTitle(), NULL, NULL);
		if(window == NULL) throw new RuntimeException("Unable to create window.");
		GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (mode.width() - width) / 2, (mode.height() - height) / 2);
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		createCapabilities();
	}
	
	private void run()
	{
		boolean render = false;
		final double frameTime = Time.SECOND / 500.0;
		
		int fps = 0;
		long framesCounter = 0;
		
		long lastTime = System.nanoTime();
		long unprocessedTime = 0;
		
		while(running)
		{
			long passedTime = System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			unprocessedTime += passedTime;
			framesCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				if(glfwWindowShouldClose(window))
				{
					stop();
					break;
				}
				render = true;
				unprocessedTime -= frameTime;
				Time.setDelta(frameTime);
				game.update();
				if(framesCounter >= Time.SECOND)
				{
					System.out.println(fps);
					fps = 0;
					framesCounter = 0;
				}
			}
			if(render)
			{
				game.render();
				glfwSwapBuffers(window);
				glfwPollEvents();
				
				render = false;
				fps++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
}
