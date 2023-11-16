package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.GenerateurAleatoire.obtenirNombreAleatoire;

public class PoissonEnnemi extends ObjetJeu {
    private int numNiveau;
    private static final int HAUTEUR_MAX_IMAGE= 120;
    private static final int HAUTEUR_MIN_IMAGE=50;

    public PoissonEnnemi(int numNiveau) {
        this.numNiveau = numNiveau;
        initialiserVariables();

    }

    @Override
    protected void initialiserVariables() {
        x = Main.LARGEUR - 100; //TODO: Test value
        y = obtenirHauteurDepart();
        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = obtenirNombreAleatoire(-100, 100);
        ax = -500;


        //TODO: fix this mess, coz the image isnt changing size (adding a random size to the image of each fish)
        var imageViewPoisson = new ImageView(Assets.choisirPoissonHasard());
        imageViewPoisson.setFitHeight(GenerateurAleatoire.generateurAleatoire.nextDouble(HAUTEUR_MAX_IMAGE-HAUTEUR_MIN_IMAGE)+HAUTEUR_MIN_IMAGE);
        imageViewPoisson.setPreserveRatio(true);


        image = imageViewPoisson.getImage();
        w= image.getWidth();
        h= image.getHeight();

    }


    private double obtenirHauteurDepart() {
        //Je divise la hauteur de l'écran par 5 pour obtenir des 5ème sans trouble de doubles
        int min = (Main.HAUTEUR/5);
        int max= (Main.HAUTEUR/5)*4;

        return obtenirNombreAleatoire(min, max);
    }
}
