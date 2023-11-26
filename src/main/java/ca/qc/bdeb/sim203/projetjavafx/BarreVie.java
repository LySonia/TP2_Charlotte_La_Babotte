package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.paint.*;

public class BarreVie extends ObjetJeu {
    private Charlotte charlotte;
    private final double LARGEUR_TOTALE_BARRE = 150.0;

    public BarreVie(Charlotte charlotte) {
        this.charlotte = charlotte;
        w = LARGEUR_TOTALE_BARRE;

        //Choisi de façon arbitraire:
        x = 15;
        y = 15;
        h = 25;
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        w = (charlotte.getNbrVie() / charlotte.getNbrVieMax()) * LARGEUR_TOTALE_BARRE;
    }

    @Override
    public void dessiner(GraphicsContext contexte) {
        contexte.setStroke(Color.WHITE);
        contexte.setFill(Color.WHITE);
        contexte.setLineWidth(1); //TODO: Transformer en constante
        contexte.strokeRect(x, y, LARGEUR_TOTALE_BARRE, h);
        contexte.fillRect(x, y, w, h);
    }
}
