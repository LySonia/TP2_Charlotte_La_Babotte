package ca.qc.bdeb.sim203.projetjavafx;

import javafx.animation.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;

import java.util.*;

public class SceneJeu extends Scenes {
    private boolean estEnModeDebug = false;
    public final static double NANOSECONDE = 1e-9;
    @Override
    public void construireScene() {
        ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
        Canvas canvas = new Canvas(Main.LARGEUR, Main.HAUTEUR);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        Charlotte charlotte = new Charlotte();
        objetsJeu.add(charlotte);
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

                lastTime = now; //TODO: Est-ce que c'est ça le problème
            }

        };
        timer.start();

        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.D) {
                gererModeDebug(objetsJeu);
            }
        });
    }

    public void gererModeDebug(ArrayList<ObjetJeu> objetsJeu) {
        boolean projectileEtoile = Input.isKeyPressed(KeyCode.Q);
        boolean projectileHippocampes = Input.isKeyPressed(KeyCode.W);
        boolean projectileSardines = Input.isKeyPressed(KeyCode.E);
        boolean redonnerVies = Input.isKeyPressed(KeyCode.R);
        boolean prochainNiveau = Input.isKeyPressed(KeyCode.T);

        //Mettre un rectangle jaune autour de tous les objets de jeu
        //TODO: Quick and dirty fix:
        if (objetsJeu != null) {
            for (ObjetJeu objet : objetsJeu) {
                objet.mettreContour();
            }
        }

    }

    @Override
    public void escape() {

    }
}
