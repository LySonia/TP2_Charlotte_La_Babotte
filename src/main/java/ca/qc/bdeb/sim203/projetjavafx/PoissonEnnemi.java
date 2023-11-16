package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

import static ca.qc.bdeb.sim203.projetjavafx.GenerateurAleatoire.generateurAleatoire;
import static ca.qc.bdeb.sim203.projetjavafx.GenerateurAleatoire.obtenirNombreAleatoire;

public class PoissonEnnemi extends ObjetJeu {
    private int numNiveau;
    private static final int HAUTEUR_MAX_IMAGE= 120;
    private static final int HAUTEUR_MIN_IMAGE=50;

    public PoissonEnnemi(int numNiveau) {
        this.numNiveau = numNiveau;
        initialiserVariables();
    }

    @Override //TODO: En faisant ça, tu t'assures pas nécessairement que les variables ont des valeurs: faudra mettre des arguments dans le master class
    protected void initialiserVariables() {
        x = Main.LARGEUR - 120; //TODO: Test value
        y = obtenirHauteurDepart();
        vx = -100 * Math.pow(numNiveau, 0.33) + 200;
        vy = obtenirNombreAleatoire(-100, 100);
        ax = -500;


        h = obtenirNombreAleatoire(50, 120);
        image = new Image(Assets.choisirPoissonHasard(), 0, h, true, false); //ugly code, to change
        w = image.getWidth();

    }

    private double obtenirHauteurDepart() {
        //Je divise la hauteur de l'écran par 5 pour obtenir des 5ème sans trouble de doubles
        int min = (Main.HAUTEUR/5);
        int max= (Main.HAUTEUR/5)*4;

        return obtenirNombreAleatoire(min, max);
    }
}
