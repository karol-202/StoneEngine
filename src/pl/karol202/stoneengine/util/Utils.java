package pl.karol202.stoneengine.util;

import pl.karol202.stoneengine.rendering.Vertex;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.BufferUtils.createIntBuffer;

public class Utils
{
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);
		for(Vertex vertex : vertices)
		{
			buffer.put(vertex.getPos().getX());
			buffer.put(vertex.getPos().getY());
			buffer.put(vertex.getPos().getZ());
			buffer.put(vertex.getUV().getX());
			buffer.put(vertex.getUV().getY());
			buffer.put(vertex.getNormal().getX());
			buffer.put(vertex.getNormal().getY());
			buffer.put(vertex.getNormal().getZ());
			buffer.put(vertex.getTangent().getX());
			buffer.put(vertex.getTangent().getY());
			buffer.put(vertex.getTangent().getZ());
			buffer.put(vertex.getBitangent().getX());
			buffer.put(vertex.getBitangent().getY());
			buffer.put(vertex.getBitangent().getZ());
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
	
	public static IntBuffer createFlippedBuffer(int... values)
	{
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	public static ByteBuffer createFlippedBuffer(byte... values)
	{
		ByteBuffer buffer = createByteBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	public static Vector3f getForwardFromEuler(Vector3f euler)
	{
		Vector3f forward = new Vector3f();
		double x = Math.toRadians(-euler.getX());
		double y = Math.toRadians(-euler.getY());
		forward.setX((float) (Math.cos(x) * Math.sin(y)));
		forward.setY((float) (Math.sin(x)));
		forward.setZ((float) (Math.cos(x) * Math.cos(y)));
		return forward;
	}
	
	public static Vector3f getRightFromEuler(Vector3f euler)
	{
		Vector3f right = new Vector3f();
		double y = Math.toRadians(-euler.getY() + 90);
		right.setX((float) Math.sin(y));
		right.setZ((float) Math.cos(y));
		return right;
	}
}
