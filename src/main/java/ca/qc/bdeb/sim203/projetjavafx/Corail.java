package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;

public class Corail extends ObjetJeu {
    public Corail(double x) {
        this.x = x;
        this.w = 80;
        this.h = 119;
        this.y = Main.HAUTEUR - h + 10;
        this.image = new Image(Assets.choisirAlgueHasard(), w, h, true, false);
    }

}
