package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;

public class Decor extends ObjetJeu {
    public Decor(double x) {
        this.x = x;
        this.w = 80;
        this.h = 119;
        this.y = Main.HAUTEUR - h + 10;
        this.image = new Image(Hasard.choisirDecorHasard(), w, h, true, false);
    }
}