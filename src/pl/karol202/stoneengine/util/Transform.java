package pl.karol202.stoneengine.util;

public class Transform
{
	private Vector3f translation;
	private Vector3f rotation;
	private Vector3f scale;
	
	public Transform()
	{
		translation = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
	}

	public Transform(Transform copy)
	{
		translation = new Vector3f(copy.getTranslation());
		rotation = new Vector3f(copy.getRotation());
		scale = new Vector3f(copy.getScale());
	}
	
	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.getX(), translation.getY(), translation.getZ());
		Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		return translationMatrix.mul(rotationMatrix).mul(scaleMatrix);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Transform transform = (Transform) o;
		
		if(!translation.equals(transform.translation)) return false;
		if(!rotation.equals(transform.rotation)) return false;
		return scale.equals(transform.scale);
		
	}
	
	@Override
	public int hashCode()
	{
		int result = translation.hashCode();
		result = 31 * result + rotation.hashCode();
		result = 31 * result + scale.hashCode();
		return result;
	}
	
	public Vector3f getTranslation()
	{
		return translation;
	}

	public void setTranslation(Vector3f translation)
	{
		this.translation = translation;
	}
	
	public void setTranslation(float x, float y, float z)
	{
		this.translation = new Vector3f(x, y, z);
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z)
	{
		this.rotation = new Vector3f(x, y, z);
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z)
	{
		this.scale = new Vector3f(x, y, z);
	}
}
