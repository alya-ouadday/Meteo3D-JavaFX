package projet.vues;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import projet.data.DonneesPlanete;
import projet.data.Zone;

/**
 * 
 * Coordonees permet de faire le lien entre des coordonnées 3D et des latitudes/longitudes 
 * @author BEN OUADDAY 
 *
 */
public class Coordonnees {
	/**
	 * valeurs des offset de latitude/longitude
	 */
	private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;
    

    /**
	 * retourne un point3D qui correspond aux valeurs de latitude longitude entrées 
	 * @param lat 
	 * 			  la latitude 
	 * @param lon
	 * 			  longitude
	 * @param radius
	 * 			   distance entre le point et la surface de la terre 
	 * @return Point3D
	 */
    public static Point3D geoCoordTo3dCoord(float lat, float lon, float radius) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))*radius,
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor))*radius,
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))*radius);
    }
 
  
    /**
	 * affiche une sphere rouge à une zone donnée  
	 * @param ville
	 * 		       le Group dans lequel sera placée la sphère 
	 * @param latitude de la zone ciblée 
	 * @param longitude de la zone ciblée 
	 * 				
	 */
    public  static void afficheZone(Group ville, int latitude, int longitude) {
    	Sphere sphere = new Sphere(0.01);
    	Point3D coor = geoCoordTo3dCoord(latitude, longitude, 1); 
    	final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.RED);
        sphere.setMaterial(redMaterial);
 
        sphere.setTranslateX(coor.getX());
        sphere.setTranslateY(coor.getY());
        sphere.setTranslateZ(coor.getZ());
        
        ville.getChildren().add(sphere);
    }

    
    /**
	 * retourne la Zone connue la plus proche de la latitude longitude entrée en paramètres
	 * @param terre 
	 * 				instance contenant les Zones connues
	 * @param lat 
	 * 				latitude de la zone non connue 
	 * @param lon 
	 * 				longitude de la zone non connue
	 * @return Zone 
	 * 				Zone existante la plus proche
	 */
  public static Zone zonePlusproche(DonneesPlanete terre, int lat, int lon) {
	  	int latitude = Math.abs(lat);
	  	int longitude = Math.abs(lon);

	  	Zone zone = null; 
	  	if(latitude % 4 != 0) { // si la latitude n'existe pas dans le fichier 
	    	if((latitude-2)%4 == (latitude +2)%4 && (latitude-2)%4 ==0) {
	    		latitude = latitude - 2;    		
	    	}
	    	else if ((latitude-1)%4 ==0) {
	    		latitude -=1;
	    	}
	    	else {
	    		latitude +=1;
	    	}
	  	} 	
	    	System.out.println(latitude);
	    if(longitude % 4 != 2) { // si la longitude n'existe pas dans le fichier 
		    if((longitude -2)%4 == (longitude + 2)%4 && (longitude -2)%4 == 2) {
		    	longitude = longitude -2; 
		    }
		    else if( (longitude -1)%4 == 2) {
		    	longitude -= 1; 
		    }
		    else {
		    	longitude += 1;
		    }
	    }
	  	
	  	if(lat < 0) latitude = - latitude; 
	  	if(lon < 0) longitude = - longitude; 

	  
    	zone = terre.getZone(latitude, longitude);
    	return zone;
  }
}
