package ca.qc.bdeb.sim203.projetjavafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SceneAccueil extends Scenes{

    private static SceneAccueil accueil = null;

    public static SceneAccueil getSceneAccueil(){
        if (accueil==null){
            accueil = new SceneAccueil();
        }
        return accueil;
    }



    @Override
    public void construireScene() {
        Image logo = new Image("logo.png");
        Button infos = new Button("Infos!");
        Button jouer = new Button("Jouer!");

        //section de boutons
        var groupeBoutons = new HBox();
        groupeBoutons.getChildren().add(jouer);
        groupeBoutons.getChildren().add(infos);
        groupeBoutons.setAlignment(Pos.CENTER);


        var imgvLogo = new ImageView(logo);
        imgvLogo.setPreserveRatio(true);
        imgvLogo.setFitWidth(logo.getWidth()/1.5);

        var vboxAccueil = new VBox();

        vboxAccueil.setMaxHeight(Main.HAUTEUR);
        vboxAccueil.setMaxWidth(Main.LARGEUR);

        vboxAccueil.getChildren().add(imgvLogo);
        vboxAccueil.getChildren().add(groupeBoutons);
        vboxAccueil.setAlignment(Pos.CENTER);


        root.getChildren().add(vboxAccueil);


    }

    @Override
    protected void declarerEvenements() {
        super.declarerEvenements();
        SceneAccueil.getSceneAccueil().getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.P){
                System.exit(0);
            }
        });
    }

    @Override
    public void escape() {

    }
}
