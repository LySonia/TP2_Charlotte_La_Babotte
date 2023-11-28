package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.paint.*;

public class BarreVie extends ObjetJeu {
    private Charlotte charlotte;
    private static final double LARGEUR_TOTALE_BARRE = 150.0;
    private static final int LARGEUR_TRAIT = 1;

    /**
     * Constructeur de Bar de vie
     * @param charlotte Charlotte la babotte qui est liée à la barre de vie
     */
    public BarreVie(Charlotte charlotte) {
        this.charlotte = charlotte;
        w = LARGEUR_TOTALE_BARRE;

        //Choisi de façon arbitraire:
        x = 15;
        y = 15;
        h = 25;
    }

    /**
     * Override de la méthode mettreAjourPhysique de Objet jeu qui change la largeur de la barre selon le pourcentage
     * de vie de Charlotte
     * @param deltaTemps différence de temps (pas utilisé dans le override)
     */
    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        w = (charlotte.getNbrVie() / charlotte.getNbrVieMax()) * LARGEUR_TOTALE_BARRE;
    }

    /**
     * Override de la méthode mettreContour pour que le rectangle jaune du mode debug ne disparaisse pas avec le
     * mouvement de la caméra
     * @param contexte pour dessiner
     */
    @Override
    public void mettreContour(GraphicsContext contexte) {
        contexte.setLineWidth(1);
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(x, y, w, h);
    }

    /**
     * Override de la méthode Dessiner de objetJeu qui dessine le rectangle et le contour de la barre de vie
     * selon les critères
     * @param contexte pour dessiner
     */
    @Override
    public void dessiner(GraphicsContext contexte) {
        contexte.setStroke(Color.WHITE);
        contexte.setFill(Color.WHITE);
        contexte.setLineWidth(LARGEUR_TRAIT);
        contexte.strokeRect(x, y, LARGEUR_TOTALE_BARRE, h);
        contexte.fillRect(x, y, w, h);
    }
}
