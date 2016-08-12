package pl.karol202.stoneengine.util;

public class Time
{
	public static final long SECOND = 1000000000L;
	
	private static long delta;
	
	public static long getDelta()
	{
		return delta;
	}
	
	public static void setDelta(long delta)
	{
		Time.delta = delta;
	}
}
