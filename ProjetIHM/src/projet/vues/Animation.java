package projet.vues;

import javafx.animation.AnimationTimer;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * 
 * Animation gère tout ce qui touche à l'animation de l'application 
 * @author BEN OUADDAY 
 *
 */

public class Animation  {
	/**
	 * Image des boutons pause et play 
	 */
	private Image imagePause = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/btnPause.PNG"), 41, 38, false, false);
	private Image imagePlay = new Image(getClass().getResourceAsStream("/projet/vues/vuesBoutons/btnPlay.PNG"), 41, 38, false, false);
	/**
	 * valeur de la vitesse de rotation de la terre 
	 */
	private  int rotationSpeed ; 
	/**
	 * rapport entre vitesse actuelle et initiale de l'animation
	 */
	private float multipleSpeed;
	/**
	 * Property dont la valeur désigne une vitesse plus rapide et plus lente 
	 */
	private FloatProperty speedForProp;
	private FloatProperty speedBackProp;
	/**
	 * constantes de vitesses:
	 * respectivement vitesse maximale, minimale et initiale de l'animation
	 */
	private static final int SPEEDMAX = 500;
	private static final int SPEEDMIN = 10; 
	private static final int INITIAL_SPEED = 100; 
	/**
	 * nombre de tour de la terre sur elle-même 
	 */
	private float angle ; 
	/**
	 * AnimationTimer qui permet d'animer la terre 
	 */
	private AnimationTimer timer; 
	/**
	 * true si l'animation est marche, false sinon
	 */
	private boolean play; 
	
	/**
	 * Constructeur
	 * @param sliderAnnee
	 * 					 le slider qui sera incrémenté au cours de l'animation 
	 * @param earth
	 * 					 le Group contenant la terre qui va tourner 
	 */
	public Animation(Slider sliderAnnee, Group earth) {
		  rotationSpeed = INITIAL_SPEED ; 
	      multipleSpeed = 1; 
	      speedForProp = new SimpleFloatProperty(multipleSpeed + 0.25f); 
	      speedBackProp = new SimpleFloatProperty(multipleSpeed - 0.25f); 
	      angle = 1; 
	      play = false; 
	      final long startNanoTime = System.nanoTime();; 
		
	       timer = new AnimationTimer() {
	    	   @Override
		        public void handle(long currentNanoTime) {
		        	double t = (currentNanoTime - startNanoTime)/ 1000000000.0; 
		        	earth.setRotationAxis(new Point3D(0,1,0));
		        	earth.setRotate(rotationSpeed*t); 
		        	if(rotationSpeed*t / (360*angle) >= 1 ) {
			            if(sliderAnnee.getValue() < 2020) {
			        	sliderAnnee.setValue(sliderAnnee.getValue()+1);
			        	angle ++; 
			            }
			            else {
			            	timer.stop();
			            }
		        	}
		        }
	        };   
	}


	
	/**
	 * augmente la vitesse de l'animation
	 */
	public void plusVite() {
		if(rotationSpeed < SPEEDMAX-25) {
		rotationSpeed += 25; 
		multipleSpeed += 0.25; 
		speedForProp.setValue(multipleSpeed + 0.25);
		speedBackProp.setValue(multipleSpeed - 0.25);
		angle = angle + (25*angle)/rotationSpeed;
		}

	}
	
	/**
	 * diminue la vitesse de l'animation
	 */
	public void moinsVite() {
		if(rotationSpeed > SPEEDMIN+25) {
		rotationSpeed -= 25; 
		multipleSpeed -= 0.25; 
		speedForProp.setValue(multipleSpeed + 0.25);
		speedBackProp.setValue(multipleSpeed - 0.25);
		}
	}
	
	/**
	 * reinitialise les paramètres de l'animation (vitesse)
	 */
	public void reset() {
		multipleSpeed = 1;
		rotationSpeed = INITIAL_SPEED; 
		speedForProp.setValue(multipleSpeed + 0.25);
		speedBackProp.setValue(multipleSpeed - 0.25);
	}
	
	
	/**
	 * retourne la Property de la valeur d'augmentation de vitesse possible 
	 * @return speeedForProp
	 */
	public FloatProperty getSpeedForProp() {
		return speedForProp; 
	}
	
	/**
	 * retourne la Property de la valeur de diminution de vitesse possible 
	 * @return speeedBackProp
	 */
	public FloatProperty getSpeedBackProp() {
		return speedBackProp; 
	}

	/**
	 * retourne le timer de l'animation
	 * @return timer
	 */
	public AnimationTimer getTimer() {
		return timer; 
	}
	
	/**
	 * retourne vrai si l'animation est en marche, false sinon 
	 * @return boolean
	 */
	public boolean getPlay() {
		return play; 
	}
	
	/**
	 * met en marche l'animation 
	 * @param button
	 * 				 Button sur lequel on appui pour mettre en marche l'animation 
	 */
	public void setPlay(Button button) {
		timer.start();
		play = true;
		// on change l'image du bouton en icone pause 
		button.setGraphic(new ImageView(imagePause));
	}
	
	/**
	 * met l'animation en pause 
	 * @param button 
	 * 				Button sur lequel on appui pour mettre en pause l'animation 
	 */
	public void setPause(Button button) {
		timer.stop();
		play = false; 
		// on change l'imageView du bouton en image play 
		button.setGraphic(new ImageView(imagePlay));
	}
	

}
