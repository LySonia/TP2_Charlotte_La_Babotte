package ca.qc.bdeb.sim203.projetjavafx;

import javafx.animation.*;
import javafx.scene.canvas.*;

public class SceneJeu extends Scenes {
    private boolean estEnModeDebug = false;
    public final static double NANOSECONDE = 1e-9;
    @Override
    public void construireScene() {
        Canvas canvas = new Canvas(Main.LARGEUR, Main.HAUTEUR);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        Charlotte charlotte = new Charlotte();
        AnimationTimer timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * NANOSECONDE;

                //region -- UPDATE --
                charlotte.update(deltaTemps);
                //endregion

                //region -- DESSINER --
                context.clearRect(0, 0, Main.LARGEUR, Main.HAUTEUR);
                charlotte.draw(context);
                //endregion

                lastTime = now; //TODO: fix
            }
        };
        timer.start();
    }

    public void gererModeDebug()

    @Override
    public void escape() {

    }
}
