package projet.vues;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import projet.data.DonneesPlanete;
import projet.data.Zone;

public class ModeHisto{
	private static LinkedHashMap<Zone, Cylinder> listeHistos = new LinkedHashMap<Zone, Cylinder>() ;
	private static EchelleCouleur echelle = new EchelleCouleur();
	
		public static Cylinder createLine(Point3D origin, Point3D target) { 	
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }
		
	public static void setEchelleCouleur() {
		   echelle.setEchelleHisto();
	}
		 public static Group initHistos(HashMap<String, Zone> zones, int annee) {
		    	Group histos = new Group(); 
		    	Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
		    	PhongMaterial material ;  
		    	Point3D target; 
		    	for(Zone zone: zones.values()) {
			    	int lat = zone.getLat(); 
			    	int lon = zone.getLon(); 
			    	float anomalie = zone.getAnomalieAnnee(annee); 
			    	if(anomalie != Float.NaN) {
	        		target = Coordonnees.geoCoordTo3dCoord(lat, lon, tailleHisto(anomalie)); 
			    	}
			    	else {
			    		target = Coordonnees.geoCoordTo3dCoord(lat, lon, 2); 
			    	}
	        		 material = echelle.getMaterialHisto(anomalie);
	        		 Cylinder histo = createLine(origin, target); 
	        		 histo.setMaterial(material);
			    	 listeHistos.put(zone, histo); 
			    	 histos.getChildren().add(histo); 
		    	}
		    	return histos;
		 }
		 
		 public static void updateHistos(int annee) {
			 long startTime = System.nanoTime(); 
			 Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
			 Point3D target;
		    	PhongMaterial material; 
		    	for (Map.Entry<Zone, Cylinder> entry : listeHistos.entrySet()) {
		    		int lat = entry.getKey().getLat(); 
			    	int lon = entry.getKey().getLon(); 
			    	float anomalie = entry.getKey().getAnomalieAnnee(annee);
			    	if(anomalie != Float.NaN) {
	        		target = Coordonnees.geoCoordTo3dCoord(lat, lon, tailleHisto(anomalie));}
			    	else {
			    	target = Coordonnees.geoCoordTo3dCoord(lat, lon, 2);
			    	}
	        		material = echelle.getMaterialHisto(anomalie);
		    	   Point3D diff = target.subtract(origin);
		           double height = diff.magnitude();
		    	   entry.getValue().setMaterial(material);
		    	   entry.getValue().setHeight(height);;
		    	   
		    	}
		    	long endTime   = System.nanoTime();
				double totalTime = (endTime - startTime) / 1000000000.0;
				System.out.println("temps d'execution updateHisto pour l'annee " + annee + ": " + totalTime);
		    }
    public static void setModeHisto(DonneesPlanete terre, int annee,  Group modeVisualisation) {
    	long startTime = System.nanoTime(); 
    	Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
    	PhongMaterial material ;  
    	Point3D target; 
    	for(int lat = -88; lat <92; lat+=4) {
        	for(int lon = -178; lon<182; lon+=4) {
        		float anomalie = terre.getAnomalieZoneAnnee(lat, lon, annee); 
        		target = Coordonnees.geoCoordTo3dCoord(lat, lon, anomalie); 
        		 material = echelle.getMaterialHisto(anomalie);
        		 Cylinder histo = createLine(origin, target); 
        		 histo.setMaterial(material);
        		 modeVisualisation.getChildren().add(histo); 
        	}
    	}
    	long endTime   = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000.0;
		System.out.println("temps d'execution setModeHisto: " + totalTime);
	
    }
    
    
	 public static void initHist(HashMap<String, Zone> zones, int annee, Group modeVisualisation) {
		 long startTime = System.nanoTime(); 
	    	Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
	    	PhongMaterial material ;  
	    	Point3D target; 
	    	for(Zone zone: zones.values()) {
		    	int lat = zone.getLat(); 
		    	int lon = zone.getLon(); 
		    	float anomalie = zone.getAnomalieAnnee(annee); 
     		target = Coordonnees.geoCoordTo3dCoord(lat, lon, tailleHisto(anomalie)); 
     		 material = echelle.getMaterialHisto(anomalie);
     		 Cylinder histo = createLine(origin, target); 
     		 histo.setMaterial(material);
		    modeVisualisation.getChildren().add(histo); 
	    	}
	    	long endTime   = System.nanoTime();
			double totalTime = (endTime - startTime) / 1000000000.0;
			System.out.println("temps d'execution initModeHisto: " + totalTime);
	    	
	 }
	 
	 public static float tailleHisto(float anomalie) {
		 return (Math.abs(anomalie)/10) + 1; 
	 }
}
