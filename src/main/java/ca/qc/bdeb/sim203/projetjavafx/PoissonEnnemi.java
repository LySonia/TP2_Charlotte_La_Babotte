package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextDouble;
import static ca.qc.bdeb.sim203.projetjavafx.Hasard.nextInt;

public class PoissonEnnemi extends ObjetJeu {
    private int numNiveau;
    private static final int HAUTEUR_MAX_IMAGE= 120;
    private static final int HAUTEUR_MIN_IMAGE = 50;
    private static final int CHARGE_Q = -100; //TODO: Bonne idée de mettre ça static?

    public PoissonEnnemi(int numNiveau) {
        this.numNiveau = numNiveau;

        vitesseMax = 300; //TODO: Déterminé arbitrairement
        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = nextInt(-100, 100);
        ax = -500;

        h = nextDouble(HAUTEUR_MIN_IMAGE, HAUTEUR_MAX_IMAGE);
        image = new Image(Hasard.choisirPoissonHasard().getEmplacement(), 0, h, true, false);
        w = image.getWidth();

        x = Camera.getCamera().getXCamera() + w + Main.LARGEUR_ECRAN;
        y = obtenirHauteurDepart();
    }

    private double obtenirHauteurDepart() {
        //TODO: Revérifier calcul fraction
        double min = (Main.HAUTEUR/5);
        double max = (Main.HAUTEUR/5)*4;

        return nextDouble(min, max);
    }

    public boolean estDansEcran() {
        boolean estDansEcran = false;

        if ((getXDroite() > Camera.getCamera().getXCamera()) &&
                (getYHaut() < Main.HAUTEUR) &&
                (getYBas() > Camera.getCamera().getYCamera())) {

            estDansEcran = true;
        }
        return estDansEcran;
    }

    public double getChargeQ(){
        return CHARGE_Q;
    }
}
