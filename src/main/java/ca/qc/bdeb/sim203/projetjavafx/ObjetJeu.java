package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

public abstract class ObjetJeu {
    //NOTE IMPORTANTE: LE X ET LE Y D'UN OBJET DE JEU DEVRAIT ÊTRE LE COIN EN HAUT À GAUCHE DU RECTANGLE
    protected double x = 0, y = 0;
    protected double xCentre = 0, yCentre = 0;
    protected double w = 0, h = 0;
    protected double vx = 0, vy = 0;
    protected double ax = 0, ay = 0;
    protected double vitesseMax = 0;

    protected Image image;

    public void mettreAJour(double deltaTemps) {
        mettreAJourPhysique(deltaTemps);
    }
    public void mettreAJourPhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        vx = assurerQueVitesseDansLesBornes(vx);
        vy = assurerQueVitesseDansLesBornes(vy);

        x += deltaTemps * vx;
        y += deltaTemps * vy;

        //TODO: Y'a que Charlotte qui a besoin de ces valeurs, à déplacer?
        xCentre = x + (w/2);
        yCentre = y + (h/2);
    }

    private double assurerQueVitesseDansLesBornes(double vitesse) {
        if (vitesse > vitesseMax) {
            vitesse = vitesseMax;
        } else if (vitesse < -vitesseMax) {
            vitesse = -vitesseMax;
        }
        return vitesse;
    }

    public void mettreContour(GraphicsContext contexte) {
        contexte.setLineWidth(1);
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(Camera.getCamera().calculerXEcran(x), Camera.getCamera().calculerYEcran(y), w, h);
    }

    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, Camera.getCamera().calculerXEcran(x), Camera.getCamera().calculerYEcran(y));
    }

    //GETTERS :
    protected double getXGauche(){
        return this.x;
    }

    protected double getXDroite(){
        return this.x + this.w;
    }

    protected double getYHaut(){
        return this.y;
    }

    protected double getYBas(){
        return this.y + this.h;
    }

    public double getVx() {
        return vx;
    }


    //SETTERS :
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
