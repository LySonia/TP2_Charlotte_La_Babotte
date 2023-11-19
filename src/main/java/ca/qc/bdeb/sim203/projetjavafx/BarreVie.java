package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.paint.*;

public class BarreVie extends ObjetJeu {
    private Charlotte charlotte;
    private final double largeurTotal = 150.0;

    public BarreVie(Charlotte charlotte) {
        this.charlotte = charlotte;
        w = largeurTotal;

        //Choisi de façon arbitraire:
        x = 2;
        y = 2;
        h = 25;
    }

    @Override
    public void update(double deltaTemps) {
        w = (charlotte.getNbrVie() / charlotte.getNbrVieMax()) * largeurTotal;
    }

    @Override
    public void dessiner(GraphicsContext contexte) {
        contexte.setStroke(Color.WHITE);
        contexte.setFill(Color.WHITE);
        contexte.setLineWidth(1); //TODO: Transformer en constante
        contexte.strokeRect(x, y, largeurTotal, h);
        contexte.fillRect(x, y, w, h);
    }
}
