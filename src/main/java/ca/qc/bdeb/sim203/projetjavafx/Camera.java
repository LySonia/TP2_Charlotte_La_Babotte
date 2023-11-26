package ca.qc.bdeb.sim203.projetjavafx;

public class Camera {
    private static Camera camera = null;
    private double xCamera;
    private double yCamera;
    private double vxCamera = 0;
    private boolean estALaFin = false;

    private Camera() {
        xCamera = 0;
        yCamera = 0;
    }

    //On ne crée qu'un seul objet Caméra
    public static Camera getCamera() {
        if (camera == null) {
            camera = new Camera();
        }
        return camera;
    }

    public void reinitialiserCamera() {
        xCamera = 0;
        yCamera = 0; //TODO: Est-ce que le y sert à quelque chose?
        estALaFin = false;
    }

    public void update(Charlotte charlotte, double deltaTemps) {
        verifierPositionCamera();
        if (!estALaFin) {
            if (calculerXEcran(charlotte.getXDroite()) >= (double) Main.LARGEUR_ECRAN / 5) { //charlotte est plus grande que 1/5 de l'écran
                vxCamera = charlotte.getVx();
            } else {
                vxCamera = 0;
            }
        } else {
            vxCamera = 0;
        }

        xCamera += deltaTemps * vxCamera;
    }

    private void verifierPositionCamera() {
        if (xCamera + Main.LARGEUR_ECRAN >= Main.LARGEUR_MONDE) {
            estALaFin = true;
        }
    }

    //Pour savoir où dessiner les objets
    public double calculerXEcran(double x) {
        return x - xCamera;
    }

    public double calculerYEcran(double y) {
        return y - yCamera;
    }

    public double getXCamera() {
        return xCamera;
    }

    public double getYCamera(){
        return yCamera;
    }
}
