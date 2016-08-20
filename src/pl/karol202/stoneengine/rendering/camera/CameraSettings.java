package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.util.Matrix4f;

public interface CameraSettings
{
	Matrix4f getProjection();
	
	void setCamera(Camera camera);
}
