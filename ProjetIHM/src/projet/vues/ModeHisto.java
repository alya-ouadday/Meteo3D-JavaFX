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

/**
 * 
 * ModeHisto représente le mode de visualisation en histogrammes 
 * @author BEN OUADDAY 
 *
 */
public class ModeHisto{
	/**
	 * histogrammes à la surface du globe
	 * LinkedHashMap qui associe une Zone au cylindre qui se trouve à cette Zone là 
	 */
	private static LinkedHashMap<Zone, Cylinder> listeHistos = new LinkedHashMap<Zone, Cylinder>() ;
	/**
	 * Echelle de Couleur des histogrammes 
	 */ 
	private static EchelleCouleur echelle = new EchelleCouleur();
	
	/**
	 * retourne un histogramme 
	 * @param origin
	 * @param target 
	 * @return line 
	 */
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
		
		/**
		 * initialise son échelle de couleur 
		 *
		 */	
	public static void setEchelleCouleur() {
		   echelle.setEchelleHisto();
	}
		 
	/**
	 * retourne un Group contenant l'ensemble des histogrammes nouvellement crées 
	 * @param zones 
	 * 				L'ensemble des zones connues 
	 * @param annee	
	 * 				L'annee pour laquelle on initialise les histogrammes
	 * @return histos
	 */
	public static Group initHistos(HashMap<String, Zone> zones, int annee) {
		    	Group histos = new Group(); 
		    	Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
		    	
		    	PhongMaterial material ;  
		    	Point3D target; 
		    	
		    	for(Zone zone: zones.values()) {//pour toutes les zones connues du modèle 
			    	int lat = zone.getLat(); 
			    	int lon = zone.getLon(); 
			    	float anomalie = zone.getAnomalieAnnee(annee); 
			    	if(!Float.isNaN(anomalie)) {
			    		//on place le "point d'arrivée" du cylindre au niveau de la Zone 
			    		//à une élévation fonction de l'anomalie 
			    		target = Coordonnees.geoCoordTo3dCoord(lat, lon, tailleHisto(anomalie)); 
			    	}
			    	else {
			    		target = Coordonnees.geoCoordTo3dCoord(lat, lon, 1); 
			    	}
			    	//on récupère le bon material en fonction de l'anomalie 
	        		 material = echelle.getMaterialHisto(anomalie);
	        		 Cylinder histo = createLine(origin, target); 
	        		 histo.setMaterial(material);
	        		 
	        		 //on ajoute l'histogramme à la liste d'histogramme en lui associant sa Zone 
			    	 listeHistos.put(zone, histo); 
			    	 //on l'ajoute au Group d'histogrammes 
			    	 histos.getChildren().add(histo); 
		    	}
		    	return histos;
		 }
	
		/**
		 * met à jour la taille et le material des histogrammes 
		 * @param annee 
		 */
		 public static void updateHistos(int annee) { 
			 Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
			 Point3D target;
		     PhongMaterial material; 
		     
		     for (Map.Entry<Zone, Cylinder> entry : listeHistos.entrySet()) {
			     int lat = entry.getKey().getLat(); 
				 int lon = entry.getKey().getLon(); 
				 
				 float anomalie = entry.getKey().getAnomalieAnnee(annee);
				 
				 //nouvelle hauteur fonction de l'anomalie 
		         target = Coordonnees.geoCoordTo3dCoord(lat, lon, tailleHisto(anomalie));
		         material = echelle.getMaterialHisto(anomalie);
		        		
			     Point3D diff = target.subtract(origin);
			     double height = diff.magnitude();
			  // on met à jour son material en fonction de la nouvelle anomalie de l'année 
			     entry.getValue().setMaterial(material);
			   // on met à jour sa hauteur 
			     entry.getValue().setHeight(height);	   
		    }
		 }

		 /**
			 * retourne un material en fonction de l'anomalie entrée en paramètre
			 * @param anomalie associée à l'histogramme 
			 * @return float 
			 * 				la taille de l'histogramme 
			 */
	 public static float tailleHisto(float anomalie) {
		 float taille = (Math.abs(anomalie)/10) + 1; 
		 return Math.round(taille*100.0)/100.0f;
	 }
}
