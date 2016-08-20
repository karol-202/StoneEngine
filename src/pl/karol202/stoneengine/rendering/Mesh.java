package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Utils;
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
	private Vertex[] vertices;
	private int[] indices;
	private int vbo;
	private int ibo;
	private int size;
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcTangents)
	{
		this.vertices = vertices;
		this.indices = indices;
		this.vbo = glGenBuffers();
		this.ibo = glGenBuffers();
		this.size = 0;
		if(calcTangents) calcTangents();
		addVertices();
	}
	
	private void calcTangents()
	{
		ArrayList<Vertex> verticesList = new ArrayList<>();
		ArrayList<Integer> indicesList = new ArrayList<>();
		
		for(int i = 0; i < indices.length; i += 3)
		{
			//Three vertices of the current face
			Vertex[] fv = new Vertex[3];
			for(int j = 0; j < 3; j++) fv[j] = new Vertex(vertices[indices[i + j]]);
			
			Vector3f deltaPos1 = fv[1].getPos().sub(fv[0].getPos());
			Vector3f deltaPos2 = fv[2].getPos().sub(fv[0].getPos());
			
			Vector2f deltaUV1 = fv[1].getUV().sub(fv[0].getUV());
			Vector2f deltaUV2 = fv[2].getUV().sub(fv[0].getUV());
			
			float x = 1f / (deltaUV1.getX() * deltaUV2.getY() - deltaUV1.getY() * deltaUV2.getX());
			Vector3f tangent = (deltaPos1.mul(deltaUV2.getY()).sub(deltaPos2.mul(deltaUV1.getY()))).mul(x);
			Vector3f bitangent = (deltaPos2.mul(deltaUV1.getX()).sub(deltaPos1.mul(deltaUV2.getX()))).mul(x);
			
			for(int j = 0; j < 3; j++)
			{
				fv[j].setTangent(tangent.add(fv[j].getNormal().mul(-1 * tangent.dot(fv[j].getNormal()))));
				if(fv[j].getNormal().cross(fv[j].getTangent()).dot(bitangent) < 0f)
					fv[j].setTangent(fv[j].getTangent().mul(-1f));
				fv[j].setBitangent(bitangent);

				if(verticesList.contains(fv[j]))
				{
					int indexOfOldVertex = verticesList.indexOf(fv[j]);
					indicesList.add(indexOfOldVertex);
					Vertex oldVertex = verticesList.get(indexOfOldVertex);
					oldVertex.setTangent(oldVertex.getTangent().add(fv[j].getTangent()));
					oldVertex.setBitangent(oldVertex.getBitangent().add(fv[j].getBitangent()));
				}
				else
				{
					indicesList.add(verticesList.size());
					verticesList.add(fv[j]);
				}
			}
		}
		
		vertices = new Vertex[verticesList.size()];
		verticesList.toArray(vertices);
		
		Integer[] indicesArray = new Integer[indicesList.size()];
		indicesList.toArray(indicesArray);
		indices = toIntArray(indicesArray);
	}
	
	private void addVertices()
	{
		size = indices.length;
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glEnableVertexAttribArray(4);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);
		glVertexAttribPointer(4, 3, GL_FLOAT, false, Vertex.SIZE * 4, 44);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
	}
	
	public static Mesh loadMesh(String path)
	{
		String[] splitArray = path.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		if(ext.equals("obj")) return loadMeshFromOBJ(path);
		else
		{
			throw new RuntimeException("File format not supported for mesh data: " + ext);
		}
	}
	
	private static Mesh loadMeshFromOBJ(String path)
	{
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
					
					for(int i = 0; i < verts.length - 2; i++)
					{
						indices.add(verts[0]);
						indices.add(verts[1 + i]);
						indices.add(verts[2 + i]);
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
	
	private static String[] removeEmptyStrings(String[] data)
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
	
	private static int[] toIntArray(Integer[] data)
	{
		int[] result = new int[data.length];
		for(int i = 0; i < data.length; i++)
		{
			result[i] = data[i];
		}
		return result;
	}
}
