package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public abstract class ObjetJeu {
    protected double x, y;
    //NOTE IMPORTANTE: LE X ET LE Y D'UN OBJET DE JEU DEVRAIT ÊTRE LE COIN EN HAUT À GAUCHE DU RECTANGLE
    private double xCentre, yCentre;
    protected double w, h;
    protected double vx, vy;
    protected double ax, ay;
    protected double VITESSE_MAX = 300; //TODO: Valeur de défaut déterminé par moi (Sonia)- on a whim, stai pour éviter un bug. Besoin meilleure solution.
    protected Image image;

    public ObjetJeu() {
        xCentre = x + (w/2);
        yCentre = y + (h/2);
    }

    public void mettreAJourPhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        vx = assurerQueVitesseDansLesBornes(vx);
        vy = assurerQueVitesseDansLesBornes(vy);

        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }

    private double assurerQueVitesseDansLesBornes(double vitesse) {
        if(vitesse > VITESSE_MAX) {
            vitesse = VITESSE_MAX;
        } else if(vitesse < -VITESSE_MAX) {
            vitesse = -VITESSE_MAX;
        }
        return vitesse;
    }

    public void mettreContour(GraphicsContext contexte) {
        contexte.setLineWidth(1); //TODO: Transformer en constante
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(x, y, w, h);
    }

    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, x, y);

    }
}
