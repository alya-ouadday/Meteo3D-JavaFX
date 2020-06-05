package projet.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonneesTerre {
	private HashMap<String, Zone> listeZones; 
	private ArrayList<Integer> listeAnnees; 
	private float anomalieMax; 
	private float anomalieMin; 
	
	public DonneesTerre() {
		anomalieMax = 0;
		anomalieMin = 0; 
		listeAnnees = new ArrayList<Integer>(); 
		listeZones = new HashMap<String, Zone>();
	}
	
	public Zone getZone(int lat, int lon) {
		return listeZones.get(lat +","+ lon); 
	}

	public ArrayList<Integer> getListeAnnees() {
		return listeAnnees;
	}


	public float getAnomalieMax() {
		System.out.println("L'anomalie maximale est de " + anomalieMax);
		return anomalieMax;
	}

	public void setAnomalieMax(float anomalieMax) {
		this.anomalieMax = anomalieMax;
	}

	public float getAnomalieMin() {
		System.out.println("L'anomalie minimale est de " + anomalieMin);
		return anomalieMin;
	}

	public void setAnomalieMin(float anomalieMin) {
		this.anomalieMin = anomalieMin;
	}
	
	public float getAnomalieZoneAnnee(int lat, int lon, int annee) {
		System.out.println("L'anomalie de l'année "+ annee + " à la zone "+ getZone(lat, lon) + " est de : "+
				getZone(lat, lon).getAnomalieAnnee(annee));
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
