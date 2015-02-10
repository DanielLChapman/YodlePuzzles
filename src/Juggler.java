import java.util.*;


public class Juggler {
	//initialize values
	public int h = 0;
	public int e = 0;
	public int p = 0;
	//initialize map for preferences, map was used when other information was being stored
	//an arraylist would work for the same purpose
	public Map<Integer, Integer> preferences = new HashMap<Integer, Integer>();
	public int identifier = 0;
	public int currentDot = 0;
	
	//initialize juggler
	public Juggler(int h, int e, int p, Map<Integer, Integer> preferences, int identifier) {
		this.h=h;
		this.e=e;
		this.p=p;
		this.identifier = identifier;
		this.preferences = preferences;
	}
	
	//set and gets for all information stored
	public void setCurrentDot(int tempDot) {
		currentDot = tempDot;
	}
	
	public int getCurrentDot() {
		return currentDot;
	}
	
	public void setH(int h) { 
		this.h=h;
	}
	public void setE(int e) { 
		this.e=e;
	}
	public void setP(int p) { 
		this.p=p;
	}
	public void setIdentifier(int e) { 
		identifier=e;
	}
	public int getH() {
		return h;
	}
	public int getE() {
		return e;
	}
	public int getP() {
		return p;
	}
	public int getIdentifier() {
		return identifier;
	}
}
