package ca.qc.bdeb.sim203.projetjavafx;

public class Camera {
    private static Camera camera = null;
    private double positionX;
    private double positionY;
    private double vitesseX = 500;

    private Camera(){
        positionX =0;
        positionY=0;
    }

    //On ne crée qu'un seul objet Caméra
    public static Camera getCamera(){
        if(camera == null){
            camera = new Camera();
        }
        return camera;
    }

    //Pour savoir où dessiner les objets
    public double calculerXEcran(double x){
        return x - positionX;
    }
    public double calculerYEcran(double y){
        return y - positionY;
    }

}
