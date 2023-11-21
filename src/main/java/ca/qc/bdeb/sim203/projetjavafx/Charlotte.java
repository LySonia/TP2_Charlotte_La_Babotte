package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;
    private final double ACCELERATION_X = 1000;
    private final double ACCELERATION_Y = 1000;
    private int nbrVie = 4;
    private final double NBR_VIE_MAX = 4.0;
    private final int TEMPS_IMMORTALITE = 2;
    private double momentDommage = 0;
    private double momentDernierClignotement = 0;
    private boolean estImmortel = false;

    private boolean estEndommagee = false;
    boolean estVisible = true;
    private Projectile projectileActuel;

    public Charlotte() {
        x = 0;
        y = 0;
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
        vitesseMax = 300;
        image = new Image(Assets.CHARLOTTE.getEmplacement()); //TODO: Trop de mélange MVC?
    }

    public void update(double deltaTemps) {
        super.update(deltaTemps);
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

        //TODO: Faire une méthode pour mouvement horizontale et verticale!
        //region -- MOUVEMENT HORIZONTAL --
        if (gauche)
            ax = -ACCELERATION_X;
        else if (droite)
            ax = ACCELERATION_X;
        else {
            // Code inspiré des NDC "Animations 5"
            ax = 0;

            int signeVitesse = trouverSigneVitesse(vx);

            vx += deltaTemps * (-signeVitesse) * ACCELERATION_X;

            int nouveauSigneVitesse = trouverSigneVitesse(vx);

            if (nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }
        }
        //endregion

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
    }

    //TODO: Fix this
    private double gererAjustementAcceleration(boolean boutonNegatif, boolean boutonPositif, double acceleration) {
        if (boutonNegatif) {
            return -acceleration;
        } else if (boutonPositif) {
            return  acceleration;
        } else {
            return 0;
        }
    }

    public void gererDommage() {
        image = new Image(Assets.CHARLOTTE_OUTCH.getEmplacement());
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
            faireClignoter(tempsActuel);
        } else if ((tempsActuel - momentDommage) > TEMPS_IMMORTALITE){
            estImmortel = false;
            estEndommagee = false;
            image = new Image(Assets.CHARLOTTE.getEmplacement());
        } else if (estImmortel) {
            faireClignoter(tempsActuel);
        } //Ya un cas que tu ignores il semble
    }

    private void faireClignoter(double tempsActuel) {
        if (tempsActuel - momentDernierClignotement > 0.25) {
            System.out.println();
            estVisible = !estVisible;
            momentDernierClignotement = tempsActuel;
        }

        if (estVisible) {
            image = new Image(Assets.CHARLOTTE_OUTCH.getEmplacement());
        } else {
            image = new Image(Assets.CHARLOTTE_OUTCH_TRANSPARENT.getEmplacement());
        }
    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        super.mettreAJourPhysique(deltaTemps);
        assurerQueResteDansEcran();
    }

    private int trouverSigneVitesse(double vitesse) {
        if (vitesse > 1) {
            return 1;
        }
        return -1;
    }

    private void assurerQueResteDansEcran() {
        x = Math.max(0, x);
        x = Math.min(x, (Main.LARGEUR - w));
        y = Math.max(0, y);
        y = Math.min(y, (Main.HAUTEUR - h));
    }

    public int getNbrVie(){
        return nbrVie;
    }

    public double getNbrVieMax() {
        return NBR_VIE_MAX;
    }

    public boolean estEndommagee() {
        return estEndommagee;
    }
    public boolean estImmortel() {
        return estImmortel;
    }

    public void setEstImmortel(boolean estImmortel) {
        this.estImmortel = estImmortel;
    }

    public void setImage(String emplacement) {
        image = new Image(emplacement);
    }
}
