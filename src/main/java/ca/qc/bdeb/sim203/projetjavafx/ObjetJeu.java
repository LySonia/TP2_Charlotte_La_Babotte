package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.layout.*;

public class ObjetJeu {
    protected double x, y;
    //NOTE IMPORTANTE: LE X ET LE Y D'UN OBJET DE JEU DEVRAIT ÊTRE LE COIN EN HAUT À GAUCHE DU RECTANGLE

    protected double w, h;

    protected double vx, vy;

    protected double ax, ay;

    private VBox vboxTailleObjet = new VBox();

    public ObjetJeu() {
        vboxTailleObjet.setMaxWidth(w);
        vboxTailleObjet.setMaxHeight(h);

    }

    public void mettreContour() {
        vboxTailleObjet.setStyle("-fx-border-color: blue;\n" + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 3;\n" + "-fx-border-style: dashed;\n");
    }
}
