package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextDouble;
import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextInt;

public class PoissonEnnemi extends ObjetJeu {
    private static final int HAUTEUR_MAX_IMAGE= 120;
    private static final int HAUTEUR_MIN_IMAGE = 50;
    public static final int CHARGE_Q = -100;

    /**
     * Construteur de la classe PoissonEnnemi
     * @param numNiveau le numéro du niveau
     */
    public PoissonEnnemi(int numNiveau) {
        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = nextInt(-100, 100);
        ax = -500;

        h = nextDouble(HAUTEUR_MIN_IMAGE, HAUTEUR_MAX_IMAGE);
        image = new Image(Hasard.choisirPoissonHasard().getEmplacement(), 0, h,
                true, false);
        w = image.getWidth();

        x = Camera.getCamera().getXCamera() + w + Main.LARGEUR_ECRAN;
        y = trouverYDepart();
    }

    /**
     * Trouver le y de départ du poisson ennemi
     * @return le y de départ du poisson ennemi
     */
    private double trouverYDepart() {
        double min = (Main.HAUTEUR/5);
        double max = (Main.HAUTEUR/5)*4;

        return nextDouble(min, max);
    }

    /**
     * Trouver si le poisson ennemi est dans l'écran
     * @return boolean qui est true si le poisson est dans l'écran
     */
   public boolean estDansEcran() {
        boolean estDansEcran = false;

        if ((getXDroite() > Camera.getCamera().getXCamera()) && //Vérifier gauche écran
                (getYHaut() < Main.HAUTEUR) && //Vérifier bas écran
                (getYBas() > Camera.getCamera().getYCamera())) { //Vérifier haut écran

            estDansEcran = true;
        }

        return estDansEcran;
    }


}
