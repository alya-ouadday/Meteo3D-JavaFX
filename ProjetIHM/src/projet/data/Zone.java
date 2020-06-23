package projet.data;

import java.util.HashMap;

/**
 * 
 * Zone repr�sente une zone g�ographique 
 * @author BEN OUADDAY 
 *
 */
public class Zone {
	
	/**
	 * latitude de la zone 
	 */
	private int lat; 
	/**
	 * longitude de la zone
	 */
	private int lon; 
	/**
	 * ensemble des anomalies de temp�rature de cette zone 
	 * HashMap qui associe chaque ann�e � la valeur de l'anomalie dans la zone cette ann�e l� 
	 */
	private HashMap<Integer,Float> anomalies;
	
	/**
	 * Constructeur
	 * @param lat
	 * 			 latitude de la Zone
	 * @param lon
	 * 			  longitude de la Zone 
	 * @param anomalies
	 * 			  HashMap<Integer, Float> contenant les ann�es associ�es � leurs anomalies 
	 */
	public Zone(int lat, int lon, HashMap<Integer, Float> anomalies) {
		this.lat = lat; 
		this.lon = lon; 
		this.anomalies = anomalies;  
		
	}
	
	/**
	 * Constructeur
	 * @param lat
	 * @param lon
	 */
	public Zone(int lat, int lon) {
		this(lat, lon, new HashMap<Integer, Float>()); 	
	}
	
	/**
	 * retourne l'ensemble des anomalies de la Zone 
	 * @return anomalies
	 */
	public HashMap<Integer,Float> getAnomalies(){
		return anomalies; 
	}
	
	/**
	 * retourne l'anomalie sur cette Zone pour l'ann�e donn�e 
	 * @param annee
	 * @return anomalie lors de l'ann�e annee
	 */
	public float getAnomalieAnnee(int annee) {
		return anomalies.get(annee); 
	}
	
	/**
	 * retourne la latitude de la Zone
	 * @return lat 
	 */
	public int getLat() {
		return lat;
	}
	
	/**
	 * retourne la longitude de la Zone
	 * @return lon
	 */
	public int getLon() {
		return lon; 
	}
	
	@Override
	public String toString() {
		return  "(" + lat + ", "+ lon + ")";
	}

}
