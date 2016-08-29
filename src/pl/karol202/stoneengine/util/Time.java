package pl.karol202.stoneengine.util;

import java.util.ArrayList;

public class Time
{
	public interface FPSListener
	{
		void onFPSChanged(int fps);
	}
	
	public static final long SECOND = 1000000000L;
	
	private static ArrayList<FPSListener> listeners = new ArrayList<>();
	private static long delta;
	private static int fps;
	
	public static void addFPSListener(FPSListener listener)
	{
		listeners.add(listener);
	}
	
	public static long getDelta()
	{
		return delta;
	}
	
	public static void setDelta(long delta)
	{
		Time.delta = delta;
	}
	
	public static int getFPS()
	{
		return fps;
	}
	
	public static void setFPS(int fps)
	{
		Time.fps = fps;
		listeners.forEach(listener -> listener.onFPSChanged(fps));
	}
}
