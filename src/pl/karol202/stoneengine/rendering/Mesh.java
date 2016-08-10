package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.utils.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh
{
	private int vertexBuffer;
	
	public Mesh(Vertex[] vertices)
	{
		this(vertices, false);
	}
	
	public Mesh(Vertex[] vertices, boolean calcNormals)
	{
		vertexBuffer = glGenBuffers();
		addVertices(vertices);
	}
	
	private void addVertices(Vertex[] vertices)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFlippedBuffer(vertices), GL_STATIC_DRAW);
	}
	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glDisableVertexAttribArray(0);
	}
}
