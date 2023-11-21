package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

import static ca.qc.bdeb.sim203.projetjavafx.Aleatoire.obtenirNombreAleatoire;

//PartieJeu est comme la classe controlleur

//J'aurais les positions pour les coraux ici
public class PartieJeu {
    //TODO: Pas encore certaine de comment utiliser cette classe
    private ArrayList<ObjetJeu> objetsJeu = new ArrayList<>();
    private Charlotte charlotte = new Charlotte();
    private ArrayList<PoissonEnnemi> poissonsEnnemis = new ArrayList<>();
    private BarreVie barreVie = new BarreVie(charlotte);
    private int nbrPoissonsEnnemis = 0;
    private int niveau = 1;
    private final int NBR_NIVEAUX_TOTAL = 8;
    private final int LARGEUR_TOTAL_NIVEAU = 8*Main.LARGEUR;
    private double nSecondes = 1;

    private double tempsDebutNiveau = 0;
    private double tempsEcouleDepuisDebutNiveau = 0;

    //TOUT EN LIEN AVEC FOND -> VUE
    private double teinte;
    private double saturation = 0.84;
    private double luminosité = 1.0;

    public PartieJeu() {
        objetsJeu.add(charlotte);
        objetsJeu.addAll(poissonsEnnemis); //Will this be updated?
        objetsJeu.add(barreVie);
    }

    public void commencerJeu() {
        //Gerer les niveaux
    }

    public void demarrerNiveau(double tempsActuel) {
        tempsDebutNiveau = tempsActuel;

        //Calculer, pour une première fois, le temps écoulé depuis le début du niveau
        calculerTempsEcouleDepuisDebutNiveau(tempsActuel);

        //Trouver une teinte aléatoire pour la couleur de fond du niveau -> Trop de vue?!? /TODO
        preparerFondNiveau();

        //Ajouter une quantité initiale de poissons enemies
        ajouterGroupePoissons();

        //Calculer à quelle fréquence les poissons enemies devraient apparaître dans ce niveau
        calculerNSecondes();
    }

    private void calculerTempsEcouleDepuisDebutNiveau(double tempsActuel) {
        tempsEcouleDepuisDebutNiveau = tempsActuel - tempsDebutNiveau;
    }

    private void preparerFondNiveau() {
        teinte = obtenirNombreAleatoire(190, 270);
    }

    public void ajouterGroupePoissons() {
        //Génération d'un nombre aléatoire de poissons:
        int nbrPoissonDansGroupe = Aleatoire.obtenirNombreAleatoire(1, 5);
        ArrayList<PoissonEnnemi> nouveauxPoissons = new ArrayList<>();

        //Ajouter le nombre aléatoire de poissons dans le ArrayList<>
        for (int i = 0; i < nbrPoissonDansGroupe; i++) {
            nouveauxPoissons.add(new PoissonEnnemi(niveau));
        }

       //Ajouter les nouveaux poissons dans le ArrayList de poissons ennemis
        poissonsEnnemis.addAll(nouveauxPoissons);

        //Ajouter les nouveaux poissonds dans le ArrayList d'objet de jeu
        //TODO: Il y a sûrement un moyen plus efficace pour placer les nouveaux poissons dans les 2 ArrayLists
        objetsJeu.addAll(nouveauxPoissons);
    }

    //TODO: À compléter
    private ArrayList<Double> trouverPositionsXAlgues() {
        ArrayList<Double> posXAlgues = new ArrayList<>();
        double dernierePos = 0;
        posXAlgues.add(dernierePos);

        double distanceDeLaDerniere = choisirDistanceDeLaDerniere(); //Fix le copié-collé
        double nouvellePos = dernierePos + distanceDeLaDerniere;

        while (nouvellePos < LARGEUR_TOTAL_NIVEAU) {
            posXAlgues.add(nouvellePos);
            distanceDeLaDerniere = choisirDistanceDeLaDerniere();
            nouvellePos = dernierePos + distanceDeLaDerniere;
        }

        return posXAlgues;
    }

    private double choisirDistanceDeLaDerniere() {
        int nbrAleatoire = obtenirNombreAleatoire(1, 2);
        double distanceChoisie = 50;
        if (nbrAleatoire == 2)
            distanceChoisie = 100;
        return distanceChoisie;
    }

    public void sortirPoisson(PoissonEnnemi poisson) {
        poissonsEnnemis.remove(poisson);
        objetsJeu.remove(poisson);
    }

    private void calculerNSecondes() {
        nSecondes = 0.75 + 1/Math.pow(niveau, 0.5);
    }


    //GETTERS
    public ArrayList<ObjetJeu> getObjetsJeu() {
        return objetsJeu;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public ArrayList<PoissonEnnemi> getPoissonsEnnemis() {
        return poissonsEnnemis;
    }

    public double getTeinte() {
        return teinte;
    }

    public double getSaturation() {
        return saturation;
    }

    public double getLuminosité() {
        return luminosité;
    }

    public double getNSecondes() {
        return nSecondes;
    }

    public double getTempsDebutNiveau() {
        return tempsDebutNiveau;
    }
}
