package projet.vues;

import java.util.HashMap;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import projet.data.DonneesPlanete;

public class ModeHisto{
    	
	
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
    
    public static void setModeHisto(DonneesPlanete terre, int annee,  Group modeVisualisation) {
    	Point3D origin = Coordonnees.geoCoordTo3dCoord(0, 0, 0); 
    	PhongMaterial material ;  
    	Point3D target; 
    	for(int lat = -88; lat <92; lat+=4) {
        	for(int lon = -178; lon<182; lon+=4) {
        		float anomalie = terre.getAnomalieZoneAnnee(lat, lon, annee); 
        		target = Coordonnees.geoCoordTo3dCoord(lat, lon, anomalie); 
        		 material = EchelleCouleur.getMaterialQuadri(anomalie);
        		 Cylinder histo = createLine(origin, target); 
        		 histo.setMaterial(material);
        		 modeVisualisation.getChildren().add(histo); 
        	}
    	}
    	
    	 
    	
    	
    }
}
