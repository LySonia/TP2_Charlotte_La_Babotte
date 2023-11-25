package ca.qc.bdeb.sim203.projetjavafx;

public class Collision {
    public static boolean estEnCollision(ObjetJeu objet1, ObjetJeu objet2){
        boolean coinHautGauche = verifierSiCoinDansObjet(objet1, objet2.getXGauche(), objet2.getYHaut());
        boolean coinBasGauche = verifierSiCoinDansObjet(objet1, objet2.getXGauche(), objet2.getYBas());
        boolean coinHautDroite = verifierSiCoinDansObjet(objet1, objet2.getXDroite(), objet2.getYHaut());
        boolean coinBasDroite = verifierSiCoinDansObjet(objet1, objet2.getXDroite(), objet2.getYBas());

        if (coinHautGauche || coinBasGauche || coinHautDroite || coinBasDroite) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean verifierSiCoinDansObjet(ObjetJeu objet, double xCoin, double yCoin){
        boolean estDansObjet = false;

        if (((objet.getXGauche() < xCoin) && (xCoin < objet.getXDroite())) && //Coin supérieur gauche
                ((objet.getYHaut() < yCoin) && (yCoin < objet.getYBas()))) {
            estDansObjet = true;
        }
        return estDansObjet;
    }
}
