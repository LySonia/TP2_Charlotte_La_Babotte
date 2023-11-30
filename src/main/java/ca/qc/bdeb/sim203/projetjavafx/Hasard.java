package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

public class Hasard {
    public static Random generateurAleatoire = new Random();

    /**
     * Méthode qui retourne une valeur int random se trouvant entre deux valeurs inclusives
     *
     * @param borneInfInclue la borne inférieure inclusive
     * @param borneSupInclue la borne supérieure inclusive
     * @return un int random se trouvant entre la borne inférieure et la borne supérieure
     */
    public static int nextInt(int borneInfInclue, int borneSupInclue) {
        return generateurAleatoire.nextInt(borneSupInclue - borneInfInclue) + borneInfInclue;
    }

    /**
     * Méthode qui retourne une valeur double random se trouvant entre deux valeurs inclusives
     *
     * @param borneInférieureInclue la borne inférieure inclusive
     * @param borneSuperieureInclue la borne supérieure inclusive
     * @return un double aléatoire se trouvant entre les deux bornes inclusivement
     */
    public static double nextDouble(double borneInférieureInclue, double borneSuperieureInclue) {
        return generateurAleatoire.nextDouble
                (borneSuperieureInclue - borneInférieureInclue) + borneInférieureInclue;
    }

    /**
     * Méthode qui retourne un Asset de poisson au hasard parmi le tableau
     *
     * @return un Asset de poisson au hasard
     */
    public static Assets choisirPoissonHasard() {
        Assets[] poissons = {
                Assets.POISSON_1,
                Assets.POISSON_2,
                Assets.POISSON_3,
                Assets.POISSON_4,
                Assets.POISSON_5
        };

        return choisirAssetHasard(poissons);
    }

    /**
     * Méthode qui retourne un Asset de décors au hasard parmi le tableau
     *
     * @return un Asset de decors
     */
    public static Assets choisirDecorHasard() {
        Assets[] decors = {
                Assets.DECOR_1,
                Assets.DECOR_2,
                Assets.DECOR_3,
                Assets.DECOR_4,
                Assets.DECOR_5,
                Assets.DECOR_6
        };

        return choisirAssetHasard(decors);
    }

    /**
     * Méthode qui retourne un Asset de projectile au hasard parmi le tableau
     *
     * @return un Asset de projectile aléatoire
     */
    public static Assets choisirTypeProjectileHasard() {
        Assets[] projectiles = {
                Assets.ETOILE,
                Assets.HIPPOCAMPE,
                Assets.SARDINES
        };

        return choisirAssetHasard(projectiles);
    }

    /**
     * Méthode qui choisit un asset aléatoire faisant partie d'un tableau
     *
     * @param assets tableau de Assets
     * @return un des assets aléatoires du tableau donné en paramètre
     */
    private static Assets choisirAssetHasard(Assets[] assets) {
        Assets assetChoisi = null;

        int nbrAleatoire = generateurAleatoire.nextInt(assets.length);

        for (int i = 0; i < assets.length; i++) {
            if (nbrAleatoire == i)
                assetChoisi = assets[i];
        }
        return assetChoisi;
    }


}
