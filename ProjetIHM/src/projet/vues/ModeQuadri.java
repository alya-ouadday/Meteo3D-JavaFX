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
/**
 * 
 * ModeHisto représente le mode de visualisation en quadrilatères de couleur 2D
 * @author BEN OUADDAY 
 *
 */
public class ModeQuadri {
	/**
	 * Quadrilatères à la surface du globe
	 * LinkedHashMap qui associe une Zone au MeshView qui se trouve sur cette Zone là  
	 */
	private static LinkedHashMap<MeshView, Zone> listeQuadri = new LinkedHashMap<MeshView, Zone>() ;
	/**
	 * Echelle de couleur des quadrilatères
	 */
	private static EchelleCouleur echelle = new EchelleCouleur();
	
	/**
	 * retourne un quadralitaère 
	 * @param parent 
	 * 			 Group dans lequel on ajoute le quadrilatère 
	 * @param topRight
	 * 			point haut droit du quadrilatère 
	 * @param bottomRight 
	 * 			point bas droit du quadrilatère 
	 * @param bottomLeft
	 * 			point bas gauche 
	 * @param topLeft
	 * 			point haut gauche 
	 * @param material 
	 * 			material du quadrilatère
	 * @return meshView 
	 */
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
    /**
	 * initialise l'échelle de couleur
	 */
    public static void setEchelleCouleur() {
    	echelle.setEchelleQuadri();
    }
    
    /**
	 * retourne un Group de quadrilatères 
	 * @param zones 
	 * 				l'ensemble des zones connues 
	 * @param annee 
	 * 				l'année pour laquelle on instancie les quadrilatères 
	 * @return quadris 
	 */
    public static Group initQuadri(HashMap<String, Zone> zones, int annee) {
    	Group quadris = new Group(); 
    	for(Zone zone: zones.values()) {//pour chaque Zone connue du modèle 
	    	int lat = zone.getLat(); 
	    	int lon = zone.getLon(); 
	    	// on récupère le material transparent 
	    	PhongMaterial material = echelle.getTranspMaterial(); 
	    	
	    	//on ajoute à la HashMap de quadrilatère un quadrilatère transparent 
	    	//en lui associant la Zone ou il apparait 
	    	listeQuadri.put(AddQuadrilateral(quadris, Coordonnees.geoCoordTo3dCoord(lat - 2, lon + 2, 1.05f), 
	 	        		Coordonnees.geoCoordTo3dCoord(lat - 2, lon - 2, 1.05f),
	 	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon - 2, 1.05f),
	 	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon +2, 1.05f), material), zone);
	    	}
    	return quadris;
    }
    
    
    /** 
     * met à jour le material des quadrilateres pour la nouvelle année 
	 * @param annee
	 * 
	 */
    public static void updateQuadri(int annee) {
    	for (Map.Entry<MeshView, Zone> entry : listeQuadri.entrySet()) {//pour tous les quadrilatère 
    		//on change leur material en fonction de l'anomalie 
    		PhongMaterial material = echelle.getMaterialQuadri(entry.getValue().getAnomalieAnnee(annee));
    		entry.getKey().setMaterial(material);
    	}
    }
    
    /**
	 * cache les quadrilateres par un material transparent   
	 */
    public static void hideQuadri() {
    	for(MeshView quadri : listeQuadri.keySet()) {// pour chaque quadrilatère
    		//on lui met un material transparent 
    		quadri.setMaterial(echelle.getTranspMaterial()); 
    	}
    }
    
    /**
	 * retourne la liste des quadrilateres 
	 * LinkedHashMap qui associe chaque quadrilatère à la Zone sur laquelle il apparait sur le globe  
	 * @return listeQuadri 
	 */
    public static LinkedHashMap<MeshView, Zone> getQuadris(){
    	return listeQuadri; 
    }

}
