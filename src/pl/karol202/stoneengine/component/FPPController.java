package pl.karol202.stoneengine.component;

import pl.karol202.stoneengine.core.Input;
import pl.karol202.stoneengine.util.Time;
import pl.karol202.stoneengine.util.Transform;
import pl.karol202.stoneengine.util.Vector2f;
import pl.karol202.stoneengine.util.Vector3f;

public class FPPController extends GameComponent
{
	private boolean move;
	private boolean lookX;
	private boolean lookY;
	private float moveSpeed;
	private float lookSpeed;
	private int keyForward;
	private int keyBackward;
	private int keyLeft;
	private int keyRight;
	private int keyUp;
	private int keyDown;
	
	public FPPController(boolean move, boolean lookX, boolean lookY, float moveSpeed, float lookSpeed)
	{
		super();
		this.move = move;
		this.lookX = lookX;
		this.lookY = lookY;
		this.moveSpeed = moveSpeed;
		this.lookSpeed = lookSpeed;
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
		if(move) move();
		
		Vector2f delta = Input.getMouseDelta();
		if(lookX && delta.getX() != 0) lookX(delta.getX());
		if(lookY && delta.getY() != 0) lookY(delta.getY());
	}
	
	@Override
	public void render() { }
	
	private void move()
	{
		Transform transform = getGameObject().getTransform();
		
		if(Input.isKeyDown(keyForward))
		{
			Vector3f move = getForward(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().add(move));
		}
		if(Input.isKeyDown(keyBackward))
		{
			Vector3f move = getForward(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().sub(move));
		}
		if(Input.isKeyDown(keyLeft))
		{
			Vector3f move = getRight(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
			transform.setTranslation(transform.getTranslation().sub(move));
		}
		if(Input.isKeyDown(keyRight))
		{
			Vector3f move = getRight(transform.getRotation()).mul((float) (getDeltaSeconds() * moveSpeed));
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
	
	private void lookX(float delta)
	{
		float rotY = getGameObject().getTransform().getRotation().getY() + (getDeltaMilis() * -lookSpeed * delta);
		getGameObject().getTransform().getRotation().setY(rotY);
	}
	
	private void lookY(float delta)
	{
		
	}
	
	private Vector3f getForward(Vector3f euler)
	{
		Vector3f forward = new Vector3f();
		double x = Math.toRadians(euler.getX());
		double y = -Math.toRadians(euler.getY());
		forward.setX((float) (Math.cos(x) * Math.sin(y)));
		forward.setY((float) (Math.sin(x)));
		forward.setZ((float) (Math.cos(x) * Math.cos(y)));
		return forward;
	}
	
	private Vector3f getRight(Vector3f euler)
	{
		Vector3f right = new Vector3f();
		double y = Math.toRadians(-euler.getY() + 90);
		right.setX((float) Math.sin(y));
		right.setZ((float) Math.cos(y));
		return right;
	}
	
	private float getDeltaMilis()
	{
		return Time.getDelta() / 1000000f;
	}
	
	private double getDeltaSeconds()
	{
		return Time.getDelta() / (double) Time.SECOND;
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