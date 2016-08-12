package pl.karol202.stoneengine.component;

import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.util.*;

public class FPPController extends GameComponent
{
	private boolean moving;
	private boolean lookingX;
	private boolean lookingY;
	private float moveSpeed;
	private float lookSpeed;
	private float xRangeMin;
	private float xRangeMax;
	private int keyForward;
	private int keyBackward;
	private int keyLeft;
	private int keyRight;
	private int keyUp;
	private int keyDown;
	
	public FPPController(float moveSpeed, float lookSpeed)
	{
		super();
		this.moving = true;
		this.lookingX = true;
		this.lookingY = true;
		this.moveSpeed = moveSpeed;
		this.lookSpeed = lookSpeed;
		this.xRangeMin = -90;
		this.xRangeMax = 90;
		this.keyForward = Input.KEY_W;
		this.keyBackward = Input.KEY_S;
		this.keyLeft = Input.KEY_A;
		this.keyRight = Input.KEY_D;
		this.keyUp = Input.KEY_SPACE;
		this.keyDown = Input.KEY_LEFT_SHIFT;
	}
	
	@Override
	public void init() { }
	
	@Override
	public void update()
	{
		if(moving) move();
		
		Vector2f delta = Input.getMouseDelta();
		if(lookingX && delta.getX() != 0) lookX(delta.getX());
		if(lookingY && delta.getY() != 0) lookY(delta.getY());
	}
	
	@Override
	public void render() { }
	
	private void move()
	{
		Transform transform = getGameObject().getTransform();
		
		if(Input.isKeyDown(keyForward))
		{
			Vector3f move = getFlatForward(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().add(move));
		}
		if(Input.isKeyDown(keyBackward))
		{
			Vector3f move = getFlatForward(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().sub(move));
		}
		if(Input.isKeyDown(keyLeft))
		{
			Vector3f move = Utils.getRightFromEuler(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().sub(move));
		}
		if(Input.isKeyDown(keyRight))
		{
			Vector3f move = Utils.getRightFromEuler(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().add(move));
		}
		if(Input.isKeyDown(keyUp))
		{
			Vector3f move = new Vector3f(0f, 1f, 0f).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().add(move));
		}
		if(Input.isKeyDown(keyDown))
		{
			Vector3f move = new Vector3f(0f, 1f, 0f).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().sub(move));
		}
	}
	
	private Vector3f getFlatForward(Vector3f euler)
	{
		return Utils.getForwardFromEuler(euler).mul(new Vector3f(1f, 0f, 1f)).normalized();
	}
	
	private void lookX(float delta)
	{
		float rotY = getGameObject().getTransform().getRotation().getY() + (getDeltaMilis() * -lookSpeed * delta);
		getGameObject().getTransform().getRotation().setY(rotY);
	}
	
	private void lookY(float delta)
	{
		float rotX = getGameObject().getTransform().getRotation().getX() + (getDeltaMilis() * lookSpeed * delta);
		if(rotX < xRangeMin) rotX = xRangeMin;
		if(rotX > xRangeMax) rotX = xRangeMax;
		getGameObject().getTransform().getRotation().setX(rotX);
	}
	
	private float getDeltaMilis()
	{
		return Time.getDelta() / 1000000f;
	}
	
	private double getDeltaSeconds()
	{
		return Time.getDelta() / (double) Time.SECOND;
	}
	
	public boolean isMoving()
	{
		return moving;
	}
	
	public void setMoving(boolean moving)
	{
		this.moving = moving;
	}
	
	public boolean isLookingX()
	{
		return lookingX;
	}
	
	public void setLookingX(boolean lookingX)
	{
		this.lookingX = lookingX;
	}
	
	public boolean isLookingY()
	{
		return lookingY;
	}
	
	public void setLookingY(boolean lookingY)
	{
		this.lookingY = lookingY;
	}
	
	public float getMoveSpeed()
	{
		return moveSpeed;
	}
	
	public void setMoveSpeed(float moveSpeed)
	{
		this.moveSpeed = moveSpeed;
	}
	
	public float getLookSpeed()
	{
		return lookSpeed;
	}
	
	public void setLookSpeed(float lookSpeed)
	{
		this.lookSpeed = lookSpeed;
	}
	
	public float getxRangeMin()
	{
		return xRangeMin;
	}
	
	public void setxRangeMin(float xRangeMin)
	{
		this.xRangeMin = xRangeMin;
	}
	
	public float getxRangeMax()
	{
		return xRangeMax;
	}
	
	public void setxRangeMax(float xRangeMax)
	{
		this.xRangeMax = xRangeMax;
	}
	
	public int getKeyForward()
	{
		return keyForward;
	}
	
	public void setKeyForward(int keyForward)
	{
		this.keyForward = keyForward;
	}
	
	public int getKeyBackward()
	{
		return keyBackward;
	}
	
	public void setKeyBackward(int keyBackward)
	{
		this.keyBackward = keyBackward;
	}
	
	public int getKeyLeft()
	{
		return keyLeft;
	}
	
	public void setKeyLeft(int keyLeft)
	{
		this.keyLeft = keyLeft;
	}
	
	public int getKeyRight()
	{
		return keyRight;
	}
	
	public void setKeyRight(int keyRight)
	{
		this.keyRight = keyRight;
	}
	
	public int getKeyUp()
	{
		return keyUp;
	}
	
	public void setKeyUp(int keyUp)
	{
		this.keyUp = keyUp;
	}
	
	public int getKeyDown()
	{
		return keyDown;
	}
	
	public void setKeyDown(int keyDown)
	{
		this.keyDown = keyDown;
	}
}