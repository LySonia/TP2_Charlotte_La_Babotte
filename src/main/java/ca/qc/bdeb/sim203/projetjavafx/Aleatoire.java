package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

//Classe avec méthodes statiques, pcq j'ai la flemme de créer un nouveau object random à chaque fois que j'en ai besoin
public class Aleatoire {
    public static Random generateurAleatoire = new Random();

    public static int obtenirNombreAleatoire(int borneSuperieureExclue) {
        return generateurAleatoire.nextInt(borneSuperieureExclue);
    }
    public static int obtenirNombreAleatoire(int borneInférieureInclue, int borneSuperieureInclue) {
        return generateurAleatoire.nextInt(borneSuperieureInclue - borneInférieureInclue) + borneInférieureInclue; //TODO: Noms de variables horribles
    }

    public static Random getGenerateurAleatoire() {
        return generateurAleatoire;
    }
}
