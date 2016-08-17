package pl.karol202.stoneengine.rendering;

import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

public class Vertex
{
	public static final int SIZE = 14;
	
	private Vector3f pos;
	private Vector2f uv;
	private Vector3f normal;
	private Vector3f tangent;
	private Vector3f bitangent;
	
	public Vertex(Vector3f pos, Vector2f uv, Vector3f normal)
	{
		this(pos, uv, normal, null, null);
	}
	
	public Vertex(Vector3f pos, Vector2f uv, Vector3f normal, Vector3f tangent, Vector3f bitangent)
	{
		this.pos = pos;
		this.uv = uv;
		this.normal = normal;
		this.tangent = tangent;
		this.bitangent = bitangent;
	}
	
	public Vertex(Vertex vertex)
	{
		this.pos = new Vector3f(vertex.getPos());
		this.uv = new Vector2f(vertex.getUV());
		this.normal = new Vector3f(vertex.getNormal());
		this.tangent = vertex.getTangent() != null ? new Vector3f(vertex.getTangent()) : null;
		this.bitangent = vertex.getBitangent() != null ? new Vector3f(vertex.getBitangent()) : null;
	}
	
	//Checks equality of two vertices by checking its positions, uvs and normals.
	//Doesn't check tangents and bitangents.
	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Vertex vertex = (Vertex) o;
		
		if(!pos.equals(vertex.pos)) return false;
		if(!uv.equals(vertex.uv)) return false;
		return normal.equals(vertex.normal);
		
	}
	
	@Override
	public int hashCode()
	{
		int result = pos.hashCode();
		result = 31 * result + uv.hashCode();
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
	
	public Vector2f getUV()
	{
		return uv;
	}
	
	public void setUV(Vector2f uv)
	{
		this.uv = uv;
	}
	
	public Vector3f getNormal()
	{
		return normal;
	}
	
	public void setNormal(Vector3f normal)
	{
		this.normal = normal;
	}
	
	public Vector3f getTangent()
	{
		return tangent;
	}
	
	public void setTangent(Vector3f tangent)
	{
		this.tangent = tangent;
	}
	
	public Vector3f getBitangent()
	{
		return bitangent;
	}
	
	public void setBitangent(Vector3f bitangent)
	{
		this.bitangent = bitangent;
	}
}
