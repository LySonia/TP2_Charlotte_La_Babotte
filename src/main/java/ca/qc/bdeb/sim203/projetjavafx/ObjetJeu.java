package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;

public abstract class ObjetJeu {
    //NOTE IMPORTANTE: LE X ET LE Y D'UN OBJET DE JEU DEVRAIT ÊTRE LE COIN EN HAUT À GAUCHE DU RECTANGLE
    protected double x = 0, y = 0;

    protected double w = 0, h = 0;
    protected double vx = 0, vy = 0;
    protected double ax = 0, ay = 0;
    protected double vitesseMax = 0;

    protected Image image;

    /**
     * Mettre à jour l'objet
     *
     * @param deltaTemps différence de temps
     */
    public void mettreAJour(double deltaTemps) {
        mettreAJourPhysique(deltaTemps);
    }

    /**
     * Mettre à jour la physique de l'objet
     *
     * @param deltaTemps différence de temps
     */
    public void mettreAJourPhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        vx = assurerQueVitesseDansLesBornes(vx);
        vy = assurerQueVitesseDansLesBornes(vy);

        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }

    /**
     * Assurer que la vitesse est dans les bornes
     *
     * @param vitesse la vitesse à vérifier
     * @return la vitesse ajustée si elle est en dehors des bornes, la vitesse donnée en argument sinon
     */
    protected double assurerQueVitesseDansLesBornes(double vitesse) {
        if (vitesse > vitesseMax) {
            vitesse = vitesseMax;
        } else if (vitesse < -vitesseMax) {
            vitesse = -vitesseMax;
        }
        return vitesse;
    }

    //TOUT EN LIEN AVEC LE DESSIN:

    /**
     * Dessiner l'objet
     *
     * @param contexte le GraphicsContext sur lequel on dessine
     */
    public void dessiner(GraphicsContext contexte) {
        contexte.drawImage(image, Camera.getCamera().calculerXEcran(x), Camera.getCamera().calculerYEcran(y));
    }

    /**
     * Mettre un contour autour de l'objet
     *
     * @param contexte le GraphicsContext sur lequel on dessine
     */
    protected void mettreContour(GraphicsContext contexte) {
        contexte.setLineWidth(1);
        contexte.setStroke(Color.YELLOW);
        contexte.strokeRect(Camera.getCamera().calculerXEcran(x), Camera.getCamera().calculerYEcran(y), w, h);
    }

    //GETTERS :
    protected double getXGauche() {
        return this.x;
    }

    protected double getXDroite() {
        return this.x + this.w;
    }

    protected double getYHaut() {
        return this.y;
    }

    protected double getYBas() {
        return this.y + this.h;
    }

    public double getVx() {
        return vx;
    }

    public void setX(double x) {this.x = x;}
}
