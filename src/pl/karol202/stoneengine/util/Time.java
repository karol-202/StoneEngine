package pl.karol202.stoneengine.util;

public class Time
{
	public static final long SECOND = 1000000000L;
	
	private static double delta;
	
	public static double getDelta()
	{
		return delta;
	}
	
	public static void setDelta(double delta)
	{
		Time.delta = delta;
	}
}
