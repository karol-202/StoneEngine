package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

public class Vertex
{
	public static final int SIZE = 6;
	
	private Vector3f pos;
	private Vector2f texCoord;
	private Vector3f normal;
	
	public Vertex(Vector3f pos)
	{
		this(pos, new Vector2f(0, 0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord)
	{
		this(pos, texCoord, new Vector3f(0, 0, 0));
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal)
	{
		this.pos = pos;
		this.texCoord = texCoord;
		this.normal = normal;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Vertex vertex = (Vertex) o;
		
		return pos.equals(vertex.pos) && texCoord.equals(vertex.texCoord) && normal.equals(vertex.normal);
		
	}
	
	@Override
	public int hashCode()
	{
		int result = pos.hashCode();
		result = 31 * result + texCoord.hashCode();
		result = 31 * result + normal.hashCode();
		return result;
	}
	
	public Vector3f getPos()
	{
		return pos;
	}
	
	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}
	
	public Vector2f getTexCoord()
	{
		return texCoord;
	}
	
	public void setTexCoord(Vector2f texCoord)
	{
		this.texCoord = texCoord;
	}
	
	public Vector3f getNormal()
	{
		return normal;
	}
	
	public void setNormal(Vector3f normal)
	{
		this.normal = normal;
	}
}
