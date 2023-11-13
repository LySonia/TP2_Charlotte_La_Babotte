/**
 *
 */

package ca.qc.bdeb.sim203.projetjavafx;


import javafx.scene.input.*;

import java.util.*;

public class Input {
    //Associe chaque touche du clavier qui a été touché par l'utilisateur à "true" ou à "false"
    //dépendant de si l'utilisateur pèse sur la touche ou non
    public static HashMap<KeyCode, Boolean> touches = new HashMap<>();

    public static boolean isKeyPressed(KeyCode touche) {
        //Va chercher la touche du clavier. Retourne la valeur du booléan si la clé est dans le HashMap.
        //Sinon, si la clé ne se retrouve pas dans le HashMap, retourne false par défaut.
        return touches.getOrDefault(touche, false);
    }

    public static void setKeyPressed(KeyCode touche, boolean estAppuyee) {
        //Mettre/ajuster la touche dans le Hashmap avec son boolean associé
        touches.put(touche, estAppuyee);
    }

}
