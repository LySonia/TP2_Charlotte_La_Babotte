package ca.qc.bdeb.sim203.projetjavafx;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public abstract class Scenes  {

    protected Pane root = new Pane();
    private Scene scene = new Scene(root, Main.LARGEUR, Main.HAUTEUR);

    public static HashMap<String, Scene> scenesDuTP;


    protected Scenes() {
        construireScene();
        root.setStyle("-fx-background-color: #2A7FFF;");

        //region ÉVÉNEMENTS
        scene.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);
        });

        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });


        //declarerEvenements();


        //endregion
    }

    public abstract void construireScene();


    protected void declarerEvenements(){ //méthode pour déclarer les évènements dans chaque sous classe, elle est appelé dans le constructeur de scène and it works idk lmao
        //escape pour quitter le programme
        /*scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
            }
        });*/
    };

    public static void construireHashmapScene(){
        scenesDuTP.put("Scène d'accueil", SceneAccueil.getSceneAccueil().getScene());
        scenesDuTP.put("Scène d'info", SceneInfo.getSceneInfo().getScene());
    }

    public abstract void escape();

    public Scene getScene() {
        return scene;
    }
}
