package ca.qc.bdeb.sim203.projetjavafx;

public class CameraObjJeu extends ObjetJeu {
    private final double VITESSE_X_CAMERA = 50;
    private double xEcran = 0;
    private double yEcran = 0;
    private boolean estALaFin = false;
    private Charlotte charlotte;

    public CameraObjJeu(Charlotte charlotte) {
        this.charlotte = charlotte;
        vx = VITESSE_X_CAMERA;
    }

    @Override
    public void mettreAJour(double deltaTemps) {
        verifierPositionCamera();
        ajusterVitesse();
        super.mettreAJour(deltaTemps);
    }

    //Ajuste la vitesse de la caméra selon la position de celle-ci
    private void ajusterVitesse() {
        if (!estALaFin) { //Si Charlotte n'est pas rendu à la fin du monde :
            double xEcranCharlotte = calculerXEcran(charlotte.getXDroite());

            //Si le côté droit de Charlotte dépasse ou est égale à 1/5 de l'écran :
            if (xEcranCharlotte >= Main.LARGEUR_ECRAN / 5.0) {
                vx = charlotte.getVx();
            } else { //Sinon :
                vx = 0;
            }
        } else { //Si Charlotte est rendue à la fin du monde :
            vx = 0;
        }
    }

    private void verifierPositionCamera() {
        if ((x + Main.LARGEUR_ECRAN) >= Main.LARGEUR_MONDE) {
            estALaFin = true;
        }
    }

    //Pour savoir où dessiner les objets
    public double calculerXEcran(double xMonde) {
        xEcran = xMonde - x;
        return xEcran;
    }

    public double calculerYEcran(double yMonde) {
        yEcran = yMonde - y;
        return yEcran;
    }



}
