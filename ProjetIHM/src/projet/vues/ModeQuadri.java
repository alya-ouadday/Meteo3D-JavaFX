package projet.vues;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import projet.data.DonneesPlanete;
import projet.data.Zone;

public class ModeQuadri {

	private static LinkedHashMap<MeshView, Zone> listeQuadri = new LinkedHashMap<MeshView, Zone>() ;
	private static EchelleCouleur echelle = new EchelleCouleur();
	
    private static MeshView AddQuadrilateral(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft, Point3D topLeft,PhongMaterial material)
    {
        final TriangleMesh triangleMesh = new TriangleMesh();
        final float[] points = {
                (float)topRight.getX(), (float)topRight.getY(), (float)topRight.getZ(),
                (float)topLeft.getX(), (float)topLeft.getY(), (float)topLeft.getZ(),
                (float)bottomLeft.getX(), (float)bottomLeft.getY(), (float)bottomLeft.getZ(),
                (float)bottomRight.getX(), (float)bottomRight.getY(), (float)bottomRight.getZ(),
        };
        final float[] texCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
        };
        final int[] faces = {
                0, 1, 1, 0, 2, 2,
                0, 1, 2, 2, 3, 3
        };
        
        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoords);
        triangleMesh.getFaces().setAll(faces);
        
        final MeshView meshView = new MeshView(triangleMesh);
        meshView.setMaterial(material);
        parent.getChildren().addAll(meshView);
        return meshView;
    }
    public static void setEchelleCouleur() {
    	echelle.setEchelleQuadri();
    }
    public static Group initQuadri(HashMap<String, Zone> zones, int annee) {
    	Group quadris = new Group(); 
    	for(Zone zone: zones.values()) {
    	int lat = zone.getLat(); 
    	int lon = zone.getLon(); 
    	PhongMaterial material = echelle.getTranspMaterial(); 
    			//echelle.getMaterialQuadri(zone.getAnomalieAnnee(annee));
    	listeQuadri.put(AddQuadrilateral(quadris, Coordonnees.geoCoordTo3dCoord(lat - 2, lon + 2, 1.05f), 
 	        		Coordonnees.geoCoordTo3dCoord(lat - 2, lon - 2, 1.05f),
 	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon - 2, 1.05f),
 	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon +2, 1.05f), material), zone);
    	}
    	return quadris;
    }
    
    public static void updateQuadri(int annee) {
    	PhongMaterial material; 
    	for (Map.Entry<MeshView, Zone> entry : listeQuadri.entrySet()) {
    	   material = echelle.getMaterialQuadri(entry.getValue().getAnomalieAnnee(annee));
    		entry.getKey().setMaterial(material);
    	}
    }
    public static void hideQuadri() {
    	for(MeshView quadri : listeQuadri.keySet()) {
    		quadri.setMaterial(echelle.getTranspMaterial()); 
    	}
    }
    
    /*
    
    public static void setModeQuadri(DonneesPlanete terre, int annee, Group groupQuadri) {
    	long startTime = System.nanoTime(); 
    	for(int lat = -88; lat <92; lat+=4) {
        	for(int lon = -178; lon<182; lon+=4) {
        		float anomalie = terre.getAnomalieZoneAnnee(lat, lon, annee); 
        		PhongMaterial material = EchelleCouleur.getMaterialQuadri(anomalie);

        		 AddQuadrilateral(groupQuadri, Coordonnees.geoCoordTo3dCoord(lat - 2, lon + 2, 1.05f), 
      	        		Coordonnees.geoCoordTo3dCoord(lat - 2, lon - 2, 1.05f),
      	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon - 2, 1.05f),
      	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon +2, 1.05f), material );
        	}		
    	}
    	
    	long endTime   = System.nanoTime();
		double totalTime = (endTime - startTime) / 1000000000.0;
		System.out.println("temps d'execution setMode: " + totalTime);
    }
    */
    
    public static LinkedHashMap<MeshView, Zone> getQuadris(){
    	return listeQuadri; 
    }

}
