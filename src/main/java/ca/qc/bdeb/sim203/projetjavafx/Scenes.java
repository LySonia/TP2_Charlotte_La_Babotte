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

import static ca.qc.bdeb.sim203.projetjavafx.Aleatoire.obtenirNombreAleatoire;

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

    public Scene getSceneJeu() {
        PartieJeu partieJeu = new PartieJeu();
        Pane root = creerRoot();
        Color couleurFond = (Color.hsb(partieJeu.getTeinte(), partieJeu.getSaturation(), partieJeu.getLuminosité()));
        root.setBackground(new Background(new BackgroundFill(couleurFond, null, null)));

        var sceneJeu = new Scene(root, Main.LARGEUR, Main.HAUTEUR);
        Canvas canvas = new Canvas(Main.LARGEUR, Main.HAUTEUR);
        GraphicsContext contexte = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        //TESTS: These should all be taken from partie jeu
        Charlotte charlotte = partieJeu.getCharlotte();
        ArrayList<PoissonEnnemi> poissonEnnemis = partieJeu.getPoissonsEnnemis();
        ArrayList<ObjetJeu> objetsJeu = partieJeu.getObjetsJeu();


        AnimationTimer timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            double tempsDepuisDerniersPoissons = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * NANOSECONDE;

                //-- UPDATE --
                for (PoissonEnnemi poissonEnnemi: poissonEnnemis) {
                    if (poissonEnnemi.estEnCollisionAvecCharlotte(charlotte))
                        charlotte.gererDommage();
                }

                charlotte.gererImmortalite(now*NANOSECONDE);

                for (ObjetJeu objetJeu: objetsJeu) {
                    objetJeu.update(deltaTemps);
                }


                //-- DESSINER --
                contexte.clearRect(0, 0, Main.LARGEUR, Main.HAUTEUR);
                for (ObjetJeu objectJeu: objetsJeu) {
                    objectJeu.dessiner(contexte);
                }

                if (charlotte.estEndommagee()) {

                }

                //Ajouter des poissons après quelques Nsecondes
                tempsDepuisDerniersPoissons = (now * NANOSECONDE) - tempsDepuisDerniersPoissons;
                if (tempsDepuisDerniersPoissons > partieJeu.getNSecondes()) {
                    partieJeu.ajouterGroupePoissons();
                    tempsDepuisDerniersPoissons = now * NANOSECONDE;
                }

                //Enlever les poissons qui sont hors-écran
                for (PoissonEnnemi poissonEnnemi: poissonEnnemis) {

                }

                //Mettre ou enlever mode debug
                if (estEnDebug) {
                    gererModeDebug(objetsJeu, canvas.getGraphicsContext2D());
                }



                lastTime = now;
            }

        };
        timer.start();


        //region ÉVÉNEMENTS
        //TODO: Fix le outrageous copié-collé icite :O)
        //TODO: Et réduire la quantité de code à l'intérieur des événements
        sceneJeu.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);

            if (e.getCode() == KeyCode.D) {
                estEnDebug = !estEnDebug;
            }

            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        sceneJeu.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
        //endregion

        return sceneJeu;
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

    public Scene getSceneAccueil() {
        Pane root = creerRoot();
        var sceneAccueil = new Scene(root, Main.LARGEUR, Main.HAUTEUR);
        var logo = new Image(Assets.LOGO.getEmplacement());
        var logoImageView = new ImageView(logo);
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitWidth(logo.getWidth() / 1.5);

        Button infos = new Button("Infos!");
        Button jouer = new Button("Jouer!");

        var vboxAccueil = new VBox();
        vboxAccueil.setPrefHeight(Main.HAUTEUR);
        vboxAccueil.setPrefWidth(Main.LARGEUR);

        var groupeBoutons = new HBox();
        groupeBoutons.getChildren().addAll(jouer, infos);
        groupeBoutons.setAlignment(Pos.CENTER);

        vboxAccueil.getChildren().addAll(
                logoImageView,
                groupeBoutons
        );
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
        vbox.setPrefWidth(Main.LARGEUR);
        vbox.setPrefHeight(Main.HAUTEUR);

        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(50));
        //TODO: Faire de sorte que c'est toujours un poisson au hasard qui est choisie
        var poissonEnnemiImage = new Image(Assets.choisirPoissonHasard());
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
        vbox.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(vbox);

        //region ÉVÉNEMENTS
        gererEvenementsGenerales(sceneInfo);

        retour.setOnAction((e) -> {
            stage.setScene(getSceneAccueil());
        });
        //endregion

        return sceneInfo;
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

    private Pane creerRoot() {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #2A7FFF;"); // Pour faire de sorte que le fond est bleu
        return root;
    }

}
