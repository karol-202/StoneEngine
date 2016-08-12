package pl.karol202.stoneengine.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import pl.karol202.stoneengine.util.Vector2f;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
	public enum State
	{
		UP, PRESSED, DOWN, RELEASED
	}
	
	public static int KEY_SPACE         = 32;
	public static int KEY_APOSTROPHE    = 39;
	public static int KEY_COMMA         = 44;
	public static int KEY_MINUS         = 45;
	public static int KEY_PERIOD        = 46;
	public static int KEY_SLASH         = 47;
	public static int KEY_0             = 48;
	public static int KEY_1             = 49;
	public static int KEY_2             = 50;
	public static int KEY_3             = 51;
	public static int KEY_4             = 52;
	public static int KEY_5             = 53;
	public static int KEY_6             = 54;
	public static int KEY_7             = 55;
	public static int KEY_8             = 56;
	public static int KEY_9             = 57;
	public static int KEY_SEMICOLON     = 59;
	public static int KEY_EQUAL         = 61;
	public static int KEY_A             = 65;
	public static int KEY_B             = 66;
	public static int KEY_C             = 67;
	public static int KEY_D             = 68;
	public static int KEY_E             = 69;
	public static int KEY_F             = 70;
	public static int KEY_G             = 71;
	public static int KEY_H             = 72;
	public static int KEY_I             = 73;
	public static int KEY_J             = 74;
	public static int KEY_K             = 75;
	public static int KEY_L             = 76;
	public static int KEY_M             = 77;
	public static int KEY_N             = 78;
	public static int KEY_O             = 79;
	public static int KEY_P             = 80;
	public static int KEY_Q             = 81;
	public static int KEY_R             = 82;
	public static int KEY_S             = 83;
	public static int KEY_T             = 84;
	public static int KEY_U             = 85;
	public static int KEY_V             = 86;
	public static int KEY_W             = 87;
	public static int KEY_X             = 88;
	public static int KEY_Y             = 89;
	public static int KEY_Z             = 90;
	public static int KEY_LEFT_BRACKET  = 91;
	public static int KEY_BACKSLASH     = 92;
	public static int KEY_RIGHT_BRACKET = 93;
	public static int KEY_GRAVE_ACCENT  = 96;
	public static int KEY_WORLD_1       = 161;
	public static int KEY_WORLD_2       = 162;
	public static int KEY_ESCAPE        = 256;
	public static int KEY_ENTER         = 257;
	public static int KEY_TAB           = 258;
	public static int KEY_BACKSPACE     = 259;
	public static int KEY_INSERT        = 260;
	public static int KEY_DELETE        = 261;
	public static int KEY_RIGHT         = 262;
	public static int KEY_LEFT          = 263;
	public static int KEY_DOWN          = 264;
	public static int KEY_UP            = 265;
	public static int KEY_PAGE_UP       = 266;
	public static int KEY_PAGE_DOWN     = 267;
	public static int KEY_HOME          = 268;
	public static int KEY_END           = 269;
	public static int KEY_CAPS_LOCK     = 280;
	public static int KEY_SCROLL_LOCK   = 281;
	public static int KEY_NUM_LOCK      = 282;
	public static int KEY_PRINT_SCREEN  = 283;
	public static int KEY_PAUSE         = 284;
	public static int KEY_F1            = 290;
	public static int KEY_F2            = 291;
	public static int KEY_F3            = 292;
	public static int KEY_F4            = 293;
	public static int KEY_F5            = 294;
	public static int KEY_F6            = 295;
	public static int KEY_F7            = 296;
	public static int KEY_F8            = 297;
	public static int KEY_F9            = 298;
	public static int KEY_F10           = 299;
	public static int KEY_F11           = 300;
	public static int KEY_F12           = 301;
	public static int KEY_F13           = 302;
	public static int KEY_F14           = 303;
	public static int KEY_F15           = 304;
	public static int KEY_F16           = 305;
	public static int KEY_F17           = 306;
	public static int KEY_F18           = 307;
	public static int KEY_F19           = 308;
	public static int KEY_F20           = 309;
	public static int KEY_F21           = 310;
	public static int KEY_F22           = 311;
	public static int KEY_F23           = 312;
	public static int KEY_F24           = 313;
	public static int KEY_F25           = 314;
	public static int KEY_KP_0          = 320;
	public static int KEY_KP_1          = 321;
	public static int KEY_KP_2          = 322;
	public static int KEY_KP_3          = 323;
	public static int KEY_KP_4          = 324;
	public static int KEY_KP_5          = 325;
	public static int KEY_KP_6          = 326;
	public static int KEY_KP_7          = 327;
	public static int KEY_KP_8          = 328;
	public static int KEY_KP_9          = 329;
	public static int KEY_KP_DECIMAL    = 330;
	public static int KEY_KP_DIVIDE     = 331;
	public static int KEY_KP_MULTIPLY   = 332;
	public static int KEY_KP_SUBTRACT   = 333;
	public static int KEY_KP_ADD        = 334;
	public static int KEY_KP_ENTER      = 335;
	public static int KEY_KP_EQUAL      = 336;
	public static int KEY_LEFT_SHIFT    = 340;
	public static int KEY_LEFT_CONTROL  = 341;
	public static int KEY_LEFT_ALT      = 342;
	public static int KEY_LEFT_SUPER    = 343;
	public static int KEY_RIGHT_SHIFT   = 344;
	public static int KEY_RIGHT_CONTROL = 345;
	public static int KEY_RIGHT_ALT     = 346;
	public static int KEY_RIGHT_SUPER   = 347;
	public static int KEY_MENU          = 348;
	
	public static int MOUSE_BUTTON_LEFT   = 0;
	public static int MOUSE_BUTTON_RIGHT  = 1;
	public static int MOUSE_BUTTON_MIDDLE = 2;
	public static int MOUSE_BUTTON_4      = 3;
	public static int MOUSE_BUTTON_5      = 4;
	public static int MOUSE_BUTTON_6      = 5;
	public static int MOUSE_BUTTON_7      = 6;
	public static int MOUSE_BUTTON_8      = 7;
	
	private static long window;
	private static int windowWidth;
	private static int windowHeight;
	private static State[] keys = new State[512];
	private static State[] mouse = new State[8];
	private static DoubleBuffer bufferX = BufferUtils.createDoubleBuffer(1);
	private static DoubleBuffer bufferY = BufferUtils.createDoubleBuffer(1);
	private static boolean cursorLocked;
	
	void init(long window, int windowWidth, int windowHeight)
	{
		Input.window = window;
		Input.windowWidth = windowWidth;
		Input.windowHeight = windowHeight;
		glfwSetKeyCallback(window, GLFWKeyCallback.create(this::onKeyEvent));
		glfwSetMouseButtonCallback(window, GLFWMouseButtonCallback.create(this::onMouseButtonEvent));
		
		for(int i = 0; i < keys.length; i++)
			keys[i] = State.UP;
		for(int i = 0; i < mouse.length; i++)
			mouse[i] = State.UP;
	}
	
	void update()
	{
		for(int i = 0; i < keys.length; i++)
		{
			if(keys[i] == State.PRESSED) keys[i] = State.DOWN;
			if(keys[i] == State.RELEASED) keys[i] = State.UP;
		}
		for(int i = 0; i < mouse.length; i++)
		{
			if(mouse[i] == State.PRESSED) mouse[i] = State.DOWN;
			if(mouse[i] == State.RELEASED) mouse[i] = State.UP;
		}
		
		if(cursorLocked) glfwSetCursorPos(window, windowWidth / 2, windowHeight / 2);
	}
	
	private void onKeyEvent(long window, int key, int scancode, int action, int mods)
	{
		if(action == GLFW_PRESS) keys[key] = State.PRESSED;
		else if(action == GLFW_RELEASE) keys[key] = State.RELEASED;
	}
	
	private void onMouseButtonEvent(long window, int button, int action, int mods)
	{
		if(action == GLFW_PRESS) mouse[button] = State.PRESSED;
		else if(action == GLFW_RELEASE) mouse[button] = State.RELEASED;
	}
	
	public static State getKeyState(int key)
	{
		return keys[key];
	}
	
	public static boolean isKeyDown(int key)
	{
		return keys[key] == State.DOWN || keys[key] == State.PRESSED;
	}
	
	public static State getMouseButtonState(int button)
	{
		return mouse[button];
	}
	
	public static boolean isMouseButtonDown(int button)
	{
		return mouse[button] == State.DOWN || mouse[button] == State.PRESSED;
	}
	
	public static Vector2f getMousePosition()
	{
		glfwGetCursorPos(window, bufferX, bufferY);
		return new Vector2f((float) bufferX.get(0), (float) bufferY.get(0));
	}
	
	public static Vector2f getMouseDelta()
	{
		if(!cursorLocked) return new Vector2f(0f, 0f);
		return getMousePosition().sub(new Vector2f(windowWidth / 2, windowHeight / 2));
	}
	
	public static boolean isCursorLocked()
	{
		return cursorLocked;
	}
	
	public static void setCursorLocked(boolean cursorLocked)
	{
		Input.cursorLocked = cursorLocked;
		if(cursorLocked)
		{
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
			glfwSetCursorPos(window, windowWidth / 2, windowHeight / 2);
		}
		else glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
}