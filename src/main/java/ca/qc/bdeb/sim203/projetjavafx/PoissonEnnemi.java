package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.GenerateurAleatoire.obtenirNombreAleatoire;

public class PoissonEnnemi extends ObjetJeu {

    public PoissonEnnemi(int numNiveau) {
        x = Main.LARGEUR - 100; //TODO: Test value
        y = obtenirHauteurDepart();
        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = obtenirNombreAleatoire(-100, 100);
        ax = -500;
        image = new Image(Assets.POISSON_1.choisirPoissonHasard());
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(obtenirNombreAleatoire(50, 120));
        //TODO: Hauteur aleatoire, besoin imageView
    }

    private double obtenirHauteurDepart() {
        int min = (int)((1.0/5.0) * Main.HAUTEUR); //TODO: Sketchy solution?
        int max = (int)((4.0/5.0) * Main.HAUTEUR);
        return obtenirNombreAleatoire(min, max);
    }
}
