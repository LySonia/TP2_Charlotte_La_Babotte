package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;

    //TODO: Pour les deux accélérations: devrait être 1000, mais quand c'est 1000, Charlotte va trop rapidement.
    private final double ACCELERATION_X = 1000;
    private final double ACCELERATION_Y = 1000;

    //TODO: La vitesse devrait être de 300, mais quand c'est 300, Charlotte va trop rapidement
    private final double VITESSE_MAX = 300;

    private Image image = new Image("charlotte.png");

    public Charlotte() {
        w = W_CHARLOTTE;
        h = H_CHARLOTTE;
        x = 0;
        y = 0;
    }

    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(KeyCode.LEFT);
        boolean haut = Input.isKeyPressed(KeyCode.UP);
        boolean droite = Input.isKeyPressed(KeyCode.RIGHT);
        boolean bas = Input.isKeyPressed(KeyCode.DOWN);

        //region -- MOUVEMENT HORIZONTAL --
        if (gauche)
            ax = -ACCELERATION_X;
        else if (droite)
            ax = ACCELERATION_X;
        else {
            //TODO: à rendre plus smooth (ax *= -1)
            ax = 0;
            vx = 0;


        }
        //endregion

        //region -- MOUVEMENT VERTICAL --
        if (haut)
            ay = -ACCELERATION_Y;
        else if (bas)
            ay = ACCELERATION_Y;
        else {
            //ay *= -1;
            ay = 0;
            vy = 0;
        }
        //endregion

        mettreAJourPhysique(deltaTemps);
    }

    private void mettreAJourPhysique(double deltaTemps) {
        //NOTE: Math.min() choisit la plus petite valeur entre les 2 valeurs
        //Math.max() choisit la plus grande valeur entre les 2 valeurs
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        vx = Math.min(vx, VITESSE_MAX);
        vy = Math.min(vy, VITESSE_MAX); //TODO: Valeur abs

        x += deltaTemps * vx;
        y += deltaTemps * vy;
        //Pour pas que sort de l'écran
        x = Math.max(0, x);
        x = Math.min(x, (Main.WIDTH + w));
        y = Math.max(0, y);
        y = Math.min(y, (Main.HEIGHT - h));
    }

    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y);
    }


}
