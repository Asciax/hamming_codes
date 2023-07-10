package application;

import javafx.scene.Group;
import javafx.scene.Node;

class AppUtilities {


    static boolean addElement(Group parent, Object toAdd){
        /*
         * addElement(Group, Node toAdd)
         *  Adds an elements to the desired group node.
         *  Upon failure, returns false.
         */
        if (Node.class.isAssignableFrom(toAdd.getClass())){
            parent.getChildren().add((Node) toAdd);
            return true;
        }
        else{
            return false;
        }
    }
}
