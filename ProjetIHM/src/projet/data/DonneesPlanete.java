package projet.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DonneesPlanete {
	private String nom; 
	private LinkedHashMap<String, Zone> listeZones; 
	private float anomalieMax; 
	private float anomalieMin; 
	
	public DonneesPlanete(String nom) {
		this.nom= nom; 
		anomalieMax = 0;
		anomalieMin = 0; 
		listeZones = new LinkedHashMap<String, Zone>();
	}
	
	public Zone getZone(int lat, int lon) {
		return listeZones.get(lat +","+ lon); 
	}

	
	public HashMap<String, Zone> getListeZones(){
		return listeZones; 
	}
	


	public float getAnomalieMax() {
		return anomalieMax;
	}

	public void setAnomalieMax(float anomalieMax) {
		this.anomalieMax = anomalieMax;
	}

	public float getAnomalieMin() {
		return anomalieMin;
	}

	public void setAnomalieMin(float anomalieMin) {
		this.anomalieMin = anomalieMin;
	}
	
	public float getAnomalieZoneAnnee(int lat, int lon, int annee) {
		return getZone(lat, lon).getAnomalieAnnee(annee); 
	}
	
	public ArrayList<Float> getAnomaliesAnnee(int annee){
		ArrayList<Float> listeAnomalies = new ArrayList<Float>(); 
		System.out.println("Voici les anomalies pour l'année "+ annee + " : ");
		for(Zone zone: listeZones.values()) {
			System.out.println(zone + " : "+zone.getAnomalieAnnee(annee));
			listeAnomalies.add(zone.getAnomalieAnnee(annee)); 
		}
		
		return listeAnomalies; 
	}
	
	public HashMap<Integer, Float> getAnomaliesZone(int lat, int lon){
		HashMap<Integer, Float> anomalies = getZone(lat, lon).getAnomalies(); 
		System.out.println("Voici les anomalies pour la zone géographique"+ getZone(lat, lon)+ " : ");
		for (Map.Entry<Integer, Float> entry : anomalies.entrySet()) {
			System.out.println( entry.getKey() + ": " +entry.getValue() ); 	
		}
		return anomalies;
	}

}
