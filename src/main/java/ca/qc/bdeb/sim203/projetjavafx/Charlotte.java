package ca.qc.bdeb.sim203.projetjavafx;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Charlotte extends ObjetJeu {
    private final double W_CHARLOTTE = 102;
    private final double H_CHARLOTTE = 90;

    //TODO: Pour les deux accélérations: devrait être 1000, mais quand c'est 1000, Charlotte va trop rapidement.
    private final double ACCELERATION_X = 0.2;
    private final double ACCELERATION_Y = 0.2;

    //TODO: La vitesse devrait être de 300, mais quand c'est 300, Charlotte va trop rapidement
    private final double VITESSE_MAX = 0.2;

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
        //NOTE: Math.min() choisit la plus petite valeur entre les 2 valeurs
        //Math.max() choisit la plus grande valeur entre les 2 valeurs
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        vx = Math.min(vx, VITESSE_MAX);
        vy = Math.min(vy, VITESSE_MAX);

        x += deltaTemps * vx;
        y += deltaTemps * vy;
        x = Math.max(0, x);
        x = Math.min(x, Main.WIDTH);
        y = Math.max(0, y);
        y = Math.min(y, (Main.HEIGHT - h)); //J'ai pris en consid
    }

    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y);
    }


}
