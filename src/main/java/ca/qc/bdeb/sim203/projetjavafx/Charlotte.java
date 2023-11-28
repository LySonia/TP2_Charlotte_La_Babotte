package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

import java.util.*;

public class Charlotte extends ObjetJeu {
    //Attributs constantes :
    private final static double W_CHARLOTTE = 102;
    private final static double H_CHARLOTTE = 90;
    private final static double ACCELERATION_X = 1000;
    private final static double ACCELERATION_Y = 1000;
    private final static double NBR_VIE_MAX = 4.0;

    //Attributs de temps:
    private final static int TEMPS_IMMORTALITE = 2;

    //Attributs boolean :
    private boolean estImmortel = false;
    private boolean estEndommagee = false;
    private boolean estVisible = true;

    //Attributs autres :
    protected double xCentre = 0, yCentre = 0;
    private double nbrVie = 4;
    private double momentDommage = 0;
    private double momentDernierClignotement = 0;
    private Assets typeProjectileActuel = Assets.ETOILE; //Par défault, le projectile est une étoile


    public Charlotte() {
        image = new Image(Assets.CHARLOTTE.getEmplacement());
        y = Main.HAUTEUR/2;
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
        vitesseMax = 300;
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

        xCentre = x + (w/2);
        yCentre = y + (h/2);

        //region -- MOUVEMENT --
        if (gauche)
            ax = -ACCELERATION_X;
        else if (droite)
            ax = ACCELERATION_X;
        else {
            ax = 0;
            int signeVitesse = trouverSigneVitesse(vx);
            vx += deltaTemps * (-signeVitesse) * ACCELERATION_X;
            int nouveauSigneVitesse = trouverSigneVitesse(vx);
            if (nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }
        }

        if (haut)
            ay = -ACCELERATION_Y;
        else if (bas)
            ay = ACCELERATION_Y;
        else {
            // Code inspiré des NDC "Animations 5"
            ay = 0;
            int signeVitesse = trouverSigneVitesse(vy);
            vy += deltaTemps * (-signeVitesse) * ACCELERATION_Y;
            int nouveauSigneVitesse = trouverSigneVitesse(vy);
            if (nouveauSigneVitesse != signeVitesse) {
                vy = 0;
            }
        }
        //endregion
        super.mettreAJourPhysique(deltaTemps);
        assurerQueResteDansEcran();
    }



    public void prendreDommage() {
        if (!estImmortel) {
            nbrVie--;
        }
        estEndommagee = true;
    }

    public void gererImmortalite(double tempsActuel) {
        if (!estImmortel && estEndommagee) {
            momentDommage = tempsActuel;
            estImmortel = true;
            momentDernierClignotement = tempsActuel;
        } else if ((tempsActuel - momentDommage) > TEMPS_IMMORTALITE) {
            estImmortel = false;
            estEndommagee = false;
        }
        gererVisibilite(tempsActuel);
    }

    //Va set le boolean estVisible à la bonne valeur
    private void gererVisibilite (double tempsActuel) {
        if (estEndommagee) {
            if (tempsActuel - momentDernierClignotement > 0.25) {
                estVisible = !estVisible;
                momentDernierClignotement = tempsActuel;
            }
        } else {
            estVisible = true;
        }
    }

    private int trouverSigneVitesse(double vitesse) {
        if (vitesse > 1) {
            return 1;
        }
        return -1;
    }

    private void assurerQueResteDansEcran() {
        x = Math.max(Camera.getCamera().getXCamera(), x);
        x = Math.min(x, (Main.LARGEUR_MONDE - w));
        y = Math.max(0, y);
        y = Math.min(y, (Main.HAUTEUR - h));
    }



    //TOUT CE QUI EST "DESSIN" :
    @Override
    public void dessiner(GraphicsContext contexte) {
        gererImageCharlotte();
        super.dessiner(contexte);
    }

    public void gererImageCharlotte() {
        if (estVisible) {
            if (estEndommagee) {
                image = new Image(Assets.CHARLOTTE_OUTCH.getEmplacement());
            } else {
                image = new Image(Assets.CHARLOTTE_AVANT.getEmplacement());
            }
        } else {
            image = null;
        }
    }


    //SETTERS :
    public void donnerMaxVie(){
        nbrVie = NBR_VIE_MAX;
    }
    public void setTypeProjectileActuel(Assets typeProjectileActuel) {
        this.typeProjectileActuel = typeProjectileActuel;
    }

    //GETTERS :
    public boolean estVivante() {
        return nbrVie > 0;
    }
    public double getNbrVie(){
        return nbrVie;
    }
    public double getNbrVieMax() {
        return NBR_VIE_MAX;
    }
    public Assets getTypeProjectileActuel() {
        return typeProjectileActuel;
    }
}
