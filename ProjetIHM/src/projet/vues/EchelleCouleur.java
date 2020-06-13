package projet.vues;

import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class EchelleCouleur {
	 
	private static HashMap<String, PhongMaterial> echelle= new HashMap<String, PhongMaterial> ();
	
	
	public HashMap<String, PhongMaterial> getEchelle(){
		return echelle; 
	}

	public  static void setEchelleQuadri() {
		
		float alpha = 0.3f; 
		
		final PhongMaterial rbMat = new PhongMaterial();
        Color royalBlue = Color.rgb(65, 105, 225, alpha);
        rbMat.setDiffuseColor(royalBlue);
        rbMat.setSpecularColor(royalBlue);
        echelle.put("royalBlue", rbMat);  
        
        final PhongMaterial skMat = new PhongMaterial();
        Color skyBlue = Color.rgb(135, 206, 235, alpha);
        skMat.setDiffuseColor(skyBlue);
        skMat.setSpecularColor(skyBlue);
        echelle.put("skyBlue", skMat); 
        
        final PhongMaterial lbMat = new PhongMaterial();
        Color lightBlue = Color.rgb(173, 216, 230, alpha);  
        lbMat.setDiffuseColor(lightBlue);
        lbMat.setSpecularColor(lightBlue);
        echelle.put("lightBlue", lbMat); 
        
        final PhongMaterial yellowMat = new PhongMaterial();
        Color yellow = Color.rgb(255, 255, 0, 0.00001);
        yellowMat.setDiffuseColor(yellow);
        yellowMat.setSpecularColor(yellow);
        echelle.put("yellow", yellowMat);  
        
        
        final PhongMaterial goldMat = new PhongMaterial();
        Color gold =Color.rgb(255, 215, 0, alpha);
        goldMat.setDiffuseColor(gold);
        goldMat.setSpecularColor(gold);
        echelle.put("gold", goldMat); 
        
        final PhongMaterial doMat = new PhongMaterial();
        Color darkOrange = Color.rgb(255, 140, 0, alpha);
        doMat.setDiffuseColor(darkOrange);
        doMat.setSpecularColor(darkOrange);
        echelle.put("darkOrange", doMat); 
        
        final PhongMaterial redMat = new PhongMaterial();
        Color red = Color.rgb(255, 0, 0, alpha);
        redMat.setDiffuseColor(red);
        redMat.setSpecularColor(red);
        echelle.put("red", redMat); 
        
        final PhongMaterial grayMat = new PhongMaterial();
        Color gray =Color.rgb(0, 0, 0, alpha);
        grayMat.setDiffuseColor(gray);
        grayMat.setSpecularColor(gray);
        echelle.put("gray", grayMat); 
	}
	
	public static PhongMaterial getMaterialQuadri(float anomalie) {
   		PhongMaterial material; 
		if(anomalie <-2) {
			material = echelle.get("royalBlue"); 
		}
		
		else if( anomalie < -1) {
			material = echelle.get("skyBlue");
		}
		
		else if( anomalie < -0.5) {
			material = echelle.get("lightBlue"); 
		}
		
		else if( anomalie < 0.5) {
			material = echelle.get("yellow"); 
		}
		
		else if( anomalie < 1) {
			material = echelle.get("gold");
		}
		
		else if( anomalie < 2) {	
			material = echelle.get("darkOrange");
		}
		
		else if( anomalie >2) {
			material = echelle.get("red");
		}
		else {
			material = echelle.get("gray");
		}
		
		return material; 
	}
}
