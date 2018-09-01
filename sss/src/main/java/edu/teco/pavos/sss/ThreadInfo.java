package edu.teco.pavos.sss;

/**
 * Contains Info about a special Thread
 * @author Jean Baumgarten
 */
public class ThreadInfo {
	
	private double lat;
	private double lon;
	
	private double latD = 2;
	private double lonD = 4;

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
