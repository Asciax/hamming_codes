// Author : Robert C.
// July 2023

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class HammingApplication extends Application{

    @Override
    public void start(Stage MainStage) {
        Text menuName = new Text();
        menuName.setText("Main Menu");
        menuName.setFont(new Font(20));
        menuName.setX(0);
        menuName.setY(200);

        Group root = new Group(menuName);
        ObservableList<Node> rootList = root.getChildren();

        Scene scene = new Scene(root, 640, 480);

        MainStage.setTitle("Hamming Application");
        MainStage.setScene(scene);
        MainStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
