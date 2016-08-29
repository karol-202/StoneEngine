package pl.karol202.stoneengine.rendering.camera;

import pl.karol202.stoneengine.rendering.ForwardRendering;
import pl.karol202.stoneengine.rendering.light.SpotLight;

public class ShadowmapSpotCamera extends ShadowmapPointCamera
{
	public ShadowmapSpotCamera(SpotLight light)
	{
		super(light);
	}
	
	@Override
	public void render()
	{
		if(render)
		{
			super.render();
			ForwardRendering.renderShadowmapSpot(this, (SpotLight) light);
			render = false;
		}
	}
}
