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
        xCentre = x + (w / 2);
        yCentre = y + (h / 2);
    }

    protected abstract void initialiserVariables();

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
        if (vitesse > VITESSE_MAX) {
            vitesse = VITESSE_MAX;
        } else if (vitesse < -VITESSE_MAX) {
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

    //Kinda weird car Charlotte est un child class
    public boolean estEnCollisionAvecCharlotte(Charlotte charlotte) {
        boolean estEnCollisionCoinSuperieurGauche = verifierSiCoinDansCharlotte(charlotte, getXGauche(), getYHaut());
        boolean estEnCollisionCoinSuperieurDroite = verifierSiCoinDansCharlotte(charlotte, getXDroite(), getYBas());
        boolean estEnCollisionCoinInferieurGauche = verifierSiCoinDansCharlotte(charlotte, getXGauche(), getYBas());
        boolean estEnCollisionCoinInferieurDroite = verifierSiCoinDansCharlotte(charlotte, getXDroite(), getYBas());

        boolean estEnCollision = false;
        if (estEnCollisionCoinSuperieurGauche ||
                estEnCollisionCoinSuperieurDroite ||
                estEnCollisionCoinInferieurGauche ||
                estEnCollisionCoinInferieurDroite) {
            estEnCollision = true;
        }

        return estEnCollision;
    }

    public boolean verifierSiCoinDansCharlotte(Charlotte charlotte, double xAnalyse, double yAnalyse) {
        boolean estDansCharlotte = false;

        if (((charlotte.getXGauche() < xAnalyse) && (xAnalyse < charlotte.getXDroite())) && //Coin supérieur gauche
                ((charlotte.getYHaut() < yAnalyse) && (yAnalyse < charlotte.getYBas()))) {
            estDansCharlotte = true;
        }
        return estDansCharlotte;
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
}
