package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;

    //TODO: Pour les deux accélérations: devrait être 1000, mais quand c'est 1000, Charlotte va trop rapidement.
    private final double ACCELERATION_X = 2;
    private final double ACCELERATION_Y = 2;

    private final double VITESSE_MAX = 4;

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

        //region -- MOUVEMENT HORIZONTALE --
        if (gauche)
            ax = -ACCELERATION_X;
        else if (droite)
            ax = ACCELERATION_X;
        else
            ax = 0;
        //endregion

        //region -- MOUVEMENT VERTICALE --
        if (haut)
            ay = -ACCELERATION_Y;
        else if (bas)
            ay = ACCELERATION_Y;
        else
            ay = 0;
        //endregion

        mettreAJourPhysique(deltaTemps);

    }

    private void mettreAJourPhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        vx = Math.max(vx, VITESSE_MAX);
        vy = Math.max(vy, VITESSE_MAX);

        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }

    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y);
    }


}
