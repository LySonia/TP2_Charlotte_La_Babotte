package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

public class GenerateurAleatoire {
    public static Random generateurAleatoire = new Random();

    public static int obtenirNombreAleatoire(int borneSuperieureExclue) {
        return generateurAleatoire.nextInt(borneSuperieureExclue);
    }
    public static int obtenirNombreAleatoire(int borneInférieureInclue, int borneSuperieureInclue) {
        return generateurAleatoire.nextInt(borneSuperieureInclue - borneInférieureInclue) + borneInférieureInclue; //TODO: Noms de variables horribles
    }
}
