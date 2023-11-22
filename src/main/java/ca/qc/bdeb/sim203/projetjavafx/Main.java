package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.stage.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static final int LARGEUR_ECRAN = 900;
    public static final int LARGEUR_MONDE = 8*Main.LARGEUR_ECRAN;
    public static final int HAUTEUR = 520;
    @Override
    public void start(Stage primaryStage) throws Exception {
        var scenesJeu = new Scenes();
        primaryStage = scenesJeu.getStage();

        primaryStage.setTitle("Charlotte la Barbotte");
        primaryStage.show();
    }
}