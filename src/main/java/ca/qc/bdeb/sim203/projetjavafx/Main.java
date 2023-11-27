package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.stage.*;

//possibilité d'avoir 0 pour les vitesses des poissons à gérer! //TODO
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static final double LARGEUR_ECRAN = 900;
    public static final double LARGEUR_MONDE = 8*Main.LARGEUR_ECRAN;
    public static final double HAUTEUR = 520;
    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        var scenes = new Scenes(stage);
        scenes.getSceneAccueil();

        stage.show();
    }
}