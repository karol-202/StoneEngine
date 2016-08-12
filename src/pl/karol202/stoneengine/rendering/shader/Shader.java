package pl.karol202.stoneengine.rendering.shader;

import pl.karol202.stoneengine.rendering.Material;
import pl.karol202.stoneengine.rendering.light.Light;
import pl.karol202.stoneengine.util.Matrix4f;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Utils;
import pl.karol202.stoneengine.util.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader
{
	private Map<String, Integer> uniforms;
	private int program;
	
	public Shader()
	{
		uniforms = new HashMap<>();
		program = glCreateProgram();
		if(program == 0) throw new RuntimeException("Cannot create shader program.");
	}
	
	public void bind()
	{
		glUseProgram(program);
	}
	
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		if(uniformLocation == -1) throw new RuntimeException("Cannot add uniform to shader.");
		uniforms.put(uniform, uniformLocation);
	}
	
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
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
	
	public void setUniform(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4fv(uniforms.get(uniformName), true, Utils.createFlippedBuffer(value));
	}
	
	public abstract void updateShader(Transform transform, Material material, Light light);
	
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