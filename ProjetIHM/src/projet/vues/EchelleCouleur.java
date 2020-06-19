package projet.vues;

import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class EchelleCouleur {
	 
	private HashMap<String, PhongMaterial> echelle= new HashMap<String, PhongMaterial> ();
	
	
	public HashMap<String, PhongMaterial> getEchelle(){
		return echelle; 
	}

	public  void setEchelleQuadri() {
		
		//float alpha = 0; 
		
		final PhongMaterial rbMat = new PhongMaterial();
        Color royalBlue = Color.rgb(65, 105, 225, 0);
        rbMat.setDiffuseColor(royalBlue);
        rbMat.setSpecularColor(royalBlue);
        echelle.put("royalBlue", rbMat);  
        
        final PhongMaterial skMat = new PhongMaterial();
        Color skyBlue = Color.rgb(135, 206, 235, 0);
        skMat.setDiffuseColor(skyBlue);
        skMat.setSpecularColor(skyBlue);
        echelle.put("skyBlue", skMat); 
        
        final PhongMaterial lbMat = new PhongMaterial();
        Color lightBlue = Color.rgb(173, 216, 230, 0);  
        lbMat.setDiffuseColor(lightBlue);
        lbMat.setSpecularColor(lightBlue);
        echelle.put("lightBlue", lbMat); 
        
        final PhongMaterial yellowMat = new PhongMaterial();
        //Color yellow = new Color(0.5,0.5,0, 0.6); 
        Color yellow = Color.rgb(230,230,0, 0); 
        yellowMat.setDiffuseColor(yellow);
        yellowMat.setSpecularColor(yellow);
        echelle.put("yellow", yellowMat);  
        
        
        final PhongMaterial goldMat = new PhongMaterial();
        //Color gold =Color.rgb(255, 215, 0, 0);
        Color gold =Color.rgb(240, 190, 0, 0);
        goldMat.setDiffuseColor(gold);
        goldMat.setSpecularColor(gold);
        echelle.put("gold", goldMat); 
        
        final PhongMaterial doMat = new PhongMaterial();
        Color darkOrange = Color.rgb(255, 140, 0, 0);
        doMat.setDiffuseColor(darkOrange);
        doMat.setSpecularColor(darkOrange);
        echelle.put("darkOrange", doMat); 
        
        final PhongMaterial redMat = new PhongMaterial();
        Color red = Color.rgb(255, 0, 0, 0);
        redMat.setDiffuseColor(red);
        redMat.setSpecularColor(red);
        echelle.put("red", redMat); 
        
        final PhongMaterial grayMat = new PhongMaterial();
        Color gray =Color.rgb(0, 0, 0, 0);
        grayMat.setDiffuseColor(gray);
        grayMat.setSpecularColor(gray);
        echelle.put("gray", grayMat); 
        
        final PhongMaterial transparent = new PhongMaterial();
        Color transp =Color.rgb(80, 80, 80);
        transparent.setDiffuseColor(transp);
        transparent.setSpecularColor(transp);
        echelle.put("gray", grayMat); 
	}
	
	public PhongMaterial getMaterialQuadri(float anomalie) {
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
	
	public PhongMaterial getTranspMaterial() {
		return echelle.get("gray"); 
	}
	public void setEchelleHisto() {
		final PhongMaterial redMat = new PhongMaterial();
        Color red = Color.rgb(255, 0, 0);
        redMat.setDiffuseColor(red);
        redMat.setSpecularColor(red);
        echelle.put("red", redMat); 
        
        final PhongMaterial rbMat = new PhongMaterial();
        Color royalBlue = Color.rgb(65, 105, 225);
        rbMat.setDiffuseColor(royalBlue);
        rbMat.setSpecularColor(royalBlue);
        echelle.put("royalBlue", rbMat);
        
        final PhongMaterial grayMat = new PhongMaterial();
        Color gray =Color.rgb(80, 80, 80);
        grayMat.setDiffuseColor(gray);
        grayMat.setSpecularColor(gray);
        echelle.put("gray", grayMat); 
        
       
		
	}
	public PhongMaterial getMaterialHisto(float anomalie) {
		if(anomalie >= 0) {
			return echelle.get("red"); 
		}
		else if (anomalie < 0 ) {
			return echelle.get("royalBlue"); 
		}
		else {
			return echelle.get("gray");
		}
	}
}
