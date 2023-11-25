package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

//Classe avec méthodes statiques, pcq j'ai la flemme de créer un nouveau object random à chaque fois que j'en ai besoin
//Tout ce qui peut être généré par hasard peut se faire générer icite
public class Hasard {
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

    public static String choisirPoissonHasard(){
        String[] poissons = {
                Assets.POISSON_1.getEmplacement(),
                Assets.POISSON_2.getEmplacement(),
                Assets.POISSON_3.getEmplacement(),
                Assets.POISSON_4.getEmplacement(),
                Assets.POISSON_5.getEmplacement()
        };

        return choisirImageHasard(poissons);
    }
    public static String choisirDecorHasard(){
        String[] decors = {
                Assets.DECOR_1.getEmplacement(),
                Assets.DECOR_2.getEmplacement(),
                Assets.DECOR_3.getEmplacement(),
                Assets.DECOR_4.getEmplacement(),
                Assets.DECOR_5.getEmplacement(),
                Assets.DECOR_6.getEmplacement()
        };

        return choisirImageHasard(decors);
    }
    private static String choisirImageHasard(String[] emplacements){
        String imageChoisi = "";

        int nbrAleatoire = obtenirNombreAleatoire(0, emplacements.length - 1);

        for (int i = 0; i < emplacements.length; i++) {
            if (nbrAleatoire == i)
                imageChoisi = emplacements[i];
        }
        return imageChoisi;
    }
}
