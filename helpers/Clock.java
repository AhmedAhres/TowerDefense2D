package helpers;

import org.lwjgl.Sys;

public class Clock {
	
	private static boolean paused = false;
	public static long lastFrame, totalTime;
	public static float d = 0, multiplier = 1;
	
	public static long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution(); //*1000 because it returns in miliseconds
	}
	
	public static float getDelta() { //deltatime is difference between now and last frame
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta * 0.01f;
	}
	
	public static float Delta() {
		if (paused){
			return 0;
		} else return d * multiplier;
	}
	
	public static float TotalTime() { //getter
		return totalTime;
	}
	
	public static float Multiplier() {
		return multiplier;
	}
	
	public static void update() {
		d = getDelta();
		totalTime += d;
	}

}
