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
 * ModeHisto repr�sente le mode de visualisation en quadrilat�res de couleur 2D
 * @author BEN OUADDAY 
 *
 */
public class ModeQuadri {
	/**
	 * Quadrilat�res � la surface du globe
	 * LinkedHashMap qui associe une Zone au MeshView qui se trouve sur cette Zone l�  
	 */
	private static LinkedHashMap<MeshView, Zone> listeQuadri = new LinkedHashMap<MeshView, Zone>() ;
	/**
	 * Echelle de couleur des quadrilat�res
	 */
	private static EchelleCouleur echelle = new EchelleCouleur();
	
	/**
	 * retourne un quadralita�re 
	 * @param parent 
	 * 			 Group dans lequel on ajoute le quadrilat�re 
	 * @param topRight
	 * 			point haut droit du quadrilat�re 
	 * @param bottomRight 
	 * 			point bas droit du quadrilat�re 
	 * @param bottomLeft
	 * 			point bas gauche 
	 * @param topLeft
	 * 			point haut gauche 
	 * @param material 
	 * 			material du quadrilat�re
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
	 * initialise l'�chelle de couleur
	 */
    public static void setEchelleCouleur() {
    	echelle.setEchelleQuadri();
    }
    
    /**
	 * retourne un Group de quadrilat�res 
	 * @param zones 
	 * 				l'ensemble des zones connues 
	 * @param annee 
	 * 				l'ann�e pour laquelle on instancie les quadrilat�res 
	 * @return quadris 
	 */
    public static Group initQuadri(HashMap<String, Zone> zones, int annee) {
    	Group quadris = new Group(); 
    	for(Zone zone: zones.values()) {//pour chaque Zone connue du mod�le 
	    	int lat = zone.getLat(); 
	    	int lon = zone.getLon(); 
	    	// on r�cup�re le material transparent 
	    	PhongMaterial material = echelle.getTranspMaterial(); 
	    	
	    	//on ajoute � la HashMap de quadrilat�re un quadrilat�re transparent 
	    	//en lui associant la Zone ou il apparait 
	    	listeQuadri.put(AddQuadrilateral(quadris, Coordonnees.geoCoordTo3dCoord(lat - 2, lon + 2, 1.05f), 
	 	        		Coordonnees.geoCoordTo3dCoord(lat - 2, lon - 2, 1.05f),
	 	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon - 2, 1.05f),
	 	        		Coordonnees.geoCoordTo3dCoord(lat + 2, lon +2, 1.05f), material), zone);
	    	}
    	return quadris;
    }
    
    
    /** 
     * met � jour le material des quadrilateres pour la nouvelle ann�e 
	 * @param annee
	 * 
	 */
    public static void updateQuadri(int annee) {
    	for (Map.Entry<MeshView, Zone> entry : listeQuadri.entrySet()) {//pour tous les quadrilat�re 
    		//on change leur material en fonction de l'anomalie 
    		PhongMaterial material = echelle.getMaterialQuadri(entry.getValue().getAnomalieAnnee(annee));
    		entry.getKey().setMaterial(material);
    	}
    }
    
    /**
	 * cache les quadrilateres par un material transparent   
	 */
    public static void hideQuadri() {
    	for(MeshView quadri : listeQuadri.keySet()) {// pour chaque quadrilat�re
    		//on lui met un material transparent 
    		quadri.setMaterial(echelle.getTranspMaterial()); 
    	}
    }
    
    /**
	 * retourne la liste des quadrilateres 
	 * LinkedHashMap qui associe chaque quadrilat�re � la Zone sur laquelle il apparait sur le globe  
	 * @return listeQuadri 
	 */
    public static LinkedHashMap<MeshView, Zone> getQuadris(){
    	return listeQuadri; 
    }

}
