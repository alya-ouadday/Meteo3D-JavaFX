package projet.data;

import java.util.HashMap;

public class Zone {
	
	private int lat; 
	private int lon; 
	private HashMap<Integer,Float> anomalies;

	public Zone(int lat, int lon, HashMap<Integer, Float> anomalies) {
		this.lat = lat; 
		this.lon = lon; 
		this.anomalies = anomalies;  
		
	}
	
	public Zone(int lat, int lon) {
		this(lat, lon, new HashMap<Integer, Float>()); 	
	}
	
	public HashMap<Integer,Float> getAnomalies(){
		return anomalies; 
	}
	
	public float getAnomalieAnnee(int annee) {
		return anomalies.get(annee); 
	}
	public int getLat() {
		return lat;
	}
	
	public int getLon() {
		return lon; 
	}
	@Override
	public String toString() {
		return  "(" + lat + ", "+ lon + ")";
	}

}
