package ca.qc.bdeb.sim203.projetjavafx;

public class Camera {
    private static Camera camera = null;
    private double positionX;
    private double positionY;
    private double vitesseX = 0;


    private boolean estALaFin = false;

    private Camera() {
        positionX = 0;
        positionY = 0;
    }

    //On ne crée qu'un seul objet Caméra
    public static Camera getCamera() {
        if (camera == null) {
            camera = new Camera();
        }
        return camera;
    }

    public void update(Charlotte charlotte, double deltaTemps) {
        verifierPositionCamera();

        if (!estALaFin) {
            if (calculerXEcran(charlotte.getXDroite()) >= (double) Main.LARGEUR_ECRAN / 5) { //charlotte est plus grande que 1/5 de l'écran
                vitesseX = charlotte.getVx();
            } else if (charlotte.getVx() == 0) {
                vitesseX = 0;
            } else if (calculerXEcran(charlotte.getXGauche()) <= positionX) {
                vitesseX = 0;

            }
        } else {
            vitesseX = 0;
        }


        positionX += deltaTemps * vitesseX;


    }

    private void verifierPositionCamera() {
        if (positionX + Main.LARGEUR_ECRAN >= Main.LARGEUR_MONDE) {
            estALaFin = true;
        }
    }

    //Pour savoir où dessiner les objets
    public double calculerXEcran(double x) {
        return x - positionX;
    }

    public double calculerYEcran(double y) {
        return y - positionY;
    }

    public double getPositionX() {
        return positionX;
    }
}
