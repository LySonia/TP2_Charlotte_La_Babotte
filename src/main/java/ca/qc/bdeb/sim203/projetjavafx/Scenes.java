package ca.qc.bdeb.sim203.projetjavafx;

import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.util.*;

public class Scenes {
    private Stage stage = new Stage();
    private Pane root = new Pane();
    private boolean estEnDebug = false;
    public final static double NANOSECONDE = 1e-9;

    public Scenes() {
        stage.setScene(getSceneAccueil()); //Par défault, c'est la scène d'accueil
    }


    public Stage getStage() {
        return stage;
    }

    private void gererEvenementsGenerales(Scene scene) {
        scene.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);

            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
    }

    public Scene getSceneJeu() {
        Pane root = creerRoot();
        var sceneJeu = new Scene(root, Main.LARGEUR, Main.HAUTEUR);
        ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
        Canvas canvas = new Canvas(Main.LARGEUR, Main.HAUTEUR);
        GraphicsContext contexte = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        Charlotte charlotte = new Charlotte();
        objetsJeu.add(charlotte);
        AnimationTimer timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * NANOSECONDE;

                //region -- UPDATE --
                charlotte.update(deltaTemps);
                //endregion

                //region -- DESSINER --
                contexte.clearRect(0, 0, Main.LARGEUR, Main.HAUTEUR);
                charlotte.dessiner(contexte);

                if (estEnDebug) {
                    gererModeDebug(objetsJeu, canvas.getGraphicsContext2D());
                }
                //endregion

                lastTime = now;
            }

        };
        timer.start();


        //region ÉVÉNEMENTS
        sceneJeu.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);
            if (e.getCode() == KeyCode.D) {
                estEnDebug = !estEnDebug;
            }
            if (e.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        sceneJeu.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
        //endregion

        return sceneJeu;
    }



    public Scene getSceneAccueil() {
        Pane root = creerRoot();
        var sceneAccueil = new Scene(root, Main.LARGEUR, Main.HAUTEUR);
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
        imgvLogo.setFitWidth(logo.getWidth() / 1.5);

        var vboxAccueil = new VBox();

        vboxAccueil.setMaxHeight(Main.HAUTEUR);
        vboxAccueil.setMaxWidth(Main.LARGEUR);
        vboxAccueil.getChildren().add(imgvLogo);
        vboxAccueil.getChildren().add(groupeBoutons);
        vboxAccueil.setAlignment(Pos.CENTER);


        root.getChildren().add(vboxAccueil);


        //region ÉVÉNEMENTS
        gererEvenementsGenerales(sceneAccueil);

        jouer.setOnAction((e) -> {
            stage.setScene(getSceneJeu());
        });

        infos.setOnAction((e) -> {
            stage.setScene(getSceneInfo());
        });
        //endregion

        return sceneAccueil;
    }

    public Scene getSceneInfo() {
        Pane root = creerRoot();
        var sceneInfo = new Scene(root, Main.LARGEUR, Main.HAUTEUR);

        var vbox = new VBox();
        vbox.setMaxWidth(Main.LARGEUR);
        vbox.setMaxHeight(Main.HAUTEUR);
        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(50));
        //TODO: Faire de sorte que c'est toujours un poisson au hasard qui est choisie
        var poissonEnnemiImage = new Image(choisirPoissonHasard());
        var poissonEnnemiImageView = new ImageView(poissonEnnemiImage);


        var par = new Text("Par");
        var camilleMarquis = new Text("Camille Marquis");
        camilleMarquis.setFont(Font.font(30));
        var hBoxCamille = new HBox();
        hBoxCamille.getChildren().addAll(
                par,
                camilleMarquis
        );
        hBoxCamille.setAlignment(Pos.BOTTOM_CENTER);
        hBoxCamille.setSpacing(5);

        var et = new Text("et");
        var soniaLy = new Text("Sonia Ly");
        soniaLy.setFont(Font.font(30));
        var hBoxSonia = new HBox();
        hBoxSonia.getChildren().addAll(
                et,
                soniaLy
        );
        hBoxSonia.setAlignment(Pos.BOTTOM_CENTER);
        hBoxSonia.setSpacing(5);


        var texteExplicatif = new Text("Travail remis à Nicolas Hurtubise et George Côté. " +
                "Graphismes adaptés de https://games-icons.net/ et de https://openclipart.org/. " +
                "Développé dans le cadre du cours 420-203-RE. " +
                "Développement de programmes dans un environnement graphique, au Collège de Bois-de-Boulogne.");
        var texteExplicatifMieux = new TextFlow(texteExplicatif);

        var retour = new Button("Retour");

        vbox.getChildren().addAll(
                titre,
                poissonEnnemiImageView,
                hBoxCamille,
                hBoxSonia,
                texteExplicatifMieux,
                retour
        );
        vbox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(vbox);

        //region ÉVÉNEMENTS
        gererEvenementsGenerales(sceneInfo);

        retour.setOnAction((e) -> {
            stage.setScene(getSceneAccueil());
        });
        //endregion

        return sceneInfo;
    }



    public void gererModeDebug(ArrayList<ObjetJeu> objetsJeu, GraphicsContext contexte) {
        //Mettre un rectangle jaune autour de tous les objets de jeu
        //TODO: Quick and dirty fix: (le not equal to null)
        if (objetsJeu != null) {
            for (ObjetJeu objet : objetsJeu) {
                objet.mettreContour(contexte);
            }
        }
    }
    public String choisirPoissonHasard(){
        //TODO: Façon plus efficace de faire?
        String poissonChoisi = Assets.POISSON_1.getEmplacement(); //Par défault

        String[] poissonsEnnemis = {
                Assets.POISSON_1.getEmplacement(),
                Assets.POISSON_2.getEmplacement(),
                Assets.POISSON_3.getEmplacement(),
                Assets.POISSON_4.getEmplacement(),
                Assets.POISSON_5.getEmplacement()
        };

        Random aleatoire = new Random();
        int nbrAleatoire = aleatoire.nextInt(poissonsEnnemis.length);

        for (int i = 0; i < poissonsEnnemis.length; i++) {
            if (nbrAleatoire == i)
                poissonChoisi = poissonsEnnemis[i];
        }
        return poissonChoisi;
    }

    private Pane creerRoot() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #2A7FFF;");
        return root;
    }

}
