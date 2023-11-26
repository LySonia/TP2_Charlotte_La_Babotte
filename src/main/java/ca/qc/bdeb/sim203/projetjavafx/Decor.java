package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;

public class Decor {
    private double w = 80;
    private double h = 119;
    private double x;
    private double y = Main.HAUTEUR - h + 10;

    private Image image = new Image(Hasard.choisirDecorHasard(), w, h, true, false);

    public Decor(double x) {
        this.x = x;
    }

    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, x, y, w, h);
    }

    public double getXDroite() {
        return x + h;
    }

}
