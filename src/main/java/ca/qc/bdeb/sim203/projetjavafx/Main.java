package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.stage.*;

/**
 * Classe Main
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static final double LARGEUR_ECRAN = 900.0;
    public static final double LARGEUR_MONDE = 8.0 * Main.LARGEUR_ECRAN;
    public static final double HAUTEUR = 520.0;
    private Stage stage;

    /**
     * Méthoe qui nous permet d'exécuter l'application avec JavaFX
     * @param primaryStage le stage de l'application JavaFX sur lequel on fixe une scène
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        var scenes = new Scenes(stage);
        scenes.getSceneAccueil();

        stage.show();
    }
}