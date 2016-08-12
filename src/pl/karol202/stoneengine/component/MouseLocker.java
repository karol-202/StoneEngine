package pl.karol202.stoneengine.component;

import pl.karol202.stoneengine.core.Input;

public class MouseLocker extends GameComponent
{
	private boolean defaultLocked;
	
	public MouseLocker(boolean defaultLocked)
	{
		this.defaultLocked = defaultLocked;
	}
	
	@Override
	public void init()
	{
		Input.setCursorLocked(defaultLocked);
	}
	
	@Override
	public void update()
	{
		if(Input.getMouseButtonState(Input.MOUSE_BUTTON_LEFT) == Input.State.PRESSED) Input.setCursorLocked(true);
		if(Input.getKeyState(Input.KEY_ESCAPE) == Input.State.PRESSED) Input.setCursorLocked(false);
	}
	
	@Override
	public void render() { }
}