package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

public abstract class ObjetJeu {
    protected double x = 0;

    protected double y = 0;
    //NOTE IMPORTANTE: LE X ET LE Y D'UN OBJET DE JEU DEVRAIT ÊTRE LE COIN EN HAUT À GAUCHE DU RECTANGLE
    private double xCentre = 1;
    private double yCentre = 1;
    protected double w = 0;
    protected double h = 0;
    protected double vx = 0;
    protected double vy = 0;
    protected double ax = 0;
    protected double ay = 0;
    protected double vitesseMax = 300; //TODO: Valeur de défaut déterminé par moi (Sonia)- on a whim, stai pour éviter un bug. Besoin meilleure solution.
    protected Image image;

    public ObjetJeu(){
        this.xCentre = (x+w)/2;
        this.yCentre = (y+h)/2;
    }


    public void update(double deltaTemps) {
        mettreAJourPhysique(deltaTemps);
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
        if (vitesse > vitesseMax) {
            vitesse = vitesseMax;
        } else if (vitesse < -vitesseMax) {
            vitesse = -vitesseMax;
        }
        return vitesse;
    }

    public void mettreContour(GraphicsContext contexte) {
        contexte.setLineWidth(1); //TODO: Transformer en constante
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(Camera.getCamera().calculerXEcran(x), Camera.getCamera().calculerYEcran(y), w, h);
    }

    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, Camera.getCamera().calculerXEcran(x), Camera.getCamera().calculerYEcran(y));
    }

    //Kinda weird car Charlotte est un child class
    public boolean estEnCollisionAvecCharlotte(Charlotte charlotte) {
        return Collision.aIntersection(charlotte, this);
    }

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

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

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
