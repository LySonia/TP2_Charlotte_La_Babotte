package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;

public class Decor extends ObjetJeu {
    /**
     * Constructeur de la classe décor
     * @param x la position initiale de chaque décor dans le monde
     */
    public Decor(double x) {
        this.x = x;
        this.w = 80;
        this.h = 119;
        this.y = Main.HAUTEUR - h + 10;
        this.image = new Image(Hasard.choisirDecorHasard().getEmplacement(), w, h, true, false);
    }
}