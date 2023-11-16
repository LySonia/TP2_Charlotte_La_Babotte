package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;
    private final double ACCELERATION_X = 1000;
    private final double ACCELERATION_Y = 1000;
    private Projectile projectileActuel;

    public Charlotte() {
        initialiserVariables();
    }

    @Override
    protected void initialiserVariables() {
        image = new Image(Assets.CHARLOTTE.getEmplacement());
        VITESSE_MAX = 300;
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
        x = 0;
        y = 0;
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
            ax -= ACCELERATION_X;
        else if (droite)
            ax += ACCELERATION_X;
        else {
            // Code inspiré des NDC "Animations 5"
            ax = 0;

            int signeVitesse = trouverSigneVitesse(vx);

            //On veut que le poisson accélère dans le sens opposé de la vitesse
            vx += deltaTemps * (-signeVitesse) * ACCELERATION_X;

            int nouveauSigneVitesse = trouverSigneVitesse(vx);

            //Si le nouveau signe de la vitesse et l'ancienne sont différentes, alors on a est passé par vx = 0
            //Par exemple, si notre vitesse initiale est de -5
            //et que notre nouvelle vitesse des de +0.0003
            //alors on peut considérer que la vitesse a atteint 0
            if (nouveauSigneVitesse != signeVitesse) {
                vx = 0;
            }

        }
        //endregion

        //region -- MOUVEMENT VERTICAL --
        if (haut)
            ay += -ACCELERATION_Y;  //TODO: ajouté +=
        else if (bas)
            ay += ACCELERATION_Y;
        else {
            ay = 0;
            int signeVitesse = trouverSigneVitesse(vy);

            vy += deltaTemps * (-signeVitesse) * ACCELERATION_Y;

            int nouveauSigneVitesse = trouverSigneVitesse(vy);

            if (nouveauSigneVitesse != signeVitesse) {
                vy = 0;
            }
        }
        //endregion
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


}
