package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.BufferUtils;
import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh
{
	private int vbo;
	private int ibo;
	private int size;
	
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this(vertices, indices, false);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
		addVertices(vertices, indices);
	}
	
	private void addVertices(Vertex[] vertices, int[] indices)
	{
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
	}
	
	public static Mesh loadMesh(String path)
	{
		String[] splitArray = path.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(!ext.equals("obj"))
		{
			new RuntimeException("File format not supported for mesh data: " + ext).printStackTrace();
			System.exit(1);
		}
		
		ArrayList<Vector3f> vertPos = new ArrayList<>();
		ArrayList<Vector2f> vertTexture = new ArrayList<>();
		ArrayList<Vector3f> vertNormal = new ArrayList<>();
		
		ArrayList<Vertex> vertices = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		
		try(BufferedReader meshReader = new BufferedReader(new FileReader(path)))
		{
			String line;
			
			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#")) continue;
				if(tokens[0].equals("v"))
					vertPos.add(new Vector3f(Float.valueOf(tokens[1]),
											 Float.valueOf(tokens[2]),
											 Float.valueOf(tokens[3])));
				if(tokens[0].equals("vt"))
					vertTexture.add(new Vector2f(Float.valueOf(tokens[1]),
												 Float.valueOf(tokens[2])));
				if(tokens[0].equals("vn"))
					vertNormal.add(new Vector3f(Float.valueOf(tokens[1]),
												Float.valueOf(tokens[2]),
												Float.valueOf(tokens[3])));
				if(tokens[0].equals("f"))
				{
					int[] verts = new int[tokens.length - 1];
					for(int i = 1; i < tokens.length; i++)
					{
						String[] comps = tokens[i].split("/");
						Vector3f pos;
						Vector2f texCoord;
						Vector3f normal;
						
						if(comps[0].equals("")) pos = new Vector3f();
						else pos = vertPos.get(Integer.parseInt(comps[0]) - 1);
						if(comps[1].equals("")) texCoord = new Vector2f();
						else texCoord = vertTexture.get(Integer.parseInt(comps[1]) - 1);
						if(comps[2].equals("")) normal = new Vector3f();
						else normal = vertNormal.get(Integer.parseInt(comps[2]) - 1);
						
						Vertex vert = new Vertex(pos, texCoord, normal);
						if(vertices.contains(vert)) verts[i - 1] = vertices.indexOf(vert);
						else
						{
							verts[i - 1] = vertices.size();
							vertices.add(vert);
						}
					}
					
					indices.add(verts[0]);
					indices.add(verts[1]);
					indices.add(verts[2]);
					
					if(verts.length > 4)
					{
						indices.add(verts[0]);
						indices.add(verts[2]);
						indices.add(verts[3]);
					}
				}
			}
			
			Vertex[] vertexData = new Vertex[vertices.size()];
			vertices.toArray(vertexData);
			
			Integer[] indexData = new Integer[indices.size()];
			indices.toArray(indexData);
			
			return new Mesh(vertexData, toIntArray(indexData), true);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Could not load mesh from file: " + path, e);
		}
	}
	
	public static String[] removeEmptyStrings(String[] data)
	{
		List<String> result = new ArrayList<>();
		for(String str : data)
		{
			if(!str.equals(""))
			{
				result.add(str);
			}
		}
		String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}
	
	public static int[] toIntArray(Integer[] data)
	{
		int[] result = new int[data.length];
		for(int i = 0; i < data.length; i++)
		{
			result[i] = data[i];
		}
		return result;
	}
}
