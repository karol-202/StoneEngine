package pl.karol202.stoneengine.utils;

import pl.karol202.stoneengine.rendering.Vertex;
import pl.karol202.stoneengine.util.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.BufferUtils.createFloatBuffer;

public class BufferUtils
{
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getPosition().getX());
			buffer.put(vertices[i].getPosition().getY());
			buffer.put(vertices[i].getPosition().getZ());
		}
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4f value)
	{
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				buffer.put(value.get(i, j));
			}
		}
		buffer.flip();
		return buffer;
	}
}
