package ca.qc.bdeb.sim203.projetjavafx;

public class Camera {
    private static Camera camera = null;
    private double xCamera;
    private double yCamera;
    private double vxCamera = 0;
    private boolean estALaFin = false;

    /**
     * Constructeur de la classe Caméra (Singleton--> on ne peut faire qu'un seul objet Caméra)
     */
    private Camera() {
        xCamera = 0;
        yCamera = 0;
    }

    //On ne crée qu'un seul objet Caméra

    /**
     * Méthode qui crée l'objet Caméra une fois, puis la return à chaque fois
     *
     * @return l'objet Caméra crée
     */
    public static Camera getCamera() {
        if (camera == null) {
            camera = new Camera();
        }
        return camera;
    }

    /**
     * Méthode qui replace la caméra à zéro lors d'un nouveau niveau
     */
    public void reinitialiserCamera() {
        xCamera = 0;
        yCamera = 0;
        estALaFin = false;
    }

    /**
     * Méthode qui update la position de la caméra en changeant sa vitesse selon celle de Charlotte pour qu'elle reste
     * bien positionnée dans l'écran
     *
     * @param charlotte  Pour utiliser sa position et sa vitesse
     * @param deltaTemps différence de temps pour trouver la position
     */
    public void update(Charlotte charlotte, double deltaTemps) {
        verifierPositionCamera();
        if (!estALaFin) {
            //charlotte est plus grande que 1/5 de l'écran
            if (calculerXEcran(charlotte.getXDroite()) >= Main.LARGEUR_ECRAN / 5) {
                vxCamera = charlotte.getVx();
            } else {
                vxCamera = 0;
            }
        } else {
            vxCamera = 0;
        }

        xCamera += deltaTemps * vxCamera;
    }

    /**
     * Méthode qui vérifie la position de la caméra pour savoir si elle se trouve à la fin du niveau
     */
    private void verifierPositionCamera() {
        if (xCamera + Main.LARGEUR_ECRAN >= Main.LARGEUR_MONDE) {
            estALaFin = true;
        }
    }

    /**
     * Pour savoir où dessiner les objets
     *
     * @param x le x d'un objet dans le monde
     * @return le x d'un objet sur l'écran
     */
    public double calculerXEcran(double x) {
        return x - xCamera;
    }

    /**
     * Pour savoir où dessiner les objets dans l'écran
     *
     * @param y le y de l'objet dans le monde
     * @return le y sur l'écran
     * Dans ce TP, la position y écran et y monde est la même, nous avons tout de même inclus cette méthode dans le cas
     * où on souhaiterait agrandir le jeu vers le haut
     */
    public double calculerYEcran(double y) {
        return y - yCamera;
    }

    public double getXCamera() {
        return xCamera;
    }

    public double getYCamera() {
        return yCamera;
    }

}
