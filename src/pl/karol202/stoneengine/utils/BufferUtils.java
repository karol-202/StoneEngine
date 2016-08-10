package pl.karol202.stoneengine.utils;

import pl.karol202.stoneengine.rendering.Vertex;

import java.nio.FloatBuffer;

public class BufferUtils
{
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getPosition().getX());
			buffer.put(vertices[i].getPosition().getY());
			buffer.put(vertices[i].getPosition().getZ());
		}
		buffer.flip();
		return buffer;
	}
}
