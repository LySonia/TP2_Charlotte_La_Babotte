package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.GenerateurAleatoire.obtenirNombreAleatoire;

public class PoissonEnnemi extends ObjetJeu {
    int numNiveau;

    public PoissonEnnemi(int numNiveau) {
        this.numNiveau = numNiveau;
        initialiserVariables();
        //TODO: Hauteur aleatoire, besoin imageView
    }

    @Override
    protected void initialiserVariables() {
        x = Main.LARGEUR - 100; //TODO: Test value
        y = obtenirHauteurDepart();
        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = obtenirNombreAleatoire(-100, 100);
        ax = -500;
        image = new Image(Assets.POISSON_1.choisirPoissonHasard());
        w= image.getWidth();
        h= image.getHeight();
    }


    private double obtenirHauteurDepart() {
        int min = (int)((1.0/5.0) * Main.HAUTEUR); //TODO: Sketchy solution?
        int max = (int)((4.0/5.0) * Main.HAUTEUR);
        return obtenirNombreAleatoire(min, max);
    }
}
