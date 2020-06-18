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
    
	public Coordonnees() {
		// TODO Auto-generated constructor stub
	}
	
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
    /*
    public  static void afficheZone(Group ville, float x, float y , float z) {
    	Sphere sphere = new Sphere(0.01); 
    	final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.RED);
        sphere.setMaterial(redMaterial);
       
        
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);
        
        ville.getChildren().add(sphere);
        
    }*/
    
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
    public static int from3DtoLat(Point2D point) {
    	return (int)((point.getY() * 180)-90)*-1; 
    }
    //from 3d to lat 
    //from 3D to lon 
    public static int from3DtoLon(Point2D point) {
    	return (int)((point.getX() * 360)-180); 
    }
   
    public static Zone zonePlusProche(DonneesPlanete terre, int lat, int lon) {
    	
    	HashMap<String, Zone> zones = terre.getListeZones();
    	Zone zone = null; 
    	for(int i = -2; i <= 2;i++) {
    		for(int j = -2; j<=2; j++) {
    			lon += j; 
    			if(terre.getZone(lat, lon) != null) {
    				System.out.println("ça marche ! "+ lat + " " + lon);
    				return terre.getZone(lat, lon);
    				
    			}
    		}
    		lat += i; 
    	}
    	return zone;
    }
  public static Zone zonePluroche(DonneesPlanete terre, int lat, int lon) {
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
