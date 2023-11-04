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
    public static final int LARGEUR = 900;
    public static final int HAUTEUR = 520;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //region -- VARIABLES D'AFFICHAGE DE BASE --
        var root = new Pane();

        // Une scene, canvas et un context de base juste pour tester Charlotte qui bouge
        var sceneInfo = new SceneInfo();
        var sceneAccueil = new SceneAccueil();
        var sceneJeu = new SceneJeu();
        var scene = sceneJeu.getScene();
        //endregion


        primaryStage.setScene(scene);
        primaryStage.setTitle("Charlotte la Barbotte");
        primaryStage.show();
    }
}