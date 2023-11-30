/**
 *
 */

package ca.qc.bdeb.sim203.projetjavafx;


import javafx.scene.input.*;

import java.util.*;

public class Input {
    /**
     * Associe chaque touche du clavier qui a été touché par l'utilisateur à "true" ou à "false"
     * dépendant de si l'utilisateur pèse sur la touche ou non
     */
    public static HashMap<KeyCode, Boolean> touches = new HashMap<>();

    /**
     * Méthode qui va chercher la touche du clavier. Retourne la valeur du booléan si la clé est dans le HashMap.
     * Sinon, si la clé ne se retrouve pas dans le HashMap, retourne false par défaut.
     *
     * @param touche le code de la touche appuyée
     * @return boolean true si la clé est dans le hashmap
     */
    public static boolean isKeyPressed(KeyCode touche) {

        return touches.getOrDefault(touche, false);
    }

    /**
     * Mettre/ajuster la touche dans le Hashmap avec son boolean associé
     *
     * @param touche     le code de la touche appuyé
     * @param estAppuyee boolean si la touche est appuyée ou non
     */
    public static void setKeyPressed(KeyCode touche, boolean estAppuyee) {
        touches.put(touche, estAppuyee);
    }

}
