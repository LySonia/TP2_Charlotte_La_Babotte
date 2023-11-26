package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.Hasard.obtenirNombreAleatoire;

public class PoissonEnnemi extends ObjetJeu {
    private int numNiveau;
    private static final int HAUTEUR_MAX_IMAGE= 120;
    private static final int HAUTEUR_MIN_IMAGE = 50;

    private static final int CHARGE_Q = -100;



    public PoissonEnnemi(int numNiveau) {
        this.numNiveau = numNiveau;

        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = obtenirNombreAleatoire(-100, 100);
        ax = -500;

        h = obtenirNombreAleatoire(HAUTEUR_MIN_IMAGE, HAUTEUR_MAX_IMAGE);
        image = new Image(Hasard.choisirPoissonHasard(), 0, h, true, false);
        w = image.getWidth();

        x = Camera.getCamera().getXCamera() + w + Main.LARGEUR_ECRAN;
        y = obtenirHauteurDepart();
    }

    private double obtenirHauteurDepart() {
        //TODO: Revérifier calcul fraction
        int min = (Main.HAUTEUR/5);
        int max = (Main.HAUTEUR/5)*4;

        return obtenirNombreAleatoire(min, max);
    }

    public boolean estDansEcran() {
        boolean estDansEcran = false;

        if ((getXGauche() > Camera.getCamera().getXCamera()) &&
                (getYHaut() < Main.HAUTEUR) &&
                (getYBas() > Camera.getCamera().getYCamera())) {

            estDansEcran = true;
        }
        return estDansEcran;
    }
}
