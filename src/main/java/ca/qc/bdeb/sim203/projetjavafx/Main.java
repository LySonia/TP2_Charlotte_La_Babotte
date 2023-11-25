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
    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        var scenes = new Scenes(stage);
        scenes.getSceneAccueil();

        stage.show();
    }
}