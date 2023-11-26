package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

import java.util.ArrayList;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;
    private final double ACCELERATION_X = 1000;
    private final double ACCELERATION_Y = 1000;
    private final static double NBR_VIE_MAX = 4.0;
    private final static int TEMPS_IMMORTALITE = 2;
    private double nbrVie = 4;
    private double momentDommage = 0;
    private double momentDernierClignotement = 0;
    private boolean estImmortel = false;
    private boolean estEnMouvement = false;
    private boolean estEndommagee = false;
    private boolean estVisible = true;

    //partie sur les projectiles
    private TypesProjectiles typeProjectileActuel;
    private double tempsDernierProjectile =0;

    private static final double DELAIS_DE_TIR = 0.5;

    public Charlotte() {
        y = Main.HAUTEUR/2;
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
        vitesseMax = 300; //TODO: Constante?
        image = new Image(Assets.CHARLOTTE.getEmplacement()); //TODO: Trop de mélange MVC?
        typeProjectileActuel = TypesProjectiles.ETOILES; //TODO: dirty fix (it's the initial one)

    }

    @Override
    public void mettreAJourPhysique(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

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
        assurerQueResteDansEcran();

        super.mettreAJourPhysique(deltaTemps);
    }

    @Override
    public void dessiner(GraphicsContext contexte) {
        gererImageCharlotte();
        super.dessiner(contexte);
    }

    public void prendreDommage() {
        if (!estImmortel) {
            nbrVie--;
        }
        estEndommagee = true;
    }

    public void utiliserProjectile(double tempsActuel){
        if(tempsActuel - tempsDernierProjectile > DELAIS_DE_TIR) {
            tempsDernierProjectile = tempsActuel;

//            double yCentre = y+(h/2) - projectileActuel.getH()/2;
//            double xCentre = x+(w/2) - projectileActuel.getW()/2;
//            projectileActuel.setYDeCentreCharlotte(yCentre);
//            projectileActuel.setEstTirer(true);
//
//            //pour que le projectile sorte du centre de charlotte quand on les dessine
//            projectileActuel.setX(xCentre);
//            projectileActuel.setY(yCentre);
        }
    }

    public void gererImageCharlotte() { //Code dans la bonne classe?
        if (estVisible) {
            if (estEndommagee) {
                image = new Image(Assets.CHARLOTTE_OUTCH.getEmplacement());
            } else {
                image = new Image(Assets.CHARLOTTE_AVANT.getEmplacement());
            }
        } else {
            image = new Image(Assets.CHARLOTTE_OUTCH_TRANSPARENT.getEmplacement());
        }
    }

    public void gererImmortalite(double tempsActuel) {
        //TODO: Est-ce qu'il y a un cas que j'ai oublié de prendre en compte?
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

    private void assurerQueResteDansEcran() { // trop de mélange MVC?
        x = Math.max(Camera.getCamera().getXCamera(), x); //position en X de la cam est la limite
        x = Math.min(x, (Main.LARGEUR_MONDE - w));
        y = Math.max(0, y);
        y = Math.min(y, (Main.HAUTEUR - h));
    }

    public double getNbrVie(){
        return nbrVie;
    }

    public void donnerMaxVie(){
        nbrVie = NBR_VIE_MAX;
    }

    public double getNbrVieMax() {
        return NBR_VIE_MAX;
    }

    public void setImage(String emplacement) {
        image = new Image(emplacement);
    }

    public TypesProjectiles getTypeProjectileActuel() {
        return typeProjectileActuel;
    }

    public void setTypeProjectile(TypesProjectiles typeProjectile) {
        this.typeProjectileActuel = typeProjectile;
    }

    //GETTERS
    public boolean estVivante() {
        return nbrVie > 0;
    }
}
