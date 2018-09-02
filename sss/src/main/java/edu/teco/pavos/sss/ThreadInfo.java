package edu.teco.pavos.sss;

/**
 * Contains Info about a special Thread
 * @author Jean Baumgarten
 */
public class ThreadInfo {
	
	private double lat;
	private double lon;
	private boolean shouldContinue = true;

	/**
	 * Default Constructor
	 * @param lat is the lattitude
	 * @param lon is the longitude
	 */
	public ThreadInfo(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	/**
	 * Getter for remaining in loop
	 * @return boolean
	 */
	public boolean continueLoop() {
		return this.shouldContinue;
	}
	
	/**
	 * Setter for remaining in loop
	 * @param b boolean
	 */
	public void setContinue(boolean b) {
		this.shouldContinue = b;
	}
	
	/**
	 * Getter for the latitude
	 * @return double value
	 */
	public double getLat() {
		return this.lat;
	}
	
	/**
	 * Getter for the longitude
	 * @return double value
	 */
	public double getLon() {
		return this.lon;
	}
	
	

}
