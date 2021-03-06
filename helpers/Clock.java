package helpers;

import org.lwjgl.Sys;

public class Clock { //time class
	
	private static boolean paused = false;
	public static long  totalTime;
	public static float d = 0, multiplier = 1;
	public static long lastFrame = getTime();
	
	public static long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution(); //*1000 because it returns in miliseconds
	}
	
	public static float getDelta() { //deltatime is difference between now and last frame
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		if (delta * 0.001f > 0.05f) 
			return 0.05f; //when moving window this value can get high, which creates a bug
		return delta * 0.001f;
	}
	
	public static float Delta() {
		if (paused){
			return 0;
		} else return d * multiplier;
	}
	
	public static float TotalTime() { 
		return totalTime;
	}
	
	public static float Multiplier() {
		return multiplier;
	}
	
	public static void update() {
		d = getDelta();
		totalTime += d;
	}
	
	public static void ChangeMultiplier(float change) {
		if (multiplier + change < -1 && multiplier + change > 7){
			
		}else {
			multiplier += change;
		}
	}
	
	public static void Pause() {
		if (paused) paused = false;
		else paused = true;
	}

}
