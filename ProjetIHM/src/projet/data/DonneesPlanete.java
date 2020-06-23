package projet.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * DonneesPlanete contient les fonctionnalités de base pour
 * manipuler les données du fichier .csv pour l'application
 * @author BEN OUADDAY
 *
 */
public class DonneesPlanete {
	/**
	 * nom de la planète dont on manipule les données 
	 */
	private String nom; 
	/**
	 * Ensemble des zones de cette planète 
	 * LinkedHashMap qui associe une chaine de caractère "latitude,longitude" à la
	 * Zone correspondante
	 */
	private LinkedHashMap<String, Zone> listeZones; 
	/**
	 * l'anomalie de température maximum du fichier 
	 */
	private float anomalieMax; 
	/**
	 * l'anomalie de température minimum du fichier
	 */
	private float anomalieMin; 
	
	/**
	 * Constructeur
	 * @param nom de la planète 
	 */
	public DonneesPlanete(String nom) {
		this.nom= nom; 
		anomalieMax = 0;
		anomalieMin = 0; 
		listeZones = new LinkedHashMap<String, Zone>();
	}

	
	/**
	 * retourne la Zone de latitude lat et longitude lon
	 * @param lat 
	 * 				latitude de la Zone recherchée 
	 * @param lon
	 * 				longitude de la Zone recherchée 
	 * @return Zone
	 */
	public Zone getZone(int lat, int lon) {
		return listeZones.get(lat +","+ lon); 
	}

	/**
	 * retourne l'ensemble des Zone du fichier 
	 * @return listeZones
	 */
	public HashMap<String, Zone> getListeZones(){
		return listeZones; 
	}
	

	/**
	 * retourne l'anomalie maximale du fichier 
	 * @return anomalieMax
	 */
	public float getAnomalieMax() {
		return anomalieMax;
	}

	/**
	 * modifie la valeur de l'anomalie maximale 
	 * @param anomalieMax 
	 */
	public void setAnomalieMax(float anomalieMax) {
		this.anomalieMax = anomalieMax;
	}
	
	/**
	 * retourne l'anomalie minimum du fichier 
	 * @return anomalieMin
	 */
	public float getAnomalieMin() {
		return anomalieMin;
	}
	
	/**
	 * modifie la valeur de l'anomalie minimale 
	 * @param anomalieMin
	 */
	public void setAnomalieMin(float anomalieMin) {
		this.anomalieMin = anomalieMin;
	}
	
	/**
	 * retourne l'anomalie d'une zone donnée une année donnée 
	 * @param lat 
	 * @param lon 
	 * @param annee
	 * @return float
	 */
	public float getAnomalieZoneAnnee(int lat, int lon, int annee) {
		return getZone(lat, lon).getAnomalieAnnee(annee); 
	}
	
	/**
	 * retourne les anomalies de toutes les zones une année donnée 
	 * @param annee 
	 * @return ArrayList<Float> 
	 */
	public ArrayList<Float> getAnomaliesAnnee(int annee){
		ArrayList<Float> listeAnomalies = new ArrayList<Float>(); 
		System.out.println("Voici les anomalies pour l'année "+ annee + " : ");
		for(Zone zone: listeZones.values()) {
			System.out.println(zone + " : "+zone.getAnomalieAnnee(annee));
			listeAnomalies.add(zone.getAnomalieAnnee(annee)); 
		}
		
		return listeAnomalies; 
	}
	
	/**
	 * retourne les anomalies d'une Zone pour toutes les années 
	 * @param lat 
	 * @paral lon 
	 * @return HashMap<Integer, Float>
	 */
	public HashMap<Integer, Float> getAnomaliesZone(int lat, int lon){
		HashMap<Integer, Float> anomalies = getZone(lat, lon).getAnomalies(); 
		System.out.println("Voici les anomalies pour la zone géographique"+ getZone(lat, lon)+ " : ");
		for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
			System.out.println( entry.getKey() + ": " +entry.getValue() ); 	
		}
		return anomalies;
	}

}
