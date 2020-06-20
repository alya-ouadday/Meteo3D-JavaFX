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

public class Coordonnees {
	private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;
    

	
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
 
    
    public  static void afficheZone(Group ville, Point3D point) {
    	Sphere sphere = new Sphere(0.01); 
    	final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.RED);
        sphere.setMaterial(redMaterial);
      
        sphere.setTranslateX(point.getX());
        sphere.setTranslateY(point.getY());
        sphere.setTranslateZ(point.getZ()); 
     
        ville.getChildren().add(sphere);
        
    }
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

  public static Zone zonePlusproche(DonneesPlanete terre, int lat, int lon) {
	  	int latitude = Math.abs(lat);
	  	int longitude = Math.abs(lon);

	  	Zone zone = null; 
	  	if(latitude % 4 != 0) {
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
	    if(longitude % 4 != 2) {
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
	  	
	  	System.out.println(longitude);
	  	if(lat < 0) latitude = - latitude; 
	  	if(lon < 0) longitude = - longitude; 
	  	System.out.println(latitude + " " + longitude);
    	zone = terre.getZone(latitude, longitude);
    	return zone;
  }
}
