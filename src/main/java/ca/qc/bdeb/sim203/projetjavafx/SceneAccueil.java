package ca.qc.bdeb.sim203.projetjavafx;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SceneAccueil extends Scenes{

    private Button jouer = new Button("Jouer!");
    private Button infos = new Button("Infos!");

    private Image logo = new Image("logo.png");


    @Override
    public void construireScene() {
        //section de boutons
        var groupeBoutons = new HBox();
        groupeBoutons.getChildren().add(jouer);
        groupeBoutons.getChildren().add(infos);
        groupeBoutons.setAlignment(Pos.CENTER);


        var imgvLogo = new ImageView(logo);
        imgvLogo.setPreserveRatio(true);
        imgvLogo.setFitWidth(logo.getWidth()/1.5);

        var vboxAccueil = new VBox();
        vboxAccueil.getChildren().add(imgvLogo);
        vboxAccueil.getChildren().add(groupeBoutons);
        vboxAccueil.setAlignment(Pos.CENTER);

        root.getChildren().add(vboxAccueil);


    }
}
