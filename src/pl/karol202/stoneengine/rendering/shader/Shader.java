package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.Texture;
import pl.karol202.stoneengine.rendering.camera.Camera;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Utils;
import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public abstract class Shader
{
	private int program;
	private Map<String, Integer> uniforms;
	private int textures;
	
	public Shader()
	{
		program = glCreateProgram();
		if(program == 0) throw new RuntimeException("Cannot create shader program.");
		uniforms = new HashMap<>();
	}
	
	public void bind()
	{
		glUseProgram(program);
	}
	
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		if(uniformLocation == -1) throw new RuntimeException("Cannot add uniform to shader: " + uniform);
		uniforms.put(uniform, uniformLocation);
	}
	
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	public void compileShader()
	{
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			throw new RuntimeException("Error during linking program of shader.");
		}
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			throw new RuntimeException("Error during validating program of shader.");
		}
	}
	
	private void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);
		if(shader == 0) throw new RuntimeException("Cannot create shader.");
		glShaderSource(shader, text);
		glCompileShader(shader);
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			throw new RuntimeException("Error during compiling shader.");
		}
		glAttachShader(program, shader);
	}
	
	private int getUniform(String uniformName)
	{
		if(!uniforms.containsKey(uniformName)) throw new RuntimeException("Cannot update shader's uniform: " + uniformName);
		return uniforms.get(uniformName);
	}
	
	protected void setUniform(String uniformName, boolean value)
	{
		glUniform1i(getUniform(uniformName), value ? 1 : 0);
	}
	
	protected void setUniform(String uniformName, int value)
	{
		glUniform1i(getUniform(uniformName), value);
	}
	
	protected void setUniform(String uniformName, float value)
	{
		glUniform1f(getUniform(uniformName), value);
	}
	
	protected void setUniform(String uniformName, Vector2f value)
	{
		glUniform2f(getUniform(uniformName), value.getX(), value.getY());
	}
	
	protected void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(getUniform(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	protected void setUniform(String uniformName, Matrix4f value, boolean transpose)
	{
		glUniformMatrix4fv(getUniform(uniformName), transpose, Utils.createFlippedBuffer(value));
	}
	
	protected void setUniform(String uniformName, Texture value)
	{
		glActiveTexture(GL_TEXTURE0 + textures);
		if(value != null) value.bind();
		else
		{
			glBindTexture(GL_TEXTURE_2D, 0);
			glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
		}
		glUniform1i(getUniform(uniformName), textures++);
	}
	
	public void updateShader(Matrix4f transformation, Material material, Light light, Camera camera)
	{
		textures = 0;
	}
	
	public static String loadShader(String path)
	{
		StringBuilder shaderSource = new StringBuilder();
		try(BufferedReader shaderReader = new BufferedReader(new FileReader(path)))
		{
			String line;
			while((line = shaderReader.readLine()) != null) shaderSource.append(line).append("\n");
			shaderReader.close();
		}
		catch(IOException e)
		{
			throw new RuntimeException("Cannot load shader from: " + path, e);
		}
		return shaderSource.toString();
	}
}