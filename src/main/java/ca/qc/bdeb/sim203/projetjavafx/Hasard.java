package ca.qc.bdeb.sim203.projetjavafx;

import java.util.*;

//Classe avec méthodes statiques, pcq j'ai la flemme de créer un nouveau object random à chaque fois que j'en ai besoin
//Tout ce qui peut être généré par hasard peut se faire générer icite
public class Hasard {
    public static  Random generateurAleatoire = new Random();

    public static int nextInt(int borneSupExclue) {
        return generateurAleatoire.nextInt(borneSupExclue);
    }

    public static int nextInt(int borneInfInclue, int borneSupInclue) {
        return generateurAleatoire.nextInt(borneSupInclue - borneInfInclue) + borneInfInclue;
    }

    public static double nextDouble(double borneInférieureInclue, double borneSuperieureInclue) {
        return generateurAleatoire.nextDouble(borneSuperieureInclue - borneInférieureInclue) + borneInférieureInclue;
    }

    public static Random getGenerateurAleatoire() {
        return generateurAleatoire;
    }

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

    public static Assets choisirTypeProjectileHasard() {
        Assets[] projectiles = {
                Assets.ETOILE,
                Assets.HIPPOCAMPE,
                Assets.SARDINES
        };

        return choisirAssetHasard(projectiles);
    }

    private static Assets choisirAssetHasard(Assets[] assets) {
        Assets assetChoisi = null;

        int nbrAleatoire = nextInt(assets.length);

        for (int i = 0; i < assets.length; i++) {
            if (nbrAleatoire == i)
                assetChoisi = assets[i];
        }
        return assetChoisi;
    }


}
