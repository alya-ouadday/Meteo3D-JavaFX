package projet.vues;

import javafx.geometry.Point3D;

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

}
