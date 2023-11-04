package ca.qc.bdeb.sim203.projetjavafx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public final static double NANOSECONDE = 1e-9;
    public static int WIDTH = 900;
    public static int HEIGHT = 520;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //region -- VARIABLES D'AFFICHAGE DE BASE --
        var root = new Pane();

        // Une scene, canvas et un context de base juste pour tester Charlotte qui bouge
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        var context = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        //endregion

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
                context.clearRect(0, 0, WIDTH, HEIGHT);
                charlotte.draw(context);
                //endregion

                lastTime = now; //TODO: fix
            }
        };


        //region ÉVÉNEMENTS
        scene.setOnKeyPressed((e) -> {
           Input.setKeyPressed(e.getCode(), true);
        });

        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
        //endregion

        timer.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Charlotte la Barbotte");
        primaryStage.show();
    }
}