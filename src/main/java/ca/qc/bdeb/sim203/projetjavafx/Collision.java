package ca.qc.bdeb.sim203.projetjavafx;

public class Collision {

    /**
     * Méthode qui crée 4 booleans pour chaque scénario où deux objets pourraient être en collision et appelle une autre
     * méthode pour vérifier
     * @param objet1 premier objet de jeu pouvant entrer en collision
     * @param objet2 deuxième objet de jeu pouvant entrer en collision
     * @return boolean qui indique s'il y a une collision entre les deux objets
     */
    public static boolean estEnCollision(ObjetJeu objet1, ObjetJeu objet2){
        boolean coinHautGauche = verifierSiCoinDansObjet(objet1, objet2.getXGauche(), objet2.getYHaut());
        boolean coinBasGauche = verifierSiCoinDansObjet(objet1, objet2.getXGauche(), objet2.getYBas());
        boolean coinHautDroite = verifierSiCoinDansObjet(objet1, objet2.getXDroite(), objet2.getYHaut());
        boolean coinBasDroite = verifierSiCoinDansObjet(objet1, objet2.getXDroite(), objet2.getYBas());

        return coinHautGauche || coinBasGauche || coinHautDroite || coinBasDroite;
    }

    /**
     * Méthode qui vérifie si les coins spécifiés se trouvent à l'intérieur de l'objet de jeu
     * @param objet objet de jeu qui est possiblement en collision
     * @param xCoin coin de l'objet entrant possiblement en collision (en x)
     * @param yCoin coin de l'objet entrant possiblement en collision (en y)
     * @return boolean true s'il y a des coins en contact avec l'objet, sinon false
     */
    private static boolean verifierSiCoinDansObjet(ObjetJeu objet, double xCoin, double yCoin){
        return ((objet.getXGauche() < xCoin) && (xCoin < objet.getXDroite())) && //Coin supérieur gauche
                ((objet.getYHaut() < yCoin) && (yCoin < objet.getYBas()));

    }
}
