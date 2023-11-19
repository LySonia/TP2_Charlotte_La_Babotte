package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.Aleatoire.obtenirNombreAleatoire;

public class PoissonEnnemi extends ObjetJeu {
    private int numNiveau;
    private static final int HAUTEUR_MAX_IMAGE= 120;
    private static final int HAUTEUR_MIN_IMAGE=50;

    private boolean estDansEcran = false;

    public PoissonEnnemi(int numNiveau) {
        this.numNiveau = numNiveau;

        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = obtenirNombreAleatoire(-100, 100);
        ax = -500;


        h = obtenirNombreAleatoire(50, 120);
        image = new Image(Assets.choisirPoissonHasard(), 0, h, true, false); //ugly code, to change
        w = image.getWidth();

        x = Main.LARGEUR - w; //TODO: Test value
        y = obtenirHauteurDepart();
    }


    private double obtenirHauteurDepart() {
        //TODO: Revérifier
        int min = (Main.HAUTEUR/5);
        int max = (Main.HAUTEUR/5)*4;

        return obtenirNombreAleatoire(min, max);
    }

    public boolean estDansEcran(double xEcran, double yEcran, double wEcran, double hEcran) {
        boolean estDansEcran = false;

    }

}
