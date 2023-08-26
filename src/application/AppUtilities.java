package application;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.Pane;

class AppUtilities {


    static boolean addElement(Parent parent, Object toAdd){
        /*
         * addElement(Group or Pane, Node toAdd)
         *  Adds an elements to the desired group or pane node.
         *  Upon failure, returns false.
         */
        if (Node.class.isAssignableFrom(toAdd.getClass())){
            if (Group.class.isAssignableFrom(parent.getClass())){
                ((Group) parent).getChildren().add((Node) toAdd);
            }
            else if (Pane.class.isAssignableFrom(parent.getClass())){
                ((Pane) parent).getChildren().add((Node) toAdd);
            }
            return true;
        }
        else{
            return false;
        }
    }
}
