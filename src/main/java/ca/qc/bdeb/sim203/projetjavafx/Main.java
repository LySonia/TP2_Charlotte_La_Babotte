package ca.qc.bdeb.sim203.projetjavafx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
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
        var scenesJeu = new Scenes();
        var scene = scenesJeu.getSceneJeu();



        primaryStage.setScene(scene);
        primaryStage.setTitle("Charlotte la Barbotte");
        primaryStage.show();
    }
}