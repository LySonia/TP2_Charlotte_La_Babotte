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
    private boolean estEnDebug = false;
    public final static double NANOSECONDE = 1e-9;
    private PartieJeu partieJeu = new PartieJeu();
    private double tempsActuel = System.nanoTime() * NANOSECONDE;
    private final double TEMPS_AFFICHAGE_NIVEAU = 4;

    public Scenes() {
        stage.setScene(getSceneAccueil()); //Par défault, c'est la scène d'accueil
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getSceneJeu() {
        //2 prochaines lignes sont des tests
        tempsActuel = System.nanoTime() * NANOSECONDE;
        System.out.println("Premier tempsActuel: " + tempsActuel);
        partieJeu.demarrerNiveau(tempsActuel);
        var root = new Pane();


        var sceneJeu = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);
        Canvas canvas = new Canvas(Main.LARGEUR_ECRAN, Main.HAUTEUR);
        GraphicsContext contexte = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        Charlotte charlotte = partieJeu.getCharlotte();
        ArrayList<PoissonEnnemi> poissonsEnnemis = partieJeu.getPoissonsEnnemis();
        ArrayList<ObjetJeu> objetsJeu = partieJeu.getObjetsJeu();


        AnimationTimer timer = new AnimationTimer() {


            double lastTime = System.nanoTime() * NANOSECONDE;
            double tempsActuel = System.nanoTime()  * NANOSECONDE;
            double deltaTemps = tempsActuel - lastTime;
            double tempsDerniersPoissons = System.nanoTime() * NANOSECONDE;

            @Override
            public void handle(long now) {

//                sceneJeu.setOnKeyPressed(event -> {
//                    if(event.getCode() == KeyCode.SPACE){
//                        charlotte.utiliserProjectile(System.nanoTime()*NANOSECONDE);
//                    }
//                });

                Color couleurFond = partieJeu.getCouleurFondNiveau();
                root.setBackground(new Background(new BackgroundFill(couleurFond, null, null)));

                deltaTemps = (now - lastTime) * NANOSECONDE;
                tempsActuel = now * NANOSECONDE;
                System.out.println("Deuxième tempsActuel: " + tempsActuel);

                //ajusterLaCouleurDeFond
                ajusterCouleurFond();

                //Gérer les collisions entre chaque poisson et Charlotte
                gererCollisionsEntrePoissonsEtCharlotte();

                //Mettre à jour chacun des objets de jeu
                mettreAJour();

                //Dessiner chaque objet de jeu
                dessiner(contexte, objetsJeu);

                //Ajouter des poissons après quelques Nsecondes
                if ((tempsActuel - tempsDerniersPoissons) > partieJeu.getNSecondes()) {
                    partieJeu.ajouterGroupePoissons();
                    tempsDerniersPoissons = tempsActuel;
                }

                //Enlever les poissons qui sont hors-écran
                    //TODO: Maybe have to check null cases
                enleverPoissonsHorsEcran();

                //Mettre ou enlever mode debug
                if (estEnDebug) {
                    gererModeDebug();
                }

                //Afficher numéro du niveau
                if (tempsActuel - partieJeu.getTempsDebutNiveau() < TEMPS_AFFICHAGE_NIVEAU) {

                    String texteNiveau = ("NIVEAU " + partieJeu.getNiveau());
                    contexte.setFont(Font.font("Arial", 100));

                    //TODO: Remplacer Main.Largeur/2 et Main.Hauteur/2 par la position du centre de la caméra
                    contexte.fillText(texteNiveau, 200, Main.HAUTEUR/2);

                    //Remettre le font à la taille normale
                    contexte.setFont(Font.font("Arial", 10));
                }

                //Gerer image de Charlotte
                gererImageCharlotte();


                //TODO: Fix copié-collé
                //Gérer les événements

                lastTime = now;
            }

            private void ajusterCouleurFond() {
            }

            private void gererImageCharlotte() { //Code dans la bonne classe?
                if (charlotte.estVisible()) {
                    if (charlotte.estEndommagee()) {
                        charlotte.setImage(Assets.CHARLOTTE_OUTCH.getEmplacement());
                    } else if (charlotte.estEnMouvement()){
                        charlotte.setImage(Assets.CHARLOTTE_AVANT.getEmplacement());
                    } else {
                        charlotte.setImage(Assets.CHARLOTTE.getEmplacement());
                    }
                } else {
                    charlotte.setImage(Assets.CHARLOTTE_OUTCH_TRANSPARENT.getEmplacement());
                }
            }

            private void enleverPoissonsHorsEcran() {
                //TODO Delete this comment before giving in TP
                //Ici, je dois me servir d'un for loop traditionnel au lieu du for each, sinon y'a erreur
                for (int i = 0; i < poissonsEnnemis.size(); i++) {
                    if (!poissonsEnnemis.get(i).estDansEcran()) {
                        partieJeu.sortirPoisson(poissonsEnnemis.get(i));
                    }
                }
            }

            private void dessiner(GraphicsContext contexte, ArrayList<ObjetJeu> objetsJeu){
                contexte.clearRect(0, 0, Main.LARGEUR_ECRAN, Main.HAUTEUR); //Clear le canvas
                for (ObjetJeu objectJeu: objetsJeu) {
                    objectJeu.dessiner(contexte);
                }
            }

            private void mettreAJour() {
                Camera.getCamera().update(charlotte, deltaTemps);
                for (ObjetJeu objetJeu: objetsJeu) {
                    objetJeu.update(deltaTemps);
                }
            }

            //TODO: Trop de logique?
            private void gererCollisionsEntrePoissonsEtCharlotte() {
                for (PoissonEnnemi poissonEnnemi: poissonsEnnemis) {
                    if (poissonEnnemi.estEnCollisionAvecCharlotte(charlotte)){
                        charlotte.prendreDommage();
                    }
                    charlotte.gererImmortalite(tempsActuel);
                }
            }

            private void gererModeDebug() {
                //Mettre un rectangle jaune autour de tous les objets de jeu
                //TODO: Quick and dirty fix: (le not equal to null)
                if (objetsJeu != null) {
                    for (ObjetJeu objet : objetsJeu) {
                        objet.mettreContour(contexte);
                    }
                }

                //Afficher les nombre de poissons dans le jeu
                var texteNbrPoissons = "NB Poissons: " + partieJeu.getNbrPoissonsEnnemis();
                contexte.fillText(texteNbrPoissons, 10, 55); //TEST

                //Afficher le temps passé depuis le début du niveau
                var texteTempsEcoule = "Temps écoulé: " + (tempsActuel - partieJeu.getTempsDebutNiveau());
                contexte.fillText(texteTempsEcoule, 10, 70); //TEST
            }


        };
        timer.start();

        //Événements:
        sceneJeu.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);
            if (e.getCode() == KeyCode.D) {
                estEnDebug = !estEnDebug;
            }

            if(e.getCode() == KeyCode.SPACE){
                charlotte.utiliserProjectile(System.nanoTime()*NANOSECONDE);
                System.out.println("Works?");
            }

            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        sceneJeu.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });

        return sceneJeu;
    }


    //region AUTRES SCENES
    public Scene getSceneAccueil() {
        Pane root = creerRoot();
        var sceneAccueil = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);
        var logo = new Image(Assets.LOGO.getEmplacement());
        var logoImageView = new ImageView(logo);
        logoImageView.setPreserveRatio(true);
        logoImageView.setFitWidth(logo.getWidth() / 1.5);

        Button infos = new Button("Infos!");
        Button jouer = new Button("Jouer!");

        var vboxAccueil = new VBox();
        vboxAccueil.setPrefHeight(Main.HAUTEUR);
        vboxAccueil.setPrefWidth(Main.LARGEUR_ECRAN);

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
        var sceneInfo = new Scene(root, Main.LARGEUR_ECRAN, Main.HAUTEUR);

        var vbox = new VBox();
        vbox.setPrefWidth(Main.LARGEUR_ECRAN);
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
    //endregion

    //region MÉTHODES GÉNÉRALES
    private void gererEvenementsGenerales(Scene scene) {
        scene.setOnKeyPressed((e) -> {
            Input.setKeyPressed(e.getCode(), true);
            if (e.getCode() == KeyCode.D) {
                estEnDebug = !estEnDebug;
            }

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
    //endregion

    //Mettre choisir poisson au hasard ici? /TODO

}
